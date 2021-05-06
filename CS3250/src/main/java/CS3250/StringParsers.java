package CS3250;

import java.io.BufferedReader;
import java.io.FileReader;

public class StringParsers {

    
    /** 
     * Parses a string to be used for database connection
     * @param s - String to be parsed
     * @return String[] - Array containing DB IP, Username and Password
     */
    public String[] parseConnectionString(String s){
        String[] arr = new String[3];
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
        arr[0] = information[0];
        arr[1] = information[1];
        arr[2] = information[2];
        return arr;
    }
    
    /** 
     * Reads a configuration file containing database connection
     * 
     * @param filename
     * @return String - DB connection string
     */
    public static String readConfig(String filename) {
        // Open and read file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String dbConnection = reader.readLine();
            reader.close();
            return dbConnection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
