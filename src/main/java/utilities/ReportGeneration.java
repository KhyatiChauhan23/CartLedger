package utilities;
import POJOs.BlinkItOrder;
import POJOs.ZeptoOrder;
import org.testng.annotations.Test;
import java.time.YearMonth;
import java.util.List;

public class ReportGeneration {

    @Test
    public void testMonthlySpendReportGeneration() {
        String zeptoPath = "C:\\Users\\Khyati\\Desktop\\CartLedger\\src\\test\\java\\csvFiles\\zepto_orders.csv";
        String blinkitPath = "C:\\Users\\Khyati\\Desktop\\CartLedger\\src\\test\\java\\csvFiles\\blinkIt_orders.csv";

        List<ZeptoOrder> zeptoOrders = CsvParser.parseOrders(zeptoPath);
        List<BlinkItOrder> blinkItOrders = CsvParser.parseOrder(blinkitPath);

        YearMonth currentMonth = YearMonth.now();

        String report = OrderAnalyzer.analyzeMonthlySpend(zeptoOrders, blinkItOrders, currentMonth);

        // Output for debugging purposes (optional in a test)
        System.out.println(report);

        // Optional: add assertions if needed
        assert report != null && !report.isEmpty() : "Report should not be empty";
    }
}
