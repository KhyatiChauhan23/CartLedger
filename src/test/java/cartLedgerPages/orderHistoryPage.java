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
import org.testng.annotations.Test;
import resources.ZeptoScraper.ZeptoOrder;
import org.openqa.selenium.JavascriptExecutor;

public class orderHistoryPage 
{
	WebDriver driver;
	WebDriverWait wait;
	
	@BeforeClass
	public void setup()
	{
			driver = baseClass.getDriver();
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	@Test
	public void orderHistory() throws InterruptedException
	{
		WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='text-sm capitalize']")));
		profile.click();
		
		//Clicking on 1st recent order:
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("(//div[@class='px-4'])[1]"))));
		element.click();
		
		Thread.sleep(2000);
		
		//Clicking on i icon
		
		// Scroll until the i icon is visible and click it
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		By iIconLocator = By.xpath("(//img[@class='relative overflow-hidden'])[5]");

		// Scroll container div
		WebElement scrollContainer = driver.findElement(By.xpath("//div[contains(@class,'overflow-y-scroll')]"));

		int maxScrollAttempts = 20;
		int attempt = 0;
		boolean elementFound = false;

		while (attempt < maxScrollAttempts) {
		    try {
		        WebElement iIcon = driver.findElement(iIconLocator);
		        if (iIcon.isDisplayed()) {
		            iIcon.click();
		            elementFound = true;
		            break;
		        }
		    } catch (Exception e) {
		        // Element not visible yet
		    }

		    jse.executeScript("arguments[0].scrollBy(0, 300);", scrollContainer);
		    Thread.sleep(500);
		    attempt++;
		}

		if (!elementFound) {
		    throw new RuntimeException("i icon not found after scrolling.");
		}

		
		//Fetching Item Cost
		WebElement costOfItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='text-heading8 truncate text-left'])[3]")));
		String itemCostText = costOfItem.getText();
		String cleanValue1 = itemCostText.replaceAll("[^\\d.]", "");
		ZeptoOrder.itemCost = Double.parseDouble(cleanValue1);
		System.out.println(ZeptoOrder.itemCost);
		
		//Fetching Item Handling Cost
		WebElement itemHandleCost = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='text-body4 truncate text-left'])[1]")));
		String costText = itemHandleCost.getText();
		String cleanValue2 = costText.replaceAll("[^\\d.]", "");
		ZeptoOrder.itemHandlingCost = Double.parseDouble(cleanValue2);
		System.out.println(ZeptoOrder.itemHandlingCost);
		
		//Fetching GST
		WebElement gstOnCost = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='text-body4 truncate text-left'])[2]")));
		String gstText = gstOnCost.getText();
		String cleanValue3 = gstText.replaceAll("[^\\d.]", "");
		ZeptoOrder.gst = Double.parseDouble(cleanValue3);
		System.out.println(ZeptoOrder.gst);
		
		JavascriptExecutor popUpClose = (JavascriptExecutor) driver;
		popUpClose.executeScript("document.querySelector('div.bg-skin-inverted').remove();");

		Thread.sleep(2000);
		
		//Fetching Delivery Fee
		WebElement delivery = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='text-heading8 truncate text-left'])[2]")));
		String deliveryText = delivery.getText();
		String cleanValue4 = deliveryText.replaceAll("[^\\d.]", "");
		ZeptoOrder.deliveryFee = Double.parseDouble(cleanValue4);
		System.out.println(ZeptoOrder.deliveryFee);

		Thread.sleep(1000);
		
		//Scrolling till the end
		try 
		{
		WebElement scroll = driver.findElement(By.xpath("//div[@class='hidden flex-col lg:block lg:h-[80vh] lg:w-2/3 lg:overflow-y-scroll lg:rounded-r-3xl lg:border-l']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollTo({ top: arguments[0].scrollHeight, behavior: 'smooth' });", scroll);
		}
		catch (Exception e) 
		{
            e.printStackTrace();
		}
		
		//Total Bill
		WebElement total = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='text-heading8 truncate text-left'])[1]")));
		String billText = total.getText();
		String cleanValue5 = billText.replaceAll("[^\\d.]", "");
		ZeptoOrder.totalBill = Double.parseDouble(cleanValue5);
		System.out.println(ZeptoOrder.totalBill);
		
		
		//Fetching Order ID
		WebElement target = driver.findElement(By.xpath("(//p[@class='text-body4 leading-3'])[1]"));
		ZeptoOrder.orderId = target.getText();
		
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

		try {
		    LocalDateTime dateTime = LocalDateTime.parse(cleanedDate, formatter);
		    LocalDate date = dateTime.toLocalDate();
		    System.out.println("Parsed: " + date);
		    ZeptoOrder.orderDate = date;
		} catch (Exception e) {
		    System.err.println("Failed to parse: [" + cleanedDate + "]");
		    e.printStackTrace();
		}
		
		resources.ZeptoScraper.ZeptoOrder order = new resources.ZeptoScraper.ZeptoOrder();
		System.out.println(order.toString());
	}
}

