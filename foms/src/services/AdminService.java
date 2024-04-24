package services;

import interfaces.IAdminService;
import models.Branch;
import models.BranchUser;
import models.PaymentMethod;
import stores.BranchStorage;
import stores.BranchUserStorage;
import stores.PaymentMethodStorage;

public class AdminService implements IAdminService{

    @Override
    public BranchUser[] getStaffList(){
        return BranchUserStorage.getAll();
    };

    @Override
    public boolean addStaff(BranchUser staff){
        try {
            BranchUserStorage.add(staff);
            return true;
        } catch (Exception e) {
            return false;
        }
    };

    @Override
    public boolean removeStaff(BranchUser staff){
        try {
            BranchUserStorage.remove(staff);
            return true;
        } catch (Exception e) {
            return false;
        }
    };

    @Override
    public boolean updateStaff(BranchUser staff){
        try {
            BranchUserStorage.update(staff);
            return true;
        } catch (Exception e) {
            return false;
        }
    };

    @Override
    public boolean addBranch(Branch branch){
        try {
            BranchStorage.add(branch);
            return true;
        } catch (Exception e) {
            return false;
        }
    };

    @Override
    public boolean removeBranch(Branch branch){
        try {
            BranchStorage.remove(branch);
            return true;
        } catch (Exception e) {
            return false;
        }
    };

    @Override
    public boolean addPaymentMethod(PaymentMethod paymentMethod){
        try {
            PaymentMethodStorage.add(paymentMethod);
            return true;
        } catch (Exception e) {
            return false;
        }
    };

    @Override
    public boolean removePaymentMethod(PaymentMethod paymentMethod){
        try {
            PaymentMethodStorage.remove(paymentMethod);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
