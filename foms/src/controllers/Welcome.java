package controllers;

import java.util.Scanner;

import models.Admin;
import models.Branch;
import utils.exceptions.PageBackException;
import services.AdminService;

public class Welcome {

    public static void welcome() {
        int choice;
		int flag = 0;

        AdminService adminService = new AdminService();
		
		System.out.println("Welcome to the Fastfood ordering and management System (FOMS)! \n");
        System.out.println("Please enter your choice to continue.");
        System.out.println("\t1. Customer");
        System.out.println("\t2. Staff");
        System.out.println("\t3. Exit");
        
		try {
			while (flag == 0) {
				System.out.println("");
				System.out.println("Enter your choice: ");
				Scanner sc = new Scanner(System.in);
				choice = sc.nextInt();
				sc.nextLine();
				switch (choice) {
					case 1:
                        System.out.print("Select Branch to transfer to: ");
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
                            System.out.println("Invalid choice. Please try again.");
                        }
						break;
					case 2:						
						LoginController.login();
						break;
					case 3:
						flag = 1;
						System.out.println("Thank you for using FOMS. Goodbye!");
						sc.nextLine();
						break;
					default:
						System.out.println("Invalid Option. Pick another option.");
						break;
				}
			}
		} catch (PageBackException e) {
            welcome();
        }
	}
}
