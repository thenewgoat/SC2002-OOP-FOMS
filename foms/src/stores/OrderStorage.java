package stores;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import models.Order;
import services.SerialDataService;

/**
 * The OrderStorage class implements the Storage interface and provides static methods to manage orders.
 */
public class OrderStorage {

    private static final String orderDataPath = "foms/data/orders.ser";
    private static HashMap<Integer, Order> orders = new HashMap<>();

    // Static initializer to load the orders when the class is first loaded
    static {
        load();
    }

    /**
     * Adds an order to the storage.
     *
     * @param order The order object to be added.
     * @throws IllegalArgumentException if the order is null or if an order with the same ID already exists.
     */
    public static void add(Order order) {
        if (order != null) {
            if (orders != null){
                if (!orders.containsKey(order.getOrderID())) {
                    orders.put(order.getOrderID(), order);
                    System.out.println("Order with ID " + order.getOrderID() + " added.");
                    Scanner sc = new Scanner(System.in);
                    sc.nextLine();
                } else {
                    throw new IllegalArgumentException("Order with ID " + order.getOrderID() + " already exists.");
                }
            }
            else orders.put(order.getOrderID(), order);
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null Order.");
        }
        save();
    }

    /**
     * Removes an order from the storage.
     *
     * @param order The order object to be removed.
     */
    public static void remove(Order order) {
        if (order != null) {
            orders.remove(order.getOrderID());
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null Order.");
        }
        save();
    }

    /**
     * Updates an existing order in the storage.
     *
     * @param order The order object to be updated.
     */
    public static void update(Order order) {
        if (order != null && orders.containsKey(order.getOrderID())) {
            orders.put(order.getOrderID(), order);
        } else {
            throw new IllegalArgumentException("Cannot update non-existing or null Order.");
        }
        save();
    }

    /**
     * Retrieves an order by its ID from the storage.
     *
     * @param orderID The ID of the order to retrieve.
     * @return The order object with the specified ID, or null if not found.
     */
    public static Order get(int orderID) {
        return orders.get(orderID);
    }

    /**
     * Retrieves all orders from the storage.
     *
     * @return An array of all order objects in the storage.
     */
    public static Order[] getAll() {
        if (orders == null) {
            return new Order[0];  // Return an empty array if orders is null
        }
        return orders.values().toArray(new Order[0]);
    }

    /**
     * Saves the orders to the data file.
     */
    public static void save() {
        SerialDataService serialDataService = new SerialDataService();
        serialDataService.exportOrderData(orders);
    }

    /**
     * Loads the orders from the data file.
     * If the file does not exist, a new empty storage is created and saved.
     */
    public static void load() {
        File file = new File(orderDataPath);
        if (file.exists()) {
            SerialDataService serialDataService = new SerialDataService();
            orders = serialDataService.importOrderData();
            if (orders == null){
                orders = new HashMap<>();
            }
        } else {
            orders = new HashMap<>();
            System.out.println("Order storage file not found. Creating new order storage.");
            save();
        }
    }

    /**
     * Clears all orders from the storage.
     */
    public static void clear() {
        orders.clear();
    }
}
