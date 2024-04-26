package interfaces;

import models.PaymentMethod;

public interface IPaymentMethodView {

    public void displayPaymentMethods(PaymentMethod[] methodList);
}
