package stores;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.BranchMenuItem;
import services.CSVDataService;
import services.SerialDataService;

/**
 * Manages the storage of {@code BranchMenuItem} objects, using serialization to persist data.
 * Provides functionality to add, remove, update, and retrieve menu items. It also manages unique
 * categories of menu items.
 */
public class BranchMenuItemStorage implements Storage {
    private HashMap<Integer, BranchMenuItem> branchMenuItems = new HashMap<>();
    private List<String> categories = new ArrayList<>();
    private final String menuFilename = "foms/data/branchMenuItems.ser";

    /**
     * Constructs a new {@code BranchMenuItemStorage} instance and initializes it by loading stored data,
     * or creating new storage if no stored data exists.
     */
    public BranchMenuItemStorage() {
        load();
    }

    /**
     * Adds a {@code BranchMenuItem} to the storage.
     * @param object the {@code BranchMenuItem} to add
     * @throws IllegalArgumentException if the object is not an instance of {@code BranchMenuItem}
     * or if a menu item with the same ID already exists.
     */
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

    /**
     * Removes a {@code BranchMenuItem} from the storage.
     * @param object the {@code BranchMenuItem} to remove
     * @throws IllegalArgumentException if the object is not an instance of {@code BranchMenuItem}.
     */
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

    /**
     * Updates an existing {@code BranchMenuItem} in the storage.
     * @param object the {@code BranchMenuItem} to update
     * @throws IllegalArgumentException if the object is not an instance of {@code BranchMenuItem} or if the item does not exist.
     */
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

    /**
     * Retrieves a {@code BranchMenuItem} by its item ID.
     * @param itemID the ID of the menu item
     * @return the {@code BranchMenuItem} if found, or null if not
     */
    @Override
    public BranchMenuItem get(int itemID) {
        return branchMenuItems.get(itemID);
    }

    /**
     * Unsupported operation for retrieving a branch menu item by name.
     * @param name the name of the menu item
     * @throws UnsupportedOperationException always
     */
    @Override
    public BranchMenuItem get(String name) {
        throw new UnsupportedOperationException("Use get(int itemID) for retrieving branchMenuItems.");
    }

    /**
     * Returns all {@code BranchMenuItem} objects stored in the storage.
     * @return an array of all stored {@code BranchMenuItem} objects
     */
    @Override
    public BranchMenuItem[] getAll() {
        return branchMenuItems.values().toArray(new BranchMenuItem[0]);
    }

    /**
     * Serializes and saves all {@code BranchMenuItem} objects to a file.
     */
    @Override
    public void save() {
        SerialDataService serialDataService = new SerialDataService();
        serialDataService.exportMenuData(branchMenuItems);
    }

    /**
     * Loads {@code BranchMenuItem} objects from a file, or initializes new storage if the file does not exist.
     */
    @Override
    public void load() {
        File file = new File(menuFilename);
        if (file.exists()) {
            SerialDataService serialDataService = new SerialDataService();
            branchMenuItems = serialDataService.importMenuData();
        }else{
            branchMenuItems = new HashMap<>();
            CSVDataService csvDataService = new CSVDataService();
            branchMenuItems = csvDataService.importMenuData();
            save();
        }
    }

    /**
     * Clears all entries from the storage.
     */
    @Override
    public void clear() {
        branchMenuItems.clear();
    }

    /**
     * Adds a unique category to the list of categories if it is not already present.
     * @param category the category to add
     */
    public void addUniqueCategory(String category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }

    /**
     * Retrieves a category by its index in the list.
     * @param index the index of the category in the list
     * @return the category at the specified index
     */
    public String getCategoryByIndex(int index) {
        return categories.get(index);
    }

    /**
     * Displays the categories of menu items and returns the number of categories displayed.
     * @return the number of categories displayed
     */
    public int displayMenuCategories() {
        int counter = 1;
        for (String category : categories) {
            System.out.println(counter + ". " + category);
            counter++;
        }
        return counter;
    }
}
