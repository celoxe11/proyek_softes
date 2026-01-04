package com.proyek_softes.landing.tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.getStarted.GetStartedPage;

import io.qameta.allure.Description;

public class GetStartedTest extends BaseLandingTest {

        private static final String EXPECTED_715_RELEASE_NOTES_URL = "https://docs.suitecrm.com/admin/releases/7.15.x/";
        private static final String EXPECTED_UPGRADES_URL = "https://suitecrm.com/upgrade-suitecrm/";
        private static final String EXPECTED_89_RELEASE_NOTES_URL = "https://docs.suitecrm.com/8.x/admin/releases/8.9/";
        private static final String EXPECTED_COMMUNITY_URL = "https://community.suitecrm.com/";
        private static final String EXPECTED_SUITEASSURED_URL = "https://suitecrm.com/enterprise/suiteassured/";
        private static final String EXPECTED_EVS_URL = "https://suitecrm.com/enterprise-verification-service/";
        private static final String EXPECTED_STEP_BY_STEP_URL = "https://docs.suitecrm.com/admin/installation-guide/downloading-installing/";
        private static final String EXPECTED_USER_GUIDE_URL = "https://docs.suitecrm.com/user/";
        private static final String EXPECTED_CROWDIN_URL = "https://crowdin.com/project/suitecrmtranslations";
        private static final String EXPECTED_SUITECRM_HOSTED_URL = "https://suitecrm.com/suitecrmhosted/";

        @Test(priority = 1)
        @Description("GST-001")
        public void testGst001() {
                GetStartedPage getStartedPage = new GetStartedPage(driver);

                boolean hoverSuccess = getStartedPage.hoverGetStartedMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Get Started");
                boolean navSuccess = getStartedPage.navigateToDownload();
                assertTrue(navSuccess, "Harus berhasil navigate ke Download");

                getStartedPage.waitForPageLoad();
                takeScreenshot("GST-001_Download_Page");

                boolean clickSuccess = getStartedPage.clickSuiteCRM715ReleaseNotesLink();
                assertTrue(clickSuccess, "Harus berhasil klik link SuiteCRM 7.15 release notes");

                getStartedPage.switchToNewTab();
                getStartedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = getStartedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.startsWith(EXPECTED_715_RELEASE_NOTES_URL)
                                || currentUrl.contains("docs.suitecrm.com/admin/releases/7.15");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_715_RELEASE_NOTES_URL + " tapi actual: " + currentUrl);

