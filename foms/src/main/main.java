package main;

import controllers.Welcome;
import stores.UserStorage;
import stores.*;
import test.dataPersistenceTest;

public class main {
    public static void main(String[] args) throws Exception {

        //dataPersistenceTest.test();

        BranchUserStorage.load();
        BranchStorage.load();
        UserStorage.load();
        OrderStorage.load();
        PasswordStorage.load();
        PaymentMethodStorage.load();
        BranchMenuItemStorage.load();

        Welcome.welcome();
    }
}
