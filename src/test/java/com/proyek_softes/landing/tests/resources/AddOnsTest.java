package com.proyek_softes.landing.tests.resources;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.resources.AddOnsPage;

import io.qameta.allure.Description;


public class AddOnsTest extends BaseLandingTest {

    private static final String EXPECTED_STORE_URL = "https://store.suitecrm.com/?tag=suitecrm";
    private static final String EXPECTED_OUTLOOK_PLUGIN_URL = "https://store.suitecrm.com/addons/SuiteCRM-official-outlook-plugin?tag=suitecrm";

    @Test(priority = 1)
    @Description("RES-007")
    public void testRes007() {
        navigateToHome();

        AddOnsPage addOnsPage = new AddOnsPage(driver);

        boolean hoverSuccess = addOnsPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = addOnsPage.navigateToAddOnsStore();
        assertTrue(navSuccess, "Harus berhasil navigate ke Add-ons > Store");

        addOnsPage.waitForPageLoad();
        waitSeconds(2);

        addOnsPage.switchToNewTab();
        addOnsPage.waitForPageLoad();

        String currentUrl = addOnsPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_STORE_URL);
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_STORE_URL + " tapi actual: " + currentUrl);

        takeScreenshot("RES-007_AddOns_Store_Page");
    }

    @Test(priority = 2)
    @Description("RES-008")
    public void testRes008() {
        navigateToHome();

        AddOnsPage addOnsPage = new AddOnsPage(driver);

        boolean hoverSuccess = addOnsPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = addOnsPage.navigateToAddOnsOutlookPlugin();
        assertTrue(navSuccess, "Harus berhasil navigate ke Add-ons > Outlook Plugin");

        addOnsPage.waitForPageLoad();
        waitSeconds(2);

        addOnsPage.switchToNewTab();
        addOnsPage.waitForPageLoad();

        String currentUrl = addOnsPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_OUTLOOK_PLUGIN_URL);
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_OUTLOOK_PLUGIN_URL + " tapi actual: " + currentUrl);

        takeScreenshot("RES-008_AddOns_OutlookPlugin_Page");
    }
}
