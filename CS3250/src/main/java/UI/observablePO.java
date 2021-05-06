package UI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class used to store customer orders in a format readable to the UI
 */
public class observablePO {
    StringProperty ID = new SimpleStringProperty();
    StringProperty productID = new SimpleStringProperty();
    StringProperty quantity=new SimpleStringProperty();
    StringProperty customerLocation = new SimpleStringProperty();
    StringProperty date = new SimpleStringProperty();
    StringProperty email = new SimpleStringProperty();

    
    /** 
     * @return String
     */
    public String getCustomerLocation() {
        return customerLocation.get();
    }
    
    /** 
     * @return String
     */
    public String getQuantity() {
        return quantity.get();
    }

    
    /** 
     * @return String
     */
    public String getEmail() {
        return email.get();
    }
    
    /** 
     * @param email
     */
    public void setEmail(String email) {
        this.email.set(email);
    }
    
    /** 
     * @return String
     */
    public String getProductID() {
        return productID.get();
    }
    
    /** 
     * @param date
     */
    public void setDate(String date) {
        this.date.set(date);
    }
    
    /** 
     * @return String
     */
    public String getDate() {
        return date.get();
    }
    
    /** 
     * @param ProductID
     */
    public void setProductID(String ProductID) {
        this.productID.set(ProductID);
    }
    
    /** 
     * @param q
     */
    public void quantity(String q) {
        this.quantity.set(q);
    }
    
    /** 
     * @param customerLocation
     */
    public void setCustomerLocation(String customerLocation) {
        this.customerLocation.set(customerLocation);
    }
    
    /** 
     * @param iD
     */
    public void setID(String iD) {
        ID.set(iD);;
    }
    
    /** 
     * @return String
     */
    public String getID() {
        return ID.get();
    }
}