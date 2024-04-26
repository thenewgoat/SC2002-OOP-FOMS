package services;

import java.util.Arrays;
import enums.Role;
import interfaces.IAdminService;
import models.Account;
import models.Branch;
import models.BranchMenuItem;
import models.BranchUser;
import models.Order;
import models.PaymentMethod;
import models.User;
import stores.BranchMenuItemStorage;
import stores.BranchStorage;
import stores.BranchUserStorage;
import stores.OrderStorage;
import stores.PasswordStorage;
import stores.PaymentMethodStorage;
import stores.UserStorage;
import utils.StaffUpdateChecker;
import utils.exceptions.AccountNotFoundException;
import utils.exceptions.PasswordMismatchException;
import utils.exceptions.PasswordValidationException;
import utils.exceptions.TooFewManagersException;
import utils.exceptions.TooManyManagersException;
import enums.Gender;

/**
 * The AdminService class provides administrative functionalities related to the management of staff, branches, and payment methods for FOMS.
 * 
 * It implements the IAdminService interface, offering methods to add, remove, and update staff records, manage branch operations, and handle payment methods.
 * 
 * This class serves as a central point for administrative actions that affect the staffing and operational capabilities of branches, ensuring that business rules and constraints are adhered to during various management tasks.
 */
public class AdminService implements IAdminService{

    
    /**
     * Retrieves a list of all staff members in the system.
     * 
     * @return an array of BranchUser objects representing all staff members in the system
     */
    @Override
    public BranchUser[] getStaffList() {
        return BranchUserStorage.getAll();
    }

    /**
     * Retrieves a list of staff members in the system based on their age.
     * 
     * @param Age the age of the staff members to be retrieved
     * @return an array of BranchUser objects representing staff members with the specified age
     */
    public BranchUser[] getStaffList(int Age){
        return Arrays.stream(BranchUserStorage.getAll())
                     .filter(user -> user.getAge() == Age)
                     .toArray(BranchUser[]::new);
    }

    /**
     * Retrieves a list of staff members in the system based on their branch.
     * 
     * @param branch the branch of the staff members to be retrieved
     * @return an array of BranchUser objects representing staff members in the specified branch
     */
    public BranchUser[] getStaffList(Branch branch) {
        return Arrays.stream(BranchUserStorage.getAll())
                         .filter(user -> user.getBranchID() == branch.getID())
                         .toArray(BranchUser[]::new);
    }

    /**
     * Retrieves a list of staff members in the system based on their role.
     * 
     * @param role the role of the staff members to be retrieved
     * @return an array of BranchUser objects representing staff members with the specified role
     */
    public BranchUser[] getStaffList(Role role) {
        // Filter by role
        return Arrays.stream(BranchUserStorage.getAll())
                     .filter(user -> user.getRole() == role)
                     .toArray(BranchUser[]::new);
    }

    /**
     * Retrieves a list of staff members in the system based on their gender.
     * 
     * @param gender the gender of the staff to be retrieved
     * @return an array of BranchUser objects representing staff members with the specified gender
     */
    public BranchUser[] getStaffList(Gender gender) {
        // Filter by gender
        return Arrays.stream(BranchUserStorage.getAll())
                     .filter(user -> user.getGender() == gender)
                     .toArray(BranchUser[]::new);
    }