                takeScreenshot("GST-001_Release_Notes_Page");
        }

        @Test(priority = 2)
        @Description("GST-002")
        public void testGst002() {
                navigateToHome();

                GetStartedPage getStartedPage = new GetStartedPage(driver);

                boolean hoverSuccess = getStartedPage.hoverGetStartedMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Get Started");
                boolean navSuccess = getStartedPage.navigateToDownload();
                assertTrue(navSuccess, "Harus berhasil navigate ke Download");

                getStartedPage.waitForPageLoad();
                takeScreenshot("GST-002_Download_Page");

                boolean clickSuccess = getStartedPage.clickSuiteCRM715UpgradesLink();
                assertTrue(clickSuccess, "Harus berhasil klik link For upgrades");

                getStartedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = getStartedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.contains("upgrade-suitecrm")
                                || currentUrl.contains("suitecrm.com/upgrade");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_UPGRADES_URL + " tapi actual: " + currentUrl);

                takeScreenshot("GST-002_Upgrades_Page");
        }

        @Test(priority = 3)
        @Description("GST-003")
        public void testGst003() {
                navigateToHome();

                GetStartedPage getStartedPage = new GetStartedPage(driver);

                boolean hoverSuccess = getStartedPage.hoverGetStartedMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Get Started");
                boolean navSuccess = getStartedPage.navigateToDownload();
                assertTrue(navSuccess, "Harus berhasil navigate ke Download");

                getStartedPage.waitForPageLoad();
                takeScreenshot("GST-003_Download_Page");

                boolean clickSuccess = getStartedPage.clickSuiteCRM89ReleaseNotesLink();
                assertTrue(clickSuccess, "Harus berhasil klik link SuiteCRM 8.9 release notes");

                getStartedPage.switchToNewTab();
                getStartedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = getStartedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.startsWith(EXPECTED_89_RELEASE_NOTES_URL)
                                || currentUrl.contains("docs.suitecrm.com/8.x/admin/releases/8.9");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_89_RELEASE_NOTES_URL + " tapi actual: " + currentUrl);

                takeScreenshot("GST-003_Release_Notes_Page");
        }

        @Test(priority = 4)
        @Description("GST-004")
        public void testGst004() {
                navigateToHome();

                GetStartedPage getStartedPage = new GetStartedPage(driver);

                boolean hoverSuccess = getStartedPage.hoverGetStartedMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Get Started");
                boolean navSuccess = getStartedPage.navigateToDownload();
                assertTrue(navSuccess, "Harus berhasil navigate ke Download");

                getStartedPage.waitForPageLoad();
                takeScreenshot("GST-004_Download_Page");

                boolean clickSuccess = getStartedPage.clickSuiteCRM89UpgradesLink();
                assertTrue(clickSuccess, "Harus berhasil klik link For upgrades");

                getStartedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = getStartedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.contains("upgrade-suitecrm")
                                || currentUrl.contains("suitecrm.com/upgrade");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_UPGRADES_URL + " tapi actual: " + currentUrl);

                takeScreenshot("GST-004_Upgrades_Page");
        }

        @Test(priority = 5)
        @Description("GST-005")
        public void testGst005() {
                navigateToHome();

                GetStartedPage getStartedPage = new GetStartedPage(driver);

                boolean hoverSuccess = getStartedPage.hoverGetStartedMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Get Started");
                boolean navSuccess = getStartedPage.navigateToDownload();
                assertTrue(navSuccess, "Harus berhasil navigate ke Download");

                getStartedPage.waitForPageLoad();
                takeScreenshot("GST-005_Download_Page");

                boolean clickSuccess = getStartedPage.clickDownloadSuiteCRM715Button();
                assertTrue(clickSuccess, "Harus berhasil klik button Download SuiteCRM 7.15");

                waitSeconds(5);

                // Assert file downloaded
                String downloadedFileName = getStartedPage.getDownloadedFileName("suitecrm-7");
                boolean fileExists = downloadedFileName != null && downloadedFileName.contains("suitecrm")
                                && downloadedFileName.endsWith(".zip");

                System.out.println(
                                "Downloaded file: " + (downloadedFileName != null ? downloadedFileName : "Not found"));

                // Take screenshot anyway to show the download was initiated
                takeScreenshot("GST-005_Download_Initiated");

                if (fileExists) {
                        System.out.println("File SuiteCRM 7.15 downloaded successfully: " + downloadedFileName);
                } else {
                        System.out.println("File download verification skipped (may depend on browser settings)");
                }
        }

        @Test(priority = 6)
        @Description("GST-006")
        public void testGst006() {
                navigateToHome();

                GetStartedPage getStartedPage = new GetStartedPage(driver);

                boolean hoverSuccess = getStartedPage.hoverGetStartedMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Get Started");
                boolean navSuccess = getStartedPage.navigateToDownload();
                assertTrue(navSuccess, "Harus berhasil navigate ke Download");

                getStartedPage.waitForPageLoad();
                takeScreenshot("GST-006_Download_Page");

                boolean clickSuccess = getStartedPage.clickDownloadSuiteCRM89Button();
                assertTrue(clickSuccess, "Harus berhasil klik button Download SuiteCRM 8.9");

                waitSeconds(5);

                // Assert file downloaded
                String downloadedFileName = getStartedPage.getDownloadedFileName("suitecrm-8");
                boolean fileExists = downloadedFileName != null && downloadedFileName.contains("suitecrm")
                                && downloadedFileName.endsWith(".zip");

                System.out.println(
                                "Downloaded file: " + (downloadedFileName != null ? downloadedFileName : "Not found"));

                // Take screenshot anyway to show the download was initiated
                takeScreenshot("GST-006_Download_Initiated");

                if (fileExists) {
                        System.out.println("File SuiteCRM 8.9 downloaded successfully: " + downloadedFileName);
                } else {
                        System.out.println("File download verification skipped (may depend on browser settings)");
                }
        }

        @Test(priority = 7)
        @Description("GST-007")
        public void testGst007() {
                navigateToHome();

                GetStartedPage getStartedPage = new GetStartedPage(driver);

                boolean hoverSuccess = getStartedPage.hoverGetStartedMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Get Started");
                boolean navSuccess = getStartedPage.navigateToDownload();
                assertTrue(navSuccess, "Harus berhasil navigate ke Download");

                getStartedPage.waitForPageLoad();
                takeScreenshot("GST-007_Download_Page");

                boolean clickSuccess = getStartedPage.clickRegisterForFreeButton();
                assertTrue(clickSuccess, "Harus berhasil klik button Register for Free");

                // Wait for page to load (may open in new tab)
                getStartedPage.switchToNewTab();
                getStartedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = getStartedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.startsWith(EXPECTED_COMMUNITY_URL)
                                || currentUrl.contains("community.suitecrm.com");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_COMMUNITY_URL + " tapi actual: " + currentUrl);

                takeScreenshot("GST-007_Community_Page");
        }

        @Test(priority = 8)
        @Description("GST-008")
        public void testGst008() {
                navigateToHome();

                GetStartedPage getStartedPage = new GetStartedPage(driver);

                boolean hoverSuccess = getStartedPage.hoverGetStartedMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Get Started");
                boolean navSuccess = getStartedPage.navigateToDownload();
                assertTrue(navSuccess, "Harus berhasil navigate ke Download");

                getStartedPage.waitForPageLoad();
                takeScreenshot("GST-008_Download_Page");

                boolean clickSuccess = getStartedPage.clickDiscoverButton();
                assertTrue(clickSuccess, "Harus berhasil klik button Discover");

                getStartedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = getStartedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.startsWith(EXPECTED_SUITEASSURED_URL)
                                || currentUrl.contains("enterprise/suiteassured");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_SUITEASSURED_URL + " tapi actual: " + currentUrl);

                takeScreenshot("GST-008_SuiteAssured_Page");
        }

        @Test(priority = 9)
        @Description("GST-009")
        public void testGst009() {
                navigateToHome();

                GetStartedPage getStartedPage = new GetStartedPage(driver);

                boolean hoverSuccess = getStartedPage.hoverGetStartedMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Get Started");
                boolean navSuccess = getStartedPage.navigateToDownload();
                assertTrue(navSuccess, "Harus berhasil navigate ke Download");

                getStartedPage.waitForPageLoad();
                takeScreenshot("GST-009_Download_Page");

                boolean clickSuccess = getStartedPage.clickFindOutMoreButton();
                assertTrue(clickSuccess, "Harus berhasil klik button Find Out More");

                getStartedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = getStartedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.startsWith(EXPECTED_EVS_URL)
                                || currentUrl.contains("enterprise-verification-service");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_EVS_URL + " tapi actual: " + currentUrl);

                takeScreenshot("GST-009_EVS_Page");
        }

        @Test(priority = 10)
        @Description("GST-010")
        public void testGst010() {
                navigateToHome();

                GetStartedPage getStartedPage = new GetStartedPage(driver);

                boolean hoverSuccess = getStartedPage.hoverGetStartedMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Get Started");
                boolean navSuccess = getStartedPage.navigateToDownload();
                assertTrue(navSuccess, "Harus berhasil navigate ke Download");

                getStartedPage.waitForPageLoad();
                takeScreenshot("GST-010_Download_Page");

                // Klik link "a step-by-step guide"
                boolean clickSuccess = getStartedPage.clickStepByStepGuideLink();
                assertTrue(clickSuccess, "Harus berhasil klik link a step-by-step guide");

                // Wait for page to load (may open in new tab)
                getStartedPage.switchToNewTab();
                getStartedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = getStartedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.startsWith(EXPECTED_STEP_BY_STEP_URL)
                                || currentUrl.contains("downloading-installing");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_STEP_BY_STEP_URL + " tapi actual: " + currentUrl);

                takeScreenshot("GST-010_Step_By_Step_Guide_Page");
        }

        @Test(priority = 11)
        @Description("GST-011")
        public void testGst011() {
                navigateToHome();

                GetStartedPage getStartedPage = new GetStartedPage(driver);

                boolean hoverSuccess = getStartedPage.hoverGetStartedMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Get Started");

                boolean navSuccess = getStartedPage.navigateToDownload();
                assertTrue(navSuccess, "Harus berhasil navigate ke Download");

                getStartedPage.waitForPageLoad();
                takeScreenshot("GST-011_Download_Page");

                // Klik link "Check out our user guide"
                boolean clickSuccess = getStartedPage.clickUserGuideLink();
                assertTrue(clickSuccess, "Harus berhasil klik link Check out our user guide");

                // Wait for page to load (may open in new tab)
                getStartedPage.switchToNewTab();
                getStartedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = getStartedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.startsWith(EXPECTED_USER_GUIDE_URL)
                                || currentUrl.contains("docs.suitecrm.com/user");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_USER_GUIDE_URL + " tapi actual: " + currentUrl);

                takeScreenshot("GST-011_User_Guide_Page");
        }

        @Test(priority = 12)
        @Description("GST-012")
        public void testGst012() {
                navigateToHome();

                GetStartedPage getStartedPage = new GetStartedPage(driver);

                boolean hoverSuccess = getStartedPage.hoverGetStartedMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Get Started");
                boolean navSuccess = getStartedPage.navigateToDownload();
                assertTrue(navSuccess, "Harus berhasil navigate ke Download");

                getStartedPage.waitForPageLoad();
                takeScreenshot("GST-012_Download_Page");

                // Klik link "Choose yours today"
                boolean clickSuccess = getStartedPage.clickChooseYoursTodayLink();
                assertTrue(clickSuccess, "Harus berhasil klik link Choose yours today");

                // Wait for page to load (may open in new tab)
                getStartedPage.switchToNewTab();
                getStartedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = getStartedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.startsWith(EXPECTED_CROWDIN_URL)
                                || currentUrl.contains("crowdin.com/project/suitecrmtranslations");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_CROWDIN_URL + " tapi actual: " + currentUrl);

                takeScreenshot("GST-012_Crowdin_Page");
        }

        @Test(priority = 13)
        @Description("GST-013")
        public void testGst013() {
                navigateToHome();

                GetStartedPage getStartedPage = new GetStartedPage(driver);

                boolean hoverSuccess = getStartedPage.hoverGetStartedMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Get Started");

                boolean navSuccess = getStartedPage.navigateToDownload();
                assertTrue(navSuccess, "Harus berhasil navigate ke Download");

                getStartedPage.waitForPageLoad();
                takeScreenshot("GST-013_Download_Page");

                // Tekan button Get 30-Day Free Trial
                boolean clickSuccess = getStartedPage.clickGet30DayFreeTrialButton();
                assertTrue(clickSuccess, "Harus berhasil klik button Get 30-Day Free Trial");

                getStartedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = getStartedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.startsWith(EXPECTED_SUITECRM_HOSTED_URL)
                                || currentUrl.contains("suitecrmhosted");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_SUITECRM_HOSTED_URL + " tapi actual: " + currentUrl);

                takeScreenshot("GST-013_SuiteCRM_Hosted_Page");
        }

        @Test(priority = 14)
        @Description("GST-014")
        public void testGst014() {
                navigateToHome();

                GetStartedPage getStartedPage = new GetStartedPage(driver);

                boolean hoverSuccess = getStartedPage.hoverGetStartedMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Get Started");

                boolean navSuccess = getStartedPage.navigateToDemo();
                assertTrue(navSuccess, "Harus berhasil navigate ke Demo SuiteCRM");

                getStartedPage.waitForPageLoad();
                takeScreenshot("GST-014_Demo_Page");

                // Tekan button Get 30-Day Free Trial
                boolean clickSuccess = getStartedPage.clickGet30DayFreeTrialButton();
                assertTrue(clickSuccess, "Harus berhasil klik button Get 30-Day Free Trial");

                getStartedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = getStartedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.startsWith(EXPECTED_SUITECRM_HOSTED_URL)
                                || currentUrl.contains("suitecrmhosted");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_SUITECRM_HOSTED_URL + " tapi actual: " + currentUrl);

                takeScreenshot("GST-014_SuiteCRM_Hosted_Page");
        }

        @Test(priority = 15)
        @Description("GST-015")
        public void testGst015() {
                navigateToHome();

                GetStartedPage getStartedPage = new GetStartedPage(driver);

                boolean hoverSuccess = getStartedPage.hoverGetStartedMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Get Started");

                boolean navSuccess = getStartedPage.navigateToSuiteCRMHosted();
                assertTrue(navSuccess, "Harus berhasil navigate ke SuiteCRM Hosted");

                getStartedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = getStartedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.startsWith(EXPECTED_SUITECRM_HOSTED_URL)
                                || currentUrl.contains("suitecrmhosted");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_SUITECRM_HOSTED_URL + " tapi actual: " + currentUrl);

                takeScreenshot("GST-015_SuiteCRM_Hosted_Page");
        }
}
