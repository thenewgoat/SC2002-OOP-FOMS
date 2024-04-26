package controllers;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import enums.OrderStatus;
import enums.OrderType;
import interfaces.IBranchMenuItemView;
import interfaces.ICustomerService;
import interfaces.IOrderView;
import models.BranchMenuItem;
import models.Cart;
import models.Order;
import models.OrderItem;
import models.PaymentMethod;
import services.CustomerService;
import utils.ChangePage;
import utils.exceptions.PageBackException;
import views.BranchMenuItemView;
import views.OrderDetailsView;
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
        System.out.println("\t3. Exit");
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to try again.");
            sc.nextLine();
            sc.nextLine();
            customerMainPage(branchID);
            return;
        }
        sc.nextLine();
        try {
            switch(choice){
                case 1:
                    ChangePage.changePage();
                    checkOrderStatus(branchID);
                    customerMainPage(branchID);
                    break;
                case 2:
                    customerOrderPage(branchID);
                    customerMainPage(branchID);
                    break;
                case 3:
                    System.out.println("Thank you for visiting " + customerService.getBranchName(branchID) + "!");
                    System.out.println("Press <enter> to return to the main page.");
                    sc.nextLine();
                    ChangePage.changePage();
                    return;
                default:
                    System.out.println("Invalid choice. Press <enter> to try again.");
                    sc.nextLine();
                    throw new PageBackException();
            }
        } catch (PageBackException e) {
            customerMainPage(branchID);
        }

    }

    private static void checkOrderStatus(int branchID){
        ChangePage.changePage();
        System.out.println("Please enter your Order ID: ");
        int orderID;
        try {
            orderID = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to continue.");
            sc.nextLine();
            sc.nextLine();
            return;
        }
        Order order = customerService.getOrder(orderID);
        if(order == null){
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
            if(seconds > 60){
                customerService.setOrderStatus(orderID);
            }
        }
        orderView = new OrderStatusView();
        orderView.displayOrderDetails(order);
        System.out.println("Press <enter> to continue.");
        sc.nextLine();
        if (order.getOrderStatus() == OrderStatus.READY) {
            System.out.println("Would you like to collect your order?");
            System.out.println("\t1. Yes");
            System.out.println("\t2. No");
            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input. Press <enter> to continue.");
                sc.nextLine();
                sc.nextLine();
                return;
            }
            switch(choice){
                case 1:
                    customerService.collectOrder(orderID);
                    System.out.println("Order collected successfully.");
                    System.out.println("Press <enter> to continue.");
                    sc.nextLine();
                    return;
                case 2:
                    System.out.println("Order not collected.");
                    System.out.println("Please be sure to collect your order before it is cancelled.");
                    System.out.println("Press <enter> to continue.");
                    sc.nextLine();
                    return;
                default:
                    System.out.println("Invalid choice. Press <enter> to continue.");
                    sc.nextLine();
                    return;
            }
        }
    }
    private static void customerOrderPage(int branchID){
        ChangePage.changePage();
        OrderType orderType = null;
        System.out.println("Welcome to " + customerService.getBranchName(branchID) + "branch Order Page!");
        System.out.println("Will you be dining in or taking out?");
        System.out.println("\t1. Dine In");
        System.out.println("\t2. Take Out");
        System.out.println("\t3. Exit");
        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to continue.");
            sc.nextLine();
            sc.nextLine();
            return;
        }
        try {
            switch(choice){
                case 1:
                    orderType = OrderType.DINE_IN;
                    break;
                case 2:
                    orderType = OrderType.TAKEAWAY;
                    break;
                case 3:
                    System.out.println("Thank you for using FOMS. Goodbye!");
                    System.out.println("Press <enter> to return to the main page.");
                    sc.nextLine();
                    ChangePage.changePage();
                    return;
                default:
                    System.out.println("Invalid choice. Press <enter> to try again.");
                    sc.nextLine();
                    throw new PageBackException();
            }
        } catch (PageBackException e) {
            return;
        }
        Cart cart = new Cart();
        manageCart(branchID, orderType, cart);
        return;
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
            int choice = -1;
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
                try {
                    choice = sc.nextInt();
                    if(choice < 1 || choice > 6){
                        choice = -1;
                        System.out.println("Invalid choice. Press <enter> to try again.");
                        sc.nextLine();
                        sc.nextLine();
                    }
                } catch (InputMismatchException ime) {
                    System.out.println("Invalid input. Press <enter> to try again.");
                    sc.nextLine();
                    sc.nextLine();
                }
            } while (choice == -1);
            sc.nextLine();
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
                    displayCart(cart);
                    break;
                case 5:
                    exit = checkoutCart(branchID, orderType, cart);
                    break;
                case 6:
                    cancelOrder(cart, branchID);
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Press <enter> to try again.");
                    sc.nextLine();
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
        int choice;
        try {
            choice = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to continue.");
            sc.nextLine();
            sc.nextLine();
            return;
        }
        try {
            if(choice < 1 || choice > branchMenuItems.size()){
                System.out.println("Invalid choice. Press <enter> to continue.");
                sc.nextLine();
                throw new PageBackException();
            }
        } catch (PageBackException e) {
            return;
        }
        choice--;
        BranchMenuItem item = branchMenuItems.get(choice);
        System.out.println("Please enter the quantity you would like to order: ");
        int quantity;
        try {
            quantity = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to continue.");
            sc.nextLine();
            sc.nextLine();
            return;
        }
        try {
            if(quantity > item.getAvailability()){
                System.out.println("Sorry, the quantity you have entered exceeds our stock.");
                System.out.println("Press <enter> to try again.");
                sc.nextLine();
                throw new PageBackException();
            }else if (quantity < 1){
                System.out.println("Invalid quantity. Please try again.");
                System.out.println("Press <enter> to try again.");
                sc.nextLine();
                throw new PageBackException();
            }
        } catch (PageBackException e) {
            return;
        }
        item.setAvailability(item.getAvailability()-quantity);
        customerService.updateBranchMenuItem(item);
        System.out.println("Do you have any Special Requests?");
        System.out.println("\t1. Yes");
        System.out.println("\t2. No");
        int specialRequestChoice;
        try {
            specialRequestChoice = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to continue.");
            sc.nextLine();
            sc.nextLine();
            return;
        }
        String specialRequest = "none";
        if(specialRequestChoice == 1){
            System.out.println("Please enter your special request: ");
            specialRequest = sc.nextLine();
        }
        OrderItem orderItem = new OrderItem(item.getName(), item.getCategory(), quantity, item.getPrice(), specialRequest);
        cart.addItem(orderItem);
        System.out.println("Item added to cart successfully.");
        System.out.println("Press <enter> to continue.");
        sc.nextLine();
    }
    private static void editCart(Cart cart, int branchID){
        ChangePage.changePage();
        if(cart.getOrderItems().isEmpty()){
            System.out.println("Your cart is empty.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
        cart.displayItems();
        System.out.println("Please enter the name of the item you would like to edit: ");
        String itemName = sc.nextLine();
        OrderItem item = cart.getItem(itemName);
        try {
            if(item == null){
                System.out.println("Item not found in cart.");
                System.out.println("Press <enter> to continue.");
                sc.nextLine();
                throw new PageBackException();
            }
        } catch (PageBackException e) {
            return;
        }
        BranchMenuItem branchMenuItem = customerService.getBranchMenuItem(branchID, itemName);
        int oldQuantity = item.getQuantity();
        System.out.println("Please enter the new quantity: ");
        int quantity;
        try {
            quantity = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to continue.");
            sc.nextLine();
            sc.nextLine();
            return;
        }
        try {
            if(quantity < 1){
                System.out.println("Invalid quantity.");
                System.out.println("Press <enter> to continue.");
                sc.nextLine();
                throw new PageBackException();
            }else if(quantity > branchMenuItem.getAvailability() + item.getQuantity()) {
                System.out.println("Sorry, the quantity you have entered exceeds the quantity in your cart.");
                System.out.println("Press <enter> to continue.");
                sc.nextLine();
                throw new PageBackException();
            }
        } catch (PageBackException e) {
            return;
        }
        cart.editItem(itemName, quantity);
        branchMenuItem.setAvailability(branchMenuItem.getAvailability() + oldQuantity - quantity);
        customerService.updateBranchMenuItem(branchMenuItem);
        System.out.println("Item quantity updated successfully.");
        System.out.println("Press <enter> to continue.");
        sc.nextLine();
    }
    private static void removeItemFromCart(Cart cart, int branchID){
        ChangePage.changePage();
        if(cart.getOrderItems().isEmpty()){
            System.out.println("Your cart is empty.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
        cart.displayItems();
        System.out.println("Please enter the name of the item you would like to remove: ");
        String itemName = sc.nextLine();
        OrderItem item = cart.getItem(itemName);
        try {
            if(item == null){
                System.out.println("Item not found in cart.");
                System.out.println("Press <enter> to continue.");
                sc.nextLine();
                throw new PageBackException();
            }
        } catch (PageBackException e) {
            return;
        }
        BranchMenuItem branchMenuItem = customerService.getBranchMenuItem(branchID, itemName);
        cart.removeItem(itemName);
        branchMenuItem.setAvailability(branchMenuItem.getAvailability() + item.getQuantity());
        customerService.updateBranchMenuItem(branchMenuItem);
        System.out.println("Item removed successfully.");
        System.out.println("Press <enter> to continue.");
        sc.nextLine();
    }

    private static Boolean checkoutCart(int branchID, OrderType orderType, Cart cart){
        orderView = new OrderDetailsView();
        ChangePage.changePage();
        if(cart.getOrderItems().isEmpty()){
            System.out.println("Your cart is empty. Please add items to your cart before checking out.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return false;
        }
        cart.calculateTotalPrice();
        System.out.println("Please confirm your order: ");
        cart.displayItems();
        System.out.println("Press <enter> to continue.");
        sc.nextLine();
        ChangePage.changePage();
        System.out.println("Please select your payment type: ");
        System.out.println("\t1. Credit/Debit Card");
        System.out.println("\t2. Online Payment");
        int choice;
        try {
            choice = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to continue.");
            sc.nextLine();
            sc.nextLine();
            return false;
        }
        switch (choice) {
            case 1:
                PaymentMethod cardPaymentMethod = cardPaymentProcess();
                if(cardPaymentMethod == null){
                    System.out.println("Payment failed. Please try again.");
                    System.out.println("Press <enter> to continue.");
                    sc.nextLine();
                    return false;
                }else{
                    cardPaymentMethod.pay(cart.getTotalPrice());
                    System.out.println("Press <enter> to continue.");
                    sc.nextLine();
                    Order order = new Order(customerService.getNextOrderID(), branchID, cart.getOrderItems(), orderType, cart.getTotalPrice());
                    customerService.newOrder(order);
                    ChangePage.changePage();
                    System.out.println("Order placed successfully.");
                    orderView.displayOrderDetails(order);
                    System.out.println("Press <enter> to continue.");
                    sc.nextLine();
                    return true;
                }
            case 2:
                PaymentMethod onlinePaymentMethod = onlinePaymentProcess();
                if(onlinePaymentMethod == null){
                    System.out.println("Payment failed. Please try again.");
                    System.out.println("Press <enter> to continue.");
                    sc.nextLine();
                    return false;
                }else{
                    onlinePaymentMethod.pay(cart.getTotalPrice());
                    System.out.println("Press <enter> to continue.");
                    sc.nextLine();
                    Order order = new Order(customerService.getNextOrderID(), branchID, cart.getOrderItems(), orderType, cart.getTotalPrice());
                    customerService.newOrder(order);
                    ChangePage.changePage();
                    System.out.println("Order placed successfully.");
                    orderView.displayOrderDetails(order);
                    System.out.println("Press <enter> to continue.");
                    sc.nextLine();
                    return true;
                }
            default:
                System.out.println("Invalid choice. Press <enter> to continue.");
                sc.nextLine();
                return false;
        }
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
    private static void displayCart(Cart cart){
        ChangePage.changePage();
        if(cart.getOrderItems().isEmpty()){
            System.out.println("Your cart is empty.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return;
        }
        cart.displayItems();
        System.out.println("Press <enter> to continue.");
        sc.nextLine();
    }
    private static PaymentMethod cardPaymentProcess(){
        List<PaymentMethod> paymentMethods = customerService.getPaymentMethods("Credit/Debit Card");
        System.out.println("Please select your card: ");
        for(int i = 0; i < paymentMethods.size(); i++){
            System.out.println("\t" + (i+1) + ". " + paymentMethods.get(i).getPaymentMethod());
        }
        int choice;
        try {
            choice = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to continue.");
            sc.nextLine();
            sc.nextLine();
            return null;
        }
        try {
            if(choice < 1 || choice > paymentMethods.size()){
                System.out.println("Invalid choice. Press <enter> to continue.");
                sc.nextLine();
                throw new PageBackException();
            }
        } catch (PageBackException e) {
            return null;
        }
        PaymentMethod paymentMethod = paymentMethods.get(choice-1);
        System.out.println("Please enter the name on your card: ");
        String cardName = sc.nextLine();
        if(cardName.length() < 1){
            System.out.println("Invalid card name. Please try again.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return null;
        }
        System.out.println("Please enter your 16-digit card number: ");
        String cardNumber = sc.nextLine();
        if(cardNumber.length() != 16){
            System.out.println("Invalid card number. Please try again.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return null;
        }
        System.out.println("Please enter your card expiry date (MM/YY): ");
        String expiryDate = sc.nextLine();
        if(expiryDate.length() != 5){
            System.out.println("Invalid expiry date. Please try again.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return null;
        }
        System.out.println("Please enter your 3-digit card CVV: ");
        String cvv = sc.nextLine();
        if(cvv.length() != 3){
            System.out.println("Invalid CVV. Please try again.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return null;
        }
        return paymentMethod;
    }
    private static PaymentMethod onlinePaymentProcess(){
        List<PaymentMethod> paymentMethods = customerService.getPaymentMethods("Online Payment");
        System.out.println("Please select your payment method:");
        for(int i = 0; i < paymentMethods.size(); i++){
            System.out.println("\t" + (i+1) + ". " + paymentMethods.get(i).getPaymentMethod());
        }
        int choice;
        try {
            choice = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to continue.");
            sc.nextLine();
            sc.nextLine();
            return null;
        }
        try {
            if(choice < 1 || choice > paymentMethods.size()){
                System.out.println("Invalid choice. Press <enter> to continue.");
                sc.nextLine();
                throw new PageBackException();
            }
        } catch (PageBackException e) {
            return null;
        }
        PaymentMethod paymentMethod = paymentMethods.get(choice-1);
        System.out.println("Please enter your email address: ");
        String email = sc.nextLine();
        if(email.length() < 1){
            System.out.println("Invalid email. Please try again.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return null;
        }
        System.out.println("Please enter your password: ");
        String password = sc.nextLine();
        if(password.length() < 1){
            System.out.println("Invalid password. Please try again.");
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            return null;
        }
        return paymentMethod;
    }
}