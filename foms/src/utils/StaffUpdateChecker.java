package utils;

import models.Branch;
import utils.exceptions.TooFewManagersException;
import utils.exceptions.TooManyManagersException;

public class StaffUpdateChecker {

    // Note to self: 
    // 
    // To close branch, remove all staff, then remove the final manager.
    // To open branch, add one manager first, then add staff.

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
