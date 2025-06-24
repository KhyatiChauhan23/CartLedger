package cartLedgerPages;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class blinkItLogin
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
	public void blinkItLogin() throws FileNotFoundException, IOException
	{		
		driver.get("https://blinkit.com/");
		
		try
		{
			WebElement detectLocationBtn = wait.until(ExpectedConditions.elementToBeClickable(
				    By.xpath("//button[text()='Detect my location']")));
				detectLocationBtn.click();
				
			wait.until(ExpectedConditions.invisibilityOfElementLocated(
					    By.className("LocationDropDown__LocationOverlay-sc-bx29pc-1")));
		}
		catch(Exception e) {}
		
		WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ProfileButton__Text-sc-975teb-2 bFHCDW']")));
		login.click();
				
		Properties props = new Properties();
		props.load(new FileInputStream("config.properties"));
		String phone = props.getProperty("phone.number");
		
		WebElement phoneNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter mobile number']")));
		phoneNumber.sendKeys(phone);
		
		driver.findElement(By.xpath("//button[normalize-space()='Continue']")).click();
		
		System.out.println("Enter the OTP recieved on your phone: ");
		Scanner sc = new Scanner(System.in);
		String otp = sc.nextLine();
		
		List<WebElement> otpInput = driver.findElements(By.xpath("(//input[@type='tel'])"));
		for(int i=0; i<otp.length();i++)
		{
			otpInput.get(i).sendKeys(String.valueOf(otp.charAt(i)));
		}
	}
}
