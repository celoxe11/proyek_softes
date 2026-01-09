package com.proyek_softes.landing.tests.main;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;

/**
 * Test class untuk Browser Compatibility
 * Menguji kompatibilitas website dengan berbagai browser
 */
public class BrowserCompatibilityTest {

    private WebDriver specificDriver;

    @Test(priority = 1)
    @Description("MAIN-001")
    public void testMain001_ChromeBrowser() {
        System.out.println("\n=== Test MAIN-001: Google Chrome Browser ===");
        
        // Setup Chrome browser
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-notifications");
        specificDriver = new ChromeDriver(chromeOptions);
        
        System.out.println("Browser Google Chrome berhasil dijalankan");

        // Akses halaman utama
        specificDriver.get("https://suitecrm.com/");
        System.out.println("Akses halaman utama https://suitecrm.com/");
        
        waitSeconds(3);

        // Validasi title website
        String pageTitle = specificDriver.getTitle();
        System.out.println("Page Title: " + pageTitle);
        
        boolean titleContainsSuiteCRM = pageTitle.contains("SuiteCRM");
        assertTrue(titleContainsSuiteCRM, 
                "Title harus mengandung 'SuiteCRM', actual: " + pageTitle);
        
        System.out.println("Validasi title berhasil - mengandung 'SuiteCRM'");

        // Screenshot halaman utama
        takeScreenshotWithDriver(specificDriver, "MAIN-001_Chrome_HomePage");
        System.out.println("Screenshot halaman utama berhasil");
    }

    @Test(priority = 2)
    @Description("MAIN-002")
    public void testMain002_FirefoxBrowser() {
        System.out.println("\n=== Test MAIN-002: Mozilla Firefox Browser ===");
        
        // Setup Firefox browser
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--width=1920");
        firefoxOptions.addArguments("--height=1080");
        specificDriver = new FirefoxDriver(firefoxOptions);
        specificDriver.manage().window().maximize();
        
        System.out.println("Browser Mozilla Firefox berhasil dijalankan");

        // Akses halaman utama
        specificDriver.get("https://suitecrm.com/");
        System.out.println("Akses halaman utama https://suitecrm.com/");
        
        waitSeconds(3);

        // Validasi title website
        String pageTitle = specificDriver.getTitle();
        System.out.println("Page Title: " + pageTitle);
        
        boolean titleContainsSuiteCRM = pageTitle.contains("SuiteCRM");
        assertTrue(titleContainsSuiteCRM, 
                "Title harus mengandung 'SuiteCRM', actual: " + pageTitle);
        
        System.out.println("Validasi title berhasil - mengandung 'SuiteCRM'");

        // Screenshot halaman utama
        takeScreenshotWithDriver(specificDriver, "MAIN-002_Firefox_HomePage");
        System.out.println("Screenshot halaman utama berhasil");

    }

    @AfterMethod
    public void closeBrowser() {
        if (specificDriver != null) {
            System.out.println("Closing browser");
            specificDriver.quit();
            specificDriver = null;
        }
    }

    private void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void takeScreenshotWithDriver(WebDriver driver, String screenshotName) {
        try {
            org.openqa.selenium.TakesScreenshot ts = (org.openqa.selenium.TakesScreenshot) driver;
            java.io.File source = ts.getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            
            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String fileName = screenshotName + "_" + timestamp + ".png";
            
            java.io.File destination = new java.io.File("screenshots/" + fileName);
            destination.getParentFile().mkdirs();
            
            java.nio.file.Files.copy(source.toPath(), destination.toPath(), 
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            
            System.out.println("Screenshot: screenshots/" + fileName);
            
            // Attach to Allure report
            io.qameta.allure.Allure.addAttachment(screenshotName, 
                    new java.io.FileInputStream(destination));
                    
        } catch (Exception e) {
            System.out.println("Screenshot gagal: " + e.getMessage());
        }
    }
}
