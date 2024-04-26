package views;

import interfaces.IStaffView;
import models.Branch;
import models.BranchUser;

/**
 * The {@link StaffListView} class implements the IStaffView interface and is responsible for displaying staff details.
 */
public class StaffListView implements IStaffView{

    /**
     * Displays the details of the given staff list. The Branch List is used to obtain the branch name from the branch ID.
     *
     * @param StaffList an array of BranchUser objects representing the staff list
     * @param BranchList an array of Branch objects representing the branches
     */
    public void display(BranchUser[] StaffList, Branch[] BranchList){
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
