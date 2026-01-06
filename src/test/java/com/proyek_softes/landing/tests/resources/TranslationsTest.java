package com.proyek_softes.landing.tests.resources;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.resources.TranslationsPage;

import io.qameta.allure.Description;


public class TranslationsTest extends BaseLandingTest {

    private static final String EXPECTED_TRANSLATIONS_URL = "https://sourceforge.net/projects/suitecrmtranslations/files/8.7.x/";

    @Test(priority = 1)
    @Description("RES-015")
    public void testRes015() {
        navigateToHome();

        TranslationsPage translationsPage = new TranslationsPage(driver);

        boolean hoverSuccess = translationsPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = translationsPage.navigateToTranslations();
        assertTrue(navSuccess, "Harus berhasil navigate ke Translations");

        translationsPage.waitForPageLoad();
        waitSeconds(2);

        translationsPage.switchToNewTab();
        translationsPage.waitForPageLoad();

        String currentUrl = translationsPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_TRANSLATIONS_URL);
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_TRANSLATIONS_URL + " tapi actual: " + currentUrl);

        takeScreenshot("RES-015_Translations_Page");
    }
}
