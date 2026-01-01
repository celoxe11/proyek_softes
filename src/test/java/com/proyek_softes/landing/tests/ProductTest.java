package com.proyek_softes.landing.tests;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.proyek_softes.landing.main.pages.products.ProductsPage;
import com.proyek_softes.landing.main.pages.products.SuiteCRMPage;
import com.proyek_softes.landing.main.utils.BrowserDetector;
import com.proyek_softes.landing.main.utils.ScreenshotUtils;

public class ProductTest {
    // Test Case ID untuk penamaan screenshot
    private static final String TEST_CASE_ID = "PRD-001";
    
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    
    // Page Objects
    private ProductsPage productsPage;
    private SuiteCRMPage suiteCRMPage;
    
    private String baseUrl = "https://suitecrm.com";
    
    // Expected URL untuk assertion
    private static final String EXPECTED_CRM_URL = "https://suitecrm.com/what-is-crm/";
    
    @BeforeClass
    public void setUp() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║        TEST CASE: " + TEST_CASE_ID + " - PRODUCTS NAVIGATION            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
        // Auto-detect browser
        driver = BrowserDetector.createDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        
        // Set timeouts
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Navigate ke base URL dengan retry mechanism
        loadPageWithRetry(baseUrl);
        
        // Initialize Page Objects
        productsPage = new ProductsPage(driver);
        suiteCRMPage = new SuiteCRMPage(driver);
        
        System.out.println("✓ Setup complete - Browser ready");
    }
    
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║        TEST CASE: " + TEST_CASE_ID + " - COMPLETE                    ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            driver.quit();
        }
    }
    
    /**
     * Test Case PRD-001: Navigate to Products > SuiteCRM > CRM Link
     */
    @Test(priority = 1)
    public void testPRD001_NavigateToCRMPage() {
        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("EXECUTING: " + TEST_CASE_ID + " - Navigate to CRM Page");
        System.out.println("════════════════════════════════════════════════════════════════");
        
        // ═══════════════════════════════════════════════════════════════
        // STEP 1: Arahkan ke menu Products
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n📌 STEP 1: Arahkan ke menu Products");
        System.out.println("─────────────────────────────────────────────────────────────────");
        
        boolean hoverSuccess = productsPage.hoverProductsMenu();
        Assert.assertTrue(hoverSuccess, "Harus berhasil hover ke menu Products");
        System.out.println("✓ STEP 1 PASSED: Menu Products accessible");
        
        // ═══════════════════════════════════════════════════════════════
        // STEP 2: Pilih sub-menu SuiteCRM
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n📌 STEP 2: Pilih sub-menu SuiteCRM");
        System.out.println("─────────────────────────────────────────────────────────────────");
        
        boolean navSuccess = productsPage.navigateToSuiteCRM();
        Assert.assertTrue(navSuccess, "Harus berhasil navigate ke halaman SuiteCRM");
        
        // Tunggu halaman dimuat
        suiteCRMPage.waitForPageLoad();
        
        // Take screenshot halaman SuiteCRM
        ScreenshotUtils.takeScreenshot(driver, TEST_CASE_ID + "_step2_suitecrm_page");
        
        System.out.println("✓ STEP 2 PASSED: Berhasil masuk ke halaman SuiteCRM");
        System.out.println("📍 Current URL: " + driver.getCurrentUrl());
        
        // ═══════════════════════════════════════════════════════════════
        // STEP 3: Klik link "Customer Relationship Management"
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n📌 STEP 3: Klik link 'Customer Relationship Management'");
        System.out.println("─────────────────────────────────────────────────────────────────");
        
        boolean clickSuccess = suiteCRMPage.clickCRMLink();
        Assert.assertTrue(clickSuccess, "Harus berhasil klik link 'Customer Relationship Management'");
        
        // Tunggu halaman baru dimuat
        suiteCRMPage.waitForPageLoad();
        
        System.out.println("✓ STEP 3 PASSED: Berhasil klik link CRM");
        
        // ═══════════════════════════════════════════════════════════════
        // STEP 4: Assert halaman https://suitecrm.com/what-is-crm/ benar
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n📌 STEP 4: Assert URL halaman");
        System.out.println("─────────────────────────────────────────────────────────────────");
        
        String currentUrl = driver.getCurrentUrl();
        System.out.println("📍 Current URL: " + currentUrl);
        System.out.println("📍 Expected URL: " + EXPECTED_CRM_URL);
        
        boolean urlMatches = currentUrl.equals(EXPECTED_CRM_URL);
        boolean urlContainsCRM = currentUrl.contains("what-is-crm");
        
        System.out.println("✓ Exact URL match: " + urlMatches);
        System.out.println("✓ Contains 'what-is-crm': " + urlContainsCRM);
        
        // ═══════════════════════════════════════════════════════════════
        // STEP 5: Screenshot hasil assert
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n📌 STEP 5: Screenshot hasil assert");
        System.out.println("─────────────────────────────────────────────────────────────────");
        
        String screenshotName;
        if (urlMatches || urlContainsCRM) {
            screenshotName = TEST_CASE_ID + "_PASSED";
            System.out.println("✅ ASSERTION PASSED - URL correct!");
        } else {
            screenshotName = TEST_CASE_ID + "_FAILED";
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
        System.out.println("FINAL RESULT: " + TEST_CASE_ID);
        System.out.println("════════════════════════════════════════════════════════════════");
        
        Assert.assertTrue(urlMatches || urlContainsCRM,
            "URL harus " + EXPECTED_CRM_URL + " tapi actual: " + currentUrl);
        
        System.out.println("✅ TEST " + TEST_CASE_ID + " PASSED!");
        System.out.println("✅ Successfully navigated to: " + currentUrl);
    }
    
    // ========================================
    // HELPER METHODS
    // ========================================
    
    /**
     * Load halaman dengan retry mechanism
     */
    private void loadPageWithRetry(String url) {
        int maxRetries = 3;
        
        for (int i = 0; i < maxRetries; i++) {
            try {
                System.out.println("🔄 Attempt " + (i + 1) + "/" + maxRetries + " - Loading: " + url);
                driver.get(url);
                
                // Tunggu sampai DOM ready
                wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
                
                System.out.println("✓ Page loaded successfully!");
                return;
                
            } catch (org.openqa.selenium.TimeoutException e) {
                System.out.println("⚠️ Timeout on attempt " + (i + 1));
                
                // Cek apakah DOM sudah ready meskipun timeout
                try {
                    String readyState = js.executeScript("return document.readyState").toString();
                    if (readyState.equals("complete") || readyState.equals("interactive")) {
                        System.out.println("✓ DOM is ready, proceeding despite timeout");
                        return;
                    }
                } catch (Exception ex) {
                    // Continue to retry
                }
                
                if (i == maxRetries - 1) {
                    System.out.println("⚠️ Max retries reached, proceeding anyway...");
                }
            }
        }
    }
}
