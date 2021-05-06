package CS3250;

import java.util.ArrayList;
import java.util.List;

public class MockUserDB implements DataMan<User>{

    List<User> li;
    @Override
    public List<User> getEntries() {
        return li;
    }

    /**
     * Initialize arraylist of User objects
     * 
     * @param connection - unused
     */
    @Override
    public void initializeDatabase(String connection) {
        var e1 = new User();
        
        e1.setPassword("password".getBytes());
        e1.setRole("role");
        e1.setUsername("username".getBytes());
        e1.setEmail("email");
        e1.setSalt("salt".getBytes()); 
        li = new ArrayList<User>();
        li.add(e1);

    }

    /**
     * Adds an User object to arraylist
     * 
     * @param ID - Id of object to be added, unused
     * @param e - Entry object to be added
     */
    @Override
    public void createEntry(String ID, User e) {
        li.add(e);
    }

    /**
     * Retrieves a User from arraylist
     * 
     * @param ID - Product ID of object to be retrieved
     * @return User - Target object
     */
    @Override
    public User readEntry(String ID) {
        for (User entry : li) {
            if(new String(entry.getUsername()) == ID)
                return entry;
        }
         return null;
    }

    @Override
    public void updateEntry(String ID, User e) {
        // TODO Auto-generated method stub
    }

    /**
     * Deletes a User from arraylist
     * 
     * @param id - username of object to be removed
     */
    @Override
    public void deleteEntry(String id) {
        for (int i = 0; i < li.size(); i++) {
            if (new String(li.get(i).getUsername()) == id)
                li.remove(i);
        }
        
    }
    
}
