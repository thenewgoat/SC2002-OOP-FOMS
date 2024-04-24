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

public class ManagerService implements IManagerService{

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

    public void addBranchMenuItem(BranchMenuItem item) {
        BranchMenuItemStorage.add(item);
    }

    public void editBranchMenuItem(BranchMenuItem item) {
        BranchMenuItemStorage.update(item);
    }

    public void removeBranchMenuItem(BranchMenuItem item) {
        BranchMenuItemStorage.remove(item);
    }

    public String getBranchName(int branchID) {
        return BranchStorage.get(branchID).getName();
    }

    public int displayMenuCategories() {
        return BranchMenuItemStorage.displayMenuCategories();
    }

    public void addCategory(String category) {
        BranchMenuItemStorage.addUniqueCategory(category);
    }

    public List<String> getCategories() {
        return BranchMenuItemStorage.getCategories();
    }

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
}
