package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.documents.CreateDocumentPage;
import com.proyek_softes.demo.pages.documents.DocumentsPage;
import com.proyek_softes.demo.utils.DocumentDataProvider;

import io.qameta.allure.Description;

public class DocumentTest extends GenericCrudTestHelper<DocumentsPage, CreateDocumentPage> {

    /**
     * DEM-036
     * Documents - Create Document
     */
    @Test(dataProvider = "createDocumentData", dataProviderClass = DocumentDataProvider.class)
    @Description("DEM-036")
    public void testDem036(Map<String, String> testData) {
        DocumentsPage documentsPage = new DocumentsPage(driver);
        CreateDocumentPage createDocumentPage = new CreateDocumentPage(driver, wait);

        testCreateEntity(
            testData,
            v -> documentsPage,
            documentsPage::navigateToDocumentsModule,
            documentsPage::navigateToCreateDocument,
            v -> createDocumentPage,
            (page, data) -> page.addInformationFromData(data),
            createDocumentPage::save,
            data -> data.get("document_name"),
            createDocumentPage::isDocumentSavedSuccessfully,
            "DEM-036",
            documentsPage::navigateToViewDocument,
            documentsPage::isInFirstRow,
            v -> documentsPage.getFirstRowLocator()
        );
    }

    /**
     * DEM-037
     * Documents - View
     */
    @Test(dataProvider = "viewDocumentData", dataProviderClass = DocumentDataProvider.class)
    @Description("DEM-037")
    public void testDem037(Map<String, String> testData) {
        DocumentsPage documentsPage = new DocumentsPage(driver);

        testViewEntity(
            testData,
            v -> documentsPage,
            documentsPage::navigateToDocumentsModule,
            documentsPage::navigateToViewDocument,
            documentsPage::clickFirstDocument,
            data -> data.get("document_name"),
            documentsPage::isDocumentTitleCorrect,
            "DEM-037"
        );
    }

    /**
     * DEM-038
     * Documents - View (Edit)
     */
    @Test(dataProvider = "editDocumentData", dataProviderClass = DocumentDataProvider.class)
    @Description("DEM-038")
    public void testDem038(Map<String, String> testData) {
        DocumentsPage documentsPage = new DocumentsPage(driver);
        CreateDocumentPage editDocumentPage = new CreateDocumentPage(driver, wait);

        testEditEntity(
            testData,
            v -> documentsPage,
            documentsPage::navigateToDocumentsModule,
            documentsPage::navigateToViewDocument,
            documentsPage::clickFirstDocument,
            data -> data.get("documentNameBeforeEdit"),
            documentsPage::isDocumentTitleCorrect,
            "DEM-038_View_Document_Detail",
            documentsPage::editDocument,
            v -> editDocumentPage,
            (page, data) -> page.addInformationFromData(data),
            editDocumentPage::save,
            data -> data.get("document_name"),
            editDocumentPage::isDocumentSavedSuccessfully,
            "DEM-038"
        );
    }

    /**
     * DEM-039
     * Documents - View (Delete)
     */
    @Test
    @Description("DEM-039")
    public void testDem039() {
        try {
            login("will", "will");
            DocumentsPage documentsPage = new DocumentsPage(driver);

            documentsPage.navigateToDocumentsModule();
            documentsPage.navigateToViewDocument();

            String firstRowDocumentName = documentsPage
                    .getFirstRowNameLocator()
                    .getText()
                    .trim();

            documentsPage.clickFirstDocument();
            Thread.sleep(2000);

            documentsPage.deleteDocument();
            documentsPage.clickOkInDeleteDialog();
            Thread.sleep(2000);

            documentsPage.filterQuick(firstRowDocumentName, false);

            boolean isFilterResultEmpty = documentsPage.isFilterResultEmpty();
            assertTrue(
                isFilterResultEmpty,
                "Deleted document should no longer exist in the documents list"
            );

            takeElementScreenshot(
                "DEM-039_Deleted_Document_Filter_Result",
                driver.findElement(documentsPage.getFilterResult())
            );

            documentsPage.checkAndClearFilter();
        } catch (Throwable e) {
            takeScreenshot("DEM-039_Error");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }
}
