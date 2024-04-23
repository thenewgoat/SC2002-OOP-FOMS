package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

import enums.Gender;
import models.Account;
import models.Branch;
import models.BranchMenuItem;
import models.BranchUser;
import models.Order;
import models.PaymentMethod;
import models.User;
import stores.BranchUserStorage;

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

        int branchID;
        Gender enumGender;
        BranchUserStorage branchUserStorage = new BranchUserStorage();

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
                    String branch = userData[5].trim();
                    branchID = branchUserStorage.findBranchByName(branch).getBranchID();
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
                    userManager.addBranchUser((BranchUser) user);
                }
                else {
                    userManager.addUser(user);
                }
            }
        } catch (Exception e) {
            System.err.println("Error importing employees: " + e.getMessage());
            e.printStackTrace();
        }

    };

    public boolean exportUserData(Map<String, User> map);

    public Map<String, BranchUser> importBranchUserData();

    public boolean exportBranchUserData(Map<String, BranchUser> map);

    public Map<Integer, BranchMenuItem> importMenuData();

    public boolean exportMenuData(Map<Integer, BranchMenuItem> map);

    public Map<String, PaymentMethod> importPaymentMethodData();

    public boolean exportPaymentMethodData(Map<String, PaymentMethod> map);

    public Map<Integer, Branch> importBranchData();

    public boolean exportBranchData(Map<Integer, Branch> map);

    public Map<String, Account> importPasswordData();

    public boolean exportPasswordData(Map<String, Account> map);
}
