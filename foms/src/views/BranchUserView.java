package views;

import interfaces.IBranchUserView;
import models.BranchUser;

/**
 * The {@link BranchUserView} class implements the IBranchUserView interface and is responsible for displaying branch user details.
 */
public class BranchUserView implements IBranchUserView{

    /**
     * Displays the details of the given branch user.
     *
     * @param user a BranchUser object
     * @param branchName the name of the branch to which the user belongs
     */
    @Override
    public void displayBranchUserDetails(BranchUser user, String branchName) {
        System.out.println("Name: " + user.getName());
        System.out.println("Login ID: " + user.getLoginID());
        System.out.println("Branch: " + branchName);
        System.out.println("Role: " + user.getRole());
        System.out.println("Gender: " + user.getGender());
        System.out.println("Age: " + user.getAge());
    }
}
