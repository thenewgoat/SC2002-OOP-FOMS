package utils;

import models.Branch;
import utils.exceptions.TooFewManagersException;
import utils.exceptions.TooManyManagersException;

/**
 * The {@link StaffUpdateChecker} class is responsible for checking the validity of staff and manager counts
 * when updating a branch's staff. It ensures that the staff count is within the branch's staff quota
 * and that the corresponding manager count is appropriate based on the staff count.
 */
public class StaffUpdateChecker {

    // Note to self: 
    // 
    // To close branch, remove all staff, then remove the final manager.
    // To open branch, add one manager first, then add staff.

    /**
     * Checks the validity of the provided staff and manager counts for a branch.
     * If the staff count is between 1 and 4, the manager count must be 1.
     * If the staff count is between 5 and 8, the manager count must be 2.
     * If the staff count is between 9 and 15, the manager count must be 3.
     * 
     * To facilitate branch opening and closing, an additional state was considered:
     * If the staff count is 0, the manager count can be 0 or 1.
     * 
     * To close a branch, remove all staff, then remove the final manager.
     * To open a branch, add one manager first, then add staff.
     * 
     * @param staffCount the number of staff to be added to the branch.
     * @param managerCount the number of managers to be added to the branch.
     * @param branch the branch to which the staff and managers are to be added.
     * @return true if the staff and manager counts are valid, false otherwise.
     * @throws IllegalArgumentException if the staff count is negative, the manager count is negative, or the branch is null.
     * @throws IllegalArgumentException if the staff count exceeds the branch's staff quota.
     * @throws IllegalArgumentException if the staff count is invalid.
     * @throws TooFewManagersException if the staff count is valid but the manager count is too low.
     * @throws TooManyManagersException if the staff count is valid but the manager count is too high.
     */
    public static boolean check(int staffCount, int managerCount, Branch branch){

        if (staffCount < 0 || managerCount < 0) {
            throw new IllegalArgumentException("Staff and manager counts must be non-negative. Provided staffCount: " + staffCount + ", managerCount: " + managerCount);
        }
        if (branch == null) {
            throw new IllegalArgumentException("Branch cannot be null.");
        }
        if (staffCount > branch.getStaffQuota()){
            throw new IllegalArgumentException("Staff count exceeds branch staff quota. Provided staffCount: " + staffCount + ", staffQuota: " + branch.getStaffQuota());
        }

        if (staffCount > 0 && staffCount < 5) {
            if (managerCount < 1) {
                throw new TooFewManagersException(null);
            } else if (managerCount > 1) {
                throw new TooManyManagersException(null);
            }
            return true;
        } else if (staffCount > 4 && staffCount < 9) {
            if (managerCount < 2) {
                throw new TooFewManagersException(null);
            } else if (managerCount > 2) {
                throw new TooManyManagersException(null);
            }
            return true;
        } else if (staffCount > 8 && staffCount < 16) {
            if (managerCount < 3) {
                throw new TooFewManagersException(null);
            } else if (managerCount > 3) {
                throw new TooManyManagersException(null);
            }
            return true;
        } else if (staffCount == 0){
            if (managerCount > 1) {
                throw new TooManyManagersException(null);
            }
            return true;
        } else {
            throw new IllegalArgumentException("Invalid staff count: " + staffCount + ". Staff count must be between 1 and 15 with corresponding manager count.");
        }
    };
}
