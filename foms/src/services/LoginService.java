package services;

import java.util.NoSuchElementException;

import interfaces.IAuthorisationService;
import models.Account;
import models.User;
import stores.PasswordStorage;
import stores.UserStorage;
import utils.exceptions.AccountNotFoundException;
import utils.exceptions.PasswordMismatchException;
import utils.exceptions.PasswordValidationException;


/**
 * The LoginService class provides methods for user authentication and login.
 * It implements the IAuthorisationService interface.
 */
public class LoginService implements IAuthorisationService {
    
    /**
     * Method to login the user.
     * 
     * @param staffLoginID The staff login ID of the user.
     * @param password The password of the user.
     * @return true if the user is authenticated, false otherwise.
     */
    @Override
    public boolean login(String staffLoginID, String password) {
        try {
            Account user = PasswordStorage.get(staffLoginID);
            return authenticate(user, password);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Authenticates the user by comparing the provided password with the stored account password.
     * 
     * @param user The Account object representing the user.
     * @param password The password to be authenticated.
     * @return true if the password is correct, false otherwise.
     */
    protected boolean authenticate(Account user, String password) {
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    /**
     * Method to change the password of the user.
     * 
     * @param loginID The login ID of the user.
     * @param oldPassword The old password of the user.
     * @param newPassword The new password to be set.
     * @return true if the password is changed successfully, false otherwise.
     * @throws AccountNotFoundException If the account is not found.
     * @throws PasswordMismatchException If the old password provided does not match the stored password.
     * @throws PasswordValidationException If the new password is invalid.
     */
    public boolean changePassword(String loginID, String oldPassword, String newPassword) throws AccountNotFoundException, PasswordMismatchException, PasswordValidationException {
        
        User user = UserStorage.get(loginID);

        Account account = findAccountByLoginID(user.getLoginID());
    
        if (account == null) {
            throw new AccountNotFoundException("No account found with login ID: " + user.getLoginID());
        }
        if (!account.getPassword().equals(oldPassword)) {
            throw new PasswordMismatchException("The old password provided is incorrect.");
        }
        if (newPassword == null || newPassword.isEmpty() || newPassword.equals("password") || newPassword.equals(oldPassword)) {
            throw new PasswordValidationException("The new password is invalid. It cannot be null, empty, 'password', or the same as the previous password.");
        }
    
        account.setPassword(newPassword);
        PasswordStorage.update(account);
        return true;
    }


    /**
     * Retrieves the user with the specified login ID.
     *
     * @param loginID the login ID of the user to retrieve
     * @return the User object associated with the login ID, or null if no user is found
     */
    public User getUser(String loginID) {
        return UserStorage.get(loginID);
    }

    /**
     * Retrieves the account with the specified login ID.
     *
     * @param loginID the login ID of the account to retrieve
     * @return the Account object associated with the login ID, or null if no account is found
     */
    public Account getAccount(String loginID) {
        return PasswordStorage.get(loginID);
    }

    /**
     * Finds an account by login ID.
     * 
     * @param loginID The login ID of the account to find.
     * @return The account with the specified login ID, or null if not found.
     */
    public Account findAccountByLoginID(String loginID) {
        for (Account account : PasswordStorage.getAll()) {
            if (account.getLoginID().equals(loginID)) {
                return account;
            }
        }
        return null;
    }
}
