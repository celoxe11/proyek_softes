package com.proyek_softes.demo.tests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.proyek_softes.demo.pages.LoginPage;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected String baseUrl = "https://suitecrm.com";
    protected String browser = "chrome"; // default browser

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        this.browser = browser;
        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            System.out.println("\n=== Firefox Browser Opened - Starting Test ===");
        } else {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();

            // Disable password breach warning and password manager features
            options.addArguments("--disable-features=PasswordLeakDetection");
            options.setExperimentalOption("prefs", java.util.Map.of(
                    "credentials_enable_service", false,
                    "profile.password_manager_enabled", false,
                    "profile.password_manager_leak_detection", false));

            driver = new ChromeDriver(options);
            System.out.println("\n=== Chrome Browser Opened - Starting Test ===");
        }
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(15));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("=== Test Complete - Closing Browser ===\n");
            driver.quit();
            driver = null;
        }
    }

    // login helper method, to be used in tests
    protected void login(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        // Wait until URL indicates we are logged in (dashboard page)
        wait.until(ExpectedConditions.urlContains("module=Home&action=Demo"));
    }

    // login helper method for SuiteCRM 8
    protected void login8(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login8(username, password);

        // Wait until URL indicates we are logged in (dashboard page)
        wait.until(ExpectedConditions.urlContains("/home"));
    }

    // Take screenshot of current viewport
    protected void takeScreenshot(String fileName) {
        try {
            // Create screenshots directory if it doesn't exist
            File screenshotDir = new File("screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            // Take screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            // Add timestamp to filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fullFileName = fileName + "_" + timestamp + ".png";

            // Copy to destination
            File destination = new File("screenshots/" + fullFileName);
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Screenshot saved: " + destination.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Take screenshot of specific element
    protected void takeElementScreenshot(String fileName, WebElement element) {
        try {
            // Create screenshots directory if it doesn't exist
            File screenshotDir = new File("screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            // Take element screenshot
            File source = element.getScreenshotAs(OutputType.FILE);

            // Add timestamp to filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fullFileName = fileName + "_" + timestamp + ".png";

            // Copy to destination
            File destination = new File("screenshots/" + fullFileName);
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Element screenshot saved: " + destination.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
