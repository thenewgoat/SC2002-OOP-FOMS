package stores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import models.Order;

public class OrderStorage implements Storage {
    private final String orderDataPath = "data/orders.ser";
    private Map<Integer, Order> orders = new HashMap<>();

    public OrderStorage() {
        load();
    }

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

    @Override
    public void remove(Object object) {
        if (object instanceof Order) {
            Order order = (Order) object;
            orders.remove(order.getOrderID());
        } else {
            throw new IllegalArgumentException("Object must be an instance of Order.");
        }
    }

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

    @Override
    public Object get(int orderID) {
        return orders.get(orderID);
    }

    @Override
    public Object get(String name) {
        throw new UnsupportedOperationException("Use get(int orderID) for retrieving orders.");
    }

    @Override
    public Object[] getAll() {
        return orders.values().toArray(new Order[0]);
    }

    @Override
    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(orderDataPath))) {
            oos.writeObject(orders);
        } catch (IOException e) {
            System.out.println("Error saving order storage: " + e.getMessage());
        }
    }

    @Override
    public void load() {
        File file = new File(orderDataPath);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(orderDataPath))) {
                orders = (Map<Integer, Order>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading order storage: " + e.getMessage());
            }
        }else{
            orders = new HashMap<>();
            System.out.println("Order storage file not found.");
            save();
        }
    }

    @Override
    public void clear() {
        orders.clear();
    }
}

