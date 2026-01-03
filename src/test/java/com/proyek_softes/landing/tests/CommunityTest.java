package com.proyek_softes.landing.tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.community.CommunityPage;

import io.qameta.allure.Description;

public class CommunityTest extends BaseLandingTest {

    private static final String EXPECTED_COMMUNITY_SUPPORT_URL = "https://community.suitecrm.com/";
    private static final String EXPECTED_OPENCOLLECTIVE_URL = "https://opencollective.com/suitecrm";
    private static final String EXPECTED_ROUTE4ME_URL = "https://www.route4me.com/";
    private static final String EXPECTED_TECHESPERTO_URL = "https://www.techesperto.com/";
    private static final String EXPECTED_GITHUB_URL = "https://github.com/SuiteCRM";

    @Test(priority = 1)
    @Description("COM-001")
    public void testCom001() {
        CommunityPage communityPage = new CommunityPage(driver);

        // Navigate ke menu Community dan pilih sub-menu Community Support
        boolean hoverSuccess = communityPage.hoverCommunityMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Community");

        boolean navSuccess = communityPage.navigateToCommunitySupport();
        assertTrue(navSuccess, "Harus berhasil navigate ke Community Support");

        // Wait for page/tab to load (external link opens in new tab)
        communityPage.switchToNewTab();
        communityPage.waitForPageLoad();
        waitSeconds(2);

        // Assert halaman correct
        String currentUrl = communityPage.getCurrentUrl();
        System.out.println("📍 Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_COMMUNITY_SUPPORT_URL)
                || currentUrl.contains("community.suitecrm.com");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_COMMUNITY_SUPPORT_URL + " tapi actual: " + currentUrl);

        // Screenshot hasil assert
        takeScreenshot("COM-001_Community_Support_Page");
    }

    @Test(priority = 2)
    @Description("COM-002")
    public void testCom002() {
        navigateToHome();

        CommunityPage communityPage = new CommunityPage(driver);

        boolean hoverSuccess = communityPage.hoverCommunityMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Community");

        boolean navSuccess = communityPage.navigateToSponsorship();
        assertTrue(navSuccess, "Harus berhasil navigate ke Sponsorship");

        communityPage.waitForPageLoad();
        takeScreenshot("COM-002_Sponsorship_Page");

        boolean clickSuccess = communityPage.clickSponsorNowButton();
        assertTrue(clickSuccess, "Harus berhasil klik button Sponsor Now");

        // Wait for new tab to open (external link)
        communityPage.switchToNewTab();
        communityPage.waitForPageLoad();
        waitSeconds(2);

        // Assert halaman correct
        String currentUrl = communityPage.getCurrentUrl();
        System.out.println("📍 Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_OPENCOLLECTIVE_URL)
                || currentUrl.contains("opencollective.com/suitecrm");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_OPENCOLLECTIVE_URL + " tapi actual: " + currentUrl);

        // Screenshot hasil assert
        takeScreenshot("COM-002_OpenCollective_Page");
    }

    @Test(priority = 3)
    @Description("COM-003")
    public void testCom003() {
        navigateToHome();

        CommunityPage communityPage = new CommunityPage(driver);

        // Navigate ke menu Community dan pilih sub-menu Sponsorship
        boolean hoverSuccess = communityPage.hoverCommunityMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Community");

        boolean navSuccess = communityPage.navigateToSponsorship();
        assertTrue(navSuccess, "Harus berhasil navigate ke Sponsorship");

        communityPage.waitForPageLoad();
        takeScreenshot("COM-003_Sponsorship_Page");

        // Klik link "Open Collective"
        boolean clickSuccess = communityPage.clickOpenCollectiveLink();
        assertTrue(clickSuccess, "Harus berhasil klik link Open Collective");

        // Wait for new tab to open (external link)
        communityPage.switchToNewTab();
        communityPage.waitForPageLoad();
        waitSeconds(2);

        // Assert halaman correct
        String currentUrl = communityPage.getCurrentUrl();
        System.out.println("📍 Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_OPENCOLLECTIVE_URL)
                || currentUrl.contains("opencollective.com/suitecrm");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_OPENCOLLECTIVE_URL + " tapi actual: " + currentUrl);

        // Screenshot hasil assert
        takeScreenshot("COM-003_OpenCollective_Page");
    }

