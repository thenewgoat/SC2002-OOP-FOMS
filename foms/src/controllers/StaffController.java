package controllers;

import java.util.List;
import java.util.Scanner;

import enums.OrderStatus;
import interfaces.IOrderView;
import interfaces.IBranchUserService;
import models.BranchUser;
import models.Order;
import models.User;
import services.StaffService;
import utils.ChangePage;
import utils.exceptions.PageBackException;

public class StaffController {

    private static final Scanner sc = new Scanner(System.in);

    protected static final IBranchUserService staffService = new StaffService();

    protected static IOrderView orderView;

    public static void start(User user) {
        BranchUser branchUser = (BranchUser) user;
        int branchID = branchUser.getBranchID();
        Boolean exit = false;
        do {
            ChangePage.changePage();
            System.out.println("Welcome " + user.getName() + "!");
            System.out.println("What would you like to do?");
            System.out.println();
            System.out.println("\t1. Display pending orders");
            System.out.println("\t2. View order details");
            System.out.println("\t3. Process order");
            System.out.println("\t4. Change password");
            System.out.println("\t5. Log out");
            System.out.println();
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            try {
                switch (choice) {
                    case 1:
                        displayPendingOrders(branchID);
                        break;
                    case 2:
                        viewOrderDetails();
                        break;
                    case 3:
                        processOrder();
                        break;
                    case 4:
                        // changePassword();
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Press <enter> to try again.");
                        sc.nextLine();
                        throw new PageBackException();
                }
            } catch (PageBackException e) {
                start(user);
            }
        } while (exit == false);
        // implement logout
    }

    private static void displayPendingOrders(int branchID) {
        List<Order> orders = staffService.getOrders(branchID);
        if (orders != null && orders.size() > 0){
            for (Order order : orders) {
                if(order.getOrderStatus() == OrderStatus.PREPARING){
                    orderView.displayOrderDetails(order);
                }
            }
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        } else {
            System.out.println("No pending orders.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
    }

    private static void viewOrderDetails() {
        System.out.print("Enter order ID: ");
        int orderID = sc.nextInt();
        sc.nextLine();
        Order order = staffService.getOrder(orderID);
        if (order == null) {
            System.out.println("Order not found.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
        orderView.displayOrderDetails(order);
        System.out.println("Press <enter> to continue.");
        sc.nextLine();
        return;
    }

    private static void processOrder() {
        System.out.print("Enter order ID: ");
        int orderID = sc.nextInt();
        sc.nextLine();
        Boolean success = staffService.updateOrderStatus(orderID);
        if (success) {
            System.out.println("Order processed successfully.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        } else {
            System.out.println("Order not found or already processed.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
    }
}
