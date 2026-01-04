package com.proyek_softes.landing.tests.services;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.services.ConsultancyPage;

import io.qameta.allure.Description;

/**
 * Test class untuk halaman Consultancy and Implementation
 * Berisi test case SRV-005+
 */
public class ConsultancyTest extends BaseLandingTest {

    private static final String EXPECTED_CONTACT_URL = "https://suitecrm.com/about/about-us/contact/";

    @Test(priority = 1)
    @Description("SRV-005")
    public void testSrv005() {
        navigateToHome();

        ConsultancyPage consultancyPage = new ConsultancyPage(driver);

        boolean hoverSuccess = consultancyPage.hoverServicesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Services");

        boolean navSuccess = consultancyPage.navigateToConsultancy();
        assertTrue(navSuccess, "Harus berhasil navigate ke Consultancy and Implementation");

        consultancyPage.waitForPageLoad();
        takeScreenshot("SRV-005_Consultancy_Page");

        // Tekan button Get Started
        boolean clickSuccess = consultancyPage.clickGetStartedButton();
        assertTrue(clickSuccess, "Harus berhasil klik button Get Started");

        consultancyPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = consultancyPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_CONTACT_URL)
                || currentUrl.contains("about-us/contact");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_CONTACT_URL + " tapi actual: " + currentUrl);

        takeScreenshot("SRV-005_Contact_Page");
    }
}
