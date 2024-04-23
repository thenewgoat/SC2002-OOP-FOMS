package services;

import java.util.NoSuchElementException;

import interfaces.IAuthorisationService;
import models.Account;
import stores.PasswordStorage;


/**
 * The AuthorisationService class provides methods for user authentication and login.
 * It implements the IAuthorisationService interface.
 */
public class AuthorisationService implements IAuthorisationService {
    
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
}
