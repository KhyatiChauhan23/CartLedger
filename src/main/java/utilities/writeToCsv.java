package utilities;
import java.io.File;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import resources.blinkItScraper;
import resources.zeptoScraper.ZeptoOrder;

public class writeToCsv {
	
	public void blinkItExportToCSV(List<blinkItScraper> orders, String filePath) {
	    try (PrintWriter writer = new PrintWriter(new File(filePath))) {
	        StringBuilder sb = new StringBuilder();
	        sb.append("Order ID,Order Date,Item Cost,Handling Charge,Delivery Charge,Total Bill\n"); // ✅ Removed GST

	        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	        for (blinkItScraper order : orders) {
	            sb.append(order.orderID).append(",");
	            sb.append(order.orderDate != null ? order.orderDate.format(dateFormatter) : "").append(",");
	            sb.append(order.itemCost).append(",");
	            sb.append(order.handlingCharge).append(",");
	            sb.append(order.deliveryCharge).append(",");
	            sb.append(order.totalBill).append("\n");
	        }

	        writer.write(sb.toString());
	        System.out.println("\nCSV file written to " + filePath);
	        System.out.println("\nZepto & BlinkIt Orders Extracted & CSV files Generated");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
    public void zeptoExportToCSV(List<ZeptoOrder> orders, String filePath) 
    {
        try (PrintWriter writer = new PrintWriter(new File(filePath))) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Order ID,Order Date,Item Cost,Handling Cost,GST,Delivery Fee,Processing Fee,Total Bill\n");

            for (ZeptoOrder order : orders) {
                sb.append(order.orderId).append(",");
                sb.append(order.orderDate).append(",");
                sb.append(order.itemCost).append(",");
                sb.append(order.itemHandlingCost).append(",");
                sb.append(order.gst).append(",");
                sb.append(order.deliveryFee).append(",");
                sb.append(order.processingFee).append(",");
                sb.append(order.totalBill).append("\n");
            }

            writer.write(sb.toString());
	        System.out.println("CSV file written to " + filePath);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
