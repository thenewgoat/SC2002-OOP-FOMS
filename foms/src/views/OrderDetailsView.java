package views;

import java.time.format.DateTimeFormatter;

import interfaces.IOrderView;
import models.Order;
import models.OrderItem;

public class OrderDetailsView implements IOrderView{

    @Override
    public void displayOrderDetails(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Order ID: " + order.getOrderID());
        System.out.println("Order Status: " + order.getOrderStatus());
        System.out.println("Order Type: " + order.getOrderType());
        System.out.println("Order Time: " + order.getOrderTime().format(formatter));
        System.out.println("Order Items:");
        for (OrderItem item : order.getOrderItems()) {
            System.out.println("\tItem Name: " + item.getItemName() + ", Category: " + item.getCategory() +
                    ", Quantity: " + item.getQuantity() + ", Price: $" + item.getPrice() + ", SubTotal: $" + item.getPrice() * item.getQuantity());
            if(item.getSpecialRequest() != "none"){
                System.out.println("Special Request: " + item.getSpecialRequest());
            }
        }
        System.out.println("Total Price: " + order.getTotalPrice());
        System.out.println("--------------------------------------------------");
        
    }
}
