package foms.models;

import java.io.Serializable;

public class PaymentMethod implements Serializable {
    private String paymentMethod;

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

    public PaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
