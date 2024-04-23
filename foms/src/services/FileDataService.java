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

    public boolean exportOrderData(Map<Integer, Order> map);

    public Map<String, User> importUserData();

    public boolean exportUserData(Map<String, User> map);

    public Map<String, BranchUser> importBranchUserData();

    public boolean exportBranchUserData(Map<String, BranchUser> map);

    public Map<Integer, BranchMenuItem> importMenuData();

    public boolean exportMenuData(Map<Integer, BranchMenuItem> map);

    public Map<String, PaymentMethod> importPaymentMethodData();

    public boolean exportPaymentMethodData(Map<String, PaymentMethod> map);

    public Map<Integer, Branch> importBranchData();

    public boolean exportBranchData(Map<Integer, Branch> map);

    public Map<String, Account> importPasswordData();

    public boolean exportPasswordData(Map<String, Account> map);

}
