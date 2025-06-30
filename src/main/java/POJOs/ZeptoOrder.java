package POJOs;

import java.time.LocalDate;

public class ZeptoOrder {
    public String orderId;
    public LocalDate orderDate;
    public double itemCost, itemHandlingCost, gst, deliveryFee, processingFee, totalBill;

    public ZeptoOrder(String orderId, LocalDate orderDate, double itemCost, double itemHandlingCost,
                      double gst, double deliveryFee, double processingFee, double totalBill) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.itemCost = itemCost;
        this.itemHandlingCost = itemHandlingCost;
        this.gst = gst;
        this.deliveryFee = deliveryFee;
        this.processingFee = processingFee;
        this.totalBill = totalBill;
    }
}
