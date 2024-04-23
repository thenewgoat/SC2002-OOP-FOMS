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
import services.CSVDataService;

public class BranchStorage implements Storage{
    private Map<Integer, Branch> branches = new HashMap<>();
    private final String branchDataPath = "data/branches.ser";

    public BranchStorage(){
        load();
    }

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
    public Object get(String branchName) {
        for (Branch branch : branches.values()) {
            if (branch.getName().equals(branchName)) {
                return branch;
            }
        }
        return null;
    }

    @Override
    public Object[] getAll() {
        return branches.values().toArray(new Branch[0]);
    }

    @Override
    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(branchDataPath))) {
            oos.writeObject(branches);
        } catch (IOException e) {
            System.out.println("Error saving order storage: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void load() {
        File file = new File(branchDataPath);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                branches = (HashMap<Integer, Branch>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading PaymentMethod storage: " + e.getMessage());
            }
        }else{
            branches = new HashMap<>();
            CSVDataService csvDataService = new CSVDataService();
            branches = csvDataService.importBranchData();
            save();
        }
    }

    @Override
    public void clear() {
        branches.clear();
    }
}
