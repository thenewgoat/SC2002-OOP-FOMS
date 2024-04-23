package stores;

import java.io.File;
import java.util.HashMap;
import models.BranchUser;
import models.User;
import services.CSVDataService;
import services.SerialDataService;

/**
 * The UserStorage class is responsible for storing and managing User objects.
 * It implements the Storage interface.
 */
public class UserStorage implements Storage {
    private HashMap<String, User> users = new HashMap<>();
    private final String userFilename = "foms/data/users.ser";

    /**
     * Constructs a new UserStorage object and loads the stored data.
     */
    public UserStorage() {
        load();
    }

    /**
     * Adds a User object to the storage.
     *
     * @param object The User object to be added.
     * @throws IllegalArgumentException if the object is not an instance of User.
     */
    @Override
    public void add(Object object) {
        if (object instanceof User) {
            User user = (User) object;
            String loginID = user.getLoginID();
            if (!users.containsKey(loginID)) {
                users.put(loginID, user);
            } else {
                throw new IllegalArgumentException("User with login ID " + loginID + " already exists.");
            }
        } else {
            throw new IllegalArgumentException("Object must be an instance of User.");
        }
    }

    /**
     * Removes a User object from the storage.
     *
     * @param object The User object to be removed.
     * @throws IllegalArgumentException if the object is not an instance of User.
     */
    @Override
    public void remove(Object object) {
        if (object instanceof User) {
            User user = (User) object;
            users.remove(user.getLoginID());
        } else {
            throw new IllegalArgumentException("Object must be an instance of User.");
        }
    }

    /**
     * Updates a User object in the storage.
     *
     * @param object The User object to be updated.
     * @throws IllegalArgumentException if the object is not an instance of User.
     */
    @Override
    public void update(Object object) {
        if (object instanceof User) {
            User user = (User) object;
            users.put(user.getLoginID(), user);
        } else {
            throw new IllegalArgumentException("Object must be an instance of User.");
        }
    }

    /**
     * Retrieves a User object by login ID.
     *
     * @param loginID The login ID of the User object to retrieve.
     * @return The User object with the specified login ID, or null if not found.
     */
    @Override
    public User get(String loginID) {
        return users.get(loginID);
    }
    
    /**
     * Retrieves a User object by login ID (integer). However, this method is not supported.
     *
     * @param loginID The login ID of the User object to retrieve.
     * @return The User object with the specified login ID, or null if not found.
     */
    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("User retrieval by ID not supported. Use get(String loginID) instead.");
    }

    /**
     * Retrieves all User objects in the storage.
     *
     * @return An array of User objects.
     */
    @Override
    public User[] getAll() {
        return users.values().toArray(new User[0]);
    }

    /**
     * Saves the User storage to a file.
     */
    @Override
    public void save() {
        SerialDataService serialDataService = new SerialDataService();
        serialDataService.exportUserData(users);
    }

    /**
     * Loads the User storage from a file.
     * If the file does not exist, it initializes the storage with data from a CSV file using CSVDataService and saves it.
     * 
     * Note that this method also loads BranchUser objects from BranchUserStorage and adds them to the storage. This is once-off to
     * initialise the storage with the necessary data and will not be repeated on subsequent loads.
     */
    @Override
    public void load() {
        File file = new File(userFilename);
        if (file.exists()) {
            SerialDataService serialDataService = new SerialDataService();
            users = serialDataService.importUserData();
        } else {
            users = new HashMap<>();
            CSVDataService csvDataService = new CSVDataService();
            users = csvDataService.importUserData();
            BranchUserStorage branchUserStorage = new BranchUserStorage();
            BranchUser[] branchUsers = branchUserStorage.getAll();
            for (BranchUser branchUser : branchUsers) {
                users.put(branchUser.getLoginID(), branchUser);
            }
            save();
        }
    }

    /**
     * Clears the User storage.
     */
    @Override
    public void clear() {
        users.clear();
    }
}
