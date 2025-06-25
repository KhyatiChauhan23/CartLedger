package resources;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ZeptoScraper 
{
	public static class ZeptoOrder {
        public static String orderId;
        public LocalDateTime orderDate;
        public double itemCost;
        public double itemHandlingCost;
        public double gst;
        public double deliveryFee;
        public double totalBill;

        public String toString() {
            return String.format("Order: %s | Date: %s | Item: ₹%.2f | Handling: ₹%.2f | GST: ₹%.2f | Delivery: ₹%.2f | Total: ₹%.2f",
                    orderId, orderDate, itemCost, itemHandlingCost, gst, deliveryFee, totalBill);
        }
    }
}
