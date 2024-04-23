package stores;

import java.io.File;
import java.util.HashMap;

import models.BranchUser;
import services.CSVDataService;
import services.SerialDataService;

/**
 * Manages the storage of {@code BranchUser} objects using serialization with static access.
 * Provides static methods to add, remove, update, and retrieve users as well as save and load
 * the users from a file.
 */
public class BranchUserStorage {

    private static HashMap<String, BranchUser> branchUsers = new HashMap<>();
    private static final String userFilename = "foms/data/branchUsers.ser";

    // Static initializer to load the data when the class is first loaded
    static {
        load();
    }

    /**
     * Adds a {@code BranchUser} to the storage.
     *
     * @param branchUser the {@code BranchUser} to add
     * @throws IllegalArgumentException if branchUser is null or if a user with the same login ID already exists
     */
    public static void add(BranchUser branchUser) {
        if (branchUser != null) {
            String loginID = branchUser.getLoginID();
            if (!branchUsers.containsKey(loginID)) {
                branchUsers.put(loginID, branchUser);
            } else {
                throw new IllegalArgumentException("BranchUser with login ID " + loginID + " already exists.");
            }
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null BranchUser.");
        }
    }

    /**
     * Removes a {@code BranchUser} from the storage.
     *
     * @param branchUser the {@code BranchUser} to remove
     */
    public static void remove(BranchUser branchUser) {
        if (branchUser != null) {
            branchUsers.remove(branchUser.getLoginID());
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null BranchUser.");
        }
    }

    /**
     * Updates an existing {@code BranchUser} in the storage.
     *
     * @param branchUser the {@code BranchUser} to update
     */
    public static void update(BranchUser branchUser) {
        if (branchUser != null) {
            branchUsers.put(branchUser.getLoginID(), branchUser);
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null BranchUser.");
        }
    }

    /**
     * Retrieves a {@code BranchUser} by their login ID.
     *
     * @param loginID the login ID of the branch user
     * @return the {@code BranchUser} if found, or null if not
     */
    public static BranchUser get(String loginID) {
        return branchUsers.get(loginID);
    }

    /**
     * Returns all {@code BranchUser} objects stored in the storage.
     *
     * @return an array of all stored {@code BranchUser} objects
     */
    public static BranchUser[] getAll() {
        return branchUsers.values().toArray(new BranchUser[0]);
    }

    /**
     * Saves all {@code BranchUser} objects to a file.
     */
    public static void save() {
        SerialDataService serialDataService = new SerialDataService();
        serialDataService.exportBranchUserData(branchUsers);
    }

    /**
     * Loads {@code BranchUser} objects from a file, or initializes new storage if the file does not exist.
     */
    public static void load() {
        File file = new File(userFilename);
        if (file.exists()) {
            SerialDataService serialDataService = new SerialDataService();
            branchUsers = serialDataService.importBranchUserData();
        } else {
            branchUsers = new HashMap<>();
            CSVDataService csvDataService = new CSVDataService();
            branchUsers = csvDataService.importBranchUserData();
            save();
        }
    }

    /**
     * Clears all entries from the storage.
     */
    public static void clear() {
        branchUsers.clear();
    }
}
