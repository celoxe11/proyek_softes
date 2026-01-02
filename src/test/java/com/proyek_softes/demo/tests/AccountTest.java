package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.accounts.AccountsPage;
import com.proyek_softes.demo.pages.accounts.CreateAccountsPage;
import com.proyek_softes.demo.pages.accounts.ImportAccountsPage;
import com.proyek_softes.demo.utils.AccountDataProvider;

import io.qameta.allure.Description;

public class AccountTest extends BaseTest {

    @Test(dataProvider = "createAccountData", dataProviderClass = AccountDataProvider.class)
    @Description("DEM-004")
    public void testDem004(Map<String, String> testData) {
        login("will", "will");
        AccountsPage accountsPage = new AccountsPage(driver);
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToCreateAccount();

        CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
        createAccountsPage.addInformationFromData(testData);
        createAccountsPage.save();

        boolean isSaved = createAccountsPage.isAccountSavedSuccessfully(testData.get("name"));
        assertTrue(isSaved, "Account with minimal data should be saved successfully");

        takeScreenshot("DEM-004_Create_Account");

        accountsPage.navigateToViewAccounts();
        boolean isInFirstRow = accountsPage.isInFirstRow(testData.get("name"));
        assertTrue(isInFirstRow, "Created account should appear in the first row of accounts list");

        // take screenshot of the row containing the account name
        takeElementScreenshot("DEM-004_Account_In_List", accountsPage.getFirstRowLocator());
    }

    @Test(dataProvider = "viewAccountData", dataProviderClass = AccountDataProvider.class)
    @Description("DEM-005")
    public void testDem005(Map<String, String> testData) {
        login("will", "will");
        AccountsPage accountsPage = new AccountsPage(driver);
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToViewAccounts();
        accountsPage.clickFirstAccount();

        try {
            Thread.sleep(2000); // wait for 2 seconds to ensure page is loaded
        } catch (InterruptedException e) {
        }

        boolean isOnAccountDetailPage = accountsPage.isAccountTitleCorrect(testData.get("name")); // Replace with actual name to verify
        assertTrue(isOnAccountDetailPage, "Should be on Account Detail page for the selected account");
        takeScreenshot("DEM-005_View_Account_Detail");
    }

    @Test(dataProvider = "editAccountData", dataProviderClass = AccountDataProvider.class)
    @Description("DEM-006")
    public void testDem006(Map<String, String> testData) {
        login("will", "will");
        AccountsPage accountsPage = new AccountsPage(driver);
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToViewAccounts();
        accountsPage.clickFirstAccount();

        try {
            Thread.sleep(2000); // wait for 2 seconds to ensure page is loaded
        } catch (InterruptedException e) {
        }

        boolean isOnAccountDetailPage = accountsPage.isAccountTitleCorrect(testData.get("name_before_edit"));
        assertTrue(isOnAccountDetailPage, "Should be on Account Detail page for the selected account");
        takeScreenshot("DEM-006_View_Account_Detail_Before_Edit");

        accountsPage.editAccount();

        CreateAccountsPage editAccountsPage = new CreateAccountsPage(driver);
        editAccountsPage.addInformationFromData(testData);
        editAccountsPage.save();

        boolean isSaved = editAccountsPage.isAccountSavedSuccessfully(testData.get("name"));
        assertTrue(isSaved, "Account should be saved successfully after editing");

        takeScreenshot("DEM-006_Edit_Account");
    }

    @Test
    @Description("DEM-007")
    public void testDem007() {
        try {
            login("will", "will");
            AccountsPage accountsPage = new AccountsPage(driver);
            accountsPage.navigateToAccountsModule();
            accountsPage.navigateToViewAccounts();

            // get the first row account name before clicking
            String firstRowAccountName = accountsPage.getFirstRowNameLocator().getText().trim();

            accountsPage.clickFirstAccount();

            Thread.sleep(2000);

            accountsPage.deleteAccount();
            accountsPage.clickOkInDeleteDialog();

            // wait until return to view account
            Thread.sleep(2000);

            accountsPage.filterQuick(firstRowAccountName, false, false);

            boolean isFilterResultEmpty = accountsPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted account should no longer exist in the accounts list");
            takeElementScreenshot("DEM-007_Deleted_Account_Filter_Result", driver.findElement(accountsPage.getFilterResult()));

            accountsPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }

    }

