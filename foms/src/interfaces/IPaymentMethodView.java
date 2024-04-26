package interfaces;

import models.PaymentMethod;

/**
 * The IPaymentMethodView interface provides methods to display payment methods.
 */
public interface IPaymentMethodView {

    /**
     * Displays the provided list of payment methods.
     *
     * @param methodList an array of PaymentMethod objects representing the payment methods to be displayed
     */
    public void displayPaymentMethods(PaymentMethod[] methodList);
}
