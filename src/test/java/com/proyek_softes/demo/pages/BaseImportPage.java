package com.proyek_softes.demo.pages;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseImportPage {
    
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final String browserType;
    
    public BaseImportPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        
        // Detect browser type from driver class
        String driverClassName = driver.getClass().getSimpleName().toLowerCase();
        if (driverClassName.contains("firefox")) {
            this.browserType = "firefox";
        } else {
            this.browserType = "chrome";
        }
    }
    
    /**
     * Gets the downloaded file name from browser's download page
     * @param timeoutSeconds maximum time to wait for download
     * @return the downloaded file name, or null if not found
     */
    protected String getDownloadedFileName(int timeoutSeconds) {
        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000L);
        String fileName = null;
        
        while (System.currentTimeMillis() < endTime) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                
                if (browserType.equals("firefox")) {
                    // Firefox: Access downloads from the download richlistbox
                    fileName = (String) js.executeScript(
                        "var downloads = document.getElementById('downloadsListBox');" +
                        "if (!downloads) return null;" +
                        "var items = downloads.getElementsByTagName('richlistitem');" +
                        "if (items.length === 0) return null;" +
                        "var fileElem = items[0].querySelector('.downloadTarget');" +
                        "return fileElem ? fileElem.getAttribute('value') : null;");
                } else {
                    // Chrome: Access shadow DOM to get download manager
                    fileName = (String) js.executeScript(
                        "var manager = document.querySelector('downloads-manager');" +
                        "if (!manager || !manager.shadowRoot) return null;" +
                        "var item = manager.shadowRoot.querySelector('downloads-item');" +
                        "if (!item || !item.shadowRoot) return null;" +
                        "var fileLink = item.shadowRoot.querySelector('#file-link');" +
                        "return fileLink ? fileLink.textContent : null;");
                }
                
                if (fileName != null && !fileName.trim().isEmpty()) {
                    System.out.println("Found downloaded file in " + browserType + " browser history: " + fileName);
                    return fileName;
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        
        return fileName;
    }
    
    /**
     * Navigates to the browser's downloads page
     */
    protected void navigateToDownloadsPage() {
        String downloadsUrl = browserType.equals("firefox") ? "about:downloads" : "chrome://downloads";
        driver.get(downloadsUrl);
    }
    
    /**
     * Takes a screenshot of the current downloads page
     * @param screenshotName the name for the screenshot file (without extension)
     */
    protected void takeScreenshotOfDownloadsPage(String screenshotName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("screenshots/" + screenshotName + ".png");
            destination.getParentFile().mkdirs();
            Files.copy(screenshot.toPath(), destination.toPath(), 
                      java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved: " + destination.getAbsolutePath());
        } catch (IOException | WebDriverException e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }
    }
    
    /**
     * Checks if a file name has CSV extension
     * @param fileName the file name to check
     * @return true if the file ends with .csv
     */
    protected boolean isTemplateFileInCSVFormat(String fileName) {
        return fileName != null && fileName.toLowerCase().endsWith(".csv");
    }
}
