package resources;
import java.time.LocalDate;

public class zeptoScraper {
    public static class ZeptoOrder {
        public String orderId;
        public LocalDate orderDate;
        public double itemCost;
        public double itemHandlingCost;
        public double gst;
        public double deliveryFee;
        public double processingFee;
        public double totalBill;

        public String toString() {
            return String.format("Order: %s | Date: %s | Item: %.2f | Handling: %.2f | GST: %.2f | Delivery: %.2f | Processing Fee: %.2f | Total: %.2f",
                    orderId, orderDate, itemCost, itemHandlingCost, gst, deliveryFee, processingFee, totalBill);
        }
    }
}
