package CS3250;
import java.util.List;
/** 
 * A generic interface for mysql implementation. 
 * 
 * 
 */
public interface DataMan<T> {
    List<T> getEntries();
    void initializeDatabase(String connection);
    void createEntry(String ID, T e);
    T readEntry(String ID);
    void updateEntry(String ID, T e);
    void deleteEntry(String id);
}
