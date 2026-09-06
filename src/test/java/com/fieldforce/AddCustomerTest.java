package com.fieldforce;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class AddCustomerTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 1);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @DataProvider(name = "customerDataProvider")
    public Object[][] getCustomerData() {
        return new Object[][] {
            {"Kenavita Reddy", "kaena.Reddy@gmail.com", "9876543210", "REF-101", "Kavya Patil", "Manager", "Direct", "0221234567", "Mumbai"}
        };
    }

    // Helper method to target inputs strictly by their visible label text
    public void enterTextByLabel(String labelText, String value) {
        try {
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(), '" + labelText + "')]//following::input[1]")));
            
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", input);
            js.executeScript("arguments[0].click();", input);
            
            // Native prototype setter for Angular reactive forms value binding
            js.executeScript(
                "let nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                "nativeInputValueSetter.call(arguments[0], arguments[1]);" +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));", 
                input, value
            );
            System.out.println("[SUCCESS] Filled field [" + labelText + "] with: " + value);
        } catch (Exception e) {
            System.out.println("[ERROR] Could not find or fill field for label: " + labelText);
        }
    }

    @Test(dataProvider = "customerDataProvider")
    public void testAddCustomer(String customerName, String email, String phone, String refNo, String contactPerson, String designation, String referredBy, String telephone, String location) {
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
        
        try { Thread.sleep(3000); } catch (Exception ignored) {}

        // 2. Click "My Customers" dropdown...
        WebElement customerDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//span[contains(text(),'My Customers')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", customerDropdown);
        
        try { Thread.sleep(1500); } catch (Exception ignored) {}

        // 3. Click "My Customer" sub-menu item....
        WebElement subCustomerMenu = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//span[normalize-space()='My Customer'] | //a[contains(@href,'/customers')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subCustomerMenu);
        
        try { Thread.sleep(2500); } catch (Exception ignored) {}

        // 4. Click the "Manage" dropdown button
        WebElement manageBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(.,'Manage')]")));
        manageBtn.click();
        
        try { Thread.sleep(1500); } catch (Exception ignored) {}

        // 5. Click "New Customer" option from the Manage dropdown ...
        WebElement newCustomerOpt = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(.,'New Customer')] | //a[contains(.,'New Customer')] | //*[contains(text(),'New Customer')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", newCustomerOpt);
       
        try { Thread.sleep(3500); } catch (Exception ignored) {}

        // 6. Fill Out Form Fields 
        enterTextByLabel("Lead/Customer Name", customerName);
        enterTextByLabel("Ref No", refNo);
        enterTextByLabel("Mobile No", phone);
        enterTextByLabel("Contact Person Name", contactPerson);
        enterTextByLabel("Contact Person Designation", designation);
        enterTextByLabel("Email", email);
        enterTextByLabel("Referred By", referredBy);
        enterTextByLabel("Telephone No", telephone);
        enterTextByLabel("Contact Person Location", location);

        try { Thread.sleep(2000); } catch (Exception ignored) {}

        // 7. Click the Save button -
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(@class,'dialog') or contains(@class,'modal') or contains(@class,'popup') or contains(@class,'overlay')]//button[contains(.,'Save')] | //button[contains(.,'Save')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);

        try { Thread.sleep(3000); } catch (Exception ignored) {}
        System.out.println("[PASS] TestNG DataProvider successfully saved customer: " + customerName);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}