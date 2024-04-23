package stores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import models.Branch;
import models.BranchUser;
import models.User;
import services.CSVDataService;

public class UserStorage implements Storage {
    private Map<String, User> users = new HashMap<>();
    private final String userFilename = "data/staff_list.csv";

    public UserStorage() {
        load();
    }

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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFilename))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving order storage: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void load() {
        File file = new File(userFilename);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                users = (Map<String, User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading PaymentMethod storage: " + e.getMessage());
            }
        }else{
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

    @Override
    public void clear() {
        users.clear();
    }
}
