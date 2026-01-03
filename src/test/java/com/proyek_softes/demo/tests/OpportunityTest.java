package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.opportunity.CreateOpportunityPage;
import com.proyek_softes.demo.pages.opportunity.ImportOpportunityPage;
import com.proyek_softes.demo.pages.opportunity.OpportunityPage;
import com.proyek_softes.demo.utils.OpportunityDataProvider;

import io.qameta.allure.Description;

public class OpportunityTest extends GenericCrudTestHelper<OpportunityPage, CreateOpportunityPage> {

    @Test(dataProvider = "createOpportunityData", dataProviderClass = OpportunityDataProvider.class)
    @Description("DEM-015")
    public void testDem015(Map<String, String> testData) {
        OpportunityPage opportunityPage = new OpportunityPage(driver);
        CreateOpportunityPage createOpportunityPage = new CreateOpportunityPage(driver, wait);
        
        testCreateEntity(
            testData,
            v -> opportunityPage,
            opportunityPage::navigateToOpportunitiesModule,
            opportunityPage::navigateToCreateOpportunity,
            v -> createOpportunityPage,
            (page, data) -> page.addInformationFromData(data),
            createOpportunityPage::save,
            data -> data.get("name"),
            createOpportunityPage::isOpportunitySavedSuccessfully,
            "DEM-015",
            opportunityPage::navigateToViewOpportunities,
            opportunityPage::isInFirstRow,
            v -> opportunityPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewOpportunityData", dataProviderClass = OpportunityDataProvider.class)
    @Description("DEM-016")
    public void testDem016(Map<String, String> testData) {
        OpportunityPage opportunityPage = new OpportunityPage(driver);
        
        testViewEntity(
            testData,
            v -> opportunityPage,
            opportunityPage::navigateToOpportunitiesModule,
            opportunityPage::navigateToViewOpportunities,
            opportunityPage::clickFirstOpportunity,
            data -> data.get("name"),
            opportunityPage::isOpportunityTitleCorrect,
            "DEM-016"
        );
    }

    @Test(dataProvider = "editOpportunityData", dataProviderClass = OpportunityDataProvider.class)
    @Description("DEM-017")
    public void testDem017(Map<String, String> testData) {
        OpportunityPage opportunityPage = new OpportunityPage(driver);
        CreateOpportunityPage editOpportunityPage = new CreateOpportunityPage(driver, wait);
        
        testEditEntity(
            testData,
            v -> opportunityPage,
            opportunityPage::navigateToOpportunitiesModule,
            opportunityPage::navigateToViewOpportunities,
            opportunityPage::clickFirstOpportunity,
            data -> data.get("nameBeforeEdit"),
            opportunityPage::isOpportunityTitleCorrect,
            "DEM-017_View_Opportunity_Detail",
            opportunityPage::editOpportunity,
            v -> editOpportunityPage,
            (page, data) -> page.addInformationFromData(data),
            editOpportunityPage::save,
            data -> data.get("name"),
            editOpportunityPage::isOpportunitySavedSuccessfully,
            "DEM-017"
        );
    }

    @Test
    @Description("DEM-018")
    public void testDem018() {
        try {
            login("will", "will");
            OpportunityPage opportunityPage = new OpportunityPage(driver);
            opportunityPage.navigateToOpportunitiesModule();
            opportunityPage.navigateToViewOpportunities();

            String firstRowOpportunityName = opportunityPage.getFirstRowLocator().getText().trim();

            opportunityPage.clickFirstOpportunity();

            Thread.sleep(2000);

            opportunityPage.deleteOpportunity();
            opportunityPage.clickOkInDeleteDialog();

            Thread.sleep(2000);

            opportunityPage.filterQuick(firstRowOpportunityName, false, false);

            boolean isFilterResultEmpty = opportunityPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted opportunity should no longer exist in the opportunities list");
            takeElementScreenshot("DEM-018_Deleted_Opportunity_Filter_Result", driver.findElement(opportunityPage.getFilterResult()));

            opportunityPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-019")
    public void testDem019() {
        login("will", "will");
        OpportunityPage opportunityPage = new OpportunityPage(driver);
        opportunityPage.navigateToOpportunitiesModule();
        opportunityPage.navigateToImportOpportunities();

        ImportOpportunityPage importOpportunityPage = new ImportOpportunityPage(driver);
        boolean isCSV = importOpportunityPage.verifyDownloadedTemplateIsCSV(10, "DEM-019_Download_History");
        assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'opportunities'");

        // upload file and complete import process
        importOpportunityPage.uploadFile("Opportunities.csv");

        importOpportunityPage.clickImportCreate();
        importOpportunityPage.clickNext();
        importOpportunityPage.clickNext();
        importOpportunityPage.clickNext();
        importOpportunityPage.clickImportNow();

        boolean isRecordsImported = importOpportunityPage.isRecordsImported();
        assertTrue(isRecordsImported, "Records from Opportunities.csv should be imported successfully");
        takeElementScreenshot("DEM-019_Import_Opportunities_Success", importOpportunityPage.getSummaryElement());

    }
}
