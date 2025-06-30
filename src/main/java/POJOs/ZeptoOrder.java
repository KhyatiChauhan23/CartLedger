package POJOs;

import java.time.LocalDate;

public class ZeptoOrder {
    private String orderId;
    private LocalDate orderDate;
    private double itemCost;
    private double itemHandlingCost;
    private double gst;
    private double deliveryFee;
    private double processingFee;
    private double totalBill;

    public ZeptoOrder() {
        // No-arg constructor for serialization/deserialization
    }

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

    public double getItemHandlingCost() {
        return itemHandlingCost;
    }

    public void setItemHandlingCost(double itemHandlingCost) {
        this.itemHandlingCost = itemHandlingCost;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    @Override
    public String toString() {
        return "ZeptoOrder{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", itemCost=" + itemCost +
                ", itemHandlingCost=" + itemHandlingCost +
                ", gst=" + gst +
                ", deliveryFee=" + deliveryFee +
                ", processingFee=" + processingFee +
                ", totalBill=" + totalBill +
                '}';
    }
}
