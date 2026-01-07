package com.proyek_softes.landing.tests.about;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.about.ContactUsPage;

import io.qameta.allure.Description;

/**
 * Test class untuk halaman Contact Us
 * Berisi test case ABT-010, ABT-011
 */
public class ContactUsTest extends BaseLandingTest {

    private static final String EXPECTED_DEMO_URL = "https://suitecrm.com/demo/";
    private static final String EXPECTED_SUPPORT_URL = "https://suitecrm.com/enterprise/support-services/";

    @Test(priority = 1)
    @Description("ABT-010")
    public void testAbt010() {
        navigateToHome();

        ContactUsPage contactUsPage = new ContactUsPage(driver);

        // Step 1: Arahkan ke menu About
        boolean hoverSuccess = contactUsPage.hoverAboutMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu About");

        // Step 2: Pilih sub-menu Contact Us
        boolean navSuccess = contactUsPage.navigateToContactUs();
        assertTrue(navSuccess, "Harus berhasil navigate ke Contact Us");

        contactUsPage.waitForPageLoad();
        waitSeconds(2);

        // Step 3: Klik link "Get your free 30-day trial..."
        boolean clickSuccess = contactUsPage.clickFreeTrialLink();
        assertTrue(clickSuccess, "Harus berhasil klik link Free Trial");

        contactUsPage.waitForPageLoad();
        waitSeconds(2);

        // Switch to new tab if opened
        contactUsPage.switchToNewTab();
        contactUsPage.waitForPageLoad();

        String currentUrl = contactUsPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        // Step 4: Assert halaman https://suitecrm.com/demo/ benar
        boolean urlCorrect = currentUrl.startsWith(EXPECTED_DEMO_URL)
                || currentUrl.contains("/demo/");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_DEMO_URL + " tapi actual: " + currentUrl);

        // Step 5: Screenshot hasil assert
        takeScreenshot("ABT-010_Demo_Page");
    }

    @Test(priority = 2)
    @Description("ABT-011")
    public void testAbt011() {
        navigateToHome();

        ContactUsPage contactUsPage = new ContactUsPage(driver);

        // Step 1: Arahkan ke menu About
        boolean hoverSuccess = contactUsPage.hoverAboutMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu About");

        // Step 2: Pilih sub-menu Contact Us
        boolean navSuccess = contactUsPage.navigateToContactUs();
        assertTrue(navSuccess, "Harus berhasil navigate ke Contact Us");

        contactUsPage.waitForPageLoad();
        waitSeconds(2);

        // Step 3: Klik link "tailored support packages"
        boolean clickSuccess = contactUsPage.clickTailoredSupportLink();
        assertTrue(clickSuccess, "Harus berhasil klik link Tailored Support");

        contactUsPage.waitForPageLoad();
        waitSeconds(2);

        // Switch to new tab if opened
        contactUsPage.switchToNewTab();
        contactUsPage.waitForPageLoad();

        String currentUrl = contactUsPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        // Step 4: Assert halaman https://suitecrm.com/enterprise/support-services/ benar
        boolean urlCorrect = currentUrl.startsWith(EXPECTED_SUPPORT_URL)
                || currentUrl.contains("support-services");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_SUPPORT_URL + " tapi actual: " + currentUrl);

        // Step 5: Screenshot hasil assert
        takeScreenshot("ABT-011_Support_Services_Page");
    }
}
