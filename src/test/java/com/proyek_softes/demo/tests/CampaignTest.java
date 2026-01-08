package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.campaign.CampaignsPage;
import com.proyek_softes.demo.pages.campaign.CreateCampaignEmailPage;
import com.proyek_softes.demo.pages.campaign.CreateCampaignNewsletterPage;
import com.proyek_softes.demo.pages.campaign.CreateCampaignNonEmailPage;
import com.proyek_softes.demo.pages.campaign.CreateCampaignSurveyPage;
import com.proyek_softes.demo.pages.campaign.CreatePersonFormPage;
import com.proyek_softes.demo.pages.campaign.NavigationChecker;
import com.proyek_softes.demo.utils.CampaignDataProvider;

import io.qameta.allure.Description;

public class CampaignTest extends GenericCrudTestHelper<CampaignsPage, CreateCampaignNewsletterPage> {

    @Test(dataProvider = "createCampaignNewsletterData", dataProviderClass = CampaignDataProvider.class)
    @Description("DEM-048")
    public void testDem048(Map<String, String> testData) {
        try {
            login("will", "will");
            CampaignsPage campaignsPage = new CampaignsPage(driver);
            campaignsPage.navigateToCampaignsModule();
            campaignsPage.navigateToCreateCampaign();
            campaignsPage.selectNewsletterCampaign();

            CreateCampaignNewsletterPage createCampaignNewsletterPage = new CreateCampaignNewsletterPage(driver);
            createCampaignNewsletterPage.addInformationFromData(testData);
            createCampaignNewsletterPage.save();

            boolean isCampaignCreated = createCampaignNewsletterPage.isCampaignSavedSuccessfully(testData.get("name"));
            assertTrue(isCampaignCreated, "Campaign should be created successfully");
            takeScreenshot("DEM-048_Create_Campaign_Newsletter");

            campaignsPage.navigateToViewCampaign();
            boolean isInFirstRow = campaignsPage.isInFirstRow(testData.get("name"));
            assertTrue(isInFirstRow, "Created campaign should appear in the first row");
            takeScreenshot("DEM-048_Created_Campaign_Newsletter_In_List");
        } catch (Exception e) {
            System.out.println("❌ Test failed with unexpected error: " + e.getMessage());
            e.printStackTrace();
            takeScreenshot("DEM-048_FAILED_Unexpected_Error");
            throw new AssertionError("Test failed with unexpected error - " + e.getMessage(), e);
        }

    }

    @Test(dataProvider = "createCampaignEmailData", dataProviderClass = CampaignDataProvider.class)
    @Description("DEM-049")
    public void testDem049(Map<String, String> testData) {
        try {
            login("will", "will");
            CampaignsPage campaignsPage = new CampaignsPage(driver);
            campaignsPage.navigateToCampaignsModule();
            campaignsPage.navigateToCreateCampaign();
            campaignsPage.selectEmailCampaign();

            CreateCampaignEmailPage createCampaignEmailPage = new CreateCampaignEmailPage(driver);
            createCampaignEmailPage.addInformationFromData(testData);
            createCampaignEmailPage.save();

            boolean isCampaignCreated = createCampaignEmailPage.isCampaignSavedSuccessfully(testData.get("name"));
            assertTrue(isCampaignCreated, "Campaign should be created successfully");
            takeScreenshot("DEM-049_Create_Campaign_Email");

            campaignsPage.navigateToViewCampaign();
            boolean isInFirstRow = campaignsPage.isInFirstRow(testData.get("name"));
            assertTrue(isInFirstRow, "Created campaign should appear in the first row");
            takeScreenshot("DEM-049_Created_Campaign_Email_In_List");
        } catch (Exception e) {
            System.out.println("❌ Test failed with unexpected error: " + e.getMessage());
            e.printStackTrace();
            takeScreenshot("DEM-049_FAILED_Unexpected_Error");
            throw new AssertionError("Test failed with unexpected error - " + e.getMessage(), e);
        }
    }

    @Test(dataProvider = "createCampaignNonEmailData", dataProviderClass = CampaignDataProvider.class)
    @Description("DEM-050")
    public void testDem050(Map<String, String> testData) {
        try {
            login("will", "will");
            CampaignsPage campaignsPage = new CampaignsPage(driver);
            campaignsPage.navigateToCampaignsModule();
            campaignsPage.navigateToCreateCampaign();
            campaignsPage.selectNonEmailCampaign();

            CreateCampaignNonEmailPage createCampaignNonEmailPage = new CreateCampaignNonEmailPage(driver);
            createCampaignNonEmailPage.addInformationFromData(testData);

            String targetMessage = createCampaignNonEmailPage.getTargetMessage();
            assertNotNull(targetMessage, "Target message should not be null");
            takeScreenshot("DEM-050_Create_Campaign_Non_Email");

            campaignsPage.navigateToViewCampaign();
            boolean isInFirstRow = campaignsPage.isInFirstRow(testData.get("name"));
            assertTrue(isInFirstRow, "Created campaign should appear in the first row");
            takeScreenshot("DEM-050_Created_Campaign_Non_Email_In_List");
        } catch (Exception e) {
            System.out.println("❌ Test failed with unexpected error: " + e.getMessage());
            e.printStackTrace();
            takeScreenshot("DEM-050_FAILED_Unexpected_Error");
            throw new AssertionError("Test failed with unexpected error - " + e.getMessage(), e);
        }
    }

