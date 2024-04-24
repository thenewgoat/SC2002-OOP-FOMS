package views;

import models.Branch;
import models.BranchUser;

public interface IStaffView {

    public void display(BranchUser[] StaffList, Branch[] BranchList);
}
