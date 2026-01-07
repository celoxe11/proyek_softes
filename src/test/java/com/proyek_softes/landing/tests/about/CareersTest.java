package com.proyek_softes.landing.tests.about;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.about.CareersPage;

import io.qameta.allure.Description;


public class CareersTest extends BaseLandingTest {

    @Test(priority = 1)
    @Description("ABT-007")
    public void testAbt007() {
        navigateToHome();

        CareersPage careersPage = new CareersPage(driver);

        boolean hoverSuccess = careersPage.hoverAboutMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu About");

        boolean navSuccess = careersPage.navigateToCareers();
        assertTrue(navSuccess, "Harus berhasil navigate ke Careers");

        careersPage.waitForPageLoad();
        waitSeconds(2);

        boolean clickSuccess = careersPage.clickFindOutMoreButton();
        assertTrue(clickSuccess, "Harus berhasil klik button Find Out More");

        careersPage.waitForPageLoad();
        waitSeconds(2);

        // Switch to new tab if opened
        careersPage.switchToNewTab();
        careersPage.waitForPageLoad();

        String currentUrl = careersPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.contains("indeed.com");
        assertTrue(urlCorrect,
                "URL harus mengandung indeed.com tapi actual: " + currentUrl);

        driver.navigate().back();
        careersPage.waitForPageLoad();
        waitSeconds(2);

        String backUrl = careersPage.getCurrentUrl();
        System.out.println("Back URL: " + backUrl);

        takeScreenshot("ABT-007_Careers_Back");
    }
}
