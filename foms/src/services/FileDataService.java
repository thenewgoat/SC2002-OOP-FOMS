package services;

import java.util.Map;

import models.BranchUser;
import models.Order;
import models.User;

public interface FileDataService {
    
    public Map<Integer, Order> importOrderData();

    public boolean exportOrderData();

    public Map<String, User> importUserData();

    public boolean exportUserData();

    public Map<String, BranchUser> importBranchUserData();

    public boolean exportBranchUserData();

    public void importMenuData();

    public void exportMenuData();

    public void importPaymentMethodData();

    public void exportPaymentMethodData();

    public void importBranchData();

    public void exportBranchData();

    public void importPasswordData();

    public void exportPasswordData();

}
