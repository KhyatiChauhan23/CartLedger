package cartLedgerPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class baseClass
{
	private static WebDriver driver;
	
	public static WebDriver getDriver()
	{
		if(driver==null)
		{
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			driver = new ChromeDriver(options);
		}
		return driver;
	}
	
}
