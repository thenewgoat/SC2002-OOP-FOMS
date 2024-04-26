package views;

import models.Branch;

public class BranchListView {

    public void displayBranchDetails(Branch[] branches) {
        int count = 1;
        for (Branch branch : branches) {
            System.out.println("Branch " + count + ". ");
            System.out.println("\tName: " + branch.getName());
            System.out.println("\tLocation: " + branch.getLocation());
            System.out.println("\tStaff Quota: " + branch.getStaffQuota());
            count++;
        }
    }
}
