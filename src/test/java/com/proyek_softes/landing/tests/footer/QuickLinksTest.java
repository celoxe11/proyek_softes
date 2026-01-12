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

        System.out.println("\n=== MENJALANKAN TEST CASE: " + tid + " ===");

        footer.clickLink("What is SuiteCRM?", tid);
        Assert.assertTrue(footer.getPageTitle().contains("suitecrm"), "Gagal di link What is SuiteCRM?");
        System.out.println("[SUCCESS] Assert Step 02: Validasi Title 'What is SuiteCRM?' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step02_Passed");
        footer.back();

        footer.clickLink("Demo", tid); 
        Assert.assertTrue(footer.getPageTitle().contains("demo"), "Gagal di link Demo");
        System.out.println("[SUCCESS] Assert Step 05: Validasi Title 'Demo' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step05_Passed");
        footer.back();

        footer.clickLink("Download", tid);
        Assert.assertTrue(footer.getPageTitle().contains("download"), "Gagal di link Download");
        System.out.println("[SUCCESS] Assert Step 08: Validasi Title 'Download' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step08_Passed");
        footer.back();

        footer.clickLink("Masterclasses", tid);
        Assert.assertTrue(footer.getPageTitle().contains("masterclasses"), "Gagal di link Masterclasses");
        System.out.println("[SUCCESS] Assert Step 11: Validasi Title 'Masterclasses' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step11_Passed");
        footer.back();

        footer.clickLink("Success Stories", tid);
        Assert.assertTrue(footer.getPageTitle().contains("success"), "Gagal di link Success Stories");
        System.out.println("[SUCCESS] Assert Step 14: Validasi Title 'Success Stories' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step14_Passed");
        footer.back();

        System.out.println("=== TEST CASE " + tid + " SELESAI: SEMUA ASSERT TRUE ===\n");
    }
}