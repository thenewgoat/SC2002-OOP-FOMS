package stores;

import java.io.File;
import java.util.HashMap;
import models.BranchUser;
import services.CSVDataService;
import services.SerialDataService;

/**
 * Manages the storage of {@code BranchUser} objects using serialization.
 * Provides methods to add, remove, update, and retrieve users as well as save and load
 * the users from a file.
 */
public class BranchUserStorage implements Storage {

    private HashMap<String, BranchUser> branchUsers = new HashMap<>();
    private final String userFilename = "foms/data/branchUsers.ser";

    /**
     * Constructs a new {@code BranchUserStorage} instance and initializes it by loading stored users
     * or creating new storage if no stored data exists.
     */
    public BranchUserStorage() {
        load();
    }

    /**
     * Adds a {@code BranchUser} to the storage.
     *
     * @param object the {@code BranchUser} to add
     * @throws IllegalArgumentException if the object is not an instance of {@code BranchUser}
     * or if a user with the same login ID already exists
     */
    @Override
    public void add(Object object) {
        if (object instanceof BranchUser) {
            BranchUser branchUser = (BranchUser) object;
            String loginID = branchUser.getLoginID();
            if (!branchUsers.containsKey(loginID)) {
                branchUsers.put(loginID, branchUser);
            } else {
                throw new IllegalArgumentException("BranchUser with login ID " + loginID + " already exists.");
            }
        } else {
            throw new IllegalArgumentException("Object must be an instance of BranchUser.");
        }
    }

    /**
     * Removes a {@code BranchUser} from the storage.
     *
     * @param object the {@code BranchUser} to remove
     * @throws IllegalArgumentException if the object is not an instance of {@code BranchUser}
     */
    @Override
    public void remove(Object object) {
        if (object instanceof BranchUser) {
            BranchUser user = (BranchUser) object;
            branchUsers.remove(user.getLoginID());
        } else {
            throw new IllegalArgumentException("Object must be an instance of BranchUser.");
        }
    }

    /**
     * Updates an existing {@code BranchUser} in the storage.
     *
     * @param object the {@code BranchUser} to update
     * @throws IllegalArgumentException if the object is not an instance of {@code BranchUser}
     */
    @Override
    public void update(Object object) {
        if (object instanceof BranchUser) {
            BranchUser user = (BranchUser) object;
            branchUsers.put(user.getLoginID(), user);
        } else {
            throw new IllegalArgumentException("Object must be an instance of BranchUser.");
        }
    }

    /**
     * Unsupported operation for getting a user by numerical ID.
     * 
     * @param id the ID of the user
     * @throws UnsupportedOperationException always
     */
    @Override
    public Object get(int id) {
        throw new UnsupportedOperationException("Use get(String loginID) for retrieving branchUsers.");
    }

    /**
     * Retrieves a {@code BranchUser} by their login ID.
     *
     * @param loginID the login ID of the branch user
     * @return the {@code BranchUser} if found, or null if not
     */
    @Override
    public BranchUser get(String loginID) {
        return branchUsers.get(loginID);
    }

    /**
     * Returns all {@code BranchUser} objects stored in the storage.
     *
     * @return an array of all stored {@code BranchUser} objects
     */
    @Override
    public BranchUser[] getAll() {
        return branchUsers.values().toArray(new BranchUser[0]);
    }

    /**
     * Saves all {@code BranchUser} objects to a file.
     */
    @Override
    public void save() {
        SerialDataService serialDataService = new SerialDataService();
        serialDataService.exportBranchUserData(branchUsers);
    }

    /**
     * Loads {@code BranchUser} objects from a file, or initializes new storage if the file does not exist.
     */
    @Override
    public void load() {
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
    @Override
    public void clear() {
        branchUsers.clear();
    }
}
