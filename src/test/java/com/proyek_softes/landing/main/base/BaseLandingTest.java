package com.proyek_softes.landing.main.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.proyek_softes.landing.main.utils.BrowserDetector;

/**
 * Base Test Class untuk Landing Page Tests
 * Menyediakan setup browser, screenshot, dan helper methods
 */
public class BaseLandingTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected String baseUrl = "https://suitecrm.com";

    @BeforeClass
    public void setUp() {
        driver = BrowserDetector.createDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        loadPage(baseUrl);
        System.out.println("\n=== Browser Ready - Starting Test ===");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            System.out.println("=== Test Complete - Closing Browser ===\n");
            driver.quit();
            driver = null;
        }
    }

    /**
     * Load page dengan retry mechanism
     */
    protected void loadPage(String url) {
        int maxRetries = 3;
        for (int i = 0; i < maxRetries; i++) {
            try {
                driver.get(url);
                wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
                return;
            } catch (Exception e) {
                if (i == maxRetries - 1) {
                    System.out.println("⚠️ Page load timeout, proceeding anyway...");
                }
            }
        }
    }

    /**
     * Navigate ke home page
     */
    protected void navigateToHome() {
        loadPage(baseUrl);
    }

    /**
     * Take screenshot dengan nama file
     */
    protected void takeScreenshot(String fileName) {
        try {
            File screenshotDir = new File("screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            // Wait for page to be ready
            waitForPageLoad();

            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filePath = "screenshots/" + fileName + "_" + timestamp + ".png";
            Files.copy(source.toPath(), new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("📸 Screenshot: " + filePath);
        } catch (IOException e) {
            System.out.println("❌ Screenshot failed: " + e.getMessage());
        }
    }

    /**
     * Wait for page to fully load
     */
    protected void waitForPageLoad() {
        try {
            wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
            Thread.sleep(500);
        } catch (Exception e) {
            // Continue
        }
    }

    /**
     * Scroll to element
     */
    protected void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Click YouTube video and wait
     */
    protected boolean clickYouTubeVideo() {
        try {
            js.executeScript("window.scrollBy(0, 400);");
            Thread.sleep(1000);

            String[] selectors = {
                    "iframe[src*='youtube']",
                    "iframe[src*='youtu.be']"
            };

            WebElement iframe = null;
            for (String selector : selectors) {
                List<WebElement> elements = driver.findElements(By.cssSelector(selector));
                if (!elements.isEmpty()) {
                    iframe = elements.get(0);
                    break;
                }
            }

            if (iframe != null) {
                scrollToElement(iframe);
                driver.switchTo().frame(iframe);

                try {
                    WebElement overlay = wait.until(ExpectedConditions.elementToBeClickable(
                            By.cssSelector(".ytp-cued-thumbnail-overlay")));
                    overlay.click();
                } catch (Exception e) {
                    try {
                        WebElement playBtn = driver.findElement(By.cssSelector(".ytp-large-play-button"));
                        playBtn.click();
                    } catch (Exception e2) {
                        js.executeScript("document.querySelector('video')?.play();");
                    }
                }

                driver.switchTo().defaultContent();
                return true;
            }
            return false;
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            return false;
        }
    }

    /**
     * Wait for specified seconds
     */
    protected void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
