package com.proyek_softes.demo.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import io.github.bonigarcia.wdm.WebDriverManager;

import com.proyek_softes.demo.pages.LoginPage;
import org.openqa.selenium.By;

public class BaseTest {
    protected static WebDriver driver;
    protected String baseUrl = "https://demo.suiteondemand.com/index.php?module=Users&action=Login";

    @BeforeSuite
    @Parameters("browser")
    public void setUpSuite(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            System.out.println("=== Firefox Browser Opened - Starting Test Suite ===");
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            System.out.println("=== Chrome Browser Opened - Starting Test Suite ===");
        }
        driver.manage().window().maximize();
    }

    @AfterSuite
    public void tearDownSuite() {
        if (driver != null) {
            System.out.println("=== Test Suite Complete - Closing Browser ===");
            driver.quit();
        }
    }

    protected void login(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLogin(baseUrl);

        // Check if login inputs are present (if not, we might already be logged in)
        if (driver.findElements(By.id("user_name")).size() > 0) {
            loginPage.login(username, password);
        } else {
            System.out.println("Already logged in or redirected to dashboard.");
        }
    }
}
