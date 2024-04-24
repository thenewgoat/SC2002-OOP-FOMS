package views;

import models.Branch;
import models.BranchUser;
import stores.BranchStorage;

public class StaffListView implements IStaffView{

    public static void display(BranchUser[] StaffList, Branch[] BranchList){
        System.out.println("======= Staff List =======");
        System.out.println("No. of Staff: " + StaffList.length);
        System.out.println("-------------------------------");
        for (BranchUser branchUser : StaffList){
            System.out.println("Name: " + branchUser.getName());
            System.out.println("Login ID: " + branchUser.getLoginID());
            System.out.println("Role: " + branchUser.getRole());
            System.out.println("Branch ID: " + branchUser.getBranchID());
            for (Branch branch : BranchList){
                if (branch.getID() == branchUser.getBranchID()){
                    System.out.println("Branch Name: " + branch.getName());
                    break;
                }
            }
            System.out.println("Age: " + branchUser.getAge());
            System.out.println("Gender: " + branchUser.getGender());
            System.out.println("-------------------------------");
        }
    }
}
