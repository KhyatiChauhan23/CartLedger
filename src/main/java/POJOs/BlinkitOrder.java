package POJOs;
import java.time.LocalDate;

public class BlinkitOrder {
    public String orderId;
    public LocalDate orderDate;
    public double itemCost, handlingCharge, deliveryCharge, totalBill;

    public BlinkitOrder(String orderId, LocalDate orderDate, double itemCost,
                        double handlingCharge, double deliveryCharge, double totalBill) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.itemCost = itemCost;
        this.handlingCharge = handlingCharge;
        this.deliveryCharge = deliveryCharge;
        this.totalBill = totalBill;
    }
}
