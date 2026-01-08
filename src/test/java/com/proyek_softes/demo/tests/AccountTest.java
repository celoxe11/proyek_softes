package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.accounts.AccountsPage;
import com.proyek_softes.demo.pages.accounts.CreateAccountsPage;
import com.proyek_softes.demo.pages.accounts.ImportAccountsPage;
import com.proyek_softes.demo.utils.AccountDataProvider;

import io.qameta.allure.Description;

public class AccountTest extends GenericCrudTestHelper<AccountsPage, CreateAccountsPage> {

    @Test(dataProvider = "createAccountData", dataProviderClass = AccountDataProvider.class)
    @Description("DEM-004")
    public void testDem004(Map<String, String> testData) {
        try {
            AccountsPage accountsPage = new AccountsPage(driver);
            CreateAccountsPage createAccountsPage = new CreateAccountsPage(driver);
            
            testCreateEntity(
                testData,
                v -> accountsPage,
                accountsPage::navigateToAccountsModule,
                accountsPage::navigateToCreateAccount,
                v -> createAccountsPage,
                (page, data) -> page.addInformationFromData(data),
                createAccountsPage::save,
                data -> data.get("name"),
                createAccountsPage::isAccountSavedSuccessfully,
                "DEM-004",
                accountsPage::navigateToViewAccounts,
                accountsPage::isInFirstRow,
                v -> accountsPage.getFirstRowLocator()
            );
        } catch (Throwable e) {
            takeScreenshot("DEM-004_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }

    @Test(dataProvider = "viewAccountData", dataProviderClass = AccountDataProvider.class)
    @Description("DEM-005")
    public void testDem005(Map<String, String> testData) {
        try {
            AccountsPage accountsPage = new AccountsPage(driver);
            
            testViewEntity(
                testData,
                v -> accountsPage,
                accountsPage::navigateToAccountsModule,
                accountsPage::navigateToViewAccounts,
                accountsPage::clickFirstAccount,
                data -> data.get("name"),
                accountsPage::isAccountTitleCorrect,
                "DEM-005"
            );
        } catch (Throwable e) {
            takeScreenshot("DEM-005_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }

    @Test(dataProvider = "editAccountData", dataProviderClass = AccountDataProvider.class)
    @Description("DEM-006")
    public void testDem006(Map<String, String> testData) {
        try {
            AccountsPage accountsPage = new AccountsPage(driver);
            CreateAccountsPage editAccountsPage = new CreateAccountsPage(driver);
            
            testEditEntity(
                testData,
                v -> accountsPage,
                accountsPage::navigateToAccountsModule,
                accountsPage::navigateToViewAccounts,
                accountsPage::clickFirstAccount,
                data -> data.get("name_before_edit"),
                accountsPage::isAccountTitleCorrect,
                "DEM-006_View_Account_Detail",
                accountsPage::editAccount,
                v -> editAccountsPage,
                (page, data) -> page.addInformationFromData(data),
                editAccountsPage::save,
                data -> data.get("name"),
                editAccountsPage::isAccountSavedSuccessfully,
                "DEM-006"
            );
        } catch (Throwable e) {
            takeScreenshot("DEM-006_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
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
}
