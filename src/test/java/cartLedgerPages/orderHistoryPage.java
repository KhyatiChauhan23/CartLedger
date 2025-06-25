package cartLedgerPages;
import java.time.Duration;
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
		
		//Fetching Order ID
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
		WebElement target = driver.findElement(By.xpath("(//p[@class='text-body4 leading-3'])[1]"));
		ZeptoOrder.orderId = target.getText();
		System.out.println(ZeptoOrder.orderId);
	}
}

