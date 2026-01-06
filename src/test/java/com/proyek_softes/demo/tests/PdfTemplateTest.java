package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.pdf_templates.CreatePdfTemplatePage;
import com.proyek_softes.demo.pages.pdf_templates.ImportPdfTemplatePage;
import com.proyek_softes.demo.pages.pdf_templates.PdfTemplatesPage;
import com.proyek_softes.demo.utils.PdfTemplateDataProvider;

import io.qameta.allure.Description;

public class PdfTemplateTest extends GenericCrudTestHelper<PdfTemplatesPage, CreatePdfTemplatePage> {

    @Test(dataProvider = "createPdfTemplateData", dataProviderClass = PdfTemplateDataProvider.class)
    @Description("DEM-136")
    public void testDem136(Map<String, String> testData) {
        CreatePdfTemplatePage createPdfTemplatePage = new CreatePdfTemplatePage(driver, wait);
        PdfTemplatesPage pdfTemplatesPage = new PdfTemplatesPage(driver);

        testCreateEntity(
                testData,
                v -> pdfTemplatesPage,
                pdfTemplatesPage::navigateToPdfTemplateModule,
                pdfTemplatesPage::navigateToCreatePdfTemplate,
                v -> createPdfTemplatePage,
                (page, data) -> page.addInformationFromData(data),
                createPdfTemplatePage::save,
                data -> data.get("name"),
                createPdfTemplatePage::isPdfTemplateSavedSuccessfully,
                "DEM-136",
                pdfTemplatesPage::navigateToViewPdfTemplates,
                pdfTemplatesPage::isInFirstRow,
                v -> pdfTemplatesPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewPdfTemplateData", dataProviderClass = PdfTemplateDataProvider.class)
    @Description("DEM-137")
    public void testDem137(Map<String, String> testData) {
        PdfTemplatesPage pdfTemplatesPage = new PdfTemplatesPage(driver);

        testViewEntity(
                testData,
                v -> pdfTemplatesPage,
                pdfTemplatesPage::navigateToPdfTemplateModule,
                pdfTemplatesPage::navigateToViewPdfTemplates,
                pdfTemplatesPage::clickFirstPdfTemplate,
                data -> data.get("name"),
                pdfTemplatesPage::isPdfTemplateTitleCorrect,
                "DEM-137"
        );
    }

    @Test(dataProvider = "editPdfTemplateData", dataProviderClass = PdfTemplateDataProvider.class)
    @Description("DEM-138")
    public void testDem138(Map<String, String> testData) {
        PdfTemplatesPage pdfTemplatesPage = new PdfTemplatesPage(driver);
        CreatePdfTemplatePage createPdfTemplatePage = new CreatePdfTemplatePage(driver, wait);

        testEditEntity(
                testData,
                v -> pdfTemplatesPage,
                pdfTemplatesPage::navigateToPdfTemplateModule,
                pdfTemplatesPage::navigateToViewPdfTemplates,
                pdfTemplatesPage::clickFirstPdfTemplate,
                data -> data.get("nameBeforeEdit"),
                pdfTemplatesPage::isPdfTemplateTitleCorrect,
                "DEM-138_View_PdfTemplate_Detail",
                pdfTemplatesPage::editPdfTemplate,
                v -> createPdfTemplatePage,
                (page, data) -> page.addInformationFromData(data),
                createPdfTemplatePage::save,
                data -> data.get("name"),
                createPdfTemplatePage::isPdfTemplateSavedSuccessfully,
                "DEM-138"
        );
    }

    @Test
    @Description("DEM-139")
    public void testDem139() {
        try {
            login("will", "will");
            PdfTemplatesPage pdfTemplatesPage = new PdfTemplatesPage(driver);
            pdfTemplatesPage.navigateToPdfTemplateModule();
            pdfTemplatesPage.navigateToViewPdfTemplates();
            String firstPdfTemplateName = pdfTemplatesPage.getFirstRowNameLocator().getText().trim();
            pdfTemplatesPage.clickFirstPdfTemplate();
            Thread.sleep(2000);

            pdfTemplatesPage.deletePdfTemplate();
            pdfTemplatesPage.clickOkInDeleteDialog();

            // wait until return to view account
            Thread.sleep(2000);

            pdfTemplatesPage.filterQuick(firstPdfTemplateName, false, false);

            boolean isFilterResultEmpty = pdfTemplatesPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted pdf template should no longer exist in the pdf templates list");
            takeElementScreenshot("DEM-139_Deleted_PdfTemplate_Filter_Result", driver.findElement(pdfTemplatesPage.getFilterResult()));
            pdfTemplatesPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-140")
    public void testDem140() {
        try {
            login("will", "will");
            PdfTemplatesPage pdfTemplatesPage = new PdfTemplatesPage(driver);
            pdfTemplatesPage.navigateToPdfTemplateModule();
            pdfTemplatesPage.navigateToImportPdfTemplates();

            ImportPdfTemplatePage importPdfTemplatePage = new ImportPdfTemplatePage(driver);
            boolean isCSV = importPdfTemplatePage.verifyDownloadedTemplateIsCSV(10, "DEM-140_Download_History");
            assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'PDF - Templates'");

            // upload file and complete import process
            importPdfTemplatePage.uploadFile("PDF - Templates.csv");

            importPdfTemplatePage.clickImportCreate();
            importPdfTemplatePage.clickNext();
            importPdfTemplatePage.clickNext();
            importPdfTemplatePage.clickNext();
            importPdfTemplatePage.clickImportNow();

            boolean isRecordsImported = importPdfTemplatePage.isRecordsImported();
            assertTrue(isRecordsImported, "Records from PDF - Templates.csv should be imported successfully");
            takeElementScreenshot("DEM-140_Import_PDF_Templates_Success", importPdfTemplatePage.getSummaryElement());

        } catch (Exception e) {
        }
    }
}
