package com.proyek_softes.demo.landing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseLandingTest {
    protected static WebDriver driver;
    protected String baseUrl = "https://suitecrm.com"; // Ganti dengan URL landing page Anda

    @BeforeSuite
    @Parameters("browser")
    public void setUpSuite(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            System.out.println("=== Firefox Browser Opened - Starting Landing Page Test Suite ===");
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            System.out.println("=== Chrome Browser Opened - Starting Landing Page Test Suite ===");
        }
        driver.manage().window().maximize();
    }

    @AfterSuite
    public void tearDownSuite() {
        if (driver != null) {
            System.out.println("=== Landing Page Test Suite Complete - Closing Browser ===");
            driver.quit();
        }
    }

    protected void navigateToLandingPage() {
        driver.get(baseUrl);
        System.out.println("Navigated to: " + baseUrl);
    }
}
