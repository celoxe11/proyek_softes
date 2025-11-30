package com.proyek_softes.demo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.proyek_softes.demo.pages.LoginPage;
import com.proyek_softes.demo.pages.WelcomePage;

public class LoginTest extends BaseTest {
    
    @Test
    public void testCompleteLoginFlow() {
        LoginPage loginPage = new LoginPage(driver);
        
        // Step 1: Navigate and Login
        loginPage.navigateToLogin(baseUrl);
        WelcomePage welcomePage = loginPage.login("will", "will");
        
        // Step 2: Verify welcome Access
        String welcomeTitle = welcomePage.getWelcomeTitle();
        Assert.assertEquals(welcomeTitle, "Welcome to the SuiteCRM 7 Demo", 
            "Welcome should be accessible after login");
    }
}
