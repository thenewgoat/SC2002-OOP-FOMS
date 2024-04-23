package models;

public class BranchMenuItem extends MenuItem {
    private double price;
    private int branchID;
    private int availability;
    private String description;
    
    public BranchMenuItem(String name, String category, double price, int availability, String description, int branchID) {
        super(name, category); // Call the superclass (MenuItem) constructor
        if (price <= 0.0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        this.price = price;
        this.branchID = branchID;
        this.availability = availability;
        this.description = description;
    }
    
    // Getters for price and branchID. The getName and getCategory methods are inherited from MenuItem.
    public double getPrice() {
        return price;
    }
    
    public int getBranchID() {
        return branchID;
    }

    public int getAvailability(){
        return availability;
    }

    public String getDescription(){
        return description;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setBranchID(int branchID){
        this.branchID = branchID;
    }

    public void setAvailability(int availability){
        this.availability = availability;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
