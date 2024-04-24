package services;

import java.util.Scanner;

import enums.OrderStatus;
import interfaces.ICustomerService;
import models.Cart;
import models.Order;
import models.OrderItem;
import stores.OrderStorage;
import utils.ChangePage;

public class CustomerService implements ICustomerService{

    @Override
    public void checkOrderStatus(int OrderID) {
        Order order = OrderStorage.get(OrderID);
        if (order != null) {
            System.out.println("Order ID: " + order.getOrderID());
            System.out.println("Order Status: " + order.getOrderStatus());
        } else {
            System.out.println("Order not found.");
        }
    }

    @Override
    public void newOrder(Order order, int BranchID) {
        // TODO Auto-generated method stub
    }

    @Override
    public void getBranchMenuItemList(int BranchID) {
        // TODO Auto-generated method stub
    }

    @Override
    public void collectOrder(int OrderID) {
        Order order = OrderStorage.get(OrderID);
        if (order != null){
            OrderStatus status = order.getOrderStatus();
            switch(status){
                case PREPARING:
                    System.out.println("Order is still being prepared.");
                    break;
                case READY:
                    order.completeOrder();
                    System.out.println("Order collected successfully.");
                    break;
                case COMPLETED:
                    System.out.println("Order has already been collected.");
                    break;
                case CANCELLED:
                    System.out.println("Order has been cancelled as it was not collected in time.");
                    break;
                default:
                    System.out.println("Invalid order status.");
                    break;
            }
        }
        else{
            System.out.println("Order not found.");
            // add page back exception
        }
    }
}
