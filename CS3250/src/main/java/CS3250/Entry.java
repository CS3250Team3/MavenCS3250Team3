package CS3250;

/**
 *  Class to store various product fields for later database storage
 */
public class Entry {
    private String productID;
    private String supplierID;
    private int stockQuantity;
    private double wholesaleCost;
    private double salePrice;
    
	
	/** 
	 * Returns product ID
	 * @return String
	 */
	public String getProductID() {
		return productID;
	}
	
	
	/** 
	 * Sets product ID
	 * @param productID
	 */
	public void setProductID(String productID) {
		this.productID = productID;
	}
	
	
	/** 
	 * Returns supplier ID
	 * @return String
	 */
	public String getSupplierID() {
		return supplierID;
	}
	
	
	/** 
	 * Sets supplier ID
	 * @param supplierID
	 */
	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}
	
	
	/** 
	 * Returns quantity of product currently in stock
	 * @return int
	 */
	public int getStockQuantity() {
		return stockQuantity;
	}
	
	
	/** 
	 * Sets the stock quantity
	 * @param stockQuantity
	 */
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	
	
	/** 
	 * Returns wholesale cost
	 * @return double
	 */
	public double getWholesaleCost() {
		return wholesaleCost;
	}
	
	
	/** 
	 * Sets wholesale cost
	 * @param wholesaleCost
	 */
	public void setWholesaleCost(double wholesaleCost) {
		this.wholesaleCost = wholesaleCost;
	}
	
	
	/** 
	 * Returns sale price
	 * @return double
	 */
	public double getSalePrice() {
		return salePrice;
	}
	
	
	/** 
	 * Sets sale price
	 * @param salePrice
	 */
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
}
