package interfaces;

import java.util.List;

import models.Order;

public interface IBranchUserService {

    public List<Order> getOrders(int branchID);

    public Order getOrder(int orderID);

    public Boolean updateOrderStatus(int orderID);

    public 
}
