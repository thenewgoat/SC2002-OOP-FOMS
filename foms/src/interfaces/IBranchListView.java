package interfaces;

import models.Branch;

/**
 * The IBranchListView interface provides methods to display branch details.
 */
public interface IBranchListView {

    /**
     * Displays the details of the given branches.
     *
     * @param branches an array of Branch objects representing the branches to be displayed
     */
    public void displayBranchDetails(Branch[] branches);

}