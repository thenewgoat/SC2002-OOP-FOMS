package models;

import enums.Gender;
import enums.Role;

/**
 * This abstract class represents a generic user in the system.
 * It provides the basic framework to handle user properties such as
 * name, login ID, role, gender, and age. The actual implementation
 * of these properties must be provided by subclasses.
 */
public abstract class User {

    /**
     * Gets the name of the user.
     * 
     * @return the name of this user
     */
    abstract public String getName();

    /**
     * Gets the login ID of the user.
     * 
     * @return the login ID of this user
     */
    abstract public String getLoginID();

    /**
     * Gets the role of the user.
     * 
     * @return the role of this user
     */
    abstract public Role getRole();

    /**
     * Gets the gender of the user.
     * 
     * @return the gender of this user
     */
    abstract public Gender getGender();

    /**
     * Gets the age of the user.
     * 
     * @return the age of this user
     */
    abstract public int getAge();

    /**
     * Sets the name of the user.
     * 
     * @param name the new name of this user
     */
    abstract public void setName(String name);

    /**
     * Sets the login ID of the user.
     * 
     * @param loginID the new login ID of this user
     */
    abstract public void setLoginID(String loginID);

    /**
     * Sets the role of the user.
     * 
     * @param role the new role of this user
     */
    abstract public void setRole(Role role);

    /**
     * Sets the gender of the user.
     * 
     * @param gender the new gender of this user
     */
    abstract public void setGender(Gender gender);

    /**
     * Sets the age of the user.
     * 
     * @param age the new age of this user
     */
    abstract public void setAge(int age);
}
