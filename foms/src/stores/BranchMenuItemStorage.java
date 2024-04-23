package stores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.BranchMenuItem;
import services.CSVDataService;

public class BranchMenuItemStorage implements Storage{
    private Map<Integer, BranchMenuItem> branchMenuItems = new HashMap<>();
    private List<String> categories = new ArrayList<>();
    private final String menuFilename = "data/menu_list.csv";

    public BranchMenuItemStorage(){
        load();
    }

    @Override
    public void add(Object object) {
        if (object instanceof BranchMenuItem) {
            BranchMenuItem branchMenuItem = (BranchMenuItem) object;
            if (!branchMenuItems.containsKey(branchMenuItem.getItemID())) {
                branchMenuItems.put(branchMenuItem.getItemID(), branchMenuItem);
            } else {
                throw new IllegalArgumentException("Menu with ID " + branchMenuItem.getItemID() + " already exists.");
            }
        } else {
            throw new IllegalArgumentException("Object must be an instance of BranchMenuItem.");
        }
    }

    @Override
    public void remove(Object object) {
        if (object instanceof BranchMenuItem) {
            BranchMenuItem branchMenuItem = (BranchMenuItem) object;
            branchMenuItems.remove(branchMenuItem.getItemID());
        } else {
            throw new IllegalArgumentException("Object must be an instance of BranchMenuItem.");
        }
    }

    @Override
    public void update(Object object) {
        if (object instanceof BranchMenuItem) {
            BranchMenuItem branchMenuItem = (BranchMenuItem) object;
            if (branchMenuItems.containsKey(branchMenuItem.getItemID())) {
                branchMenuItems.put(branchMenuItem.getItemID(), branchMenuItem);
            } else {
                throw new IllegalArgumentException("Cannot update non-existing branchMenuItem.");
            }
        } else {
            throw new IllegalArgumentException("Object must be an instance of BranchMenuItem.");
        }
    }

    @Override
    public Object get(int itemID) {
        return branchMenuItems.get(itemID);
    }

    @Override
    public Object get(String name) {
        throw new UnsupportedOperationException("Use get(int itemID) for retrieving branchMenuItems.");
    }

    @Override
    public Object[] getAll() {
        return branchMenuItems.values().toArray(new BranchMenuItem[0]);
    }

    @Override
    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(menuFilename))) {
            oos.writeObject(branchMenuItems);
        } catch (IOException e) {
            System.out.println("Error saving order storage: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void load() {
        File file = new File(menuFilename);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                branchMenuItems = (HashMap<Integer, BranchMenuItem>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading PaymentMethod storage: " + e.getMessage());
            }
        }else{
            branchMenuItems = new HashMap<>();
            CSVDataService csvDataService = new CSVDataService();
            branchMenuItems = csvDataService.importMenuData();
            save();
        }
    }

    @Override
    public void clear() {
        branchMenuItems.clear();
    }

}
