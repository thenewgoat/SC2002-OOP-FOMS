package models;

import enums.Gender;
import enums.Role;

/**
 * Represents a branch user in the system.
 * Inherits from the User class.
 */
public class BranchUser extends User{
    /**
     * The name of the branch user.
     */
    private String name;
    
    /**
     * The login ID of the branch user.
     */
    private String loginID;
    
    /**
     * The role of the branch user.
     */
    private Role role;
    
    /**
     * The gender of the branch user.
     */
    private Gender gender;
    
    /**
     * The age of the branch user.
     */
    private int age;
    
    /**
     * The ID of the branch the user belongs to.
     */
    private int branchID;
    
    /**
     * Constructs a new BranchUser object with the specified properties.
     * 
     * @param name The name of the branch user.
     * @param staffLoginId The login ID of the branch user.
     * @param role The role of the branch user.
     * @param gender The gender of the branch user.
     * @param age The age of the branch user.
     * @param branchID The ID of the branch the user belongs to.
     */
    public BranchUser(String name, String staffLoginId, Role role, Gender gender, int age, int branchID) {
        this.name = name;
        this.loginID = staffLoginId;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.branchID = branchID;
    }

    /**
     * Gets the name of the branch user.
     * 
     * @return The name of the branch user.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets the login ID of the branch user.
     * 
     * @return The login ID of the branch user.
     */
    public String getLoginID(){
        return this.loginID;
    }

    /**
     * Gets the role of the branch user.
     * 
     * @return The role of the branch user.
     */
    public Role getRole(){
        return this.role;
    }

    /**
     * Gets the gender of the branch user.
     * 
     * @return The gender of the branch user.
     */
    public Gender getGender(){
        return this.gender;
    }

    /**
     * Gets the age of the branch user.
     * 
     * @return The age of the branch user.
     */
    public int getAge(){
        return this.age;
    }

    /**
     * Gets the ID of the branch the user belongs to.
     * 
     * @return The ID of the branch the user belongs to.
     */
    public int getBranchID(){
        return this.branchID;
    }

    /**
     * Sets the name of the branch user.
     * 
     * @param name The name of the branch user.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the login ID of the branch user.
     * 
     * @param loginID The login ID of the branch user.
     */
    public void setLoginID(String loginID){
        this.loginID = loginID;
    }

    /**
     * Sets the role of the branch user.
     * 
     * @param role The role of the branch user.
     */
    public void setRole(Role role){
        this.role = role;
    }

    /**
     * Sets the gender of the branch user.
     * 
     * @param gender The gender of the branch user.
     */
    public void setGender(Gender gender){
        this.gender = gender;
    }

    /**
     * Sets the age of the branch user.
     * 
     * @param age The age of the branch user.
     */
    public void setAge(int age){
        this.age = age;
    }

    /**
     * Sets the ID of the branch the user belongs to.
     * 
     * @param branchID The ID of the branch the user belongs to.
     */
    public void setBranchID(int branchID){
        this.branchID = branchID;
    }
}
