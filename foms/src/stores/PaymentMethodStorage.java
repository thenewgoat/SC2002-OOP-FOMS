package stores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import models.PaymentMethod;

public class PaymentMethodStorage implements Storage{
    private final String paymentMethodDataPath = "data/paymentMethods.ser";
    private Map<String, PaymentMethod> PaymentMethods = new HashMap<>();

    public PaymentMethodStorage(){
        load();
    }

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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(paymentMethodDataPath))) {
            oos.writeObject(PaymentMethods);
        } catch (IOException e) {
            System.out.println("Error saving PaymentMethod storage: " + e.getMessage());
        }
    }

    @Override
    public void load() {
        File file = new File(paymentMethodDataPath);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                PaymentMethods = (HashMap<String, PaymentMethod>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading PaymentMethod storage: " + e.getMessage());
            }
        }else{
            PaymentMethods = new HashMap<>();
            System.out.println("PaymentMethod storage file not found. Creating new storage.");
            PaymentMethods.put("Credit/Debit Card", new PaymentMethod("Credit/Debit Card"));
            PaymentMethods.put("PayPal", new PaymentMethod("PayPal"));
            save();
        }
    }

    @Override
    public void clear() {
        PaymentMethods.clear();
    }
}
