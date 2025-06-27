package cartLedgerPages;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class blinkItOrderHistoryPage 
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
	public void blinkItOrderHistory() 
	{
		WebElement account = driver.findElement(By.xpath("//div[@class='ProfileButton__Text-sc-975teb-2 bFHCDW']"));
		account.click();
		
		WebElement myOrders = driver.findElement(By.xpath("//div[@class='account-dropdown--nav_item full-width']"));
		myOrders.click();
	}
}
