package views;

import interfaces.IPaymentMethodView;
import models.PaymentMethod;

/**
 * The {@link PaymentMethodView} class implements the IPaymentMethodView interface and is responsible for displaying payment method details.
 */
public class PaymentMethodView implements IPaymentMethodView{

    /**
     * Displays the provided list of payment methods.
     *
     * @param methodList an array of PaymentMethod objects representing the payment methods to be displayed
     */
    public void displayPaymentMethods(PaymentMethod[] methodList){
        System.out.println("Payment Methods:");
        int count = 1;
        for (PaymentMethod method : methodList) {
            System.out.println("PaymentMethod "+ count++ + ". ");
            System.out.println("\tName: " + method.getPaymentMethod());
            System.out.println("\tType: " + method.getType());
        }
    }
}
