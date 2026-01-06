package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.reports.CreateReportPage;
import com.proyek_softes.demo.pages.reports.ImportReportPage;
import com.proyek_softes.demo.pages.reports.ReportsPage;
import com.proyek_softes.demo.utils.ReportDataProvider;

import io.qameta.allure.Description;

public class ReportTest extends GenericCrudTestHelper<ReportsPage, CreateReportPage> {

    @Test(dataProvider = "createReportData", dataProviderClass = ReportDataProvider.class)
    @Description("DEM-141")
    public void testDem141(Map<String, String> testData) {
        CreateReportPage createReportPage = new CreateReportPage(driver, wait);
        ReportsPage reportsPage = new ReportsPage(driver);

        testCreateEntity(
                testData,
                v -> reportsPage,
                reportsPage::navigateToReportModule,
                reportsPage::navigateToCreateReport,
                v -> createReportPage,
                (page, data) -> page.addInformationFromData(data),
                createReportPage::save,
                data -> data.get("name"),
                createReportPage::isReportSavedSuccessfully,
                "DEM-141",
                reportsPage::navigateToViewReports,
                reportsPage::isInFirstRow,
                v -> reportsPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewReportData", dataProviderClass = ReportDataProvider.class)
    @Description("DEM-142")
    public void testDem142(Map<String, String> testData) {
        ReportsPage reportsPage = new ReportsPage(driver);

        testViewEntity(
                testData,
                v -> reportsPage,
                reportsPage::navigateToReportModule,
                reportsPage::navigateToViewReports,
                reportsPage::clickFirstReport,
                data -> data.get("name"),
                reportsPage::isReportTitleCorrect,
                "DEM-142"
        );
    }

    @Test(dataProvider = "editReportData", dataProviderClass = ReportDataProvider.class)
    @Description("DEM-143")
    public void testDem143(Map<String, String> testData) {
        ReportsPage reportsPage = new ReportsPage(driver);
        CreateReportPage createReportPage = new CreateReportPage(driver, wait);

        testEditEntity(
                testData,
                v -> reportsPage,
                reportsPage::navigateToReportModule,
                reportsPage::navigateToViewReports,
                reportsPage::clickFirstReport,
                data -> data.get("nameBeforeEdit"),
                reportsPage::isReportTitleCorrect,
                "DEM-143_View_Report_Detail",
                reportsPage::navigateToEditReport,
                v -> createReportPage,
                (page, data) -> page.addInformationFromData(data),
                createReportPage::save,
                data -> data.get("name"),
                createReportPage::isReportSavedSuccessfully,
                "DEM-143"
        );
    }

    @Test
    @Description("DEM-144")
    public void testDem144() {
        try {
            login("will", "will");
            ReportsPage reportsPage = new ReportsPage(driver);
            reportsPage.navigateToReportModule();
            reportsPage.navigateToViewReports();
            String firstReportName = reportsPage.getFirstRowNameLocator().getText().trim();
            reportsPage.clickFirstReport();
            Thread.sleep(2000);

            reportsPage.deleteReport();
            reportsPage.clickOkInDeleteDialog();
            // wait until return to view account
            Thread.sleep(2000);

            reportsPage.filterQuick(firstReportName, false);

            boolean isFilterResultEmpty = reportsPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted report should no longer exist in the reports list");
            takeElementScreenshot("DEM-144_Deleted_Report_Filter_Result", driver.findElement(reportsPage.getFilterResult()));
            reportsPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-145")
    public void testDem145() {
        login("will", "will");
        ReportsPage reportsPage = new ReportsPage(driver);
        reportsPage.navigateToReportModule();
        reportsPage.navigateToImportReports();

        ImportReportPage importReportPage = new ImportReportPage(driver);
        boolean isCSV = importReportPage.verifyDownloadedTemplateIsCSV(10, "DEM-145_Download_History");
        assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'Reports'");

        // upload file and complete import process
        importReportPage.uploadFile("Reports.csv");

        importReportPage.clickImportCreate();
        importReportPage.clickNext();
        importReportPage.clickNext();
        importReportPage.clickNext();
        importReportPage.clickImportNow();

        boolean isRecordsImported = importReportPage.isRecordsImported();
        assertTrue(isRecordsImported, "Records from Reports.csv should be imported successfully");
        takeElementScreenshot("DEM-145_Import_Reports_Success", importReportPage.getSummaryElement());
    }
}
