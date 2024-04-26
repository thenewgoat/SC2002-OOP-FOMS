package controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import enums.Role;
import enums.OrderStatus;
import interfaces.IBranchMenuItemView;
import interfaces.IBranchUserView;
import interfaces.IManagerService;
import interfaces.IOrderView;
import models.BranchMenuItem;
import models.BranchUser;
import models.Order;
import models.User;
import services.ManagerService;
import utils.ChangePage;
import utils.exceptions.AccountNotFoundException;
import utils.exceptions.PageBackException;
import utils.exceptions.PasswordMismatchException;
import utils.exceptions.PasswordValidationException;
import views.BranchMenuItemView;
import views.BranchUserView;
import views.OrderDetailsView;


/**
 * Controller class for managing the user interface for branch managers.
 * This includes handling of orders, staff management, and menu management.
 */
public class ManagerController {

    private static final Scanner sc = new Scanner(System.in);

    protected static final IManagerService managerService = new ManagerService();

    protected static IOrderView orderView;

    protected static IBranchUserView branchUserView;

    protected static IBranchMenuItemView branchMenuItemView;

    /**
     * Starts the manager interface for the given user.
     * 
     * @param user The user who logs in and is verified as a branch manager.
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
            System.out.println("\t4. Display Staff List in Branch");
            System.out.println("\t5. Manage Menu");
            System.out.println("\t6. Change password");
            System.out.println("\t7. Log out");
            System.out.println();
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input. Press <enter> to continue");
                sc.nextLine();
                sc.nextLine();
                start(user);
                return;
            }
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
                        displayStaffList(branchID);
                        break;
                    case 5:
                        manageMenu(branchID);
                        break;
                    case 6:
                        changePassword(user);
                        break;
                    case 7:
                        System.out.println("Logging out...");
                        System.out.println("Logged out successfully.");
                        System.out.println("Press <enter> to continue.");
                        sc.nextLine();
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (PageBackException e) {
                start(user);
            }
        } while (exit == false);
    }


    /**
     * Displays a list of pending orders for the given branch.
     * 
     * @param branchID The ID of the branch whose pending orders are to be displayed.
     */
    private static void displayPendingOrders(int branchID) {
        ChangePage.changePage();
        Boolean flag = true;
        orderView = new OrderDetailsView();
        List<Order> orders = managerService.getOrders(branchID);
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
     * @param branchID The ID of the branch where the order was placed.
     */
    private static void viewOrderDetails(int branchID) {
        ChangePage.changePage();
        orderView = new OrderDetailsView();
        System.out.print("Enter order ID: ");
        int orderID;
        try {
            orderID = sc.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to return to previous page.");
            sc.nextLine();
            sc.nextLine();
            return;
        }
        sc.nextLine();
        Order order = managerService.getOrder(orderID);
        if (order == null) {
            System.out.println("Order not found.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
        else if(order.getBranchID() != branchID){
            System.out.println("Order not found.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
        if(order.getOrderStatus() == OrderStatus.READY){
            LocalDateTime currDateTime = LocalDateTime.now();
            long seconds = Duration.between(order.getReadyTime(), currDateTime).getSeconds();
            if(seconds > 300){
                managerService.cancelOrder(orderID);
            }
        }
        orderView.displayOrderDetails(order);
        System.out.println("Press <enter> to continue.");
        sc.nextLine();
        return;
    }


    /**
     * Processes the specified order by updating its status.
     * 
     * @param branchID The ID of the branch where the order was placed.
     */
    private static void processOrder(int branchID) {
        ChangePage.changePage();
        System.out.print("Enter order ID: ");
        int orderID;
        try {
            orderID = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to return to previous page.");
            sc.nextLine();
            sc.nextLine();
            return;
        }
        Order order = managerService.getOrder(orderID);
        if (order == null) {
            System.out.println("Order not found.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
        else if(order.getBranchID() != branchID){
            System.out.println("Order not found.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
        Boolean success = managerService.updateOrderStatus(orderID);
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
     * Handles the change of password for a user.
     * 
     * @param user The user whose password is to be changed.
     * @throws PageBackException if there is an error and allows returning to the previous page.
     */
    private static void changePassword(User user) throws PageBackException {
        ChangePage.changePage();
        System.out.println("Enter old password:");
        String oldPassword = sc.nextLine();
        System.out.println("Enter new password:");
        String newPassword = sc.nextLine();

        try {
            managerService.changePassword(user, oldPassword, newPassword);
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

    /**
     * Displays a list of staff members working in a specific branch.
     * 
     * @param branchID The ID of the branch whose staff list is to be displayed.
     */
    private static void displayStaffList(int branchID) {
        ChangePage.changePage();
        List<BranchUser> users = managerService.getStaffList(branchID);
        String branchName = managerService.getBranchName(branchID);
        branchUserView = new BranchUserView();
        if (users != null && users.size() > 0){
            System.out.println(" =============== Staff ===============");
            for (BranchUser user : users) {
                if(user.getRole().equals(Role.STAFF)){
                    branchUserView.displayBranchUserDetails(user, branchName);
                }
            }
            System.out.println(" ============== Managers ==============");
            for (BranchUser user : users) {
                if(user.getRole().equals(Role.BRANCHMANAGER)){
                    branchUserView.displayBranchUserDetails(user, branchName);
                }
            }
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        } else {
            System.out.println("No staff in branch.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
    }

    /**
     * Manages the menu items of a branch.
     * 
     * @param branchID The ID of the branch whose menu is managed.
     */
    private static void manageMenu(int branchID) {
        Boolean exit = false;
        do {
            ChangePage.changePage();
            System.out.println("What do you want to do?");

            System.out.println("\t1. Add item to menu");
            System.out.println("\t2. Remove item from menu");
            System.out.println("\t3. Edit item price");
            System.out.println("\t4. Edit item availability");
            System.out.println("\t5. Edit item description");
            System.out.println("\t6. View Menu");
            System.out.println("\t7. Back");

            System.out.print("Please enter your choice: ");
            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input. Press <enter> to return to previous page.");
                sc.nextLine();
                sc.nextLine();
                return;
            }
            sc.nextLine();
            switch (choice) {
                case 1:
                    addItemToMenu(branchID);
                    break;
                case 2:
                    removeItemFromMenu(branchID);
                    break;
                case 3:
                    editItemPrice(branchID);
                    break;
                case 4:
                    editItemAvailability(branchID);
                    break;
                case 5:
                    editItemDescription(branchID);
                    break;
                case 6:
                    viewMenu(branchID);
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println("Press <enter> to continue.");
                    sc.nextLine();
                    break;
            }
        } while (exit == false);
        return;
    }

    /**
     * Adds a new item to the menu of a branch.
     * 
     * @param branchID The ID of the branch where the item is to be added.
     */
    private static void addItemToMenu(int branchID) {
        ChangePage.changePage();
        List<BranchMenuItem> items = managerService.getBranchMenuItemList(branchID);
        System.out.print("Enter item name: ");
        String name = sc.nextLine();
        for (BranchMenuItem item : items) {
            if (item.getName().equals(name)) {
                System.out.println("Item already exists.");
                System.out.println("Press <enter> to continue.");
                sc.nextLine();
                return;
            }
        }
        String category;
        System.out.println("Select item category:");
        int counter = managerService.displayMenuCategories();
        System.out.println(counter + ". Add new category");
        System.out.print("Enter your choice: ");
        int categoryChoice;
        try {
            categoryChoice = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Press <enter> to continue.");
            sc.nextLine();
            sc.nextLine();
            return;
        }
        if(categoryChoice < 1 || categoryChoice > counter){
            System.out.println("Invalid choice. Press <enter> to continue.");
            sc.nextLine();
            return;
        } else if (categoryChoice == counter) {
            System.out.print("Enter new category: ");
            category = sc.nextLine();
            managerService.addCategory(category);
        } else {
            category = managerService.getCategories().get(categoryChoice - 1);
        }
        System.out.println("Enter item price:");
        double itemPrice;
        try {
            itemPrice = sc.nextDouble();
            sc.nextLine();
            if (itemPrice < 0.0) {
                throw new IllegalArgumentException();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid price. Press <enter> to continue.");
            sc.nextLine();
            sc.nextLine();
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid price. Press <enter> to continue.");
            sc.nextLine();
            sc.nextLine();
            return;
        }
        System.out.println("Enter item availability:");
        int itemAvailability;
        try {
            itemAvailability = sc.nextInt();
            sc.nextLine();
            if (itemAvailability < 0.0) {
                throw new IllegalArgumentException();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input. Press <enter> to continue.");
            sc.nextLine();
            sc.nextLine();
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Input. Press <enter> to continue.");
            sc.nextLine();
            sc.nextLine();
            return;
        }
        System.out.println("Enter item description:");
        String description = sc.nextLine();

        int itemID = managerService.getNextItemID();

        BranchMenuItem item = new BranchMenuItem(name, itemID, category, itemPrice, itemAvailability, description, branchID);
        managerService.addBranchMenuItem(item);
    }

    /**
     * Views the entire menu of a branch.
     * 
     * @param branchID The ID of the branch whose menu is to be viewed.
     */
    private static void viewMenu(int branchID){
        ChangePage.changePage();
        List<BranchMenuItem> items = managerService.getBranchMenuItemList(branchID);
        branchMenuItemView = new BranchMenuItemView();
        int counter = 1; 
        for (BranchMenuItem item : items) {
            branchMenuItemView.displayBranchMenuItem(item, counter);
            counter++;
        }
        System.out.println("Press <enter> to continue.");
        sc.nextLine();
        return;
    }

    /**
     * Removes an item from the menu of a branch.
     * 
     * @param branchID The ID of the branch from which the item is to be removed.
     */
    private static void removeItemFromMenu(int branchID) {
        ChangePage.changePage();
        List<BranchMenuItem> items = managerService.getBranchMenuItemList(branchID);
        branchMenuItemView = new BranchMenuItemView();
        int counter = 1; 
        for (BranchMenuItem item : items) {
            branchMenuItemView.displayBranchMenuItem(item, counter);
            counter++;
        }
        System.out.print("Enter the name of item to be removed: ");
        String name = sc.nextLine();
        for (BranchMenuItem item : items) {
            if (item.getName().equals(name)) {
                managerService.removeBranchMenuItem(item);
                System.out.println("Item removed successfully.");
                System.out.println("Press <enter> to continue.");
                sc.nextLine();
                return;
            }
        }
        System.out.println("Item not found.");
        System.out.println("Press <enter> to continue.");
        sc.nextLine();
        return;
    }

    /**
     * Edits the price of a menu item in a branch.
     * 
     * @param branchID The ID of the branch where the menu item price is to be edited.
     */
    private static void editItemPrice(int branchID) {
        ChangePage.changePage();
        List<BranchMenuItem> items = managerService.getBranchMenuItemList(branchID);
        branchMenuItemView = new BranchMenuItemView();
        int counter = 1; 
        for (BranchMenuItem item : items) {
            branchMenuItemView.displayBranchMenuItem(item, counter);
            counter++;
        }
        System.out.print("Enter the name of item to edit price: ");
        String name = sc.nextLine();
        for (BranchMenuItem item : items) {
            if (item.getName().equals(name)) {
                System.out.print("Enter new price: ");
                double price;
                try {
                    price = sc.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid price.");
                    System.out.println("Press <enter> to continue.");
                    sc.nextLine();
                    sc.nextLine();
                    return;
                }
                sc.nextLine();
                if(price < 0.0){
                    System.out.println("Invalid price.");
                    System.out.println("Press <enter> to continue.");
                    sc.nextLine();
                    return;
                }
                item.setPrice(price);
                managerService.editBranchMenuItem(item);
                System.out.println("Price updated successfully.");
                System.out.println("Press <enter> to continue.");
                sc.nextLine();
                return;
            }
        }
        System.out.println("Item not found.");
        System.out.println("Press <enter> to continue.");
        sc.nextLine();
        return;
    }

    /**
     * Edits the availability of a specific menu item in the branch. This method prompts the manager to
     * specify which item's availability needs to be updated, and then allows them to enter the new availability value.
     * It checks for valid numeric input and ensures that the availability is not negative.
     *
     * @param branchID The ID of the branch where the menu item's availability is to be edited.
     */
    private static void editItemAvailability(int branchID) {
        ChangePage.changePage();
        List<BranchMenuItem> items = managerService.getBranchMenuItemList(branchID);
        branchMenuItemView = new BranchMenuItemView();
        int counter = 1; 
        for (BranchMenuItem item : items) {
            branchMenuItemView.displayBranchMenuItem(item, counter);
            counter++;
        }
        System.out.print("Enter the name of item to edit availability: ");
        String name = sc.nextLine();
        for (BranchMenuItem item : items) {
            if (item.getName().equals(name)) {
                System.out.print("Enter new availability: ");
                int availability;
                try {
                    availability = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid availability.");
                    System.out.println("Press <enter> to continue.");
                    sc.nextLine();
                    sc.nextLine();
                    return;
                }
                sc.nextLine();
                if(availability < 0){
                    System.out.println("Invalid availability.");
                    System.out.println("Press <enter> to continue.");
                    sc.nextLine();
                    return;
                }
                item.setAvailability(availability);
                managerService.editBranchMenuItem(item);
                System.out.println("Availability updated successfully.");
                System.out.println("Press <enter> to continue.");
                sc.nextLine();
                return;
            }
        }
        System.out.println("Item not found.");
        System.out.println("Press <enter> to continue.");
        sc.nextLine();
        return;
    }

    /**
     * Edits the description of a specific menu item in the branch. This method prompts the manager to
     * specify which item's description needs to be updated, and then allows them to enter the new description value.
     *
     * @param branchID The ID of the branch where the menu item's description is to be edited.
     */
    private static void editItemDescription(int branchID) {
        ChangePage.changePage();
        List<BranchMenuItem> items = managerService.getBranchMenuItemList(branchID);
        branchMenuItemView = new BranchMenuItemView();
        int counter = 1; 
        for (BranchMenuItem item : items) {
            branchMenuItemView.displayBranchMenuItem(item, counter);
            counter++;
        }
        System.out.print("Enter the name of item to edit description: ");
        String name = sc.nextLine();
        for (BranchMenuItem item : items) {
            if (item.getName().equals(name)) {
                System.out.print("Enter new description: ");
                String description = sc.nextLine();
                item.setDescription(description);
                managerService.editBranchMenuItem(item);
                System.out.println("Description updated successfully.");
                System.out.println("Press <enter> to continue.");
                sc.nextLine();
                return;
            }
        }
        System.out.println("Item not found.");
        System.out.println("Press <enter> to continue.");
        sc.nextLine();
        return;
    }
}
