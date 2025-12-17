package com.proyek_softes.demo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.proyek_softes.demo.pages.LoginPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    protected WebDriver driver;
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
            driver = new ChromeDriver();
            System.out.println("\n=== Chrome Browser Opened - Starting Test ===");
        }
        driver.manage().window().maximize();
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

        // Check if login inputs are present (if not, we might already be logged in)
        if (!driver.findElements(By.id("user_name")).isEmpty()) {
            loginPage.login(username, password);

            // Wait for dashboard to fully load after login
            try {
                // Wait for navigation menu to be present (indicates page is loaded)
                org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(
                        driver, java.time.Duration.ofSeconds(15));
                wait.until(org.openqa.selenium.support.ui.ExpectedConditions
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
