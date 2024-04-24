package views;

import interfaces.IBranchUserView;
import models.BranchUser;

public class BranchUserView implements IBranchUserView{

    @Override
    public void displayBranchUserDetails(BranchUser user, String branchName) {
        System.out.println("Name: " + user.getName());
        System.out.println("Login ID: " + user.getLoginID());
        System.out.println("Branch: " + branchName);
        System.out.println("Role: " + user.getRole());
        System.out.println("Gender: " + user.getGender());
        System.out.println("Age: " + user.getAge());
    }
}
