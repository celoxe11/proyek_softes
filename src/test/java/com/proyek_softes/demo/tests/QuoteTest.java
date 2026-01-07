package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.quotes.CreateQuotePage;
import com.proyek_softes.demo.pages.quotes.ImportQuotePage;
import com.proyek_softes.demo.pages.quotes.QuotesPage;
import com.proyek_softes.demo.utils.QuoteDataProvider;
import com.proyek_softes.demo.pages.invoices.ImportLineItemPage;

import io.qameta.allure.Description;

public class QuoteTest extends GenericCrudTestHelper<QuotesPage, CreateQuotePage> {

    @Test(dataProvider = "createQuoteData", dataProviderClass = QuoteDataProvider.class)
    @Description("DEM-026")
    public void testDem026(Map<String, String> testData) {
        QuotesPage quotesPage = new QuotesPage(driver);
        CreateQuotePage createQuotePage = new CreateQuotePage(driver, wait);

        testCreateEntity(
                testData,
                v -> quotesPage,
                quotesPage::navigateToQuotesModule,
                quotesPage::navigateToCreateQuote,
                v -> createQuotePage,
                (page, data) -> page.addInformationFromData(data),
                createQuotePage::save,
                data -> data.get("name"),
                createQuotePage::isQuoteSavedSuccessfully,
                "DEM-026",
                quotesPage::navigateToViewQuote,
                quotesPage::isInFirstRow,
                v -> quotesPage.getFirstRowLocator());
    }

    @Test(dataProvider = "viewQuoteData", dataProviderClass = QuoteDataProvider.class)
    @Description("DEM-027")
    public void testDem027(Map<String, String> testData) {
        QuotesPage quotesPage = new QuotesPage(driver);

        testViewEntity(
                testData,
                v -> quotesPage,
                quotesPage::navigateToQuotesModule,
                quotesPage::navigateToViewQuote,
                quotesPage::clickFirstQuote,
                data -> data.get("name"),
                quotesPage::isQuoteTitleCorrect,
                "DEM-027");
    }

    @Test(dataProvider = "editQuoteData", dataProviderClass = QuoteDataProvider.class)
    @Description("DEM-028")
    public void testDem028(Map<String, String> testData) {
        QuotesPage quotesPage = new QuotesPage(driver);
        CreateQuotePage editQuotePage = new CreateQuotePage(driver, wait);

        testEditEntity(
                testData,
                v -> quotesPage,
                quotesPage::navigateToQuotesModule,
                quotesPage::navigateToViewQuote,
                quotesPage::clickFirstQuote,
                data -> data.get("nameBeforeEdit"),
                quotesPage::isQuoteTitleCorrect,
                "DEM-028_View_Quote_Detail",
                quotesPage::editQuote,
                v -> editQuotePage,
                (page, data) -> page.addInformationFromData(data),
                editQuotePage::save,
                data -> data.get("name"),
                editQuotePage::isQuoteSavedSuccessfully,
                "DEM-028");
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
            takeElementScreenshot("DEM-029_Deleted_Quote_Filter_Result",
                    driver.findElement(quotesPage.getFilterResult()));

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

    @Test
    @Description("DEM-031")
    public void testDem031() {
        try {
            login("will", "will");
            QuotesPage quotesPage = new QuotesPage(driver);
            quotesPage.navigateToQuotesModule();
            quotesPage.navigateToImportLineItems();

            ImportLineItemPage importLineItemPage = new ImportLineItemPage(driver);
            boolean isCSV = importLineItemPage.verifyDownloadedTemplateIsCSV(10, "DEM-031_Download_History");
            assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'Line Items'");

            // upload file and complete import process
            // Using overloaded method to specify folder
            importLineItemPage.uploadFile("quote_demo", "Quotes_Line_Items.csv");

            importLineItemPage.clickImportCreate();
            importLineItemPage.clickNext();
            importLineItemPage.clickNext();
            importLineItemPage.clickNext();
            importLineItemPage.clickImportNow();

            boolean isRecordsImported = importLineItemPage.isRecordsImported();
            assertTrue(isRecordsImported, "Records from Quotes_Line_Items.csv should be imported successfully");
            takeElementScreenshot("DEM-031_Import_Line_Items_Success", importLineItemPage.getSummaryElement());

        } catch (Throwable e) {
            takeScreenshot("DEM-031_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }
}
