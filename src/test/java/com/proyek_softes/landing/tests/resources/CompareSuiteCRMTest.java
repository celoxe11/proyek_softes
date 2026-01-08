package com.proyek_softes.landing.tests.resources;

import static org.testng.Assert.assertTrue;

import java.util.Map;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.resources.CompareSuiteCRMPage;
import com.proyek_softes.landing.main.utils.CompareSuiteCRMDataProvider;

import io.qameta.allure.Description;


public class CompareSuiteCRMTest extends BaseLandingTest{

    private static final String EXPECTED_PRIVACY_POLICY_URL = "https://suitecrm.com/privacy-policy/";
    private static final String EXPECTED_DEMO_URL = "https://suitecrm.com/demo/";
    private static final String EXPECTED_SUCCESS_MESSAGE = "You will receive an email shortly with the whitepaper";

    @Test(priority = 1, dataProvider = "whitepaperFormData", dataProviderClass = CompareSuiteCRMDataProvider.class)
    @Description("RES-011")
    public void testRes011(Map<String, String> data) {
        navigateToHome();

        CompareSuiteCRMPage comparePage = new CompareSuiteCRMPage(driver);

        boolean hoverSuccess = comparePage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = comparePage.navigateToCompareSalesforce();
        assertTrue(navSuccess, "Harus berhasil navigate ke Compare with Salesforce");

        comparePage.waitForPageLoad();
        takeScreenshot("RES-011_CompareSalesforce_Page");

        boolean clickWhitepaperSuccess = comparePage.clickSalesforceWhitepaperButton();
        assertTrue(clickWhitepaperSuccess, "Harus berhasil klik button SuiteCRM vs Salesforce - Whitepaper");

        comparePage.waitForPageLoad();
        waitSeconds(2);
        takeScreenshot("RES-011_WhitepaperForm_Page");

        // Fill form - get data from HashMap
        String fullName = data.get("full_name");
        String email = data.get("email");
        String companyName = data.get("company_name");
        String country = data.get("country");

        boolean fullNameSuccess = comparePage.enterFullName(fullName);
        assertTrue(fullNameSuccess, "Harus berhasil isi full name");

        boolean emailSuccess = comparePage.enterEmail(email);
        assertTrue(emailSuccess, "Harus berhasil isi email");

        boolean companySuccess = comparePage.enterCompanyName(companyName);
        assertTrue(companySuccess, "Harus berhasil isi company name");

        boolean countrySuccess = comparePage.selectCountry(country);
        assertTrue(countrySuccess, "Harus berhasil select country");

        boolean privacySuccess = comparePage.checkPrivacyPolicy();
        assertTrue(privacySuccess, "Harus berhasil centang Privacy Policy");

        boolean marketingSuccess = comparePage.checkMarketingInfo();
        assertTrue(marketingSuccess, "Harus berhasil centang marketing information");

        // Note: CAPTCHA manual step - user must complete image selection
        System.out.println("*** MANUAL STEP REQUIRED: Please complete the CAPTCHA image selection ***");
        waitSeconds(30); // Give time for manual CAPTCHA completion

        boolean captchaSuccess = comparePage.checkCaptcha();
        assertTrue(captchaSuccess, "Harus berhasil centang CAPTCHA checkbox");

        takeScreenshot("RES-011_FormFilled");

        boolean submitSuccess = comparePage.clickSubmitButton();
        assertTrue(submitSuccess, "Harus berhasil klik Submit button");

        comparePage.waitForPageLoad();
        waitSeconds(2);

        boolean messageVerified = comparePage.verifySuccessMessage(EXPECTED_SUCCESS_MESSAGE);
        assertTrue(messageVerified, "Harus muncul success message: " + EXPECTED_SUCCESS_MESSAGE);

        takeScreenshot("RES-011_SuccessMessage");
    }

    @Test(priority = 2)
    @Description("RES-012")
    public void testRes012() {
        navigateToHome();

        CompareSuiteCRMPage comparePage = new CompareSuiteCRMPage(driver);

        boolean hoverSuccess = comparePage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = comparePage.navigateToCompareSalesforce();
        assertTrue(navSuccess, "Harus berhasil navigate ke Compare with Salesforce");

        comparePage.waitForPageLoad();
        takeScreenshot("RES-012_CompareSalesforce_Page");

        boolean clickWhitepaperSuccess = comparePage.clickSalesforceWhitepaperButton();
        assertTrue(clickWhitepaperSuccess, "Harus berhasil klik button SuiteCRM vs Salesforce - Whitepaper");

        comparePage.waitForPageLoad();
        waitSeconds(2);
        takeScreenshot("RES-012_WhitepaperForm_Page");

        boolean clickPrivacySuccess = comparePage.clickPrivacyPolicyLink();
        assertTrue(clickPrivacySuccess, "Harus berhasil klik link Privacy Policy");

        comparePage.waitForPageLoad();
        waitSeconds(2);

        comparePage.switchToNewTab();
        comparePage.waitForPageLoad();

        String currentUrl = comparePage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_PRIVACY_POLICY_URL);
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_PRIVACY_POLICY_URL + " tapi actual: " + currentUrl);

        takeScreenshot("RES-012_PrivacyPolicy_Page");
    }

    @Test(priority = 3)
    @Description("RES-013")
    public void testRes013() {
        navigateToHome();

        CompareSuiteCRMPage comparePage = new CompareSuiteCRMPage(driver);

        boolean hoverSuccess = comparePage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = comparePage.navigateToCompareSalesforce();
        assertTrue(navSuccess, "Harus berhasil navigate ke Compare with Salesforce");

        comparePage.waitForPageLoad();
        takeScreenshot("RES-013_CompareSalesforce_Page");

        boolean clickDemoSuccess = comparePage.clickSalesforceDemoButton();
        assertTrue(clickDemoSuccess, "Harus berhasil klik button DEMO");

        comparePage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = comparePage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_DEMO_URL);
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_DEMO_URL + " tapi actual: " + currentUrl);

        takeScreenshot("RES-013_Demo_Page");
    }

    @Test(priority = 4)
    @Description("RES-014")
    public void testRes014() {
        navigateToHome();

        CompareSuiteCRMPage comparePage = new CompareSuiteCRMPage(driver);

        boolean hoverSuccess = comparePage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = comparePage.navigateToCompareMicrosoftDynamics();
        assertTrue(navSuccess, "Harus berhasil navigate ke Compare with Microsoft Dynamics");

        comparePage.waitForPageLoad();
        takeScreenshot("RES-014_CompareMicrosoftDynamics_Page");

        boolean clickDemoSuccess = comparePage.clickMicrosoftDynamicsDemoButton();
        assertTrue(clickDemoSuccess, "Harus berhasil klik button DEMO");

        comparePage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = comparePage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_DEMO_URL);
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_DEMO_URL + " tapi actual: " + currentUrl);

        takeScreenshot("RES-014_Demo_Page");
    }
}
