package com.WebDriverDemos;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SecuronixDetectionRulesAutomation {

    public static void main(String[] args) {
        // Set path to ChromeDriver
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");

        WebDriver driver = new ChromeDriver();

        try {
            // 1. Navigate to Securonix login page
            driver.get("https://securonix-instance-url/login");

            // 2. Log in
            driver.findElement(By.id("username")).sendKeys("your_username");
            driver.findElement(By.id("password")).sendKeys("your_password");
            driver.findElement(By.id("login-button")).click();

            // 3. Wait and navigate to detection rule page
            Thread.sleep(5000); // Use WebDriverWait in real code

            driver.get("https://securonix-instance-url/detection-rules");

            // 4. Create a new rule (or edit)
            driver.findElement(By.id("create-rule-btn")).click();
            Thread.sleep(2000);

            // 5. Enter rule details
            driver.findElement(By.id("rule-name")).sendKeys("Mock Rule for Testing");
            driver.findElement(By.id("rule-description")).sendKeys("Simulates alert based on mock log data");

            // Set detection logic (customize as needed)
            driver.findElement(By.id("rule-condition")).sendKeys("event_type == 'LOGIN_FAILURE'");

            // 6. Save the rule
            driver.findElement(By.id("save-rule-btn")).click();
            Thread.sleep(3000);

            // 7. Simulate mock log data (if UI supports it)
            driver.get("https://securonix-instance-url/log-injector");
            driver.findElement(By.id("log-input")).sendKeys("{\"event_type\":\"LOGIN_FAILURE\",\"user\":\"test_user\"}");
            driver.findElement(By.id("submit-log-btn")).click();

            // 8. Navigate to alerts page to validate
            driver.get("https://securonix-instance-url/alerts");
            Thread.sleep(5000);

            WebElement alert = driver.findElement(By.xpath("//td[contains(text(), 'Mock Rule for Testing')]"));

            if (alert != null) {
                System.out.println("✅ Alert generated successfully!");
            } else {
                System.out.println("❌ Alert not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}