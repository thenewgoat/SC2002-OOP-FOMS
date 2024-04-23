package stores;

import java.util.HashMap;
import java.util.Map;

import models.User;

public class UserStorage implements Storage {
    private Map<String, User> users = new HashMap<>();

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

    @Override
    public void remove(Object object) {
        if (object instanceof User) {
            User user = (User) object;
            users.remove(user.getLoginID());
        } else {
            throw new IllegalArgumentException("Object must be an instance of User.");
        }
    }

    @Override
    public void update(Object object) {
        if (object instanceof User) {
            User user = (User) object;
            users.put(user.getLoginID(), user);
        } else {
            throw new IllegalArgumentException("Object must be an instance of User.");
        }
    }

    @Override
    public Object get(int id) {
        throw new UnsupportedOperationException("Use get(String loginID) for retrieving users.");
    }

    @Override
    public User get(String loginID) {
        return users.get(loginID);
    }

    @Override
    public User[] getAll() {
        return users.values().toArray(new User[0]);
    }

    @Override
    public void save() {
        // Implementation depends on the persistence mechanism (e.g., serialization, database)
    }

    @Override
    public void load() {
        // Implementation depends on the persistence mechanism
    }

    @Override
    public void clear() {
        users.clear();
    }
}
