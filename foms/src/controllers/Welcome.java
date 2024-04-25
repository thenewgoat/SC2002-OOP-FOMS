package controllers;

import java.util.Scanner;

import models.Branch;
import utils.ChangePage;
import services.AdminService;
import stores.BranchMenuItemStorage;
import stores.BranchStorage;
import stores.BranchUserStorage;
import stores.OrderStorage;
import stores.PasswordStorage;
import stores.PaymentMethodStorage;
import stores.UserStorage;

public class Welcome {

    public static void welcome() throws Exception {
        int choice;
		int flag = 0;

        AdminService adminService = new AdminService();
		
		
        
		try {
			while (flag == 0) {
                BranchUserStorage.save();
                BranchStorage.save();
                UserStorage.save();
                OrderStorage.save();
                PasswordStorage.save();
                PaymentMethodStorage.save();
                BranchMenuItemStorage.save();
                BranchUserStorage.load();
                BranchStorage.load();
                UserStorage.load();
                OrderStorage.load();
                PasswordStorage.load();
                PaymentMethodStorage.load();
                BranchMenuItemStorage.load();


                ChangePage.changePage();
                System.out.println("Welcome to the Fastfood ordering and management System (FOMS)! \n");
                System.out.println("Please enter your choice to continue.");
                System.out.println("\t1. Customer");
                System.out.println("\t2. Staff");
                System.out.println("\t3. Exit");
				System.out.println("");
				System.out.println("Enter your choice: ");
				Scanner sc = new Scanner(System.in);
				try {
                    choice = sc.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid input. Press <enter> to try again.");
                    sc.nextLine();
                    sc.nextLine();
                    continue;
                }
				sc.nextLine();
				switch (choice) {
					case 1:
                        ChangePage.changePage();
                        System.out.println("Select Branch: ");
                        int count = 1;
                        int branchID;
                        Branch[] branches = adminService.getBranchList();
                        for (Branch branch : branches) {
                            System.out.println("\t" + count + ". " + branch.getName());
                            count++;
                        }
                        System.out.print("Enter your choice: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                        if (choice > 0 && choice <= branches.length) {
                            Branch newBranch = branches[choice - 1];
                            branchID = newBranch.getID();
                            CustomerController.customerMainPage(branchID);
                        } else {
                            System.out.println("Invalid choice. Press <enter> to go back.");
                            sc.nextLine();
                        }
						break;
					case 2:
                        ChangePage.changePage();						
						LoginController.login();
						break;
					case 3:
						flag = 1;
						break;
					default:
						System.out.println("Invalid Option. Press <enter> to try again.");
                        sc.nextLine();
                        break;
				}
			}
		} finally {
            System.out.println("Thank you for using FOMS. Goodbye!");
        }
	}
}
