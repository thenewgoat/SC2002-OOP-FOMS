package interfaces;

import java.util.List;

import models.BranchMenuItem;
import models.Order;

public interface ICustomerService {

    public Order getOrder(int OrderID);

    public void newOrder(Order order);

    public List<BranchMenuItem> getBranchMenuItemList(int BranchID);

    public Boolean collectOrder(int OrderID);
}
