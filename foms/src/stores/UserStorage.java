package stores;

import java.io.File;
import java.util.HashMap;

import models.BranchUser;
import models.User;
import services.CSVDataService;
import services.SerialDataService;

/**
 * The UserStorage class is responsible for storing and managing User objects.
 * All methods and fields are static.
 */
public class UserStorage {
    private static HashMap<String, User> users = new HashMap<>();
    private static final String userFilename = "foms/data/users.ser";

    // Static initializer to load the stored data when the class is first used
    static {
        load();
    }

    /**
     * Adds a User object to the storage.
     *
     * @param user The User object to be added.
     * @throws IllegalArgumentException if the user is null or if a user with the same login ID already exists.
     */
    public static void add(User user) {
        if (user != null) {
            String loginID = user.getLoginID();
            if (!users.containsKey(loginID)) {
                users.put(loginID, user);
            } else {
                throw new IllegalArgumentException("User with login ID " + loginID + " already exists.");
            }
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null User.");
        }
    }

    /**
     * Removes a User object from the storage.
     *
     * @param user The User object to be removed.
     */
    public static void remove(User user) {
        if (user != null) {
            users.remove(user.getLoginID());
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null User.");
        }
    }

    /**
     * Updates a User object in the storage.
     *
     * @param user The User object to be updated.
     */
    public static void update(User user) {
        if (user != null) {
            users.put(user.getLoginID(), user);
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null User.");
        }
    }

    /**
     * Retrieves a User object by login ID.
     *
     * @param loginID The login ID of the User object to retrieve.
     * @return The User object with the specified login ID, or null if not found.
     */
    public static User get(String loginID) {
        return users.get(loginID);
    }

    /**
     * Retrieves all User objects in the storage.
     *
     * @return An array of User objects.
     */
    public static User[] getAll() {
        return users.values().toArray(new User[0]);
    }

    /**
     * Saves the User storage to a file.
     */
    public static void save() {
        SerialDataService serialDataService = new SerialDataService();
        serialDataService.exportUserData(users);
    }

    /**
     * Loads the User storage from a file.
     * If the file does not exist, it initializes the storage with data from a CSV file using CSVDataService and saves it.
     */
    public static void load() {
        File file = new File(userFilename);
        BranchUser[] branchUsers = BranchUserStorage.getAll();
        if (file.exists()) {
            SerialDataService serialDataService = new SerialDataService();
            users = serialDataService.importUserData();
            for (BranchUser branchUser : branchUsers) {
                for (User user : users.values()) {
                    if (user.getLoginID().equals(branchUser.getLoginID())) {
                        users.remove(user.getLoginID());
                        users.put(branchUser.getLoginID(), branchUser);
                        break;
                    }
                }             
            }

        } else {
            users = new HashMap<>();
            CSVDataService csvDataService = new CSVDataService();
            users = csvDataService.importUserData();      
            for (BranchUser branchUser : branchUsers) {
                users.put(branchUser.getLoginID(), branchUser);
            }
            save();
        }
    }

    /**
     * Clears the User storage.
     */
    public static void clear() {
        users.clear();
    }
}
