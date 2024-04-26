package interfaces;

import models.Branch;
import models.BranchUser;

/**
 * The IStaffView interface provides methods to display staff details.
 */
public interface IStaffView {

    /**
     * Displays the staff list and branch list.
     *
     * @param StaffList  an array of BranchUser objects representing the staff list
     * @param BranchList an array of Branch objects representing the branch list
     */
    public void display(BranchUser[] StaffList, Branch[] BranchList);
}
