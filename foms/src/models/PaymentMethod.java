package models;

import java.io.Serializable;

/**
 * The PaymentMethod class represents a payment method with its associated type.
 */
public class PaymentMethod implements Serializable {
    private String paymentMethod;
    private String type;

    /**
     * Constructs a PaymentMethod object with the specified payment method and type.
     * 
     * @param paymentMethod the payment method to be set
     * @param type the type of the payment method
     */
    public PaymentMethod(String paymentMethod, String type) {
        this.paymentMethod = paymentMethod;
        this.type = type;
    }

    /**
     * Pays the specified amount using the payment method.
     * 
     * @param amount the amount to be paid
     */
    public void pay(double amount) {
        System.out.println("Paying " + amount + " using " + paymentMethod + " " + type +".");
        System.out.println("Processing payment...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(paymentMethod + " Payment successful!");
    }

    /**
     * Sets the type of the payment method.
     * 
     * @param type the type to be set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the type of the payment method.
     * 
     * @return the type of the payment method
     */
    public String getType() {
        return type;
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
