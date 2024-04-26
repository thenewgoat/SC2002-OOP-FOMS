package interfaces;

import models.Branch;

/**
 * The IWelcomeService interface provides methods for refreshing and retrieving branch information.
 */
public interface IWelcomeService {

    /**
     * Refreshes the data storage system. 
     */
    public void refresh();

    /**
     * Retrieves the list of branches.
     *
     * @return an array of Branch objects representing the branches.
     */
    public Branch[] getBranchList();
}
