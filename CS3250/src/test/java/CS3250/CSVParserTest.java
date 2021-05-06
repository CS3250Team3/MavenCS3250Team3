package CS3250;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

// import CS3250.CSVData;
import CS3250.CSVParser;

class CSVParserTest {
	CSVParser parse = new CSVParser();
	// CSVData data = new CSVData();
	String file = "customer_orders_team5.csv";

	SQLPo newPo = new SQLPo();
	SQLData inventory = new SQLData();	


	
// 	// @Test
// 	// public void ReadCSVWorks() {
// 	// 	parse.readCSV(file, data);
// 	// 	assertEquals(42585, data.retSize());
// 	// }
	
	@Test
	public void ReadOrdersCSVWorks() {
		String connectionString = StringParsers.readConfig(".config");
		newPo.initializeDatabase(connectionString);
		inventory.initializeDatabase(connectionString);
		parse.readOrdersCSV(file, newPo, inventory,false);
		assert(true);
	}
}

