package com.proyek_softes.demo.tests.crud;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.proyek_softes.demo.pages.WelcomePage;
import com.proyek_softes.demo.tests.BaseTest;
import com.proyek_softes.demo.pages.AccountsPage;

public class AccountCrudTest extends BaseTest {
    
    @Test
    public void testCreateAccount() {
        System.out.println("\nTesting: Create Account");
        WelcomePage welcomePage = new WelcomePage(driver);
        
        // Verify Dashboard
        String dashboardTitle = welcomePage.getWelcomeTitle();
        Assert.assertEquals(dashboardTitle, "Welcome to the SuiteCRM 7 Demo");
        
        // Step 2: Navigate to Accounts
        AccountsPage accountsPage = welcomePage.navigateToAccounts();
        Assert.assertTrue(accountsPage.getCurrentUrl().contains("Accounts"));
        
        // Step 3: Create Account
        accountsPage.createAccount("Test Company Inc", "555-0123", "www.testcompany.com");
        Assert.assertTrue(accountsPage.isAccountCreated(), "Account should be created successfully");
        
        // Step 4: Update Account
        accountsPage.openFirstAccount();
        accountsPage.updateAccount("Updated Test Company Inc");
        Assert.assertTrue(accountsPage.getCurrentUrl().contains("Accounts"));
        
        // Step 5: Delete Account
        accountsPage.openFirstAccount();
        accountsPage.deleteAccount();
        System.out.println("Account CRUD operations completed successfully!");
    }
}
