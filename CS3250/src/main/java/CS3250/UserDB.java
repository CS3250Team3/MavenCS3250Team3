package CS3250;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class UserDB implements DataMan<User>{


    String connectionString = "";
    String username = "";
    String password = "";
    Connection con;
    Statement st;
    ResultSet rs;
    
 

    
    /** 
     * Retrieves all users from database 
     * 
     * @return List<User>
     */
    @Override
    public List<User> getEntries() {
        String statement = "SELECT * FROM Users;";
        ArrayList<User> arr = new ArrayList<User>();
        try {

            rs = st.executeQuery(statement);
            while(rs.next()){
                User currUser = new User();
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

   


    @Override
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
     * Creates a user entry on database
     * 
     * @param ID
     * @param e
     */
    @Override
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
     * Retrieves user object from database
     * 
     * @param username
     * @return User
     */
    @Override
    public User readEntry(String username) {
        byte[] usrnm = null;
        try {
            usrnm = UserAuthenticator.getEncryptedUsername(username);
        } catch (InvalidKeySpecException e1) {
            e1.printStackTrace();
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        String statement = "SELECT * FROM Users;";
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
                    if(Arrays.equals(currUser.getUsername(), usrnm)){
                        return currUser;
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    /** 
     * Updates user info on database
     * 
     * @param ID
     * @param e
     */
    @Override
    public void updateEntry(String ID, User e) {
        deleteEntry("" + e.getID());
        createEntry("holder",e);
        
    }

    
    /** 
     * Deletes user from database 
     * 
     * @param id
     */
    @Override
    public void deleteEntry(String id) {
        String statement = "DELETE FROM Users WHERE ID ='"+ id + "';";
        try {
            st.execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
