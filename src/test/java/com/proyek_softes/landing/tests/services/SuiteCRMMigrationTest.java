package com.proyek_softes.landing.tests.services;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.services.SuiteCRMMigrationPage;

import io.qameta.allure.Description;

/**
 * Test class untuk halaman SuiteCRM Migration
 * Berisi test case SRV-013+
 */
public class SuiteCRMMigrationTest extends BaseLandingTest {

    private static final String EXPECTED_CONTACT_URL = "https://suitecrm.com/about/about-us/contact/";

    @Test(priority = 1)
    @Description("SRV-013")
    public void testSrv013() {
        navigateToHome();

        SuiteCRMMigrationPage suiteCRMMigrationPage = new SuiteCRMMigrationPage(driver);

        boolean hoverSuccess = suiteCRMMigrationPage.hoverServicesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Services");

        boolean navSuccess = suiteCRMMigrationPage.navigateToSuiteCRMMigration();
        assertTrue(navSuccess, "Harus berhasil navigate ke SuiteCRM Migration");

        suiteCRMMigrationPage.waitForPageLoad();
        takeScreenshot("SRV-013_SuiteCRM_Migration_Page");

        // Tekan button Contact Us for Migrations
        boolean clickSuccess = suiteCRMMigrationPage.clickContactUsForMigrationsButton();
        assertTrue(clickSuccess, "Harus berhasil klik button Contact Us for Migrations");

        suiteCRMMigrationPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = suiteCRMMigrationPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_CONTACT_URL)
                || currentUrl.contains("about-us/contact");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_CONTACT_URL + " tapi actual: " + currentUrl);

        takeScreenshot("SRV-013_Contact_Page");
    }
}
