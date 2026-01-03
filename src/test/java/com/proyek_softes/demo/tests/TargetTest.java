package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.targets.CreateTargetPage;
import com.proyek_softes.demo.pages.targets.ImportTargetPage;
import com.proyek_softes.demo.pages.targets.TargetsPage;
import com.proyek_softes.demo.utils.TargetDataProvider;

import io.qameta.allure.Description;

public class TargetTest extends GenericCrudTestHelper<TargetsPage, CreateTargetPage> {

    @Test(dataProvider = "createTargetData", dataProviderClass = TargetDataProvider.class)
    @Description("DEM-096")
    public void testDem096(Map<String, String> testData) {
        TargetsPage targetsPage = new TargetsPage(driver);  
        CreateTargetPage createTargetPage = new CreateTargetPage(driver, wait);
        testCreateEntity(
                testData,
                v -> targetsPage,
                targetsPage::navigateToTargetsModule,
                targetsPage::navigateToCreateTarget,
                v -> createTargetPage,
                (page, data) -> page.addInformationFromData(data),
                createTargetPage::save,
                data -> data.get("first_name") + " " + data.get("last_name"),
                createTargetPage::isTargetSavedSuccessfully,
                "DEM-096",
                targetsPage::navigateToViewTarget,
                targetsPage::isInFirstRow,
                v -> targetsPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewTargetData", dataProviderClass = TargetDataProvider.class)
    @Description("DEM-097")
    public void testDem097(Map<String, String> testData
    ) {
        TargetsPage targetsPage = new TargetsPage(driver);
        testViewEntity(
                testData,
                v -> targetsPage,
                targetsPage::navigateToTargetsModule,
                targetsPage::navigateToViewTarget,
                targetsPage::clickFirstTarget,
                data -> data.get("first_name") + " " + data.get("last_name"),
                targetsPage::isTargetTitleCorrect,
                "DEM-097"
        );
    }

    @Test(dataProvider = "editTargetData", dataProviderClass = TargetDataProvider.class)
    @Description("DEM-098")
    public void testDem098(Map<String, String> testData
    ) {
        TargetsPage targetsPage = new TargetsPage(driver);
        CreateTargetPage editTargetPage = new CreateTargetPage(driver, wait);

        testEditEntity(
                testData,
                v -> targetsPage,
                targetsPage::navigateToTargetsModule,
                targetsPage::navigateToViewTarget,
                targetsPage::clickFirstTarget,
                 data -> data.get("firstNameBeforeEdit") + " " + data.get("lastNameBeforeEdit"),
                targetsPage::isTargetTitleCorrect,
                "DEM-098_View_Target_Detail",
                targetsPage::editTarget,
                v -> editTargetPage,
                (page, data) -> page.addInformationFromData(data),
                editTargetPage::save,
                data -> data.get("first_name") + " " + data.get("last_name"),
                editTargetPage::isTargetSavedSuccessfully,
                "DEM-098"
        );
    }

    @Test
    @Description("DEM-099")
    public void testDem099() {
        try {
            login("will", "will");
            TargetsPage targetsPage = new TargetsPage(driver);
            targetsPage.navigateToTargetsModule();
            targetsPage.navigateToViewTarget();

            // get the first row case name before clicking
            String firstRowCaseName = targetsPage.getFirstRowNameLocator().getText().trim();

            targetsPage.clickFirstTarget();

            Thread.sleep(2000);

            targetsPage.deleteTarget();
            targetsPage.clickOkInDeleteDialog();

            // wait until return to view case
            Thread.sleep(2000);

            targetsPage.filterQuick(firstRowCaseName, false, false, false);

            boolean isFilterResultEmpty = targetsPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted case should no longer exist in the cases list");
            takeElementScreenshot("DEM-099_Deleted_Case_Filter_Result", driver.findElement(targetsPage.getFilterResult()));
            
            targetsPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-100")
    public void testDem100() {
        login("will", "will");
        TargetsPage targetsPage = new TargetsPage(driver);
        targetsPage.navigateToTargetsModule();
        targetsPage.navigateToImportTarget();

        ImportTargetPage importTargetPage = new ImportTargetPage(driver);
        boolean isCSV = importTargetPage.verifyDownloadedTemplateIsCSV(10, "DEM-100_Download_History");
        assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'targets'");

        // upload file and complete import process
        importTargetPage.uploadFile("Targets.csv");

        importTargetPage.clickImportCreate();
        importTargetPage.clickNext();
        importTargetPage.clickNext();
        importTargetPage.clickNext();
        importTargetPage.clickImportNow();

        boolean isRecordsImported = importTargetPage.isRecordsImported();
        assertTrue(isRecordsImported, "Records from Targets.csv should be imported successfully");
        takeElementScreenshot("DEM-100_Import_Targets_Success", importTargetPage.getSummaryElement());

    }
}
