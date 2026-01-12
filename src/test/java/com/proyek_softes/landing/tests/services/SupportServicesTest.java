package com.proyek_softes.landing.tests.services;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.services.SupportServicesPage;

import io.qameta.allure.Description;

/**
 * Test class untuk halaman Support Services
 * Berisi test case SRV-001 s/d SRV-004
 */
public class SupportServicesTest extends BaseLandingTest {

    private static final String EXPECTED_CONTACT_URL = "https://suitecrm.com/about/about-us/contact/";
    private static final String EXPECTED_EVS_URL = "https://suitecrm.com/enterprise-verification-service/";
    private static final String EXPECTED_SUITECRM_HOSTED_URL = "https://suitecrm.com/suitecrmhosted/";
    private static final String EXPECTED_TERMS_PDF_URL = "https://suitecrm.com/wp-content/uploads/2025/03/SuiteCRM_Support_Services_Terms_And_Conditions_2025.pdf";

    @Test(priority = 1)
    @Description("SRV-001")
    public void testSrv001() {
        navigateToHome();

        SupportServicesPage supportServicesPage = new SupportServicesPage(driver);

        boolean hoverSuccess = supportServicesPage.hoverServicesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Services");

        boolean navSuccess = supportServicesPage.navigateToSupportServices();
        assertTrue(navSuccess, "Harus berhasil navigate ke Support Services");

        supportServicesPage.waitForPageLoad();
        takeScreenshot("SRV-001_Support_Services_Page");

        // Tekan button Contact Us di support package Gold
        boolean clickSuccess = supportServicesPage.clickGoldContactUsButton();
        assertTrue(clickSuccess, "Harus berhasil klik button Contact Us pada Gold package");

        supportServicesPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = supportServicesPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_CONTACT_URL)
                || currentUrl.contains("about-us/contact");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_CONTACT_URL + " tapi actual: " + currentUrl);

        takeScreenshot("SRV-001_Contact_Page");

        // Cleanup: tutup semua tab ekstra dan kembali ke main tab
        while (driver.getWindowHandles().size() > 1) {
            driver.close();
            driver.switchTo().window(driver.getWindowHandles().iterator().next());
        }
    }

    @Test(priority = 2)
    @Description("SRV-002")
    public void testSrv002() {
        navigateToHome();

        SupportServicesPage supportServicesPage = new SupportServicesPage(driver);

        boolean hoverSuccess = supportServicesPage.hoverServicesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Services");

        boolean navSuccess = supportServicesPage.navigateToSupportServices();
        assertTrue(navSuccess, "Harus berhasil navigate ke Support Services");

        supportServicesPage.waitForPageLoad();
        takeScreenshot("SRV-002_Support_Services_Page");

        // Klik link Enterprise Verification Service
        boolean clickSuccess = supportServicesPage.clickEnterpriseVerificationServiceLink();
        assertTrue(clickSuccess, "Harus berhasil klik link Enterprise Verification Service");

        supportServicesPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = supportServicesPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_EVS_URL)
                || currentUrl.contains("enterprise-verification-service");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_EVS_URL + " tapi actual: " + currentUrl);

        takeScreenshot("SRV-002_EVS_Page");

        // Cleanup: tutup semua tab ekstra dan kembali ke main tab
        while (driver.getWindowHandles().size() > 1) {
            driver.close();
            driver.switchTo().window(driver.getWindowHandles().iterator().next());
        }
    }

    @Test(priority = 3)
    @Description("SRV-003")
    public void testSrv003() {
        navigateToHome();

        SupportServicesPage supportServicesPage = new SupportServicesPage(driver);

        boolean hoverSuccess = supportServicesPage.hoverServicesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Services");

        boolean navSuccess = supportServicesPage.navigateToSupportServices();
        assertTrue(navSuccess, "Harus berhasil navigate ke Support Services");

        supportServicesPage.waitForPageLoad();
        takeScreenshot("SRV-003_Support_Services_Page");

        // Klik link Fully Managed SuiteCRM Hosting Services
        boolean clickSuccess = supportServicesPage.clickFullyManagedHostingLink();
        assertTrue(clickSuccess, "Harus berhasil klik link Fully Managed SuiteCRM Hosting Services");

        supportServicesPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = supportServicesPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_SUITECRM_HOSTED_URL)
                || currentUrl.contains("suitecrmhosted");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_SUITECRM_HOSTED_URL + " tapi actual: " + currentUrl);

        takeScreenshot("SRV-003_SuiteCRM_Hosted_Page");

        // Cleanup: tutup semua tab ekstra dan kembali ke main tab
        while (driver.getWindowHandles().size() > 1) {
            driver.close();
            driver.switchTo().window(driver.getWindowHandles().iterator().next());
        }
    }

    @Test(priority = 4)
    @Description("SRV-004")
    public void testSrv004() {
        navigateToHome();

        SupportServicesPage supportServicesPage = new SupportServicesPage(driver);

        boolean hoverSuccess = supportServicesPage.hoverServicesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Services");

        boolean navSuccess = supportServicesPage.navigateToSupportServices();
        assertTrue(navSuccess, "Harus berhasil navigate ke Support Services");

        supportServicesPage.waitForPageLoad();
        takeScreenshot("SRV-004_Support_Services_Page");

        // Klik link "please click here" untuk Terms and Conditions
        boolean clickSuccess = supportServicesPage.clickTermsAndConditionsLink();
        assertTrue(clickSuccess, "Harus berhasil klik link please click here");

        // Link membuka tab baru (PDF)
        supportServicesPage.switchToNewTab();
        supportServicesPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = supportServicesPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_TERMS_PDF_URL)
                || currentUrl.contains("SuiteCRM_Support_Services_Terms_And_Conditions");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_TERMS_PDF_URL + " tapi actual: " + currentUrl);

        takeScreenshot("SRV-004_Terms_PDF_Page");

        // Cleanup: tutup semua tab ekstra dan kembali ke main tab
        while (driver.getWindowHandles().size() > 1) {
            driver.close();
            driver.switchTo().window(driver.getWindowHandles().iterator().next());
        }
    }
}
