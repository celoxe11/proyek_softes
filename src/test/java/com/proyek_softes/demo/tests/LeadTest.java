package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.leads.CreateLeadPage;
import com.proyek_softes.demo.pages.leads.LeadsPage;
import com.proyek_softes.demo.utils.LeadDataProvider;

import io.qameta.allure.Description;

public class LeadTest extends BaseTest {

    @Test(dataProvider = "createLeadData", dataProviderClass = LeadDataProvider.class)
    @Description("DEM-020")
    public void testDem020(Map<String, String> testData) {
        login("will", "will");
        LeadsPage leadsPage = new LeadsPage(driver);
        leadsPage.navigateToLeadsModule();
        leadsPage.navigateToCreateLead();

        CreateLeadPage createLeadPage = new CreateLeadPage(driver, wait);
        createLeadPage.addInformationFromData(testData);
        createLeadPage.save();

        String fullName = testData.get("firstName") + " " + testData.get("lastName");
        boolean isSaved = createLeadPage.isLeadSavedSuccessfully(fullName);
        assertTrue(isSaved, "Lead should be saved successfully");

        takeScreenshot("DEM-020_Create_Lead");

        leadsPage.navigateToViewLeads();
        boolean isInFirstRow = leadsPage.isInFirstRow(testData.get("lastName"));
        assertTrue(isInFirstRow, "Created lead should appear in the first row of leads list");

        takeElementScreenshot("DEM-020_Lead_In_List", leadsPage.getFirstRowLocator());
    }

    @Test(dataProvider = "viewLeadData", dataProviderClass = LeadDataProvider.class)
    @Description("DEM-021")
    public void testDem021(Map<String, String> testData) {
        login("will", "will");
        LeadsPage leadsPage = new LeadsPage(driver);
        leadsPage.navigateToLeadsModule();
        leadsPage.navigateToViewLeads();
        leadsPage.clickFirstLead();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String fullName = testData.get("firstName") + " " + testData.get("lastName");
        boolean isOnLeadDetailPage = leadsPage.isLeadTitleCorrect(fullName);
        assertTrue(isOnLeadDetailPage, "Should be on Lead Detail page for the selected lead");
        takeScreenshot("DEM-021_View_Lead_Detail");
    }

    @Test(dataProvider = "editLeadData", dataProviderClass = LeadDataProvider.class)
    @Description("DEM-022")
    public void testDem022(Map<String, String> testData) {
        login("will", "will");
        LeadsPage leadsPage = new LeadsPage(driver);
        leadsPage.navigateToLeadsModule();
        leadsPage.navigateToViewLeads();
        leadsPage.clickFirstLead();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String fullNameBeforeEdit = testData.get("firstNameBeforeEdit") + " " + testData.get("lastNameBeforeEdit");
        boolean isOnLeadDetailPage = leadsPage.isLeadTitleCorrect(fullNameBeforeEdit);
        assertTrue(isOnLeadDetailPage, "Should be on Lead Detail page for the selected lead");
        takeScreenshot("DEM-022_View_Lead_Detail_Before_Edit");

        leadsPage.editLead();

        CreateLeadPage editLeadPage = new CreateLeadPage(driver, wait);
        editLeadPage.addInformationFromData(testData);
        editLeadPage.save();

        String fullName = testData.get("firstName") + " " + testData.get("lastName");
        boolean isSaved = editLeadPage.isLeadSavedSuccessfully(fullName);
        assertTrue(isSaved, "Lead should be saved successfully after editing");

        takeScreenshot("DEM-022_Edit_Lead");
    }

    @Test
    @Description("DEM-023")
    public void testDem023() {
        try {
            login("will", "will");
            LeadsPage leadsPage = new LeadsPage(driver);
            leadsPage.navigateToLeadsModule();
            leadsPage.navigateToViewLeads();

            String firstRowLeadName = leadsPage.getFirstRowLocator().getText().trim();

            leadsPage.clickFirstLead();

            Thread.sleep(2000);

            leadsPage.deleteLead();
            leadsPage.clickOkInDeleteDialog();

            Thread.sleep(2000);

            leadsPage.filterQuick(firstRowLeadName, false, false, false);

            boolean isFilterResultEmpty = leadsPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted lead should no longer exist in the leads list");
            takeElementScreenshot("DEM-023_Deleted_Lead_Filter_Result", driver.findElement(leadsPage.getFilterResult()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
