package com.proyek_softes.demo.tests;

import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.accounts.AccountsPage;
import com.proyek_softes.demo.pages.accounts.CreateAccountsPage;
import com.proyek_softes.demo.utils.AccountDataProvider;

public class AccountTest extends BaseTest {

    @Test
    public void testNavigateToCreateAccount() {
        System.out.println("\n--- Testing: Navigate to Create Account Page ---");
        login("will", "will");
        AccountsPage accountsPage = new AccountsPage(driver);
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToCreateAccount();
        boolean isOnCreatePage = accountsPage.checkPageTitle("CREATE");
        assertTrue(isOnCreatePage, "Should be on Create Account page with SAVE button and name field visible");
        System.out.println("✅ Navigation to Create Account page verified successfully!");
    }

    @Test
    public void testNavigateToViewAccounts() {
        System.out.println("\n--- Testing: Navigate to View Accounts Page ---");

        login("will", "will");

        AccountsPage accountsPage = new AccountsPage(driver);
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToViewAccounts();

        boolean isOnViewPage = accountsPage.checkPageTitle("ACCOUNTS");
        assertTrue(isOnViewPage, "Should be on View Accounts page (URL should contain module=Accounts and action = index)");

        System.out.println("✅ Navigation to View Accounts page verified successfully!");
    }

    @Test
    public void testNavigateToImportAccounts() {
        System.out.println("\n--- Testing: Navigate to Import Accounts Page ---");

        login("will", "will");

        AccountsPage accountsPage = new AccountsPage(driver);
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToImportAccounts();

        boolean isOnImportPage = accountsPage.checkPageTitle("Step 1: Upload Import File");
        assertTrue(isOnImportPage,
                "Should be on Import Accounts page (URL should contain module=Import or action = Step1)");

        System.out.println("✅ Navigation to Import Accounts page verified successfully!");
    }

    // ==================== VALIDATION TESTS ====================
    @Test(dataProvider = "validationTestData", dataProviderClass = AccountDataProvider.class)
    public void testCreateAccountWithoutMandatoryFields(Map<String, String> testData) {
        System.out.println("\n--- Testing: " + testData.get("testCase") + " ---");
        login("will", "will");
        AccountsPage accountsPage = new AccountsPage(driver);
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToCreateAccount();
        CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
        // Try to save without filling mandatory fields
        createAccountsPage.save();
        // Assert - Validation message should be displayed
        String messageText = createAccountsPage.cekValidationMessage();
        String expectedMessage = testData.get("expectedValidationMessage");
        assertTrue(messageText.contains(expectedMessage),
                "Validation message should contain '" + expectedMessage + "'. Actual: "
                + messageText);
        boolean hasError = createAccountsPage.hasValidationError();
        assertTrue(hasError, "Form should show validation error when saving without mandatory fields");
        System.out.println("✅ Validation test passed: " + testData.get("testCase"));
    }

    // ==================== CREATE ACCOUNT TESTS ====================
    @Test(dataProvider = "minimalAccountData", dataProviderClass = AccountDataProvider.class)
    public void testCreateAccountWithMinimalData(Map<String, String> testData) {
        String testCase = testData.get("testCase");
        System.out.println("\n--- Testing: " + testCase + " ---");
        login("will", "will");

        AccountsPage accountsPage = new AccountsPage(driver);
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToCreateAccount();

        CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
        createAccountsPage.addInformationFromData(testData);
        createAccountsPage.save();

        System.out.println(testData.get("name"));
        boolean isSaved = createAccountsPage.isAccountSavedSuccessfully(testData.get("name"));
        assertTrue(isSaved, "Account with minimal data should be saved successfully");
        System.out.println("✅ Account created successfully: " + testData.get("name"));
    }

