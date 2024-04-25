package services;

import java.util.NoSuchElementException;

import interfaces.IAuthorisationService;
import models.Account;
import models.User;
import stores.PasswordStorage;
import stores.UserStorage;
import utils.exceptions.PasswordIncorrectException;


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

    public boolean changePassword(String loginID, String oldPassword, String newPassword) throws PasswordIncorrectException {
        Account account = PasswordStorage.get(loginID);
        if (account != null && account.getPassword().equals(oldPassword)) {
            account.setPassword(newPassword);
            PasswordStorage.update(account); // Assume a method to update the account in storage
            return true;
        } else {
            throw new PasswordIncorrectException("Old password is incorrect.");
        }
    }

    public User getUser(String loginID) {
        return UserStorage.get(loginID);
    }

    public Account getAccount(String loginID) {
        return PasswordStorage.get(loginID);
    }

}
