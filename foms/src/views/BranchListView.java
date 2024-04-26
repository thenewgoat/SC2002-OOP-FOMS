package views;

import interfaces.IBranchListView;
import models.Branch;

/**
 * The {@link BranchListView} class implements the IBranchListView interface and is responsible for displaying branch details.
 */
public class BranchListView implements IBranchListView {

    /**
     * Displays the details of the given branches.
     *
     * @param branches an array of Branch objects
     */
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
