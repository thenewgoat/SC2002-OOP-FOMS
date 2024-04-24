package services;

import java.util.ArrayList;
import java.util.List;

import enums.OrderStatus;
import interfaces.ICustomerService;
import models.BranchMenuItem;
import models.Order;
import stores.BranchMenuItemStorage;
import stores.BranchStorage;
import stores.OrderStorage;

public class CustomerService implements ICustomerService{
    

    @Override
    public Order getOrder(int OrderID) {
        Order order = OrderStorage.get(OrderID);
        return order;
    }

    @Override
    public void newOrder(Order order) {
        OrderStorage.add(order);
    }

    @Override
    public List<BranchMenuItem> getBranchMenuItemList(int BranchID) {
        BranchMenuItem[] items = BranchMenuItemStorage.getAll();
        List<BranchMenuItem> branchItems = new ArrayList<>(); 
        if (items != null) {
            for (BranchMenuItem item : items) {
                if(item.getBranchID() == BranchID){
                    branchItems.add(item);
                }
            }
            return branchItems;
        } else {
            return null;
        }
    }

    @Override
    public Boolean collectOrder(int OrderID) {
        Order order = OrderStorage.get(OrderID);
        if (order != null) {
            OrderStatus status = order.getOrderStatus();
            if (status == OrderStatus.READY) {
                order.completeOrder();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int getNextOrderID() {
        return OrderStorage.getAll().length + 1;
    }

    @Override
    public String getBranchName(int branchID) {
        return BranchStorage.get(branchID).getName();
    }

    @Override
    public void updateBranchMenuItem(BranchMenuItem item) {
        BranchMenuItemStorage.update(item);
    }

    public BranchMenuItem getBranchMenuItem(int branchID, String itemName){
        List<BranchMenuItem> items = getBranchMenuItemList(branchID);
        for (BranchMenuItem item : items) {
            if(item.getName().equals(itemName)){
                return item;
            }
        }
        return null;
    }
}