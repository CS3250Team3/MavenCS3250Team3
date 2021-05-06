package CS3250;

import java.util.List;

/**Class used for dependency injection of database objects
 * 
 * @param <T>
 */
public class Database<T> {

DataMan<T> db = null;

public Database(DataMan<T> d){
    this.db = d;
}

public List<T> listEntries(){
    return db.getEntries();
}

public T getEntry(String id){
    return db.readEntry(id);
}

public void updateEntry(String id, T e) {
    db.updateEntry(id, e);
}

public void deleteEntry(String id){
    db.deleteEntry(id);
}
}
