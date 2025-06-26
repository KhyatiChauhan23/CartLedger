package resources;
import java.time.LocalDate;

public class ZeptoScraper 
{
	public static class ZeptoOrder {
        public static String orderId;
        public static LocalDate orderDate;
        public static double itemCost;
        public static double itemHandlingCost;
        public static double gst;
        public static double deliveryFee;
        public static double totalBill;

        public String toString() {
            return String.format("Order: %s | Date: %s | Item: %.2f | Handling: %.2f | GST: %.2f | Delivery: %.2f | Total: %.2f",
                    orderId, orderDate, itemCost, itemHandlingCost, gst, deliveryFee, totalBill);
        }
    }
}
