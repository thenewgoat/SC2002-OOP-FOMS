package controllers;

import services.LoginService;
import models.Account;
import models.User;
import java.util.Scanner;
import utils.exceptions.AccountNotFoundException;
import utils.exceptions.PageBackException;
import utils.exceptions.PasswordMismatchException;
import utils.exceptions.PasswordValidationException;

/**
 * Handles user authentication and post-login redirection based on user role.
 */
public class LoginController {
    /**
     * The scanner to be used for user input.
     */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Initiates the login process. Continuously prompts for user credentials until login succeeds or the user decides to exit.
     */
    public static void login() {
        while (true) {
            System.out.println("Please enter your login ID:");
            String loginID = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            LoginService loginService = new LoginService();

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

    /**
     * Handles actions to be taken immediately after successful login, such as
     * prompting for password change if the default password is detected or redirecting
     * the user based on their role.
     *
     * @param account The account of the user who has just logged in.
     */
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

    /**
     * Facilitates changing of a user's password.
     *
     * @param account The user's account for which the password is to be changed.
     */
    protected static void changePassword(Account account) {
        LoginService loginService = new LoginService();
        System.out.println("Enter new password:");
        String newPassword = scanner.nextLine();

        try {
            if (loginService.changePassword(account.getLoginID(), account.getPassword(), newPassword)) {
                System.out.println("Password changed successfully. Please login again.");
            }
        } catch (PasswordMismatchException e) {
            System.out.println("Password change failed. " + e.getMessage());
            changePassword(account);
        } catch (AccountNotFoundException e) {
            System.out.println("Password change failed. " + e.getMessage());
            changePassword(account);
        } catch (PasswordValidationException e) {
            System.out.println("Password change failed. " + e.getMessage());
            changePassword(account);
        }
    }

    /**
     * Redirects the user to the appropriate controller based on their role.
     * 
     * @param user The user to be redirected.
     */
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
