package controllers;

import java.util.Scanner;

import models.Admin;
import models.Branch;
import utils.ChangePage;
import utils.exceptions.PageBackException;
import services.AdminService;

public class Welcome {

    public static void welcome() throws Exception {
        int choice;
		int flag = 0;

        AdminService adminService = new AdminService();
		
		
        
		try {
			while (flag == 0) {
                ChangePage.changePage();
                System.out.println("Welcome to the Fastfood ordering and management System (FOMS)! \n");
                System.out.println("Please enter your choice to continue.");
                System.out.println("\t1. Customer");
                System.out.println("\t2. Staff");
                System.out.println("\t3. Exit");
				System.out.println("");
				System.out.println("Enter your choice: ");
				Scanner sc = new Scanner(System.in);
				choice = sc.nextInt();
				sc.nextLine();
				switch (choice) {
					case 1:
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
