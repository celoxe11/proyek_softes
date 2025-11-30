package com.proyek_softes.demo.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.proyek_softes.demo.pages.LoginPage;
import com.proyek_softes.demo.pages.WelcomePage;

public class ComprehensiveTest extends BaseTest {
    
    @BeforeTest
    public void login() {
        System.out.println("\n--- Logging into SuiteCRM ---");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLogin(baseUrl);
        WelcomePage welcomePage = loginPage.login("will", "will");
        
        String welcomeTitle = welcomePage.getWelcomeTitle();
        Assert.assertEquals(welcomeTitle, "Welcome to the SuiteCRM 7 Demo", 
            "Dashboard should be accessible after login");
        
        System.out.println("Login successful\n");
    }
    
    @Test
    public void testDashboardAccess() {
        System.out.println("Testing: Dashboard Access");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("Home") || currentUrl.contains("index"), 
            "Should be on dashboard page");
        System.out.println("Dashboard accessible");
    }
    
    @Test
    public void testPageTitle() {
        System.out.println("Testing: Page Title");
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("SuiteCRM"), "Page title should contain SuiteCRM");
        System.out.println("Page title verified: " + title);
    }
}
