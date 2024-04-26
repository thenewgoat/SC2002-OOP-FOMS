package services;

import java.util.ArrayList;
import java.util.List;

import enums.OrderStatus;
import interfaces.IStaffService;
import models.Order;
import models.User;
import models.Account;
import stores.OrderStorage;
import stores.PasswordStorage;
import utils.exceptions.AccountNotFoundException;
import utils.exceptions.PasswordMismatchException;
import utils.exceptions.PasswordValidationException;

/**
 * The StaffService class implements the IStaffService interface and provides
 * methods to perform various operations related to staff members.
 */
public class StaffService implements IStaffService {

    /**
     * Retrieves a list of orders associated with a specific branch.
     *
     * @param branchID the ID of the branch
     * @return a list of orders associated with the branch, or null if no orders are found
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
     * Retrieves an order by its ID.
     *
     * @param orderID the ID of the order
     * @return the order with the specified ID, or null if no order is found
     */
    @Override
    public Order getOrder(int orderID) {
        Order order = OrderStorage.get(orderID);
        return order;
    }

    /**
     * Updates the status of an order to "READY" if it is currently in the "PREPARING" state.
     *
     * @param orderID the ID of the order
     * @return true if the order status is successfully updated, false otherwise
     */
    @Override
    public Boolean updateOrderStatus(int orderID) {
        Order order = getOrder(orderID);
        if (order != null) {
            if (order.getOrderStatus() == OrderStatus.PREPARING) {
                order.setOrderStatus(OrderStatus.READY);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Finds an account by its login ID.
     *
     * @param loginID the login ID of the account
     * @return the account with the specified login ID, or null if no account is found
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
     * Changes the password of a user.
     *
     * @param user         the user whose password needs to be changed
     * @param oldPassword  the old password
     * @param newPassword  the new password
     * @throws AccountNotFoundException     if no account is found with the specified login ID
     * @throws PasswordMismatchException     if the old password provided is incorrect
     * @throws PasswordValidationException  if the new password is invalid
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
     * Cancels an order by setting its status to "CANCELLED".
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
