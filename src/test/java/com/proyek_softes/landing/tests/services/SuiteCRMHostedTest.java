package com.proyek_softes.landing.tests.services;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.services.SuiteCRMHostedPage;

import io.qameta.allure.Description;

/**
 * Test class untuk halaman SuiteCRM Hosted
 * Berisi test case SRV-009+
 */
public class SuiteCRMHostedTest extends BaseLandingTest {

        private static final String EXPECTED_CHECKOUT_URL = "https://suitecrm.com/suite-checkout/";
        private static final String EXPECTED_MIGRATIONS_URL = "https://suitecrm.com/enterprise/migrations/";
        private static final String EXPECTED_CONTACT_URL = "https://suitecrm.com/about/about-us/contact/";
        private static final String EXPECTED_VIEWPACKAGES_URL = "https://suitecrm.com/suitecrmhosted/#viewpackages";

        @Test(priority = 1)
        @Description("SRV-009")
        public void testSrv009() {
                navigateToHome();

                SuiteCRMHostedPage suiteCRMHostedPage = new SuiteCRMHostedPage(driver);

                boolean hoverSuccess = suiteCRMHostedPage.hoverServicesMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Services");

                boolean navSuccess = suiteCRMHostedPage.navigateToSuiteCRMHosted();
                assertTrue(navSuccess, "Harus berhasil navigate ke SuiteCRM Hosted");

                suiteCRMHostedPage.waitForPageLoad();
                takeScreenshot("SRV-009_SuiteCRM_Hosted_Page");

                // Tekan button Start Free Trial bagian Premium
                boolean clickSuccess = suiteCRMHostedPage.clickPremiumStartFreeTrialButton();
                assertTrue(clickSuccess, "Harus berhasil klik button Start Free Trial pada Premium package");

                suiteCRMHostedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = suiteCRMHostedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.startsWith(EXPECTED_CHECKOUT_URL)
                                || currentUrl.contains("suite-checkout");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_CHECKOUT_URL + " tapi actual: " + currentUrl);

                takeScreenshot("SRV-009_Suite_Checkout_Page");
        }

        @Test(priority = 2)
        @Description("SRV-010")
        public void testSrv010() {
                navigateToHome();

                SuiteCRMHostedPage suiteCRMHostedPage = new SuiteCRMHostedPage(driver);

                boolean hoverSuccess = suiteCRMHostedPage.hoverServicesMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Services");

                boolean navSuccess = suiteCRMHostedPage.navigateToSuiteCRMHosted();
                assertTrue(navSuccess, "Harus berhasil navigate ke SuiteCRM Hosted");

                suiteCRMHostedPage.waitForPageLoad();
                takeScreenshot("SRV-010_SuiteCRM_Hosted_Page");

                // Klik link "click here to read about our migration services"
                boolean clickSuccess = suiteCRMHostedPage.clickMigrationServicesLink();
                assertTrue(clickSuccess, "Harus berhasil klik link migration services");

                suiteCRMHostedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = suiteCRMHostedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.startsWith(EXPECTED_MIGRATIONS_URL)
                                || currentUrl.contains("enterprise/migrations");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_MIGRATIONS_URL + " tapi actual: " + currentUrl);

                takeScreenshot("SRV-010_Migrations_Page");
        }

        @Test(priority = 3)
        @Description("SRV-011")
        public void testSrv011() {
                navigateToHome();

                SuiteCRMHostedPage suiteCRMHostedPage = new SuiteCRMHostedPage(driver);

                boolean hoverSuccess = suiteCRMHostedPage.hoverServicesMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Services");

                boolean navSuccess = suiteCRMHostedPage.navigateToSuiteCRMHosted();
                assertTrue(navSuccess, "Harus berhasil navigate ke SuiteCRM Hosted");

                suiteCRMHostedPage.waitForPageLoad();
                takeScreenshot("SRV-011_SuiteCRM_Hosted_Page");

                // Klik link "contact our sales team"
                boolean clickSuccess = suiteCRMHostedPage.clickContactSalesTeamLink();
                assertTrue(clickSuccess, "Harus berhasil klik link contact our sales team");

                suiteCRMHostedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = suiteCRMHostedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.startsWith(EXPECTED_CONTACT_URL)
                                || currentUrl.contains("about-us/contact");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_CONTACT_URL + " tapi actual: " + currentUrl);

                takeScreenshot("SRV-011_Contact_Page");
        }

        @Test(priority = 4)
        @Description("SRV-012")
        public void testSrv012() {
                navigateToHome();

                SuiteCRMHostedPage suiteCRMHostedPage = new SuiteCRMHostedPage(driver);

                boolean hoverSuccess = suiteCRMHostedPage.hoverServicesMenu();
                assertTrue(hoverSuccess, "Harus berhasil hover ke menu Services");

                boolean navSuccess = suiteCRMHostedPage.navigateToSuiteCRMHosted();
                assertTrue(navSuccess, "Harus berhasil navigate ke SuiteCRM Hosted");

                suiteCRMHostedPage.waitForPageLoad();
                takeScreenshot("SRV-012_SuiteCRM_Hosted_Page");

                // Tekan button Get Started with SuiteCRM Hosted
                boolean clickSuccess = suiteCRMHostedPage.clickGetStartedWithSuiteCRMHostedButton();
                assertTrue(clickSuccess, "Harus berhasil klik button Get Started with SuiteCRM Hosted");

                suiteCRMHostedPage.waitForPageLoad();
                waitSeconds(2);

                String currentUrl = suiteCRMHostedPage.getCurrentUrl();
                System.out.println("Current URL: " + currentUrl);

                boolean urlCorrect = currentUrl.startsWith(EXPECTED_VIEWPACKAGES_URL)
                                || currentUrl.contains("suitecrmhosted/#viewpackages")
                                || currentUrl.contains("#viewpackages");
                assertTrue(urlCorrect,
                                "URL harus " + EXPECTED_VIEWPACKAGES_URL + " tapi actual: " + currentUrl);

                takeScreenshot("SRV-012_ViewPackages_Page");
        }
}
