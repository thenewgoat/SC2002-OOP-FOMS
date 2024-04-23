package stores;

/**
 * The Storage interface represents a storage mechanism for objects.
 * It provides methods to add, remove, update, retrieve, and manage objects in the storage.
 */
public interface Storage {

    /**
     * Adds an object to the storage.
     *
     * @param object the object to be added
     */
    public void add(Object object);

    /**
     * Removes an object from the storage.
     *
     * @param object the object to be removed
     */
    public void remove(Object object);

    /**
     * Updates an object in the storage.
     *
     * @param object the object to be updated
     */
    public void update(Object object);

    /**
     * Retrieves an object from the storage based on its ID.
     *
     * @param id the ID of the object to be retrieved
     * @return the retrieved object, or null if not found
     */
    public Object get(int id);

    /**
     * Retrieves an object from the storage based on its name.
     *
     * @param name the name of the object to be retrieved
     * @return the retrieved object, or null if not found
     */
    public Object get(String name);

    /**
     * Retrieves all objects from the storage.
     *
     * @return an array of all objects in the storage
     */
    public Object[] getAll();

    /**
     * Saves the storage to a persistent storage medium.
     */
    public void save();

    /**
     * Loads the storage from a persistent storage medium.
     */
    public void load();

    /**
     * Clears the storage, removing all objects.
     */
    public void clear();

}
