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
 * categories of menu items. All methods and fields are static.
 */
public class BranchMenuItemStorage {
    private static HashMap<Integer, BranchMenuItem> branchMenuItems = new HashMap<>();
    private static List<String> categories = new ArrayList<>();
    private static final String menuFilename = "foms/data/branchMenuItems.ser";

    // Static initializer to load the data when the class is first loaded
    static {
        load();
    }

    /**
     * Adds a {@code BranchMenuItem} to the storage.
     * @param branchMenuItem the {@code BranchMenuItem} to add
     * @throws IllegalArgumentException if the branchMenuItem is null or if a menu item with the same ID already exists.
     */
    public static void add(BranchMenuItem branchMenuItem) {
        if (branchMenuItem != null) {
            if (!branchMenuItems.containsKey(branchMenuItem.getItemID())) {
                branchMenuItems.put(branchMenuItem.getItemID(), branchMenuItem);
                addUniqueCategory(branchMenuItem.getCategory());
            } else {
                throw new IllegalArgumentException("Menu with ID " + branchMenuItem.getItemID() + " already exists.");
            }
        } else {
            throw new IllegalArgumentException("Parameter must be a non-null BranchMenuItem.");
        }
        save();
    }

    /**
     * Removes a {@code BranchMenuItem} from the storage.
     * @param branchMenuItem the {@code BranchMenuItem} to remove
     */
    public static void remove(BranchMenuItem branchMenuItem) {
        if (branchMenuItem != null) {
            branchMenuItems.remove(branchMenuItem.getItemID());
            refreshCategories();
        }
        save();
    }

    /**
     * Updates an existing {@code BranchMenuItem} in the storage.
     * @param branchMenuItem the {@code BranchMenuItem} to update
     */
    public static void update(BranchMenuItem branchMenuItem) {
        if (branchMenuItem != null && branchMenuItems.containsKey(branchMenuItem.getItemID())) {
            branchMenuItems.put(branchMenuItem.getItemID(), branchMenuItem);
        } else {
            throw new IllegalArgumentException("Cannot update non-existing or null BranchMenuItem.");
        }
        save();
    }

    /**
     * Retrieves a {@code BranchMenuItem} by its item ID.
     * @param itemID the ID of the menu item
     * @return the {@code BranchMenuItem} if found, or null if not
     */
    public static BranchMenuItem get(int itemID) {
        return branchMenuItems.get(itemID);
    }

    /**
     * Returns all {@code BranchMenuItem} objects stored in the storage.
     * @return an array of all stored {@code BranchMenuItem} objects
     */
    public static BranchMenuItem[] getAll() {
        return branchMenuItems.values().toArray(new BranchMenuItem[0]);
    }

    /**
     * Serializes and saves all {@code BranchMenuItem} objects to a file.
     */
    public static void save() {
        SerialDataService serialDataService = new SerialDataService();
        serialDataService.exportMenuData(branchMenuItems);
    }

    /**
     * Loads {@code BranchMenuItem} objects from a file, or initializes new storage if the file does not exist.
     */
    public static void load() {
        File file = new File(menuFilename);
        if (file.exists()) {
            SerialDataService serialDataService = new SerialDataService();
            branchMenuItems = serialDataService.importMenuData();
        } else {
            branchMenuItems = new HashMap<>();
            CSVDataService csvDataService = new CSVDataService();
            branchMenuItems = csvDataService.importMenuData();
            save();
        }
        refreshCategories();
    }

    /**
     * Clears all entries from the storage.
     */
    public static void clear() {
        branchMenuItems.clear();
        categories.clear();
    }

    /**
     * Adds a unique category to the list of categories if it is not already present.
     * @param category the category to add
     */
    public static void addUniqueCategory(String category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }

    /**
     * Refreshes the list of unique categories based on existing menu items.
     */
    private static void refreshCategories() {
        categories.clear();
        for (BranchMenuItem item : branchMenuItems.values()) {
            if (!categories.contains(item.getCategory())) {
                categories.add(item.getCategory());
            }
        }
    }

    /**
     * Displays the categories of menu items and returns the number of categories displayed.
     * @return the number of categories displayed
     */
    public static int displayMenuCategories() {
        int counter = 1;
        for (String category : categories) {
            System.out.println(counter + ". " + category);
            counter++;
        }
        return counter;
    }

    /**
     * Returns the list of unique categories.
     * @return the list of unique categories
     */
    public static List<String> getCategories() {
        return categories;
    }
}
