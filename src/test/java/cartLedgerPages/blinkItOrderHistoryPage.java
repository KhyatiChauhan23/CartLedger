package cartLedgerPages;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import resources.blinkItScraper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import utilities.scroller;
import org.openqa.selenium.JavascriptExecutor;
import utilities.writeToCsv;

public class blinkItOrderHistoryPage 
{
	
	WebDriver driver;
	WebDriverWait wait;
	List<blinkItScraper> orders = new ArrayList<>();
	JavascriptExecutor js = (JavascriptExecutor) driver;
	
	@BeforeClass
	public void setup()
	{
			driver = baseClass.getDriver();
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	@Test
	public void blinkItOrderHistory() 
	{
		WebElement account = driver.findElement(By.xpath("//div[@class='ProfileButton__Text-sc-975teb-2 bFHCDW']"));
		account.click();

		WebElement myOrders = driver.findElement(By.xpath("//div[@class='account-dropdown--nav_item full-width']"));
		myOrders.click();
		
		writeToCsv csv = new writeToCsv();
		
		int index=1;
		
		while(true)
		{
		try
		{
			String xpath = "(//div[@class='tw-px-3 tw-inline-flex tw-items-center tw-justify-center tw-rounded-full'])["+index+"]";
			WebElement orderCard = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			orderCard.click(); 		
			Thread.sleep(2000);
			
			blinkItScraper orderDetails = fetchOrderDetails();
	        LocalDate firstOfThisMonth = LocalDate.now().withDayOfMonth(1);

	        if (orderDetails.orderDate != null && orderDetails.orderDate.isBefore(firstOfThisMonth)) 
	        {
	            System.out.println("Encountered order from before this month. Stopping.");
	            break;
	        }
			orders.add(orderDetails);
			index++;
			
			driver.navigate().back();
			Thread.sleep(500);
		}
		
		catch(Exception e) 
		{
			System.out.println("No more Orders");
			break;
		};
		}
		csv.blinkItExportToCSV(orders, "blinkIt_orders.csv"); 
	}
		
	public blinkItScraper fetchOrderDetails()
		{
			blinkItScraper order = new blinkItScraper();
			
			//Fetching OrderId
			By orderId = By.xpath("//div[@data-pf='reset' and contains(text(), 'ORD')]");
			scroller.scrollUntilVisible(driver, orderId);
			WebElement target = driver.findElement(orderId);
			order.orderID = target.getText();
			
			//Fetching OrderDate
			try 
			{
			By dateOfOrder = By.xpath("//span[@data-pf='reset' and contains(text(), 'placed on')]");
			scroller.scrollUntilVisible(driver, dateOfOrder);
			WebElement orderPlacedDate = driver.findElement(dateOfOrder);
			String rawText = orderPlacedDate.getText();
			String datePart = rawText.replace("placed on ", "").split(",")[1].trim();
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd MMM''yy", Locale.ENGLISH);
			LocalDate parsedDate = LocalDate.parse(datePart, inputFormatter);
			order.orderDate = parsedDate;
			} 
			catch (Exception e) {}

			//Fetching Item Cost
			try 
			{
			By costOfItem = By.xpath("//div[@class='tw-text-200 tw-font-medium tw-bg-white-500 tw-my-1.5']");
			scroller.scrollUntilVisible(driver, costOfItem);
			WebElement itemsCost = driver.findElement(costOfItem);
			String item1 = itemsCost.getText();
			String cleanValue1 = item1.replaceAll("[^\\d.]", "");
			order.itemCost = Double.parseDouble(cleanValue1);
		    } 
			catch (Exception e) 
			{
		        order.itemCost = 0.0;
		    }
			
			//Fetching Handling Charge
			try 
			{
			By chargeOfHandling = By.xpath("(//div[@class='tw-text-200 tw-font-medium tw-bg-white-900 tw-my-1.5'])[2]");
			scroller.scrollUntilVisible(driver, chargeOfHandling);
			WebElement handlingCharges = driver.findElement(chargeOfHandling);
			String item2 = handlingCharges.getText();
			String cleanValue2 = item2.replaceAll("[^\\d.]", "");
			order.handlingCharge = Double.parseDouble(cleanValue2);
		    } 
			catch (Exception e) 
			{
		        order.handlingCharge = 0.0;
		    }
			
			//Fetching Delivery Fee
			try 
			{
			By feeOfDelivery = By.xpath("(//div[@class='tw-text-200 tw-font-medium tw-bg-white-900 tw-my-1.5'])[3]");
			scroller.scrollUntilVisible(driver, feeOfDelivery);
			WebElement deliveryFees = driver.findElement(feeOfDelivery);
 			String item3 = deliveryFees.getText();
			String cleanValue3 = item3.replaceAll("[^\\d.]", "");
			order.deliveryCharge = Double.parseDouble(cleanValue3);
		    } 
			catch (Exception e) 
			{
		        order.deliveryCharge = 0.0;
		    }
			
			//Fetching Total Bill
			try 
			{
			By orderBill = By.xpath("(//div[@class='tw-text-300 tw-font-semibold tw-bg-white-900 tw-my-1.5'])[2]");
			scroller.scrollUntilVisible(driver, orderBill);
			WebElement totalBills = driver.findElement(orderBill);
			String item4 = totalBills.getText();
			String cleanValue4 = item4.replaceAll("[^\\d.]", "");
			order.totalBill = Double.parseDouble(cleanValue4);
		    } 
			catch (Exception e) 
			{
		        order.totalBill = 0.0;
		    }
			
			return order;
		}
	}

