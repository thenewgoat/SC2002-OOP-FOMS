package models;

import java.io.Serializable;

/**
 * The Account class represents a user account in the system.
 * It stores the login ID and password of the account.
 */
public class Account implements Serializable {
    /**
     * The serial version UID used for serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The login ID of the account.
     */
    private String loginID;
    /**
     * The password of the account.
     */
    private String password;

    /**
     * Constructs an Account object with the specified login ID and password.
     *
     * @param loginID  the login ID of the account
     * @param password the password of the account
     */
    public Account(String loginID, String password) {
        this.loginID = loginID;
        this.password = password;
    }

    // Getters and setters

    /**
     * Returns the login ID of the account.
     *
     * @return the login ID
     */
    public String getLoginID() {
        return loginID;
    }

    /**
     * Sets the login ID of the account.
     *
     * @param loginID the login ID to set
     */
    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    /**
     * Returns the password of the account.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the account.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}