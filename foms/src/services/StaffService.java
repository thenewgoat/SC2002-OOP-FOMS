package services;

import java.util.ArrayList;
import java.util.List;

import enums.OrderStatus;
import interfaces.IBranchUserService;
import models.Order;
import models.User;
import models.Account;
import stores.OrderStorage;
import stores.PasswordStorage;
import utils.exceptions.AccountNotFoundException;
import utils.exceptions.PasswordMismatchException;
import utils.exceptions.PasswordValidationException;

public class StaffService implements IBranchUserService{

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

    @Override
    public Order getOrder(int orderID) {
        Order order = OrderStorage.get(orderID);
        return order;
    }

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

    public Account findAccountByLoginID(String loginID) {
        for (Account account : PasswordStorage.getAll()) {
            if (account.getLoginID().equals(loginID)) {
                return account;
            }
        }
        return null;
    }

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
}
