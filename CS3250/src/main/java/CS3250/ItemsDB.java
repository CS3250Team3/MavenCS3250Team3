package CS3250;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


/**
 * Class to connect to and edit a MySQL database containing various product entries
 */
public class ItemsDB implements DataMan<Entry> {

    String connectionString = "";
    String username = "";
    String password = "";
    Connection con;
    Statement st;
    ResultSet rs;

    
    /** 
     * Connects to Database using a connection string and confirms connection
     * @param filename - Name of file containing db connection string
     */
    @Override
    public void initializeDatabase(String filename) {
        // Connect to database
        StringParsers s = new StringParsers();
        var a = s.parseConnectionString(filename);
        connectionString = a[0];
        username = a[1];
        password = a[2];
        try {
            con = (Connection) DriverManager.getConnection(connectionString, username, password);
            st =  (Statement) con.createStatement();
            rs = st.executeQuery("SELECT VERSION()");
            if(rs.next()){
                System.out.println("Connected to..." + rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    /** 
     * Inserts an entry into the database
     * @param ID - Product ID
     * @param e - Entry object to be inserted
     */
    @Override
    public void createEntry(String ID, Entry e) {
        String statement="INSERT INTO DataEntries(productID,supplierID,stockQuantity,WholesaleCost,salePrice) VALUES('" + e.getProductID() + "', '" + e.getSupplierID() + "' , '"+ e.getStockQuantity() + "' , '" + e.getWholesaleCost() + "' , '" + e.getSalePrice() + "');" ;
        try {
            st.execute(statement);    
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        
        
    }

    
    /** 
     * Returns an entry from the database
     * @param ID - Product ID of product to be read
     * @return Entry - Entry to be read
     */
    @Override
    public Entry readEntry(String ID) {
        String statement = "SELECT * FROM DataEntries HAVING productID ='"+ ID + "';";
        String s = "";
        Entry result = new Entry();
        try {
            // Retrieve entries
            rs = st.executeQuery(statement);
            if(!rs.next()) {
                return null;
            }
            // Format entries
            System.out.println(rs.getString("productID"));
            s += rs.getString(1);
            s += "_" +  rs.getString(2);
            s += "_" +  rs.getString(3);
            s += "_" +  rs.getString(4);
            s +=  "_" + rs.getString(5);
            result = parseEntry(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /** 
     * Pulls all entries from DB's product table and returns them as a list
     * @return ArrayList<Entry> - List containing all entries from database
     */
    public ArrayList<Entry> getEntries(){
        String statement = "SELECT * FROM DataEntries;";
        String s = "";
        ArrayList<Entry> al = new ArrayList<Entry>();       
        try {
            // Retrieve entries
            rs = st.executeQuery(statement);
            
            // Format entries
            while(rs.next()){
            s += rs.getString(1);
            s += "_" +  rs.getString(2);
            s += "_" +  rs.getString(3);
            s += "_" +  rs.getString(4);
            s +=  "_" + rs.getString(5);
            al.add(parseEntry(s));
            s = "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
       

    }
    
    /** 
     * Creates a new entry object from a string
     * @param s - String containing product ID, supplier ID, stock quantity, wholesale cost and sale price of a product
     * @return Entry - Entry object filled with fields from string
     */
    private Entry parseEntry(String s){
        Entry e = new Entry();
        var ar = s.split("_");

        // Populate object
        e.setProductID(ar[0]);
        e.setSupplierID(ar[1]);
        e.setStockQuantity(Integer.parseInt(ar[2]));
        e.setWholesaleCost(Double.parseDouble(ar[3]));
        e.setSalePrice(Double.parseDouble(ar[4]));
        return e;
    }

    
    /** 
     * Updates an existing entry in the database
     * @param ID - Product ID of the item to be updated
     * @param e - Entry containing updated fields
     */
    @Override
    public void updateEntry(String ID, Entry e) {
        deleteEntry(ID);
        createEntry(ID,e);
    }

    
    /** 
     * Deletes an entry from the database
     * @param ID - Product ID of item to be deleted
     */
    @Override
    public void deleteEntry(String ID) {
        String statement = "DELETE FROM DataEntries WHERE productID ='"+ ID + "';";
        try {
            st.execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}