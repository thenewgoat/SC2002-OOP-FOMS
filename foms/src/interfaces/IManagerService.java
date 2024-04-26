package interfaces;

import java.util.List;


import models.Order;
import models.User;
import models.Account;
import models.BranchMenuItem;
import models.BranchUser;
import utils.exceptions.AccountNotFoundException;
import utils.exceptions.PasswordMismatchException;
import utils.exceptions.PasswordValidationException;

/**
 * This interface defines the operations by the manager service in the FOMS system.
 * It provides methods to manage orders, accounts, staff, menu items, and categories.
 */
public interface IManagerService {

    /**
     * Retrieves a list of orders for a specific branch.
     *
     * @param branchID the ID of the branch
     * @return a list of orders
     */
    public List<Order> getOrders(int branchID);

    /**
     * Retrieves an order by its ID.
     *
     * @param orderID the ID of the order
     * @return the order object
     */
    public Order getOrder(int orderID);

    /**
     * Updates the status of an order.
     *
     * @param orderID the ID of the order
     * @return true if the order status is successfully updated, false otherwise
     */
    public Boolean updateOrderStatus(int orderID);

    /**
     * Finds an account by its login ID.
     *
     * @param loginID the login ID of the account
     * @return the account object
     */
    public Account findAccountByLoginID(String loginID);

    /**
     * Changes the password for a user.
     *
     * @param user         the user object
     * @param oldPassword  the old password
     * @param newPassword  the new password
     * @throws AccountNotFoundException     if the account is not found
     * @throws PasswordMismatchException    if the old password does not match the account's password
     * @throws PasswordValidationException  if the new password does not meet the password validation rules
     */
    public void changePassword(User user, String oldPassword, String newPassword) throws AccountNotFoundException, PasswordMismatchException, PasswordValidationException;

    /**
     * Retrieves a list of staff members for a specific branch.
     *
     * @param branchID the ID of the branch
     * @return a list of staff members
     */
    public List<BranchUser> getStaffList(int branchID);

    /**
     * Retrieves a list of menu items for a specific branch.
     *
     * @param branchID the ID of the branch
     * @return a list of menu items
     */
    public List<BranchMenuItem> getBranchMenuItemList(int branchID);

    /**
     * Adds a new menu item to a branch.
     *
     * @param item the menu item to be added
     */
    public void addBranchMenuItem(BranchMenuItem item);

    /**
     * Edits an existing menu item of a branch.
     *
     * @param item the menu item to be edited
     */
    public void editBranchMenuItem(BranchMenuItem item);

    /**
     * Removes a menu item from a branch.
     *
     * @param item the menu item to be removed
     */
    public void removeBranchMenuItem(BranchMenuItem item);

    /**
     * Retrieves the name of a branch by its ID.
     *
     * @param branchID the ID of the branch
     * @return the name of the branch
     */
    public String getBranchName(int branchID);

    /**
     * Displays the menu categories.
     *
     * @return the number of menu categories
     */
    public int displayMenuCategories();

    /**
     * Adds a new category to the menu.
     *
     * @param category the category to be added
     */
    public void addCategory(String category);

    /**
     * Retrieves a list of menu categories.
     *
     * @return a list of menu categories
     */
    public List<String> getCategories();

    /**
     * Retrieves the next available item ID.
     *
     * @return the next item ID
     */
    public int getNextItemID();

    /**
     * Cancels an order.
     *
     * @param orderID the ID of the order to be canceled
     */
    public void cancelOrder(int orderID);
}
