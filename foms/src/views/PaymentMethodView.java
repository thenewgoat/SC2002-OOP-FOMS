package views;

import models.PaymentMethod;

public class PaymentMethodView {

    public static void displayPaymentMethods(PaymentMethod[] methodList){
        System.out.println("Payment Methods:");
        int count = 1;
        for (PaymentMethod method : methodList) {
            System.out.println("PaymentMethod "+ count++ + ". ");
            System.out.println("\tName: " + method.getPaymentMethod());
            System.out.println("\tType: " + method.getType());
        }
    }
}