    @Test(priority = 4)
    @Description("COM-004")
    public void testCom004() {
        navigateToHome();

        CommunityPage communityPage = new CommunityPage(driver);

        // Navigate ke menu Community dan pilih sub-menu Sponsorship
        boolean hoverSuccess = communityPage.hoverCommunityMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Community");

        boolean navSuccess = communityPage.navigateToSponsorship();
        assertTrue(navSuccess, "Harus berhasil navigate ke Sponsorship");

        communityPage.waitForPageLoad();
        takeScreenshot("COM-004_Sponsorship_Page");

        // Klik link "Route4Me"
        boolean clickSuccess = communityPage.clickRoute4MeLink();
        assertTrue(clickSuccess, "Harus berhasil klik link Route4Me");

        // Wait for new tab to open (external link)
        communityPage.switchToNewTab();
        communityPage.waitForPageLoad();
        waitSeconds(2);

        // Assert halaman correct
        String currentUrl = communityPage.getCurrentUrl();
        System.out.println("📍 Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_ROUTE4ME_URL)
                || currentUrl.contains("route4me.com");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_ROUTE4ME_URL + " tapi actual: " + currentUrl);

        // Screenshot hasil assert
        takeScreenshot("COM-004_Route4Me_Page");
    }

    @Test(priority = 5)
    @Description("COM-005")
    public void testCom005() {
        navigateToHome();

        CommunityPage communityPage = new CommunityPage(driver);

        boolean hoverSuccess = communityPage.hoverCommunityMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Community");

        boolean navSuccess = communityPage.navigateToSponsorship();
        assertTrue(navSuccess, "Harus berhasil navigate ke Sponsorship");

        communityPage.waitForPageLoad();
        takeScreenshot("COM-005_Sponsorship_Page");

        boolean clickSuccess = communityPage.clickBecomeASponsorButton();
        assertTrue(clickSuccess, "Harus berhasil klik button Become a Sponsor Today");

        communityPage.switchToNewTab();
        communityPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = communityPage.getCurrentUrl();
        System.out.println("📍 Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_OPENCOLLECTIVE_URL)
                || currentUrl.contains("opencollective.com/suitecrm");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_OPENCOLLECTIVE_URL + " tapi actual: " + currentUrl);

        takeScreenshot("COM-005_OpenCollective_Page");
    }

    @Test(priority = 6)
    @Description("COM-006: Button Visit Techesperto berfungsi dengan baik")
    public void testCom006() {
        navigateToHome();

        CommunityPage communityPage = new CommunityPage(driver);

        boolean hoverSuccess = communityPage.hoverCommunityMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Community");

        boolean navSuccess = communityPage.navigateToPartners();
        assertTrue(navSuccess, "Harus berhasil navigate ke Partners");

        communityPage.waitForPageLoad();
        takeScreenshot("COM-006_Partners_Page");

        boolean clickSuccess = communityPage.clickVisitTechespertoButton();
        assertTrue(clickSuccess, "Harus berhasil klik button Visit Techesperto");

        communityPage.switchToNewTab();
        communityPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = communityPage.getCurrentUrl();
        System.out.println("📍 Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_TECHESPERTO_URL)
                || currentUrl.contains("techesperto.com");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_TECHESPERTO_URL + " tapi actual: " + currentUrl);

        takeScreenshot("COM-006_Techesperto_Page");
    }

    @Test(priority = 7)
    @Description("COM-007: Halaman GitHub dapat ditampilkan dengan baik")
    public void testCom007() {
        navigateToHome();

        CommunityPage communityPage = new CommunityPage(driver);

        boolean hoverSuccess = communityPage.hoverCommunityMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Community");

        boolean navSuccess = communityPage.navigateToGitHub();
        assertTrue(navSuccess, "Harus berhasil navigate ke GitHub");

        communityPage.switchToNewTab();
        communityPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = communityPage.getCurrentUrl();
        System.out.println("📍 Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_GITHUB_URL)
                || currentUrl.contains("github.com/SuiteCRM");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_GITHUB_URL + " tapi actual: " + currentUrl);

        takeScreenshot("COM-007_GitHub_Page");
    }
}
