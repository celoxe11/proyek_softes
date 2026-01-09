package com.proyek_softes.landing.main.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;


public class ScreenshotUtils {

    // Pindah ke root folder agar tidak terhapus saat mvn clean
    private static final String SCREENSHOT_DIR = "screenshots/landing/";

    // Default timeout untuk menunggu page load (dalam detik)
    private static final int DEFAULT_PAGE_LOAD_TIMEOUT = 30;
    
    /**
     * Mengambil screenshot dengan nama custom
     * MENUNGGU halaman selesai loading sebelum screenshot
     * 
     * @param driver       WebDriver instance
     * @param testName     Nama test
     * @param customSuffix Suffix tambahan untuk nama file
     * @return Path ke file screenshot
     */
    public static String takeScreenshot(WebDriver driver, String testName, String customSuffix) {
        try {
            // Tunggu halaman selesai loading
            waitForPageLoad(driver);
            
            // Scroll to top untuk memastikan halaman terlihat dari atas
            scrollToTop(driver);

            createScreenshotDirectory();

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + customSuffix + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + fileName;

            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

            Path destination = Paths.get(filePath);
            Files.copy(srcFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Screenshot saved: " + filePath);
            return filePath;

        } catch (IOException e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Mengambil screenshot dengan scroll ke element tertentu
     * Element akan di-highlight dan di-scroll ke center viewport
     * 
     * @param driver       WebDriver instance
     * @param element      Element yang akan ditampilkan di screenshot
     * @param testName     Nama test
     * @param customSuffix Suffix tambahan untuk nama file
     * @return Path ke file screenshot
     */
    public static String takeScreenshot(WebDriver driver, WebElement element, String testName, String customSuffix) {
        try {
            // Tunggu halaman selesai loading
            waitForPageLoad(driver);
            
            // Scroll dan highlight element
            prepareElementForScreenshot(driver, element);

            createScreenshotDirectory();

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + customSuffix + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + fileName;

            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

            Path destination = Paths.get(filePath);
            Files.copy(srcFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Screenshot saved with element highlighted: " + filePath);
            
            // Remove highlight setelah screenshot
            removeElementHighlight(driver, element);
            
            return filePath;

        } catch (IOException e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Mengambil screenshot saat assertion berhasil
     * 
     * @param driver   WebDriver instance
     * @param testName Nama test
     * @return Path ke file screenshot
     */
    public static String takeAssertionScreenshot(WebDriver driver, String testName) {
        return takeScreenshot(driver, testName, "ASSERT_PASSED");
    }

    /**
     * Mengambil screenshot saat test gagal
     * 
     * @param driver   WebDriver instance
     * @param testName Nama test
     * @return Path ke file screenshot
     */
    public static String takeFailureScreenshot(WebDriver driver, String testName) {
        return takeScreenshot(driver, testName, "FAILED");
    }

    /**
     * Membuat direktori screenshot jika belum ada
     */
    private static void createScreenshotDirectory() {
        File dir = new File(SCREENSHOT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Created screenshot directory: " + SCREENSHOT_DIR);
        }
    }

    /**
     * Menunggu halaman selesai loading sebelum screenshot
     * Menggunakan JavaScript document.readyState dan network idle detection
     * 
     * @param driver WebDriver instance
     */
    private static void waitForPageLoad(WebDriver driver) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_PAGE_LOAD_TIMEOUT));

            // 1. Tunggu document.readyState = complete
            wait.until((ExpectedCondition<Boolean>) wd -> {
                String readyState = js.executeScript("return document.readyState").toString();
                return readyState.equals("complete");
            });

            // 2. Tunggu semua images selesai loading
            wait.until((ExpectedCondition<Boolean>) wd -> {
                Boolean imagesLoaded = (Boolean) js.executeScript(
                        "return Array.from(document.images).every(img => img.complete && img.naturalHeight !== 0);");
                return imagesLoaded != null && imagesLoaded;
            });

            // 3. Tunggu jQuery AJAX selesai (jika ada jQuery)
            try {
                Boolean jQueryDefined = (Boolean) js.executeScript("return typeof jQuery !== 'undefined'");
                if (jQueryDefined != null && jQueryDefined) {
                    wait.until((ExpectedCondition<Boolean>) wd -> {
                        Boolean ajaxComplete = (Boolean) js.executeScript("return jQuery.active == 0");
                        return ajaxComplete != null && ajaxComplete;
                    });
                }
            } catch (Exception e) {
                // jQuery tidak ada, skip
            }

            // 4. Tunggu animasi/spinner selesai (extra delay)
            Thread.sleep(500);

            System.out.println("Page fully loaded, ready for screenshot");

        } catch (Exception e) {
            System.out.println("Warning: Could not fully wait for page load: " + e.getMessage());
            // Fallback: tunggu sebentar
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Scroll element ke posisi center dan highlight sebelum screenshot
     * Fungsi ini memastikan button/element terlihat jelas di screenshot
     * 
     * @param driver  WebDriver instance
     * @param element Element yang akan di-scroll dan highlight
     */
    public static void prepareElementForScreenshot(WebDriver driver, WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // 1. Scroll element ke posisi center viewport
            js.executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'});",
                    element);

            // Wait for scroll animation
            Thread.sleep(500);

            // 2. Highlight element dengan border merah tebal
            String originalStyle = element.getAttribute("style");
            js.executeScript(
                    "arguments[0].setAttribute('style', arguments[1] + 'border: 3px solid red; box-shadow: 0 0 10px rgba(255,0,0,0.5);');",
                    element, originalStyle != null ? originalStyle : "");

            // Wait untuk highlight terlihat
            Thread.sleep(300);

            System.out.println("Element prepared for screenshot (scrolled and highlighted)");

        } catch (Exception e) {
            System.out.println("Warning: Could not prepare element for screenshot: " + e.getMessage());
        }
    }
    
    /**
     * Scroll halaman ke paling atas
     * 
     * @param driver WebDriver instance
     */
    private static void scrollToTop(WebDriver driver) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo({top: 0, behavior: 'smooth'});");
            Thread.sleep(300);
        } catch (Exception e) {
            // Ignore errors
        }
    }

