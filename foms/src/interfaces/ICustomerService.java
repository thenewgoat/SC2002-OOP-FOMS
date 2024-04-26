package interfaces;

import java.time.LocalDateTime;
import java.util.List;

import models.BranchMenuItem;
import models.Order;
import models.PaymentMethod;

/**
 * This interface provides the methods related to customer actions in a Fastfood Ordering and Management System.
 * It provides functionality to manage orders, menu items, and payment methods for customers.
 */
public interface ICustomerService {

    /**
     * Retrieves an order by its ID.
     * 
     * @param OrderID The unique identifier of the order.
     * @return The Order object if found; otherwise, null.
     */
    public Order getOrder(int OrderID);

    /**
     * Records a new order into the system.
     * 
     * @param order The order to be added.
     */
    public void newOrder(Order order);

    /**
     * Retrieves a list of menu items available at a specific branch.
     * 
     * @param BranchID The unique identifier of the branch.
     * @return A list of BranchMenuItem objects.
     */
    public List<BranchMenuItem> getBranchMenuItemList(int BranchID);

    /**
     * Allows a customer to collect a ready order by marking it as completed.
     * 
     * @param CustomerID The unique identifier of the customer.
     * @return A list of Order objects.
     */
    public Boolean collectOrder(int OrderID);

    /**
     * Retrieves the next available order ID to be used when creating a new order.
     *
     * @return The next order ID as an integer.
     */
    public int getNextOrderID();

    /**
     * Gets the name of a branch based on its identifier.
     *
     * @param branchID The unique identifier of the branch.
     * @return The name of the branch.
     */
    public String getBranchName(int branchID);

    /**
     * Updates the details of a specific menu item in a branch's menu.
     *
     * @param item The BranchMenuItem object containing the updated details to be saved.
     */
    public void updateBranchMenuItem(BranchMenuItem item);

    /**
     * Retrieves a specific menu item from a branch based on the item's name.
     *
     * @param branchID The ID of the branch where the menu item is located.
     * @param itemName The name of the item to retrieve.
     * @return The corresponding BranchMenuItem object if found, or null if no item matches.
     */
    public BranchMenuItem getBranchMenuItem(int branchID, String itemName);

    /**
     * Retrieves a list of payment methods available for a specific type (e.g., credit card, online payment).
     *
     * @param type The type of payment methods to retrieve (e.g., "Credit/Debit Card", "Online Payment").
     * @return A list of PaymentMethod objects that match the specified type.
     */
    public List<PaymentMethod> getPaymentMethods(String type);

    /**
     * Retrieves the time when an order was marked ready for collection.
     *
     * @param OrderID The unique identifier of the order.
     * @return The estimated time when the order will be ready.
     */
    public LocalDateTime getReadyTime(int OrderID);

    /**
     * Sets the status of an order to indicate that it is ready for collection.
     *
     * @param OrderID The unique identifier of the order.
     */
    public void setOrderStatus(int OrderID);
}
