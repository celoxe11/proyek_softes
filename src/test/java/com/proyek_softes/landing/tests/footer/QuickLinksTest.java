package com.proyek_softes.landing.tests.footer;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.components.Footer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class QuickLinksTest extends BaseLandingTest {

    @Test(description = "FTR-001: Quick Links Navigation")
    public void testQuickLinks() {
        Footer footer = new Footer(driver);

        // 1-3. What is SuiteCRM?
        footer.clickLink("What is SuiteCRM?");
        Assert.assertTrue(footer.getPageTitle().contains("SuiteCRM"), "Title tidak mengandung 'SuiteCRM'");
        footer.back();

        // 4-6. What is Demo
        footer.clickLink("Demo"); 
        Assert.assertTrue(footer.getPageTitle().contains("Demo"), "Title tidak mengandung 'Demo'");
        footer.back();

        // 7-9. Download
        footer.clickLink("Download");
        // PERBAIKAN: Gunakan kata kunci "Download" saja agar lebih aman
        String titleDownload = footer.getPageTitle();
        Assert.assertTrue(titleDownload.toLowerCase().contains("download"), 
            "Title salah! Harusnya mengandung 'Download', tapi ditemukan: " + titleDownload);
        footer.back();

        // 10-12. Masterclasses
        footer.clickLink("Masterclasses");
        Assert.assertTrue(footer.getPageTitle().contains("Masterclasses"), "Title tidak mengandung 'Masterclasses'");
        footer.back();

        // 13-15. Success Stories
        footer.clickLink("Success Stories");
        // Gunakan toLowerCase agar tidak masalah dengan huruf besar/kecil
        Assert.assertTrue(footer.getPageTitle().toLowerCase().contains("success"), "Title tidak mengandung 'Success'");
        footer.back();
    }
}