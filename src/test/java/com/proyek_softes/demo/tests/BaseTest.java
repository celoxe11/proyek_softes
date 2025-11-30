package com.proyek_softes.demo.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    protected static WebDriver driver;
    protected String baseUrl = "https://demo.suiteondemand.com/index.php?module=Users&action=Login";

    @BeforeSuite
    public void setUpSuite() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println("=== Browser Opened - Starting Test Suite ===");
    }

    @AfterSuite
    public void tearDownSuite() {
        if (driver != null) {
            System.out.println("=== Test Suite Complete - Closing Browser ===");
            driver.quit();
        }
    }
}
