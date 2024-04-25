package interfaces;

import java.time.LocalDateTime;
import java.util.List;

import models.BranchMenuItem;
import models.Order;
import models.PaymentMethod;

public interface ICustomerService {

    public Order getOrder(int OrderID);

    public void newOrder(Order order);

    public List<BranchMenuItem> getBranchMenuItemList(int BranchID);

    public Boolean collectOrder(int OrderID);

    public int getNextOrderID();

    public String getBranchName(int branchID);

    public void updateBranchMenuItem(BranchMenuItem item);

    public BranchMenuItem getBranchMenuItem(int branchID, String itemName);

    public List<PaymentMethod> getPaymentMethods(String type);

    public LocalDateTime getReadyTime(int OrderID);

    public void setOrderStatus(int OrderID);
}
