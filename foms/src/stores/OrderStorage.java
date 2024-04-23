package stores;

import java.util.HashMap;
import java.util.Map;

import models.Order;

public class OrderStorage implements Storage {
    private Map<Integer, Order> orders = new HashMap<>();

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
        // Implementation depends on the persistence mechanism (e.g., serialization, database)
    }

    @Override
    public void load() {
        // Implementation depends on the persistence mechanism
    }

    @Override
    public void clear() {
        orders.clear();
    }
}

