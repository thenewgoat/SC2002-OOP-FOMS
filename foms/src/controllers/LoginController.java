package controllers;

import services.AuthorisationService;
import services.UserService;
import models.User;
import enums.Role;
import java.util.Scanner;

public class LoginController {
    private static Scanner scanner = new Scanner(System.in);
    private static AuthorisationService authService = new AuthorisationService();
    private static UserService userService = new UserService();

    public static void login() {
        System.out.println("Please enter your login ID:");
        String loginID = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        boolean authenticated = authService.login(loginID, password);
        if (authenticated) {
            User user = userService.getUserByLoginID(loginID);
            if (user != null) {
                System.out.println("Login successful!");
                if (password.equals("password")) {
                    System.out.println("You must change your default password.");
                    changePassword(user);
                    return;
                }
                redirectToRoleSpecificPage(user);
                return;
            }
        }
        System.out.println("Login failed. Please try again.");
    }

    private static void changePassword(User user) {
        System.out.println("Enter new password:");
        String newPassword = scanner.nextLine();
        try {
            if (userService.changePassword(user.getLoginID(), "password", newPassword)) {
                System.out.println("Password changed successfully.");
                login();
            } else {
                System.out.println("Password change failed. Please try again.");
                changePassword(user);
            }
        } catch (PasswordIncorrectException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void redirectToRoleSpecificPage(User user) {
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
                System.out.println("Invalid role.");
        }
    }
}
