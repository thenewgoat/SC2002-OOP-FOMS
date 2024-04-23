package main;

import models.BranchMenuItem;
import models.BranchUser;
import models.Order;
import models.User;
import stores.BranchMenuItemStorage;
import stores.BranchStorage;
import stores.BranchUserStorage;
import stores.OrderStorage;
import stores.PasswordStorage;
import stores.PaymentMethodStorage;
import stores.UserStorage;

public class main {
    public static void main(String[] args) throws Exception {
        
        BranchMenuItemStorage branchMenuItemStorage = new BranchMenuItemStorage();
        BranchStorage branchStorage = new BranchStorage();
        BranchUserStorage branchUserStorage = new BranchUserStorage();
        OrderStorage orderStorage = new OrderStorage();
        PasswordStorage passwordStorage = new PasswordStorage();
        UserStorage userStorage = new UserStorage();
        PaymentMethodStorage paymentMethodStorage = new PaymentMethodStorage();

        branchMenuItemStorage.getAll();
        

    }
}
