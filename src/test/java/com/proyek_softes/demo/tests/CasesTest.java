package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.cases.CasesPage;
import com.proyek_softes.demo.pages.cases.CreateCasePage;
import com.proyek_softes.demo.pages.cases.ImportCasePage;
import com.proyek_softes.demo.utils.CaseDataProvider;

import io.qameta.allure.Description;

public class CasesTest extends GenericCrudTestHelper<CasesPage, CreateCasePage> {

    @Test(dataProvider = "createCaseData", dataProviderClass = CaseDataProvider.class)
    @Description("DEM-091")
    public void testDem091(Map<String, String> testData) {
        CasesPage casesPage = new CasesPage(driver);
        CreateCasePage createCasePage = new CreateCasePage(driver, wait);

        testCreateEntity(
                testData,
                v -> casesPage,
                casesPage::navigateToCasesModule,
                casesPage::navigateToCreateCase,
                v -> createCasePage,
                (page, data) -> page.addInformationFromData(data),
                createCasePage::save,
                data -> data.get("name"),
                createCasePage::isCaseSavedSuccessfully,
                "DEM-091",
                casesPage::navigateToViewCase,
                casesPage::isInFirstRow,
                v -> casesPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewCaseData", dataProviderClass = CaseDataProvider.class)
    @Description("DEM-092")
    public void testDem092(Map<String, String> testData
    ) {
        CasesPage casesPage = new CasesPage(driver);
        testViewEntity(
                testData,
                v -> casesPage,
                casesPage::navigateToCasesModule,
                casesPage::navigateToViewCase,
                casesPage::clickFirstCase,
                data -> data.get("name"),
                casesPage::isCaseTitleCorrect,
                "DEM-092"
        );
    }

    @Test(dataProvider = "editCaseData", dataProviderClass = CaseDataProvider.class)
    @Description("DEM-093")
    public void testDem093(Map<String, String> testData
    ) {
        CasesPage casesPage = new CasesPage(driver);
        CreateCasePage editCasePage = new CreateCasePage(driver, wait);

        testEditEntity(
                testData,
                v -> casesPage,
                casesPage::navigateToCasesModule,
                casesPage::navigateToViewCase,
                casesPage::clickFirstCase,
                data -> data.get("nameBeforeEdit"),
                casesPage::isCaseTitleCorrect,
                "DEM-093_View_Case_Detail",
                casesPage::editCase,
                v -> editCasePage,
                (page, data) -> page.addInformationFromData(data),
                editCasePage::save,
                data -> data.get("name"),
                editCasePage::isCaseSavedSuccessfully,
                "DEM-093"
        );
    }

    @Test
    @Description("DEM-094")
    public void testDem094() {
        try {
            login("will", "will");
            CasesPage casesPage = new CasesPage(driver);
            casesPage.navigateToCasesModule();
            casesPage.navigateToViewCase();

            // get the first row case name before clicking
            String firstRowCaseName = casesPage.getFirstRowNameLocator().getText().trim();

            casesPage.clickFirstCase();

            Thread.sleep(2000);

            casesPage.deleteCase();
            casesPage.clickOkInDeleteDialog();

            // wait until return to view case
            Thread.sleep(2000);

            casesPage.filterQuick(firstRowCaseName, false, false, false);

            boolean isFilterResultEmpty = casesPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted case should no longer exist in the cases list");
            takeElementScreenshot("DEM-094_Deleted_Case_Filter_Result", driver.findElement(casesPage.getFilterResult()));
            casesPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-095")
    public void testDem095() {
        login("will", "will");
        CasesPage casesPage = new CasesPage(driver);
        casesPage.navigateToCasesModule();
        casesPage.navigateToImportCase();

        ImportCasePage importCasePage = new ImportCasePage(driver);
        boolean isCSV = importCasePage.verifyDownloadedTemplateIsCSV(10, "DEM-095_Download_History");
        assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'cases'");

        // upload file and complete import process
        importCasePage.uploadFile("Cases.csv");

        importCasePage.clickImportCreate();
        importCasePage.clickNext();
        importCasePage.clickNext();
        importCasePage.clickNext();
        importCasePage.clickImportNow();
        
        boolean isRecordsImported = importCasePage.isRecordsImported();
        assertTrue(isRecordsImported, "Records from Cases.csv should be imported successfully");
        takeElementScreenshot("DEM-095_Import_Cases_Success", importCasePage.getSummaryElement());

    }
}