    @Test(dataProvider = "fullAccountData", dataProviderClass = AccountDataProvider.class)
    public void testCreateAccountWithFullData(Map<String, String> testData) {
        String testCase = testData.get("testCase");
        System.out.println("\n--- Testing: " + testCase + " ---");
        login("will", "will");

        AccountsPage accountsPage = new AccountsPage(driver);
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToCreateAccount();

        CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
        createAccountsPage.addInformationFromData(testData);
        createAccountsPage.save();

        boolean isSaved = createAccountsPage.isAccountSavedSuccessfully(testData.get("name"));
        assertTrue(isSaved, "Account with full data should be saved successfully");
        System.out.println("✅ Account created successfully: " + testData.get("name"));
    }

    @Test(dataProvider = "multipleEmailAccountData", dataProviderClass = AccountDataProvider.class)
    public void testCreateAccountWithMultipleEmails(Map<String, String> testData) {
        String testCase = testData.get("testCase");
        System.out.println("\n--- Testing: " + testCase + " ---");
        login("will", "will");

        AccountsPage accountsPage = new AccountsPage(driver);
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToCreateAccount();

        CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
        createAccountsPage.addInformationFromData(testData);
        createAccountsPage.save();

        boolean isSaved = createAccountsPage.isAccountSavedSuccessfully(testData.get("name"));
        assertTrue(isSaved, "Account with multiple emails should be saved successfully");
        System.out.println("✅ Account created successfully: " + testData.get("name"));
    }

    // ==================== DUPLICATE ACCOUNT TESTS ====================
    
    @Test(dataProvider = "duplicateAccountDataConfirmation", dataProviderClass = AccountDataProvider.class)
    public void testDuplicateAccountWithDataProvider(Map<String, String> testData) {
        String expectedResult = testData.get("expectedResult");
        System.out.println("\n--- Testing Duplicate Account Scenario ---");
        System.out.println("  → Expected Result: " + expectedResult);
        
        login("will", "will");
        
        AccountsPage accountsPage = new AccountsPage(driver);
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToCreateAccount();
        
        CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
        
        createAccountsPage.addInformationFromData(testData);
        createAccountsPage.save();
        
        // First account should save successfully without warning
        if (expectedResult.equals("success")) {
            boolean isSaved = createAccountsPage.isAccountSavedSuccessfully(testData.get("name"));
            assertTrue(isSaved, "First account should be saved successfully");
            System.out.println("✅ First account created successfully: " + testData.get("name"));
        } 
        // Second account should show duplicate warning
        else if (expectedResult.equals("asked_confirmation")) {
            boolean warningDisplayed = createAccountsPage.isDuplicateWarningDisplayed();
            assertTrue(warningDisplayed, "Duplicate warning should be displayed for second account");
            System.out.println("✅ Duplicate warning detected as expected");
        }
    }
    
    @Test
    public void testDuplicateAccountWarningAppears() {
        System.out.println("\n--- Testing: Duplicate Account Warning Detection ---");
        login("will", "will");
        
        AccountsPage accountsPage = new AccountsPage(driver);
        
        // Step 1: Create first account
        System.out.println("  → Step 1: Creating first account...");
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToCreateAccount();
        
        CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
        Map<String, String> firstAccountData = Map.of("name", "Test Account Duplicate Warning");
        createAccountsPage.addInformationFromData(firstAccountData);
        createAccountsPage.save();
        
        boolean firstAccountSaved = createAccountsPage.isAccountSavedSuccessfully("Test Account Duplicate Warning");
        assertTrue(firstAccountSaved, "First account should be created successfully");
        System.out.println("  ✓ First account created successfully");
        
        // Step 2: Try to create duplicate account
        System.out.println("  → Step 2: Attempting to create duplicate account...");
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToCreateAccount();
        
        createAccountsPage = new CreateAccountsPage(driver);
        Map<String, String> duplicateData = Map.of("name", "Test Account Duplicate Warning");
        createAccountsPage.addInformationFromData(duplicateData);
        createAccountsPage.save();
        
        // Step 3: Verify duplicate warning appears
        boolean warningDisplayed = createAccountsPage.isDuplicateWarningDisplayed();
        assertTrue(warningDisplayed, "Duplicate account warning should be displayed");
        System.out.println("  ✓ Duplicate warning message detected");
        System.out.println("✅ Duplicate detection test passed!");
    }
    
