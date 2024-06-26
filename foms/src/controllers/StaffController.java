package controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import enums.OrderStatus;
import interfaces.IOrderView;
import interfaces.IStaffService;
import models.BranchUser;
import models.Order;
import models.User;
import services.StaffService;
import utils.ChangePage;
import utils.exceptions.AccountNotFoundException;
import utils.exceptions.PageBackException;
import utils.exceptions.PasswordMismatchException;
import utils.exceptions.PasswordValidationException;
import views.OrderDetailsView;

/**
 * Handles staff operations such as viewing and processing orders.
 */
public class StaffController {

    /**
     * The scanner to be used for user input.
     */
    private static final Scanner sc = new Scanner(System.in);

    /**
     * The service to be used for staff operations.
     */
    protected static final IStaffService staffService = new StaffService();

    /**
     * The view to be used for order details.
     */
    protected static IOrderView orderView;

    /**
     * Initiates the staff menu.
     * 
     * @param user The staff user who has logged in.
     */
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
            int choice = -1;
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
                try {
                    choice = sc.nextInt();
                    if(choice < 1 || choice > 5){
                        choice = -1;
                        System.out.println("Invalid choice. Press <enter> to try again.");
                        sc.nextLine();
                        sc.nextLine();
                    }
                } catch (InputMismatchException ime) {
                    System.out.println("Invalid input.");
                    System.out.println("Press <enter> to try again.");
                    sc.nextLine();
                    sc.nextLine();
                    continue;
                }
            } while (choice == -1);
            sc.nextLine();
            try {
                switch (choice) {
                    case 1:
                        displayPendingOrders(branchID);
                        break;
                    case 2:
                        viewOrderDetails(branchID);
                        break;
                    case 3:
                        processOrder(branchID);
                        break;
                    case 4:
                        changePassword(user);
                        break;
                    case 5:
                        System.out.println("Logging out...");
                        System.out.println("Logged out successfully.");
                        System.out.println("Press <enter> to continue.");
                        sc.nextLine();
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
    }

    /**
     * Displays pending orders for the staff user to view.
     * 
     * @param branchID The ID of the branch to which the staff user belongs.
     */
    private static void displayPendingOrders(int branchID) {
        ChangePage.changePage();
        Boolean flag = true;
        orderView = new OrderDetailsView();
        List<Order> orders = staffService.getOrders(branchID);
        if (orders != null && orders.size() > 0){
            for (Order order : orders) {
                if(order.getOrderStatus() == OrderStatus.PREPARING){
                    orderView.displayOrderDetails(order);
                    flag = false;
                }
            }
            if(flag == false){
                System.out.println("Press <enter> to continue.");
                sc.nextLine();
                return;
            }
        }
        if(orders.size() > 0 && flag == true){
            System.out.println("No pending orders.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
        else {
            System.out.println("No pending orders.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
    }  

    /**
     * Displays the details of a specific order.
     * 
     * @param branchID The ID of the branch to which the staff user belongs.
     */
    private static void viewOrderDetails(int branchID) {
        ChangePage.changePage();
        orderView = new OrderDetailsView();
        System.out.print("Enter order ID: ");
        int orderID;
        try {
            orderID = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            sc.nextLine();
            return;
        }
        Order order = staffService.getOrder(orderID);
        if (order == null) {
            System.out.println("Order not found.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
        else if (order.getBranchID() != branchID) {
            System.out.println("Order not found.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
        if(order.getOrderStatus() == OrderStatus.READY){
            LocalDateTime currDateTime = LocalDateTime.now();
            long seconds = Duration.between(order.getReadyTime(), currDateTime).getSeconds();
            if(seconds > 300){
                staffService.cancelOrder(orderID);
            }
        }
        orderView.displayOrderDetails(order);
        System.out.println("Press <enter> to continue.");
        sc.nextLine();
        return;
    }

    /**
     * Processes a pending order.
     * 
     * @param branchID The ID of the branch to which the staff user belongs.
     */
    private static void processOrder(int branchID) {
        ChangePage.changePage();
        System.out.print("Enter order ID: ");
        int orderID;
        try {
            orderID = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
        Order order = staffService.getOrder(orderID);
        if (order == null) {
            System.out.println("Order not found.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
        else if (order.getBranchID() != branchID) {
            System.out.println("Order not found.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
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

    /**
     * Changes the password of the staff user.
     * 
     * @param user The staff user whose password is to be changed.
     * @throws PageBackException if an error occurs and allows for return to previous page.
     */
    private static void changePassword(User user) throws PageBackException {
        ChangePage.changePage();
        System.out.println("Enter old password:");
        String oldPassword = sc.nextLine();
        System.out.println("Enter new password:");
        String newPassword = sc.nextLine();

        try {
            staffService.changePassword(user, oldPassword, newPassword);
            System.out.println("Password changed successfully. Press <enter> to log in again.");
            sc.nextLine();
        } catch (AccountNotFoundException | PasswordMismatchException | PasswordValidationException e) {
            System.out.println("Error changing password: " + e.getMessage());
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            throw new PageBackException();
        }
        return;
        
    }
}
