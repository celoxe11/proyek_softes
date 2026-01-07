package com.proyek_softes.landing.main.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
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
     * Mengambil screenshot dan menyimpannya dengan nama file timestamp
     * MENUNGGU halaman selesai loading sebelum screenshot
     * 
     * @param driver   WebDriver instance
     * @param testName Nama test untuk penamaan file
     * @return Path ke file screenshot
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            // Tunggu halaman selesai loading
            waitForPageLoad(driver);

            // Buat direktori jika belum ada
            createScreenshotDirectory();

            // Generate nama file dengan timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + fileName;

            // Ambil screenshot
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

            // Copy ke lokasi target (REPLACE jika sudah ada)
            Path destination = Paths.get(filePath);
            Files.copy(srcFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("📸 Screenshot saved: " + filePath);
            return filePath;

        } catch (IOException e) {
            System.out.println("❌ Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }

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

            createScreenshotDirectory();

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + customSuffix + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + fileName;

            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

            Path destination = Paths.get(filePath);
            Files.copy(srcFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("📸 Screenshot saved: " + filePath);
            return filePath;

        } catch (IOException e) {
            System.out.println("❌ Failed to take screenshot: " + e.getMessage());
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
            System.out.println("📁 Created screenshot directory: " + SCREENSHOT_DIR);
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

            System.out.println("✓ Page fully loaded, ready for screenshot");

        } catch (Exception e) {
            System.out.println("⚠️ Warning: Could not fully wait for page load: " + e.getMessage());
            // Fallback: tunggu sebentar
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Mendapatkan path direktori screenshot
     * 
     * @return Path direktori screenshot
     */
    public static String getScreenshotDirectory() {
        return SCREENSHOT_DIR;
    }
}
