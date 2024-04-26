package controllers;

import java.util.InputMismatchException;
import java.util.Scanner;

import models.Branch;
import utils.ChangePage;
import services.WelcomeService;

/**
 * Prints the welcome screen and user role selection for the Fastfood ordering and management System (FOMS).
 * Allows users to choose between different roles (Customer, Staff) or to exit the application.
 */
public class Welcome {

    /**
     * The WelcomeService instance to be used for welcome screen operations.
     */
    private static WelcomeService welcomeService = new WelcomeService();
    /**
     * The scanner to be used for user input.
     */
    private static Scanner sc = new Scanner(System.in);

    /**
     * Provides an interactive welcome screen for the Fastfood ordering and management System (FOMS).
     * It prompts users to choose between different roles (Customer, Staff) or to exit the application.
     * Depending on the user's choice, it directs them to the appropriate controller for further actions.
     * This method uses a loop to continuously display the menu until the user decides to exit. It handles
     * invalid input gracefully by prompting the user to try again.
     *
     * @throws Exception Throws an exception if there are issues during the execution, such as input/output errors.
     */
    public static void welcome() throws Exception {
        int choice;
		int flag = 0;

        welcomeService.refresh();
        
		try {
			while (flag == 0) {
                
                ChangePage.changePage();
                System.out.println("Welcome to the Fastfood ordering and management System (FOMS)! \n");
                System.out.println("Please enter your choice to continue.");
                System.out.println("\t1. Customer");
                System.out.println("\t2. Staff");
                System.out.println("\t3. Exit");
				System.out.println("");
				System.out.print("Enter your choice: ");

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
                        welcomeService.refresh();
                        ChangePage.changePage();
                        System.out.println("Select Branch: ");
                        int count = 1;
                        int branchID;
                        Branch[] branches = welcomeService.getBranchList();
                        for (Branch branch : branches) {
                            System.out.println("\t" + count + ". " + branch.getName());
                            count++;
                        }
                        System.out.print("Enter your choice: ");
                        try {
                            choice = sc.nextInt();
                        } catch (InputMismatchException ime) {
                            System.out.println("Invalid input. Press <enter> to try again.");
                            sc.nextLine();
                            sc.nextLine();
                            continue;
                        }
                        sc.nextLine();
                        if (choice > 0 && choice <= branches.length) {
                            Branch newBranch = branches[choice - 1];
                            branchID = newBranch.getID();
                            CustomerController.customerMainPage(branchID);
                        } else {
                            System.out.println("Invalid choice. Press <enter> to go back.");
                            sc.nextLine();
                        }
                        welcomeService.refresh();
						break;
					case 2:
                        welcomeService.refresh();
                        ChangePage.changePage();						
						LoginController.login();
                        welcomeService.refresh();
						break;
					case 3:
						flag = 1;
                        welcomeService.refresh();
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
