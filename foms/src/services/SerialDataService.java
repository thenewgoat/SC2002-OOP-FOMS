package services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import models.Account;
import models.Branch;
import models.BranchMenuItem;
import models.BranchUser;
import models.Order;
import models.PaymentMethod;
import models.User;

public class SerialDataService implements FileDataService{

    private final String orderDataPath = "data/orders.ser";
    private final String userDataPath = "data/users.ser";
    private final String branchUserDataPath = "data/branchUsers.ser";
    private final String menuDataPath = "data/menu.ser";
    private final String paymentMethodDataPath = "data/paymentMethods.ser";
    private final String branchDataPath = "data/branches.ser";
    private final String passwordDataPath = "data/passwords.ser";


    @SuppressWarnings("unchecked")
    public HashMap<Integer, Order> importOrderData(){
        return (HashMap<Integer, Order>) importHelper(orderDataPath);
    };

    public boolean exportOrderData(HashMap<Integer, Order> HashMap){
        return exportHelper(orderDataPath, HashMap);
    };

    @SuppressWarnings("unchecked")
    public HashMap<String, User> importUserData(){
        return (HashMap<String, User>) importHelper(userDataPath);
    };

    public boolean exportUserData(HashMap<String, User> HashMap){
        return exportHelper(userDataPath, HashMap);
    };

    @SuppressWarnings("unchecked")
    public HashMap<String, BranchUser> importBranchUserData(){
        return (HashMap<String, BranchUser>) importHelper(branchUserDataPath);
    };

    public boolean exportBranchUserData(HashMap<String, BranchUser> HashMap){
        return exportHelper(branchUserDataPath, HashMap);
    };

    @SuppressWarnings("unchecked")
    public HashMap<Integer, BranchMenuItem> importMenuData(){
        return (HashMap<Integer, BranchMenuItem>) importHelper(menuDataPath);
    };

    public boolean exportMenuData(HashMap<Integer, BranchMenuItem> HashMap){
        return exportHelper(menuDataPath, HashMap);
    };

    @SuppressWarnings("unchecked")
    public HashMap<String, PaymentMethod> importPaymentMethodData(){
        return (HashMap<String, PaymentMethod>) importHelper(paymentMethodDataPath);
    };

    public boolean exportPaymentMethodData(HashMap<String, PaymentMethod> HashMap){
        return exportHelper(paymentMethodDataPath, HashMap);
    };

    @SuppressWarnings("unchecked")
    public HashMap<Integer, Branch> importBranchData(){
        return (HashMap<Integer, Branch>) importHelper(branchDataPath);
    };

    public boolean exportBranchData(HashMap<Integer, Branch> HashMap){
        return exportHelper(branchDataPath, HashMap);
    };

    @SuppressWarnings("unchecked")
    public HashMap<String, Account> importPasswordData(){
        return (HashMap<String, Account>) importHelper(passwordDataPath);
    };

    public boolean exportPasswordData(HashMap<String, Account> HashMap){
        return exportHelper(passwordDataPath, HashMap);
    };



    private HashMap<?,?> importHelper(String importPathString){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(importPathString))) {
            return (HashMap<?,?>) ois.readObject();
        } catch (IOException e) {
            System.out.println("Error reading user data: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found on reading user data: " + e.getMessage());
        }
        return null;
    };

    private boolean exportHelper(String exportPathString, HashMap<?, ?> HashMap){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(exportPathString))) {
            oos.writeObject(HashMap);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
            return false;
        }
    }

}