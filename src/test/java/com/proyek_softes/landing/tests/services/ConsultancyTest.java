package com.proyek_softes.landing.tests.services;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.services.ConsultancyPage;
import com.proyek_softes.landing.main.utils.ImplementationDataProvider;

import io.qameta.allure.Description;


public class ConsultancyTest extends BaseLandingTest {

    private static final String EXPECTED_CONTACT_URL = "https://suitecrm.com/about/about-us/contact/";

    @Test(priority = 1)
    @Description("SRV-005")
    public void testSrv005() {
        navigateToHome();

        ConsultancyPage consultancyPage = new ConsultancyPage(driver);

        boolean hoverSuccess = consultancyPage.hoverServicesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Services");

        boolean navSuccess = consultancyPage.navigateToConsultancy();
        assertTrue(navSuccess, "Harus berhasil navigate ke Consultancy and Implementation");

        consultancyPage.waitForPageLoad();
        takeScreenshot("SRV-005_Consultancy_Page");

        // Tekan button Get Started
        boolean clickSuccess = consultancyPage.clickGetStartedButton();
        assertTrue(clickSuccess, "Harus berhasil klik button Get Started");

        consultancyPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = consultancyPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_CONTACT_URL)
                || currentUrl.contains("about-us/contact");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_CONTACT_URL + " tapi actual: " + currentUrl);

        takeScreenshot("SRV-005_Contact_Page");

        // Cleanup: tutup semua tab ekstra dan kembali ke main tab
        while (driver.getWindowHandles().size() > 1) {
            driver.close();
            driver.switchTo().window(driver.getWindowHandles().iterator().next());
        }
    }

    @Test(priority = 2, dataProvider = "implementationData", dataProviderClass = ImplementationDataProvider.class)
    @Description("SRV-007")
    public void testSrv007(String fullName, String surname, String emailAddress, 
                          String companyName, String country,
                          boolean checkPrivacyPolicy, boolean checkMarketingComms,
                          String expectedSuccessMessage) {
        navigateToHome();

        ConsultancyPage consultancyPage = new ConsultancyPage(driver);

        boolean hoverSuccess = consultancyPage.hoverServicesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Services");

        boolean navSuccess = consultancyPage.navigateToConsultancy();
        assertTrue(navSuccess, "Harus berhasil navigate ke Consultancy and Implementation");

        consultancyPage.waitForPageLoad();
        waitSeconds(2);

        boolean clickSuccess = consultancyPage.clickCrmImplementationChecklistButton();
        assertTrue(clickSuccess, "Harus berhasil klik button CRM Implementation Checklist");

        consultancyPage.waitForPageLoad();
        waitSeconds(2);

        boolean formLoaded = consultancyPage.waitForImplementationFormToLoad();
        assertTrue(formLoaded, "Form Implementation Checklist harus berhasil dimuat");

        consultancyPage.fillField(
            consultancyPage.getFullNameLocator(), 
            fullName, 
            "Full Name"
        );
        consultancyPage.fillField(
            consultancyPage.getSurnameLocator(), 
            surname, 
            "Surname"
        );
        consultancyPage.fillField(
            consultancyPage.getEmailLocator(), 
            emailAddress, 
            "Email Address"
        );
        waitSeconds(1);

        consultancyPage.fillField(
            consultancyPage.getCompanyNameLocator(), 
            companyName, 
            "Company Name"
        );
        consultancyPage.selectCountry(country);
        waitSeconds(1);

        if (checkPrivacyPolicy) {
            consultancyPage.checkPrivacyPolicy();
            waitSeconds(1);
        }

        if (checkMarketingComms) {
            consultancyPage.checkMarketingCommunications();
            waitSeconds(1);
        }

        System.out.println("Waiting 120 seconds for manual CAPTCHA completion");
        
        // Wait 120 seconds for manual CAPTCHA solving with countdown
        for (int i = 120; i > 0; i--) {
            if (i % 10 == 0 || i <= 10) {
                System.out.println("Time remaining: " + i + " seconds");
            }
            waitSeconds(1);
        }
        
        System.out.println("CAPTCHA wait time completed");
        
        waitSeconds(3); // Wait for form submission to complete

        boolean hasSuccessMessage = consultancyPage.verifySuccessMessage(expectedSuccessMessage);
        assertTrue(hasSuccessMessage,
                "Success message harus muncul dengan teks: " + expectedSuccessMessage);

        // Step 10: Screenshot hasil assert
        takeScreenshot("SRV-007_Implementation_Form_Success");

        // Cleanup: tutup semua tab ekstra dan kembali ke main tab
        while (driver.getWindowHandles().size() > 1) {
            driver.close();
            driver.switchTo().window(driver.getWindowHandles().iterator().next());
        }
    }

    @Test(priority = 3, dataProvider = "implementationDataSrv008", dataProviderClass = ImplementationDataProvider.class)
    @Description("SRV-008")
    public void testSrv008(String fullName, String surname, String emailAddress,
                          String companyName, String country,
                          boolean checkPrivacyPolicy, boolean checkMarketingComms,
                          String expectedErrorMessage) {
        navigateToHome();

        ConsultancyPage consultancyPage = new ConsultancyPage(driver);

        boolean hoverSuccess = consultancyPage.hoverServicesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Services");

        boolean navSuccess = consultancyPage.navigateToConsultancy();
        assertTrue(navSuccess, "Harus berhasil navigate ke Consultancy and Implementation");

        consultancyPage.waitForPageLoad();
        waitSeconds(2);

        boolean clickSuccess = consultancyPage.clickCrmImplementationChecklistButton();
        assertTrue(clickSuccess, "Harus berhasil klik button CRM Implementation Checklist");

        consultancyPage.waitForPageLoad();
        waitSeconds(2);

        boolean formLoaded = consultancyPage.waitForImplementationFormToLoad();
        assertTrue(formLoaded, "Form Implementation Checklist harus berhasil dimuat");

        // Isi full name saja
        consultancyPage.fillField(
            consultancyPage.getFullNameLocator(), 
            fullName, 
            "Full Name"
        );
        consultancyPage.fillField(
            consultancyPage.getSurnameLocator(), 
            surname, 
            "Surname"
        );
        waitSeconds(1);

        // Submit form (field lain kosong, akan muncul validation error)
        consultancyPage.clickSubmit();
        waitSeconds(2);

        // Assert error message
        boolean hasErrorMessage = consultancyPage.verifyErrorMessage(expectedErrorMessage);
        assertTrue(hasErrorMessage,
                "Error message harus muncul dengan teks: " + expectedErrorMessage);

        takeScreenshot("SRV-008_Implementation_Form_Validation_Error");

        // Cleanup: tutup semua tab ekstra dan kembali ke main tab
        while (driver.getWindowHandles().size() > 1) {
            driver.close();
            driver.switchTo().window(driver.getWindowHandles().iterator().next());
        }
    }
}
