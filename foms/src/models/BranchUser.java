package foms.models;

import foms.models.enums.Gender;
import foms.models.enums.Role;

public class BranchUser extends User{
    private String name;
    private String loginID;
    private Role role;
    private Gender gender;
    private int age;
    private int branchID;
    
    public BranchUser(String name, String staffLoginId, Role role, Gender gender, int age, int branchID) {
        this.name = name;
        this.loginID = staffLoginId;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.branchID = branchID;
    }

    public String getName(){
        return this.name;
    }
    public String getLoginID(){
        return this.loginID;
    }
    public Role getRole(){
        return this.role;
    }
    public Gender getGender(){
        return this.gender;
    }
    public int getAge(){
        return this.age;
    }
    public int getBranchID(){
        return this.branchID;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setLoginID(String loginID){
        this.loginID = loginID;
    }
    public void setRole(Role role){
        this.role = role;
    }
    public void setGender(Gender gender){
        this.gender = gender;
    }
    public void setAge(int age){
        this.age = age;
    }
    public void setBranchID(int branchID){
        this.branchID = branchID;
    }

}