    @Test(dataProvider = "createCampaignSurveyData", dataProviderClass = CampaignDataProvider.class)
    @Description("DEM-051")
    public void testDem051(Map<String, String> testData) {
        try {
            login("will", "will");
            // SurveysPage surveysPage = new SurveysPage(driver);
            // surveysPage.navigateToSurveysModule();
            // surveysPage.createNewSurveyFromData(testData);

            CampaignsPage campaignsPage = new CampaignsPage(driver);
            campaignsPage.navigateToCampaignsModule();
            campaignsPage.navigateToCreateCampaign();
            campaignsPage.selectSurveyCampaign();

            CreateCampaignSurveyPage createCampaignSurveyPage = new CreateCampaignSurveyPage(driver);
            createCampaignSurveyPage.addInformationFromData(testData);
            createCampaignSurveyPage.save();

            boolean isCampaignCreated = createCampaignSurveyPage.isCampaignSavedSuccessfully(testData.get("name"));
            assertTrue(isCampaignCreated, "Campaign should be created successfully");
            takeScreenshot("DEM-051_Create_Campaign_Survey");

            campaignsPage.navigateToViewCampaign();
            boolean isInFirstRow = campaignsPage.isInFirstRow(testData.get("name"));
            assertTrue(isInFirstRow, "Created campaign should appear in the first row");
            takeScreenshot("DEM-051_Created_Campaign_Survey_In_List");
        } catch (Exception e) {
            System.out.println("❌ Test failed with unexpected error: " + e.getMessage());
            e.printStackTrace();
            takeScreenshot("DEM-051_FAILED_Unexpected_Error");
            throw new AssertionError("Test failed with unexpected error - " + e.getMessage(), e);
        }
    }

    @Test(dataProvider = "viewCampaignData", dataProviderClass = CampaignDataProvider.class)
    @Description("DEM-052")
    public void testDem052(Map<String, String> testData) {
        try {
            login("will", "will");
            CampaignsPage campaignsPage = new CampaignsPage(driver);
            campaignsPage.navigateToCampaignsModule();
            campaignsPage.navigateToViewCampaign();

            campaignsPage.findAndClickCampaign(testData.get("name"));

            Thread.sleep(2000);

            boolean isTitleCorrect = campaignsPage.isCampaignTitleCorrect(testData.get("name"));
            assertTrue(isTitleCorrect, "Campaign title should be correct when viewing campaign details");
            takeScreenshot("DEM-052_View_Campaign_Details");
        } catch (InterruptedException e) {
        }
    }

    @Test(dataProvider = "editCampaignData", dataProviderClass = CampaignDataProvider.class)
    @Description("DEM-053")
    public void testDem053(Map<String, String> testData) {
        try {
            login("will", "will");
            CampaignsPage campaignsPage = new CampaignsPage(driver);
            campaignsPage.navigateToCampaignsModule();
            campaignsPage.navigateToViewCampaign();

            campaignsPage.findAndClickCampaign(testData.get("nameBeforeEdit"));

            Thread.sleep(2000);

            boolean isTitleCorrect = campaignsPage.isCampaignTitleCorrect(testData.get("nameBeforeEdit"));
            assertTrue(isTitleCorrect, "Campaign title should be correct when viewing campaign details");
            takeScreenshot("DEM-053_Before_Edit_Campaign");

            campaignsPage.editCampaign(testData);

            // Refresh page to see updated values
            driver.navigate().refresh();
            Thread.sleep(2000);

            // Verify the campaign title is still correct after edit
            boolean isTitleStillCorrect = campaignsPage.isCampaignTitleCorrect(testData.get("name"));
            assertTrue(isTitleStillCorrect, "Campaign title should still be correct after editing");
            takeScreenshot("DEM-053_Edit_Campaign_Verified");
        } catch (InterruptedException e) {
            System.out.println("❌ Test failed with unexpected error: " + e.getMessage());
            e.printStackTrace();
            takeScreenshot("DEM-053_FAILED_Unexpected_Error");
            throw new AssertionError("Test failed with unexpected error - " + e.getMessage(), e);
        }
    }

