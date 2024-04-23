package services;

import java.util.HashMap;

import models.Account;
import models.Branch;
import models.BranchMenuItem;
import models.BranchUser;
import models.Order;
import models.PaymentMethod;
import models.User;

/**
 * The FileDataService interface provides methods for importing and exporting data from/to files.
 */
public interface FileDataService {
    
    /**
     * Imports order data from a file and returns it as a HashMap.
     *
     * @return the imported order data as a HashMap<Integer, Order>
     */
    public HashMap<Integer, Order> importOrderData();

    /**
     * Exports order data to a file.
     *
     * @param orderData the order data to be exported as a HashMap<Integer, Order>
     * @return true if the export is successful, false otherwise
     */
    public boolean exportOrderData(HashMap<Integer, Order> orderData);

    /**
     * Imports user data from a file and returns it as a HashMap.
     *
     * @return the imported user data as a HashMap<String, User>
     */
    public HashMap<String, User> importUserData();

    /**
     * Exports user data to a file.
     *
     * @param userData the user data to be exported as a HashMap<String, User>
     * @return true if the export is successful, false otherwise
     */
    public boolean exportUserData(HashMap<String, User> userData);

    /**
     * Imports branch user data from a file and returns it as a HashMap.
     *
     * @return the imported branch user data as a HashMap<String, BranchUser>
     */
    public HashMap<String, BranchUser> importBranchUserData();

    /**
     * Exports branch user data to a file.
     *
     * @param branchUserData the branch user data to be exported as a HashMap<String, BranchUser>
     * @return true if the export is successful, false otherwise
     */
    public boolean exportBranchUserData(HashMap<String, BranchUser> branchUserData);

    /**
     * Imports menu data from a file and returns it as a HashMap.
     *
     * @return the imported menu data as a HashMap<Integer, BranchMenuItem>
     */
    public HashMap<Integer, BranchMenuItem> importMenuData();

    /**
     * Exports menu data to a file.
     *
     * @param menuData the menu data to be exported as a HashMap<Integer, BranchMenuItem>
     * @return true if the export is successful, false otherwise
     */
    public boolean exportMenuData(HashMap<Integer, BranchMenuItem> menuData);

    /**
     * Imports payment method data from a file and returns it as a HashMap.
     *
     * @return the imported payment method data as a HashMap<String, PaymentMethod>
     */
    public HashMap<String, PaymentMethod> importPaymentMethodData();

    /**
     * Exports payment method data to a file.
     *
     * @param paymentMethodData the payment method data to be exported as a HashMap<String, PaymentMethod>
     * @return true if the export is successful, false otherwise
     */
    public boolean exportPaymentMethodData(HashMap<String, PaymentMethod> paymentMethodData);

    /**
     * Imports branch data from a file and returns it as a HashMap.
     *
     * @return the imported branch data as a HashMap<Integer, Branch>
     */
    public HashMap<Integer, Branch> importBranchData();

    /**
     * Exports branch data to a file.
     *
     * @param branchData the branch data to be exported as a HashMap<Integer, Branch>
     * @return true if the export is successful, false otherwise
     */
    public boolean exportBranchData(HashMap<Integer, Branch> branchData);

    /**
     * Imports password data from a file and returns it as a HashMap.
     *
     * @return the imported password data as a HashMap<String, Account>
     */
    public HashMap<String, Account> importPasswordData();

    /**
     * Exports password data to a file.
     *
     * @param passwordData the password data to be exported as a HashMap<String, Account>
     * @return true if the export is successful, false otherwise
     */
    public boolean exportPasswordData(HashMap<String, Account> passwordData);

}
