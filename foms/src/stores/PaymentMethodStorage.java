package stores;

import java.io.File;
import java.util.HashMap;

import models.PaymentMethod;
import services.SerialDataService;

/**
 * The PaymentMethodStorage class is responsible for storing and managing payment methods.
 * All methods and fields are static.
 */
public class PaymentMethodStorage {
    private static final String paymentMethodDataPath = "foms/data/paymentMethods.ser";
    private static HashMap<String, PaymentMethod> paymentMethods = new HashMap<>();

    // Static initializer to load the payment methods when the class is first loaded
    static {
        load();
    }

    /**
     * Adds a payment method to the storage.
     * 
     * @param paymentMethod The payment method to be added.
     * @throws IllegalArgumentException if the paymentMethod is null or if a payment method with the same name already exists.
     */
    public static void add(PaymentMethod paymentMethod) {
        if (paymentMethod != null) {
            if (!paymentMethods.containsKey(paymentMethod.getPaymentMethod())) {
                paymentMethods.put(paymentMethod.getPaymentMethod(), paymentMethod);
            } else {
                throw new IllegalArgumentException("Payment method with name " + paymentMethod.getPaymentMethod() + " already exists.");
            }
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null PaymentMethod.");
        }
        save();
    }

    /**
     * Removes a payment method from the storage.
     * 
     * @param paymentMethod The payment method to be removed.
     */
    public static void remove(PaymentMethod paymentMethod) {
        if (paymentMethod != null) {
            paymentMethods.remove(paymentMethod.getPaymentMethod());
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null PaymentMethod.");
        }
        save();
    }

    /**
     * Updates a payment method in the storage.
     * 
     * @param paymentMethod The payment method to be updated.
     */
    public static void update(PaymentMethod paymentMethod) {
        if (paymentMethod != null && paymentMethods.containsKey(paymentMethod.getPaymentMethod())) {
            paymentMethods.put(paymentMethod.getPaymentMethod(), paymentMethod);
        } else {
            throw new IllegalArgumentException("Cannot update non-existing or null PaymentMethod.");
        }
        save();
    }

    /**
     * Retrieves a payment method from the storage based on its name.
     * 
     * @param name The name of the payment method.
     * @return The payment method with the specified name, or null if not found.
     */
    public static PaymentMethod get(String name) {
        return paymentMethods.get(name);
    }

    /**
     * Retrieves all payment methods from the storage.
     * 
     * @return An array of all payment methods in the storage.
     */
    public static PaymentMethod[] getAll() {
        return paymentMethods.values().toArray(new PaymentMethod[0]);
    }

    /**
     * Saves the payment methods to the storage.
     */
    public static void save() {
        SerialDataService serialDataService = new SerialDataService();
        serialDataService.exportPaymentMethodData(paymentMethods);
    }

    /**
     * Loads the payment methods from the storage.
     * If the storage file does not exist, creates a new storage with default payment methods.
     */
    public static void load() {
        File file = new File(paymentMethodDataPath);
        if (file.exists()) {
            SerialDataService serialDataService = new SerialDataService();
            paymentMethods = serialDataService.importPaymentMethodData();
        } else {
            paymentMethods = new HashMap<>();
            System.out.println("Payment method storage file not found. Creating new storage.");
            paymentMethods.put("UOB", new PaymentMethod("UOB","Credit/Debit Card"));
            paymentMethods.put("DBS", new PaymentMethod("DBS","Credit/Debit Card"));
            paymentMethods.put("PayPal", new PaymentMethod("PayPal","Online Payment"));
            save();
        }
    }

    /**
     * Clears all payment methods from the storage.
     */
    public static void clear() {
        paymentMethods.clear();
    }
}
