package com.proyek_softes.landing.tests.about;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.about.ContactUsPage;
import com.proyek_softes.landing.main.utils.ContactUsDataProvider;

import io.qameta.allure.Description;


public class ContactUsTest extends BaseLandingTest {

    private static final String EXPECTED_DEMO_URL = "https://suitecrm.com/demo/";
    private static final String EXPECTED_SUPPORT_URL = "https://suitecrm.com/enterprise/support-services/";

    @Test(priority = 1)
    @Description("ABT-010")
    public void testAbt010() {
        navigateToHome();

        ContactUsPage contactUsPage = new ContactUsPage(driver);

        // Step 1: Arahkan ke menu About
        boolean hoverSuccess = contactUsPage.hoverAboutMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu About");

        // Step 2: Pilih sub-menu Contact Us
        boolean navSuccess = contactUsPage.navigateToContactUs();
        assertTrue(navSuccess, "Harus berhasil navigate ke Contact Us");

        contactUsPage.waitForPageLoad();
        waitSeconds(2);

        // Step 3: Klik link "Get your free 30-day trial..."
        boolean clickSuccess = contactUsPage.clickFreeTrialLink();
        assertTrue(clickSuccess, "Harus berhasil klik link Free Trial");

        contactUsPage.waitForPageLoad();
        waitSeconds(2);

        // Switch to new tab if opened
        contactUsPage.switchToNewTab();
        contactUsPage.waitForPageLoad();

        String currentUrl = contactUsPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        // Step 4: Assert halaman https://suitecrm.com/demo/ benar
        boolean urlCorrect = currentUrl.startsWith(EXPECTED_DEMO_URL)
                || currentUrl.contains("/demo/");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_DEMO_URL + " tapi actual: " + currentUrl);

