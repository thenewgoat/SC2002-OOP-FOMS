package stores;

import java.util.HashMap;
import java.util.Map;

import models.BranchUser;

public class BranchUserStorage implements Storage{
    private Map<String, BranchUser> branchUsers = new HashMap<>();

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
        // Implementation depends on the persistence mechanism (e.g., serialization, database)
    }

    @Override
    public void load() {
        // Implementation depends on the persistence mechanism
    }

    public void init() {
        
    }

    @Override
    public void clear() {
        branchUsers.clear();
    }
}
