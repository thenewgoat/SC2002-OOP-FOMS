package interfaces;

import models.Branch;
import models.BranchUser;
import models.PaymentMethod;
/**
 * The IAdminService interface provides methods to manage/view staff members, branches, and payment methods.
 */
public interface IAdminService {

    /**
     * Retrieves the list of staff members.
     *
     * @return an array of BranchUser objects representing the staff list.
     */
    public BranchUser[] getStaffList(); 

    /**
     * Retrieves the list of branches.
     *
     * @return an array of Branch objects representing the branch list.
     */
    public Branch[] getBranchList();

    /**
     * Adds a new staff member.
     *
     * @param staff the BranchUser object representing the staff member to be added.
     * @return true if the staff member was successfully added, false otherwise.
     */
    public boolean addStaff(BranchUser staff);

    /**
     * Removes a staff member.
     *
     * @param staff the BranchUser object representing the staff member to be removed.
     * @return true if the staff member was successfully removed, false otherwise.
     */
    public boolean removeStaff(BranchUser staff);

    /**
     * Updates the details of a staff member.
     *
     * @param staff the BranchUser object representing the staff member to be updated.
     * @return true if the staff member was successfully updated, false otherwise.
     */
    public boolean updateStaff(BranchUser staff);

    /**
     * Promotes a staff member to a higher position.
     *
     * @param staff the BranchUser object representing the staff member to be promoted.
     * @return true if the staff member was successfully promoted, false otherwise.
     */
    public boolean promoteStaff(BranchUser staff);

    /**
     * Transfers a list of staff members from one branch to another.
     *
     * @param staffList the array of BranchUser objects representing the staff members to be transferred.
     * @param oldBranch the Branch object representing the old branch.
     * @param newBranch the Branch object representing the new branch.
     * @return true if the staff members were successfully transferred, false otherwise.
     */
    public boolean transferStaff(BranchUser[] staffList, Branch oldBranch, Branch newBranch);

    /**
     * Adds a new branch.
     *
     * @param branch the Branch object representing the branch to be added.
     * @return true if the branch was successfully added, false otherwise.
     */
    public boolean addBranch(Branch branch);

    /**
     * Removes a branch.
     *
     * @param branch the Branch object representing the branch to be removed.
     * @return true if the branch was successfully removed, false otherwise.
     */
    public boolean removeBranch(Branch branch);

    /**
     * Adds a new payment method.
     *
     * @param paymentMethod the PaymentMethod object representing the payment method to be added.
     * @return true if the payment method was successfully added, false otherwise.
     */
    public boolean addPaymentMethod(PaymentMethod paymentMethod);

    /**
     * Removes a payment method.
     *
     * @param paymentMethod the PaymentMethod object representing the payment method to be removed.
     * @return true if the payment method was successfully removed, false otherwise.
     */
    public boolean removePaymentMethod(PaymentMethod paymentMethod);   
}
