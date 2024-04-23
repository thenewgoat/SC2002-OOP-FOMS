package stores;

import models.Account;
import models.User;
import services.SerialDataService;

import java.io.File;
import java.util.HashMap;

/**
 * The PasswordStorage class is responsible for storing and managing user accounts and their passwords.
 * It implements the Storage interface.
 */
public class PasswordStorage implements Storage {
    private final String passwordDataPath = "foms/data/passwords.ser";
    private HashMap<String, Account> accounts = new HashMap<>();

    /**
     * Constructs a new PasswordStorage object and loads the stored accounts from the file.
     */
    public PasswordStorage() {
        load();
    }

    /**
     * Adds an account to the password storage.
     *
     * @param object The account to be added.
     * @throws IllegalArgumentException If the object is not an instance of Account.
     */
    @Override
    public void add(Object object) {
        if (object instanceof Account) {
            Account account = (Account) object;
            if (accounts.containsKey(account.getLoginID())) {
                throw new IllegalArgumentException("Account with login ID " + account.getLoginID() + " already exists.");
            }
            accounts.put(account.getLoginID(), account);
        } else {
            throw new IllegalArgumentException("Object must be an instance of Account.");
        }
    }

    /**
     * Removes an account from the password storage.
     *
     * @param object The account to be removed.
     * @throws IllegalArgumentException If the object is not an instance of Account.
     */
    @Override
    public void remove(Object object) {
        if (object instanceof Account) {
            Account account = (Account) object;
            accounts.remove(account.getLoginID());
        } else {
            throw new IllegalArgumentException("Object must be an instance of Account.");
        }
    }

    /**
     * Updates an account in the password storage.
     *
     * @param object The account to be updated.
     * @throws IllegalArgumentException If the object is not an instance of Account.
     */
    @Override
    public void update(Object object) {
        if (object instanceof Account) {
            Account account = (Account) object;
            accounts.put(account.getLoginID(), account); // This will overwrite the existing Account
        } else {
            throw new IllegalArgumentException("Object must be an instance of Account.");
        }
    }

    /**
     * Retrieves an Account by its numeric ID.
     * This operation is not supported because accounts are managed by login ID.
     *
     * @param id The integer ID of the account.
     * @return None as this method always throws an exception.
     * @throws UnsupportedOperationException to indicate that account retrieval by numeric ID is not supported.
     * Use {@link #get(String loginID)} to retrieve accounts by their login ID instead.
     */
    @Override
    public Account get(int id) {
        throw new UnsupportedOperationException("Account retrieval by ID not supported. Use get(String loginID) instead.");
    }

    /**
     * Retrieves an account from the password storage by login ID.
     *
     * @param loginID The login ID of the account to retrieve.
     * @return The account with the specified login ID, or null if not found.
     */
    @Override
    public Account get(String loginID) {
        return accounts.get(loginID);
    }

    /**
     * Retrieves all accounts from the password storage.
     *
     * @return An array of all accounts in the password storage.
     */
    @Override
    public Account[] getAll() {
        return accounts.values().toArray(new Account[0]);
    }

    /**
     * Saves the accounts in the password storage to a file.
     */
    @Override
    public void save() {
        SerialDataService serialDataService = new SerialDataService();
        serialDataService.exportPasswordData(accounts);
    }

    /**
     * Loads the accounts from the file into the password storage.
     */
    @Override
    public void load() {
        File file = new File(passwordDataPath);
        if (file.exists()) {
            SerialDataService serialDataService = new SerialDataService();
            accounts = serialDataService.importPasswordData();
        } else {
            accounts = new HashMap<>();
            System.out.println("No password storage found. Creating a new storage.");
            UserStorage userStorage = new UserStorage();
            User[] users = userStorage.getAll();
            for (User user : users) {
                accounts.put(user.getLoginID(), new Account(user.getLoginID(), "password"));
            }
            save();
        }
    }

    /**
     * Clears all accounts from the password storage.
     */
    @Override
    public void clear() {
        accounts.clear();
    }
}

