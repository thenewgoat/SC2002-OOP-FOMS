package services;

import java.util.Map;

import models.Account;
import models.Branch;
import models.BranchMenuItem;
import models.BranchUser;
import models.Order;
import models.PaymentMethod;
import models.User;

public interface FileDataService {
    
    public Map<Integer, Order> importOrderData();

    public boolean exportOrderData();

    public Map<String, User> importUserData();

    public boolean exportUserData();

    public Map<String, BranchUser> importBranchUserData();

    public boolean exportBranchUserData();

    public Map<Integer, BranchMenuItem> importMenuData();

    public void exportMenuData();

    public Map<String, PaymentMethod> importPaymentMethodData();

    public void exportPaymentMethodData();

    public Map<Integer, Branch> importBranchData();

    public void exportBranchData();

    public Map<String, Account> importPasswordData();

    public void exportPasswordData();

}
