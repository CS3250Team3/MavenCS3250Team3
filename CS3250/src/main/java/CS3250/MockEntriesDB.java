package CS3250;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to mock inventory database
 */
public class MockEntriesDB implements DataMan<Entry>{
    List<Entry> li;
    @Override
    public List<Entry> getEntries() {
       return li;
    }

    /**
     * Initialize arraylist
     * 
     * @param connection - unused
     */
    @Override
    public void initializeDatabase(String connection) {
        var e1 = new Entry();
        
        e1.setProductID("productID");
        e1.setSalePrice(100);
        e1.setStockQuantity(10);
        e1.setSupplierID("sup");
        e1.setWholesaleCost(200);

        li = new ArrayList<Entry>();
        li.add(e1);

    }

    /**
     * Adds an entry object to arraylist
     * 
     * @param ID - Id of object to be added, unused
     * @param e - Entry object to be added
     */
    @Override
    public void createEntry(String ID, Entry e) {
        li.add(e);
    }

    /**
     * Retrieves an entry from arraylist
     * 
     * @param ID - Product ID of object to be retrieved
     */
    @Override
    public Entry readEntry(String ID) {
        for (Entry entry : li) {
            if(entry.getProductID() == ID)
                return entry;
        }
         return null;
    }

    @Override
    public void updateEntry(String ID, Entry e) {
        // TODO Auto-generated method stub
    }

    /**
     * Deletes an entry from arraylist
     * 
     * @param id - Product id of object to be removed
     */
    @Override
    public void deleteEntry(String id) {
        for (int i = 0; i < li.size(); i++) {
            if (li.get(i).getProductID() == id)
                li.remove(i);
        }      
    } 
}
