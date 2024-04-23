package stores;

public interface Storage {

    public void add(Object object);

    public void remove(Object object);

    public void update(Object object);

    public Object get(int id);

    public Object get(String name);

    public Object[] getAll();

    public void save();

    public void load();

    public void clear();

}
