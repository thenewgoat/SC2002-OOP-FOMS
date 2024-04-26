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

public interface IManagerService {

    public List<Order> getOrders(int branchID);

    public Order getOrder(int orderID);

    public Boolean updateOrderStatus(int orderID);

    public Account findAccountByLoginID(String loginID);

    public void changePassword(User user, String oldPassword, String newPassword) throws AccountNotFoundException, PasswordMismatchException, PasswordValidationException;

    public List<BranchUser> getStaffList(int branchID);

    public List<BranchMenuItem> getBranchMenuItemList(int branchID);

    public void addBranchMenuItem(BranchMenuItem item);

    public void editBranchMenuItem(BranchMenuItem item);

    public void removeBranchMenuItem(BranchMenuItem item);

    public String getBranchName(int branchID);

    public int displayMenuCategories();

    public void addCategory(String category);

    public List<String> getCategories();

    public int getNextItemID();

    public void cancelOrder(int orderID);
}
