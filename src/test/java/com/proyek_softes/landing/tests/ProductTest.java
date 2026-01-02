package com.proyek_softes.landing.tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.products.ProductsPage;
import com.proyek_softes.landing.main.pages.products.SuiteCRMPage;

import io.qameta.allure.Description;

public class ProductTest extends BaseLandingTest {

    private static final String EXPECTED_CRM_URL = "https://suitecrm.com/what-is-crm/";

    @Test(priority = 1)
    @Description("PRD-001")
    public void testPrd001() {
        ProductsPage productsPage = new ProductsPage(driver);
        SuiteCRMPage suiteCRMPage = new SuiteCRMPage(driver);

        boolean hoverSuccess = productsPage.hoverProductsMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Products");

        boolean navSuccess = productsPage.navigateToSuiteCRM();
        assertTrue(navSuccess, "Harus berhasil navigate ke halaman SuiteCRM");
        suiteCRMPage.waitForPageLoad();
        takeScreenshot("PRD-001_SuiteCRM_Page");

        boolean clickSuccess = suiteCRMPage.clickCRMLink();
        assertTrue(clickSuccess, "Harus berhasil klik link CRM");
        suiteCRMPage.waitForPageLoad();

        String currentUrl = driver.getCurrentUrl();
        boolean urlCorrect = currentUrl.equals(EXPECTED_CRM_URL) || currentUrl.contains("what-is-crm");
        assertTrue(urlCorrect, "URL harus " + EXPECTED_CRM_URL + " tapi actual: " + currentUrl);

        takeScreenshot("PRD-001_Navigate_CRM");
    }

    @Test(priority = 2)
    @Description("PRD-002")
    public void testPrd002() {
        navigateToHome();

        ProductsPage productsPage = new ProductsPage(driver);
        SuiteCRMPage suiteCRMPage = new SuiteCRMPage(driver);

        boolean hoverSuccess = productsPage.hoverProductsMenu();
        assertTrue(hoverSuccess, "Products menu should be accessible");

        boolean navSuccess = productsPage.navigateToSuiteCRM();
        if (!navSuccess) {
            loadPage("https://suitecrm.com/what-is-suitecrm/");
        }
        suiteCRMPage.waitForPageLoad();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("suitecrm"), "Should be on SuiteCRM page");

        boolean videoPlayed = clickYouTubeVideo();
        assertTrue(videoPlayed, "YouTube video should be played");

        waitSeconds(10);

        takeScreenshot("PRD-002_Play_Youtube");
    }

    @Test(priority = 3)
    @Description("PRD-003")
    public void testPrd003() {
        navigateToHome();

        ProductsPage productsPage = new ProductsPage(driver);
        SuiteCRMPage suiteCRMPage = new SuiteCRMPage(driver);

        // Navigate to Products > SuiteCRM
        boolean hoverSuccess = productsPage.hoverProductsMenu();
        assertTrue(hoverSuccess, "Products menu should be accessible");

        boolean navSuccess = productsPage.navigateToSuiteCRM();
        if (!navSuccess) {
            loadPage("https://suitecrm.com/what-is-suitecrm/");
        }
        suiteCRMPage.waitForPageLoad();
        takeScreenshot("PRD-003_SuiteCRM_Page");

        // Click "Try For Free" button
        suiteCRMPage.clickTryForFreeButton();
        waitForPageLoad();
        String title1 = driver.getTitle();
        assertTrue(title1.contains("SuiteCRM Demo") || title1.contains("Demo"),
                "Title should contain 'SuiteCRM Demo', but was: " + title1);
        takeScreenshot("PRD-003_TryForFree");

        // Go back
        driver.navigate().back();
        waitForPageLoad();

        // Click "Book Demo" button
        suiteCRMPage.clickBookDemoButton();
        waitForPageLoad();
        String title2 = driver.getTitle();
        assertTrue(title2.contains("Contact") || title2.contains("contact"),
                "Title should contain 'Contact Us', but was: " + title2);
        takeScreenshot("PRD-003_BookDemo");

        // Go back
        driver.navigate().back();
        waitForPageLoad();

        // Click "Get in Touch" button
        suiteCRMPage.clickGetInTouchButton();
        waitForPageLoad();
        String title3 = driver.getTitle();
        assertTrue(title3.contains("Contact") || title3.contains("contact"),
                "Title should contain 'Contact Us', but was: " + title3);
        takeScreenshot("PRD-003_GetInTouch");

        // Go back
        driver.navigate().back();
        waitForPageLoad();
        takeScreenshot("PRD-003_Complete");
    }

    @Test(priority = 4)
    @Description("PRD-004")
    public void testPrd004() {
        navigateToHome();

        ProductsPage productsPage = new ProductsPage(driver);
        SuiteCRMPage suiteCRMPage = new SuiteCRMPage(driver);

        // Navigate to Products > SuiteCRM
        boolean hoverSuccess = productsPage.hoverProductsMenu();
        assertTrue(hoverSuccess, "Products menu should be accessible");

        boolean navSuccess = productsPage.navigateToSuiteCRM();
        if (!navSuccess) {
            loadPage("https://suitecrm.com/what-is-suitecrm/");
        }
        suiteCRMPage.waitForPageLoad();

        // Get active case study title
        String caseStudyTitle = suiteCRMPage.getActiveCaseStudyTitle();
        System.out.println("📍 Active Case Study Title: " + caseStudyTitle);
        assertTrue(caseStudyTitle != null && !caseStudyTitle.isEmpty(),
                "Case study title should not be empty");
        takeScreenshot("PRD-004_CaseStudy_Active");

        // Click Read Case Study button
        suiteCRMPage.clickReadCaseStudyButton();
        waitSeconds(3); // Wait for PDF to load

        // Assert URL contains case study PDF
        String currentUrl = driver.getCurrentUrl();
        System.out.println("📍 Current URL: " + currentUrl);
        boolean isPdfUrl = currentUrl.contains(".pdf") || currentUrl.contains("casestudy");
        assertTrue(isPdfUrl, "URL should be a PDF file, but was: " + currentUrl);

        // Screenshot PDF preview
        takeScreenshot("PRD-004_CaseStudy_PDF");
    }

    @Test(priority = 5)
    @Description("PRD-005")
    public void testPrd005() {
        navigateToHome();

        ProductsPage productsPage = new ProductsPage(driver);

        // Mengarah ke menu Products
        boolean hoverSuccess = productsPage.hoverProductsMenu();
        assertTrue(hoverSuccess, "Products menu should be accessible");

        // Mengarah ke sub-menu SuiteASSURED
        boolean navSuccess = productsPage.navigateToSuiteAssured();
        if (!navSuccess) {
            loadPage("https://suitecrm.com/enterprise/suiteassured/");
        }
        waitForPageLoad();
        takeScreenshot("PRD-005_SuiteASSURED_Page");

        // Tekan button Contact Us
        productsPage.clickContactUsButton();
        waitForPageLoad();

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "https://suitecrm.com/about/about-us/contact/";
        System.out.println("📍 Current URL: " + currentUrl);
        System.out.println("📍 Expected URL: " + expectedUrl);

        boolean urlCorrect = currentUrl.equals(expectedUrl) || currentUrl.contains("contact");
        assertTrue(urlCorrect, "URL should be " + expectedUrl + " but was: " + currentUrl);

        // Screenshot hasil assert
        takeScreenshot("PRD-005_ContactUs");
    }
}
