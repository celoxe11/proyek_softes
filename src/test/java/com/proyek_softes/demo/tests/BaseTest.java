package com.proyek_softes.demo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl = "https://demo.suiteondemand.com/index.php?module=Users&action=Login";
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
                    "profile.password_manager_leak_detection", false
            ));

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

    protected void login(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLogin(baseUrl);

        // wait until page is loaded
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user_name")));

        // Check if login inputs are present (if not, we might already be logged in)
        if (!driver.findElements(By.id("user_name")).isEmpty()) {
            loginPage.login(username, password);

            // Wait for dashboard to fully load after login
            try {
                wait.until(ExpectedConditions
                        .presenceOfElementLocated(By.id("grouptab_0")));

                // Additional wait to ensure page is fully stable
                Thread.sleep(2000);
                System.out.println("✓ Login successful and dashboard loaded");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.out.println("Warning: Dashboard may not be fully loaded - " + e.getMessage());
            }
        } else {
            System.out.println("Already logged in or redirected to dashboard.");
        }
    }
}
