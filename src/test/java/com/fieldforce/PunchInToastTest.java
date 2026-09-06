package com.fieldforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class PunchInToastTest {

    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 1);
        options.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // 1. Login
            driver.get("https://test.fieldforceconnect.com/");
            WebElement user = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@type='text' or @type='email' or contains(@placeholder,'Mobile')]")));
            user.sendKeys("bhirudkhushbu67@gmail.com");

            try {
                WebElement pass = driver.findElement(By.xpath("//input[@type='password' or contains(@placeholder,'Password')]"));
                if (pass.isDisplayed()) pass.sendKeys("22402240");
            } catch (Exception ignored) {}

            driver.findElement(By.xpath("//button[contains(.,'Sign In')]")).click();
            wait.until(ExpectedConditions.urlContains("dashboard"));
            Thread.sleep(3000);

            // 2. Click Punch-In Card
            WebElement punchCard = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(text(),'Punched In') or contains(@class,'punch')]")));
            punchCard.click();

            // 3. Capture Toast Message
            WebElement notification = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@class,'toast') or contains(@class,'popup') or contains(@class,'alert') or contains(@class,'notification') or contains(@class,'banner') or contains(text(),'data available')]")));
            
            System.out.println("[PASS] Task 2 Validated! Captured Message: " + notification.getText());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}

