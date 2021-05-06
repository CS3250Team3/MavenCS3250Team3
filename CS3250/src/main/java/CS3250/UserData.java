package CS3250;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/**
 * Class to connect to and alter a database containing a list of users
 */
public class UserData {

    String connectionString = "";
    String username = "";
    String password = "";
    Connection con;
    Statement st;
    ResultSet rs;
    
    
    
    /** 
     * Returns a list of all User objects from the database
     * @return ArrayList<User> - List containing all users
     */
    public ArrayList<User> getUsers() {
        String statement = "SELECT * FROM Users;";
        ArrayList<User> arr = new ArrayList<User>();
        try {

            User currUser = new User();
            rs = st.executeQuery(statement);
            while(rs.next()){
                byte[] c = rs.getBytes("Username");
                    currUser.setUsername(c);
                    currUser.setPassword(rs.getBytes(2));
                    currUser.setSalt(rs.getBytes(3));
                    currUser.setID(rs.getInt("ID"));
                    currUser.setEmail(rs.getString("email"));


                    currUser.setRole(rs.getString("role"));


                    arr.add(currUser);
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }

    
    
    /** 
     * Connects to database using connection string and confirms connection
     * @param filename - String containing database connection, username and password
     */
    public void initializeDatabase(String filename) {
        parseString(filename);
        try {
            con = (Connection) DriverManager.getConnection(connectionString, username, password);
            st =  (Statement) con.createStatement(); 
            rs = st.executeQuery("SELECT VERSION()");
            if(rs.next()){
                System.out.println("Connected to..." + rs.getString(1) + " Users");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    
    /** 
     * Parses a string containing the database connection, username and password
     * @param s - String to be parsed
     */
    private void parseString(String s){
        s+=" ";
        String buffer = "";
        String[] information = new String[3];
        int place = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' '){
                information[place] = buffer;
                buffer = "";
                place++;
            }
            else{
                buffer+= s.charAt(i);
            }
        }
        connectionString = information[0];
        username = information[1];
        password = information[2];
    }

   
    
    /** 
     * Inserts a user into the database
     * @param ID - ID to be used for indexing
     * @param e - User to be stored
     */
    public void createEntry(String ID, User e) {        
        byte[] ubytes = e.getUsername(); 
        byte[] pbytes = e.getPassword();
        byte[] sbytes = e.getSalt();
        String email = e.getEmail();

        String sql = "INSERT INTO Users(Username,Password,salt,email,role) VALUES(?,?,?,?,?)";

        try (PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql)) {
                pst.setBytes(1, ubytes);
                pst.setBytes(2, pbytes);
                pst.setBytes(3, sbytes);
                pst.setString(4, email);


                pst.setString(5, e.getRole());

                pst.executeUpdate();
        } catch (SQLException ex) {
        }
    }
  
    /** 
     * Returns a list of all User objects from the database who match a given ID
     * @return ArrayList<User> - List containing all users matching ID
     */
    public CS3250.User getUser(int id) {
        String statement = "SELECT * FROM Users WHERE ID = '" + id + "';";
        try {
                User currUser = new User();
            rs = st.executeQuery(statement);
            while(rs.next()){
                    currUser.setUsername(rs.getBytes(1));
                    currUser.setPassword(rs.getBytes(2));
                    currUser.setSalt(rs.getBytes(3));
                    currUser.setID(rs.getInt("ID"));
                    currUser.setEmail(rs.getString("email"));


                    currUser.setRole(rs.getString("role"));

                    return currUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


  /** 
     * Returns a list of all User objects from the database who match a username
     * @param username - Hashed username of the user to be looked for on the database
     * @return ArrayList<User> - List of all matching users
     */
   public ArrayList<User> getUser(byte[] username) {
        String statement = "SELECT * FROM Users;";
        ArrayList<User> arr = new ArrayList<User>();
        try {
                User currUser = new User();
            rs = st.executeQuery(statement);
            while(rs.next()){
                byte[] c = rs.getBytes("Username");
                byte[] tc = username;
                if (Arrays.equals(c, tc)) {
                    currUser.setUsername(username);
                    currUser.setPassword(rs.getBytes(2));
                    currUser.setSalt(rs.getBytes(3));
                    currUser.setID(rs.getInt("ID"));
                    currUser.setEmail(rs.getString("email"));


                    currUser.setRole(rs.getString("role"));

                    arr.add(currUser);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }
   
    
    /** 
     * Edits a user on the database
     * @param ID - Not used
     * @param e - User object with updated fields
     */
    public void updateUser(int ID, User e) {
        deleteUser(e.getID());
        createEntry("holder",e);

    }

  
    
    /** 
     * Deletes a user from the database
     * @param id - ID of the user to be deleted
     */
    public void deleteUser(int id) {
        String statement = "DELETE FROM Users WHERE ID ='"+ id + "';";
        try {
            st.execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   

        
    /** 
     * Saves a user to the database
     * @param e - User to be saved
     */
    public void saveEntry(User e) {
        String newEntry = String.valueOf(e.getID());
        createEntry(newEntry, e);
    }
    

    
    /** 
     * Returns the size of the user table
     * @return int - Total number of Users stored in the database
     */
    public int retSize() {
        String statement = "SELECT COUNT(*) FROM Users;";
        try {
            rs = st.executeQuery(statement);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        

    }
    
}
