package com.proyek_softes.landing.tests.resources;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.resources.TrainingPage;

import io.qameta.allure.Description;


public class TrainingTest extends BaseLandingTest{

    private static final String EXPECTED_DEMO_URL = "https://suitecrm.com/demo/";

    @Test(priority = 1)
    @Description("RES-016")
    public void testRes016() {
        navigateToHome();

        TrainingPage trainingPage = new TrainingPage(driver);

        boolean hoverSuccess = trainingPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = trainingPage.navigateToUserTraining();
        assertTrue(navSuccess, "Harus berhasil navigate ke User Training");

        trainingPage.waitForPageLoad();
        takeScreenshot("RES-016_UserTraining_Page");

        boolean playSuccess = clickYouTubeVideo();
        assertTrue(playSuccess, "Harus berhasil play YouTube video");

        waitSeconds(10);

        takeScreenshot("RES-016_YouTubeVideo_Playing");
    }

    @Test(priority = 2)
    @Description("RES-017")
    public void testRes017() {
        navigateToHome();

        TrainingPage trainingPage = new TrainingPage(driver);

        boolean hoverSuccess = trainingPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = trainingPage.navigateToUserTraining();
        assertTrue(navSuccess, "Harus berhasil navigate ke User Training");

        trainingPage.waitForPageLoad();
        takeScreenshot("RES-017_UserTraining_Page");

        boolean clickSuccess = trainingPage.clickTryForFreeButton();
        assertTrue(clickSuccess, "Harus berhasil klik button Try for Free");

        trainingPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = trainingPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_DEMO_URL);
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_DEMO_URL + " tapi actual: " + currentUrl);

        takeScreenshot("RES-017_Demo_Page");
    }

    @Test(priority = 3)
    @Description("RES-018")
    public void testRes018() {
        navigateToHome();

        TrainingPage trainingPage = new TrainingPage(driver);

        boolean hoverSuccess = trainingPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = trainingPage.navigateToUserTraining();
        assertTrue(navSuccess, "Harus berhasil navigate ke User Training");

        trainingPage.waitForPageLoad();
        takeScreenshot("RES-018_UserTraining_Page");

        boolean clickSuccess = trainingPage.clickContactUsPricingButton();
        assertTrue(clickSuccess, "Harus berhasil klik button Contact Us to Receive Pricing for Training");

        trainingPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = trainingPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith("https://suitecrm.com/about/about-us/contact/");
        assertTrue(urlCorrect,
                "URL harus https://suitecrm.com/about/about-us/contact/ tapi actual: " + currentUrl);

        takeScreenshot("RES-018_Contact_Page");
    }

    @Test(priority = 4)
    @Description("RES-019")
    public void testRes019() {
        navigateToHome();

        TrainingPage trainingPage = new TrainingPage(driver);

        boolean hoverSuccess = trainingPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = trainingPage.navigateToDeveloperTraining();
        assertTrue(navSuccess, "Harus berhasil navigate ke Developer Training");

        trainingPage.waitForPageLoad();
        takeScreenshot("RES-019_DeveloperTraining_Page");

        boolean clickMasterclassesSuccess = trainingPage.clickMasterclassesButton();
        assertTrue(clickMasterclassesSuccess, "Harus berhasil klik button Take Me to the Masterclasses Now");

        trainingPage.waitForPageLoad();
        waitSeconds(2);
        takeScreenshot("RES-019_Masterclasses_Page");

        boolean playSuccess = clickYouTubeVideo();
        assertTrue(playSuccess, "Harus berhasil play YouTube video");

        waitSeconds(10);

        takeScreenshot("RES-019_YouTubeVideo_Playing");
    }

    @Test(priority = 5)
    @Description("RES-020")
    public void testRes020() {
        navigateToHome();

        TrainingPage trainingPage = new TrainingPage(driver);

        boolean hoverSuccess = trainingPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = trainingPage.navigateToDeveloperTraining();
        assertTrue(navSuccess, "Harus berhasil navigate ke Developer Training");

        trainingPage.waitForPageLoad();
        takeScreenshot("RES-020_DeveloperTraining_Page");

        boolean clickMasterclassesSuccess = trainingPage.clickMasterclassesButton();
        assertTrue(clickMasterclassesSuccess, "Harus berhasil klik button Take Me to the Masterclasses Now");

        trainingPage.waitForPageLoad();
        waitSeconds(2);
        takeScreenshot("RES-020_Masterclasses_Page");

        // Select Series 2 (SuiteCRM Basics) package
        boolean selectPackageSuccess = trainingPage.selectSeries2Package();
        assertTrue(selectPackageSuccess, "Harus berhasil pilih Series 2 - SuiteCRM Basics");

        waitSeconds(1);

        boolean checkPrivacySuccess = trainingPage.checkPrivacyPolicyCheckbox();
        assertTrue(checkPrivacySuccess, "Harus berhasil centang Privacy Policy");

        waitSeconds(1);

        boolean clickPaySuccess = trainingPage.clickPayWithCardButton();
        assertTrue(clickPaySuccess, "Harus berhasil klik button Pay with Card");

        trainingPage.waitForPageLoad();
        waitSeconds(2);

        boolean series2Visible = trainingPage.verifySeries2TextVisible();
        assertTrue(series2Visible, "Teks 'Series 2 - SuiteCRM Developer Basics' harus terlihat");

        takeScreenshot("RES-020_Series2_Visible");
    }

    @Test(priority = 6)
    @Description("RES-021")
    public void testRes021() {
        navigateToHome();

        TrainingPage trainingPage = new TrainingPage(driver);

        boolean hoverSuccess = trainingPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = trainingPage.navigateToDeveloperTraining();
        assertTrue(navSuccess, "Harus berhasil navigate ke Developer Training");

        trainingPage.waitForPageLoad();
        takeScreenshot("RES-021_DeveloperTraining_Page");

        boolean clickMasterclassesSuccess = trainingPage.clickMasterclassesButton();
        assertTrue(clickMasterclassesSuccess, "Harus berhasil klik button Take Me to the Masterclasses Now");

        trainingPage.waitForPageLoad();
        waitSeconds(2);
        takeScreenshot("RES-021_Masterclasses_Page");

        boolean checkPrivacySuccess = trainingPage.checkPrivacyPolicyCheckbox();
        assertTrue(checkPrivacySuccess, "Harus berhasil centang Privacy Policy");

        waitSeconds(1);

        boolean clickPaySuccess = trainingPage.clickPayWithCardButton();
        assertTrue(clickPaySuccess, "Harus berhasil klik button Pay with Card");

        trainingPage.waitForPageLoad();
        waitSeconds(2);
        takeScreenshot("RES-021_PaymentPage");

        trainingPage.clickBackButton();
        trainingPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = trainingPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith("https://suitecrm.com/payment-failed/");
        assertTrue(urlCorrect,
                "URL harus https://suitecrm.com/payment-failed/ tapi actual: " + currentUrl);

        takeScreenshot("RES-021_PaymentFailed_Page");
    }

    @Test(priority = 7)
    @Description("RES-022")
    public void testRes022() {
        navigateToHome();

        TrainingPage trainingPage = new TrainingPage(driver);

        boolean hoverSuccess = trainingPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = trainingPage.navigateToUserTraining();
        assertTrue(navSuccess, "Harus berhasil navigate ke User Training");

        trainingPage.waitForPageLoad();
        takeScreenshot("RES-022_UserTraining_Page");

        boolean clickSuccess = trainingPage.clickContactUsPricingButton();
        assertTrue(clickSuccess, "Harus berhasil klik button Contact Us to Receive Pricing for Training");

        trainingPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = trainingPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith("https://suitecrm.com/about/about-us/contact/");
        assertTrue(urlCorrect,
                "URL harus https://suitecrm.com/about/about-us/contact/ tapi actual: " + currentUrl);

        takeScreenshot("RES-022_Contact_Page");
    }
}
