package services;

import interfaces.IWelcomeService;
import models.Branch;
import stores.BranchMenuItemStorage;
import stores.BranchStorage;
import stores.BranchUserStorage;
import stores.OrderStorage;
import stores.PasswordStorage;
import stores.PaymentMethodStorage;
import stores.UserStorage;

public class WelcomeService implements IWelcomeService{

    @Override
    public void refresh() {
        BranchUserStorage.save();
        BranchStorage.save();
        UserStorage.save();
        OrderStorage.save();
        PasswordStorage.save();
        PaymentMethodStorage.save();
        BranchMenuItemStorage.save();
        BranchUserStorage.load();
        BranchStorage.load();
        UserStorage.load();
        OrderStorage.load();
        PasswordStorage.load();
        PaymentMethodStorage.load();
        BranchMenuItemStorage.load();
    }

    @Override
    public Branch[] getBranchList() {
        return BranchStorage.getAll();
    }

}
