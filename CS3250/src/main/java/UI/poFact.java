package UI;

import javafx.beans.property.SimpleStringProperty;

/**
 * Holds one line of the PO database
 * 
 * @author Kyle Brown
 */

public class poFact {

    public final SimpleStringProperty userID = new SimpleStringProperty();
    public final SimpleStringProperty total = new SimpleStringProperty();
    public final SimpleStringProperty date = new SimpleStringProperty();
    public final SimpleStringProperty ID = new SimpleStringProperty();

    /**
     * Constructor for the poFact class
     * 
     * @param userID - holds the userID
     * @param date - holds the date
     * @param total - holds the total
     * @param ID - holds the item ID
     */
    public poFact(String userID, String date, String total, String ID){
        this.userID.set(userID);
        this.total.set(total);
        this.date.set(date);
        this.ID.set(ID);
    }
    /**
     * Gets the userID
     * 
     * @return - returns the userID
     */
    public final String getUserID(){
        return this.userID.get();
    }

    /**
     * Gets the total
     * 
     * @return - returns the total
     */
    public final String getTotal(){
        return this.total.get();
    }

    /**
     * Gets the date
     * 
     * @return - returns the date
     */
    public final String getDate(){
        return this.date.get();
    }

    /**
     * Gets the ID
     * 
     * @return - returns the ID
     */
    public final String getID(){
        return this.ID.get();
    }

    /**
     * Sets the userID
     * 
     * @param userID - holds the userID
     */
    public final void setUserID(String userID){
        this.userID.set(userID);
    }

    /**
     * Sets the total
     * 
     * @param total - holds the total
     */
    public final void setTotal(String total){
        this.total.set(total);
    }

    /**
     * Sets the date
     * 
     * @param date - holds the date
     */
    public final void setDate(String date){
        this.date.set(date);
    }

    /**
     * Sets the ID
     * 
     * @param ID - holds the ID
     */
    public final void setID(String ID){
        this.ID.set(ID);
    }







    
}
