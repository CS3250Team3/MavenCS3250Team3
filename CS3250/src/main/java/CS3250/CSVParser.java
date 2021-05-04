package CS3250;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import UI.observablePO;

/*
  This class will eventually hook up to our database,
  so in the future it might change from adding the entries 
  to a HashMap to instead calling the createEntry method
  to add it straight in to the DB
 */
public class CSVParser {
	
	/**
	 * Parses a CSV file full of products into Entry objects
	 *  
	 * @param filename - Path to the csv file to be parsed
	 * @param database - Database object
	 * 
	 */
	public void readProductsCSV(String filename, DataInterface database){
		String line;  	// Current row contents
		String[] fields;// Array to store individual product fields
		
		// Try to open the file and start reading
		try (InputStream inputStream = getClass().getResourceAsStream(filename);
			    BufferedReader reader = new BufferedReader(new FileReader(filename))) {
				reader.readLine();
			    while((line = reader.readLine()) != null) {
			    	Entry newEntry = new Entry(); // Create new entry
			    	fields = line.split(",");     // Split the row into individual fields
			    	
			    	// Fill in newEntry's fields
			    	newEntry.setProductID(fields[0]);
			    	newEntry.setStockQuantity(Integer.parseInt(fields[1]));
			    	newEntry.setWholesaleCost(Double.parseDouble(fields[2]));
			    	newEntry.setSalePrice(Double.parseDouble(fields[3]));
			    	newEntry.setSupplierID(fields[4]);
			    	
			    	// Add the entry to the result map
			    	database.createEntry(newEntry.getProductID(), newEntry);
			    }
		} catch (IOException e) {
				e.printStackTrace();
		}
		return;
	}

/**
	 * Parses a CSV file full of products into Entry objects
	 *  
	 * @param filename - Path to the csv file to be parsed
	 * @param database - Database object
	 * 
	 */
	public void readOrdersCSV(String filename, SQLPo newEntry, SQLData inventory, boolean isReversed){
		String line;  	// Current row contents
		String[] fields;// Array to store individual product fields
		
		// Try to open the file and start reading
		try (InputStream inputStream = getClass().getResourceAsStream(filename);
			    BufferedReader reader = new BufferedReader(new FileReader(filename))) {
				reader.readLine();
			    while((line = reader.readLine()) != null) {
			    	fields = line.split(",");     // Split the row into individual fields

			    	// Fill in fields
					if(isReversed) {
						undoInventoryUpdate(fields[3], Integer.parseInt(fields[4]), inventory, newEntry);
					}
					else {
						populateDB(fields[0], fields[1], fields[2], fields[3], fields[4], newEntry);
					}
			    }
		} catch (IOException e) {
				e.printStackTrace();
		}
		return;
	}

	

	public void undoInventoryUpdate(String productID, int orderedQuantity, SQLData inventory, SQLPo PoDB) {
		Entry inventoryItem = inventory.readEntry(productID);
		if(inventoryItem == null) {
			System.out.println("Ordered item " + productID + " doesn't exist!");
		}
		else {
			int currentQuantity = inventoryItem.getStockQuantity();
			inventoryItem.setStockQuantity(currentQuantity + orderedQuantity);
			inventory.updateEntry(productID, inventoryItem);
		}
	}

	private void populateDB(String date, String customerEmail, String customerLocation, String productID, 
							String quantity, SQLPo PoDB) {

		observablePO po = new observablePO();
		po.setDate(date);
		po.setEmail(customerEmail);
		po.setCustomerLocation(customerLocation);
		po.setProductID(productID);
		po.quantity(quantity);

		PoDB.createEntry("1", po);
		 
		return;
	}

	
}
