package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import enums.OrderStatus;
import interfaces.ICustomerService;
import models.BranchMenuItem;
import models.Order;
import models.PaymentMethod;
import stores.BranchMenuItemStorage;
import stores.BranchStorage;
import stores.OrderStorage;
import stores.PaymentMethodStorage;

/**
 * The CustomerService class implements the ICustomerService interface and provides
 * methods to interact with customer-related functionality in the system.
 */
public class CustomerService implements ICustomerService{
    
    /**
     * Retrieves the order with the specified OrderID.
     *
     * @param OrderID The ID of the order to retrieve.
     * @return The Order object with the specified OrderID, or null if not found.
     */
    @Override
    public Order getOrder(int OrderID) {
        Order order = OrderStorage.get(OrderID);
        return order;
    }

    /**
     * Creates a new order in the system.
     *
     * @param order The Order object to be created.
     */
    @Override
    public void newOrder(Order order) {
        OrderStorage.add(order);
    }

    /**
     * Retrieves a list of branch menu items for the specified BranchID.
     *
     * @param BranchID The ID of the branch to retrieve menu items for.
     * @return A list of BranchMenuItem objects for the specified BranchID, or null if not found.
     */
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

    /**
     * Collects the order with the specified OrderID.
     *
     * @param OrderID The ID of the order to collect.
     * @return true if the order was successfully collected, false otherwise.
     */
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

    /**
     * Retrieves the next available OrderID.
     *
     * @return The next available OrderID.
     */
    @Override
    public int getNextOrderID() {
        return OrderStorage.getAll().length + 1;
    }

    /**
     * Retrieves the name of the branch with the specified branchID.
     *
     * @param branchID The ID of the branch.
     * @return The name of the branch with the specified branchID.
     */
    @Override
    public String getBranchName(int branchID) {
        return BranchStorage.get(branchID).getName();
    }

    /**
     * Updates the specified branch menu item.
     *
     * @param item The BranchMenuItem object to update.
     */
    @Override
    public void updateBranchMenuItem(BranchMenuItem item) {
        BranchMenuItemStorage.update(item);
    }

    /**
     * Retrieves the branch menu item with the specified branchID and itemName.
     *
     * @param branchID The ID of the branch.
     * @param itemName The name of the menu item.
     * @return The BranchMenuItem object with the specified branchID and itemName, or null if not found.
     */
    public BranchMenuItem getBranchMenuItem(int branchID, String itemName){
        List<BranchMenuItem> items = getBranchMenuItemList(branchID);
        for (BranchMenuItem item : items) {
            if(item.getName().equals(itemName)){
                return item;
            }
        }
        return null;
    }

    /**
     * Retrieves a list of payment methods of the specified type.
     *
     * @param type The type of payment method.
     * @return A list of PaymentMethod objects of the specified type.
     */
    public List<PaymentMethod> getPaymentMethods(String type) {
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        PaymentMethod[] methods = PaymentMethodStorage.getAll();
        for (PaymentMethod method : methods) {
            if(method.getType().equals(type)){
                paymentMethods.add(method);
            }
        }
        return paymentMethods;
    }

    /**
     * Retrieves the ready time of the order with the specified OrderID.
     *
     * @param OrderID The ID of the order.
     * @return The ready time of the order with the specified OrderID.
     */
    public LocalDateTime getReadyTime(int OrderID) {
        Order order = OrderStorage.get(OrderID);
        return order.getReadyTime();
    }

    /**
     * Sets the status of the order with the specified OrderID to CANCELLED.
     *
     * @param OrderID The ID of the order.
     */
    public void setOrderStatus(int OrderID) {
        Order order = OrderStorage.get(OrderID);
        order.setOrderStatus(OrderStatus.CANCELLED);
        OrderStorage.update(order);
    }
}
