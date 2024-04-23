package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import enums.OrderStatus;
import enums.OrderType;

/**
 * Represents an order in the system.
 */
public class Order implements Serializable {
    private int orderID;
    private int branchID;
    private List<OrderItem> orderItems;
    private double totalPrice;
    private OrderStatus orderStatus;
    private OrderType orderType;
    private LocalDateTime orderTime;
    private ScheduledExecutorService scheduler;

    /**
     * Constructs an Order object with the specified order ID, branch ID, order items, order type, and total price.
     *
     * @param orderID     the ID of the order
     * @param branchID    the ID of the branch
     * @param orderItems  the list of order items
     * @param orderType   the type of the order
     * @param totalPrice the total price of the order
     */
    public Order(int orderID, int branchID, List<OrderItem> orderItems, OrderType orderType, double totalPrice) {
        this.orderID = orderID;
        this.branchID = branchID;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.orderStatus = OrderStatus.PREPARING;
        this.orderType = orderType;
        this.orderTime = LocalDateTime.now();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Gets the total price of the order.
     *
     * @return the total price of the order
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Schedules the cancellation of the order after a specified delay.
     * If the order status is READY, it will be changed to CANCELLED.
     * The scheduler will be shutdown after the cancellation.
     */
    private void scheduleCancellation() {
        long delayUntilCancellation = 60; // Delay in seconds
        this.scheduler.schedule(() -> {
            if (orderStatus == OrderStatus.READY) {
                orderStatus = OrderStatus.CANCELLED;
                scheduler.shutdown();
            }
        }, delayUntilCancellation, TimeUnit.SECONDS);
    }

    /**
     * Sets the status of the order.
     *
     * @param status the status of the order
     */
    public void setOrderStatus(OrderStatus status) {
        this.orderStatus = status;
        if (status == OrderStatus.READY) {
            scheduleCancellation();
        }
    }

    /**
     * Completes the order.
     */
    public void completeOrder() {
        scheduler.shutdownNow();
        if (orderStatus == OrderStatus.READY) {
            this.setOrderStatus(OrderStatus.COMPLETED);
        }
    }

    /**
     * Gets the status of the order.
     *
     * @return the status of the order
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * Displays the details of the order.
     */
    public void displayOrderDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Order ID: " + orderID);
        System.out.println("Branch ID: " + branchID);
        System.out.println("Order Status: " + orderStatus);
        System.out.println("Order Type: " + orderType);
        System.out.println("Order Time: " + orderTime.format(formatter));
        System.out.println("Order Items:");
        for (OrderItem item : orderItems) {
            System.out.println("\tItem Name: " + item.getItemName() + ", Category: " + item.getCategory() +
                    ", Quantity: " + item.getQuantity() + ", Price: $" + item.getPrice() + ", SubTotal: $" + item.getPrice() * item.getQuantity());
        }
        System.out.println("Total Price: " + totalPrice);
        System.out.println("--------------------------------------------------");
    }

    /**
     * Gets the ID of the order.
     *
     * @return the ID of the order
     */
    public int getOrderID() {
        return this.orderID;
    }
}
