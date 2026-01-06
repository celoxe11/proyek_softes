package com.proyek_softes.landing.tests.resources;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.resources.DocumentationPage;

import io.qameta.allure.Description;


public class DocumentationTest extends BaseLandingTest {

    private static final String EXPECTED_DOCUMENTATION_URL = "https://docs.suitecrm.com/";

    @Test(priority = 1)
    @Description("RES-006")
    public void testRes006() {
        navigateToHome();

        DocumentationPage documentationPage = new DocumentationPage(driver);

        boolean hoverSuccess = documentationPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = documentationPage.navigateToDocumentation();
        assertTrue(navSuccess, "Harus berhasil navigate ke Documentation");

        documentationPage.waitForPageLoad();
        waitSeconds(2);

        documentationPage.switchToNewTab();
        documentationPage.waitForPageLoad();

        String currentUrl = documentationPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_DOCUMENTATION_URL);
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_DOCUMENTATION_URL + " tapi actual: " + currentUrl);

        takeScreenshot("RES-006_Documentation_Page");
    }
}
