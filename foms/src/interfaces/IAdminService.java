package interfaces;

import models.Branch;
import models.BranchUser;
import models.PaymentMethod;

public interface IAdminService {

    public BranchUser[] getStaffList();

    public boolean addStaff(BranchUser staff);

    public boolean removeStaff(String staffID);

    public boolean updateStaff(BranchUser staff);

    public boolean promoteStaff(String staffID);

    public boolean transferStaff(String staffID, int branchID);

    public boolean addBranch(Branch branch);

    public boolean removeBranch(int branchID);

    public boolean addPaymentMethod(PaymentMethod paymentMethod);

    public boolean removePaymentMethod(String paymentMethodName);   


}
