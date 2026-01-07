package com.proyek_softes.landing.tests.about;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.about.SuiteCRMRoadmapPage;

import io.qameta.allure.Description;


public class SuiteCRMRoadmapTest extends BaseLandingTest {

    private static final String EXPECTED_ROADMAP_URL = "https://suitecrm.com/suitecrm-roadmap/";

    @Test(priority = 1)
    @Description("ABT-004")
    public void testAbt004() {
        navigateToHome();

        SuiteCRMRoadmapPage roadmapPage = new SuiteCRMRoadmapPage(driver);

        boolean hoverSuccess = roadmapPage.hoverAboutMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu About");

        boolean navSuccess = roadmapPage.navigateToRoadmap();
        assertTrue(navSuccess, "Harus berhasil navigate ke SuiteCRM Roadmap");

        roadmapPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = roadmapPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_ROADMAP_URL)
                || currentUrl.contains("roadmap");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_ROADMAP_URL + " tapi actual: " + currentUrl);

        takeScreenshot("ABT-004_Roadmap_Page");
    }
}