    /**
     * Remove highlight dari element setelah screenshot
     * 
     * @param driver  WebDriver instance
     * @param element Element yang akan di-unhighlight
     */
    public static void removeElementHighlight(WebDriver driver, WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].removeAttribute('style');", element);
            System.out.println("Element highlight removed");
        } catch (Exception e) {
            System.out.println("Warning: Could not remove element highlight: " + e.getMessage());
        }
    }

    /**
     * Prepare element, take screenshot, then remove highlight
     * All-in-one method untuk screenshot element
     * 
     * @param driver   WebDriver instance
     * @param element  Element yang akan di-screenshot
     * @param testName Nama test untuk penamaan file
     * @param suffix   Suffix untuk nama file
     * @return Path ke file screenshot
     */
    public static String takeElementScreenshot(WebDriver driver, WebElement element, String testName, String suffix) {
        try {
            // Prepare element (scroll + highlight)
            prepareElementForScreenshot(driver, element);

            // Take screenshot
            String screenshotPath = takeScreenshot(driver, testName, suffix);

            // Remove highlight
            removeElementHighlight(driver, element);

            return screenshotPath;

        } catch (Exception e) {
            System.out.println("Error taking element screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Screenshot element BEFORE clicking it
     * Menampilkan button/element yang akan diklik dengan highlight
     * 
     * @param driver        WebDriver instance
     * @param element       Element yang akan diklik
     * @param testName      Nama test (e.g., "GST-008")
     * @param elementName   Nama element (e.g., "Discover_Button")
     * @return Path ke file screenshot
     */
    public static String screenshotBeforeClick(WebDriver driver, WebElement element, String testName, String elementName) {
        return takeElementScreenshot(driver, element, testName, "Before_Click_" + elementName);
    }
    
    /**
     * Screenshot element, then click it
     * Helper method untuk workflow: screenshot button → click button
     * 
     * @param driver        WebDriver instance
     * @param element       Element yang akan diklik
     * @param testName      Nama test
     * @param elementName   Nama element untuk screenshot
     * @return true jika berhasil click
     */
    public static boolean screenshotAndClick(WebDriver driver, WebElement element, String testName, String elementName) {
        try {
            // 1. Screenshot element (scroll + highlight)
            screenshotBeforeClick(driver, element, testName, elementName);
            
            // 2. Click element
            try {
                element.click();
                System.out.println("Clicked: " + elementName);
                return true;
            } catch (Exception e) {
                // Fallback to JS click
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", element);
                System.out.println("Clicked via JS: " + elementName);
                return true;
            }
            
        } catch (Exception e) {
            System.out.println("Failed to screenshot and click: " + elementName + " - " + e.getMessage());
            return false;
        }
    }
}
