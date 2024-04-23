package services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

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
    public Map<Integer, Order> importOrderData(){
        return (Map<Integer, Order>) importHelper(orderDataPath);
    };

    public boolean exportOrderData(Map<Integer, Order> map){
        return exportHelper(orderDataPath, map);
    };

    @SuppressWarnings("unchecked")
    public Map<String, User> importUserData(){
        return (Map<String, User>) importHelper(userDataPath);
    };

    public boolean exportUserData(Map<String, User> map){
        return exportHelper(userDataPath, map);
    };

    @SuppressWarnings("unchecked")
    public Map<String, BranchUser> importBranchUserData(){
        return (Map<String, BranchUser>) importHelper(branchUserDataPath);
    };

    public boolean exportBranchUserData(Map<String, BranchUser> map){
        return exportHelper(branchUserDataPath, map);
    };

    @SuppressWarnings("unchecked")
    public Map<Integer, BranchMenuItem> importMenuData(){
        return (Map<Integer, BranchMenuItem>) importHelper(menuDataPath);
    };

    public boolean exportMenuData(Map<Integer, BranchMenuItem> map){
        return exportHelper(menuDataPath, map);
    };

    @SuppressWarnings("unchecked")
    public Map<String, PaymentMethod> importPaymentMethodData(){
        return (Map<String, PaymentMethod>) importHelper(paymentMethodDataPath);
    };

    public boolean exportPaymentMethodData(Map<String, PaymentMethod> map){
        return exportHelper(paymentMethodDataPath, map);
    };

    @SuppressWarnings("unchecked")
    public Map<Integer, Branch> importBranchData(){
        return (Map<Integer, Branch>) importHelper(branchDataPath);
    };

    public boolean exportBranchData(Map<Integer, Branch> map){
        return exportHelper(branchDataPath, map);
    };

    @SuppressWarnings("unchecked")
    public Map<String, Account> importPasswordData(){
        return (Map<String, Account>) importHelper(passwordDataPath);
    };

    public boolean exportPasswordData(Map<String, Account> map){
        return exportHelper(passwordDataPath, map);
    };



    private Map<?,?> importHelper(String importPathString){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(importPathString))) {
            return (Map<?,?>) ois.readObject();
        } catch (IOException e) {
            System.out.println("Error reading user data: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found on reading user data: " + e.getMessage());
        }
        return null;
    };

    private boolean exportHelper(String exportPathString, Map<?, ?> map){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(exportPathString))) {
            oos.writeObject(map);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
            return false;
        }
    }

}