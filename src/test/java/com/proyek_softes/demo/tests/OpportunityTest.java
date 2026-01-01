package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.opportunity.CreateOpportunityPage;
import com.proyek_softes.demo.pages.opportunity.OpportunityPage;
import com.proyek_softes.demo.utils.OpportunityDataProvider;

import io.qameta.allure.Description;

public class OpportunityTest extends BaseTest {

    @Test(dataProvider = "createOpportunityData", dataProviderClass = OpportunityDataProvider.class)
    @Description("DEM-015")
    public void testDem015(Map<String, String> testData) {
        login("will", "will");
        OpportunityPage opportunityPage = new OpportunityPage(driver);
        opportunityPage.navigateToOpportunitiesModule();
        opportunityPage.navigateToCreateOpportunity();

        CreateOpportunityPage createOpportunityPage = new CreateOpportunityPage(driver, wait);
        createOpportunityPage.addInformationFromData(testData);
        createOpportunityPage.save();

        boolean isSaved = createOpportunityPage.isOpportunitySavedSuccessfully(testData.get("name"));
        assertTrue(isSaved, "Opportunity with minimal data should be saved successfully");

        takeScreenshot("DEM-015_Create_Opportunity");

        opportunityPage.navigateToViewOpportunities();
        boolean isInFirstRow = opportunityPage.isInFirstRow(testData.get("name"));
        assertTrue(isInFirstRow, "Created opportunity should appear in the first row of opportunities list");

        takeElementScreenshot("DEM-015_Opportunity_In_List", opportunityPage.getFirstRowLocator());
    }

    @Test(dataProvider = "viewOpportunityData", dataProviderClass = OpportunityDataProvider.class)
    @Description("DEM-016")
    public void testDem016(Map<String, String> testData) {
        login("will", "will");
        OpportunityPage opportunityPage = new OpportunityPage(driver);
        opportunityPage.navigateToOpportunitiesModule();
        opportunityPage.navigateToViewOpportunities();
        opportunityPage.clickFirstOpportunity();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean isOnOpportunityDetailPage = opportunityPage.isOpportunityTitleCorrect(testData.get("name"));
        assertTrue(isOnOpportunityDetailPage, "Should be on Opportunity Detail page for the selected opportunity");
        takeScreenshot("DEM-016_View_Opportunity_Detail");
    }

    @Test(dataProvider = "editOpportunityData", dataProviderClass = OpportunityDataProvider.class)
    @Description("DEM-017")
    public void testDem017(Map<String, String> testData) {
        login("will", "will");
        OpportunityPage opportunityPage = new OpportunityPage(driver);
        opportunityPage.navigateToOpportunitiesModule();
        opportunityPage.navigateToViewOpportunities();
        opportunityPage.clickFirstOpportunity();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean isOnOpportunityDetailPage = opportunityPage.isOpportunityTitleCorrect(testData.get("nameBeforeEdit"));
        assertTrue(isOnOpportunityDetailPage, "Should be on Opportunity Detail page for the selected opportunity");
        takeScreenshot("DEM-017_View_Opportunity_Detail_Before_Edit");

        opportunityPage.editOpportunity();

        CreateOpportunityPage editOpportunityPage = new CreateOpportunityPage(driver, wait);
        editOpportunityPage.addInformationFromData(testData);
        editOpportunityPage.save();

        boolean isSaved = editOpportunityPage.isOpportunitySavedSuccessfully(testData.get("name"));
        assertTrue(isSaved, "Opportunity should be saved successfully after editing");

        takeScreenshot("DEM-017_Edit_Opportunity");
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
