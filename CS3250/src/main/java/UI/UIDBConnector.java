package UI;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import CS3250.StringParsers;
import CS3250.DataMan;
import CS3250.Entry;
import CS3250.ItemsDB;
import CS3250.PODB;
import CS3250.UserData;


/**
 * Connects to the mySql database
 * @author Kyle Brown
 */
public class UIDBConnector {
    UserData data = new UserData();
    ItemsDB itemsDB = new ItemsDB();
    PODB podb = new PODB();
    /**
     * Connects to database
     * @return - Returns mySql connection
     * @throws SQLException - throws if mySql error
     * @throws IOException
     */
    public static Connection getConnection() throws SQLException, IOException{
        String connectionString = StringParsers.readConfig("config");
        StringParsers p = new StringParsers();
        String[] dbConnection = p.parseConnectionString(connectionString);
        Connection connection = DriverManager.getConnection(dbConnection[0], dbConnection[1], dbConnection[2]);
        return connection;
    }
    /**
     * 
     * @return - Database object connected to inventory table
     * @throws SQLException
     */
    public DataMan<Entry> getItemsConnection() throws SQLException{
        itemsDB.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");
        return itemsDB;
    }
    /**
     * @return - Database object connected to customer orders table
     * @throws SQLException
     */
    public DataMan<observablePO> getPOConnection() throws SQLException{
        podb.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");
        return podb;
    }


}
