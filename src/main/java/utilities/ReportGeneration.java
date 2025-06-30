	package utilities;
	import POJOs.BlinkItOrder;
	import POJOs.ZeptoOrder;
	
	import org.testng.annotations.Parameters;
	import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.YearMonth;
	import java.util.List;
import java.util.Properties;
	public class ReportGeneration {
	
	    @Test
	    @Parameters({"inputMonth", "inputYear"})
	    public void testMonthlySpendReportGeneration(String inputMonth, String inputYear) throws FileNotFoundException, IOException {
	        String zeptoPath = "C:\\Users\\Khyati\\Desktop\\CartLedger\\src\\test\\java\\csvFiles\\zepto_orders.csv";
	        String blinkitPath = "C:\\Users\\Khyati\\Desktop\\CartLedger\\src\\test\\java\\csvFiles\\blinkIt_orders.csv";
	
	        List<ZeptoOrder> zeptoOrders = CsvParser.parseOrders(zeptoPath);
	        List<BlinkItOrder> blinkItOrders = CsvParser.parseOrder(blinkitPath);
	
	        int year = Integer.parseInt(inputYear);
	        int month = Integer.parseInt(inputMonth); // should be 1–12
	        YearMonth selectedMonth = YearMonth.of(year, month);
	
	        String report = OrderAnalyzer.analyzeMonthlySpend(zeptoOrders, blinkItOrders, selectedMonth);
	
	        // Output for debugging purposes (optional in a test)
	        System.out.println(report);
	        
//			Properties prop = new Properties();
//			prop.load(new FileInputStream("config.properties"));
//			String toEmail = prop.getProperty("email");
	
	        // Send email with report
	        String recipientEmail = "khyati1023@gmail.com";
	        String subject = "Monthly Spend Report - " + selectedMonth;
	        EmailSender.sendEmail(recipientEmail, subject, report);
	        
	        // Optional: add assertions if needed
	        assert report != null && !report.isEmpty() : "Report should not be empty";
	    }
	}
