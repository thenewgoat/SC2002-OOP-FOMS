package interfaces;

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
public interface IFileDataService {
    
    /**
     * Imports order data from a file and returns it as a HashMap.
     *
     * @return the imported order data as a HashMap of Order objects
     */
    public HashMap<Integer, Order> importOrderData();

    /**
     * Exports order data to a file.
     *
     * @param orderData the order data to be exported as a HashMap of Order objects
     * @return true if the export is successful, false otherwise
     */
    public boolean exportOrderData(HashMap<Integer, Order> orderData);

    /**
     * Imports user data from a file and returns it as a HashMap.
     *
     * @return the imported user data as a HashMap of User objects
     */
    public HashMap<String, User> importUserData();

    /**
     * Exports user data to a file.
     *
     * @param userData the user data to be exported as a HashMap of User objects
     * @return true if the export is successful, false otherwise
     */
    public boolean exportUserData(HashMap<String, User> userData);

    /**
     * Imports branch user data from a file and returns it as a HashMap.
     *
     * @return the imported branch user data as a HashMap of BranchUser objects
     */
    public HashMap<String, BranchUser> importBranchUserData();

    /**
     * Exports branch user data to a file.
     *
     * @param branchUserData the branch user data to be exported as a HashMap of BranchUser objects
     * @return true if the export is successful, false otherwise
     */
    public boolean exportBranchUserData(HashMap<String, BranchUser> branchUserData);

    /**
     * Imports menu data from a file and returns it as a HashMap.
     *
     * @return the imported menu data as a HashMap of BranchMenuItem objects
     */
    public HashMap<Integer, BranchMenuItem> importMenuData();

    /**
     * Exports menu data to a file.
     *
     * @param menuData the menu data to be exported as a HashMap of BranchMenuItem objects
     * @return true if the export is successful, false otherwise
     */
    public boolean exportMenuData(HashMap<Integer, BranchMenuItem> menuData);

    /**
     * Imports payment method data from a file and returns it as a HashMap.
     *
     * @return the imported payment method data as a HashMap of PaymentMethod objects
     */
    public HashMap<String, PaymentMethod> importPaymentMethodData();

    /**
     * Exports payment method data to a file.
     *
     * @param paymentMethodData the payment method data to be exported as a HashMap of PaymentMethod objects
     * @return true if the export is successful, false otherwise
     */
    public boolean exportPaymentMethodData(HashMap<String, PaymentMethod> paymentMethodData);

    /**
     * Imports branch data from a file and returns it as a HashMap.
     *
     * @return the imported branch data as a HashMap of Branch objects
     */
    public HashMap<Integer, Branch> importBranchData();

    /**
     * Exports branch data to a file.
     *
     * @param branchData the branch data to be exported as a HashMap of Branch objects
     * @return true if the export is successful, false otherwise
     */
    public boolean exportBranchData(HashMap<Integer, Branch> branchData);

    /**
     * Imports password data from a file and returns it as a HashMap.
     *
     * @return the imported password data as a HashMap of Account objects
     */
    public HashMap<String, Account> importPasswordData();

    /**
     * Exports password data to a file.
     *
     * @param passwordData the password data to be exported as a HashMap of Account objects
     * @return true if the export is successful, false otherwise
     */
    public boolean exportPasswordData(HashMap<String, Account> passwordData);

}
