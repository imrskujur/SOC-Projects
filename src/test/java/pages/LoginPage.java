package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    By username = By.name("username");
    By password = By.name("password");
    By loginBtn = By.cssSelector("button[type='submit']");
    By errorMsg = By.xpath("//p[contains(@class, 'oxd-alert-content-text')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String user, String pass) {
        driver.findElement(username).clear();
        driver.findElement(username).sendKeys(user);
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginBtn).click();
    }

    public boolean isErrorMessageVisible() {
        return driver.findElements(errorMsg).size() > 0;
    }
}