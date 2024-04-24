package interfaces;

import models.Order;

public interface ICustomerService {

    public void checkOrderStatus(int OrderID);

    public void newOrder(Order order, int BranchID);

    public void getBranchMenuItemList(int BranchID);

    public void collectOrder(int OrderID);
}
