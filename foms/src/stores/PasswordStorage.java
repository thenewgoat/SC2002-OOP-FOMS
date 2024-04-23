package stores;

import models.Account;
import models.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordStorage implements Storage {
    private final String passwordDataPath = "data/passwords.ser";
    private Map<String, Account> accounts = new HashMap<>();

    public PasswordStorage() {
        load();
    }

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

    @Override
    public void remove(Object object) {
        if (object instanceof Account) {
            Account account = (Account) object;
            accounts.remove(account.getLoginID());
        } else {
            throw new IllegalArgumentException("Object must be an instance of Account.");
        }
    }

    @Override
    public void update(Object object) {
        if (object instanceof Account) {
            Account account = (Account) object;
            accounts.put(account.getLoginID(), account); // This will overwrite the existing Account
        } else {
            throw new IllegalArgumentException("Object must be an instance of Account.");
        }
    }

    @Override
    public Account get(int id) {
        throw new UnsupportedOperationException("Account retrieval by ID not supported. Use get(String loginID) instead.");
    }

    @Override
    public Account get(String loginID) {
        return accounts.get(loginID);
    }

    @Override
    public Account[] getAll() {
        return accounts.values().toArray(new Account[0]);
    }

    @Override
    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(passwordDataPath))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.out.println("Error saving password storage: " + e.getMessage());
        }
    }

    @Override
    public void load() {
        File file = new File(passwordDataPath);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                accounts = (HashMap<String, Account>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading password storage: " + e.getMessage());
            }
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

    @Override
    public void clear() {
        accounts.clear();
    }
}

