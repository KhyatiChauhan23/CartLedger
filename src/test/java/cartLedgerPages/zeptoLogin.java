package cartLedgerPages;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class zeptoLogin extends baseClass
{
	@Test
	public void login() throws FileNotFoundException, IOException
	{
		Properties props = new Properties();
		props.load(new FileInputStream("config.properties"));
		String phone = props.getProperty("phone.number");
		
		WebDriver driver = baseClass.getDriver();
		driver.get("https://www.zeptonow.com/");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='text-sm capitalize']")));
		loginButton.click();
		
		WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter Phone Number']")));
		phoneInput.sendKeys(phone);
		
		WebElement getOtp = driver.findElement(By.xpath("//div[normalize-space()='Continue']"));
		getOtp.click();
		
		String otp = utilities.OTPReader.getOTP("Zepto");
		
		System.out.println();
    	System.out.println("**Execution Starts**");
		
		List<WebElement> otpInput = driver.findElements(By.cssSelector("input[type='text']"));
		for(int i=0; i<otp.length(); i++)
		{
			otpInput.get(i).sendKeys(String.valueOf(otp.charAt(i)));
		}
		
		try
		{
			WebElement popUp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//span[text()='close']]")));
			popUp.click();
		}
		catch(Exception e) {}
	}
}
