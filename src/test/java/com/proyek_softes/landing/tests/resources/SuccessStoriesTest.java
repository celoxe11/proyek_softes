package com.proyek_softes.landing.tests.resources;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.resources.SuccessStoriesPage;

import io.qameta.allure.Description;

/**
 * Test class untuk halaman Success Stories
 * Berisi test case RES-009 dan RES-010
 */
public class SuccessStoriesTest extends BaseLandingTest {

    private static final String EXPECTED_FEATURED_PDF_URL = "https://suitecrm.com/wp-content/uploads/2025/11/freedom-fibre-casestudy-20251030.pdf";
    private static final String EXPECTED_FOURTH_PDF_URL = "https://suitecrm.com/wp-content/uploads/2025/05/sbt_casestudy.pdf";

    @Test(priority = 1)
    @Description("RES-009")
    public void testRes009() {
        navigateToHome();

        SuccessStoriesPage successStoriesPage = new SuccessStoriesPage(driver);

        boolean hoverSuccess = successStoriesPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = successStoriesPage.navigateToSuccessStories();
        assertTrue(navSuccess, "Harus berhasil navigate ke Success Stories");

        successStoriesPage.waitForPageLoad();
        takeScreenshot("RES-009_SuccessStories_Page");

        boolean clickSuccess = successStoriesPage.clickFeaturedDownloadButton();
        assertTrue(clickSuccess, "Harus berhasil klik Download button untuk featured item");

        successStoriesPage.waitForPageLoad();
        waitSeconds(2);

        successStoriesPage.switchToNewTab();
        successStoriesPage.waitForPageLoad();

        String currentUrl = successStoriesPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_FEATURED_PDF_URL);
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_FEATURED_PDF_URL + " tapi actual: " + currentUrl);

        takeScreenshot("RES-009_FeaturedPDF_Page");
    }

    @Test(priority = 2)
    @Description("RES-010")
    public void testRes010() {
        navigateToHome();

        SuccessStoriesPage successStoriesPage = new SuccessStoriesPage(driver);

        boolean hoverSuccess = successStoriesPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = successStoriesPage.navigateToSuccessStories();
        assertTrue(navSuccess, "Harus berhasil navigate ke Success Stories");

        successStoriesPage.waitForPageLoad();
        takeScreenshot("RES-010_SuccessStories_Page");

        boolean clickSuccess = successStoriesPage.clickFourthItemDownloadButton();
        assertTrue(clickSuccess, "Harus berhasil klik Download button untuk item ke-4");

        successStoriesPage.waitForPageLoad();
        waitSeconds(2);

        successStoriesPage.switchToNewTab();
        successStoriesPage.waitForPageLoad();

        String currentUrl = successStoriesPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_FOURTH_PDF_URL);
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_FOURTH_PDF_URL + " tapi actual: " + currentUrl);

        takeScreenshot("RES-010_FourthItemPDF_Page");
    }
}
