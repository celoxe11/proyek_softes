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

public class LeadTest extends GenericCrudTestHelper<LeadsPage, CreateLeadPage> {

    @Test(dataProvider = "createLeadData", dataProviderClass = LeadDataProvider.class)
    @Description("DEM-020")
    public void testDem020(Map<String, String> testData) {
        try {
            LeadsPage leadsPage = new LeadsPage(driver);
            CreateLeadPage createLeadPage = new CreateLeadPage(driver, wait);
            
            testCreateEntity(
                testData,
                v -> leadsPage,
                leadsPage::navigateToLeadsModule,
                leadsPage::navigateToCreateLead,
                v -> createLeadPage,
                (page, data) -> page.addInformationFromData(data),
                createLeadPage::save,
                data -> data.get("firstName") + " " + data.get("lastName"),
                name -> createLeadPage.isLeadSavedSuccessfully(name),
                "DEM-020",
                leadsPage::navigateToViewLeads,
                name -> leadsPage.isInFirstRow(testData.get("lastName")),
                v -> leadsPage.getFirstRowLocator()
            );
        } catch (Throwable e) {
            takeScreenshot("DEM-020_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }

    @Test(dataProvider = "viewLeadData", dataProviderClass = LeadDataProvider.class)
    @Description("DEM-021")
    public void testDem021(Map<String, String> testData) {
        try {
            LeadsPage leadsPage = new LeadsPage(driver);
            
            testViewEntity(
                testData,
                v -> leadsPage,
                leadsPage::navigateToLeadsModule,
                leadsPage::navigateToViewLeads,
                leadsPage::clickFirstLead,
                data -> data.get("firstName") + " " + data.get("lastName"),
                leadsPage::isLeadTitleCorrect,
                "DEM-021"
            );
        } catch (Throwable e) {
            takeScreenshot("DEM-021_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }

    @Test(dataProvider = "editLeadData", dataProviderClass = LeadDataProvider.class)
    @Description("DEM-022")
    public void testDem022(Map<String, String> testData) {
        try {
            LeadsPage leadsPage = new LeadsPage(driver);
            CreateLeadPage editLeadPage = new CreateLeadPage(driver, wait);
            
            testEditEntity(
                testData,
                v -> leadsPage,
                leadsPage::navigateToLeadsModule,
                leadsPage::navigateToViewLeads,
                leadsPage::clickFirstLead,
                data -> data.get("firstNameBeforeEdit") + " " + data.get("lastNameBeforeEdit"),
                leadsPage::isLeadTitleCorrect,
                "DEM-022_View_Lead_Detail",
                leadsPage::editLead,
                v -> editLeadPage,
                (page, data) -> page.addInformationFromData(data),
                editLeadPage::save,
                data -> data.get("firstName") + " " + data.get("lastName"),
                editLeadPage::isLeadSavedSuccessfully,
                "DEM-022"
            );
        } catch (Throwable e) {
            takeScreenshot("DEM-022_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
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
