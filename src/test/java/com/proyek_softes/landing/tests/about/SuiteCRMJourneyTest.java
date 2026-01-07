package com.proyek_softes.landing.tests.about;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.about.SuiteCRMJourneyPage;

import io.qameta.allure.Description;


public class SuiteCRMJourneyTest extends BaseLandingTest {

    private static final String EXPECTED_GITHUB_URL = "https://github.com/SuiteCRM/SuiteCRM";
    private static final String EXPECTED_COMMUNITY_URL = "https://community.suitecrm.com/";

    @Test(priority = 1)
    @Description("ABT-005")
    public void testAbt005() {
        navigateToHome();

        SuiteCRMJourneyPage journeyPage = new SuiteCRMJourneyPage(driver);

        boolean hoverSuccess = journeyPage.hoverAboutMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu About");

        boolean navSuccess = journeyPage.navigateToJourney();
        assertTrue(navSuccess, "Harus berhasil navigate ke SuiteCRM Journey");

        journeyPage.waitForPageLoad();
        takeScreenshot("ABT-005_Journey_Page");

        // Tekan button Support Us on GitHub
        boolean clickSuccess = journeyPage.clickGitHubButton();
        assertTrue(clickSuccess, "Harus berhasil klik button Support Us on GitHub");

        journeyPage.waitForPageLoad();
        waitSeconds(2);

        // Switch to new tab if opened
        journeyPage.switchToNewTab();
        journeyPage.waitForPageLoad();

        String currentUrl = journeyPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.contains("github.com") && currentUrl.contains("SuiteCRM");
        assertTrue(urlCorrect,
                "URL harus mengandung github.com/SuiteCRM tapi actual: " + currentUrl);

        takeScreenshot("ABT-005_GitHub_Page");
    }

    @Test(priority = 2)
    @Description("ABT-006")
    public void testAbt006() {
        navigateToHome();

        SuiteCRMJourneyPage journeyPage = new SuiteCRMJourneyPage(driver);

        boolean hoverSuccess = journeyPage.hoverAboutMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu About");

        boolean navSuccess = journeyPage.navigateToJourney();
        assertTrue(navSuccess, "Harus berhasil navigate ke SuiteCRM Journey");

        journeyPage.waitForPageLoad();
        takeScreenshot("ABT-006_Journey_Page");

        // Tekan button Join the Community
        boolean clickSuccess = journeyPage.clickJoinCommunityButton();
        assertTrue(clickSuccess, "Harus berhasil klik button Join the Community");

        journeyPage.waitForPageLoad();
        waitSeconds(2);

        // Switch to new tab if opened
        journeyPage.switchToNewTab();
        journeyPage.waitForPageLoad();

        String currentUrl = journeyPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_COMMUNITY_URL)
                || currentUrl.contains("community.suitecrm.com");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_COMMUNITY_URL + " tapi actual: " + currentUrl);

        takeScreenshot("ABT-006_Community_Page");
    }
}
