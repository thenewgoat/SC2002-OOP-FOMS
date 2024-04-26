package interfaces;

import models.Order;

/**
 * The {@code IOrderView} interface provides methods to view order details.
 */
public interface IOrderView {

    /**
     * Displays the details of the given order.
     *
     * @param order the order to be displayed
     */
    public void displayOrderDetails(Order order);
}
