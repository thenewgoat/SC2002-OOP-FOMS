package interfaces;

import java.util.List;

import models.Account;
import models.Order;
import models.User;
import utils.exceptions.AccountNotFoundException;
import utils.exceptions.PasswordMismatchException;
import utils.exceptions.PasswordValidationException;

public interface IStaffService {

    public List<Order> getOrders(int branchID);

    public Order getOrder(int orderID);

    public Boolean updateOrderStatus(int orderID);

    public Account findAccountByLoginID(String loginID);

    public void changePassword(User user, String oldPassword, String newPassword) throws AccountNotFoundException, PasswordMismatchException, PasswordValidationException;

    public void cancelOrder(int orderID);
}
