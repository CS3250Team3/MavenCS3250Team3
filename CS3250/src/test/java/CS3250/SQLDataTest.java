package CS3250;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import CS3250.DataInterface;
import CS3250.Entry;
import CS3250.SQLData;

public class SQLDataTest {
    DataInterface init = new SQLData();
    @Test
    void ConnectionWorks(){
        init.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");
        CSVParser cv = new CSVParser();
        System.out.println("...done");
        assertNotEquals(init.getEntries(), null);
    }
    


}
