package services;

import java.util.HashMap;

import models.Account;
import models.Branch;
import models.BranchMenuItem;
import models.BranchUser;
import models.Order;
import models.PaymentMethod;
import models.User;

public interface FileDataService {
    
    public HashMap<Integer, Order> importOrderData();

    public boolean exportOrderData(HashMap<Integer, Order> HashMap);

    public HashMap<String, User> importUserData();

    public boolean exportUserData(HashMap<String, User> HashMap);

    public HashMap<String, BranchUser> importBranchUserData();

    public boolean exportBranchUserData(HashMap<String, BranchUser> HashMap);

    public HashMap<Integer, BranchMenuItem> importMenuData();

    public boolean exportMenuData(HashMap<Integer, BranchMenuItem> HashMap);

    public HashMap<String, PaymentMethod> importPaymentMethodData();

    public boolean exportPaymentMethodData(HashMap<String, PaymentMethod> HashMap);

    public HashMap<Integer, Branch> importBranchData();

    public boolean exportBranchData(HashMap<Integer, Branch> HashMap);

    public HashMap<String, Account> importPasswordData();

    public boolean exportPasswordData(HashMap<String, Account> HashMap);

}
