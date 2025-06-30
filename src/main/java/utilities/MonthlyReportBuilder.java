package utilities;
import POJOs.ZeptoOrder;
import POJOs.BlinkitOrder;
import java.util.List;

public class MonthlyReportBuilder {

    public static String build(List<ZeptoOrder> zeptoOrders, List<BlinkitOrder> blinkitOrders) {
        double zeptoTotal = zeptoOrders.stream().mapToDouble(z -> z.totalBill).sum();
        double zeptoDelivery = zeptoOrders.stream()
                .mapToDouble(z -> z.deliveryFee + z.processingFee)
                .sum();
        double zeptoItems = zeptoOrders.stream().mapToDouble(z -> z.itemCost).sum();

        double blinkitTotal = blinkitOrders.stream().mapToDouble(b -> b.totalBill).sum();
        double blinkitDelivery = blinkitOrders.stream().mapToDouble(b -> b.deliveryCharge).sum();
        double blinkitItems = blinkitOrders.stream().mapToDouble(b -> b.itemCost).sum();

        int zeptoCount = zeptoOrders.size();
        int blinkitCount = blinkitOrders.size();

        String comparison = zeptoTotal > blinkitTotal ?
                String.format("You spent ₹%.2f more on Zepto than Blinkit.", zeptoTotal - blinkitTotal) :
                String.format("You spent ₹%.2f more on Blinkit than Zepto.", blinkitTotal - zeptoTotal);

        return """
            <h2>Monthly Order Summary</h2>

            <h3>Zepto</h3>
            <ul>
              <li>Total Orders: %d</li>
              <li>Total Item Cost: ₹%.2f</li>
              <li>Total Delivery Charges: ₹%.2f</li>
              <li>Total Bill: ₹%.2f</li>
            </ul>

            <h3>Blinkit</h3>
            <ul>
              <li>Total Orders: %d</li>
              <li>Total Item Cost: ₹%.2f</li>
              <li>Total Delivery Charges: ₹%.2f</li>
              <li>Total Bill: ₹%.2f</li>
            </ul>

            <h3>Comparison</h3>
            <p><b>%s</b></p>
            """.formatted(
                zeptoCount, zeptoItems, zeptoDelivery, zeptoTotal,
                blinkitCount, blinkitItems, blinkitDelivery, blinkitTotal,
                comparison
            );
    }
}
