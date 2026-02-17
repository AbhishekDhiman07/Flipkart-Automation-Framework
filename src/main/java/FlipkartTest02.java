import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.time.Duration;

public class FlipkartTest02 {
    public static void main(String[] args) {
        // 1. Setup Report
        ExtentSparkReporter spark = new ExtentSparkReporter("reports/TestReport.html");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            driver.manage().window().maximize();
            driver.get("https://www.flipkart.com");

            String[] itemsToTest = {"iPhone 16", "Samsung S24"};

            for (String item : itemsToTest) {
                // Create a test entry in the report for each item
                ExtentTest test = extent.createTest("Testing: " + item);
                
                try {
                    WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));
                    searchBox.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
                    searchBox.sendKeys(item + Keys.ENTER);

                    // Sync and Extract
                    wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), item.split(" ")[0]));
                    WebElement price = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("(//div[contains(@class, 'Nx9Wp0') or contains(text(), '₹')])[1]")));

                    // Take Screenshot
                    String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + item.replace(" ", "_") + ".png";
                    File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    FileUtils.copyFile(src, new File(screenshotPath));

                    // Log Success to Report with Screenshot
                    test.pass("Successfully found " + item + " at price: " + price.getText(),
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                    
                } catch (Exception e) {
                    test.fail("Failed to extract data for " + item + ". Error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Critical Error: " + e.getMessage());
        } finally {
            driver.quit();
            extent.flush(); // This actually writes the report file
            System.out.println("HTML Report generated: reports/TestReport.html");
        }
    }
}