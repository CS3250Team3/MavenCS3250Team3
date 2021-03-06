package UI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;







public class poStream {
        
         Multimap<String, Integer> prodAndQuant = ArrayListMultimap.create();

         Multimap<String, Integer> currentMonthMap = ArrayListMultimap.create();

         Multimap<String, Integer> twoMonthMap = ArrayListMultimap.create();

         Multimap<String, Integer> threeMonthMap = ArrayListMultimap.create();

         Multimap<String, Integer> currentWeekMap = ArrayListMultimap.create();

         HashMap <String, Double> productPrice = new HashMap<String, Double>();




    public void productCostMap() throws SQLException{
        Connection poCon = UIDBConnector.getConnection();
        ResultSet rs = poCon.createStatement().executeQuery("SELECT * FROM DataEntries");
        while (rs.next()){
            productPrice.put(rs.getString("productID"), rs.getDouble("salePrice"));
           
        }
    }

    public void salesOrderMap() throws SQLException{
        Connection poCon = UIDBConnector.getConnection();
        ResultSet rs = poCon.createStatement().executeQuery("SELECT * FROM PO");
        while (rs.next()){
            prodAndQuant.put(rs.getString("productID"), rs.getInt("quantity"));
        }
    }

public void totalSalesPrep() throws SQLException{
        productCostMap();
        salesOrderMap();
        
        Set keySet = prodAndQuant.keySet(); 
        Iterator<String> it = keySet.iterator(); 
        while(it.hasNext()) {
            String key = (String) it.next();
            List values = (List) prodAndQuant.get(key);
            Integer sum = 0; 
            if(values.size() > 1){
                List<Integer> tempSumList = new ArrayList(); 
                for(Integer i = 0; i < values.size();){
                     sum += (Integer) values.get(i);
                     i++;
                }
                tempSumList.add(sum);
                prodAndQuant.replaceValues(key, tempSumList);
                tempSumList.clear();
            } 
        }
                
}

public void currentMonthMap() throws SQLException{
    Connection poCon = UIDBConnector.getConnection();
        ResultSet rs = poCon.createStatement().executeQuery("SELECT * FROM PO WHERE monthname (date) = 'June' " );
        while (rs.next()){
            currentMonthMap.put(rs.getString("productID"), rs.getInt("quantity"));
        }
    }

public void twoMonthMap() throws SQLException{
    Connection poCon = UIDBConnector.getConnection();
    ResultSet ss = poCon.createStatement().executeQuery("SELECT * FROM PO WHERE monthname (date) = 'May' " );
    while (ss.next()){
        twoMonthMap.put(ss.getString("productID"), ss.getInt("quantity"));
    }
}

public void threeMonthMap() throws SQLException{
    Connection poCon = UIDBConnector.getConnection();
    ResultSet ts = poCon.createStatement().executeQuery("SELECT * FROM PO WHERE monthname (date) = 'April' " );
        while (ts.next()){
            threeMonthMap.put(ts.getString("productID"), ts.getInt("quantity"));
        }
}

public String calcTotalSales() throws SQLException{
    totalSalesPrep();
    Set quantSet = prodAndQuant.keySet(); 
    Iterator<String> quantIt = quantSet.iterator(); 

    Double totalSales = 0.0;

    while(quantIt.hasNext()){
        String qauntKey = (String) quantIt.next();
        
        List tempQauntList = (List) prodAndQuant.get(qauntKey);
        
        Double tempPrice = productPrice.get(qauntKey);
        totalSales += tempPrice * (Integer) tempQauntList.get(0);
    }
    return String.format("%,.2f", totalSales); 
}    

public void currentMonthPrep() throws SQLException {
    //Gives current month
    //Date date = Calendar.getInstance().getTime();
    //DateFormat dateFormatMonth = new SimpleDateFormat("M");
    //String strDate = dateFormatMonth.format(date);
    //DateFormat dateFormatYear = new SimpleDateFormat("YYYY");
    //String strDateYear = dateFormatYear.format(date); 
    //System.out.println(strDate);
    //System.out.println(strDateYear);
    //String currentMon = strDate;

    //Integer tempInt = Integer.parseInt(strDate);
    //Integer tempOneMonthAgoInt = tempInt-1;    
    //Integer temptwoMonthAgoInt = tempInt - 2;
    
    //String oneMonthAgo = tempOneMonthAgoInt.toString();
    //String twoMonthAgo = temptwoMonthAgoInt.toString(); 

    productCostMap();
    currentMonthMap();
        
        Set keySet = currentMonthMap.keySet(); 
        Iterator<String> it = keySet.iterator(); 
        while(it.hasNext()) {
            String key = (String) it.next();
            List values = (List) currentMonthMap.get(key);
            Integer sum = 0; 
            if(values.size() > 1){
                List<Integer> tempSumList = new ArrayList(); 
                for(Integer i = 0; i < values.size();){
                     sum += (Integer) values.get(i);
                     i++;
                }
                tempSumList.add(sum);
                currentMonthMap.replaceValues(key, tempSumList);
                tempSumList.clear();
            } 
        }
}

public void twoMonthPrep() throws SQLException{
    productCostMap();
    twoMonthMap();;
        
        Set keySet = twoMonthMap.keySet(); 
        Iterator<String> it = keySet.iterator(); 
        while(it.hasNext()) {
            String key = (String) it.next();
            List values = (List) twoMonthMap.get(key);
            Integer sum = 0; 
            if(values.size() > 1){
                List<Integer> tempSumList = new ArrayList(); 
                for(Integer i = 0; i < values.size();){
                     sum += (Integer) values.get(i);
                     i++;
                }
                tempSumList.add(sum);
                twoMonthMap.replaceValues(key, tempSumList);
                tempSumList.clear();
            } 
        }
        
}
    
public void threeMonthPrep() throws SQLException{
    productCostMap();
    threeMonthMap();;
        
        Set keySet = threeMonthMap.keySet(); 
        Iterator<String> it = keySet.iterator(); 
        while(it.hasNext()) {
            String key = (String) it.next();
            List values = (List) threeMonthMap.get(key);
            Integer sum = 0; 
            if(values.size() > 1){
                List<Integer> tempSumList = new ArrayList(); 
                for(Integer i = 0; i < values.size();){
                     sum += (Integer) values.get(i);
                     i++;
                }
                tempSumList.add(sum);
                threeMonthMap.replaceValues(key, tempSumList);
                tempSumList.clear();
            } 
        }
}

public String calcCurrentMonthSales() throws SQLException{
    currentMonthPrep();
    Set quantSet = currentMonthMap.keySet(); 
    Iterator<String> quantIt = quantSet.iterator(); 

    Double currentSales = 0.0;

    while(quantIt.hasNext()){
        String qauntKey = (String) quantIt.next();
        
        List tempQauntList = (List) currentMonthMap.get(qauntKey);
        
        Double tempPrice = productPrice.get(qauntKey);
        currentSales += tempPrice * (Integer) tempQauntList.get(0);
    }
    return String.format("%,.2f", currentSales); 
}    

public String calcTwoMonthSales() throws SQLException{
    twoMonthPrep();
    Set quantSet = twoMonthMap.keySet(); 
    Iterator<String> quantIt = quantSet.iterator(); 

    Double twoMonthSales = 0.0;

    while(quantIt.hasNext()){
        String qauntKey = (String) quantIt.next();
        
        List tempQauntList = (List) twoMonthMap.get(qauntKey);
        
        Double tempPrice = productPrice.get(qauntKey);
        twoMonthSales += tempPrice * (Integer) tempQauntList.get(0);
    }
    return String.format("%,.2f", twoMonthSales); 
}   

public String calcThreeMonthSales() throws SQLException{
    threeMonthPrep();
    Set quantSet = threeMonthMap.keySet(); 
    Iterator<String> quantIt = quantSet.iterator(); 

    Double threeMonthSales = 0.0;

    while(quantIt.hasNext()){
        String qauntKey = (String) quantIt.next();
        
        List tempQauntList = (List) threeMonthMap.get(qauntKey);
        
        Double tempPrice = productPrice.get(qauntKey);
        threeMonthSales += tempPrice * (Integer) tempQauntList.get(0);
    }
    return String.format("%,.2f", threeMonthSales); 
}   


}
          
        
          
        


        
        

 