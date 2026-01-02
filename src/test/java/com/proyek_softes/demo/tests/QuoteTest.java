package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.quotes.CreateQuotePage;
import com.proyek_softes.demo.pages.quotes.ImportQuotePage;
import com.proyek_softes.demo.pages.quotes.QuotesPage;
import com.proyek_softes.demo.utils.QuoteDataProvider;

import io.qameta.allure.Description;

public class QuoteTest extends BaseTest {

    @Test(dataProvider = "createQuoteData", dataProviderClass = QuoteDataProvider.class)
    @Description("DEM-026")
    public void testDem026(Map<String, String> testData) {
        login("will", "will");
        QuotesPage quotesPage = new QuotesPage(driver);
        quotesPage.navigateToQuotesModule();
        quotesPage.navigateToCreateQuote();

        CreateQuotePage createQuotePage = new CreateQuotePage(driver, wait);
        createQuotePage.addInformationFromData(testData);
        createQuotePage.save();

        String quoteName = testData.get("name");
        boolean isSaved = createQuotePage.isQuoteSavedSuccessfully(quoteName);
        assertTrue(isSaved, "Quote with minimal data should be saved successfully");

        takeScreenshot("DEM-026_Create_Quote");

        quotesPage.navigateToViewQuote();
        boolean isInFirstRow = quotesPage.isInFirstRow(quoteName);
        assertTrue(isInFirstRow, "Created quote should appear in the first row of quotes list");

        takeElementScreenshot("DEM-026_Quote_In_List", quotesPage.getFirstRowLocator());
    }

    @Test(dataProvider = "viewQuoteData", dataProviderClass = QuoteDataProvider.class)
    @Description("DEM-027")
    public void testDem027(Map<String, String> testData) {
        try {
            login("will", "will");
            QuotesPage quotesPage = new QuotesPage(driver);
            quotesPage.navigateToQuotesModule();
            quotesPage.navigateToViewQuote();

            quotesPage.clickFirstQuote();

            Thread.sleep(2000);

            String quoteName = testData.get("name");
            boolean isOnQuoteDetailPage = quotesPage.isQuoteTitleCorrect(quoteName);
            assertTrue(isOnQuoteDetailPage, "Should be on Quote Detail page for the selected quote");
            takeScreenshot("DEM-027_View_Quote_Detail");
        } catch (InterruptedException e) {
        }
    }

    @Test(dataProvider = "editQuoteData", dataProviderClass = QuoteDataProvider.class)
    @Description("DEM-028")
    public void testDem028(Map<String, String> testData) {
        try {
            login("will", "will");
            QuotesPage quotesPage = new QuotesPage(driver);
            quotesPage.navigateToQuotesModule();
            quotesPage.navigateToViewQuote();

            quotesPage.clickFirstQuote();

            Thread.sleep(2000);

            String quoteNameBeforeEdit = testData.get("nameBeforeEdit");
            boolean isOnQuoteDetailPage = quotesPage.isQuoteTitleCorrect(quoteNameBeforeEdit);
            assertTrue(isOnQuoteDetailPage, "Should be on Quote Detail page for the selected quote");
            takeScreenshot("DEM-028_View_Quote_Detail_Before_Edit");

            quotesPage.editQuote();

            CreateQuotePage editQuotePage = new CreateQuotePage(driver, wait);
            editQuotePage.addInformationFromData(testData);
            editQuotePage.save();

            String quoteName = testData.get("name");
            boolean isSaved = editQuotePage.isQuoteSavedSuccessfully(quoteName);
            assertTrue(isSaved, "Quote should be saved successfully after editing");

            takeScreenshot("DEM-028_Edit_Quote");
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-029")
    public void testDem029() {
        try {
            login("will", "will");
            QuotesPage quotesPage = new QuotesPage(driver);
            quotesPage.navigateToQuotesModule();
            quotesPage.navigateToViewQuote();

            String firstRowQuoteName = quotesPage.getFirstRowNameLocator().getText().trim();

            quotesPage.clickFirstQuote();

            Thread.sleep(2000);

            quotesPage.deleteQuote();
            quotesPage.clickOkInDeleteDialog();

            Thread.sleep(2000);

            quotesPage.filterQuick(firstRowQuoteName, false, false);

            boolean isFilterResultEmpty = quotesPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted quote should no longer exist in the quotes list");
            takeElementScreenshot("DEM-029_Deleted_Quote_Filter_Result", driver.findElement(quotesPage.getFilterResult()));

            quotesPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-030")
    public void testDem030() {
        try {
            login("will", "will");
            QuotesPage quotesPage = new QuotesPage(driver);
            quotesPage.navigateToQuotesModule();
            quotesPage.navigateToImportQuote();

            ImportQuotePage importQuotePage = new ImportQuotePage(driver);
            importQuotePage.uploadFile("quotes_import.csv");
            importQuotePage.clickNextButton();
            
            Thread.sleep(2000);
            
            importQuotePage.clickImportButton();

            boolean isImportSuccessful = importQuotePage.isImportSuccessful();
            assertTrue(isImportSuccessful, "Quote should be imported successfully");
            takeScreenshot("DEM-030_Import_Quote");

            quotesPage.navigateToViewQuote();

            String importedQuoteName = "Imported Quote Sample";
            boolean isInFirstRow = quotesPage.isInFirstRow(importedQuoteName);
            assertTrue(isInFirstRow, "Imported quote should appear in the quotes list");

            takeElementScreenshot("DEM-030_Imported_Quote_In_List", quotesPage.getFirstRowLocator());
        } catch (InterruptedException e) {
        }
    }
}

