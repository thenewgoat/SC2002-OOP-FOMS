package interfaces;

import java.util.List;

import models.Order;

public interface IStaffService {

    public List<Order> getOrders(int branchID);

    public Order getOrder(int orderID);

    public Boolean updateOrderStatus(int orderID);
}
