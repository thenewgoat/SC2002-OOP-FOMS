package stores;

import models.Account;
import models.User;
import services.SerialDataService;

import java.io.File;
import java.util.HashMap;

/**
 * The PasswordStorage class is responsible for storing and managing user accounts and their passwords.
 * All methods and fields are static.
 */
public class PasswordStorage {
    private static final String passwordDataPath = "foms/data/passwords.ser";
    private static HashMap<String, Account> accounts = new HashMap<>();

    // Static initializer to load the stored accounts from the file
    static {
        load();
    }

    /**
     * Adds an account to the password storage.
     *
     * @param account The account to be added.
     * @throws IllegalArgumentException If the account is null or if an account with the same login ID already exists.
     */
    public static void add(Account account) {
        if (account != null) {
            if (accounts.containsKey(account.getLoginID())) {
                throw new IllegalArgumentException("Account with login ID " + account.getLoginID() + " already exists.");
            }
            accounts.put(account.getLoginID(), account);
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null Account.");
        }
    }

    /**
     * Removes an account from the password storage.
     *
     * @param account The account to be removed.
     */
    public static void remove(Account account) {
        if (account != null) {
            accounts.remove(account.getLoginID());
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null Account.");
        }
    }

    /**
     * Updates an account in the password storage.
     *
     * @param account The account to be updated.
     */
    public static void update(Account account) {
        if (account != null) {
            accounts.put(account.getLoginID(), account); // This will overwrite the existing Account
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null Account.");
        }
    }

    /**
     * Retrieves an account from the password storage by login ID.
     *
     * @param loginID The login ID of the account to retrieve.
     * @return The account with the specified login ID, or null if not found.
     */
    public static Account get(String loginID) {
        return accounts.get(loginID);
    }

    /**
     * Retrieves all accounts from the password storage.
     *
     * @return An array of all accounts in the password storage.
     */
    public static Account[] getAll() {
        return accounts.values().toArray(new Account[0]);
    }

    /**
     * Saves the accounts in the password storage to a file.
     */
    public static void save() {
        SerialDataService serialDataService = new SerialDataService();
        serialDataService.exportPasswordData(accounts);
    }

    /**
     * Loads the accounts from the file into the password storage.
     */
    public static void load() {
        File file = new File(passwordDataPath);
        if (file.exists()) {
            SerialDataService serialDataService = new SerialDataService();
            accounts = serialDataService.importPasswordData();
        } else {
            accounts = new HashMap<>();
            System.out.println("No password storage found. Creating a new storage.");
            User[] users = UserStorage.getAll();
            for (User user : users) {
                accounts.put(user.getLoginID(), new Account(user.getLoginID(), "password"));
            }
            save();
        }
    }

    /**
     * Clears all accounts from the password storage.
     */
    public static void clear() {
        accounts.clear();
    }
}
