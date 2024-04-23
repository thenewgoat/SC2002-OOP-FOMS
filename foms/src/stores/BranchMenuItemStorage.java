package stores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.BranchMenuItem;

public class BranchMenuItemStorage implements Storage{
    private Map<Integer, BranchMenuItem> branchMenuItems = new HashMap<>();
    private List<String> categories = new ArrayList<>();

    public BranchMenuItemStorage(){
        load();
    }

    @Override
    public void add(Object object) {
        if (object instanceof BranchMenuItem) {
            BranchMenuItem branchMenuItem = (BranchMenuItem) object;
            if (!branchMenuItems.containsKey(branchMenuItem.getItemID())) {
                branchMenuItems.put(branchMenuItem.getItemID(), branchMenuItem);
                addUniqueCategory(branchMenuItem.getCategory());
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
            BranchMenuItem[] branchMenuItemsArray = (BranchMenuItem[]) getAll();
            for (BranchMenuItem item : branchMenuItemsArray) {
                if(item.getCategory().equals(branchMenuItem.getCategory())){
                    return;
                }
            }
            categories.remove(branchMenuItem.getCategory());
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
        // Implement based on your persistence mechanism, e.g., serialization or database storage
    }

    @Override
    public void load() {
        // Implement loading logic based on your persistence mechanism
    }

    @Override
    public void clear() {
        branchMenuItems.clear();
    }

    public void addUniqueCategory(String category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }

    public String getCategoryByIndex(int index) {
        return categories.get(index);
    }

    public int displayMenuCategories() {
        int counter = 1;
        for (String category : categories) {
            System.out.println(counter + ". " + category);
            counter++;
        }
        return counter;
    }

}
