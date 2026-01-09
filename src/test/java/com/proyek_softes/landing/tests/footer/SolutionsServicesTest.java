package com.proyek_softes.landing.tests.footer;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.components.Footer;
import com.proyek_softes.landing.main.utils.ScreenshotUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SolutionsServicesTest extends BaseLandingTest {

    @Test(description = "FTR-002: Verifikasi navigasi Solutions & Services")
    public void testSolutionsServices() {
        Footer footer = new Footer(driver);
        String tid = "FTR-002";

        footer.clickLink("Hosted", tid);
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("hosted"));
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step02_HostedPage");
        footer.back();

        footer.clickLink("SuiteASSURED", tid);
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("assured"));
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step05_SuiteASSUREDPage");
        footer.back();

        footer.clickLink("Support Services", tid);
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("support"));
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step08_SupportPage");
        footer.back();

        footer.clickLink("Training", tid);
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("training"));
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step11_TrainingPage");
        footer.back();

        footer.clickLink("Consultancy", tid);
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("consultancy"));
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step14_ConsultancyPage");
        footer.back();
    }
}