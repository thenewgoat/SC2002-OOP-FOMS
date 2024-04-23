package models;

import java.io.Serializable;

/**
 * Represents a branch of the fast food chain, including details about the branch's name,
 * location, and staff quota. This class allows for managing the properties associated
 * with a branch.
 */
public class Branch implements Serializable {
    private int branchID;
    private String branchName;
    private String location;
    private int staffQuota;

    /**
     * Constructs a new Branch with the specified details.
     * 
     * @param name The name of the branch.
     * @param location The physical location of the branch.
     * @param staffQuota The maximum number of staff members that can be accommodated at the branch.
     */
    public Branch(String name, String location, int staffQuota) {
        this.branchName = name;
        this.location = location;
        this.staffQuota = staffQuota;
    }

    /**
     * Returns the name of the branch.
     * 
     * @return the name of the branch
     */
    public String getName() {
        return branchName;
    }

    /**
     * Sets the name of the branch.
     * 
     * @param name the new name to be set for the branch
     */
    public void setName(String name) {
        this.branchName = name;
    }

    /**
     * Returns the location of the branch.
     * 
     * @return the location of the branch
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the branch.
     * 
     * @param location the new location to be set for the branch
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the staff quota of the branch.
     * 
     * @return the staff quota of the branch
     */
    public int getStaffQuota() {
        return staffQuota;
    }

    /**
     * Sets the staff quota for the branch.
     * 
     * @param staffQuota the new staff quota to be set for the branch
     */
    public void setStaffQuota(int staffQuota) {
        this.staffQuota = staffQuota;
    }
}