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

        System.out.println("\n=== MENJALANKAN TEST CASE: " + tid + " ===");

        footer.clickLink("Hosted", tid);
        Assert.assertTrue(footer.getPageTitle().contains("hosted"), "Gagal di link Hosted");
        System.out.println("[SUCCESS] Assert Step 02: Validasi Title 'Hosted' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step02_Passed");
        footer.back();

        footer.clickLink("SuiteASSURED", tid);
        Assert.assertTrue(footer.getPageTitle().contains("assured"), "Gagal di link SuiteASSURED");
        System.out.println("[SUCCESS] Assert Step 05: Validasi Title 'SuiteASSURED' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step05_Passed");
        footer.back();

        footer.clickLink("Support Services", tid);
        Assert.assertTrue(footer.getPageTitle().contains("support"), "Gagal di link Support Services");
        System.out.println("[SUCCESS] Assert Step 08: Validasi Title 'Support Services' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step08_Passed");
        footer.back();

        footer.clickLink("Training", tid);
        Assert.assertTrue(footer.getPageTitle().contains("training"), "Gagal di link Training");
        System.out.println("[SUCCESS] Assert Step 11: Validasi Title 'Training' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step11_Passed");
        footer.back();

        footer.clickLink("Consultancy", tid);
        Assert.assertTrue(footer.getPageTitle().contains("consultancy"), "Gagal di link Consultancy");
        System.out.println("[SUCCESS] Assert Step 14: Validasi Title 'Consultancy' BERHASIL (True)");
        ScreenshotUtils.takeAssertionScreenshot(driver, tid + "_Step14_Passed");
        footer.back();

        System.out.println("=== TEST CASE " + tid + " SELESAI: SEMUA ASSERT TRUE ===\n");
    }
}