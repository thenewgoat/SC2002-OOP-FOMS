package interfaces;

import models.Branch;

public interface IStaffUpdateChecker {

    public boolean check(int staffCount, int managerCount, Branch branch);

}
