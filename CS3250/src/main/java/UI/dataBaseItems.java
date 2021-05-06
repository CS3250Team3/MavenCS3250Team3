package UI;

import javafx.beans.property.SimpleStringProperty;

/**
 * Holds one line of database and gets and sets the respective variables
 * 
 * @author Kyle Brown, Hunter DeArment
 */
public class dataBaseItems {


    public final SimpleStringProperty productID = new SimpleStringProperty();
    public final SimpleStringProperty stockQuantity = new SimpleStringProperty();
    public final SimpleStringProperty wholesaleCost = new SimpleStringProperty();
    public final SimpleStringProperty salePrice = new SimpleStringProperty();
    public final SimpleStringProperty supplierID = new SimpleStringProperty();

   // StringProperty productID;
    //IntegerProperty stockQuantity;
    //FloatProperty wholesaleCost,salePrice;
    //StringProperty supplierID;

    /**
     * Constructor for database item class
     * 
     * @param productID - holds the product id column information
     * @param stockQuantity - holds the stock quantity column informatio
     * @param wholesaleCost - holds the wholesale cost column information
     * @param salePrice - holds sale price column information
     * @param supplierID - holds the supplier id column information
     */
    public dataBaseItems(String productID, String stockQuantity, String wholesaleCost, String salePrice, String supplierID) {
        this.productID.set(productID);
        this.stockQuantity.set(stockQuantity);
        this.wholesaleCost.set(wholesaleCost);
        this.salePrice.set(salePrice);
        this.supplierID.set(supplierID);
    }
  
    
    /** 
     * Gets product id
     * @return - Returns product id
     */
    public final String getProductID() {
        return this.productID.get();
    }

    
    /** 
     * Gets stock Quantity
     * @return Returns stockQuantity
     */
    public final String getStockQuantity() {
        return String.valueOf(this.stockQuantity.get()); 
    }

    
    /** 
     * Gets wholesale cost
     * @return - returns wholesale cost
     */
    public final String getWholesaleCost() {
        return String.valueOf(wholesaleCost.get());
    }

    
    /** 
     * Gets sale price
     * @return - returns sale price
     */
    public final String getSalePrice() {
        return this.salePrice.get();
    }


    
    /** 
     * Gets supplier id
     * @return - returns supplier id
     */
    public final String getSupplierID(){
        return this.supplierID.get();
    }

    
    /** 
     * Sets the product id
     * @param productID - holds the product id
     */
    public final void setProductID(String productID) {
        this.productID.set(productID);
    }

    
    /** 
     * Sets the stock quantity
     * @param stockQuantity - holds the stock quantity
     */
    public final void setQuantity(String stockQuantity) {
        this.stockQuantity.set(stockQuantity);
    }

    
    /** 
     * Sets the wholesale cost
     * @param wholesaleCost - holds the wholesale cost
     */
    public final void setCost(String wholesaleCost) {
        this.wholesaleCost.set(wholesaleCost);
    }

    
    /** 
     * Gets the sale price
     * @param salePrice - holds the sale price
     */
    public final void setSale(String salePrice) {
        this.salePrice.set(salePrice);
    }

    

}
