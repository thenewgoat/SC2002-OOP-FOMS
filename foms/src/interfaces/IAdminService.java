package interfaces;

import enums.Gender;
import enums.Role;
import models.Branch;
import models.BranchUser;
import models.PaymentMethod;
import models.User;
import utils.exceptions.AccountNotFoundException;
import utils.exceptions.PasswordMismatchException;
import utils.exceptions.PasswordValidationException;
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

    /**
     * Changes the password for a given user.
     *
     * @param user         the user whose password needs to be changed
     * @param oldPassword  the current password of the user
     * @param newPassword  the new password to be set for the user
     * @throws PasswordMismatchException     if the old password provided does not match the user's current password
     * @throws AccountNotFoundException     if the user account is not found
     * @throws PasswordValidationException  if the new password does not meet the required validation rules
     */
    public void changePassword(User user, String oldPassword, String newPassword) throws PasswordMismatchException, AccountNotFoundException, PasswordValidationException;
    
    /**
     * Creates a new branch with the given details.
     *
     * @param branchName     the name of the branch
     * @param branchLocation the location of the branch
     * @param staffQuota     the maximum number of staff members allowed in the branch
     * @return true if the branch was successfully created, false otherwise.
     */
    public boolean createBranch(String branchName, String branchLocation, int staffQuota);

    /**
     * Retrieves the list of payment methods.
     *
     * @return an array of PaymentMethod objects representing the payment method list.
     */
    public PaymentMethod[] getPaymentMethods();

    /**
     * Finds a staff member by their login ID.
     *
     * @param staffLoginId the login ID of the staff member to find
     * @return the BranchUser object representing the found staff member, or null if not found.
     */
    public BranchUser findStaffByLoginID(String staffLoginId);

    /**
     * Retrieves the list of staff members in the selected branch.
     *
     * @param selectedBranch the Branch object representing the selected branch
     * @return an array of BranchUser objects representing the staff list in the selected branch.
     */
    public BranchUser[] getStaffList(Branch selectedBranch);

    /**
     * Retrieves the list of staff members with the specified role.
     *
     * @param staff the role of the staff members to retrieve
     * @return an array of BranchUser objects representing the staff list with the specified role.
     */
    public BranchUser[] getStaffList(Role staff);

    /**
     * Retrieves the list of staff members with the specified gender.
     *
     * @param gender the gender of the staff members to retrieve
     * @return an array of BranchUser objects representing the staff list with the specified gender.
     */
    public BranchUser[] getStaffList(Gender gender);

    /**
     * Retrieves the list of staff members with the specified age.
     *
     * @param age the age of the staff members to retrieve
     * @return an array of BranchUser objects representing the staff list with the specified age.
     */
    public BranchUser[] getStaffList(int age);   
}