    @Test
    public void testCancelDuplicateAccountCreation() {
        System.out.println("\n--- Testing: Cancel Duplicate Account Creation ---");
        login("will", "will");
        
        AccountsPage accountsPage = new AccountsPage(driver);
        
        // Step 1: Create first account
        System.out.println("  → Step 1: Creating first account...");
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToCreateAccount();
        
        CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
        Map<String, String> firstAccountData = Map.of("name", "Test Account Cancel Duplicate");
        createAccountsPage.addInformationFromData(firstAccountData);
        createAccountsPage.save();
        
        boolean firstAccountSaved = createAccountsPage.isAccountSavedSuccessfully("Test Account Cancel Duplicate");
        assertTrue(firstAccountSaved, "First account should be created successfully");
        System.out.println("  ✓ First account created successfully");
        
        // Step 2: Try to create duplicate and verify warning
        System.out.println("  → Step 2: Attempting duplicate and canceling...");
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToCreateAccount();
        
        createAccountsPage = new CreateAccountsPage(driver);
        Map<String, String> duplicateData = Map.of("name", "Test Account Cancel Duplicate");
        createAccountsPage.addInformationFromData(duplicateData);
        createAccountsPage.save();
        
        // Verify warning appears
        boolean warningDisplayed = createAccountsPage.isDuplicateWarningDisplayed();
        assertTrue(warningDisplayed, "Duplicate warning should appear before canceling");
        System.out.println("  ✓ Duplicate warning appeared");
        
        // Step 3: Cancel the duplicate creation
        createAccountsPage.cancel();
        System.out.println("  ✓ Clicked Cancel button");

        WebElement accountPageTitle = wait.until(ExpectedConditions.presenceOfElementLocated(accountsPage.getPageTitle()));
        boolean onAccountsList = accountPageTitle.getText().trim().equalsIgnoreCase("ACCOUNTS");
        assertTrue(onAccountsList, "Should be redirected to accounts list after cancel");
        System.out.println("✅ Cancel duplicate test passed!");
    }
    
    @Test
    public void testSaveDuplicateAccountAnyway() {
        System.out.println("\n--- Testing: Save Duplicate Account Anyway ---");
        login("will", "will");
        
        AccountsPage accountsPage = new AccountsPage(driver);
        
        // Step 1: Create first account
        System.out.println("  → Step 1: Creating first account...");
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToCreateAccount();
        
        CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
        Map<String, String> firstAccountData = Map.of("name", "Test Account Save Duplicate");
        createAccountsPage.addInformationFromData(firstAccountData);
        createAccountsPage.save();
        
        boolean firstAccountSaved = createAccountsPage.isAccountSavedSuccessfully("Test Account Save Duplicate");
        assertTrue(firstAccountSaved, "First account should be created successfully");
        System.out.println("  ✓ First account created successfully");
        
        // Step 2: Try to create duplicate
        System.out.println("  → Step 2: Creating duplicate account...");
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToCreateAccount();
        
        createAccountsPage = new CreateAccountsPage(driver);
        Map<String, String> duplicateData = Map.of("name", "Test Account Save Duplicate");
        createAccountsPage.addInformationFromData(duplicateData);
        createAccountsPage.save();
        
        // Verify warning appears
        boolean warningDisplayed = createAccountsPage.isDuplicateWarningDisplayed();
        assertTrue(warningDisplayed, "Duplicate warning should appear");
        System.out.println("  ✓ Duplicate warning appeared");
        
        // Step 3: Save anyway (clicking save again)
        System.out.println("  → Step 3: Confirming save for duplicate...");
        createAccountsPage.saveDuplicateAccount();
        
        // Verify duplicate was saved successfully
        boolean duplicateSaved = createAccountsPage.isAccountSavedSuccessfully("Test Account Save Duplicate");
        assertTrue(duplicateSaved, "Duplicate account should be saved after confirmation");
        System.out.println("✅ Save duplicate account test passed!");
    }

}
