package controllers;

import java.util.InputMismatchException;
import java.util.Scanner;

import enums.Gender;
import enums.Role;
import models.Admin;
import models.Branch;
import models.BranchUser;
import models.PaymentMethod;
import models.User;
import services.AdminService;
import utils.ChangePage;
import utils.exceptions.AccountNotFoundException;
import utils.exceptions.PageBackException;
import utils.exceptions.PasswordMismatchException;
import utils.exceptions.PasswordValidationException;
import views.StaffListView;

public class AdminController {

    private static final Scanner sc = new Scanner(System.in);
    
    public static void start(User user) throws PageBackException{
        if (user instanceof Admin){
            ChangePage.changePage();
            System.out.println("Welcome to Admin Main Page");
            System.out.println("Hello, " + user.getName() + "!");
            System.out.println("What would you like to do?");
            System.out.println();
            System.out.println("\t1. Display Staff list");
            System.out.println("\t2. Manage Staff Accounts");
            System.out.println("\t3. Promote Staff to Manager");
            System.out.println("\t4. Transfer Staff/Manager");
            System.out.println("\t5. Manage Payment Methods");
            System.out.println("\t6. Manage Branches");
            System.out.println("\t7. Change Password");
            System.out.println("\t8. Logout");

            System.out.println();
            System.out.print("Please enter your choice: ");

            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input. Press <enter> to continue.");
                sc.nextLine();
                sc.nextLine();
                AdminController.start(user);
                return;
            }
            sc.nextLine();

            try {
                switch (choice) {
                    case 1:
                        getStaffList();
                        ChangePage.changePage();
                        AdminController.start(user);
                        break;
                    case 2: 
                        manageStaff();
                        ChangePage.changePage();
                        AdminController.start(user);
                        break;
                    case 3:
                        promoteStaff();
                        ChangePage.changePage();
                        AdminController.start(user);
                        break;
                    case 4:
                        transferStaff();
                        ChangePage.changePage();
                        AdminController.start(user);
                        break;
                    case 5:
                        managePayments();
                        ChangePage.changePage();
                        AdminController.start(user);
                        break;
                    case 6:
                        branchManagement();
                        ChangePage.changePage();
                        AdminController.start(user);
                        break;
                    case 7:
                        changePassword(user);
                        break;
                    case 8:
                        System.out.println("Logging out...");
                        System.out.println("Logged out successfully.");
                        System.out.println("Press <enter> to continue.");
                        sc.nextLine();
                        break;
                    default:
                        System.out.println("Invalid choice. Press <enter> to try again.");
                        sc.nextLine();
                        throw new PageBackException();                    
                }
            } catch (PageBackException e) {
                AdminController.start(user);
            }
        }
        else {
            System.out.println("This message should not be seen.");
            System.out.println("You are not authorized to access this page.");
            throw new PageBackException();
        }
    }

    private static void changePassword(User user) throws PageBackException {
        AdminService adminService = new AdminService();
        System.out.println("Enter old password:");
        String oldPassword = sc.nextLine();
        System.out.println("Enter new password:");
        String newPassword = sc.nextLine();

        try {
            adminService.changePassword(user, oldPassword, newPassword);
            System.out.println("Password changed successfully. Press <enter> to log in again.");
            sc.nextLine();
        } catch (AccountNotFoundException | PasswordMismatchException | PasswordValidationException e) {
            System.out.println("Error changing password: " + e.getMessage());
            System.out.println("Press <enter> to continue.");
            sc.nextLine();
            throw new PageBackException();
        }
        return;
        
    }

    private static void branchManagement() throws PageBackException {
        System.out.println("Selection action: ");
        System.out.println("\t1. Add Branch");
        System.out.println("\t2. Close Branch");
        System.out.print("Enter your choice: ");

        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to try again.");
            sc.nextLine();
            sc.nextLine();
            branchManagement();
            return;
        }
        sc.nextLine();

        switch (choice) {
            case 1:
                addBranch();
                break;
            case 2:
                closeBranch();
                break;
            default:
                System.out.println("Invalid choice. Press <enter> to go back to the previous page.");
                sc.nextLine();
                throw new PageBackException();
        }

    }

    private static void addBranch() throws PageBackException {
        AdminService adminService = new AdminService();

        System.out.println("Adding new branch...");
        System.out.print("Enter branch name: ");
        String branchName = sc.nextLine();
        if (branchName == null || branchName.isEmpty()) {
            System.out.println("Branch name cannot be empty. Press <enter> to continue.");
            sc.nextLine();
            throw new PageBackException();
        }
        System.out.print("Enter branch location: ");
        String branchLocation = sc.nextLine();
        if (branchLocation == null || branchLocation.isEmpty()) {
            System.out.println("Branch location cannot be empty. Press <enter> to continue.");
            sc.nextLine();
            throw new PageBackException();
        }
        System.out.print("Set Staff Quota: ");
        int staffQuota;
        try {
            staffQuota = sc.nextInt();
            sc.nextLine();
            if (staffQuota <= 0) {
                System.out.println("Staff quota must be a positive integer. Press <enter> to continue.");
                sc.nextLine();
                throw new PageBackException();
            }
            if (staffQuota > 15){
                System.out.println("Staff quota cannot exceed 15. Press <enter> to continue.");
                sc.nextLine();
                throw new PageBackException();
            }
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to continue.");
            sc.nextLine();
            throw new PageBackException();
        }

        if (adminService.createBranch(branchName, branchLocation, staffQuota)){
            System.out.println("Branch created successfully. Press <enter> to continue.");
            sc.nextLine();
        } else {
            System.out.println("Branch could not be created. Press <enter> to continue.");
            sc.nextLine();
        }
        
    }

    private static void closeBranch() {
        AdminService adminService = new AdminService();

        System.out.println("Select Branch to close: ");
        int count = 1;
        Branch[] branches = adminService.getBranchList();
        for (Branch branch : branches) {
            System.out.println("\t" + count + ". " + branch.getName());
            count++;
        }
        System.out.print("Enter your choice: ");
        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to return to previous page.");
            sc.nextLine();
            sc.nextLine();
            return;
        }
        sc.nextLine();
        if (choice > 0 && choice <= branches.length) {
            Branch branch = branches[choice - 1];
            if (adminService.removeBranch(branch)) {
                System.out.println("Branch closed successfully. Press <enter> to continue.");
                sc.nextLine();
            } else {
                System.out.println("Branch could not be closed. Press <enter> to continue.");
                sc.nextLine();
            }
        } else {
            System.out.println("Invalid choice. Please select a number between 1 and " + branches.length);
            System.out.println("Press <enter> to return.");
            sc.nextLine();
        }
    }


    private static void managePayments() {
        System.out.println("Select action: ");
        System.out.println("\t1. Add Payment Method");
        System.out.println("\t2. Remove Payment Method");

        System.out.print("Enter your choice: ");
        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to try again.");
            sc.nextLine();
            sc.nextLine();
            managePayments();
            return;
        }
        sc.nextLine();

        switch (choice) {
            case 1:
                addPaymentMethod();
                break;
            case 2:
                removePaymentMethod();
                break;
            default:
                System.out.println("Invalid choice. Press <enter> to go back to the previous page.");
                sc.nextLine();
                return;
        }
    }

    private static void removePaymentMethod() {
        AdminService adminService = new AdminService();

        System.out.println("Select Payment Method to remove: ");
        int count = 1;
        PaymentMethod[] paymentMethods = adminService.getPaymentMethods();
        for (PaymentMethod paymentMethod : paymentMethods) {
            System.out.println("\t" + count + ". " + paymentMethod.getPaymentMethod());
            count++;
        }
        
        System.out.print("Enter your choice: ");
        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to return to previous page.");
            sc.nextLine();
            sc.nextLine();
            return;
        }
        sc.nextLine();
        if (choice > 0 && choice <= paymentMethods.length) {
            PaymentMethod selectedPaymentMethod = paymentMethods[choice - 1];
            if (adminService.removePaymentMethod(selectedPaymentMethod)) {
                System.out.println("Payment Method removed successfully. Press <enter> to continue.");
                sc.nextLine();
            } else {
                System.out.println("Payment Method could not be removed. Press <enter> to continue.");
                sc.nextLine();
            }
        } else {
            System.out.println("Invalid choice. Please select a number between 1 and " + paymentMethods.length);
            System.out.println("Press <enter> to return.");
            sc.nextLine();
        }
    }

    private static void addPaymentMethod() {
        AdminService adminService = new AdminService();

        System.out.print("Enter Payment Method Name: ");
        String paymentMethod = sc.nextLine();
        if (paymentMethod.isEmpty()) {
            System.out.println("Payment Method Name cannot be empty. Press <enter> to continue.");
            sc.nextLine();
            return;
        }

        System.out.println("Select Payment Method Type: ");
        System.out.println("\t1. Credit/Debit Card");
        System.out.println("\t2. Online Payment");
        System.out.print("Enter your choice: ");

        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to return to previous page.");
            sc.nextLine();
            sc.nextLine();
            return;
        }
        PaymentMethod newPaymentMethod = null;
        if (choice == 1){
            newPaymentMethod = new PaymentMethod(paymentMethod, "Credit/Debit Card");
        }
        else if (choice == 2){
            newPaymentMethod = new PaymentMethod(paymentMethod, "Online Payment");
        }
        
        if (adminService.addPaymentMethod(newPaymentMethod)) {
            System.out.println("Payment Method added successfully. Press <enter> to continue.");
            sc.nextLine();
        } else {
            System.out.println("Payment Method could not be added. Press <enter> to continue.");
            sc.nextLine();
        }
    }

    private static void transferStaff() throws PageBackException {
        AdminService adminService = new AdminService();
    
        System.out.print("Enter Staff Login ID of staff to transfer: ");
        String staffLoginId = sc.nextLine();
        BranchUser staff = adminService.findStaffByLoginID(staffLoginId);
    
        if (staff == null) {
            System.out.println("No staff member found with that Login ID. Press <enter> to continue.");
            sc.nextLine();
            throw new PageBackException();
        }
    
        System.out.println("Select Branch to transfer to: ");
        int count = 1;
        Branch[] branches = adminService.getBranchList();
        for (Branch branch : branches) {
            System.out.println("\t" + count + ". " + branch.getName());
            count++;
        }
        System.out.print("Enter your choice: ");
        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to return to previous page.");
            sc.nextLine();
            sc.nextLine();
            throw new PageBackException();
        }
        sc.nextLine();
        if (choice > 0 && choice <= branches.length) {
            Branch newBranch = branches[choice - 1];
            Branch oldBranch = adminService.findBranchById(staff.getBranchID());
            if (oldBranch != null) {
                if(adminService.transferStaff(staff, oldBranch, newBranch)){
                    System.out.println("Staff transferred successfully. Press <enter> to continue.");
                    sc.nextLine();
                } else {
                    System.out.println("Staff could not be transferred. Press <enter> to continue.");
                    sc.nextLine();
                }
            }
        } else {
            System.out.println("Invalid choice. Please select a number between 1 and " + branches.length);
            System.out.println("Press <enter> to return.");
            sc.nextLine();
            throw new PageBackException();
        }
    }

    private static void promoteStaff() throws PageBackException {
        AdminService adminService = new AdminService();
    
        System.out.print("Enter Staff Login ID of staff to promote: ");
        String staffLoginId = sc.nextLine();
        BranchUser staff = adminService.findStaffByLoginID(staffLoginId);
    
        if (staff == null) {
            System.out.println("No staff member found with that Login ID. Press <enter> to continue.");
            sc.nextLine();
            throw new PageBackException();
        } else {
            if (adminService.promoteStaff(staff)){
                System.out.println("Staff successfully promoted to manager. Press <enter> to continue.");
            }
            else {
                System.out.println("Press <enter> to continue.");
            }
            sc.nextLine();
        }
    }
    

    private static void manageStaff() throws PageBackException {

        ChangePage.changePage();
        System.out.println("Action to be taken:");
        System.out.println("\t1. Add Staff");
        System.out.println("\t2. Edit Staff");
        System.out.println("\t3. Remove Staff");
        System.out.println("\t4. Back");

        System.out.print("Enter your choice: ");

        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to try again.");
            sc.nextLine();
            sc.nextLine();
            manageStaff();
            return;
        }
        sc.nextLine();

        switch (choice) {
            case 1:
                addStaff();
                break;
            case 2:
                editStaff();
                break;
            case 3:
                removeStaff();
                break;
            case 4:
                throw new PageBackException();
            default:
                System.out.println("Invalid choice.");
                System.out.println("Press <enter> to go back to the previous page.");
                sc.nextLine();
                throw new PageBackException();
        }

    }

    private static void removeStaff() throws PageBackException {
        AdminService adminService = new AdminService();
    
        System.out.print("Enter Staff Login ID to remove: ");
        String staffLoginId = sc.nextLine();
        BranchUser staff = adminService.findStaffByLoginID(staffLoginId);
    
        if (staff == null) {
            System.out.println("No staff member found with that Login ID. Press <enter> to continue.");
            sc.nextLine();
            return;
        }
    
        System.out.print("Are you sure you want to remove " + staff.getName() + "? (Y/N): ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            if (adminService.removeStaff(staff)) {
                System.out.println("Staff removed successfully. Press <enter> to continue.");
                sc.nextLine();
            } else {
                System.out.println("Staff could not be removed. Press <enter> to continue.");
                sc.nextLine();
            }
        } else {
            System.out.println("Staff not removed. Press <enter> to continue.");
            sc.nextLine();
        }
    }
    

    private static void editStaff() throws PageBackException {
        AdminService adminService = new AdminService();
    
        System.out.print("Enter Staff Login ID to edit: ");
        String staffLoginId = sc.nextLine();
        BranchUser staff = adminService.findStaffByLoginID(staffLoginId);
    
        if (staff == null) {
            System.out.println("No staff member found with that Login ID. Press <enter> to continue.");
            sc.nextLine();
            throw new PageBackException();
        }
    
        System.out.print("Enter new name or press Enter to keep [" + staff.getName() + "]: ");
        String name = sc.nextLine();
        if (!name.isEmpty()) {
            staff.setName(name);
        }
    
        System.out.print("Enter new age or press Enter to keep [" + staff.getAge() + "]: ");
        String ageInput = sc.nextLine();
        if (!ageInput.isEmpty()) {
            try {
                int age = Integer.parseInt(ageInput);
                if (age < 18) {
                    System.out.println("Invalid age. Must be at least 18. Press <enter> to continue.");
                    sc.nextLine();
                    throw new PageBackException();
                } else {
                    staff.setAge(age);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for age. Press <enter> to continue.");
                sc.nextLine();
                throw new PageBackException();
            }
        }
    
        System.out.println("Select new gender or press Enter to keep [" + staff.getGender() + "]: ");
        System.out.println("\t1. Male");
        System.out.println("\t2. Female");
        System.out.print("Enter your choice: ");
        String genderChoice = sc.nextLine();
        if (!genderChoice.isEmpty()) {
            switch (genderChoice) {
                case "1":
                    staff.setGender(Gender.MALE);
                    break;
                case "2":
                    staff.setGender(Gender.FEMALE);
                    break;
                default:
                    System.out.println("Invalid choice. Press <enter> to continue.");
                    sc.nextLine();
                    throw new PageBackException();
            }
        }
    
        adminService.updateStaff(staff);
        System.out.println("Staff details updated successfully. Press <enter> to continue.");
        sc.nextLine();
    }
    
    private static void addStaff() throws PageBackException {

        AdminService adminService = new AdminService();
        
        ChangePage.changePage();
        System.out.println("Adding new branch staff member.");
        System.out.print("Enter Staff Name: ");
        String name = sc.nextLine();
        if (name.isEmpty()){
            System.out.println("Name cannot be empty. Press <enter> to Continue.");
            sc.nextLine();
            throw new PageBackException();           
        }
        System.out.print("Enter Staff Login ID: ");
        String staffLoginId = sc.nextLine();
        if (staffLoginId.isEmpty()) {
            System.out.println("Login ID cannot be empty. Press <enter> to Continue.");
            sc.nextLine();
            throw new PageBackException();
        }
        System.out.println("Select Role: ");
        System.out.println("\t1. Branch Manager");
        System.out.println("\t2. Staff");
        System.out.print("Enter here: ");
        int rolechoice;
        try {
            rolechoice = sc.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to return to previous page.");
            sc.nextLine();
            sc.nextLine();
            throw new PageBackException();
        }
        sc.nextLine();
        Role role;
        switch (rolechoice) {
            case 1:
                role = Role.BRANCHMANAGER;
                break;
            case 2:
                role = Role.STAFF;
                break;
            default:
                System.out.println("Invalid choice. Press <enter> to Continue.");
                sc.nextLine();
                throw new PageBackException();
        }

        System.out.println("Select Gender: ");
        System.out.println("\t1. Male");
        System.out.println("\t2. Female");
        System.out.print("Enter here: ");
        int genderchoice;
        try {
            genderchoice = sc.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to return to previous page.");
            sc.nextLine();
            sc.nextLine();
            throw new PageBackException();
        }
        sc.nextLine();
        Gender gender;
        switch (genderchoice) {
            case 1:
                gender = Gender.MALE;
                break;
            case 2:
                gender = Gender.FEMALE;
                break;
            default:
                System.out.println("Invalid choice. Press <enter> to Continue.");
                sc.nextLine();
                throw new PageBackException();
        }

        System.out.print("Enter Age: ");
        int age = 0;
        try {
            age = sc.nextInt();
            sc.nextLine();

            if (age <= 0) {
                System.out.println("Age must be a positive integer. Press <enter> to Continue.");
                sc.nextLine();
                throw new PageBackException();
            }
            else if (age < 18) {
                System.out.println("Boss, they are underage. We can't hire them. Press <enter> to Continue.");
                sc.nextLine();
                throw new PageBackException();
            }
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to Continue.");
            sc.nextLine();
            sc.nextLine();
            throw new PageBackException();
        }
        System.out.println("Select Branch: ");
        int count = 1;
        Branch[] branches = adminService.getBranchList();
        for (Branch branch : branches){
            System.out.println("\t" + count + ". " + branch.getName());
            count++;
        }
        System.out.print("Enter your choice: ");

        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to return to previous page.");
            sc.nextLine();
            sc.nextLine();
            throw new PageBackException();
        }    
        sc.nextLine();
        Branch selectedBranch = null;
        int branchID = -3;
        if (choice > 0 && choice <= branches.length) {
            selectedBranch = branches[choice - 1];
        } else {
            System.out.println("Invalid choice. Please select a number between 1 and " + branches.length);
            System.out.println("Press <enter> to return.");
            sc.nextLine();
            throw new PageBackException();
        }
        if (selectedBranch != null){
            branchID = selectedBranch.getID();
        }
        if (branchID == -3){
            System.out.println("Invalid choice. Press <enter> to Continue.");
            sc.nextLine();
            throw new PageBackException();
        }
        
        BranchUser staff = new BranchUser(name, staffLoginId, role, gender, age, branchID);
        if (adminService.addStaff(staff)){
            System.out.println("Staff added successfully. Press <enter> to Continue.");
            sc.nextLine();
        } else {
            System.out.println("Staff could not be added. Press <enter> to Continue.");
            sc.nextLine();
        }

    }

    private static void getStaffList() throws PageBackException {

        AdminService adminService = new AdminService();
        StaffListView staffListView = new StaffListView();

        ChangePage.changePage();
        System.out.println("Filter by:");
        System.out.println("\t1. No Filter");
        System.out.println("\t2. Branch");
        System.out.println("\t3. Role");
        System.out.println("\t4. Gender");
        System.out.println("\t5. Age");
        System.out.print("Enter your choice: ");

        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Press <enter> to return to previous page.");
            sc.nextLine();
            sc.nextLine();
            throw new PageBackException();
        }     
        sc.nextLine();   

        switch (choice) {
            case 1:
                staffListView.display(adminService.getStaffList(), adminService.getBranchList());
                System.out.println("Press <enter> to return.");
                sc.nextLine();
                break;
            case 2:
                
                int count = 1;
                Branch[] branches = adminService.getBranchList(); 

                System.out.println("Select Branch to filter by: ");
                for (Branch branch : branches){
                    System.out.println("\t" + count + ". " + branch.getName());
                    count++;
                }
                System.out.print("Enter your choice: ");

                try {
                    choice = sc.nextInt();
                } catch (InputMismatchException ime) {
                    System.out.println("Invalid input. Press <enter> to return to previous page.");
                    sc.nextLine();
                    sc.nextLine();
                    throw new PageBackException();
                }
                sc.nextLine();
                Branch selectedBranch = null;
                if (choice > 0 && choice <= branches.length) {
                    selectedBranch = branches[choice - 1];
                } else {
                    System.out.println("Invalid choice. Please select a number between 1 and " + branches.length);
                    System.out.println("Press <enter> to return.");
                    sc.nextLine();
                    throw new PageBackException();
                }
                if (selectedBranch != null){
                    staffListView.display(adminService.getStaffList(selectedBranch), adminService.getBranchList());
                    System.out.println("Press <enter> to return.");
                    sc.nextLine();
                }
                break;

            case 3:
                System.out.println("Select Role to filter by: ");
                System.out.println("\t1. STAFF");
                System.out.println("\t2. MANAGER");
                System.out.print("Enter your choice: ");
                try {
                    choice = sc.nextInt();
                } catch (InputMismatchException ime) {
                    System.out.println("Invalid input. Press <enter> to return to previous page.");
                    sc.nextLine();
                    sc.nextLine();
                    throw new PageBackException();
                }
                sc.nextLine();

                if (choice == 1){
                    staffListView.display(adminService.getStaffList(Role.STAFF), adminService.getBranchList());
                    System.out.println("Press <enter> to return.");
                    sc.nextLine();
                } else if (choice == 2){
                    staffListView.display(adminService.getStaffList(Role.BRANCHMANAGER), adminService.getBranchList());
                    System.out.println("Press <enter> to return.");
                    sc.nextLine();
                } else {
                    System.out.println("Invalid choice. Press <enter> to return.");
                    sc.nextLine();
                    throw new PageBackException();
                }
                break;
            
            case 4:
                System.out.println("Select Gender to filter by: ");
                System.out.println("\t1. Male");
                System.out.println("\t2. Female");
                System.out.print("Enter your choice: ");
                try {
                    choice = sc.nextInt();
                } catch (InputMismatchException ime) {
                    System.out.println("Invalid input. Press <enter> to return to previous page.");
                    sc.nextLine();
                    sc.nextLine();
                    throw new PageBackException();
                }
                sc.nextLine();

                if (choice == 1){
                    staffListView.display(adminService.getStaffList(Gender.MALE), adminService.getBranchList());
                    System.out.println("Press <enter> to return.");
                    sc.nextLine();
                } else if (choice == 2){
                    staffListView.display(adminService.getStaffList(Gender.FEMALE), adminService.getBranchList());
                    System.out.println("Press <enter> to return.");
                    sc.nextLine();
                } else {
                    System.out.println("Invalid choice. Press <enter> to return.");
                    sc.nextLine();
                    throw new PageBackException();
                }
                break;

            case 5:
            System.out.print("Enter Age to filter by: ");
            int age;
            try {
                age = sc.nextInt();
                sc.nextLine();
                if (age < 0) {
                    System.out.println("Invalid input. Age cannot be negative. Press <enter> to return.");
                    sc.nextLine();
                    throw new PageBackException();
                } else if (age < 18){
                    System.out.println("Nice try, but we don't hire minors. Press <enter> to return.");
                    sc.nextLine();
                    throw new PageBackException();
                } else {
                    staffListView.display(adminService.getStaffList(age), adminService.getBranchList());
                    System.out.println("Press <enter> to return.");
                    sc.nextLine();
                }
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input. Press <enter> to return to previous page.");
                sc.nextLine();
                throw new PageBackException();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Press <enter> to return.");
                sc.nextLine();
                throw new PageBackException();
            }
            break;
            default:
                System.out.println("Invalid choice. Press <enter> to go back to the previous page.");
                sc.nextLine();
                throw new PageBackException();       
        }            
    }    
}