    @Test
    @Description("DEM-054")
    public void testDem054() {
        try {
            login("will", "will");
            CampaignsPage campaignsPage = new CampaignsPage(driver);
            campaignsPage.navigateToCampaignsModule();
            campaignsPage.navigateToViewCampaign();
            String firstCampaignName = campaignsPage.getFirstRowNameLocator().getText().trim();
            campaignsPage.clickFirstCampaign();
            Thread.sleep(2000);

            campaignsPage.deleteCampaign();
            campaignsPage.clickOkInDeleteDialog();

            // wait until return to view account
            Thread.sleep(2000);

            campaignsPage.filterQuick(firstCampaignName, false);

            boolean isFilterResultEmpty = campaignsPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted campaign should no longer exist in the campaigns list");
            takeElementScreenshot("DEM-054_Deleted_Campaign_Filter_Result",
                    driver.findElement(campaignsPage.getFilterResult()));
            campaignsPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-055")
    public void testDem055() {
        login("will", "will");
        CampaignsPage campaignsPage = new CampaignsPage(driver);
        campaignsPage.navigateToCampaignsModule();
        campaignsPage.navigateToCreateEmailTemplate();

        NavigationChecker navigationChecker = new NavigationChecker(driver);
        boolean isTabCorrect = navigationChecker.isCurrentTabCorrect("Email - Templates");
        boolean isOnEmailTemplatePage = navigationChecker.isModulePageTitleCorrect("Create");
        assertTrue((isOnEmailTemplatePage && isTabCorrect), "User should be on Email Template page");
        takeScreenshot("DEM-055_Email_Template_Page_Access");
    }

    @Test
    @Description("DEM-056")
    public void testDem056() {
        login("will", "will");
        CampaignsPage campaignsPage = new CampaignsPage(driver);
        campaignsPage.navigateToCampaignsModule();
        campaignsPage.navigateToViewEmailTemplate();

        NavigationChecker navigationChecker = new NavigationChecker(driver);
        boolean isOnEmailTemplatePage = navigationChecker.isPageCorrect("Email - Templates", "Email - Templates");
        assertTrue(isOnEmailTemplatePage, "User should be on Email Template page");
        takeScreenshot("DEM-056_Email_Template_Page_Access");
    }

    @Test
    @Description("DEM-057")
    public void testDem057() {
        login("will", "will");
        CampaignsPage campaignsPage = new CampaignsPage(driver);
        campaignsPage.navigateToCampaignsModule();
        campaignsPage.navigateToViewDiagnostics();

        NavigationChecker navigationChecker = new NavigationChecker(driver);
        boolean isTabCorrect = navigationChecker.isCurrentTabCorrect("Campaigns");
        boolean isOnDiagnosticsPage = navigationChecker.isModulePageTitleCorrect("Campaign Diagnostics");
        assertTrue((isOnDiagnosticsPage && isTabCorrect), "User should be on Campaign Diagnostics page");
        takeScreenshot("DEM-057_Campaign_Diagnostics_Page_Access");
    }

    @Test(dataProvider = "createPersonFormData", dataProviderClass = CampaignDataProvider.class)
    @Description("DEM-058")
    public void testDem058(Map<String, String> testData) {
        try {
            login("will", "will");
            CampaignsPage campaignsPage = new CampaignsPage(driver);
            campaignsPage.navigateToCampaignsModule();
            campaignsPage.navigateToCreateCampaign();
            campaignsPage.selectNonEmailCampaign();

            CreateCampaignNonEmailPage createCampaignNonEmailPage = new CreateCampaignNonEmailPage(driver);
            createCampaignNonEmailPage.addInformationFromData(testData);

            campaignsPage.navigateToCreatePersonForm();

            CreatePersonFormPage createPersonFormPage = new CreatePersonFormPage(driver);
            createPersonFormPage.clickAddAllFields();
            Thread.sleep(600);
            createPersonFormPage.clickNext();
            Thread.sleep(600);

            createPersonFormPage.addInformationFromData(testData);
            createPersonFormPage.clickGenerateForm();

            Thread.sleep(1000);

            createPersonFormPage.clickSaveWebForm();
            Thread.sleep(1000);

            createPersonFormPage.clickWebToPersonFormLink();
            Thread.sleep(2000);

            boolean isFileNameCorrect = createPersonFormPage.verifyDownloadedWebToLeadForm();
            assertTrue(isFileNameCorrect, "Downloaded web to lead form file name should be correct");
            createPersonFormPage.takeScreenshotOfDownloadsPage("DEM-058_Downloaded_Web_To_Lead_Form_Verification");

        } catch (InterruptedException e) {
        }
    }

}
