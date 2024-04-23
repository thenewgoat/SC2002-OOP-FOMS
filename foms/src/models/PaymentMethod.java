package models;

import java.io.Serializable;

/**
 * The PaymentMethod class represents a payment method used for making payments.
 * It provides functionality to pay a specified amount using the payment method.
 */
public class PaymentMethod implements Serializable {
    private String paymentMethod;

    /**
     * Constructs a PaymentMethod object with the specified payment method.
     * 
     * @param paymentMethod the payment method to be set
     */
    public PaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Pays the specified amount using the payment method.
     * 
     * @param amount the amount to be paid
     */
    public void pay(double amount) {
        System.out.println("Paying " + amount + " using " + paymentMethod + ".");
        System.out.println("Processing payment...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(paymentMethod + " Payment successful!");
    }

    /**
     * Returns the payment method.
     * 
     * @return the payment method
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }
}
