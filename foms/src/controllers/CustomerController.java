package controllers;
import java.util.List;
import java.util.Scanner;

import enums.OrderType;
import interfaces.IBranchMenuItemView;
import interfaces.ICustomerService;
import interfaces.IOrderView;
import models.BranchMenuItem;
import models.Cart;
import models.Order;
import models.OrderItem;
import services.CustomerService;
import utils.ChangePage;
import utils.TimeDelay;
import utils.exceptions.PageBackException;
import views.BranchMenuItemView;
import views.OrderStatusView;

public class CustomerController {

    private static final Scanner sc = new Scanner(System.in);

    protected static final ICustomerService customerService = new CustomerService();

    protected static IBranchMenuItemView branchMenuItemView;

    protected static IOrderView orderView;

    public CustomerController() {
    }

    public static void customerMainPage(int branchID) {
        ChangePage.changePage();
        int choice;

        System.out.println("Welcome to " + customerService.getBranchName(branchID) + "!");
        System.out.println("Please select your choice: ");
        System.out.println("\t1. Check Order Status");
        System.out.println("\t2. Make a New Order");
        System.out.println("\t3. Change Branch");
        System.out.println("\t4. Exit");
        choice = sc.nextInt();
        sc.nextLine();
        try {
            switch(choice){
                case 1:
                    ChangePage.changePage();
                    checkOrderStatus();
                    break;
                case 2:
                    customerOrderPage(branchID);
                    break;
                case 3:
                    // page back exception
                    break;
                case 4:
                    System.out.println("Thank you for visiting " + customerService.getBranchName(branchID) + "!");
                    TimeDelay.delay(3000);
                    ChangePage.changePage();
                    Welcome.welcome();
                    // send back to starting page
                    break;
                default:
                    System.out.println("Invalid choice. Press <enter> to try again.");
                    sc.nextLine();
                    throw new PageBackException();
            }
        } catch (PageBackException e) {
            // System.out.println("Invalid choice. Press <enter> to go back.");
            // sc.nextLine();
            customerMainPage(branchID);
        }
        
    }

    private static void checkOrderStatus(){
        System.out.println("Please enter your Order ID: ");
        int orderID = sc.nextInt();
        Order order = customerService.getOrder(orderID);
        orderView = new OrderStatusView();
        orderView.displayOrderDetails(order);
    }

