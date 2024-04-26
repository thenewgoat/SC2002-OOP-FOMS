package interfaces;

import models.Branch;

/**
 * The IStaffUpdateChecker interface provides a method for checking staff addition, removal and movements.
 */
public interface IStaffUpdateChecker {

    /**
     * Checks if the staff update is valid based on the given parameters.
     *
     * @param staffCount   the total count of staff members
     * @param managerCount the count of managers
     * @param branch       the branch where the staff update is being checked
     * @return true if the staff update is valid, false otherwise
     */
    public boolean check(int staffCount, int managerCount, Branch branch);

}
