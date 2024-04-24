package services;

import models.Account;
import stores.PasswordStorage;
import utils.exceptions.PasswordIncorrectException;

public class UserService {

    /**
     * Changes the password for the given login ID.
     * @param loginID The login ID of the account.
     * @param oldPassword Current password for validation.
     * @param newPassword New password to set.
     * @return true if the password was changed successfully, false otherwise.
     * @throws PasswordIncorrectException if the old password does not match the current password.
     */
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
}