    private static void customerOrderPage(int branchID){
        ChangePage.changePage();
        OrderType orderType = null;
        System.out.println("Welcome to " + customerService.getBranchName(branchID) + "branch Order Page!");
        System.out.println("Will you be dining in or taking out?");
        System.out.println("\t1. Dine In");
        System.out.println("\t2. Take Out");
        System.out.println("\t3. Exit");
        int choice = sc.nextInt();
        switch(choice){
            case 1:
                orderType = OrderType.DINE_IN;
                break;
            case 2:
                orderType = OrderType.TAKEAWAY;
                break;
            case 3:
                System.out.println("Thank you for using FOMS. Goodbye!");
                TimeDelay.delay(3000);
                ChangePage.changePage();
                //Welcome.welcome();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
        Cart cart = new Cart();
        manageCart(branchID, orderType, cart);
    }

    private static void manageCart(int branchID, OrderType orderType, Cart cart){
        Boolean exit = false;
        do {
            ChangePage.changePage();
            System.out.println("What would you like to do?");
            System.out.println("\t1. Add Item to Cart");
            System.out.println("\t2. Edit Cart");
            System.out.println("\t3. Remove Item from Cart");
            System.out.println("\t4. Display Cart");
            System.out.println("\t5. Checkout Cart");
            System.out.println("\t6. Cancel Order");
            System.out.print("Please enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addItemToCart(cart, branchID);
                    break;
                case 2:
                    editCart(cart, branchID);
                    break;
                case 3:
                    removeItemFromCart(cart, branchID);
                    break;
                case 4:
                    cart.displayItems();
                    break;
                case 5:
                    checkoutCart(branchID, orderType, cart);
                    exit = true;
                    break;
                case 6:
                    cancelOrder(cart, branchID);
                    exit = true;
                    break;
                default:
                    break;
            }
        } while (exit == false);
        
    }

    private static void addItemToCart(Cart cart, int branchID){
        ChangePage.changePage();
        List<BranchMenuItem> branchMenuItems = customerService.getBranchMenuItemList(branchID);
        int index = 1;
        branchMenuItemView = new BranchMenuItemView();
        for (BranchMenuItem item : branchMenuItems) {
            branchMenuItemView.displayBranchMenuItem(item, index);
            index++;
        }
        System.out.println("Please enter the item number you would like to add to your cart: ");
        int choice = sc.nextInt();
        if(choice < 1 || choice > branchMenuItems.size()){
            System.out.println("Invalid choice. Please try again.");
            // page back exception
        }
        choice--;
        BranchMenuItem item = branchMenuItems.get(choice);
        System.out.println("Please enter the quantity you would like to order: ");
        int quantity = sc.nextInt();
        if(quantity > item.getAvailability()){
            System.out.println("Sorry, the quantity you have entered exceeds our stock.");
            // page back exception
        }else if (quantity < 1){
            System.out.println("Invalid quantity. Please try again.");
            // page back exception
        }
        item.setAvailability(item.getAvailability()-quantity);
        customerService.updateBranchMenuItem(item);
        OrderItem orderItem = new OrderItem(item.getName(), item.getCategory(), quantity, item.getPrice());
        cart.addItem(orderItem);
    }

    private static void editCart(Cart cart, int branchID){
        ChangePage.changePage();
        cart.displayItems();
        System.out.println("Please enter the name of the item you would like to edit: ");
        String itemName = sc.next();
        OrderItem item = cart.getItem(itemName);
        if(item == null){
            System.out.println("Item not found in cart.");
            // page back exception
        }
        BranchMenuItem branchMenuItem = customerService.getBranchMenuItem(branchID, itemName);
        int oldQuantity = item.getQuantity();
        System.out.println("Please enter the new quantity: ");
        int quantity = sc.nextInt();
        if(quantity < 1){
            System.out.println("Invalid quantity. Please try again.");
            // page back exception
        }else if(quantity > branchMenuItem.getAvailability() + item.getQuantity()) {
            System.out.println("Sorry, the quantity you have entered exceeds the quantity in your cart.");
        }
        cart.editItem(itemName, quantity);
        branchMenuItem.setAvailability(branchMenuItem.getAvailability() + oldQuantity - quantity);
        customerService.updateBranchMenuItem(branchMenuItem);
        System.out.println("Item quantity updated successfully.");
        TimeDelay.delay(2000);
    }

    private static void removeItemFromCart(Cart cart, int branchID){
        ChangePage.changePage();
        cart.displayItems();
        System.out.println("Please enter the name of the item you would like to remove: ");
        String itemName = sc.next();
        OrderItem item = cart.getItem(itemName);
        if(item == null){
            System.out.println("Item not found in cart.");
            // page back exception
        }
        BranchMenuItem branchMenuItem = customerService.getBranchMenuItem(branchID, itemName);
        cart.removeItem(itemName);
        branchMenuItem.setAvailability(branchMenuItem.getAvailability() + item.getQuantity());
        customerService.updateBranchMenuItem(branchMenuItem);
        System.out.println("Item removed successfully.");
        TimeDelay.delay(2000);
    }

    private static void checkoutCart(int branchID, OrderType orderType, Cart cart){
        ChangePage.changePage();
        if(cart.getOrderItems().isEmpty()){
            System.out.println("Your cart is empty. Please add items to your cart before checking out.");
            // page back exception
        }
        // do once payment is implemented
    }

    private static void cancelOrder(Cart cart, int branchID){
        ChangePage.changePage();
        for (OrderItem item : cart.getOrderItems()) {
            BranchMenuItem branchMenuItem = customerService.getBranchMenuItem(branchID, item.getItemName());
            branchMenuItem.setAvailability(branchMenuItem.getAvailability() + item.getQuantity());
            customerService.updateBranchMenuItem(branchMenuItem);
        }
        cart.getOrderItems().clear();
        System.out.println("Order cancelled successfully.");
    }
}