    /**
     * Retrieves a list of all branches.
     * 
     * @return an array of Branch objects containing all branches.
     */
    public Branch[] getBranchList(){
        return BranchStorage.getAll();
    };

    
    /**
     * Adds a new staff member to the system.
     * 
     * The staff member is only added to the system if the staff quota for the branch is not exceeded and the branch has the required manager to staff ratio.
     * 
     * An account is also created for the new staff member with a default password of "password".
     * 
     * @param staff the staff member to be added
     * @return true if the staff member was added successfully, false otherwise
     * @throws IllegalArgumentException if the staff role is neither BRANCHMANAGER nor STAFF.
     */
    @Override
    public boolean addStaff(BranchUser staff){

        Account[] staffList = PasswordStorage.getAll();
        for (Account account : staffList){
            if (account.getLoginID() == staff.getLoginID()){
                System.out.println("Staff already exists.");
                return false;
            }
        }

        int staffCount = 0;
        int managerCount = 0;
        Branch branch = BranchStorage.get(staff.getBranchID());

        if (staff.getRole() == Role.BRANCHMANAGER){
            for (BranchUser branchUser : BranchUserStorage.getAll()){
                if (branchUser.getBranchID() == staff.getBranchID()){
                    if (branchUser.getRole() == Role.BRANCHMANAGER){
                        managerCount++;
                    }
                    else if (branchUser.getRole() == Role.STAFF){
                        staffCount++;
                    }
                }
            }
            managerCount++;
        } else if (staff.getRole() == Role.STAFF){
            for (BranchUser branchUser : BranchUserStorage.getAll()){
                if (branchUser.getBranchID() == staff.getBranchID()){
                    if (branchUser.getRole() == Role.BRANCHMANAGER){
                        managerCount++;
                    }
                    else if (branchUser.getRole() == Role.STAFF){
                        staffCount++;
                    }
                }
            }
            staffCount++;
        } else {
            throw new IllegalArgumentException("Staff role must be either BRANCHMANAGER or STAFF.");
        }

        if (staffCount > branch.getStaffQuota()){
            System.out.println("Staff quota exceeded.");
            return false;
        }

        try {
            StaffUpdateChecker.check(staffCount, managerCount, branch);
            createAccount(staff);
            BranchUserStorage.add(staff);
            UserStorage.add(staff);          
            System.out.println("Employee added successfully.");
            return true;
        } catch (TooFewManagersException e) {
            System.out.println("Addition blocked as there will be too many staff in the branch.");
            System.out.println("Try adding new staff to other branches.");
            return false;
        } catch (TooManyManagersException e) {
            System.out.println("Addition blocked as there will be too many managers in the branch.");
            System.out.println("Try adding new manager to other branches.");
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    };

    /**
     * Removes a staff member from the system.
     * 
     * Similar to addition, the staff member is only removed if the branch has the required manager to staff ratio after the removal.
     * 
     * The staff member's account is also deleted from the system.
     * 
     * @param staff the staff member to be removed
     * @return true if the staff member was successfully removed, false otherwise
     */
    @Override
    public boolean removeStaff(BranchUser staff){
        for (BranchUser branchUser : BranchUserStorage.getAll()){
            if (branchUser.getLoginID() == staff.getLoginID()){
                
                int staffCount = 0;
                int managerCount = 0;


                for (BranchUser curBranchUser : BranchUserStorage.getAll()){
                    if (curBranchUser.getBranchID() == staff.getBranchID()){
                        if (curBranchUser.getRole() == Role.BRANCHMANAGER){
                            managerCount++;
                        }
                        else if (curBranchUser.getRole() == Role.STAFF){
                            staffCount++;
                        }
                    }
                }



                if (Role.BRANCHMANAGER == staff.getRole()){
                    managerCount--;
                }
                else if (Role.STAFF == staff.getRole()){
                    staffCount--;
                }


                try {
                    StaffUpdateChecker.check(staffCount, managerCount, BranchStorage.get(staff.getBranchID()));
                    deleteAccount(staff);
                    BranchUserStorage.remove(staff);
                    UserStorage.remove(staff);
                    return true;
                } catch (TooFewManagersException e) {
                    System.out.println("Removal blocked as there will be insufficient managers in the branch.");
                    return false;
                } catch (TooManyManagersException e) {
                    System.out.println("Removal blocked as there will be insufficient staff in the branch.");
                    return false;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
            }
        }
        System.out.println("Staff does not exist.");
        return false;
    };


    /**
     * Updates the information of a staff member.
     * 
     * Only to be called when editing Name, Age and Gender.
     * 
     * Role and BranchID are not allowed to be changed without checks.
     * 
     * Staff ID is not allowed to be changed.
     * 
     * @param staff the BranchUser object representing the staff member to be updated
     * @return true if the staff member was successfully updated, false otherwise
     */
    @Override
    public boolean updateStaff(BranchUser staff){
        try {
            BranchUserStorage.update(staff);
            return true;
        } catch (Exception e) {
            return false;
        }
    };

    /**
     * Promotes a staff member to a branch manager.
     * 
     * The promotion is only successful if the branch has the required manager to staff ratio after the promotion.
     * 
     * @param staff the staff member to be promoted
     * @return true if the promotion is successful, false otherwise
     */
    @Override
    public boolean promoteStaff(BranchUser staff){

        if (staff.getRole() == Role.BRANCHMANAGER){
            System.out.println("Staff is already a manager.");
            return false;
        }

        int staffCount = 0;
        int managerCount = 0;
        Branch branch = BranchStorage.get(staff.getBranchID());

        for (BranchUser branchUser : BranchUserStorage.getAll()){
            if (branchUser.getBranchID() == staff.getBranchID()){
                if (branchUser.getRole() == Role.BRANCHMANAGER){
                    managerCount++;
                }
                else if (branchUser.getRole() == Role.STAFF){
                    staffCount++;
                }
            }
        }

        try {
            StaffUpdateChecker.check(staffCount - 1, managerCount + 1, branch);
            staff.setRole(Role.BRANCHMANAGER);
            return true;
        } catch (TooFewManagersException e) {
            System.out.println("Promotion blocked as there will be insufficient staff in the branch.");
            return false;
        } catch (TooManyManagersException e) {
            System.out.println("Promotion blocked as there will be too many managers in the branch.");
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    };


    /**
     * Transfers staff members from one branch to another.
     * 
     * The transfer is only successful if the staff members can be accommodated in the new branch without exceeding the staff quota and the required manager to staff ratio is maintained in both branches.
     *
     * Can support transferring one or multiple staff members at once.
     * 
     * @param staffList   an array of BranchUser objects representing the staff members to be transferred
     * @param oldBranch   the branch from which the staff members are being transferred
     * @param newBranch   the branch to which the staff members are being transferred
     * @return true if the transfer is successful, false otherwise
     * @throws IllegalArgumentException if the staff role is neither BRANCHMANAGER nor STAFF
     */
    @Override
    public boolean transferStaff(BranchUser[] staffList, Branch oldBranch, Branch newBranch){

        int oldStaffCount = 0;
        int oldManagerCount = 0;
        int newStaffCount = 0;
        int newManagerCount = 0;
        int transferringManagers = 0;
        int transferringStaff = 0;
    
        // First, count current staff and managers in both branches
        for (BranchUser branchUser : BranchUserStorage.getAll()) {
            if (branchUser.getBranchID() == oldBranch.getID()) {
                if (branchUser.getRole() == Role.BRANCHMANAGER) {
                    oldManagerCount++;
                } else if (branchUser.getRole() == Role.STAFF) {
                    oldStaffCount++;
                }
            } else if (branchUser.getBranchID() == newBranch.getID()) {
                if (branchUser.getRole() == Role.BRANCHMANAGER) {
                    newManagerCount++;
                } else if (branchUser.getRole() == Role.STAFF) {
                    newStaffCount++;
                }
            }
        }
    
        // Count the number of managers and staff being transferred
        for (BranchUser staff : staffList) {
            if (staff == null) {
                break;
            }
            if (staff.getRole() == Role.BRANCHMANAGER) {
                transferringManagers++;
            } else if (staff.getRole() == Role.STAFF) {
                transferringStaff++;
            } else {
                throw new IllegalArgumentException("Staff role must be either BRANCHMANAGER or STAFF.");
            }
        }
    
        // Perform checks for the old branch after removing transferred staff
        try {
            StaffUpdateChecker.check(oldStaffCount - transferringStaff, oldManagerCount - transferringManagers, oldBranch);
        } catch (TooFewManagersException e){
            System.out.println("Too Few Managers in old branch! Transfer failed.");
            return false;
        } catch (TooManyManagersException e){
            System.out.println("Too Many Managers in old branch! Transfer failed.");
            return false;
        }
    
        // Perform checks for the new branch after adding transferred staff
        try {
            StaffUpdateChecker.check(newStaffCount + transferringStaff, newManagerCount + transferringManagers, newBranch);
        } catch (TooFewManagersException e){
            System.out.println("Too Few Managers in new branch! Transfer failed.");
            return false;
        } catch (TooManyManagersException e){
            System.out.println("Too Many Managers in new branch! Transfer failed.");
            return false;
        }
        
        if (newStaffCount > newBranch.getStaffQuota()) {
            System.out.println("Staff quota exceeded in new branch. Transfer failed.");
            return false;
        }

        // Set new branch ID for each staff being transferred
        for (BranchUser staff : staffList) {
            if (staff == null) {
                break;
            }
            staff.setBranchID(newBranch.getID());
        }
        return true;
    }

    /**
     * Adds a new branch to the system.
     * 
     * Duplicate branch names are not allowed.
     * 
     * @param branch the branch to be added
     * @return true if the branch was successfully added, false if the branch already exists
     */
    @Override
    public boolean addBranch(Branch branch){
        for (Branch curBranch : BranchStorage.getAll()){
            if (curBranch.getName().equals(branch.getName())){
                System.out.println("Branch already exists. Enter a unique branch name.");
                return false;
            }
        }
        BranchStorage.add(branch);
        return true;
    };

    /**
     * Removes a branch from the system.
     * This method removes the branch from the branch storage, deletes all associated orders and branch menu items.
     * If the branch does not exist, it prints a message and returns false.
     * 
     * If branch still contains staff, it will send a warning before removing all staff from the branch and system.
     *
     * @param branch the branch to be removed
     * @return true if the branch is successfully removed, false otherwise
     */
    @Override
    public boolean removeBranch(Branch branch){
        for (BranchUser branchuser : getStaffList()){
            if (branchuser.getBranchID() == branch.getID()){
                deleteAccount(branchuser);
                BranchUserStorage.remove(branchuser);
                UserStorage.remove(branchuser);
            }
        }
        for(Branch curBranch : BranchStorage.getAll()){
            if (curBranch.getID() == branch.getID()){
                BranchStorage.remove(branch);
                for (Order order : OrderStorage.getAll()){
                    if (order.getBranchID() == branch.getID()){
                        OrderStorage.remove(order);
                    }
                }
                for (BranchMenuItem item : BranchMenuItemStorage.getAll()){
                    if (item.getBranchID() == branch.getID()){
                        BranchMenuItemStorage.remove(item);
                    }
                }
                return true;
            }
        }
        System.out.println("Branch does not exist.");
        return false;
    };

    /**
     * Adds a payment method to the system.
     * 
     * Duplicate payment names are not allowed, even across different payment types.
     *
     * @param paymentMethod the payment method to be added
     * @return true if the payment method was successfully added, false otherwise
     */
    @Override
    public boolean addPaymentMethod(PaymentMethod paymentMethod){

        if (paymentMethod == null){
            System.out.println("Payment method cannot be null.");
            return false;
        }
        for (PaymentMethod curPaymentMethod : PaymentMethodStorage.getAll()){
            if (curPaymentMethod.getPaymentMethod().equals(paymentMethod.getPaymentMethod())){
                System.out.println("Payment method already exists.");
                return false;
            }
        }
        PaymentMethodStorage.add(paymentMethod);
        return true;
    };


    /**
     * Removes a payment method from the system.
     * 
     * @param paymentMethod the payment method to be removed
     * @return true if the payment method was successfully removed, false otherwise
     */
    @Override
    public boolean removePaymentMethod(PaymentMethod paymentMethod){
        for (PaymentMethod curPaymentMethod : PaymentMethodStorage.getAll()){
            if (curPaymentMethod.getPaymentMethod().equals(paymentMethod.getPaymentMethod())){
                PaymentMethodStorage.remove(paymentMethod);
                return true;
            }
        }
        System.out.println("Payment method does not exist.");
        return false;
    }


    /**
     * Finds a payment method by its name.
     *
     * @param paymentMethod the name of the payment method to find
     * @return the found PaymentMethod object, or null if not found
     */
    public PaymentMethod findPaymentMethod(String paymentMethod){
        for (PaymentMethod curPaymentMethod : PaymentMethodStorage.getAll()){
            if (curPaymentMethod.getPaymentMethod().equals(paymentMethod)){
                return curPaymentMethod;
            }
        }
        return null;
    }

    /**
     * Finds a staff member by their login ID.
     *
     * @param staffLoginID the login ID of the staff member to find
     * @return the found BranchUser object, or null if not found
     */
    public BranchUser findStaffByLoginID(String staffLoginID) {
        for (BranchUser staff : getStaffList()) {
            if (staff.getLoginID().equals(staffLoginID)) {
                return staff;
            }
        }
        return null;
    }

    /**
     * Finds a branch by its ID.
     *
     * @param branchID the ID of the branch to find
     * @return the found Branch object, or null if not found
     */
    public Branch findBranchById(int branchID) {
        for (Branch branch : getBranchList()) {
            if (branch.getID() == branchID) {
                return branch;
            }
        }
        return null;
    }

    /**
     * Finds an account by the loginID/staff ID.
     *
     * @param loginID the login ID of the account to find
     * @return the found Account object, or null if not found
     */
    public Account findAccountByLoginID(String loginID) {
        for (Account account : PasswordStorage.getAll()) {
            if (account.getLoginID().equals(loginID)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Changes the password of a staff member.
     * 
     * @param user the staff member whose password is to be changed
     * @param oldPassword the old password of the staff member
     * @param newPassword the new password to be set
     * @throws AccountNotFoundException if the account is not found
     * @throws PasswordMismatchException if the old password provided does not match the account's current password
     * @throws PasswordValidationException if the new password is invalid
     */
    public void changePassword(User user, String oldPassword, String newPassword) throws AccountNotFoundException, PasswordMismatchException, PasswordValidationException {
        Account account = findAccountByLoginID(user.getLoginID());
    
        if (account == null) {
            throw new AccountNotFoundException("No account found with login ID: " + user.getLoginID());
        }
        if (!account.getPassword().equals(oldPassword)) {
            throw new PasswordMismatchException("The old password provided is incorrect.");
        }
        if (newPassword == null || newPassword.isEmpty() || newPassword.equals("password") || newPassword.equals(oldPassword)) {
            throw new PasswordValidationException("The new password is invalid. It cannot be null, empty, 'password', or the same as the previous password.");
        }
    
        account.setPassword(newPassword);
        PasswordStorage.update(account);
    }

    /**
     * Creates a new branch in the system.
     * 
     * @param branchName the name of the branch
     * @param branchLocation the location of the branch
     * @param staffQuota the maximum number of staff members that can be accommodated at the branch
     * @return true if the branch was successfully created, false otherwise
     */
    public boolean createBranch(String branchName, String branchLocation, int staffQuota) {
        Branch[] branches = getBranchList();
        int temp = 0;
        for (Branch branch : branches) {
            if (branch.getID() > temp){
                temp = branch.getID();
            }
        }
        Branch branch = new Branch(temp + 1, branchName, branchLocation, staffQuota);
        return addBranch(branch);
    }

    /**
     * Retrieves all payment methods in the system.
     * 
     * @return an array of PaymentMethod objects representing all payment methods in the system
     */
    public PaymentMethod[] getPaymentMethods() {
        return PaymentMethodStorage.getAll();
    }

    
    /**
     * Creates a new account for a branch user.
     * 
     * @param staff the branch user for whom the account is being created
     * @return true if the account is created successfully, false otherwise
     * @throws IllegalArgumentException if the staff already exists
     */
    private boolean createAccount(BranchUser staff){
        for (BranchUser curStaff: getStaffList()){
            if (curStaff.getLoginID().equals(staff.getLoginID())){
                throw new IllegalArgumentException("Staff already exists.");
            }
        }
        Account account = new Account(staff.getLoginID(), "password");
        PasswordStorage.add(account);
        return true;
    }

    /**
     * Deletes an account from the system.
     * 
     * @param staff the branch user whose account is to be deleted
     * @return true if the account is deleted successfully, false otherwise
     */
    private boolean deleteAccount(BranchUser staff){
        for (BranchUser curStaff: getStaffList()){
            if (curStaff.getLoginID().equals(staff.getLoginID())){
                Account account = new Account(staff.getLoginID(), "password");
                PasswordStorage.remove(account);
                return true;
            }   
        }
        return false;
    }
}
