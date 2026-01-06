package com.proyek_softes.landing.tests.resources;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.resources.AllSuiteCRMReleasesPage;

import io.qameta.allure.Description;

/**
 * Test class untuk halaman All SuiteCRM Releases
 * Berisi test case RES-001 dan RES-002
 */
public class AllSuiteCRMReleasesTest extends BaseLandingTest {

    private static final String EXPECTED_OPENCOLLECTIVE_URL = "https://opencollective.com/suitecrm";
    private static final String EXPECTED_FILE_NAME = "SuiteCRM-7.15.0";
    private static final String EXPECTED_RELEASE_NOTES_URL = "https://docs.suitecrm.com/admin/releases/7.15.x/";
    private static final String EXPECTED_INSTALL_GUIDE_URL = "https://docs.suitecrm.com/admin/installation-guide/downloading-installing/";
    private static final String EXPECTED_UPGRADE_GUIDE_URL = "https://docs.suitecrm.com/admin/installation-guide/upgrading/";

    @Test(priority = 1)
    @Description("RES-001")
    public void testRes001() {
        navigateToHome();

        AllSuiteCRMReleasesPage releasesPage = new AllSuiteCRMReleasesPage(driver);

        boolean hoverSuccess = releasesPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = releasesPage.navigateToAllSuiteCRMReleases();
        assertTrue(navSuccess, "Harus berhasil navigate ke All SuiteCRM Releases");

        releasesPage.waitForPageLoad();
        takeScreenshot("RES-001_AllSuiteCRMReleases_Page");

        boolean clickSuccess = releasesPage.clickOpenCollectiveBanner();
        assertTrue(clickSuccess, "Harus berhasil klik banner Open Collective");

        releasesPage.waitForPageLoad();
        waitSeconds(2);

        releasesPage.switchToNewTab();
        releasesPage.waitForPageLoad();

        String currentUrl = releasesPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_OPENCOLLECTIVE_URL);
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_OPENCOLLECTIVE_URL + " tapi actual: " + currentUrl);

        takeScreenshot("RES-001_OpenCollective_Page");
    }

    @Test(priority = 2)
    @Description("RES-002")
    public void testRes002() {
        navigateToHome();

        AllSuiteCRMReleasesPage releasesPage = new AllSuiteCRMReleasesPage(driver);

        boolean hoverSuccess = releasesPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = releasesPage.navigateToAllSuiteCRMReleases();
        assertTrue(navSuccess, "Harus berhasil navigate ke All SuiteCRM Releases");

        releasesPage.waitForPageLoad();
        takeScreenshot("RES-002_AllSuiteCRMReleases_Page");

        boolean clickSuccess = releasesPage.clickZip715ESR();
        assertTrue(clickSuccess, "Harus berhasil klik link zip untuk 7.15 ESR");

        waitSeconds(3);

        // Assert file name "SuiteCRM-7.15.0" dalam bentuk zip
        boolean fileDownloaded = releasesPage.verifyFileDownloaded(EXPECTED_FILE_NAME);
        assertTrue(fileDownloaded,
                "File " + EXPECTED_FILE_NAME + ".zip harus berhasil didownload");

        takeScreenshot("RES-002_File_Downloaded");
    }

    @Test(priority = 3)
    @Description("RES-003")
    public void testRes003() {
        navigateToHome();

        AllSuiteCRMReleasesPage releasesPage = new AllSuiteCRMReleasesPage(driver);

        boolean hoverSuccess = releasesPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = releasesPage.navigateToAllSuiteCRMReleases();
        assertTrue(navSuccess, "Harus berhasil navigate ke All SuiteCRM Releases");

        releasesPage.waitForPageLoad();
        takeScreenshot("RES-003_AllSuiteCRMReleases_Page");

        boolean clickSuccess = releasesPage.clickReleaseNotes715ESR();
        assertTrue(clickSuccess, "Harus berhasil klik link Release notes untuk 7.15 ESR");

        releasesPage.waitForPageLoad();
        waitSeconds(2);

        releasesPage.switchToNewTab();
        releasesPage.waitForPageLoad();

        String currentUrl = releasesPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_RELEASE_NOTES_URL);
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_RELEASE_NOTES_URL + " tapi actual: " + currentUrl);

        takeScreenshot("RES-003_ReleaseNotes_Page");
    }

    @Test(priority = 4)
    @Description("RES-004")
    public void testRes004() {
        navigateToHome();

        AllSuiteCRMReleasesPage releasesPage = new AllSuiteCRMReleasesPage(driver);

        boolean hoverSuccess = releasesPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = releasesPage.navigateToAllSuiteCRMReleases();
        assertTrue(navSuccess, "Harus berhasil navigate ke All SuiteCRM Releases");

        releasesPage.waitForPageLoad();
        takeScreenshot("RES-004_AllSuiteCRMReleases_Page");

        boolean clickSuccess = releasesPage.clickInstallGuide715ESR();
        assertTrue(clickSuccess, "Harus berhasil klik link Install Guide untuk 7.15 ESR");

        releasesPage.waitForPageLoad();
        waitSeconds(2);

        releasesPage.switchToNewTab();
        releasesPage.waitForPageLoad();

        String currentUrl = releasesPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_INSTALL_GUIDE_URL);
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_INSTALL_GUIDE_URL + " tapi actual: " + currentUrl);

        takeScreenshot("RES-004_InstallGuide_Page");
    }

    @Test(priority = 5)
    @Description("RES-005")
    public void testRes005() {
        navigateToHome();

        AllSuiteCRMReleasesPage releasesPage = new AllSuiteCRMReleasesPage(driver);

        boolean hoverSuccess = releasesPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = releasesPage.navigateToAllSuiteCRMReleases();
        assertTrue(navSuccess, "Harus berhasil navigate ke All SuiteCRM Releases");

        releasesPage.waitForPageLoad();
        takeScreenshot("RES-005_AllSuiteCRMReleases_Page");

        boolean clickSuccess = releasesPage.clickUpgradeGuide715Upgrade();
        assertTrue(clickSuccess, "Harus berhasil klik link Upgrade Guide untuk 7.15.0 Upgrade from 7.14.x");

        releasesPage.waitForPageLoad();
        waitSeconds(2);

        releasesPage.switchToNewTab();
        releasesPage.waitForPageLoad();

        String currentUrl = releasesPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_UPGRADE_GUIDE_URL);
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_UPGRADE_GUIDE_URL + " tapi actual: " + currentUrl);

        takeScreenshot("RES-005_UpgradeGuide_Page");
    }
}
