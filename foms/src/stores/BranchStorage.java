package stores;

import java.util.HashMap;
import java.util.Map;

import models.Branch;

public class BranchStorage implements Storage{
    private Map<Integer, Branch> branches = new HashMap<>();

    @Override
    public void add(Object object) {
        if (object instanceof Branch) {
            Branch branch = (Branch) object;
            if (!branches.containsKey(branch.getID())) {
                branches.put(branch.getID(), branch);
            } else {
                throw new IllegalArgumentException("Branch with ID " + branch.getID() + " already exists.");
            }
        } else {
            throw new IllegalArgumentException("Object must be an instance of Branch.");
        }
    }

    @Override
    public void remove(Object object) {
        if (object instanceof Branch) {
            Branch branch = (Branch) object;
            branches.remove(branch.getID());
        } else {
            throw new IllegalArgumentException("Object must be an instance of Branch.");
        }
    }

    @Override
    public void update(Object object) {
        if (object instanceof Branch) {
            Branch branch = (Branch) object;
            if (branches.containsKey(branch.getID())) {
                branches.put(branch.getID(), branch);
            } else {
                throw new IllegalArgumentException("Cannot update non-existing branch.");
            }
        } else {
            throw new IllegalArgumentException("Object must be an instance of Branch.");
        }
    }

    @Override
    public Object get(int branchID) {
        return branches.get(branchID);
    }

    @Override
    public Object get(String name) {
        throw new UnsupportedOperationException("Use get(int branchID) for retrieving branches.");
    }

    @Override
    public Object[] getAll() {
        return branches.values().toArray(new Branch[0]);
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
        branches.clear();
    }
}
