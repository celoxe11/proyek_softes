package com.proyek_softes.demo.tests;

import static org.testng.Assert.assertTrue;

import java.util.Map;

import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.accounts.AccountsPage;
import com.proyek_softes.demo.pages.accounts.CreateAccountsPage;
import com.proyek_softes.demo.utils.AccountDataProvider;

public class AccountTest extends BaseTest {

    // @Test
    // public void testNavigateToCreateAccount() {
    // System.out.println("\n--- Testing: Navigate to Create Account Page ---");

    // login("will", "will");

    // AccountsPage accountsPage = new AccountsPage(driver);
    // accountsPage.navigateToAccountsModule();
    // accountsPage.navigateToCreateAccount();

    // boolean isOnCreatePage = accountsPage.checkPageTitle("CREATE");
    // assertTrue(isOnCreatePage, "Should be on Create Account page with SAVE button
    // and name field visible");

    // System.out.println("✅ Navigation to Create Account page verified
    // successfully!");
    // }

    // @Test
    // public void testNavigateToViewAccounts() {
    // System.out.println("\n--- Testing: Navigate to View Accounts Page ---");

    // login("will", "will");

    // AccountsPage accountsPage = new AccountsPage(driver);
    // accountsPage.navigateToAccountsModule();
    // accountsPage.navigateToViewAccounts();

    // boolean isOnViewPage = accountsPage.checkPageTitle("ACCOUNTS");
    // assertTrue(isOnViewPage,
    // "Should be on View Accounts page (URL should contain module=Accounts and
    // action=index)");

    // System.out.println("✅ Navigation to View Accounts page verified
    // successfully!");
    // }

    // @Test
    // public void testNavigateToImportAccounts() {
    // System.out.println("\n--- Testing: Navigate to Import Accounts Page ---");

    // login("will", "will");

    // AccountsPage accountsPage = new AccountsPage(driver);
    // accountsPage.navigateToAccountsModule();
    // accountsPage.navigateToImportAccounts();

    // boolean isOnImportPage = accountsPage.checkPageTitle("Step 1: Upload Import
    // File");
    // assertTrue(isOnImportPage,
    // "Should be on Import Accounts page (URL should contain module=Import or
    // action=Step1)");

    // System.out.println("✅ Navigation to Import Accounts page verified
    // successfully!");
    // }

    // ==================== VALIDATION TESTS ====================

    // @Test(dataProvider = "validationTestData", dataProviderClass = AccountDataProvider.class)
    // public void testCreateAccountWithoutMandatoryFields(Map<String, String> testData) {
    //     System.out.println("\n--- Testing: " + testData.get("testCase") + " ---");

    //     login("will", "will");

    //     AccountsPage accountsPage = new AccountsPage(driver);
    //     accountsPage.navigateToAccountsModule();
    //     accountsPage.navigateToCreateAccount();

    //     CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);

    //     // Try to save without filling mandatory fields
    //     createAccountsPage.save();

    //     // Assert - Validation message should be displayed
    //     String messageText = createAccountsPage.cekValidationMessage();
    //     String expectedMessage = testData.get("expectedValidationMessage");
    //     assertTrue(messageText.contains(expectedMessage),
    //             "Validation message should contain '" + expectedMessage + "'. Actual: " +
    //                     messageText);

    //     boolean hasError = createAccountsPage.hasValidationError();
    //     assertTrue(hasError, "Form should show validation error when saving without mandatory fields");

    //     System.out.println("✅ Validation test passed: " + testData.get("testCase"));
    // }

    // // ==================== CREATE ACCOUNT TESTS WITH DATA PROVIDER
    // // ====================

    @Test(dataProvider = "createAccountData", dataProviderClass = AccountDataProvider.class)
    public void testCreateAccountWithDataProvider(Map<String, String> testData) {
        String testCase = testData.get("testCase");
        String expectedResult = testData.get("expectedResult");

        System.out.println("\n--- Testing: " + testCase + " ---");

        login("will", "will");

        AccountsPage accountsPage = new AccountsPage(driver);
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToCreateAccount();

        CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);

        // Fill data from external source
        createAccountsPage.addInformationFromData(testData);
        createAccountsPage.save();

        // Assert based on expected result
        if ("success".equals(expectedResult)) {
            boolean isSaved = createAccountsPage.isAccountSavedSuccessfully(testData.get("name"));
            assertTrue(isSaved, "Account should be saved successfully for test case: " +
                    testCase);
            System.out.println("✅ Account created successfully: " +
                    testData.get("name"));
        } else if ("validation_error".equals(expectedResult)) {
            boolean hasError = createAccountsPage.hasValidationError();
            assertTrue(hasError, "Should show validation error for test case: " +
                    testCase);
            System.out.println("✅ Validation error shown as expected: " + testCase);
        }
    }
}
