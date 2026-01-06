package com.proyek_softes.landing.tests.resources;

import static org.testng.Assert.assertTrue;

import java.util.Map;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.resources.ClientLoginPage;
import com.proyek_softes.landing.main.utils.LoginDataProvider;

import io.qameta.allure.Description;

/**
 * Test class untuk halaman Client Login
 * Berisi test case RES-023 dan RES-024
 */
public class ClientLoginTest extends BaseLandingTest {

    private static final String EXPECTED_CLIENT_LOGIN_URL = "https://portal.suitecrm.com/";
    private static final String EXPECTED_ERROR_MESSAGE = "Username and password do not match or you do not have an account yet.";

    @Test(priority = 1)
    @Description("RES-023")
    public void testRes023() {
        navigateToHome();

        ClientLoginPage clientLoginPage = new ClientLoginPage(driver);

        boolean hoverSuccess = clientLoginPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = clientLoginPage.navigateToClientLogin();
        assertTrue(navSuccess, "Harus berhasil navigate ke Client Login");

        clientLoginPage.waitForPageLoad();
        waitSeconds(2);

        clientLoginPage.switchToNewTab();
        clientLoginPage.waitForPageLoad();

        String currentUrl = clientLoginPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_CLIENT_LOGIN_URL);
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_CLIENT_LOGIN_URL + " tapi actual: " + currentUrl);

        takeScreenshot("RES-023_ClientLogin_Page");
    }

    @Test(priority = 2, dataProvider = "loginData", dataProviderClass = LoginDataProvider.class)
    @Description("RES-024")
    public void testRes024(Map<String, String> data) {
        navigateToHome();

        ClientLoginPage clientLoginPage = new ClientLoginPage(driver);

        boolean hoverSuccess = clientLoginPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = clientLoginPage.navigateToClientLogin();
        assertTrue(navSuccess, "Harus berhasil navigate ke Client Login");

        clientLoginPage.waitForPageLoad();
        waitSeconds(2);

        clientLoginPage.switchToNewTab();
        clientLoginPage.waitForPageLoad();
        takeScreenshot("RES-024_ClientLogin_Page");

        boolean usernameEntered = clientLoginPage.enterUsername(data.get("username"));
        assertTrue(usernameEntered, "Harus berhasil memasukkan username");

        boolean passwordEntered = clientLoginPage.enterPassword(data.get("password"));
        assertTrue(passwordEntered, "Harus berhasil memasukkan password");

        waitSeconds(1);
        takeScreenshot("RES-024_Credentials_Entered");

        boolean clickSuccess = clientLoginPage.clickLoginButton();
        assertTrue(clickSuccess, "Harus berhasil klik button LOG IN");

        clientLoginPage.waitForPageLoad();
        waitSeconds(2);

        boolean errorMessageVisible = clientLoginPage.verifyErrorMessage(EXPECTED_ERROR_MESSAGE);
        assertTrue(errorMessageVisible,
                "Error message harus terlihat: " + EXPECTED_ERROR_MESSAGE);

        takeScreenshot("RES-024_Error_Message");
    }

    @Test(priority = 3, dataProvider = "forgotUsernameData", dataProviderClass = LoginDataProvider.class)
    @Description("RES-025")
    public void testRes025(Map<String, String> data) {
        navigateToHome();

        ClientLoginPage clientLoginPage = new ClientLoginPage(driver);

        boolean hoverSuccess = clientLoginPage.hoverResourcesMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu Resources");

        boolean navSuccess = clientLoginPage.navigateToClientLogin();
        assertTrue(navSuccess, "Harus berhasil navigate ke Client Login");

        clientLoginPage.waitForPageLoad();
        waitSeconds(2);

        clientLoginPage.switchToNewTab();
        clientLoginPage.waitForPageLoad();
        takeScreenshot("RES-025_ClientLogin_Page");

        boolean clickForgotSuccess = clientLoginPage.clickForgotUsernameLink();
        assertTrue(clickForgotSuccess, "Harus berhasil klik link Forgot your username");

        clientLoginPage.waitForPageLoad();
        waitSeconds(2);
        takeScreenshot("RES-025_ForgotUsername_Page");

        boolean emailEntered = clientLoginPage.enterEmail(data.get("email"));
        assertTrue(emailEntered, "Harus berhasil memasukkan email address");

        waitSeconds(1);
        takeScreenshot("RES-025_Email_Entered");

        boolean submitSuccess = clientLoginPage.clickSubmitButton();
        assertTrue(submitSuccess, "Harus berhasil klik button Submit");

        clientLoginPage.waitForPageLoad();
        waitSeconds(2);

        String expectedSuccessMessage = "If the email address you entered is registered on this site you will shortly receive an email with a reminder.";
        boolean successMessageVisible = clientLoginPage.verifySuccessMessage(expectedSuccessMessage);
        assertTrue(successMessageVisible,
                "Success message harus terlihat: " + expectedSuccessMessage);

        takeScreenshot("RES-025_Success_Message");
    }
}
