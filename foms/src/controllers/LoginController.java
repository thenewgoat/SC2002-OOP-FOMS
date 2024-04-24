package controllers;

import services.LoginService;
import models.Account;
import models.User;
import java.util.Scanner;

import utils.exceptions.PageBackException;
import utils.exceptions.PasswordIncorrectException;

public class LoginController {
    private static final Scanner scanner = new Scanner(System.in);


    public static void login() {
        while (true) {
            System.out.println("Please enter your login ID:");
            String loginID = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            LoginService loginService = new LoginService();
            ;

            if (loginService.login(loginID, password)) {
                Account account = loginService.getAccount(loginID);
                System.out.println("Login successful!");
                handlePostLogin(account);
                return;
            }
            System.out.println("Login failed. Press <enter> to try again or type 'exit' to quit.");
            if ("exit".equalsIgnoreCase(scanner.nextLine())) {
                return;
            }
        }
    }

    private static void handlePostLogin(Account account) {
        
        LoginService loginService = new LoginService();

        if (account.getPassword().equals("password")) {  // Example of a default password check
            System.out.println("You must change your default password.");
            changePassword(account);
        } else {
            User user = loginService.getUser(account.getLoginID());
            redirect(user);
        }
    }

    protected static void changePassword(Account account) {

        LoginService loginService = new LoginService();

        System.out.println("Enter new password:");
        String newPassword = scanner.nextLine();

        try {
            if (loginService.changePassword(account.getLoginID(), account.getPassword(), newPassword)) {
                System.out.println("Password changed successfully. Please login again.");
                return;
            } else {
                System.out.println("Password change failed. Please try again.");
                changePassword(account);
            }
        } catch (PasswordIncorrectException e) {
            System.out.println(e.getMessage());
            changePassword(account);
        }
    }

    private static void redirect(User user) {
        try {
            switch (user.getRole()) {
                case STAFF:
                    StaffController.start(user);
                    break;
                case ADMIN:
                    AdminController.start(user);
                    break;
                case BRANCHMANAGER:
                    ManagerController.start(user);
                    break;
                default:
                    System.out.println("Invalid role detected. Contact support.");
                    break;
            }
        } catch (PageBackException e) {
            System.out.println("Failed to redirect: " + e.getMessage());
            return;
        }
    }
}
