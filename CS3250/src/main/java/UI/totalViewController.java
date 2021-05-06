package UI;

import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Statement;

import CS3250.DataMan;
import CS3250.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class totalViewController {

    @FXML
    private AnchorPane basePane;

    @FXML
    private JFXButton orders_Btn;

    @FXML
    private JFXButton inv_Btn;

    @FXML
    private JFXButton sale_Btn;

    @FXML
    private JFXButton data_Btn;

    @FXML
    private JFXButton add_Btn;

    @FXML
    private JFXButton del_Btn;

    @FXML
    private JFXButton view_Btn;

    @FXML
    private JFXButton exit_Btn;

    @FXML
    private TableView<?> total_Table;

    @FXML
    private TableColumn<?, ?> cellOne;

    @FXML
    private TableColumn<?, ?> CellTwo;

    @FXML
    private TableColumn<?, ?> cellThree;

    @FXML
    private TableColumn<?, ?> cellFour;

    @FXML
    private TableColumn<?, ?> cellFive;

    @FXML
    private JFXTextField searchBox;

    @FXML
    private TextField textId;

    @FXML
    private TextField textQuantity;

    @FXML
    private TextField textCost;

    @FXML
    private TextField textPrice;

    @FXML
    private TextField textSid;

    @FXML
    private TextField textID;

    @FXML
    private Label textField1;

    @FXML
    private Label textField2;

    @FXML
    private Label textField3;

    @FXML
    private Label textField4;

    @FXML
    private Label textField5;

    @FXML
    private Label textField6;

    @FXML
    private JFXButton maximizeBtn;

    @FXML
    private JFXButton minBtn; 

    ObservableList oblist = FXCollections.observableArrayList();

    @FXML


    public void initialize() throws SQLException, java.io.IOException {
        showOrders();
    }

    @FXML
    public void showInventory() throws SQLException{
       
        orderScreenDisplayed = false;

        total_Table.getItems().clear();
        textField1.setText("   Product Id");
        textField2.setText("   Quantity");
        textField3.setText("   Cost");
        textField4.setText("   Price");
        textField5.setText("   Sid");
        textField6.setText(" ");
        textID.setText("");
        try {
            UIDBConnector udb = new UIDBConnector();

            var items = udb.getItemsConnection();
            List<Entry> rs = items.getEntries();

            for (Entry object : rs) {
                oblist.add(new dataBaseItems(object.getProductID(), Integer.toString(object.getStockQuantity()),
               Double.toString(object.getWholesaleCost()), Double.toString(object.getSalePrice()), object.getSupplierID()));
            }


        }finally{}
        
        cellOne.setText("Product_ID");
        CellTwo.setText("Stock_Quantity");
        cellThree.setText("WholeSale_Cost");
        cellFour.setText("Sale_Price");
        cellFive.setText("Supplier_ID");



        CellTwo.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        cellThree.setCellValueFactory(new PropertyValueFactory<>("wholesaleCost"));
        cellFour.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        cellFive.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        cellOne.setCellValueFactory(new PropertyValueFactory<>("productID"));

        total_Table.setItems(oblist);


        FilteredList<dataBaseItems> filteredData = new FilteredList<>(oblist, p -> true); 
        searchBox.textProperty().addListener((Observable, oldVal, newVal) -> {
            filteredData.setPredicate(dataBaseItems -> { 
                if(newVal == null || newVal.isEmpty()){
                    return true; 
                }
                String lowerFilter = newVal.toLowerCase(); 
                if(dataBaseItems.getProductID().toLowerCase().contains(lowerFilter)){
                    return true;
                }return false;
            });
        });
        //SortedList<dataBaseItems> sortedData = new SortedList<>(filteredData); 
        //sortedData.comparatorProperty().bind(total_Table.comparatorProperty());
        //total_Table.setItems((ObservableList<dataBaseItems>) sortedData);
        
        
    }

 
    ObservableList poList;
    public Boolean orderScreenDisplayed;

    DataMan<observablePO> items;
    @FXML

    public void showOrders() throws SQLException, java.io.IOException{
        UIDBConnector udb = new UIDBConnector();
        items = udb.getPOConnection();

        List<observablePO> rs = items.getEntries();
        orderScreenDisplayed = true; 

        textField1.setText("   Product Id");
        textField2.setText("   Date");
        textField3.setText("   Quantity");
        textField4.setText("   Customer Location");
        textField5.setText("   Email");
        textField6.setText("   ID");

        cellOne.setText("productID");
        CellTwo.setText("Date");
        cellThree.setText("Email");
        cellFour.setText("ID");
        cellFive.setText(" ");
        total_Table.getItems().clear();

        
    

      
        poList = FXCollections.observableArrayList(rs);
        cellOne.setCellValueFactory(new PropertyValueFactory<>("productID"));
        CellTwo.setCellValueFactory(new PropertyValueFactory<>("Date"));
        cellThree.setCellValueFactory(new PropertyValueFactory<>("Email"));
        cellFour.setCellValueFactory(new PropertyValueFactory<>("ID"));
        total_Table.setItems(poList);
    
    FilteredList<observablePO> filteredList = new FilteredList<>(poList);
    searchBox.textProperty().addListener((Observable, oldVal, newVal) -> {
        filteredList.setPredicate(poFact -> { 
            if(newVal == null || newVal.isEmpty()){
                return true; 
            }
            String lowerFilter = newVal.toLowerCase(); 
            if(poFact.getProductID().toLowerCase().contains(lowerFilter)){
                return true;
            }return false;
        });
    });
    //SortedList<observablePO> sortedData = new SortedList<>(filteredList); 
    //total_Table.setItems(sortedData);
    }

    @FXML
    public void inventoryBtn(ActionEvent event){
        inv_Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    showInventory();;
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
    }


    @FXML
    public void ordersBtn(ActionEvent event) {
        orders_Btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override public void handle(ActionEvent e) {
                try {
                    showOrders();
                } catch (Exception e1) {

                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                };
            }
        });
    }

    @FXML
    public void signoutBtn(ActionEvent event) {
        Stage stage = (Stage) exit_Btn.getScene().getWindow();
        stage.close();

}






    @FXML
    public void showReport(ActionEvent event) throws IOException, java.io.IOException, SQLException {

        poStream totalSaleStream = new poStream();

        String[] bestCustomer = totalSaleStream.bestCustomer();
        Double bestCustomerRevenue = Double.valueOf(bestCustomer[1]);

        String[] totalSales = totalSaleStream.salesCalc();
        Double totSales = Double.valueOf(totalSales[0]);
        Double currentMonthSales = Double.valueOf(totalSales[2]);
        Double twoMonthSales = Double.valueOf(totalSales[4]);
        Double threeMonthSales = Double.valueOf(totalSales[6]);
        int totalOrders = Integer.valueOf(totalSales[7]);

        String[] weeklySales = totalSaleStream.thisWeeksSales();
        Double currentWeekSales = Double.valueOf(weeklySales[1]);
        Double twoWeekSales = Double.valueOf(weeklySales[3]);
        Double threeWeekSales = Double.valueOf(weeklySales[5]);

        String[] bestProducts = totalSaleStream.popularItems();
        Double bestTotal = Double.valueOf(bestProducts[1]);
        Double secTotal = Double.valueOf(bestProducts[3]);
        Double thirdTotal = Double.valueOf(bestProducts[5]);

        String[] dailySales = totalSaleStream.dailySales();
        Double dSales = Double.valueOf(dailySales[0]);

        jChart salesCharts = new jChart();
        salesCharts.lineChart();
        salesCharts.weeklyOrdersSales();

        // Creates temporary sales pdf
        File tempSales = File.createTempFile("SalesReport", ".pdf");
        PdfWriter writer = new PdfWriter(tempSales);
        PdfDocument salesDoc = new PdfDocument(writer);
        Document doc = new Document(salesDoc);

        // Adds Rt3 Logo
        String rt3Loc = "CS3250\\src\\main\\java\\UI\\Images\\RT3.png";
        ImageData rt3Data = ImageDataFactory.create(rt3Loc);
        Image rt3Image = new Image(rt3Data);
        rt3Image.scaleAbsolute(100, 100);
        rt3Image.setFixedPosition(250, 675);
        doc.add(rt3Image);

        // Sales Report heading
        String headingText = "Sales Report Generated by IMS";
        Paragraph headingBreak = new Paragraph(headingText);
        headingBreak.setTextAlignment(TextAlignment.CENTER);
        doc.add(headingBreak);

        // Add Table
        float[] columnWidths = { 1.5f, 2f, 5f, 2f };
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        Cell cells = new Cell(4, 4).add(new Paragraph("Sales Snap-Shot")).setTextAlignment(TextAlignment.CENTER);
        table.addHeaderCell(cells);
        table.setFixedPosition(100, 650, 400);

        Cell totalSalesCell = new Cell(4, 4)
                .add(new Paragraph("Total Sales: " + "$" + String.format("%,.2f", totSales)));
        table.addFooterCell(totalSalesCell);
        doc.add(table);

        Cell dailySalesCell = new Cell(4, 4)
                .add(new Paragraph("Sales for: " + dailySales[1] + "\n" + "Sales: $" + String.format("%,.2f", dSales)));
        table.addFooterCell(dailySalesCell);
        doc.add(table);

        Cell totalOrdersCell = new Cell(4, 4)
                .add(new Paragraph("Total orders:" + String.format("%,8d%n", totalOrders)));
        table.addFooterCell(totalOrdersCell);
        doc.add(table);

        Cell thisMonthSalesCell = new Cell(4, 4)
                .add(new Paragraph("Sales in " + totalSales[1] + ": $" + String.format("%,.2f", currentMonthSales)));
        table.addFooterCell(thisMonthSalesCell);
        doc.add(table);

        Cell twoMonthSalesCell = new Cell(4, 4)
                .add(new Paragraph("Sales in " + totalSales[3] + ": $" + String.format("%,.2f", twoMonthSales)));
        table.addFooterCell(twoMonthSalesCell);
        doc.add(table);

        Cell threeMonthSalesCell = new Cell(4, 4)
                .add(new Paragraph("Sales in " + totalSales[5] + ": $" + String.format("%,.2f", threeMonthSales)));
        table.addFooterCell(threeMonthSalesCell);
        doc.add(table);

        Cell bestCustomerCell = new Cell(4, 4).add(new Paragraph("Best Customer by Revenue: " + bestCustomer[0] + "\n"
                + "Total Spent: $" + String.format("%,.2f", bestCustomerRevenue)));

        table.addFooterCell(bestCustomerCell);
        doc.add(table);

        Cell thisWeeksSalesCell = new Cell(4, 4).add(new Paragraph("Sales for the week of " + weeklySales[0] + "\n"
                + "Sales: $" + String.format("%,.2f", currentWeekSales)));
        table.addFooterCell(thisWeeksSalesCell);
        doc.add(table);

        Cell twoWeekSalesCell = new Cell(4, 4).add(new Paragraph(
                "Sales for the week of " + weeklySales[2] + "\n" + "Sales: $" + String.format("%,.2f", twoWeekSales)));
        table.addFooterCell(twoWeekSalesCell);
        doc.add(table);

        Cell threeWeekSalesCell = new Cell(4, 4).add(new Paragraph("Sales for the week of " + weeklySales[4] + "\n"
                + "Sales: $" + String.format("%,.2f", threeWeekSales)));
        table.addFooterCell(threeWeekSalesCell);
        doc.add(table);

        Cell bestProductsCell = new Cell(4, 4).add(new Paragraph("Best Selling Product: " + bestProducts[0] + "\n"
                + "Sales: $" + String.format("%,.2f", bestTotal) + "\n"));
        table.addFooterCell(bestProductsCell);
        doc.add(table);

        Cell secProductsCell = new Cell(4, 4).add(new Paragraph("Second Best Selling Product: " + bestProducts[2] + "\n"
                + "Sales: $" + String.format("%,.2f", secTotal)));
        table.addFooterCell(secProductsCell);
        doc.add(table);

        Cell thirdProductsCell = new Cell(4, 4).add(new Paragraph("Third Best Selling Product: " + bestProducts[4]
                + "\n" + "Sales: $" + String.format("%,.2f", thirdTotal)));
        table.addFooterCell(thirdProductsCell);
        doc.add(table);

        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        salesDoc.addNewPage();

        String salesChartLoc = "CS3250\\src\\main\\java\\UI\\Images\\salesLineChart.PNG";
        ImageData salesChartData = ImageDataFactory.create(salesChartLoc);
        Image salesChartImage = new Image(salesChartData);
        doc.add(salesChartImage);

        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        salesDoc.addNewPage();

        String salesChangeLoc = "CS3250\\src\\main\\java\\UI\\Images\\salesChangeChart.PNG";
        ImageData salesChangeData = ImageDataFactory.create(salesChangeLoc);
        Image salesChangeImage = new Image(salesChangeData);
        doc.add(salesChangeImage);

        doc.close();
        Desktop.getDesktop().open(tempSales);
        tempSales.deleteOnExit();
    }


    @FXML
    public void highlightClick(MouseEvent event) {

        if (orderScreenDisplayed == true) {
            UI.observablePO selectedItem = (UI.observablePO) total_Table.getSelectionModel().getSelectedItem();

            textId.setText(selectedItem.getProductID());
            textQuantity.setText(selectedItem.getDate());
            textCost.setText(selectedItem.getQuantity());
            textPrice.setText(selectedItem.getCustomerLocation());
            textSid.setText(selectedItem.getEmail());
            textID.setText(selectedItem.getID());
        } else if (orderScreenDisplayed == false) {
            UI.dataBaseItems selectedItem = (UI.dataBaseItems) total_Table.getSelectionModel().getSelectedItem();

            textId.setText(selectedItem.getProductID());
            textQuantity.setText(selectedItem.getStockQuantity());
            textCost.setText(selectedItem.getWholesaleCost());
            textPrice.setText(selectedItem.getSalePrice());
            textSid.setText(selectedItem.getSupplierID());

        }


    }

    Statement st;

    @FXML
    public void addItem() throws SQLException {
        if (orderScreenDisplayed == true) {
            observablePO p = new observablePO();
            p.setCustomerLocation(textPrice.getText());
            p.setDate(textQuantity.getText());
            p.setEmail(textSid.getText());
            p.setProductID(textId.getText());
            p.quantity(textCost.getText());
            items.createEntry("0", p);
            total_Table.getItems().clear();
            try {
                showOrders();
            } catch (java.io.IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else if (orderScreenDisplayed == false){
            Connection con = null;
            try {
                con = UIDBConnector.getConnection();
            } catch (java.io.IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            st =  (Statement) con.createStatement();
            String statement = "UPDATE DataEntries SET supplierID = '" + textSid.getText() + "', stockQuantity = " + textQuantity.getText() + ", wholesaleCost = " + textCost.getText() + ", salePrice = " + textPrice.getText() + "WHERE productID = '" + textId.getText() + "'";
            st.execute(statement);
            total_Table.getItems().clear();
            showInventory();
        }

    }

    @FXML
    public void delItem() throws SQLException {
        if (orderScreenDisplayed == true) {
                    String id = textID.getText();
                    Connection con = null;
                    try {
                        con = UIDBConnector.getConnection();
                    } catch (java.io.IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    st =  (Statement) con.createStatement();
                    String statement = "DELETE FROM PO WHERE ID ='"+ id+ "';";
                    st.execute(statement);
            
            
                }
    }


    @FXML
    public void setMax(){
        Stage stage = (Stage) basePane.getScene().getWindow();
            stage.setMaximized(true);
    }

    @FXML
    public void setMin(){
        Stage stage = (Stage) basePane.getScene().getWindow();
            stage.setMaximized(false);
    }

}