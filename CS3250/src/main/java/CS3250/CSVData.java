// package CS3250;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.Vector;

// /**
//  * Class to create, populate and edit a psuedodatabase
//  */
// public class CSVData implements DataInterface {

//     private Vector<Entry> Data = new Vector<Entry>();
//     private int NumEntries;
//     private HashMap<String, Entry> initialData = new HashMap<String, Entry>();

    
//     /** 
//      * Parses a CSV file containing products and stores them in a psuedodatabase of entry objects
//      * 
//      * @param filename
//      */
//     @Override
//     public void initializeDatabase(String filename) {
//         // Initialize a HashMap that stores all inventory data
//         HashMap<String, Entry> initData = new HashMap<String, Entry>();
//         // Initialize a new parser
//         CSVParser parse = new CSVParser();

//         // Load the HashMap with entries
//         parse.readProductsCSV(filename, this);

//         // Track how many entries were added
//         this.NumEntries = initialData.size();

//     }

    
//     /** 
//      * Adds an entry object to the psuedodatabase
//      * 
//      * @param ID - Product ID to be used for lookup key
//      * @param e - Entry object to be added
//      */
//     @Override
//     public void createEntry(String ID, Entry e) {
//         // Put into hashmap and creates a new entry
//         initialData.put(ID, e);
//     }

    
//     /** 
//      * Returns an entry to be read from psuedodatabase
//      * @param ID - Product ID
//      * @return Entry
//      */
//     @Override
//     public Entry readEntry(String ID) {
//         // What entry is to be read from the hashmap
//         return initialData.get(ID);
//     }

    
//     /** 
//      * Updates an entry in the psuedodatabase
//      * @param ID - Product ID
//      * @param e - Entry
//      */
//     @Override
//     public void updateEntry(String ID, Entry e) {
//         // we will just replace the entry with a new one that
//         // auto fills the information that the user did not want to
//         // change.
//         deleteEntry(ID);
//         createEntry(ID, e);
//     }

    
//     /** 
//      * Deletes a product from the psuedodatabase
//      * @param id - Product ID
//      */
//     @Override
//     public void deleteEntry(String id) {
//         // deletes entry using id provided
//         initialData.remove(id);
//     }

    
//     /** 
//      * Saves an entry into the psuedodatabase
//      * @param e - Product ID
//      */
//     @Override
//     public void saveEntry(Entry e) {
//         createEntry(e.getProductID(), e);
//     }

    
//     /** 
//      * Returns the number of entries in the psuedodatabase
//      * @return int
//      */
//     @Override
//     public int retSize() {
//         // returns size of HashMap to test delete function
//         return initialData.size();
//     }

    
//     /** 
//      * Returns a list of all entries in psuedodatabase
//      * @return ArrayList<Entry>
//      */
//     @Override
//     public ArrayList<Entry> getEntries() {
//         // TODO Auto-generated method stub
//         return null;
//     }
// }
