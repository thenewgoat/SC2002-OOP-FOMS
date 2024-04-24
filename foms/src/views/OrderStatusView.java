package views;

import enums.OrderStatus;
import interfaces.IOrderView;
import models.Order;

public class OrderStatusView implements IOrderView{

    public OrderStatusView() {
    }

    @Override
    public void displayOrderDetails(Order order) {
        if (order != null){
            OrderStatus status = order.getOrderStatus();
            switch(status){
                case PREPARING:
                    System.out.println("Order is still being prepared.");
                    break;
                case READY:
                    System.out.println("Order is ready for collection.");
                    break;
                case COMPLETED:
                    System.out.println("Order has already been collected.");
                    break;
                case CANCELLED:
                    System.out.println("Order has been cancelled as it was not collected in time.");
                    System.out.println("Please contact the branch for further assistance.");
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
