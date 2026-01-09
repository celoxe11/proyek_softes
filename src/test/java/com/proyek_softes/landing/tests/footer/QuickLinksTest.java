package com.proyek_softes.landing.tests.footer;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.components.Footer;
import com.proyek_softes.landing.main.utils.ScreenshotUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class QuickLinksTest extends BaseLandingTest {

    @Test(description = "FTR-001: Verifikasi navigasi Quick Links")
    public void testQuickLinks() {
        Footer footer = new Footer(driver);
        String tid = "FTR-001";

        footer.clickLink("What is SuiteCRM?", tid);
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("suitecrm"));
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step02_WhatIsSuiteCRM");
        footer.back();

        footer.clickLink("Demo", tid); 
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("demo"));
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step05_DemoPage");
        footer.back();

        footer.clickLink("Download", tid);
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("download"));
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step08_DownloadPage");
        footer.back();

        footer.clickLink("Masterclasses", tid);
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("masterclasses"));
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step11_MasterclassesPage");
        footer.back();

        footer.clickLink("Success Stories", tid);
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("success"));
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step14_SuccessStoriesPage");
        footer.back();
    }
}