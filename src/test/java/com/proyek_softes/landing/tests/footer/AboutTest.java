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

        System.out.println("\n=== MENJALANKAN TEST CASE: " + tid + " ===");

        footer.clickLink("Contact Us", tid);
        Assert.assertTrue(footer.getPageTitle().contains("contact"), "Gagal di link Contact Us");
        System.out.println("[SUCCESS] Assert Step 02: Validasi Title 'Contact Us' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step02_Passed");
        footer.back();

        footer.clickLink("Roadmap", tid);
        Assert.assertTrue(footer.getPageTitle().contains("roadmap"), "Gagal di link Roadmap");
        System.out.println("[SUCCESS] Assert Step 05: Validasi Title 'Roadmap' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step05_Passed");
        footer.back();

        footer.clickLink("Always Open Source", tid);
        Assert.assertTrue(footer.getPageTitle().contains("open source"), "Gagal di link Open Source");
        System.out.println("[SUCCESS] Assert Step 08: Validasi Title 'Open Source' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step08_Passed");
        footer.back();

        footer.clickLink("News & Press", tid);
        String newsTitle = footer.getPageTitle();
        Assert.assertTrue(newsTitle.contains("news") || newsTitle.contains("press"), "Gagal di News & Press");
        System.out.println("[SUCCESS] Assert Step 11: Validasi Title 'News & Press' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step11_Passed");
        footer.back();

        footer.clickLink("Careers", tid);
        String jobsTitle = footer.getPageTitle();
        Assert.assertTrue(jobsTitle.contains("jobs") || jobsTitle.contains("career"), "Gagal di Careers");
        System.out.println("[SUCCESS] Assert Step 14: Validasi Title 'Careers' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step14_Passed");
        footer.back();

        footer.clickLink("SuiteCRM Ltd", tid);
        Assert.assertTrue(footer.getPageTitle().contains("suitecrm"), "Gagal di link Ltd");
        System.out.println("[SUCCESS] Assert Step 17: Validasi Title 'Ltd' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step17_Passed");
        footer.back();

        System.out.println("=== TEST CASE " + tid + " SELESAI: SEMUA ASSERT TRUE ===\n");
    }
}