package stores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import models.BranchUser;
import services.CSVDataService;

public class BranchUserStorage implements Storage{
    private Map<String, BranchUser> branchUsers = new HashMap<>();
    private final String userFilename = "data/staff_list.csv";

    public BranchUserStorage(){
        load();
    }

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

    @Override
    public void remove(Object object) {
        if (object instanceof BranchUser) {
            BranchUser user = (BranchUser) object;
            branchUsers.remove(user.getLoginID());
        } else {
            throw new IllegalArgumentException("Object must be an instance of BranchUser.");
        }
    }

    @Override
    public void update(Object object) {
        if (object instanceof BranchUser) {
            BranchUser user = (BranchUser) object;
            branchUsers.put(user.getLoginID(), user);
        } else {
            throw new IllegalArgumentException("Object must be an instance of BranchUser.");
        }
    }

    @Override
    public Object get(int id) {
        throw new UnsupportedOperationException("Use get(String loginID) for retrieving branchUsers.");
    }

    @Override
    public BranchUser get(String loginID) {
        return branchUsers.get(loginID);
    }

    @Override
    public BranchUser[] getAll() {
        return branchUsers.values().toArray(new BranchUser[0]);
    }

    @Override
    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFilename))) {
            oos.writeObject(branchUsers);
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
                branchUsers = (Map<String, BranchUser>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading PaymentMethod storage: " + e.getMessage());
            }
        }else{
            branchUsers = new HashMap<>();
            CSVDataService csvDataService = new CSVDataService();
            branchUsers = csvDataService.importBranchUserData();
            save();
        }
    }


    @Override
    public void clear() {
        branchUsers.clear();
    }
}
