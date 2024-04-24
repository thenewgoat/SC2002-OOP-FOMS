package services;

import java.util.ArrayList;
import java.util.List;

import enums.OrderStatus;
import interfaces.IStaffService;
import models.Order;
import stores.OrderStorage;

public class StaffService implements IStaffService{

    @Override
    public List<Order> getOrders(int branchID) {
        Order[] orders = OrderStorage.getAll();
        List<Order> branchOrders = new ArrayList<>();
        if (orders != null) {
            for (Order order : orders) {
                if (order.getBranchID() == branchID) {
                    branchOrders.add(order);
                }
            }
            return branchOrders;
        }
        return null;
    }

    @Override
    public Order getOrder(int orderID) {
        Order order = OrderStorage.get(orderID);
        return order;
    }

    @Override
    public Boolean updateOrderStatus(int orderID) {
        Order order = getOrder(orderID);
        if (order != null) {
            if(order.getOrderStatus() == OrderStatus.PREPARING){
                order.setOrderStatus(OrderStatus.READY);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
