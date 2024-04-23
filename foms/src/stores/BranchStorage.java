package stores;

import java.io.File;
import java.util.HashMap;
import models.Branch;
import services.CSVDataService;
import services.SerialDataService;

/**
 * The BranchStorage class is responsible for storing and managing Branch objects.
 * It implements the Storage interface.
 */
public class BranchStorage implements Storage {
    private HashMap<Integer, Branch> branches = new HashMap<>();
    private final String branchDataPath = "foms/data/branches.ser";

    /**
     * Constructs a new BranchStorage object and loads the stored data.
     */
    public BranchStorage() {
        load();
    }

    /**
     * Adds a Branch object to the storage.
     * 
     * @param object The Branch object to be added.
     * @throws IllegalArgumentException if the object is not an instance of Branch.
     */
    @Override
    public void add(Object object) {
        if (object instanceof Branch) {
            Branch branch = (Branch) object;
            if (!branches.containsKey(branch.getID())) {
                branches.put(branch.getID(), branch);
            } else {
                throw new IllegalArgumentException("Branch with ID " + branch.getID() + " already exists.");
            }
        } else {
            throw new IllegalArgumentException("Object must be an instance of Branch.");
        }
    }

    /**
     * Removes a Branch object from the storage.
     * 
     * @param object The Branch object to be removed.
     * @throws IllegalArgumentException if the object is not an instance of Branch.
     */
    @Override
    public void remove(Object object) {
        if (object instanceof Branch) {
            Branch branch = (Branch) object;
            branches.remove(branch.getID());
        } else {
            throw new IllegalArgumentException("Object must be an instance of Branch.");
        }
    }

    /**
     * Updates a Branch object in the storage.
     * 
     * @param object The Branch object to be updated.
     * @throws IllegalArgumentException if the object is not an instance of Branch.
     */
    @Override
    public void update(Object object) {
        if (object instanceof Branch) {
            Branch branch = (Branch) object;
            if (branches.containsKey(branch.getID())) {
                branches.put(branch.getID(), branch);
            } else {
                throw new IllegalArgumentException("Cannot update non-existing branch.");
            }
        } else {
            throw new IllegalArgumentException("Object must be an instance of Branch.");
        }
    }

    /**
     * Retrieves a Branch object from the storage based on the branch ID.
     * 
     * @param branchID The ID of the branch to retrieve.
     * @return The Branch object with the specified ID, or null if not found.
     */
    @Override
    public Branch get(int branchID) {
        return branches.get(branchID);
    }

    /**
     * Retrieves a Branch object from the storage based on the branch name.
     * 
     * @param branchName The name of the branch to retrieve.
     * @return The Branch object with the specified name, or null if not found.
     */
    @Override
    public Branch get(String branchName) {
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
    @Override
    public Branch[] getAll() {
        return branches.values().toArray(new Branch[0]);
    }

    /**
     * Saves the current state of the storage to a file.
     */
    @Override
    public void save() {
        SerialDataService serialDataService = new SerialDataService();
        serialDataService.exportBranchData(branches);
    }

    /**
     * Loads the stored data from a file, or initializes a new storage if the file does not exist.
     */
    @Override
    public void load() {
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
    @Override
    public void clear() {
        branches.clear();
    }
}
