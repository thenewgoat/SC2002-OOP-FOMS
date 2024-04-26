package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import enums.Gender;
import enums.Role;
import interfaces.IFileDataService;
import models.Account;
import models.Admin;
import models.Branch;
import models.BranchMenuItem;
import models.BranchUser;
import models.Order;
import models.PaymentMethod;
import models.User;
import stores.BranchStorage;

/**
 * Provides services to import and export data from/to CSV files for various data models
 * including users, menu items, and branches.
 * 
 * In this programme, CSV files are only used to import staff_livst.csv, menu_list.csv, and branch_list.csv.
 * Furthermore, this import is only performed once for system initialization.
 * The other irrelevant import methods are not implemented.
 * 
 * CSV files are not used to store any data, hence none of the export methods are implemented.
 * 
 */
public class CSVDataService implements IFileDataService {

    private final String userFilename = "foms/data/staff_list.csv";
    private final String menuFilename = "foms/data/menu_list.csv";
    private final String branchFilename = "foms/data/branch_list.csv";

    /**
     * Imports order data from a CSV file.
     * @return a map of orders indexed by their ID.
     * @throws UnsupportedOperationException since the method is not implemented.
     */
    public HashMap<Integer, Order> importOrderData() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * Exports order data to a CSV file.
     * @param hashMap the data to export.
     * @return true if export is successful.
     * @throws UnsupportedOperationException since the method is not implemented.
     */
    public boolean exportOrderData(HashMap<Integer, Order> hashMap) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * Imports user data from a specified CSV file.
     * @return a HashMap of User objects, keyed by their login IDs.
     */
    public HashMap<String, User> importUserData() {
        File file = new File(userFilename);
        if (!file.exists()) {
            System.err.println("Error: The file " + userFilename + " does not exist.");
            return null;
        }

        HashMap<String, User> users = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(userFilename))) {
            String line;
            br.readLine(); // Skip the header row
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                String name = userData[0].trim();
                String staffLoginID = userData[1].trim();
                String role = userData[2].trim();
                String gender = userData[3].trim();
                int age = Integer.parseInt(userData[4].trim());
                Gender enumGender = "M".equals(gender) ? Gender.MALE : Gender.FEMALE;

                User user = null;
                switch (role) {
                    case "S":
                        break;
                    case "M":
                        break;
                    case "A":
                        user = new Admin(name, staffLoginID, enumGender, age);
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported role: " + role);
                }
                if (user != null) {
                    users.put(staffLoginID, user);
                }
            }
        } catch (Exception e) {
            System.err.println("Error importing employees: " + e.getMessage());
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Exports user data to a CSV file.
     * @param hashMap the data to export.
     * @return true if export is successful.
     * @throws UnsupportedOperationException since the method is not implemented.
     */
    public boolean exportUserData(HashMap<String, User> hashMap) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * Imports BranchUser data from a CSV file.
     * @return a HashMap of BranchUser objects, keyed by their login IDs.
     */
    public HashMap<String, BranchUser> importBranchUserData() {
        File file = new File(userFilename);
        if (!file.exists()) {
            System.err.println("Error: The file " + userFilename + " does not exist.");
            return null;
        }

        HashMap<String, BranchUser> branchUsers = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(userFilename))) {
            String line;
            br.readLine(); // Skip the header row
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                String name = userData[0].trim();
                String staffLoginID = userData[1].trim();
                String role = userData[2].trim();
                String gender = userData[3].trim();
                int age = Integer.parseInt(userData[4].trim());
                Gender enumGender = "M".equals(gender) ? Gender.MALE : Gender.FEMALE;

                User user;
                int branchID = -1; // default for admin
                if (!"A".equals(role)) {
                    String branchName = userData[5].trim();
                    Branch branch = BranchStorage.get(branchName);
                    branchID = branch != null ? branch.getID() : -1;
                }

                switch (role) {
                    case "S":
                        user = new BranchUser(name, staffLoginID, Role.STAFF, enumGender, age, branchID);
                        break;
                    case "M":
                        user = new BranchUser(name, staffLoginID, Role.BRANCHMANAGER, enumGender, age, branchID);
                        break;
                    case "A":
                        user = new Admin(name, staffLoginID, enumGender, age);
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported role: " + role);
                }
                if (user instanceof BranchUser) {
                    branchUsers.put(staffLoginID, (BranchUser) user);
                }
            }
        } catch (Exception e) {
            System.err.println("Error importing employees: " + e.getMessage());
            e.printStackTrace();
        }

        return branchUsers;
    }

    /**
     * Exports BranchUser data to a CSV file.
     * @param hashMap the data to export.
     * @return true if export is successful.
     * @throws UnsupportedOperationException since the method is not implemented.
     */
    public boolean exportBranchUserData(HashMap<String, BranchUser> hashMap) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * Imports menu data from a CSV file and returns a map of menu items indexed by item ID.
     * @return HashMap of menu items.
     */
    public HashMap<Integer, BranchMenuItem> importMenuData() {
        HashMap<Integer, BranchMenuItem> menuItems = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(menuFilename))) {
            String line;
            br.readLine(); // Skip the header row
            int count = 0;

            while ((line = br.readLine()) != null) {
                String[] menuItemData = line.split(",");
                String itemName = menuItemData[0].trim();
                double price = Double.parseDouble(menuItemData[1].trim());
                String branchName = menuItemData[2].trim();
                String category = menuItemData[3].trim();
                int availability = Integer.parseInt(menuItemData[4].trim());
                String description = menuItemData[5].trim();

                Branch branch = BranchStorage.get(branchName);
                int branchID = branch != null ? branch.getID() : -1;

                count++;
                BranchMenuItem item = new BranchMenuItem(itemName, count, category, price, availability, description, branchID);
                menuItems.put(count, item);
            }
        } catch (Exception e) {
            System.err.println("Error importing menu: " + e.getMessage());
            e.printStackTrace();
        }

        return menuItems;
    }

    /**
     * Exports menu data to a CSV file.
     * @param hashMap the data to export.
     * @return true if export is successful.
     * @throws UnsupportedOperationException since the method is not implemented.
     */
    public boolean exportMenuData(HashMap<Integer, BranchMenuItem> hashMap) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * Imports payment method data from a CSV file.
     * @return a HashMap of PaymentMethod objects.
     * @throws UnsupportedOperationException since the method is not implemented.
     */
    public HashMap<String, PaymentMethod> importPaymentMethodData() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * Exports payment method data to a CSV file.
     * @param hashMap the data to export.
     * @return true if export is successful.
     * @throws UnsupportedOperationException since the method is not implemented.
     */
    public boolean exportPaymentMethodData(HashMap<String, PaymentMethod> hashMap) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * Imports branch data from a CSV file and returns a map of Branch objects indexed by their ID.
     * @return HashMap of Branch objects.
     */
    public HashMap<Integer, Branch> importBranchData() {
        HashMap<Integer, Branch> branches = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(branchFilename))) {
            String line;
            br.readLine(); // Skip the header row
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] branchData = line.split(",");
                String name = branchData[0].trim();
                String location = branchData[1].trim();
                int staffQuota = Integer.parseInt(branchData[2].trim());
                count++;
                Branch branch = new Branch(count, name, location, staffQuota);
                branches.put(count, branch);
            }
        } catch (Exception e) {
            System.err.println("Error loading branch data: " + e.getMessage());
            e.printStackTrace();
        }
        return branches;
    }

    /**
     * Exports branch data to a CSV file.
     * @param hashMap the data to export.
     * @return true if export is successful.
     * @throws UnsupportedOperationException since the method is not implemented.
     */
    public boolean exportBranchData(HashMap<Integer, Branch> hashMap) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * Imports password data from a CSV file.
     * @return a HashMap of Account objects.
     * @throws UnsupportedOperationException since the method is not implemented.
     */
    public HashMap<String, Account> importPasswordData() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * Exports password data to a CSV file.
     * @param hashMap the data to export.
     * @return true if export is successful.
     * @throws UnsupportedOperationException since the method is not implemented.
     */
    public boolean exportPasswordData(HashMap<String, Account> hashMap) {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
