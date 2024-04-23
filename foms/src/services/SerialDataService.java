package services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import interfaces.IFileDataService;
import models.Account;
import models.Branch;
import models.BranchMenuItem;
import models.BranchUser;
import models.Order;
import models.PaymentMethod;
import models.User;

/**
 * Handles serialization and deserialization of different data types to and from disk.
 * This class supports operations for orders, users, branch users, menu items, payment methods,
 * branches, and password data.
 */
public class SerialDataService implements IFileDataService{

    private final String orderDataPath = "foms/data/orders.ser";
    private final String userDataPath = "foms/data/users.ser";
    private final String branchUserDataPath = "foms/data/branchUsers.ser";
    private final String menuDataPath = "foms/data/menu.ser";
    private final String paymentMethodDataPath = "foms/data/paymentMethods.ser";
    private final String branchDataPath = "foms/data/branches.ser";
    private final String passwordDataPath = "foms/data/passwords.ser";

    /**
     * Imports order data from serialization file.
     * @return A HashMap containing the imported orders, keyed by order ID.
     */
    @SuppressWarnings("unchecked")
    public HashMap<Integer, Order> importOrderData(){
        return (HashMap<Integer, Order>) importHelper(orderDataPath);
    }

    /**
     * Exports order data to a serialization file.
     * @param hashMap The HashMap of orders to serialize.
     * @return True if the export is successful, false otherwise.
     */
    public boolean exportOrderData(HashMap<Integer, Order> hashMap){
        return exportHelper(orderDataPath, hashMap);
    }

    /**
     * Imports user data from serialization file.
     * @return A HashMap containing the imported users, keyed by user login ID.
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, User> importUserData(){
        return (HashMap<String, User>) importHelper(userDataPath);
    }

    /**
     * Exports user data to a serialization file.
     * @param hashMap The HashMap of users to serialize.
     * @return True if the export is successful, false otherwise.
     */
    public boolean exportUserData(HashMap<String, User> hashMap){
        return exportHelper(userDataPath, hashMap);
    }

    /**
     * Imports branch user data from serialization file.
     * @return A HashMap containing the imported branch users, keyed by user login ID.
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, BranchUser> importBranchUserData(){
        return (HashMap<String, BranchUser>) importHelper(branchUserDataPath);
    }

    /**
     * Exports branch user data to a serialization file.
     * @param hashMap The HashMap of branch users to serialize.
     * @return True if the export is successful, false otherwise.
     */
    public boolean exportBranchUserData(HashMap<String, BranchUser> hashMap){
        return exportHelper(branchUserDataPath, hashMap);
    }

    /**
     * Imports menu data from serialization file.
     * @return A HashMap containing the imported menu items, keyed by item ID.
     */
    @SuppressWarnings("unchecked")
    public HashMap<Integer, BranchMenuItem> importMenuData(){
        return (HashMap<Integer, BranchMenuItem>) importHelper(menuDataPath);
    }

    /**
     * Exports menu data to a serialization file.
     * @param hashMap The HashMap of menu items to serialize.
     * @return True if the export is successful, false otherwise.
     */
    public boolean exportMenuData(HashMap<Integer, BranchMenuItem> hashMap){
        return exportHelper(menuDataPath, hashMap);
    }

    /**
     * Imports payment method data from serialization file.
     * @return A HashMap containing the imported payment methods, keyed by payment method name.
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, PaymentMethod> importPaymentMethodData(){
        return (HashMap<String, PaymentMethod>) importHelper(paymentMethodDataPath);
    }

    /**
     * Exports payment method data to a serialization file.
     * @param hashMap The HashMap of payment methods to serialize.
     * @return True if the export is successful, false otherwise.
     */
    public boolean exportPaymentMethodData(HashMap<String, PaymentMethod> hashMap){
        return exportHelper(paymentMethodDataPath, hashMap);
    }

    /**
     * Imports branch data from serialization file.
     * @return A HashMap containing the imported branches, keyed by branch ID.
     */
    @SuppressWarnings("unchecked")
    public HashMap<Integer, Branch> importBranchData(){
        return (HashMap<Integer, Branch>) importHelper(branchDataPath);
    }

    /**
     * Exports branch data to a serialization file.
     * @param hashMap The HashMap of branches to serialize.
     * @return True if the export is successful, false otherwise.
     */
    public boolean exportBranchData(HashMap<Integer, Branch> hashMap){
        return exportHelper(branchDataPath, hashMap);
    }

    /**
     * Imports password data from serialization file.
     * @return A HashMap containing the imported accounts, keyed by login ID.
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, Account> importPasswordData(){
        return (HashMap<String, Account>) importHelper(passwordDataPath);
    }

    /**
     * Exports password data to a serialization file.
     * @param hashMap The HashMap of accounts to serialize.
     * @return True if the export is successful, false otherwise.
     */
    public boolean exportPasswordData(HashMap<String, Account> hashMap){
        return exportHelper(passwordDataPath, hashMap);
    }

    /**
     * Helper method to handle the import of serialized data.
     * @param importPathString The path to the file from which to import data.
     * @return The imported HashMap.
     */
    private HashMap<?,?> importHelper(String importPathString){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(importPathString))) {
            return (HashMap<?,?>) ois.readObject();
        } catch (IOException e) {
            System.out.println("Error reading user data: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found on reading user data: " + e.getMessage());
        }
        return null;
    }

    /**
     * Helper method to handle the export of serialized data.
     * @param exportPathString The path to the file to which to export data.
     * @param hashMap The HashMap to serialize.
     * @return True if the export is successful, false otherwise.
     */
    private boolean exportHelper(String exportPathString, HashMap<?, ?> hashMap){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(exportPathString))) {
            oos.writeObject(hashMap);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
            return false;
        }
    }
}
