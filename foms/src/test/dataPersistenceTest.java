/**
 * This class is used to test the data persistence functionality of the FOMS application.
 * It retrieves and displays data from various storage classes.
 */
package test;

import java.util.Scanner;

import models.Account;
import models.Branch;
import models.BranchMenuItem;
import models.BranchUser;
import models.Order;
import models.PaymentMethod;
import models.User;
import stores.BranchMenuItemStorage;
import stores.BranchStorage;
import stores.BranchUserStorage;
import stores.OrderStorage;
import stores.PasswordStorage;
import stores.PaymentMethodStorage;
import stores.UserStorage;
import utils.FileCleanupUtility;

public class dataPersistenceTest {

    public static void test() throws Exception {


        System.out.println("Current working directory: " + System.getProperty("user.dir"));

        Scanner sc = new Scanner(System.in);
        BranchStorage branchStorage = new BranchStorage();
        Branch[] branches = branchStorage.getAll();
        System.out.println("Branches:");
        System.out.println("-------------------------------");
        for (Branch branch : branches) {
            System.out.println("Branch ID: " + branch.getID());
            System.out.println("Branch Name: " + branch.getName());
            System.out.println("Branch Location: " + branch.getLocation());
            System.out.println("Branch Staff Quota: " + branch.getStaffQuota());
            System.out.println("-------------------------------");
        }

        sc.nextLine();

        PaymentMethodStorage paymentMethodStorage = new PaymentMethodStorage();
        PaymentMethod[] paymentMethods = paymentMethodStorage.getAll();
        System.out.println("Payment Methods:");
        System.out.println("-------------------------------");
        for (PaymentMethod paymentMethod : paymentMethods) {
            System.out.println("Payment Method: " + paymentMethod.getPaymentMethod());
            System.out.println("-------------------------------");
        }
        
        sc.nextLine();

        BranchMenuItemStorage branchMenuItemStorage = new BranchMenuItemStorage();
        BranchMenuItem[] branchMenuItems = branchMenuItemStorage.getAll();
        System.out.println("Branch Menu Items:");
        System.out.println("-------------------------------");
        for (BranchMenuItem branchMenuItem : branchMenuItems) {
            System.out.println("Item ID: " + branchMenuItem.getItemID());
            System.out.println("Item Name: " + branchMenuItem.getName());
            System.out.println("Branch ID: " + branchMenuItem.getBranchID());
            System.out.println("-------------------------------");
        }
        
        sc.nextLine();
        
        BranchUserStorage branchUserStorage = new BranchUserStorage();
        BranchUser[] branchUsers = branchUserStorage.getAll();
        System.out.println("Branch Users:");
        System.out.println("Number of Branch Users: " + branchUsers.length);
        System.out.println("-------------------------------");
        for (BranchUser branchUser : branchUsers) {
            System.out.println("User ID: " + branchUser.getLoginID());
            System.out.println("Branch ID: " + branchUser.getBranchID());
            System.out.println("User Type: " + branchUser.getName());
            System.out.println("-------------------------------");
        }

        sc.nextLine();


        UserStorage userStorage = new UserStorage();
        User[] users = userStorage.getAll();
        System.out.println("Users:");
        System.out.println("Number of Users: " + users.length);
        System.out.println("-------------------------------");
        for (User user : users) {
            System.out.println("User ID: " + user.getLoginID());
            System.out.println("User Type: " + user.getName());
            System.out.println("-------------------------------");
        }

        sc.nextLine();

        OrderStorage orderStorage = new OrderStorage();
        Order[] orders = orderStorage.getAll();
        System.out.println("Orders:");
        System.out.println("Number of Orders: " + orders.length);
        System.out.println("-------------------------------");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderID());
            System.out.println("Total Price: " + order.getTotalPrice());
            System.out.println("Order Status: " + order.getOrderStatus());
            order.displayOrderDetails();
            System.out.println("-------------------------------");
        }

        sc.nextLine();

        PasswordStorage passwordStorage = new PasswordStorage();
        Account[] accounts = passwordStorage.getAll();
        System.out.println("Accounts:");
        System.out.println("Number of Accounts: " + accounts.length);
        System.out.println("-------------------------------");
        for (Account account : accounts) {
            System.out.println("User ID: " + account.getLoginID());
            System.out.println("Password: " + account.getPassword());
            System.out.println("-------------------------------");
        }

        sc.nextLine();

        FileCleanupUtility.deleteSerFiles("foms/data"); // Used to delete all .ser files in the data directory, delete before submission or when testing persistence

    }
}