        // Step 5: Screenshot hasil assert
        takeScreenshot("ABT-010_Demo_Page");
    }

    @Test(priority = 2)
    @Description("ABT-011")
    public void testAbt011() {
        navigateToHome();

        ContactUsPage contactUsPage = new ContactUsPage(driver);

        // Step 1: Arahkan ke menu About
        boolean hoverSuccess = contactUsPage.hoverAboutMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu About");

        // Step 2: Pilih sub-menu Contact Us
        boolean navSuccess = contactUsPage.navigateToContactUs();
        assertTrue(navSuccess, "Harus berhasil navigate ke Contact Us");

        contactUsPage.waitForPageLoad();
        waitSeconds(2);

        // Step 3: Klik link "tailored support packages"
        boolean clickSuccess = contactUsPage.clickTailoredSupportLink();
        assertTrue(clickSuccess, "Harus berhasil klik link Tailored Support");

        contactUsPage.waitForPageLoad();
        waitSeconds(2);

        // Switch to new tab if opened
        contactUsPage.switchToNewTab();
        contactUsPage.waitForPageLoad();

        String currentUrl = contactUsPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        // Step 4: Assert halaman https://suitecrm.com/enterprise/support-services/ benar
        boolean urlCorrect = currentUrl.startsWith(EXPECTED_SUPPORT_URL)
                || currentUrl.contains("support-services");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_SUPPORT_URL + " tapi actual: " + currentUrl);

        // Step 5: Screenshot hasil assert
        takeScreenshot("ABT-011_Support_Services_Page");
    }

    @Test(priority = 3, dataProvider = "contactUsData", dataProviderClass = ContactUsDataProvider.class)
    @Description("ABT-012")
    public void testAbt012(String firstName, String lastName, String businessEmail, String phone,
                          String company, String jobTitle, String country, String message,
                          boolean checkPrivacyPolicy, boolean checkMarketingComms,
                          String expectedSuccessMessage) {
        navigateToHome();

        ContactUsPage contactUsPage = new ContactUsPage(driver);

        boolean hoverSuccess = contactUsPage.hoverAboutMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu About");

        boolean navSuccess = contactUsPage.navigateToContactUs();
        assertTrue(navSuccess, "Harus berhasil navigate ke Contact Us");

        contactUsPage.waitForPageLoad();
        waitSeconds(2);

        boolean formLoaded = contactUsPage.waitForFormToLoad();
        assertTrue(formLoaded, "Form Contact Us harus berhasil dimuat");

        contactUsPage.fillField(
            contactUsPage.getFirstNameLocator(), 
            firstName, 
            "First Name"
        );
        contactUsPage.fillField(
            contactUsPage.getLastNameLocator(), 
            lastName, 
            "Last Name"
        );
        waitSeconds(1);

        contactUsPage.fillField(
            contactUsPage.getEmailLocator(), 
            businessEmail, 
            "Business Email"
        );
        contactUsPage.fillField(
            contactUsPage.getCompanyLocator(), 
            company, 
            "Company"
        );
        waitSeconds(1);

        contactUsPage.selectCountry(country);
        contactUsPage.fillField(
            contactUsPage.getPhoneLocator(), 
            phone, 
            "Phone"
        );
        contactUsPage.fillField(
            contactUsPage.getJobTitleLocator(), 
            jobTitle, 
            "Job Title"
        );
        waitSeconds(1);

        contactUsPage.fillField(
            contactUsPage.getMessageLocator(), 
            message, 
            "Message"
        );
        waitSeconds(1);

        if (checkPrivacyPolicy) {
            contactUsPage.checkPrivacyPolicy();
            waitSeconds(1);
        }

        if (checkMarketingComms) {
            contactUsPage.checkMarketingCommunications();
            waitSeconds(1);
        }

        System.out.println("CAPTCHA needs to be solved manually during test execution");
        System.out.println("Please solve the CAPTCHA within 30 seconds");
        waitSeconds(30); // Give time for manual CAPTCHA solving

        contactUsPage.clickSubmit();
        waitSeconds(3);

        boolean hasSuccessMessage = contactUsPage.verifySuccessMessage(expectedSuccessMessage);
        assertTrue(hasSuccessMessage,
                "Success message harus muncul dengan teks: " + expectedSuccessMessage);

        // Step 12: Screenshot hasil assert
        takeScreenshot("ABT-012_Contact_Form_Success");
    }

    @Test(priority = 4, dataProvider = "contactUsDataAbt013", dataProviderClass = ContactUsDataProvider.class)
    @Description("ABT-013")
    public void testAbt013(String firstName, String lastName, String businessEmail, String phone,
                          String company, String jobTitle, String country, String message,
                          boolean checkPrivacyPolicy, boolean checkMarketingComms,
                          String expectedErrorMessage) {
        navigateToHome();

        ContactUsPage contactUsPage = new ContactUsPage(driver);

        boolean hoverSuccess = contactUsPage.hoverAboutMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu About");

        boolean navSuccess = contactUsPage.navigateToContactUs();
        assertTrue(navSuccess, "Harus berhasil navigate ke Contact Us");

        contactUsPage.waitForPageLoad();
        waitSeconds(2);

        boolean formLoaded = contactUsPage.waitForFormToLoad();
        assertTrue(formLoaded, "Form Contact Us harus berhasil dimuat");

        contactUsPage.fillField(contactUsPage.getFirstNameLocator(), firstName, "First Name");
        contactUsPage.fillField(contactUsPage.getLastNameLocator(), lastName, "Last Name");
        waitSeconds(1);

        contactUsPage.fillField(contactUsPage.getEmailLocator(), businessEmail, "Business Email");
        contactUsPage.fillField(contactUsPage.getCompanyLocator(), company, "Company");
        waitSeconds(1);

        contactUsPage.selectCountry(country);
        contactUsPage.fillField(contactUsPage.getPhoneLocator(), phone, "Phone");
        contactUsPage.fillField(contactUsPage.getJobTitleLocator(), jobTitle, "Job Title");
        waitSeconds(1);

        contactUsPage.fillField(contactUsPage.getMessageLocator(), message, "Message");
        waitSeconds(1);

        if (checkPrivacyPolicy) {
            contactUsPage.checkPrivacyPolicy();
        }

        if (checkMarketingComms) {
            contactUsPage.checkMarketingCommunications();
        }

        contactUsPage.clickSubmit();
        waitSeconds(1);
        
        // Verify error message
        boolean hasErrorMessage = contactUsPage.verifyRecaptchaErrorMessage(expectedErrorMessage);
        assertTrue(hasErrorMessage, "Error message harus muncul: " + expectedErrorMessage);
        
        // Screenshot error message
        takeScreenshot("ABT-013_Contact_Form_Recaptcha_Error");
    }
}
