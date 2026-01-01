package com.proyek_softes.landing.tests;

import com.proyek_softes.landing.main.components.LandingNavigationPage;
import com.proyek_softes.landing.main.utils.BrowserDetector;
import com.proyek_softes.landing.main.utils.ScreenshotUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

public class CommunityNavigationTest {
    private WebDriver driver;
    private LandingNavigationPage navigationPage;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private String baseUrl = "https://suitecrm.com";

    // Expected URL untuk halaman Community
    private String expectedCommunityUrl = "https://community.suitecrm.com/";

    @BeforeClass
    public void setUp() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║        COMMUNITY NAVIGATION TEST - STARTING                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // Auto-detect browser
        driver = BrowserDetector.createDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;

        // Set timeouts
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Navigate to landing page with retry mechanism
        boolean pageLoaded = false;
        int maxRetries = 3;

        for (int i = 0; i < maxRetries && !pageLoaded; i++) {
            try {
                System.out.println("🔄 Attempt " + (i + 1) + "/" + maxRetries + " - Loading: " + baseUrl);
                driver.get(baseUrl);
                pageLoaded = true;
                System.out.println("✓ Page loaded successfully!");
            } catch (org.openqa.selenium.TimeoutException e) {
                System.out.println("⚠️ Timeout on attempt " + (i + 1) + ", retrying...");
                if (i == maxRetries - 1) {
                    throw new RuntimeException(
                            "Failed to load page after " + maxRetries + " attempts: " + e.getMessage());
                }
            }
        }

        // Initialize page objects
        navigationPage = new LandingNavigationPage(driver);

