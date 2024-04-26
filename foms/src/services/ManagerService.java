package services;

import java.util.ArrayList;
import java.util.List;

import enums.OrderStatus;
import interfaces.IManagerService;
import models.Order;
import models.User;
import models.Account;
import models.BranchMenuItem;
import models.BranchUser;
import stores.BranchMenuItemStorage;
import stores.BranchStorage;
import stores.BranchUserStorage;
import stores.OrderStorage;
import stores.PasswordStorage;
import utils.exceptions.AccountNotFoundException;
import utils.exceptions.PasswordMismatchException;
import utils.exceptions.PasswordValidationException;

/**
 * The ManagerService class implements the IManagerService interface and provides
 * methods to manage orders, accounts, staff, branch menu items, and categories.
 * It allows retrieving and updating orders, finding accounts by login ID,
 * changing passwords, retrieving staff list, retrieving branch menu items,
 * adding, editing, and removing branch menu items, retrieving branch name,
 * displaying menu categories, adding categories, retrieving categories,
 * getting the next item ID, and canceling orders.
 */
public class ManagerService implements IManagerService{

    /**
     * Retrieves a list of orders for a specific branch.
     *
     * @param branchID the ID of the branch
     * @return a list of orders belonging to the specified branch, or null if no orders are found
     */
    @Override
    public List<Order> getOrders(int branchID) {
        Order[] orders = OrderStorage.getAll();
        List<Order> branchOrders = new ArrayList<>();
        if (orders != null) {
            for (Order order : orders) {
                if (order.getBranchID() == branchID) {
                    branchOrders.add(order);
                }
            }
            return branchOrders;
        }
        return null;
    }

    /**
        * Retrieves the order with the specified order ID.
        *
        * @param orderID the ID of the order to retrieve
        * @return the order with the specified ID, or null if no order is found
        */
    @Override
    public Order getOrder(int orderID) {
        Order order = OrderStorage.get(orderID);
        return order;
    }

    /**
     * Updates the status of an order.
     * 
     * @param orderID the ID of the order to update
     * @return true if the order status was successfully updated to "READY", false otherwise
     */
    @Override
    public Boolean updateOrderStatus(int orderID) {
        Order order = getOrder(orderID);
        if (order != null) {
            if(order.getOrderStatus() == OrderStatus.PREPARING){
                order.setOrderStatus(OrderStatus.READY);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
        * Finds an account by login ID.
        *
        * @param loginID the login ID to search for
        * @return the account with the specified login ID, or null if not found
        */
    @Override
    public Account findAccountByLoginID(String loginID) {
        for (Account account : PasswordStorage.getAll()) {
            if (account.getLoginID().equals(loginID)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Changes the password for a given user.
     *
     * @param user         The user whose password needs to be changed.
     * @param oldPassword  The old password to be verified.
     * @param newPassword  The new password to be set.
     * @throws AccountNotFoundException    If no account is found with the given user's login ID.
     * @throws PasswordMismatchException    If the old password provided does not match the account's current password.
     * @throws PasswordValidationException If the new password is null, empty, or equals to "password".
     */
    @Override
    public void changePassword(User user, String oldPassword, String newPassword) throws AccountNotFoundException, PasswordMismatchException, PasswordValidationException {
        Account account = findAccountByLoginID(user.getLoginID());
    
        if (account == null) {
            throw new AccountNotFoundException("No account found with login ID: " + user.getLoginID());
        }
        if (!account.getPassword().equals(oldPassword)) {
            throw new PasswordMismatchException("The old password provided is incorrect.");
        }
        if (newPassword == null || newPassword.isEmpty() || newPassword.equals("password")) {
            throw new PasswordValidationException("The new password is invalid. It cannot be null, empty, or 'password'.");
        }
    
        account.setPassword(newPassword);
        PasswordStorage.update(account);
    }

    /**
     * Retrieves a list of staff members associated with the specified branch ID.
     *
     * @param branchID the ID of the branch
     * @return a list of BranchUser objects representing the staff members
     */
    @Override
    public List<BranchUser> getStaffList(int branchID) {
        List<BranchUser> staffList = new ArrayList<>();
        BranchUser[] users = BranchUserStorage.getAll();
        for (BranchUser user : users) {
            if (user.getBranchID() == branchID) {
                staffList.add(user);
            }
        }
        return staffList;
    }

    /**
     * Retrieves a list of branch menu items based on the given branch ID.
     *
     * @param BranchID the ID of the branch
     * @return a list of branch menu items
     */
    @Override
    public List<BranchMenuItem> getBranchMenuItemList(int BranchID) {
        BranchMenuItem[] items = BranchMenuItemStorage.getAll();
        List<BranchMenuItem> branchItems = new ArrayList<>(); 
        if (items != null) {
            for (BranchMenuItem item : items) {
                if(item.getBranchID() == BranchID){
                    branchItems.add(item);
                }
            }
            return branchItems;
        } else {
            return null;
        }
    }

    /**
        * Adds a branch menu item to the storage and prints it.
        *
        * @param item the branch menu item to be added
        */
    @Override
    public void addBranchMenuItem(BranchMenuItem item) {
        BranchMenuItemStorage.add(item);
        System.out.println(item);
    }

    /**
        * Edits a branch menu item.
        *
        * @param item the branch menu item to be edited
        */
    @Override
    public void editBranchMenuItem(BranchMenuItem item) {
        BranchMenuItemStorage.update(item);
    }

    /**
     * Removes a branch menu item from the storage.
     *
     * @param item the branch menu item to be removed
     */
    @Override
    public void removeBranchMenuItem(BranchMenuItem item) {
        BranchMenuItemStorage.remove(item);
    }

    /**
        * Returns the name of the branch with the specified ID.
        *
        * @param branchID the ID of the branch
        * @return the name of the branch
        */
    @Override
    public String getBranchName(int branchID) {
        return BranchStorage.get(branchID).getName();
    }

    /**
     * Displays the menu categories.
     *
     * @return the number of menu categories displayed
     */
    @Override
    public int displayMenuCategories() {
        return BranchMenuItemStorage.displayMenuCategories();
    }

    /**
        * Adds a new category to the branch menu item storage.
        *
        * @param category the category to be added
        */
    @Override
    public void addCategory(String category) {
        BranchMenuItemStorage.addUniqueCategory(category);
    }

    /**
     * Retrieves a list of categories from the BranchMenuItemStorage.
     *
     * @return a list of categories
     */
    @Override
    public List<String> getCategories() {
        return BranchMenuItemStorage.getCategories();
    }

    /**
     * Returns the next available item ID.
     * 
     * @return The next available item ID.
     */
    @Override
    public int getNextItemID() {
        BranchMenuItem[] items = BranchMenuItemStorage.getAll();
        int id = 0;
        for(BranchMenuItem item : items){
            if(item.getItemID() > id){
                id = item.getItemID();
            }
        }
        return id + 1;
    }

    /**
     * Cancels an order with the specified order ID.
     *
     * @param orderID the ID of the order to be cancelled
     */
    @Override
    public void cancelOrder(int orderID) {
        Order order = getOrder(orderID);
        if (order != null) {
            order.setOrderStatus(OrderStatus.CANCELLED);
        }
    }
}
