package CS3250;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import UI.observablePO;

public class SQLPo {
    SQLData inventory = new SQLData();
    String connectionString = "";
    String username = "";
    String password = "";
    Connection con;
    Statement st;
    ResultSet rs;

     /** 
     * Initializes connection to main database
     * 
     * @param filename - Name of file containing db connection string
     */
    public void initializeDatabase(String filename) {

        StringParsers s = new StringParsers();
        var a = s.parseConnectionString(filename);
        connectionString = a[0];
        username = a[1];
        password = a[2];
        try {
            con = (Connection) DriverManager.getConnection(connectionString, username, password);
            inventory.con = this.con;
            st = (Statement) con.createStatement();
            inventory.st = this.st;
            rs = st.executeQuery("SELECT VERSION()");
            inventory.rs = this.rs;
            if (rs.next()) {
                System.out.println("Connected to..." + rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     /** 
     * Creates a new customer order and adds to the database
     * 
     * @param ID - Unused
     * @param p - observablePO to be added
     */
    public void createEntry(String ID, observablePO p) {
        Entry inventoryItem = inventory.readEntry(p.getProductID());
        int quantity = Integer.parseInt(p.getQuantity());
        if(poExists(p.getProductID(), quantity, p.getDate(), p.getEmail(), p.getCustomerLocation())) {
            System.out.println("Order already exists");
            return;
        }
        if(inventoryItem == null) {
            System.out.println("Ordered item " + p.getProductID() + " doesn't exist!");
            return;
        }
        else {
            if(inventoryItem.getStockQuantity() < quantity) {
                System.out.println("Order quantity exceeds quantity in inventory!");
                return;
            }
            else {
                int currentQuantity = inventoryItem.getStockQuantity();
                inventoryItem.setStockQuantity(currentQuantity - quantity);
                inventory.updateEntry(p.getProductID(), inventoryItem);
            }
        }
        String statement = "INSERT INTO PO(productID,quantity,date,email,custLoc) VALUES('" + p.getProductID() + "', '" + p.getQuantity()
            + "' , '" + p.getDate() + "' , '" + p.getEmail() + "' , '" + p.getCustomerLocation()  +"');";
        try {
            st.execute(statement);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    /** 
     * Retrieves all customer orders from database
     * 
     * @return List<observablePO> - List of all orders in database
     */
    public List<UI.observablePO> GenerateShortPOs(){
        List<UI.observablePO> arr = new ArrayList<UI.observablePO>();
        String statement2 = "SELECT * FROM PO;";
        UserData u = new UserData();
        u.initializeDatabase(connectionString + " " + username + " " + password);
        UI.observablePO po = new UI.observablePO();
        try{
            rs = st.executeQuery(statement2);
            while (rs.next()) {
                po = new UI.observablePO();
                po.setProductID(rs.getString("productID"));
                po.setEmail( rs.getString("email"));
                po.setDate(rs.getString("date"));
                po.setID(rs.getString("ID"));
                po.setCustomerLocation(rs.getString("custLoc"));
                po.quantity(rs.getString("quantity"));
                arr.add(po);
            }             
        }
        catch(Exception e){
            System.out.println(e);
        }
        return arr;
    }

    /**
     * Retrieves a PO object from database
     * 
     * @param ID
     * @return PO
     */
    public PO getPo(int ID) {
        String statement2 = "SELECT * FROM PO WHERE id = '" + ID + "';";
        PO po = new PO();
        try {
            rs = st.executeQuery(statement2);
            rs.next();
            po.setDate(rs.getString("date"));
            po.setID(rs.getInt("ID"));
            po.setProductID(rs.getString("productID"));
            po.customerLocation = rs.getString("custLoc");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return po;
    }

    /** 
     * Checks to see if customer order is already in database
     * 
     * @param PID - Product ID
     * @param quantity
     * @param date
     * @param email
     * @param location
     * @return boolean - True if order already exists in database, false otherwise
     */
    public boolean poExists(String PID, int quantity, String date, String email, String location) {
        String statement2 = "SELECT * FROM PO WHERE productID = '" + PID + "' AND quantity = '" + quantity + "' AND date = '" + date + "' AND email = '" + email + "' AND custLoc = '" + location + "';";       
        try {
            rs = st.executeQuery(statement2);
            return rs.next();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return false;
    }

    /** 
     * 
     * @param ID
     * @param e
     */
    public void updateInventory(String productID, int orderedQuantity) {
		// TODO
	}

    public void updateEntry(String ID, Entry e) {
        // TODO Auto-generated method stub
    }

    /** 
     * Deletes a customer order from database
     * 
     * @param id
     * @param email
     * @param quantity
     */
    public void deleteEntry(String id, String email, String quantity) {
        String statement = "DELETE FROM POItems WHERE productID ='"+ id + "' AND email = '" + email + "' AND quantity = '" + quantity + "';";
        try {
            st.execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 
     * @param e
     */
    public void saveEntry(Entry e) {
        // TODO Auto-generated method stub
    }

    /** 
     * @return int
     */
    public int retSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * 
     * @param statement2
     * @return
     */
    public List<UI.observablePO> GenerateWeeklyPO(String statement2){
        List<UI.observablePO> arr = new ArrayList<UI.observablePO>();
        UI.observablePO po = new UI.observablePO();
        try{
            rs = st.executeQuery(statement2);
            while (rs.next()) {
                po = new UI.observablePO();
                po.setProductID(rs.getString("productID"));
                po.setEmail( rs.getString("email"));
                po.setDate(rs.getString("date"));
                po.setID(rs.getString("ID"));
                po.setCustomerLocation(rs.getString("custLoc"));
                po.quantity(rs.getString("quantity"));
                arr.add(po);
            }             
        }
        catch(Exception e){
            System.out.println(e);
        }
        return arr;
    }

}
