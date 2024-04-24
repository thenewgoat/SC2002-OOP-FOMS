package interfaces;

import models.Branch;
import models.BranchUser;
import models.PaymentMethod;

public interface IAdminService {

    public BranchUser[] getStaffList();

    public boolean addStaff(BranchUser staff);

    public boolean removeStaff(BranchUser staff);

    public boolean updateStaff(BranchUser staff);

    public boolean addBranch(Branch branch);

    public boolean removeBranch(Branch branch);

    public boolean addPaymentMethod(PaymentMethod paymentMethod);

    public boolean removePaymentMethod(PaymentMethod paymentMethod);   


}
