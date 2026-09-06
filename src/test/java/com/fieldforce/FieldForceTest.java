package com.fieldforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class FieldForceTest {
    // Parameterized Data: {Username, Password}
    public static String[][] getCredentials() {
        return new String[][] { {"bhirudkhushbu67@gmail.com", "22402240"} };
    }

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            String[] user = getCredentials()[0];
            driver.get("https://test.fieldforceconnect.com/");

            // Enter Username
            WebElement userInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@type='text' or @type='email' or contains(@placeholder,'Mobile')]")));
            userInput.clear();
            userInput.sendKeys(user[0]);

            // Enter Password (if visible)
            try {
                WebElement passInput = driver.findElement(By.xpath("//input[@type='password' or contains(@placeholder,'Password')]"));
                if (passInput.isDisplayed()) passInput.sendKeys(user[1]);
            } catch (Exception ignored) {}

            // Click Sign In
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'Sign In')]"))).click();

            // Validate Login Success
            boolean success = wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("dashboard"),
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Punch')]"))
            ));

            System.out.println(success ? "[PASS] Login Validated!" : "[FAIL] Login Failed!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
