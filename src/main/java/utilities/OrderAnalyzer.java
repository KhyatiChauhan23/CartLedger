package utilities;
import POJOs.BlinkItOrder;
import POJOs.ZeptoOrder;
import java.time.YearMonth;
import java.util.List;

public class OrderAnalyzer {

    public static String analyzeMonthlySpend(List<ZeptoOrder> zeptoOrders, List<BlinkItOrder> blinkItOrders, YearMonth month) {
        double zeptoTotal = 0, zeptoDeliveryTotal = 0;
        double blinkitTotal = 0, blinkitDeliveryTotal = 0;

        // Analyze Zepto Orders
        for (ZeptoOrder order : zeptoOrders) {
            if (YearMonth.from(order.getOrderDate()).equals(month)) {
                zeptoTotal += order.getTotalBill();
                zeptoDeliveryTotal += order.getGst() + order.getItemHandlingCost()+ order.getDeliveryFee() + order.getProcessingFee();
            }
        }

        // Analyze Blinkit Orders
        for (BlinkItOrder order : blinkItOrders) {
            if (YearMonth.from(order.getOrderDate()).equals(month)) {
                blinkitTotal += order.getTotalBill();
                blinkitDeliveryTotal += order.getHandlingCharge() + order.getDeliveryCharge();
            }
        }

        // Build Result Summary
        StringBuilder report = new StringBuilder();
        report.append("\nMonthly Spend Report - " + month + "\n\n");

        report.append("Zepto:\n");
        report.append("Total Bill: ").append(String.format("%.2f", zeptoTotal)).append("\n");
        report.append("Delivery related charges paid: ").append(String.format("%.2f", zeptoDeliveryTotal)).append("\n\n");

        report.append("Blinkit:\n");
        report.append("Total Bill: ").append(String.format("%.2f", blinkitTotal)).append("\n");
        report.append("Delivery related charges paid: ").append(String.format("%.2f", blinkitDeliveryTotal)).append("\n\n");

        report.append("Spent More On:\n");
        if (zeptoTotal > blinkitTotal) {
            report.append("You spent ").append(String.format("%.2f", zeptoTotal - blinkitTotal)).append(" more on Zepto.\n");
        } else if (blinkitTotal > zeptoTotal) {
            report.append("You spent ").append(String.format("%.2f", blinkitTotal - zeptoTotal)).append(" more on Blinkit.\n");
        } else {
            report.append("You spent the same amount on Zepto and Blinkit.\n");
        }

        double grandTotal = zeptoTotal + blinkitTotal;
        double totalDelivery = zeptoDeliveryTotal + blinkitDeliveryTotal;

        report.append("\n Grand Total Spent: ").append(String.format("%.2f", grandTotal)).append("\n");
        report.append(" Combined Delivery Charges: ").append(String.format("%.2f", totalDelivery)).append("\n");

        return report.toString();
    }
}
