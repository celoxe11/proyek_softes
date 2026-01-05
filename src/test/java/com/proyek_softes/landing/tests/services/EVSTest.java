package com.proyek_softes.landing.tests.services;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.services.EVSPage;

import io.qameta.allure.Description;

/**
 * Test class untuk halaman Enterprise Verification Service (EVS)
 * Berisi test case SRV-014+
 */
public class EVSTest extends BaseLandingTest {

    private static final String EXPECTED_CONTACT_URL = "https://suitecrm.com/about/about-us/contact/";

    @Test(priority = 1)
    @Description("SRV-014")
    public void testSrv014() {
        navigateToHome();

        EVSPage evsPage = new EVSPage(driver);

        boolean hoverSuccess = evsPage.hoverServicesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Services");

        boolean navSuccess = evsPage.navigateToEVS();
        assertTrue(navSuccess, "Harus berhasil navigate ke Enterprise Verification Service");

        evsPage.waitForPageLoad();
        takeScreenshot("SRV-014_EVS_Page");

        // Tekan button Contact Us
        boolean clickSuccess = evsPage.clickContactUsButton();
        assertTrue(clickSuccess, "Harus berhasil klik button Contact Us");

        evsPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = evsPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_CONTACT_URL)
                || currentUrl.contains("about-us/contact");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_CONTACT_URL + " tapi actual: " + currentUrl);

        takeScreenshot("SRV-014_Contact_Page");
    }
}
