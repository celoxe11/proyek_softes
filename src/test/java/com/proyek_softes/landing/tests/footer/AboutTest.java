package com.proyek_softes.landing.tests.footer;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.components.Footer;
import com.proyek_softes.landing.main.utils.ScreenshotUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AboutTest extends BaseLandingTest {

    @Test(description = "FTR-003: Verifikasi navigasi kategori About")
    public void testAboutLinks() {
        Footer footer = new Footer(driver);
        String tid = "FTR-003";

        footer.clickLink("Contact Us", tid);
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("contact"));
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step02_ContactUsPage");
        footer.back();

        footer.clickLink("Roadmap", tid);
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("roadmap"));
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step05_RoadmapPage");
        footer.back();

        footer.clickLink("Always Open Source", tid);
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("open source"));
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step08_OpenSourcePage");
        footer.back();

        footer.clickLink("News & Press", tid);
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("news"));
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step11_NewsPressPage");
        footer.back();

        footer.clickLink("Careers", tid);
        String title14 = footer.getPageTitle().toLowerCase();
        // Sesuai temuan error: mengandung 'jobs' atau 'career'
        Assert.assertTrue(title14.contains("jobs") || title14.contains("career"), "Ditemukan: " + title14);
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step14_CareersPage");
        footer.back();

        footer.clickLink("SuiteCRM Ltd", tid);
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("suitecrm"));
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step17_LtdPage");
        footer.back();
    }
}