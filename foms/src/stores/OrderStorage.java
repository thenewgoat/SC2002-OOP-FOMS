package stores;

import java.io.File;
import java.util.HashMap;
import models.Order;
import services.SerialDataService;

/**
 * The OrderStorage class implements the Storage interface and provides methods to manage orders.
 */
public class OrderStorage implements Storage {
    private final String orderDataPath = "foms/data/orders.ser";
    private HashMap<Integer, Order> orders = new HashMap<>();

    /**
     * Constructs a new OrderStorage object and loads the orders from the data file.
     */
    public OrderStorage() {
        load();
    }

    /**
     * Adds an order to the storage.
     *
     * @param object The order object to be added.
     * @throws IllegalArgumentException if the object is not an instance of Order.
     */
    @Override
    public void add(Object object) {
        if (object instanceof Order) {
            Order order = (Order) object;
            if (!orders.containsKey(order.getOrderID())) {
                orders.put(order.getOrderID(), order);
            } else {
                throw new IllegalArgumentException("Order with ID " + order.getOrderID() + " already exists.");
            }
        } else {
            throw new IllegalArgumentException("Object must be an instance of Order.");
        }
    }

    /**
     * Removes an order from the storage.
     *
     * @param object The order object to be removed.
     * @throws IllegalArgumentException if the object is not an instance of Order.
     */
    @Override
    public void remove(Object object) {
        if (object instanceof Order) {
            Order order = (Order) object;
            orders.remove(order.getOrderID());
        } else {
            throw new IllegalArgumentException("Object must be an instance of Order.");
        }
    }

    /**
     * Updates an existing order in the storage.
     *
     * @param object The order object to be updated.
     * @throws IllegalArgumentException if the object is not an instance of Order.
     * @throws IllegalArgumentException if the order does not exist in the storage.
     */
    @Override
    public void update(Object object) {
        if (object instanceof Order) {
            Order order = (Order) object;
            if (orders.containsKey(order.getOrderID())) {
                orders.put(order.getOrderID(), order);
            } else {
                throw new IllegalArgumentException("Cannot update non-existing order.");
            }
        } else {
            throw new IllegalArgumentException("Object must be an instance of Order.");
        }
    }

    /**
     * Retrieves an order by its ID from the storage.
     *
     * @param orderID The ID of the order to retrieve.
     * @return The order object with the specified ID, or null if not found.
     */
    @Override
    public Order get(int orderID) {
        return orders.get(orderID);
    }

    /**
     * This method is not supported for the OrderStorage class.
     *
     * @param name The name of the order (not used).
     * @throws UnsupportedOperationException if called.
     */
    @Override
    public Order get(String name) {
        throw new UnsupportedOperationException("Use get(int orderID) for retrieving orders.");
    }

    /**
     * Retrieves all orders from the storage.
     *
     * @return An array of all order objects in the storage.
     */
    @Override
    public Order[] getAll() {
        return orders.values().toArray(new Order[0]);
    }

    /**
     * Saves the orders to the data file.
     */
    @Override
    public void save() {
        SerialDataService serialDataService = new SerialDataService();
        serialDataService.exportOrderData(orders);
    }

    /**
     * Loads the orders from the data file.
     * If the file does not exist, a new empty storage is created and saved.
     */
    @Override
    public void load() {
        File file = new File(orderDataPath);
        if (file.exists()) {
            SerialDataService serialDataService = new SerialDataService();
            orders = serialDataService.importOrderData();
        } else {
            orders = new HashMap<>();
            System.out.println("Order storage file not found. Creating new order storage.");
            save();
        }
    }

    /**
     * Clears all orders from the storage.
     */
    @Override
    public void clear() {
        orders.clear();
    }
}

