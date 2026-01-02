package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.leads.CreateLeadPage;
import com.proyek_softes.demo.pages.leads.ImportLeadPage;
import com.proyek_softes.demo.pages.leads.ImportLeadVCardPage;
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
            

            String firstRowLeadName = leadsPage.getFirstRowNameLocator().getText().trim();

            leadsPage.clickFirstLead();

            Thread.sleep(2000);

            leadsPage.deleteLead();
            leadsPage.clickOkInDeleteDialog();

            Thread.sleep(2000);

            leadsPage.filterQuick(firstRowLeadName, false, false, false);

            boolean isFilterResultEmpty = leadsPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted lead should no longer exist in the leads list");
            takeElementScreenshot("DEM-023_Deleted_Lead_Filter_Result", driver.findElement(leadsPage.getFilterResult()));

            leadsPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-024")
    public void testDem024() {
        login("will", "will");
        LeadsPage leadsPage = new LeadsPage(driver);
        leadsPage.navigateToLeadsModule();
        leadsPage.navigateToImportVCard();

        ImportLeadVCardPage importLeadVCardPage = new ImportLeadVCardPage(driver);
        importLeadVCardPage.uploadFile("Leads_vCard.vcf");
        importLeadVCardPage.clickImportButton();

        boolean isLeadSavedSuccessfully = importLeadVCardPage.isLeadSavedSuccessfully("John Smith");
        assertTrue(isLeadSavedSuccessfully, "Lead should be saved successfully after importing");
        takeScreenshot("DEM-024_Import_Lead_VCard");

        leadsPage.navigateToViewLeads();

        String importedLeadName = "John Smith";
        boolean isInFirstRow = leadsPage.isInFirstRow(importedLeadName);
        assertTrue(isInFirstRow, "Imported lead should appear in the leads list");

        takeElementScreenshot("DEM-024_Imported_Lead_In_List", leadsPage.getFirstRowLocator());
    }

    @Test
    @Description("DEM-025")
    public void testDem025() {
        login("will", "will");
        LeadsPage leadsPage = new LeadsPage(driver);
        leadsPage.navigateToLeadsModule();
        leadsPage.navigateToImportLeads();

        ImportLeadPage importLeadPage = new ImportLeadPage(driver);
        boolean isCSV = importLeadPage.verifyDownloadedTemplateIsCSV(10, "DEM-025_Download_History");
        assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'leads'");

        // upload file and complete import process
        importLeadPage.uploadFile("Leads.csv");

        importLeadPage.clickImportCreate();
        importLeadPage.clickNext();
        importLeadPage.clickNext();
        importLeadPage.clickNext();
        importLeadPage.clickImportNow();

        boolean isRecordsImported = importLeadPage.isRecordsImported();
        assertTrue(isRecordsImported, "Records from Leads.csv should be imported successfully");
        takeElementScreenshot("DEM-025_Import_Leads_Success", importLeadPage.getSummaryElement());
    }

    
}
