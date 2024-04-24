package controllers;

import java.util.InputMismatchException;
import java.util.Scanner;

import enums.Gender;
import enums.Role;
import models.Admin;
import models.Branch;
import models.User;
import services.AdminService;
import utils.ChangePage;
import utils.exceptions.PageBackException;
import views.StaffListView;

public class AdminController {
    
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

            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1:
                        getStaffList();
                        break;
                    case 2: 
                        StaffEditor.manageStaff(user);
                        break;
                    case 3:
                        StaffPromoter.promoteStaff(user);
                        break;
                    case 4:
                        StaffTransfer.transferStaff(user);
                        break;
                    case 5:
                        PaymentEditor.managePayments(user);
                        break;
                    case 6:
                        BranchManagement.branchManagement(user);
                        break;
                    case 7:
                        PasswordChangerPage.changePassword(user);
                        break;
                    case 8:
                        System.out.println("Logging out...");
                        System.out.println("Logged out successfully.");
                        System.out.println("Press <enter> to continue.");
                        new Scanner(System.in).nextLine();
                        Welcome.welcome();
                        break;
                    default:
                        System.out.println("Invalid choice. Please press <enter> to try again.");
                        new Scanner(System.in).nextLine();
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

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        sc.nextLine();
        

        switch (choice) {
            case 1:
                staffListView.display(adminService.getStaffList(), adminService.getBranchList());
                break;
            case 2:
                
                int count = 1;
                Branch[] branches = adminService.getBranchList(); 

                System.out.print("Select Branch to filter by: ");
                for (Branch branch : branches){
                    System.out.println("\t" + count + ". " + branch.getName());
                    count++;
                }
                System.out.print("Enter your choice: ");

                choice = sc.nextInt();
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
                }
                break;

            case 3:
                System.out.println("Select Role to filter by: ");
                System.out.println("\t1. STAFF");
                System.out.println("\t2. MANAGER");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1){
                    staffListView.display(adminService.getStaffList(Role.STAFF), adminService.getBranchList());
                } else if (choice == 2){
                    staffListView.display(adminService.getStaffList(Role.BRANCHMANAGER), adminService.getBranchList());
                } else {
                    System.out.println("Invalid choice. Please press <enter> to return.");
                    sc.nextLine();
                    throw new PageBackException();
                }
                break;
            
            case 4:
                System.out.println("Select Gender to filter by: ");
                System.out.println("\t1. Male");
                System.out.println("\t2. Female");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1){
                    staffListView.display(adminService.getStaffList(Gender.MALE), adminService.getBranchList());
                } else if (choice == 2){
                    staffListView.display(adminService.getStaffList(Gender.FEMALE), adminService.getBranchList());
                } else {
                    System.out.println("Invalid choice. Please press <enter> to return.");
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
                    System.out.println("Invalid input. Age cannot be negative. Please enter a positive age.");
                } else if (age < 18){
                    System.out.println("Nice try, but we don't hire minors. Please enter an age above 18.");
                } else {
                    staffListView.display(adminService.getStaffList(age), adminService.getBranchList());
                }
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input. Press <enter> to return.");
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
                System.out.println("Invalid choice.");
                System.out.println("Press Enter to go back to the previous page.");
                sc.nextLine();
                throw new PageBackException();       
        }            
    }
}
