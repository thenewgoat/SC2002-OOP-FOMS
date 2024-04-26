package interfaces;

import models.BranchUser;

/**
 * The IBranchUserView interface provides methods to display branch user details.
 */
public interface IBranchUserView {

    /**
     * Displays the branch user details along with the branch name.
     * 
     * @param branchUser The branch user object containing the user details.
     * @param branchName The name of the branch.
     */
    public void displayBranchUserDetails(BranchUser branchUser, String branchName);
}
