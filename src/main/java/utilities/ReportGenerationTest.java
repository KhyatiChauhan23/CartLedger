package utilities;
import utilities.csvParser;
import utilities.MonthlyReportBuilder;
//import mail.EmailSender;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import POJOs.ZeptoOrder;
import POJOs.BlinkitOrder;
import java.util.List;
import static org.testng.Assert.*;

public class ReportGenerationTest {

    private List<ZeptoOrder> zeptoOrders;
    private List<BlinkitOrder> blinkitOrders;
    private String reportHtml;

    @BeforeClass
    public void setup() {
        String zeptoCsv = "C:\\Users\\Khyati\\Desktop\\CartLedger\\src\\test\\java\\csvFiles\\zepto_orders.csv";
        String blinkitCsv = "C:\\Users\\Khyati\\Desktop\\CartLedger\\src\\test\\java\\csvFiles\\blinkIt_orders.csv";

        zeptoOrders = csvParser.parseZeptoCSV(zeptoCsv);
        blinkitOrders = csvParser.parseBlinkitCSV(blinkitCsv);

        assertNotNull(zeptoOrders, "Zepto order list should not be null");
        assertNotNull(blinkitOrders, "Blinkit order list should not be null");
    }

    @Test
    public void generateReport() {
        reportHtml = MonthlyReportBuilder.build(zeptoOrders, blinkitOrders);

        assertNotNull(reportHtml, "Generated report should not be null");
        assertTrue(reportHtml.contains("Zepto"), "Report should contain Zepto section");
        assertTrue(reportHtml.contains("Blinkit"), "Report should contain Blinkit section");

        System.out.println("Generated HTML Report:\n" + reportHtml);
    }

//    @Test(priority = 2, dependsOnMethods = "generateReport")
//    public void sendEmailReport() {
//        try {
//            EmailSender.sendEmail("your.email@gmail.com", "Monthly Spend Report - Zepto vs Blinkit", reportHtml);
//        } catch (Exception e) {
//            fail("Failed to send email: " + e.getMessage());
//        }
//    }
}
