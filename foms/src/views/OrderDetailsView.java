package views;

import java.time.format.DateTimeFormatter;

import interfaces.IOrderView;
import models.Order;
import models.OrderItem;

/**
 * The {@link OrderDetailsView} class implements the IOrderView interface and is responsible for displaying order details.
 */
public class OrderDetailsView implements IOrderView{

    /**
     * Displays the details of the given order.
     *
     * @param order an Order object
     */
    @Override
    public void displayOrderDetails(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Order ID: " + order.getOrderID());
        System.out.println("Order Status: " + order.getOrderStatus());
        System.out.println("Order Type: " + order.getOrderType());
        System.out.println("Order Time: " + order.getOrderTime().format(formatter));
        System.out.println("Order Items:");
        int counter = 1;
        for (OrderItem item : order.getOrderItems()) {
            System.out.println("Item " + counter + ":");
            System.out.println("\tItem Name: " + item.getItemName() + ", Category: " + item.getCategory() +
                    ", Quantity: " + item.getQuantity() + ", Price: $" + item.getPrice() + ", SubTotal: $" + String.format("%.2f", item.getPrice() * item.getQuantity()));
            if(item.getSpecialRequest() != "none"){
                System.out.println("\tSpecial Request: " + item.getSpecialRequest());
            }
            counter++;
            System.out.println();
        }
        System.out.println("Total Price: " + order.getTotalPrice());
        System.out.println("--------------------------------------------------");
        
    }
}
