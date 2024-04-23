package stores;

import models.Account;
import java.util.HashMap;
import java.util.Map;

public class PasswordStorage implements Storage {
    private Map<String, Account> accounts = new HashMap<>();

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
        // Implement based on your persistence mechanism, e.g., serialization or database storage
    }

    @Override
    public void load() {
        // Implement loading logic based on your persistence mechanism
    }

    @Override
    public void clear() {
        accounts.clear();
    }
}

