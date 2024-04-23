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

public class Order implements Serializable {
    private int orderID;
    private int branchID;
    private List<OrderItem> orderItems;
    private double totalPrice;
    private OrderStatus orderStatus;
    private OrderType orderType;
    private LocalDateTime orderTime;
    private ScheduledExecutorService scheduler;

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

    public double getTotalPrice() {
        return totalPrice;
    }

    private void scheduleCancellation() {
        long delayUntilCancellation = 60; // Delay in seconds
        this.scheduler.schedule(() -> {
            if (orderStatus == OrderStatus.READY) {
                orderStatus = OrderStatus.CANCELLED;
                System.out.println("Order " + orderID + " has been cancelled due to time out.");
                scheduler.shutdown();
            }
        }, delayUntilCancellation, TimeUnit.SECONDS);
    }



    public void setOrderStatus(OrderStatus status) {
        this.orderStatus = status;
        if (status == OrderStatus.READY) {
            scheduleCancellation();
        }
    }

    public void completeOrder() {
        scheduler.shutdownNow();
        if (orderStatus == OrderStatus.READY) {
            this.setOrderStatus(OrderStatus.COMPLETED);
        }
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

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

    public int getOrderID(){
        return this.orderID;
    }

}
