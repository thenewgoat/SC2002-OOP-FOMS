package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import enums.Gender;
import enums.Role;
import models.Account;
import models.Admin;
import models.Branch;
import models.BranchMenuItem;
import models.BranchUser;
import models.Order;
import models.PaymentMethod;
import models.User;
import stores.BranchStorage;
import stores.UserStorage;

public class CSVDataService implements FileDataService{

    private final String userFilename = "data/staff_list.csv";
    private final String branchUserFilename = "data/staff_list.csv";
    private final String menuFilename = "data/menu_list.csv";
    private final String branchFilename = "data/branch_list.csv";

    public Map<Integer, Order> importOrderData(){
        throw new UnsupportedOperationException("Not implemented.");
    };

    public boolean exportOrderData(Map<Integer, Order> map){
        throw new UnsupportedOperationException("Not implemented.");
    };

    public Map<String, User> importUserData(){
        File file = new File(userFilename);
        if (!file.exists()) {
            System.err.println("Error: The file " + userFilename + " does not exist.");
            return null; // Stop the method here
        }

        Gender enumGender;

        Map<String, User> users = new HashMap<>();

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
                
                if ("M".equals(gender)){
                    enumGender = Gender.MALE;
                }
                else enumGender = Gender.FEMALE;

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
                if (user != null){
                    users.put(staffLoginID, user);
                }
            }
        } catch (Exception e) {
            System.err.println("Error importing employees: " + e.getMessage());
            e.printStackTrace();
        }

        return users;        
    };

    public boolean exportUserData(Map<String, User> map){
        throw new UnsupportedOperationException("Not implemented.");
    };

    public Map<String, BranchUser> importBranchUserData(){
        File file = new File(userFilename);
        if (!file.exists()) {
            System.err.println("Error: The file " + userFilename + " does not exist.");
            return null; // Stop the method here
        }

        int branchID;
        Branch branch;
        Gender enumGender;

        BranchStorage branchStorage = new BranchStorage();

        Map<String, BranchUser> branchUsers = new HashMap<>();

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
                if (!"A".equals(role)){
                    String branchName = userData[5].trim();
                    branch = (Branch) branchStorage.get(branchName);
                    branchID = branch.getID();
                }
                else branchID = -1;
                
                if ("M".equals(gender)){
                    enumGender = Gender.MALE;
                }
                else enumGender = Gender.FEMALE;

                // Create user object based on role
                User user;
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
                if (user instanceof BranchUser){
                    branchUsers.put(staffLoginID, (BranchUser) user);
                }
            }
        } catch (Exception e) {
            System.err.println("Error importing employees: " + e.getMessage());
            e.printStackTrace();
        }

        return branchUsers;
    };

    public boolean exportBranchUserData(Map<String, BranchUser> map){
        throw new UnsupportedOperationException("Not implemented.");
    };

    public Map<Integer, BranchMenuItem> importMenuData(){
        try (BufferedReader br = new BufferedReader(new FileReader(menuFilename))) {
            String line;
            br.readLine(); // Skip the header row

            int branchID;
            Branch branch;
            Map<String, BranchMenuItem> menuItems = new HashMap<>();

            while ((line = br.readLine()) != null) {
            	if (line.trim().isEmpty()) continue;
                String[] menuItemData = line.split(",");
                String itemName = menuItemData[0].trim();
                double price = Double.parseDouble(menuItemData[1].trim());
                String branchName = menuItemData[2].trim();
                String category = menuItemData[3].trim();
                int availability = Integer.parseInt(menuItemData[4].trim());
                String description = menuItemData[5].trim();
                
                branch = (Branch) branchStorage.get(branchName);
                branchID = branch.getID();


            }
        } catch (Exception e) {
            System.err.println("Error importing menu: " + e.getMessage());
            e.printStackTrace();
        }
    };

    public boolean exportMenuData(Map<Integer, BranchMenuItem> map){
        throw new UnsupportedOperationException("Not implemented.");
    };

    public Map<String, PaymentMethod> importPaymentMethodData();

    public boolean exportPaymentMethodData(Map<String, PaymentMethod> map);

    public Map<Integer, Branch> importBranchData();

    public boolean exportBranchData(Map<Integer, Branch> map);

    public Map<String, Account> importPasswordData();

    public boolean exportPasswordData(Map<String, Account> map);
}
