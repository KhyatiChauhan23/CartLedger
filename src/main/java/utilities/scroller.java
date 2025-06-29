package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class scroller {
	
	public static void scrollUntilVisible(WebDriver driver, By by) {
        int maxScrolls = 10;
        int scrollCount = 0;

        while (scrollCount < maxScrolls) {
            try {
                WebElement element = driver.findElement(by);
                if (element.isDisplayed()) {
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block: 'center'});", element);
                    break;
                }
            } catch (Exception e) {
                // Element not found or not yet loaded, keep scrolling
            }

            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 200);");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // best practice
            }
            scrollCount++;
        }
    }
}
