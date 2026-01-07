package com.proyek_softes.landing.tests.about;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.about.NewsletterSignUpPage;
import com.proyek_softes.landing.main.utils.SignUpDataProvider;

import io.qameta.allure.Description;

/**
 * Test class untuk halaman Newsletter Sign Up
 * Berisi test case ABT-008
 */
public class NewsletterSignUpTest extends BaseLandingTest {

    @Test(priority = 1, dataProvider = "signUpData", dataProviderClass = SignUpDataProvider.class)
    @Description("ABT-008")
    public void testAbt008(String email) {
        navigateToHome();

        NewsletterSignUpPage signUpPage = new NewsletterSignUpPage(driver);

        boolean hoverSuccess = signUpPage.hoverAboutMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu About");

        boolean navSuccess = signUpPage.navigateToNewsletterSignUp();
        assertTrue(navSuccess, "Harus berhasil navigate ke Newsletter Sign-Up");

        signUpPage.waitForPageLoad();
        waitSeconds(2);

        boolean onNewsletterPage = signUpPage.isOnNewsletterPage();
        assertTrue(onNewsletterPage, "Harus berada di halaman Newsletter Sign-Up");

        // Scroll to form
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight * 0.5);");
        waitSeconds(1);

        boolean emailFilled = signUpPage.fillEmail(email);
        assertTrue(emailFilled, "Harus berhasil mengisi email");

        boolean privacyChecked = signUpPage.checkPrivacyPolicy();
        assertTrue(privacyChecked, "Harus berhasil centang Privacy Policy");

        boolean marketingChecked = signUpPage.checkMarketingCommunications();
        assertTrue(marketingChecked, "Harus berhasil centang marketing communications");

        boolean captchaHandled = signUpPage.handleCaptcha();
        assertTrue(captchaHandled, "CAPTCHA handling placeholder");

        System.out.println("\n IMPORTANT: CAPTCHA MANUAL INTERVENTION REQUIRED");
        System.out.println("Please manually check the CAPTCHA checkbox before continuing");
        System.out.println("Waiting 10 seconds for manual CAPTCHA interaction");
        waitSeconds(10);

        boolean submitClicked = signUpPage.clickSubmitButton();
        assertTrue(submitClicked, "Harus berhasil klik Submit button");

        signUpPage.waitForPageLoad();
        waitSeconds(3);

        boolean successMessageShown = signUpPage.verifySuccessMessage();

        takeScreenshot("ABT-008_Newsletter_SignUp_Success");

        assertTrue(successMessageShown, 
                "Thank you for joining our mailing list!");
    }

    @Test(priority = 2, dataProvider = "signUpData", dataProviderClass = SignUpDataProvider.class)
    @Description("ABT-009")
    public void testAbt009(String email) {
        navigateToHome();

        NewsletterSignUpPage signUpPage = new NewsletterSignUpPage(driver);

        boolean hoverSuccess = signUpPage.hoverAboutMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu About");

        boolean navSuccess = signUpPage.navigateToNewsletterSignUp();
        assertTrue(navSuccess, "Harus berhasil navigate ke Newsletter Sign-Up");

        signUpPage.waitForPageLoad();
        waitSeconds(2);

        boolean onNewsletterPage = signUpPage.isOnNewsletterPage();
        assertTrue(onNewsletterPage, "Harus berada di halaman Newsletter Sign-Up");

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight * 0.5);");
        waitSeconds(1);

        // Isi email saja (skip checkboxes)
        boolean emailFilled = signUpPage.fillEmail(email);
        assertTrue(emailFilled, "Harus berhasil mengisi email");

        boolean submitClicked = signUpPage.clickSubmitButton();
        assertTrue(submitClicked, "Harus berhasil klik Submit button");

        waitSeconds(2);

        boolean errorMessageShown = signUpPage.verifyRequiredErrorMessage();

        takeScreenshot("ABT-009_Newsletter_Required_Error");

        assertTrue(errorMessageShown, "This is required.");
    }
}
