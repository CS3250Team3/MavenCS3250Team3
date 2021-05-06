package UI;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class jChart {

    
    /** 
     * Generates time-series plot of item sales
     * 
     * @throws IOException
     * @throws NumberFormatException
     * @throws SQLException
     */
    public void lineChart() throws IOException, NumberFormatException, SQLException {
        poStream threeMonths = new poStream();
        DefaultCategoryDataset salesData = new DefaultCategoryDataset();
        // Calculate total sales for last three months
        String[] monthSales = threeMonths.salesCalc();

        // Split sales into respective months
        Double threeMDouble = Double.parseDouble(monthSales[6]);
        Double twoMDouble = Double.parseDouble(monthSales[4]);
        Double currMDouble = Double.parseDouble(monthSales[2]);

        // Add sales figures to dataset
        salesData.addValue(threeMDouble, "Sales", monthSales[5]);
        salesData.addValue(twoMDouble, "Sales", monthSales[3]);
        salesData.addValue(currMDouble, "Sales", monthSales[1]);

        // Create line chart of sales in dollars
        JFreeChart salesDataChartObj = ChartFactory.createLineChart("Sales of the last 3 Months", "Months",
                "Sales in Dollars", salesData, PlotOrientation.VERTICAL, true, true, false);

        int width = 487;
        int height = 480;

        File salesLineChart = new File("CS3250\\src\\main\\java\\UI\\Images\\salesLineChart.PNG");
        ChartUtilities.saveChartAsPNG(salesLineChart, salesDataChartObj, width, height);
    }

    
    /** 
     * Calculate percentage change in sales from last month
     * 
     * @param firstValue - Previous month sales
     * @param secondValue - Current month sales
     * @return Double - Percentage change
     */
    public Double percentCalc(Double firstValue, Double secondValue) {
        Double percentChange = ((secondValue - firstValue) / firstValue) * 100;
        return percentChange;
    }

    
    /** 
     * Generates time-series plot showing revenue over last 3 weeks
     * @throws SQLException
     * @throws IOException
     */
    public void weeklyOrdersSales() throws SQLException, IOException {
        poStream weeks = new poStream();
        DefaultCategoryDataset changeInSales = new DefaultCategoryDataset();

        // Calculate sales by weeks
        String[] weeklySales = weeks.thisWeeksSales();

        Double currentWeekSales = Double.parseDouble(weeklySales[1]);
        Double twoWeekSales = Double.parseDouble(weeklySales[3]);
        Double threeWeekSales = Double.parseDouble(weeklySales[5]);

        // Calculate changes in sales
        changeInSales.addValue(percentCalc(threeWeekSales, threeWeekSales), "Revenue", "Week 3");
        changeInSales.addValue(percentCalc(twoWeekSales, threeWeekSales), "Revenue", "Week 2");
        changeInSales.addValue(percentCalc(currentWeekSales, twoWeekSales), "Revenue", "Week 1");

        Double currentOrders = Double.parseDouble(weeklySales[6]);
        Double twoWeekOrders = Double.parseDouble(weeklySales[7]);
        Double threeWeekOrders = Double.parseDouble(weeklySales[8]);

        // Calculate changes in number of orders
        changeInSales.addValue(percentCalc(threeWeekOrders, threeWeekOrders), "Orders", "Week 3");
        changeInSales.addValue(percentCalc(twoWeekOrders, threeWeekOrders), "Orders", "Week 2");
        changeInSales.addValue(percentCalc(currentOrders, twoWeekOrders), "Orders", "Week 1");

        // Generate line chart
        JFreeChart salesDataChartObj = ChartFactory.createLineChart("Percent Change in Weekly Revenue", "Weeks",
                "Percent Change", changeInSales, PlotOrientation.VERTICAL, true, true, false);

        int width = 487;
        int height = 480;

        File salesChangeChart = new File("CS3250\\src\\main\\java\\UI\\Images\\salesChangeChart.PNG");
        ChartUtilities.saveChartAsPNG(salesChangeChart, salesDataChartObj, width, height);

    }
}
