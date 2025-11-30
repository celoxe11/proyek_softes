package com.proyek_softes.demo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.proyek_softes.demo.pages.LoginPage;
import com.proyek_softes.demo.pages.WelcomePage;

public class LoginTest extends BaseTest {
    
    @Test
    public void testCompleteLoginFlow() {
        // Step 1: Navigate and Login using helper
        login("will", "will");
        WelcomePage welcomePage = new WelcomePage(driver);
        
        // Step 2: Verify welcome Access
        String welcomeTitle = welcomePage.getWelcomeTitle();
        Assert.assertEquals(welcomeTitle, "Welcome to the SuiteCRM 7 Demo", 
            "Welcome should be accessible after login");
    }
}
