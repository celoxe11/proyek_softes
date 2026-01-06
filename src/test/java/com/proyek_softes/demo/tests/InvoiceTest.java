package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.invoices.CreateInvoicePage;
import com.proyek_softes.demo.pages.invoices.ImportInvoicePage;
import com.proyek_softes.demo.pages.invoices.ImportLineItemPage;
import com.proyek_softes.demo.pages.invoices.InvoicesPage;
import com.proyek_softes.demo.utils.InvoiceDataProvider;

import io.qameta.allure.Description;

public class InvoiceTest extends GenericCrudTestHelper<InvoicesPage, CreateInvoicePage> {

    @Test(dataProvider = "createInvoiceData", dataProviderClass = InvoiceDataProvider.class)
    @Description("DEM-080")
    public void testDem080(Map<String, String> testData) {
        InvoicesPage invoicesPage = new InvoicesPage(driver);
        CreateInvoicePage createInvoicePage = new CreateInvoicePage(driver, wait);

        testCreateEntity(
                testData,
                v -> invoicesPage,
                invoicesPage::navigateToInvoicesModule,
                invoicesPage::navigateToCreateInvoice,
                v -> createInvoicePage,
                (page, data) -> page.addInformationFromData(data),
                createInvoicePage::save,
                data -> data.get("name"),
                createInvoicePage::isInvoiceSavedSuccessfully,
                "DEM-080",
                invoicesPage::navigateToViewInvoice,
                invoicesPage::isInFirstRow,
                v -> invoicesPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewInvoiceData", dataProviderClass = InvoiceDataProvider.class)
    @Description("DEM-081")
    public void testDem081(Map<String, String> testData) {
        InvoicesPage invoicesPage = new InvoicesPage(driver);

        testViewEntity(
                testData,
                v -> invoicesPage,
                invoicesPage::navigateToInvoicesModule,
                invoicesPage::navigateToViewInvoice,
                invoicesPage::clickFirstInvoice,
                data -> data.get("name"),
                invoicesPage::isInvoiceTitleCorrect,
                "DEM-081"
        );
    }

    @Test(dataProvider = "editInvoiceData", dataProviderClass = InvoiceDataProvider.class)
    @Description("DEM-082")
    public void testDem082(Map<String, String> testData) {
        InvoicesPage invoicesPage = new InvoicesPage(driver);
        CreateInvoicePage createInvoicePage = new CreateInvoicePage(driver, wait);

        testEditEntity(
                testData,
                v -> invoicesPage,
                invoicesPage::navigateToInvoicesModule,
                invoicesPage::navigateToViewInvoice,
                invoicesPage::clickFirstInvoice,
                data -> data.get("nameBeforeEdit"),
                invoicesPage::isInvoiceTitleCorrect,
                "DEM-082_View_Invoice_Detail",
                invoicesPage::editInvoice,
                v -> createInvoicePage,
                (page, data) -> page.addInformationFromData(data),
                createInvoicePage::save,
                data -> data.get("name"),
                createInvoicePage::isInvoiceSavedSuccessfully,
                "DEM-082"
        );
    }

    @Test
    @Description("DEM-083")
    public void testDem083() {
        try {
            login("will", "will");
            InvoicesPage invoicesPage = new InvoicesPage(driver);
            invoicesPage.navigateToInvoicesModule();
            invoicesPage.navigateToViewInvoice();
            String firstInvoiceName = invoicesPage.getFirstRowNameLocator().getText().trim();
            invoicesPage.clickFirstInvoice();
            Thread.sleep(2000);

            invoicesPage.deleteInvoice();
            invoicesPage.clickOkInDeleteDialog();

            // wait until return to view account
            Thread.sleep(2000);

            invoicesPage.filterQuick(firstInvoiceName, false, false);

            boolean isFilterResultEmpty = invoicesPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted invoice should no longer exist in the invoices list");
            takeElementScreenshot("DEM-083_Deleted_Invoice_Filter_Result", driver.findElement(invoicesPage.getFilterResult()));
            invoicesPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-084")
    public void testDem084() {
        try {
            login("will", "will");
            InvoicesPage invoicesPage = new InvoicesPage(driver);
            invoicesPage.navigateToInvoicesModule();
            invoicesPage.navigateToImportInvoice();

            ImportInvoicePage importInvoicePage = new ImportInvoicePage(driver);
            boolean isCSV = importInvoicePage.verifyDownloadedTemplateIsCSV(10, "DEM-084_Download_History");
            assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'invoices'");

            // upload file and complete import process
            importInvoicePage.uploadFile("Invoices.csv");

            importInvoicePage.clickImportCreate();
            importInvoicePage.clickNext();
            importInvoicePage.clickNext();
            importInvoicePage.clickNext();
            importInvoicePage.clickImportNow();

            boolean isRecordsImported = importInvoicePage.isRecordsImported();
            assertTrue(isRecordsImported, "Records from Invoices.csv should be imported successfully");
            takeElementScreenshot("DEM-084_Import_Invoices_Success", importInvoicePage.getSummaryElement());

        } catch (Throwable e) {
            takeScreenshot("DEM-084_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }

    @Test
    @Description("DEM-085")
    public void testDem085() {
        try {
            login("will", "will");
            InvoicesPage invoicesPage = new InvoicesPage(driver);
            invoicesPage.navigateToInvoicesModule();
            invoicesPage.navigateToImportLineItems();

            ImportLineItemPage importLineItemPage = new ImportLineItemPage(driver);
            boolean isCSV = importLineItemPage.verifyDownloadedTemplateIsCSV(10, "DEM-085_Download_History");
            assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'Line Items'");

            // upload file and complete import process
            importLineItemPage.uploadFile("Invoices_Line_Items.csv");

            importLineItemPage.clickImportCreate();
            importLineItemPage.clickNext();
            importLineItemPage.clickNext();
            importLineItemPage.clickNext();
            importLineItemPage.clickImportNow();

            boolean isRecordsImported = importLineItemPage.isRecordsImported();
            assertTrue(isRecordsImported, "Records from Invoices_Line_Items.csv should be imported successfully");
            takeElementScreenshot("DEM-085_Import_Line_Items_Success", importLineItemPage.getSummaryElement());

        } catch (Throwable e) {
            takeScreenshot("DEM-085_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }
}
