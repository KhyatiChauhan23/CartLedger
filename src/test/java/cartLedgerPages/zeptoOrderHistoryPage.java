package cartLedgerPages;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import resources.zeptoScraper.ZeptoOrder;
import org.openqa.selenium.JavascriptExecutor;
import java.util.ArrayList;
import java.util.List;
import utilities.writeToCsv;

public class zeptoOrderHistoryPage 
{
	WebDriver driver;
	WebDriverWait wait;
	List<ZeptoOrder> allOrders = new ArrayList<>();
	
	@BeforeClass
	public void setup()
	{
			driver = baseClass.getDriver();
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	@Test
	@Parameters({"inputMonth", "inputYear"})
	public void orderHistory(String monthParam, String yearParam) throws InterruptedException
	{
		WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='text-sm capitalize']")));
		profile.click();
		
		Thread.sleep(2000);
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		writeToCsv csv = new writeToCsv();
		
	    int inputMonth = Integer.parseInt(monthParam);
	    int inputYear = Integer.parseInt(yearParam);

		LocalDate selectedMonthStart = LocalDate.of(inputYear, inputMonth, 1);
		LocalDate selectedMonthEnd = selectedMonthStart.withDayOfMonth(selectedMonthStart.lengthOfMonth());
		
		int index = 1;
		while (true) 
		{
		    try 
		    {
		        // Fetching the current indexed order card
		        String xpath = "(//div[@class='px-4'])[" + index + "]";
		        WebElement orderCard = driver.findElement(By.xpath(xpath));

		        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", orderCard);
		        orderCard.click();
		        Thread.sleep(1000);
		        
		     // Check if the order is cancelled
		       try 
		       {
		            WebElement cancelledTag = driver.findElement(By.xpath("//p[@class='text-heading3' and text()='Cancelled']"));
		            if (cancelledTag.isDisplayed()) 
		            {
//		                System.out.println("Order at index " + index + " is cancelled. Skipping.");
		                driver.navigate().back();
		                Thread.sleep(1500);
		                driver.navigate().refresh();
		                Thread.sleep(2000);
		                index++;
		                continue;
		            }
		        } 
		        catch (Exception e) 
		        {
		            // Not cancelled --> continue normally
		        }

		        ZeptoOrder orderDetails = fetchOrderDetails();

		        if (orderDetails.orderDate == null) 
		        {
		            driver.navigate().back();
		            Thread.sleep(1500);
		            driver.navigate().refresh();
		            Thread.sleep(2000);
		            index++;
		            continue;
		        }

		        if (orderDetails.orderDate.isBefore(selectedMonthStart)) 
		        {
		            break; // Orders are listed newest to oldest, so we can stop
		        }

		        if (!orderDetails.orderDate.isAfter(selectedMonthEnd)) 
		        {
		            allOrders.add(orderDetails);
		        }
		        
		        driver.navigate().back();
		        Thread.sleep(1500);
		        driver.navigate().refresh();
		        Thread.sleep(2000);

		        index++; // Move to next order

		    } 
		    catch (org.openqa.selenium.NoSuchElementException e) 
		    {
		        // Check if "Load More" is available
		    	try 
		    	{
		    	    WebElement scrollerr = driver.findElement(By.xpath("//div[contains(@class,'overflow-y-scroll')]"));
		    	    Thread.sleep(2000);
		    	    WebElement loadMore = driver.findElement(By.xpath("//p[contains(text(),'Load More')]"));

		    	    if (loadMore.isDisplayed()) 
		    	    {
		    	        js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", scrollerr);
		    	        Thread.sleep(1000);
		    	        try 
		    	        {
		    	            loadMore.click(); // normal click
		    	        } catch (Exception clickErr) 
		    	        {
		    	            js.executeScript("arguments[0].click();", loadMore); // fallback JS click
		    	        }
		    	        Thread.sleep(2000);
		    	        continue; // Retry the same index
		    	    }
		    	} 
		    	catch (Exception nested) 
		    	{
		    	    System.out.println("No 'Load More' found or not clickable.");
		    	    break;
		    	}
		    }
		}
		    csv.zeptoExportToCSV(allOrders, "src/test/java/csvFiles/zepto_orders.csv");    
	}
	
