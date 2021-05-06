package CS3250;

import java.util.ArrayList;
import java.util.List;

import UI.observablePO;

public class MockPODB  implements DataMan<observablePO>{

    List<observablePO> li;
    @Override
    public List<observablePO> getEntries() {
        return li;
    }

    /**
     * Initialize arraylist of observable PO's
     * 
     * @param connection - unused
     */
    @Override
    public void initializeDatabase(String connection) {
        var e1 = new observablePO();
        
        e1.setProductID("productID");
        e1.setCustomerLocation("customerLocation");
        e1.setDate("12-3-12");
        e1.setEmail("email");
        e1.setID("iD");

        li = new ArrayList<observablePO>();
        li.add(e1);

    }

    /**
     * Adds an observablePO object to arraylist
     * 
     * @param ID - Id of object to be added, unused
     * @param e - Entry object to be added
     */
    @Override
    public void createEntry(String ID, observablePO e) {
        li.add(e);
    }

    /**
     * Retrieves an observablePO from arraylist
     * 
     * @param ID - Product ID of object to be retrieved
     * @return observablePO - Target po
     */
    @Override
    public observablePO readEntry(String ID) {
        for (observablePO entry : li) {
            if(entry.getProductID() == ID)
                return entry;
        }
         return null;
    }

    @Override
    public void updateEntry(String ID, observablePO e) {
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
