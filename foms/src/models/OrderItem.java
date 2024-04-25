package models;

import java.io.Serializable;

/**
 * Represents an item in an order.
 */
public class OrderItem implements Serializable{
    private String itemName;
    private String category;
    private int quantity;
    private double price;
    private String specialRequest;

    /**
     * Constructs an OrderItem object with the specified item name, category, quantity, and price.
     *
     * @param itemName  the name of the item
     * @param category  the category of the item
     * @param quantity  the quantity of the item
     * @param price     the price of the item
     * @param specialRequest the special request of the item
     * @throws IllegalArgumentException if the item name is null or empty, or if the quantity or price is less than or equal to 0
     */
    public OrderItem(String itemName, String category, int quantity, double price, String specialRequest) {
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        if (price <= 0.0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.specialRequest = specialRequest;
    }

    /**
     * Returns the name of the item.
     *
     * @return the name of the item
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Returns the category of the item.
     *
     * @return the category of the item
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns the quantity of the item.
     *
     * @return the quantity of the item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the price of the item.
     *
     * @return the price of the item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the special request of the item.
     *
     * @return the special request of the item
     */
    public String getSpecialRequest() {
        return specialRequest;
    }

    /**
     * Sets the quantity of the item.
     *
     * @param quantity the new quantity of the item
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
}