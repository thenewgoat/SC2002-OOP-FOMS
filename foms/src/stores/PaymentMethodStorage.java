package stores;

import java.util.HashMap;
import java.util.Map;

import models.PaymentMethod;

public class PaymentMethodStorage implements Storage{
    private Map<String, PaymentMethod> PaymentMethods = new HashMap<>();

    @Override
    public void add(Object object) {
        if (object instanceof PaymentMethod) {
            PaymentMethod PaymentMethod = (PaymentMethod) object;
            if (!PaymentMethods.containsKey(PaymentMethod.getPaymentMethod())) {
                PaymentMethods.put(PaymentMethod.getPaymentMethod(), PaymentMethod);
            } else {
                throw new IllegalArgumentException("PaymentMethod with name " + PaymentMethod.getPaymentMethod() + " already exists.");
            }
        } else {
            throw new IllegalArgumentException("Object must be an instance of PaymentMethod.");
        }
    }

    @Override
    public void remove(Object object) {
        if (object instanceof PaymentMethod) {
            PaymentMethod PaymentMethod = (PaymentMethod) object;
            PaymentMethods.remove(PaymentMethod.getPaymentMethod());
        } else {
            throw new IllegalArgumentException("Object must be an instance of PaymentMethod.");
        }
    }

    @Override
    public void update(Object object) {
        if (object instanceof PaymentMethod) {
            PaymentMethod PaymentMethod = (PaymentMethod) object;
            if (PaymentMethods.containsKey(PaymentMethod.getPaymentMethod())) {
                PaymentMethods.put(PaymentMethod.getPaymentMethod(), PaymentMethod);
            } else {
                throw new IllegalArgumentException("Cannot update non-existing PaymentMethod.");
            }
        } else {
            throw new IllegalArgumentException("Object must be an instance of PaymentMethod.");
        }
    }

    @Override
    public Object get(int PaymentMethodID) {
        throw new UnsupportedOperationException("Use get(String PaymentMethodID) for retrieving PaymentMethods.");
    }

    @Override
    public Object get(String name) {
        return PaymentMethods.get(name);
    }

    @Override
    public Object[] getAll() {
        return PaymentMethods.values().toArray(new PaymentMethod[0]);
    }

    @Override
    public void save() {
        // Implement based on your persistence mechanism, e.g., serialization or database storage
    }

    @Override
    public void load() {
        // Implement loading logic based on your persistence mechanism
    }

    @Override
    public void clear() {
        PaymentMethods.clear();
    }
}
