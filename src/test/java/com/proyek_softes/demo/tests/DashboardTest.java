package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.DashboardPage;
import com.proyek_softes.demo.pages.ProfilePage;
import com.proyek_softes.demo.pages.WelcomePage;
import com.proyek_softes.demo.utils.DashboardDataProvider;

import io.qameta.allure.Description;

public class DashboardTest extends BaseTest {

    @Test
    @Description("DEM-172")
    public void testDem172() {
        login("will", "will");
        WelcomePage welcomePage = new WelcomePage(driver);
        welcomePage.navigateToAboutPage();
        boolean isAboutHeadingVisible = welcomePage.isInAboutPage();
        assertTrue(isAboutHeadingVisible, "About SuiteCRM heading should be visible");
        takeScreenshot("DEM-172_About_Page");
    }

    @Test
    @Description("DEM-175")
    public void testDem175() {
        login8("will", "will");
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.waitForPageToLoad();
        dashboardPage.clickAddDashlets();
        dashboardPage.clickMyNotesDashlet();
        dashboardPage.closeModal();
        boolean hasMyNotes = dashboardPage.hasMyNotesDashlet();
        assertTrue(hasMyNotes, "My Notes dashlet should be added to the dashboard");
        takeScreenshot("DEM-175_Dashboard_with_My_Notes_Dashlet");
    }

    @Test(dataProvider = "addTabData", dataProviderClass = DashboardDataProvider.class)
    @Description("DEM-176")
    public void testDem176(Map<String, String> testData) {
        login8("will", "will");
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.waitForPageToLoad();
        dashboardPage.clickAddTab();
        dashboardPage.fillAddDashboardForm(testData);
        dashboardPage.confirmAddDashboard();
        dashboardPage.slow();

        dashboardPage.waitAndClickNewTab();
        dashboardPage.slow();

        dashboardPage.clickAddDashlets();
        dashboardPage.slow();
        
        dashboardPage.chooseChartAndSelectOpportunity();
        dashboardPage.slow();

        dashboardPage.closeModal();
        dashboardPage.slow();
        
        boolean hasOpportunityChart = dashboardPage.hasOpportunityChart();
        assertTrue(hasOpportunityChart, "Opportunity chart should be added to the dashboard");
        takeScreenshot("DEM-176_Dashboard_with_Opportunity_Chart");
    }

    @Test(dataProvider = "editTabData", dataProviderClass = DashboardDataProvider.class)
    @Description("DEM-177") 
    public void testDem177(Map<String, String> testData) {
        login8("will", "will");
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.waitForPageToLoad();
        dashboardPage.clickAddTab();
        dashboardPage.fillAddDashboardForm(testData);
        dashboardPage.confirmAddDashboard();
        dashboardPage.slow();

        dashboardPage.clickEditTab();
        dashboardPage.slow();

        dashboardPage.removeLastDashboardTab();
        dashboardPage.slow();

        dashboardPage.closeEditModal();
        dashboardPage.slow();

        boolean hasOnlyDefaultAndTestDashboard = dashboardPage.hasOnlyDefaultAndTestDashboard();
        assertTrue(hasOnlyDefaultAndTestDashboard, "Only default and test dashboard should be visible");
        takeScreenshot("DEM-177_Dashboard_with_Only_Default_and_Test_Dashboard");
    }

    @Test(dataProvider = "searchData", dataProviderClass = DashboardDataProvider.class)
    @Description("DEM-178")
    public void testDem178(Map<String, String> testData) {
        login8("will", "will");
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.waitForPageToLoad();

        dashboardPage.searchDashboard(testData.get("searchTerm"));
        dashboardPage.slow();

        boolean hasSearchResults = dashboardPage.hasExpectedSearchResults();
        assertTrue(hasSearchResults, "Search results should be visible");
        takeScreenshot("DEM-178_Dashboard_with_Search_Results");
    }

    @Test(dataProvider="editProfileData", dataProviderClass = DashboardDataProvider.class)
    @Description("DEM-179")
    public void testDem179(Map<String, String> testData) {
        login("will", "will");
        WelcomePage welcomePage = new WelcomePage(driver);
        welcomePage.navigateToProfilePage();

        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.addInformationFromData(testData);
        profilePage.clickSaveButton();
        try {
            Thread.sleep(2000); // Wait for save to complete
        } catch (InterruptedException e) {
        }
        
        boolean isSaved = profilePage.isProfileSavedSuccessfully(testData.get("first_name") + " " + testData.get("last_name"));
        assertTrue(isSaved, "Profile should be saved successfully");
        takeScreenshot("DEM-179_Profile_After_Saving");
    }

}
