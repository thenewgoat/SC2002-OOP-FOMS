package models;

import java.io.Serializable;

/**
 * Represents a menu item specific to a branch in a restaurant.
 */
public class BranchMenuItem implements Serializable {
    private String name;
    private String category;
    private double price;
    private int branchID;
    private int availability;
    private String description;
    
    /**
     * Constructs a new BranchMenuItem object with the specified properties.
     * 
     * @param name the name of the menu item
     * @param category the category of the menu item
     * @param price the price of the menu item
     * @param availability the availability of the menu item
     * @param description the description of the menu item
     * @param branchID the ID of the branch associated with the menu item
     */
    public BranchMenuItem(String name, String category, double price, int availability, String description, int branchID) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.branchID = branchID;
        this.availability = availability;
        this.description = description;
    }
    
    /**
     * Returns the name of the menu item.
     * 
     * @return the name of the menu item
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the category of the menu item.
     * 
     * @return the category of the menu item
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns the price of the menu item.
     * 
     * @return the price of the menu item
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * Returns the ID of the branch associated with the menu item.
     * 
     * @return the ID of the branch associated with the menu item
     */
    public int getBranchID() {
        return branchID;
    }

    /**
     * Returns the availability of the menu item.
     * 
     * @return the availability of the menu item
     */
    public int getAvailability(){
        return availability;
    }

    /**
     * Returns the description of the menu item.
     * 
     * @return the description of the menu item
     */
    public String getDescription(){
        return description;
    }

    /**
     * Sets the name of the menu item.
     * 
     * @param name the name of the menu item
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the category of the menu item.
     * 
     * @param category the category of the menu item
     */
    public void setCategory(String category){
        this.category = category;
    }

    /**
     * Sets the price of the menu item.
     * 
     * @param price the price of the menu item
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * Sets the ID of the branch associated with the menu item.
     * 
     * @param branchID the ID of the branch associated with the menu item
     */
    public void setBranchID(int branchID){
        this.branchID = branchID;
    }

    /**
     * Sets the availability of the menu item.
     * 
     * @param availability the availability of the menu item
     */
    public void setAvailability(int availability){
        this.availability = availability;
    }

    /**
     * Sets the description of the menu item.
     * 
     * @param description the description of the menu item
     */
    public void setDescription(String description){
        this.description = description;
    }

}