    @Test
    @Description("DEM-008")
    public void testDem008() {
        login("will", "will");
        AccountsPage accountsPage = new AccountsPage(driver);
        accountsPage.navigateToAccountsModule();
        accountsPage.navigateToImportAccounts();

        ImportAccountsPage importAccountsPage = new ImportAccountsPage(driver);
        boolean isCSV = importAccountsPage.verifyDownloadedTemplateIsCSV(10, "DEM-008_Download_History");
        assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'accounts'");

        // upload file and complete import process
        importAccountsPage.uploadFile("Accounts.csv");

        importAccountsPage.clickImportCreate();
        importAccountsPage.clickNext();
        importAccountsPage.clickNext();
        importAccountsPage.clickNext();
        importAccountsPage.clickImportNow();

        boolean isRecordsImported = importAccountsPage.isRecordsImported();
        assertTrue(isRecordsImported, "Records from Accounts.csv should be imported successfully");
        takeElementScreenshot("DEM-008_Import_Accounts_Success", importAccountsPage.getSummaryElement());

    }

    // @Test
    // public void testNavigateToCreateAccount() {
    //     System.out.println("\n--- Testing: Navigate to Create Account Page ---");
    //     login("will", "will");
    //     AccountsPage accountsPage = new AccountsPage(driver);
    //     accountsPage.navigateToAccountsModule();
    //     accountsPage.navigateToCreateAccount();
    //     boolean isOnCreatePage = accountsPage.checkPageTitle("CREATE");
    //     assertTrue(isOnCreatePage, "Should be on Create Account page with SAVE button and name field visible");
    //     System.out.println("✅ Navigation to Create Account page verified successfully!");
    // }
    // @Test
    // public void testNavigateToViewAccounts() {
    //     System.out.println("\n--- Testing: Navigate to View Accounts Page ---");
    //     login("will", "will");
    //     AccountsPage accountsPage = new AccountsPage(driver);
    //     accountsPage.navigateToAccountsModule();
    //     accountsPage.navigateToViewAccounts();
    //     boolean isOnViewPage = accountsPage.checkPageTitle("ACCOUNTS");
    //     assertTrue(isOnViewPage, "Should be on View Accounts page (URL should contain module=Accounts and action = index)");
    //     System.out.println("✅ Navigation to View Accounts page verified successfully!");
    // }
    // @Test
    // public void testNavigateToImportAccounts() {
    //     System.out.println("\n--- Testing: Navigate to Import Accounts Page ---");
    //     login("will", "will");
    //     AccountsPage accountsPage = new AccountsPage(driver);
    //     accountsPage.navigateToAccountsModule();
    //     accountsPage.navigateToImportAccounts();
    //     boolean isOnImportPage = accountsPage.checkPageTitle("Step 1: Upload Import File");
    //     assertTrue(isOnImportPage,
    //             "Should be on Import Accounts page (URL should contain module=Import or action = Step1)");
    //     System.out.println("✅ Navigation to Import Accounts page verified successfully!");
    // }
    // // ==================== VALIDATION TESTS ====================
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
    //             "Validation message should contain '" + expectedMessage + "'. Actual: "
    //             + messageText);
    //     boolean hasError = createAccountsPage.hasValidationError();
    //     assertTrue(hasError, "Form should show validation error when saving without mandatory fields");
    //     System.out.println("✅ Validation test passed: " + testData.get("testCase"));
    // }
    // // ==================== CREATE ACCOUNT TESTS ====================
    // @Test(dataProvider = "minimalAccountData", dataProviderClass = AccountDataProvider.class)
    // public void testCreateAccountWithMinimalData(Map<String, String> testData) {
    //     String testCase = testData.get("testCase");
    //     System.out.println("\n--- Testing: " + testCase + " ---");
    //     login("will", "will");
    //     AccountsPage accountsPage = new AccountsPage(driver);
    //     accountsPage.navigateToAccountsModule();
    //     accountsPage.navigateToCreateAccount();
    //     CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
    //     createAccountsPage.addInformationFromData(testData);
    //     createAccountsPage.save();
    //     System.out.println(testData.get("name"));
    //     boolean isSaved = createAccountsPage.isAccountSavedSuccessfully(testData.get("name"));
    //     assertTrue(isSaved, "Account with minimal data should be saved successfully");
    //     System.out.println("✅ Account created successfully: " + testData.get("name"));
    // }
    // @Test(dataProvider = "fullAccountData", dataProviderClass = AccountDataProvider.class)
    // public void testCreateAccountWithFullData(Map<String, String> testData) {
    //     String testCase = testData.get("testCase");
    //     System.out.println("\n--- Testing: " + testCase + " ---");
    //     login("will", "will");
    //     AccountsPage accountsPage = new AccountsPage(driver);
    //     accountsPage.navigateToAccountsModule();
    //     accountsPage.navigateToCreateAccount();
    //     CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
    //     createAccountsPage.addInformationFromData(testData);
    //     createAccountsPage.save();
    //     boolean isSaved = createAccountsPage.isAccountSavedSuccessfully(testData.get("name"));
    //     assertTrue(isSaved, "Account with full data should be saved successfully");
    //     System.out.println("✅ Account created successfully: " + testData.get("name"));
    // }
    // @Test(dataProvider = "multipleEmailAccountData", dataProviderClass = AccountDataProvider.class)
    // public void testCreateAccountWithMultipleEmails(Map<String, String> testData) {
    //     String testCase = testData.get("testCase");
    //     System.out.println("\n--- Testing: " + testCase + " ---");
    //     login("will", "will");
    //     AccountsPage accountsPage = new AccountsPage(driver);
    //     accountsPage.navigateToAccountsModule();
    //     accountsPage.navigateToCreateAccount();
    //     CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
    //     createAccountsPage.addInformationFromData(testData);
    //     createAccountsPage.save();
    //     boolean isSaved = createAccountsPage.isAccountSavedSuccessfully(testData.get("name"));
    //     assertTrue(isSaved, "Account with multiple emails should be saved successfully");
    //     System.out.println("✅ Account created successfully: " + testData.get("name"));
    // }
    // // ==================== DUPLICATE ACCOUNT TESTS ====================
    // @Test(dataProvider = "duplicateAccountDataConfirmation", dataProviderClass = AccountDataProvider.class)
    // public void testDuplicateAccountWithDataProvider(Map<String, String> testData) {
    //     String expectedResult = testData.get("expectedResult");
    //     System.out.println("\n--- Testing Duplicate Account Scenario ---");
    //     System.out.println("  → Expected Result: " + expectedResult);
    //     login("will", "will");
    //     AccountsPage accountsPage = new AccountsPage(driver);
    //     accountsPage.navigateToAccountsModule();
    //     accountsPage.navigateToCreateAccount();
    //     CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
    //     createAccountsPage.addInformationFromData(testData);
    //     createAccountsPage.save();
    //     // First account should save successfully without warning
    //     if (expectedResult.equals("success")) {
    //         boolean isSaved = createAccountsPage.isAccountSavedSuccessfully(testData.get("name"));
    //         assertTrue(isSaved, "First account should be saved successfully");
    //         System.out.println("✅ First account created successfully: " + testData.get("name"));
    //     } // Second account should show duplicate warning
    //     else if (expectedResult.equals("asked_confirmation")) {
    //         boolean warningDisplayed = createAccountsPage.isDuplicateWarningDisplayed();
    //         assertTrue(warningDisplayed, "Duplicate warning should be displayed for second account");
    //         System.out.println("✅ Duplicate warning detected as expected");
    //     }
    // }
    // @Test
    // public void testDuplicateAccountWarningAppears() {
    //     System.out.println("\n--- Testing: Duplicate Account Warning Detection ---");
    //     login("will", "will");
    //     AccountsPage accountsPage = new AccountsPage(driver);
    //     // Step 1: Create first account
    //     System.out.println("  → Step 1: Creating first account...");
    //     accountsPage.navigateToAccountsModule();
    //     accountsPage.navigateToCreateAccount();
    //     CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
    //     Map<String, String> firstAccountData = Map.of("name", "Test Account Duplicate Warning");
    //     createAccountsPage.addInformationFromData(firstAccountData);
    //     createAccountsPage.save();
    //     boolean firstAccountSaved = createAccountsPage.isAccountSavedSuccessfully("Test Account Duplicate Warning");
    //     assertTrue(firstAccountSaved, "First account should be created successfully");
    //     System.out.println("  ✓ First account created successfully");
    //     // Step 2: Try to create duplicate account
    //     System.out.println("  → Step 2: Attempting to create duplicate account...");
    //     accountsPage.navigateToAccountsModule();
    //     accountsPage.navigateToCreateAccount();
    //     createAccountsPage = new CreateAccountsPage(driver);
    //     Map<String, String> duplicateData = Map.of("name", "Test Account Duplicate Warning");
    //     createAccountsPage.addInformationFromData(duplicateData);
    //     createAccountsPage.save();
    //     // Step 3: Verify duplicate warning appears
    //     boolean warningDisplayed = createAccountsPage.isDuplicateWarningDisplayed();
    //     assertTrue(warningDisplayed, "Duplicate account warning should be displayed");
    //     System.out.println("  ✓ Duplicate warning message detected");
    //     System.out.println("✅ Duplicate detection test passed!");
    // }
    // @Test
    // public void testCancelDuplicateAccountCreation() {
    //     System.out.println("\n--- Testing: Cancel Duplicate Account Creation ---");
    //     login("will", "will");
    //     AccountsPage accountsPage = new AccountsPage(driver);
    //     // Step 1: Create first account
    //     System.out.println("  → Step 1: Creating first account...");
    //     accountsPage.navigateToAccountsModule();
    //     accountsPage.navigateToCreateAccount();
    //     CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
    //     Map<String, String> firstAccountData = Map.of("name", "Test Account Cancel Duplicate");
    //     createAccountsPage.addInformationFromData(firstAccountData);
    //     createAccountsPage.save();
    //     boolean firstAccountSaved = createAccountsPage.isAccountSavedSuccessfully("Test Account Cancel Duplicate");
    //     assertTrue(firstAccountSaved, "First account should be created successfully");
    //     System.out.println("  ✓ First account created successfully");
    //     // Step 2: Try to create duplicate and verify warning
    //     System.out.println("  → Step 2: Attempting duplicate and canceling...");
    //     accountsPage.navigateToAccountsModule();
    //     accountsPage.navigateToCreateAccount();
    //     createAccountsPage = new CreateAccountsPage(driver);
    //     Map<String, String> duplicateData = Map.of("name", "Test Account Cancel Duplicate");
    //     createAccountsPage.addInformationFromData(duplicateData);
    //     createAccountsPage.save();
    //     // Verify warning appears
    //     boolean warningDisplayed = createAccountsPage.isDuplicateWarningDisplayed();
    //     assertTrue(warningDisplayed, "Duplicate warning should appear before canceling");
    //     System.out.println("  ✓ Duplicate warning appeared");
    //     // Step 3: Cancel the duplicate creation
    //     createAccountsPage.cancel();
    //     System.out.println("  ✓ Clicked Cancel button");
    //     WebElement accountPageTitle = wait.until(ExpectedConditions.presenceOfElementLocated(accountsPage.getPageTitle()));
    //     boolean onAccountsList = accountPageTitle.getText().trim().equalsIgnoreCase("ACCOUNTS");
    //     assertTrue(onAccountsList, "Should be redirected to accounts list after cancel");
    //     System.out.println("✅ Cancel duplicate test passed!");
    // }
    // @Test
    // public void testSaveDuplicateAccountAnyway() {
    //     System.out.println("\n--- Testing: Save Duplicate Account Anyway ---");
    //     login("will", "will");
    //     AccountsPage accountsPage = new AccountsPage(driver);
    //     // Step 1: Create first account
    //     System.out.println("  → Step 1: Creating first account...");
    //     accountsPage.navigateToAccountsModule();
    //     accountsPage.navigateToCreateAccount();
    //     CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
    //     Map<String, String> firstAccountData = Map.of("name", "Test Account Save Duplicate");
    //     createAccountsPage.addInformationFromData(firstAccountData);
    //     createAccountsPage.save();
    //     boolean firstAccountSaved = createAccountsPage.isAccountSavedSuccessfully("Test Account Save Duplicate");
    //     assertTrue(firstAccountSaved, "First account should be created successfully");
    //     System.out.println("  ✓ First account created successfully");
    //     // Step 2: Try to create duplicate
    //     System.out.println("  → Step 2: Creating duplicate account...");
    //     accountsPage.navigateToAccountsModule();
    //     accountsPage.navigateToCreateAccount();
    //     createAccountsPage = new CreateAccountsPage(driver);
    //     Map<String, String> duplicateData = Map.of("name", "Test Account Save Duplicate");
    //     createAccountsPage.addInformationFromData(duplicateData);
    //     createAccountsPage.save();
    //     // Verify warning appears
    //     boolean warningDisplayed = createAccountsPage.isDuplicateWarningDisplayed();
    //     assertTrue(warningDisplayed, "Duplicate warning should appear");
    //     System.out.println("  ✓ Duplicate warning appeared");
    //     // Step 3: Save anyway (clicking save again)
    //     System.out.println("  → Step 3: Confirming save for duplicate...");
    //     createAccountsPage.saveDuplicateAccount();
    //     // Verify duplicate was saved successfully
    //     boolean duplicateSaved = createAccountsPage.isAccountSavedSuccessfully("Test Account Save Duplicate");
    //     assertTrue(duplicateSaved, "Duplicate account should be saved after confirmation");
    //     System.out.println("✅ Save duplicate account test passed!");
    // }
    // @Test
    // public void testImportAccountsCreate() {
    //     System.out.println("\n--- Testing: Import Accounts Functionality ---");
    //     login("will", "will");
    //     AccountsPage accountsPage = new AccountsPage(driver);
    //     accountsPage.navigateToAccountsModule();
    //     accountsPage.navigateToImportAccounts();
    //     ImportAccountsPage importPage = new ImportAccountsPage(driver);
    //     importPage.uploadFile("Accounts.csv");
    //     importPage.clickImportCreate();
    //     importPage.clickNext();
    //     wait.until(ExpectedConditions.visibilityOfElementLocated(
    //             By.xpath("//h2[contains(text(), ' Step 2: Confirm Import File Properties ')]")
    //     ));
    //     assertTrue(importPage.confirmInStep(" Step 2: Confirm Import File Properties "),
    //             "Should be on Step 2: Confirm Import File Properties page after uploading file and clicking Import Create");
    //     importPage.clickNext();
    //     wait.until(ExpectedConditions.visibilityOfElementLocated(
    //             By.xpath("//h2[contains(text(), ' Step 3: Confirm Field Mappings ')]")
    //     ));
    //     assertTrue(importPage.confirmInStep(" Step 3: Confirm Field Mappings "),
    //             "Should be on Step 3: Confirm Field Mappings page after clicking Next on Step 2");
    //     importPage.clickNext();
    //     wait.until(ExpectedConditions.visibilityOfElementLocated(
    //             By.xpath("//h2[contains(text(), ' Step 4: Check for Possible Duplicates ')]")
    //     ));
    //     assertTrue(importPage.confirmInStep(" Step 4: Check for Possible Duplicates "),
    //             "Should be on Step 4: Check for Possible Duplicates page after clicking Next on Step 3");
    //     importPage.clickImportNow();
    //     wait.until(ExpectedConditions.visibilityOfElementLocated(
    //             By.xpath("//h2[contains(text(), ' Step 5: View Import Results ')]")
    //     ));
    //     assertTrue(importPage.confirmInStep(" Step 5: View Import Results "),
    //             "Should be on Step 5: View Import Results page after clicking Import Now on Step 4");
    //     // Assert if the accounts in Accounts.csv are imported successfully
    //     int csvRowCount = importPage.countCSVDataRows("Accounts.csv");
    //     int importedCount = importPage.getImportedRecordsCount();
    //     System.out.println("CSV Data Rows: " + csvRowCount);
    //     System.out.println("Imported Records: " + importedCount);
    //     assertTrue(importedCount == csvRowCount,
    //             "Number of imported records (" + importedCount + ") should match CSV data rows (" + csvRowCount + ")");
    //     importPage.clickExit();
    //     System.out.println("✅ Import Accounts test passed!");
    // }
}
