package utilities;
import com.opencsv.CSVReader;
import POJOs.BlinkItOrder;
import POJOs.ZeptoOrder;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {
	
	public static List<ZeptoOrder> parseOrders(String csvFilePath) {
        List<ZeptoOrder> orders = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Format must match CSV

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] line;
            boolean isHeader = true;

            while ((line = reader.readNext()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (line.length < 8) {
                    System.err.println("Skipping malformed row: " + String.join(",", line));
                    continue;
                }

                ZeptoOrder order = new ZeptoOrder();
                order.setOrderId(line[0]);
                order.setOrderDate(LocalDate.parse(line[1], dateFormatter));
                order.setItemCost(Double.parseDouble(line[2]));
                order.setItemHandlingCost(Double.parseDouble(line[3]));
                order.setGst(Double.parseDouble(line[4]));
                order.setDeliveryFee(Double.parseDouble(line[5]));
                order.setProcessingFee(Double.parseDouble(line[6]));
                order.setTotalBill(Double.parseDouble(line[7]));

                orders.add(order);
            }

        } catch (Exception e) {
            System.err.println("Error parsing CSV: " + e.getMessage());
            e.printStackTrace();
        }

        return orders;
    }

	public static List<BlinkItOrder> parseOrder(String csvFilePath) {
        List<BlinkItOrder> blinkItOrders = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Updated format

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] line;
            boolean isHeader = true;

            while ((line = reader.readNext()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (line.length < 6) {
                    System.err.println("Skipping malformed row: " + String.join(",", line));
                    continue;
                }

                BlinkItOrder order = new BlinkItOrder();
                order.setOrderId(line[0]);
                order.setOrderDate(LocalDate.parse(line[1], dateFormatter));
                order.setItemCost(Double.parseDouble(line[2]));
                order.setHandlingCharge(Double.parseDouble(line[3]));
                order.setDeliveryCharge(Double.parseDouble(line[4]));
                order.setTotalBill(Double.parseDouble(line[5]));

                blinkItOrders.add(order);
            }

        } catch (Exception e) {
            System.err.println("Error parsing CSV: " + e.getMessage());
            e.printStackTrace();
        }

        return blinkItOrders;
    }

    public static void main(String[] args) {
        // Update path as needed
        String filePath = "C:\\Users\\Khyati\\Desktop\\CartLedger\\src\\test\\java\\csvFiles\\blinkIt_orders.csv";

        List<BlinkItOrder> blinkItOrders = parseOrder(filePath);

        for (BlinkItOrder order : blinkItOrders) {
            System.out.println(order);
        }
    }
}