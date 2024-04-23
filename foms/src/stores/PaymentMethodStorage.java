package stores;

import java.io.File;
import java.util.HashMap;
import models.PaymentMethod;
import services.SerialDataService;

/**
 * The PaymentMethodStorage class is responsible for storing and managing payment methods.
 * It implements the Storage interface and provides methods for adding, removing, updating,
 * and retrieving payment methods.
 */
public class PaymentMethodStorage implements Storage{
    private final String paymentMethodDataPath = "foms/data/paymentMethods.ser";
    private HashMap<String, PaymentMethod> PaymentMethods = new HashMap<>();

    /**
     * Constructs a new PaymentMethodStorage object and loads the payment methods from storage.
     */
    public PaymentMethodStorage(){
        load();
    }

    /**
     * Adds a payment method to the storage.
     * 
     * @param object The payment method to be added.
     * @throws IllegalArgumentException if the object is not an instance of PaymentMethod.
     * @throws IllegalArgumentException if a payment method with the same name already exists.
     */
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

    /**
     * Removes a payment method from the storage.
     * 
     * @param object The payment method to be removed.
     * @throws IllegalArgumentException if the object is not an instance of PaymentMethod.
     */
    @Override
    public void remove(Object object) {
        if (object instanceof PaymentMethod) {
            PaymentMethod PaymentMethod = (PaymentMethod) object;
            PaymentMethods.remove(PaymentMethod.getPaymentMethod());
        } else {
            throw new IllegalArgumentException("Object must be an instance of PaymentMethod.");
        }
    }

    /**
     * Updates a payment method in the storage.
     * 
     * @param object The payment method to be updated.
     * @throws IllegalArgumentException if the object is not an instance of PaymentMethod.
     * @throws IllegalArgumentException if the payment method does not exist in the storage.
     */
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

    /**
     * Unsupported operation for getting a payment method by numerical ID.
     * 
     * @param PaymentMethodID the ID of the payment method.
     * @throws UnsupportedOperationException always.
     */
    @Override
    public PaymentMethod get(int PaymentMethodID) {
        throw new UnsupportedOperationException("Use get(String PaymentMethodID) for retrieving PaymentMethods.");
    }

    /**
     * Retrieves a payment method from the storage based on its name.
     * 
     * @param name The name of the payment method.
     * @return The payment method with the specified name, or null if not found.
     */
    @Override
    public PaymentMethod get(String name) {
        return PaymentMethods.get(name);
    }

    /**
     * Retrieves all payment methods from the storage.
     * 
     * @return An array of all payment methods in the storage.
     */
    @Override
    public PaymentMethod[] getAll() {
        return PaymentMethods.values().toArray(new PaymentMethod[0]);
    }

    /**
     * Saves the payment methods to the storage.
     */
    @Override
    public void save() {
        SerialDataService serialDataService = new SerialDataService();
        serialDataService.exportPaymentMethodData(PaymentMethods);
    }

    /**
     * Loads the payment methods from the storage.
     * If the storage file does not exist, creates a new storage with default payment methods.
     */
    @Override
    public void load() {
        File file = new File(paymentMethodDataPath);
        if (file.exists()) {
            SerialDataService serialDataService = new SerialDataService();
            PaymentMethods = serialDataService.importPaymentMethodData();
        }else{
            PaymentMethods = new HashMap<>();
            System.out.println("PaymentMethod storage file not found. Creating new storage.");
            PaymentMethods.put("Credit/Debit Card", new PaymentMethod("Credit/Debit Card"));
            PaymentMethods.put("PayPal", new PaymentMethod("PayPal"));
            save();
        }
    }

    /**
     * Clears all payment methods from the storage.
     */
    @Override
    public void clear() {
        PaymentMethods.clear();
    }
}