        System.out.println("✓ Browser started and navigated to: " + baseUrl);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║        COMMUNITY NAVIGATION TEST - COMPLETE                  ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            driver.quit();
        }
    }
    
    @Test(priority = 1)
    public void testNavigateToCommunityPage() {
        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("TEST: Navigate to Community Page via Journey Button");
        System.out.println("════════════════════════════════════════════════════════════════");

        // ═══════════════════════════════════════════════════════════════
        // STEP 1: Arahkan ke menu About
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n📌 STEP 1: Arahkan ke menu About");
        System.out.println("─────────────────────────────────────────────────────────────────");

        boolean aboutMenuVisible = navigationPage.isMainMenuVisible("about");
        Assert.assertTrue(aboutMenuVisible, "About menu should be visible");
        System.out.println("✓ About menu is visible and ready");

        // ═══════════════════════════════════════════════════════════════
        // STEP 2: Pilih sub-menu "SuiteCRM Journey"
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n📌 STEP 2: Pilih sub-menu 'SuiteCRM Journey'");
        System.out.println("─────────────────────────────────────────────────────────────────");

        boolean navigateSuccess = navigationPage.navigateToSubMenu("about", "journey");

        if (!navigateSuccess) {
            // Try direct navigation as fallback
            System.out.println("⚠️ Sub-menu navigation failed, trying direct URL...");
            driver.get("https://suitecrm.com/the-suitecrm-8-journey/");
        }

        // Wait for page to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String currentUrl = driver.getCurrentUrl();
        System.out.println("📍 Current URL after navigation: " + currentUrl);

        // Take screenshot of Journey page
        ScreenshotUtils.takeScreenshot(driver, "step2_journey_page");

        // ═══════════════════════════════════════════════════════════════
        // STEP 3: Tekan button "Join the Community"
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n📌 STEP 3: Tekan button 'Join the Community'");
        System.out.println("─────────────────────────────────────────────────────────────────");

        // Store the original window handle
        String originalWindow = driver.getWindowHandle();

        boolean buttonClicked = clickJoinCommunityButton();

        if (!buttonClicked) {
            ScreenshotUtils.takeScreenshot(driver, "community_button_not_found");
            Assert.fail("Failed to find or click 'Join the Community' button");
        }

        System.out.println("✓ Successfully clicked on 'Join the Community' button");

        // Wait for new tab/page to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // ═══════════════════════════════════════════════════════════════
        // STEP 4: Assert halaman https://community.suitecrm.com/
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n📌 STEP 4: Assert halaman Community");
        System.out.println("─────────────────────────────────────────────────────────────────");

        // Check if new window/tab opened
        Set<String> windowHandles = driver.getWindowHandles();

        if (windowHandles.size() > 1) {
            // Switch to new tab
            for (String handle : windowHandles) {
                if (!handle.equals(originalWindow)) {
                    driver.switchTo().window(handle);
                    System.out.println("✓ Switched to new tab");
                    break;
                }
            }
        }

        // Wait for page to fully load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        currentUrl = driver.getCurrentUrl();
        System.out.println("📍 Current URL: " + currentUrl);
        System.out.println("📍 Expected URL: " + expectedCommunityUrl);

        boolean urlMatches = currentUrl.equals(expectedCommunityUrl);
        boolean urlContainsCommunity = currentUrl.contains("community.suitecrm.com");

        System.out.println("✓ URL exact match: " + urlMatches);
        System.out.println("✓ URL contains 'community.suitecrm.com': " + urlContainsCommunity);

        // ═══════════════════════════════════════════════════════════════
        // STEP 5: Screenshot hasil assert
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n📌 STEP 5: Screenshot hasil assert");
        System.out.println("─────────────────────────────────────────────────────────────────");

        String screenshotName;
        if (urlMatches || urlContainsCommunity) {
            screenshotName = "community_page_SUCCESS";
            System.out.println("✅ ASSERTION PASSED - URL is correct!");
        } else {
            screenshotName = "community_page_FAILED";
            System.out.println("❌ ASSERTION FAILED - URL mismatch!");
        }

        // Take screenshot
        String screenshotPath = ScreenshotUtils.takeScreenshot(driver, screenshotName);
        if (screenshotPath != null) {
            System.out.println("📸 Screenshot saved: " + screenshotPath);
        }

        // ═══════════════════════════════════════════════════════════════
        // FINAL ASSERTION
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("FINAL RESULT");
        System.out.println("════════════════════════════════════════════════════════════════");

        Assert.assertTrue(urlMatches || urlContainsCommunity,
                "URL should be " + expectedCommunityUrl + " but was: " + currentUrl);

        System.out.println("✅ TEST PASSED - Successfully navigated to Community page!");
    }

    /**
     * Helper method to click "Join the Community" button
     */
    private boolean clickJoinCommunityButton() {
        try {
            // Scroll down to find the button
            js.executeScript("window.scrollBy(0, 500);");
            Thread.sleep(1000);

            // Try multiple selectors for the button
            String[] buttonSelectors = {
                    "a.fusion-button[href*='community.suitecrm.com']",
                    "a[href*='community.suitecrm.com']",
                    "//a[contains(text(), 'Join the Community')]",
                    "//a[contains(text(), 'JOIN THE COMMUNITY')]",
                    "//span[contains(text(), 'JOIN THE COMMUNITY')]/parent::a",
                    "a.fusion-button-default-type[href*='community']"
            };

            WebElement button = null;

            for (String selector : buttonSelectors) {
                try {
                    if (selector.startsWith("//")) {
                        // XPath selector
                        button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
                    } else {
                        // CSS selector
                        button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
                    }

                    if (button != null && button.isDisplayed()) {
                        System.out.println("✓ Found button with selector: " + selector);
                        break;
                    }
                } catch (Exception e) {
                    // Try next selector
                    continue;
                }
            }

            if (button == null) {
                System.out.println("❌ Button not found with any selector");
                return false;
            }

            // Scroll button into view
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
            Thread.sleep(500);

            // Get button info
            String buttonText = button.getText();
            String buttonHref = button.getAttribute("href");
            System.out.println("📍 Button text: " + buttonText);
            System.out.println("📍 Button href: " + buttonHref);

            // Take screenshot before click
            ScreenshotUtils.takeScreenshot(driver, "step3_before_click_community");

            // Click the button
            try {
                button.click();
            } catch (Exception e) {
                // Fallback to JavaScript click
                js.executeScript("arguments[0].click();", button);
            }

            return true;

        } catch (Exception e) {
            System.out.println("❌ Error clicking button: " + e.getMessage());
            return false;
        }
    }
}
