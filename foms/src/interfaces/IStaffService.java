package interfaces;

import java.util.List;

import models.Account;
import models.Order;
import models.User;
import utils.exceptions.AccountNotFoundException;
import utils.exceptions.PasswordMismatchException;
import utils.exceptions.PasswordValidationException;

/**
 * The IStaffService interface provides methods for managing staff-related operations.
 */
public interface IStaffService {

    /**
     * Retrieves a list of orders for a given branch ID.
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
     * @param user the user object
     * @param oldPassword the old password
     * @param newPassword the new password
     * @throws AccountNotFoundException if the account is not found
     * @throws PasswordMismatchException if the old password does not match the account's current password
     * @throws PasswordValidationException if the new password does not meet the password validation requirements
     */
    public void changePassword(User user, String oldPassword, String newPassword) throws AccountNotFoundException, PasswordMismatchException, PasswordValidationException;

    /**
     * Cancels an order.
     *
     * @param orderID the ID of the order to be canceled
     */
    public void cancelOrder(int orderID);
}
