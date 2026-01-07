package com.proyek_softes.landing.tests.about;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.about.AboutUsPage;

import io.qameta.allure.Description;

/**
 * Test class untuk halaman About Us
 * Berisi test case ABT-001, ABT-002
 */
public class AboutUsTest extends BaseLandingTest {

    private static final String EXPECTED_OPEN_SOURCE_URL = "https://suitecrm.com/join-the-project/always-open-source/";
    private static final String EXPECTED_CONTACT_URL = "https://suitecrm.com/about/about-us/contact/";

    @Test(priority = 1)
    @Description("ABT-001")
    public void testAbt001() {
        navigateToHome();

        AboutUsPage aboutUsPage = new AboutUsPage(driver);

        boolean hoverSuccess = aboutUsPage.hoverAboutMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu About");

        boolean navSuccess = aboutUsPage.navigateToAboutUs();
        assertTrue(navSuccess, "Harus berhasil navigate ke About Us");

        aboutUsPage.waitForPageLoad();
        takeScreenshot("ABT-001_AboutUs_Page");

        // Klik link Always Open Source
        boolean clickSuccess = aboutUsPage.clickAlwaysOpenSourceLink();
        assertTrue(clickSuccess, "Harus berhasil klik link Always Open Source");

        aboutUsPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = aboutUsPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_OPEN_SOURCE_URL)
                || currentUrl.contains("always-open-source");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_OPEN_SOURCE_URL + " tapi actual: " + currentUrl);

        takeScreenshot("ABT-001_AlwaysOpenSource_Page");
    }

    @Test(priority = 2)
    @Description("ABT-002")
    public void testAbt002() {
        navigateToHome();

        AboutUsPage aboutUsPage = new AboutUsPage(driver);

        boolean hoverSuccess = aboutUsPage.hoverAboutMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu About");

        boolean navSuccess = aboutUsPage.navigateToAboutUs();
        assertTrue(navSuccess, "Harus berhasil navigate ke About Us");

        aboutUsPage.waitForPageLoad();
        takeScreenshot("ABT-002_AboutUs_Page");

        // Tekan button Contact Us
        boolean clickSuccess = aboutUsPage.clickContactUsButton();
        assertTrue(clickSuccess, "Harus berhasil klik button Contact Us");

        aboutUsPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = aboutUsPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_CONTACT_URL)
                || currentUrl.contains("about-us/contact");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_CONTACT_URL + " tapi actual: " + currentUrl);

        takeScreenshot("ABT-002_Contact_Page");
    }
}
