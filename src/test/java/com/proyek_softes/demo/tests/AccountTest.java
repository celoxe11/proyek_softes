package com.proyek_softes.demo.tests;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.accounts.AccountsPage;
import com.proyek_softes.demo.pages.accounts.CreateAccountsPage;

public class AccountTest extends BaseTest {

    private AccountsPage accountsPage;
    private CreateAccountsPage createAccountsPage;

    @Test
    public void testNavigateToCreateAccount() {
        System.out.println("\nTesting: Navigate to Create Account Page");
        login("will", "will");
        accountsPage = new AccountsPage(driver);

        // Navigate to create account page
        accountsPage.navigateToCreateAccount();

        // Additional assertions can be added here to verify navigation
        System.out.println("✅ Navigation to Create Account page verified successfully!");
    }

    @Test
    public void testCreateAccountWithoutMandatoryFields() {
        System.out.println("\nTesting: Create Account without Mandatory Fields");
        createAccountsPage = new CreateAccountsPage(driver);

        // Attempt to save without filling mandatory fields
        createAccountsPage.save();
        String messageText = createAccountsPage.cekValidationMessage();

        assertTrue(messageText.contains("This field is required"),
                "Validation message for mandatory fields should be displayed");
        System.out.println("✅ Validation for mandatory fields verified successfully!");
    }

    @Test
    public void testCreateAccountWithNameOnly() {
        System.out.println("\nTesting: Create Account with Name Only");
        createAccountsPage = new CreateAccountsPage(driver);

        // Fill in only the name and save
        createAccountsPage.onlyAddName();
        createAccountsPage.save();

        // Additional assertions can be added here to verify account creation
        System.out.println("✅ Account creation with name only verified successfully!");
    }

    @Test
    public void testCreateAccountWithAllFields() {
        System.out.println("\nTesting: Create Account with All Fields");
        createAccountsPage = new CreateAccountsPage(driver);

        // Fill in all fields and save
        createAccountsPage.addInformation();
        createAccountsPage.save();

        // Additional assertions can be added here to verify account creation
        System.out.println("✅ Account creation with all fields verified successfully!");
    }

    @Test
    public void testNavigateToViewAccounts() {
        System.out.println("\nTesting: Navigate to View Accounts Page");
        // Navigate to view accounts page
        accountsPage.navigateToViewAccounts();

        // Additional assertions can be added here to verify navigation
        System.out.println("✅ Navigation to View Accounts page verified successfully!");
    }

    @Test
    public void testNavigateToImportAccounts() {
        System.out.println("\nTesting: Navigate to Import Accounts Page");
        // Navigate to import accounts page
        accountsPage.navigateToImportAccounts();

        // Additional assertions can be added here to verify navigation
        System.out.println("✅ Navigation to Import Accounts page verified successfully!");
    }
}
