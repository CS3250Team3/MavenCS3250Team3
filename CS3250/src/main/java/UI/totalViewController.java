package UI;
import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itextpdf.io.IOException;
import com.itextpdf.layout.element.Table;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import CS3250.PO;
import CS3250.SQLPo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
    private Label textField1; 

    @FXML
    private Label textField2;

    @FXML
    private Label textField3;

    @FXML
    private Label textField4;

    @FXML
    private Label textField5;



    ObservableList oblist = FXCollections.observableArrayList();

    @FXML
    public void showInventory() throws SQLException{
        total_Table.getItems().clear();
        textField1.setText("   Id");
        textField2.setText("   Quantity");
        textField3.setText("   Cost");
        textField4.setText("   Price");
        textField5.setText("   Sid");

        try {
            Connection con = UIDBConnector.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM DataEntries");
            
            while (rs.next()) {// "should be column names"

                oblist.add(new dataBaseItems(rs.getString("productID"), rs.getString("stockQuantity"),
                        rs.getString("wholesaleCost"), rs.getString("salePrice"), rs.getString("supplierID")));
                
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
       // SortedList<dataBaseItems> sortedData = new SortedList<>(filteredData); 
        //sortedData.comparatorProperty().bind(total_Table.comparatorProperty());
       // total_Table.setItems(sortedData);
        
    }

    


    SQLPo po = new SQLPo();
    ObservableList poList;
    Boolean orderScreenDisplayed = null;
    
    @FXML
    public void showOrders(){
        orderScreenDisplayed = true; 

        textField1.setText("   Id");
        textField2.setText("   Date");
        textField3.setText("   Quantity");
        textField4.setText("   Customer Location");
        textField5.setText("   Email");

        cellOne.setText("productID");
        CellTwo.setText("Date");
        cellThree.setText("Email");
        cellFour.setText("ID");
        cellFive.setText(" ");
        total_Table.getItems().clear();

        
    

        po.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");
        poList = FXCollections.observableArrayList(po.GenerateShortPOs());
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
    public void ordersBtn(ActionEvent event){
        orders_Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                showOrders();;
            }
        });
    }




    @FXML
    public void signoutBtn(ActionEvent event) {
        Stage stage = (Stage) exit_Btn.getScene().getWindow();
        stage.close();
}

@FXML
public void showReport(ActionEvent event) throws IOException, java.io.IOException{
    //Creates temporary sales pdf
    File tempSales = File.createTempFile("SalesReport", ".pdf"); 
    PdfWriter writer = new PdfWriter(tempSales);
    PdfDocument salesDoc = new PdfDocument(writer);
    PdfPage pageOne = salesDoc.addNewPage();
    Document doc = new Document(salesDoc);

    //Adds Rt3 Logo
    String rt3Loc = "CS3250\\src\\main\\java\\UI\\Images\\RT3.png";
    ImageData rt3Data = ImageDataFactory.create(rt3Loc);
    Image rt3Image = new Image(rt3Data);
    rt3Image.scaleAbsolute(100, 100);
    rt3Image.setFixedPosition(250,675);
    doc.add(rt3Image);

    
    //Sales Report heading
    String headingText = "Sales Report Generated by IMS";
    Paragraph headingBreak = new Paragraph(headingText);
    headingBreak.setTextAlignment(TextAlignment.CENTER);
    doc.add(headingBreak);
    
    //Add Table
    float[] columnWidths = {1.5f, 2f, 5f, 2f};
    Table table = new Table(UnitValue.createPercentArray(columnWidths));
    Cell cells = new Cell(4,4)
                .add(new Paragraph("Sales Snap-Shot"))
                .setTextAlignment(TextAlignment.CENTER);
    table.addHeaderCell(cells);            
    table.setFixedPosition(100, 650,400);

    Cell totalSalesCell = new Cell(4, 4)
                       .add(new Paragraph("Total Sales: ")); 
    table.addFooterCell(totalSalesCell);
    doc.add(table); 

    Cell thisMonthSalesCell = new Cell(4, 4)
                       .add(new Paragraph("This Months Sales: ")); 
    table.addFooterCell(thisMonthSalesCell);
    doc.add(table);

    Cell thisWeekSalesCell = new Cell(4, 4)
                       .add(new Paragraph("This Weeks Sales: ")); 
    table.addFooterCell(thisWeekSalesCell);
    doc.add(table);

    Cell mostPopularItemCell = new Cell(4, 4)
                       .add(new Paragraph("Most Popular Item: ")); 
    table.addFooterCell(mostPopularItemCell);
    doc.add(table);

    Cell bestCustomerCell = new Cell(4, 4)
                       .add(new Paragraph("Best Customer by revenue: ")); 
    table.addFooterCell(bestCustomerCell);
    doc.add(table);

    doc.close();
    Desktop.getDesktop().open(tempSales);
    tempSales.deleteOnExit();
}


@FXML
public void viewBtn(ActionEvent event){
    SQLPo sp = new SQLPo();
    sp.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");
    Integer PID = Integer.valueOf(cellFour.toString());
    PO fullpo = new PO();
    fullpo = sp.getPo(PID);
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Detailed Purchase View");
    alert.setHeaderText("Detailed PO");
    alert.setContentText("Ordered by: " + fullpo.getEmail() + "\n"  + "Located: " + fullpo.getCustomerLocation() + "\n" + 
    "Purchase ID: " + fullpo.getID() + "\n" +
    "Ordered on: " + fullpo.getDate() + "\n" + "Item ID: " +
    fullpo.getProductID() + "\n" + "Item Quantity: " + fullpo.getQuantity());
    alert.show();

}



    


    
}
