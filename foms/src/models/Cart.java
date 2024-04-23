package models;

import java.util.ArrayList;
import java.util.List;

import foms.utils.DefaultPrints;

public class Cart {
    private List<OrderItem> orderItems;
    private double totalPrice;

    public Cart() {
        this.orderItems = new ArrayList<>();
        this.totalPrice = 0;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void calculateTotalPrice() {
        totalPrice = 0; 
        for (OrderItem item : orderItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void addItem(OrderItem item) {
        this.orderItems.add(item);
    }

    public OrderItem getItem(String itemName) {
        for (OrderItem item : orderItems) {
            if (item.getItemName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void editItem(String itemName, int quantity) {
        for (OrderItem orderItem : this.getOrderItems()) {
            if (orderItem.getItemName().equals(itemName)) {
                orderItem.setQuantity(quantity);
                break;
            }
        }
    }

    public void removeItem(String itemName) {
        for (OrderItem item : orderItems) {
            if (item.getItemName().equals(itemName)) {
                orderItems.remove(item);
                break;
            }
        }
    }

    public void displayItems() {
        for(OrderItem item : this.orderItems) {
            System.out.println(DefaultPrints.header);
            System.out.println("Item Name: " + item.getItemName());
            System.out.println("Item Category: " + item.getCategory());
            System.out.println("Item Price: " + item.getPrice());
            System.out.println("Item Quantity: " + item.getQuantity());
            System.out.println("Price of " + item.getQuantity() + " " + item.getItemName() + " is " + item.getPrice() * item.getQuantity());
        }
        System.out.println(DefaultPrints.header);
        calculateTotalPrice();
        System.out.println("Total Price of Items: " + this.totalPrice);
    }
}
