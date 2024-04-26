package models;

import enums.Gender;
import enums.Role;

/**
 * Represents an administrator in the system. The Admin class extends the abstract User class,
 * providing implementations for all its abstract methods. This class specifically sets the role
 * to ADMIN by default and manages properties such as name, login ID, gender, and age.
 */
public class Admin extends User{
    /**
     * The name of the admin.
     */
    private String name;
    /**
     * The login ID of the admin.
     */
    private String loginID;
    /**
     * The role of the admin. Set to Admin by default.
     */
    private Role role = Role.ADMIN;  // Default role for Admin
    /**
     * The gender of the admin.
     */
    private Gender gender;
    /**
     * The age of the admin.
     */
    private int age;

    /**
     * Constructs a new Admin with the specified details.
     * 
     * @param name The name of the admin.
     * @param staffLoginId The login ID of the admin.
     * @param gender The gender of the admin.
     * @param age The age of the admin.
     */
    public Admin(String name, String staffLoginId, Gender gender, int age) {
        this.name = name;
        this.loginID = staffLoginId;
        this.gender = gender;
        this.age = age;
    }

    /**
     * Returns the name of the admin.
     * 
     * @return the name of this admin
     */
    @Override
    public String getName(){
        return this.name;
    }

    /**
     * Returns the login ID of the admin.
     * 
     * @return the login ID of this admin
     */
    @Override
    public String getLoginID(){
        return this.loginID;
    }

    /**
     * Returns the role of the admin. The role is always ADMIN.
     * 
     * @return the role of this admin
     */
    @Override
    public Role getRole(){
        return this.role;
    }

    /**
     * Returns the gender of the admin.
     * 
     * @return the gender of this admin
     */
    @Override
    public Gender getGender(){
        return this.gender;
    }

    /**
     * Returns the age of the admin.
     * 
     * @return the age of this admin
     */
    @Override
    public int getAge(){
        return this.age;
    }
    
    /**
     * Sets the name of the admin.
     * 
     * @param name the new name of this admin
     */
    @Override
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the login ID of the admin.
     * 
     * @param loginID the new login ID of this admin
     */
    @Override
    public void setLoginID(String loginID){
        this.loginID = loginID;
    }

    /**
     * Sets the role of the admin. This implementation allows changing the admin's role,
     * although typically it should remain set to ADMIN.
     * 
     * 
     * @param role the new role of this admin
     */
    @Override
    public void setRole(Role role){
        this.role = role;
    }

    /**
     * Sets the gender of the admin.
     * 
     * @param gender the new gender of this admin
     */
    @Override
    public void setGender(Gender gender){
        this.gender = gender;
    }

    /**
     * Sets the age of the admin.
     * 
     * @param age the new age of this admin
     */
    @Override
    public void setAge(int age){
        this.age = age;
    }
}
