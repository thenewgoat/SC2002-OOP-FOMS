package stores;

import java.io.File;
import java.util.HashMap;

import models.Branch;
import services.CSVDataService;
import services.SerialDataService;

/**
 * The BranchStorage class is responsible for storing and managing Branch objects.
 * It implements the Storage interface. All methods and fields are static.
 */
public class BranchStorage {
    private static HashMap<Integer, Branch> branches = new HashMap<>();
    private static final String branchDataPath = "foms/data/branches.ser";

    // Static initializer to load the data when the class is first loaded
    static {
        load();
    }

    /**
     * Adds a Branch object to the storage.
     * 
     * @param branch The Branch object to be added.
     * @throws IllegalArgumentException if the branch is null or if a branch with the same ID already exists.
     */
    public static void add(Branch branch) {
        if (branch != null) {
            if (!branches.containsKey(branch.getID())) {
                branches.put(branch.getID(), branch);
            } else {
                throw new IllegalArgumentException("Branch with ID " + branch.getID() + " already exists.");
            }
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null Branch.");
        }
        save();
    }

    /**
     * Removes a Branch object from the storage.
     * 
     * @param branch The Branch object to be removed.
     */
    public static void remove(Branch branch) {
        if (branch != null) {
            branches.remove(branch.getID());
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null Branch.");
        }
        save();
    }

    /**
     * Updates a Branch object in the storage.
     * 
     * @param branch The Branch object to be updated.
     */
    public static void update(Branch branch) {
        if (branch != null && branches.containsKey(branch.getID())) {
            branches.put(branch.getID(), branch);
        } else {
            throw new IllegalArgumentException("Cannot update non-existing or null Branch.");
        }
        save();
    }

    /**
     * Retrieves a Branch object from the storage based on the branch ID.
     * 
     * @param branchID The ID of the branch to retrieve.
     * @return The Branch object with the specified ID, or null if not found.
     */
    public static Branch get(int branchID) {
        return branches.get(branchID);
    }

    /**
     * Retrieves a Branch object from the storage based on the branch name.
     * 
     * @param branchName The name of the branch to retrieve.
     * @return The Branch object with the specified name, or null if not found.
     */
    public static Branch get(String branchName) {
        for (Branch branch : branches.values()) {
            if (branch.getName().equals(branchName)) {
                return branch;
            }
        }
        return null;
    }

    /**
     * Retrieves all Branch objects from the storage.
     * 
     * @return An array of all Branch objects in the storage.
     */
    public static Branch[] getAll() {
        return branches.values().toArray(new Branch[0]);
    }

    /**
     * Saves the current state of the storage to a file.
     */
    public static void save() {
        SerialDataService serialDataService = new SerialDataService();
        serialDataService.exportBranchData(branches);
    }

    /**
     * Loads the stored data from a file, or initializes a new storage if the file does not exist.
     */
    public static void load() {
        File file = new File(branchDataPath);
        if (file.exists()) {
            SerialDataService serialDataService = new SerialDataService();
            branches = serialDataService.importBranchData();
        } else {
            branches = new HashMap<>();
            CSVDataService csvDataService = new CSVDataService();
            branches = csvDataService.importBranchData();
            save();
        }
    }

    /**
     * Clears all Branch objects from the storage.
     */
    public static void clear() {
        branches.clear();
    }
}
