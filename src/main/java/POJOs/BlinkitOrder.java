package POJOs;
import java.time.LocalDate;

public class BlinkItOrder {
    private String orderId;
    private LocalDate orderDate;
    private double itemCost;
    private double handlingCharge;
    private double deliveryCharge;
    private double totalBill;

    public BlinkItOrder() {
        // No-arg constructor for serialization/deserialization
    }

    public BlinkItOrder(String orderId, LocalDate orderDate, double itemCost, double handlingCharge,
                      double deliveryCharge, double totalBill) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.itemCost = itemCost;
        this.handlingCharge = handlingCharge;
        this.deliveryCharge = deliveryCharge;
        this.totalBill = totalBill;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public double getItemCost() {
        return itemCost;
    }

    public void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }

    public double getHandlingCharge() {
        return handlingCharge;
    }

    public void setHandlingCharge (double handlingCharge) {
        this.handlingCharge = handlingCharge;
    }

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    @Override
    public String toString() {
        return "BlinkItOrder{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", itemCost=" + itemCost +
                ", HandlingCharge=" + handlingCharge +
                ", deliveryFee=" + deliveryCharge +
                ", totalBill=" + totalBill +
                '}';
    }
}