    private ZeptoOrder fetchOrderDetails() throws InterruptedException 
    {
        ZeptoOrder order = new ZeptoOrder();
        
		//Clicking on i icon
		// Scroll until the i icon is visible and click it
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		By iIconLocator = By.xpath("//img[contains(@class, 'relative') and contains(@src, 'promo-cash-cart-info-icon.png')]");
		// Scroll container div
		WebElement scrollContainer = driver.findElement(By.xpath("//div[contains(@class,'overflow-y-scroll')]"));

		int maxScrollAttempts = 20;
		int attempt = 0;
		boolean elementFound = false;

		while (attempt < maxScrollAttempts) 
		{
		    try 
		    {
		        WebElement iIcon = driver.findElement(iIconLocator);
		        if (iIcon.isDisplayed()) 
		        {
		            iIcon.click();
		            elementFound = true;
		            break;
		        }
		    } 
		    catch (Exception e) 
		    {
		        // Element not visible yet
		    }

		    jse.executeScript("arguments[0].scrollBy(0, 300);", scrollContainer);
		    Thread.sleep(1000);
		    attempt++;
		}

		if (!elementFound) 
		{
		    throw new RuntimeException("i icon not found after scrolling.");
		}

		try 
		{
		//Fetching Item Cost
		WebElement costOfItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='text-heading8 truncate text-left'])[3]")));
		String itemCostText = costOfItem.getText();
		String cleanValue1 = itemCostText.replaceAll("[^\\d.]", "");
		order.itemCost = Double.parseDouble(cleanValue1);
	    } 
		catch (Exception e) 
		{
	        order.itemCost = 0.0;
	    }
		
		try 
		{
		//Fetching Item Handling Cost
		WebElement itemHandleCost = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='text-body4 truncate text-left'])[1]")));
		String costText = itemHandleCost.getText();
		String cleanValue2 = costText.replaceAll("[^\\d.]", "");
		order.itemHandlingCost = Double.parseDouble(cleanValue2);
		} 
		catch (Exception e) 
		{
	        order.itemHandlingCost = 0.0;
	    }
		
		try 
		{
		//Fetching GST
		WebElement gstOnCost = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='text-body4 truncate text-left'])[2]")));
		String gstText = gstOnCost.getText();
		String cleanValue3 = gstText.replaceAll("[^\\d.]", "");
		order.gst = Double.parseDouble(cleanValue3);
		} 
		catch (Exception e) 
		{
	        order.gst = 0.0;
	    }
		
		JavascriptExecutor popUpClose = (JavascriptExecutor) driver;
		popUpClose.executeScript("document.querySelector('div.bg-skin-inverted').remove();");
		
		try 
		{
	        WebElement scroll = driver.findElement(By.xpath("//div[@class='hidden flex-col lg:block lg:h-[80vh] lg:w-2/3 lg:overflow-y-scroll lg:rounded-r-3xl lg:border-l']"));
	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        int scrollAttempts = 0;
	        int maxScrolls = 20;

	        while (scrollAttempts < maxScrolls) 
	        {
	            boolean allFound = true;

	            try 
	            {
	                driver.findElement(By.xpath("(//span[@class='text-heading8 truncate text-left'])[1]")); // Total Bill
	            } catch (Exception e) 
	            {
	                allFound = false;
	            }

	            try 
	            {
	                driver.findElement(By.xpath("(//span[@class='text-heading8 truncate text-left'])[2]")); // Delivery Fee
	            } catch (Exception e) 
	            {
	                allFound = false;
	            }

	            try 
	            {
	                driver.findElement(By.xpath("(//p[@class='text-body4 leading-3'])[1]")); // Order ID
	            } catch (Exception e) 
	            {
	                allFound = false;
	            }

	            if (allFound) break;

	            js.executeScript("arguments[0].scrollBy(0, 300);", scroll);
	            Thread.sleep(1000);
	            scrollAttempts++;
	        }

	        if (scrollAttempts == maxScrolls) 
	        {
	            System.out.println("Warning: Some elements might not have loaded after scrolling.");
	        }
	    } 
		catch (Exception e) 
		{
	        System.out.println("Error while scrolling for final details.");
	        e.printStackTrace();
	    }
		
		try 
		{
		//Total Bill
			Thread.sleep(1000);
		WebElement total = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='text-cta1 truncate text-left']")));
		String billText = total.getText();
		String cleanValue5 = billText.replaceAll("[^\\d.]", "");
		order.totalBill = Double.parseDouble(cleanValue5);
		}
		catch (Exception e) 
		{
	        order.totalBill = 0.0;
	    }
		
		try 
		{
		//Fetching Delivery Fee
		WebElement delivery = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='text-heading8 truncate text-left'])[2]")));
		String deliveryText = delivery.getText();
		String cleanValue4 = deliveryText.replaceAll("[^\\d.]", "");
		order.deliveryFee = Double.parseDouble(cleanValue4);
		}
		catch (Exception e) 
		{
	        order.deliveryFee = 0.0;
	    }
		
		try 
		{
		//Processing Fees
		WebElement processingFees = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='text-heading8 truncate text-left'])[3]")));
		String processingText = processingFees.getText();
		String cleanValue5 = processingText.replaceAll("[^\\d.]", "");
		order.processingFee = Double.parseDouble(cleanValue5);
		}
		catch (Exception e)
		{
	        order.processingFee = 0.0;
	    }
		
		//Fetching Order ID
		WebElement target = driver.findElement(By.xpath("(//p[@class='text-body4 leading-3'])[1]"));
		order.orderId = target.getText();
		
		//Fetching OrderDate
		WebElement orderPlacedElement = driver.findElement(By.xpath("(//p[@class='text-body4 leading-3'])[3]"));
		String rawText = orderPlacedElement.getText();

		String cleanedDate = rawText
		    .replaceAll("(?<=\\d)(st|nd|rd|th)", "")   // remove ordinal
		    .replace('\u00A0', ' ')                   // replace non-breaking space
		    .replaceAll("[\\r\\n]", "")               // remove carriage returns and new lines
		    .replaceAll("\\s+", " ")                  // normalize all whitespace
		    .trim();

		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
		    .parseCaseInsensitive()
		    .appendPattern("d MMM yyyy, hh:mm a")
		    .toFormatter(Locale.ENGLISH);

		try 
		{
		    LocalDateTime dateTime = LocalDateTime.parse(cleanedDate, formatter);
		    LocalDate date = dateTime.toLocalDate();
		    order.orderDate = date;
		} 
		catch (Exception e) 
		{
		    System.err.println("Failed to parse: [" + cleanedDate + "]");
		    e.printStackTrace();
		}
		return order;
    }
}	