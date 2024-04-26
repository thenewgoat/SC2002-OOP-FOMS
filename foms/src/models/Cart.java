package models;

import java.util.ArrayList;
import java.util.List;

/**
 * The Cart class represents a shopping cart that holds a collection of order items.
 * It provides methods to add, remove, and edit items in the cart, as well as calculate the total price of the items.
 */
public class Cart {
    /**
     * Represents the list of order items in the cart.
     */
    private List<OrderItem> orderItems;
    /**
     * Represents the total price of the items in the cart.
     */
    private double totalPrice;

    /**
     * Constructs an empty Cart object.
     */
    public Cart() {
        this.orderItems = new ArrayList<>();
        this.totalPrice = 0;
    }

    /**
     * Returns the list of order items in the cart.
     *
     * @return the list of order items
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Calculates the total price of all items in the cart.
     * The total price is stored in the totalPrice field.
     * The total price is rounded to two decimal places.
     */
    public void calculateTotalPrice() {
        totalPrice = 0; 
        for (OrderItem item : orderItems) {
            totalPrice += Math.round((item.getPrice() * item.getQuantity()) * 100.0) / 100.0;
        }
    }

    /**
     * Returns the total price of all items in the cart.
     *
     * @return the total price of all items
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Adds an order item to the cart.
     * The total price of the cart is recalculated after adding the item.
     *
     * @param item the order item to be added
     */
    public void addItem(OrderItem item) {
        this.orderItems.add(item);
        calculateTotalPrice();
    }

    /**
     * Returns the order item with the specified item name.
     *
     * @param itemName the name of the item to search for
     * @return the order item with the specified item name, or null if not found
     */
    public OrderItem getItem(String itemName) {
        for (OrderItem item : orderItems) {
            if (item.getItemName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Edits the quantity of the order item with the specified item name.
     * The total price of the cart is recalculated after editing the item.
     *
     * @param itemName the name of the item to edit
     * @param quantity the new quantity of the item
     */
    public void editItem(String itemName, int quantity) {
        for (OrderItem orderItem : this.getOrderItems()) {
            if (orderItem.getItemName().equals(itemName)) {
                orderItem.setQuantity(quantity);
                break;
            }
        }
        calculateTotalPrice();
    }

    /**
     * Removes the order item with the specified item name from the cart.
     * The total price of the cart is recalculated after removing the item.
     *
     * @param itemName the name of the item to remove
     */
    public void removeItem(String itemName) {
        for (OrderItem item : orderItems) {
            if (item.getItemName().equals(itemName)) {
                orderItems.remove(item);
                break;
            }
        }
        calculateTotalPrice();
    }

    /**
     * Displays the details of all items in the cart.
     */
    public void displayItems() {
        for(OrderItem item : this.orderItems) {
            System.out.println("--------------------------------------------------");
            System.out.println("Item Name: " + item.getItemName());
            System.out.println("Item Category: " + item.getCategory());
            System.out.println("Item Price: " + item.getPrice());
            System.out.println("Item Quantity: " + item.getQuantity());
            System.out.println("Price of " + item.getQuantity() + " " + item.getItemName() + " is " + String.format("%.2f", item.getPrice() * item.getQuantity()));
            if(item.getSpecialRequest() != "none"){
                System.out.println("Special Request: " + item.getSpecialRequest());
            }
            System.out.println("--------------------------------------------------");
        }
        calculateTotalPrice();
        System.out.println("Total Price of Items: " + String.format("%.2f",this.totalPrice));
    }
}

