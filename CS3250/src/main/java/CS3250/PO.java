package CS3250;

/**
 * Class to store various fields of customer orders to add to database
 */
public class PO {
    int ID = 0;
    String productID = "";
    int quantity = 0;
    String customerLocation = "";
    String date = "";
    String email = "";

    
    /** 
     * 
     * @return String - Customer location
     */
    public String getCustomerLocation() {
        return customerLocation;
    }
    
    /**
     * 
     * @return int - Order quantity
     */
    public int getQuantity() {
        return quantity;
    }

    
    /** 
     * @return String
     */
    public String getEmail() {
        return email;
    }
    
    /** 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /** 
     * @return String
     */
    public String getProductID() {
        return productID;
    }
    
    /** 
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }
    
    /** 
     * @return String
     */
    public String getDate() {
        return date;
    }
    
    /** 
     * @param ProductID
     */
    public void setProductID(String ProductID) {
        this.productID = ProductID;
    }
    
    /** 
     * @param q
     */
    public void quantity(int q) {
        this.quantity = q ;
    }
    
    /** 
     * @param iD
     */
    public void setID(int iD) {
        ID = iD;
    }
    
    /** 
     * @return int
     */
    public int getID() {
        return ID;
    }
}