package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.spot.CreateSpotsPage;
import com.proyek_softes.demo.pages.spot.SpotsPage;
import com.proyek_softes.demo.utils.SpotDataProvider;

import io.qameta.allure.Description;

public class SpotTest extends BaseTest {

    @Test(dataProvider = "createSpotData", dataProviderClass = SpotDataProvider.class)
    @Description("DEM-044")
    public void testDem044(Map<String, String> testData) {
        login("will", "will");
        SpotsPage spotsPage = new SpotsPage(driver);
        spotsPage.navigateToSpotsModule();
        spotsPage.navigateToCreateSpot();

        CreateSpotsPage createSpotsPage = new CreateSpotsPage(driver, wait);
        createSpotsPage.addInformationFromData(testData);
        createSpotsPage.save();

        String spotName = testData.get("name");
        boolean isSaved = createSpotsPage.isSpotSavedSuccessfully(spotName);
        assertTrue(isSaved, "Spot should be saved successfully");

        takeScreenshot("DEM-044_Create_Spot");

        spotsPage.navigateToViewSpot();
        boolean isInFirstRow = spotsPage.isInFirstRow(spotName);
        assertTrue(isInFirstRow, "Created spot should appear in the first row of spots list");

        takeElementScreenshot("DEM-044_Spot_In_List", spotsPage.getFirstRowLocator());
    }

    @Test(dataProvider = "viewSpotData", dataProviderClass = SpotDataProvider.class)
    @Description("DEM-045")
    public void testDem045(Map<String, String> testData) {
        try {
            login("will", "will");
            SpotsPage spotsPage = new SpotsPage(driver);
            spotsPage.navigateToSpotsModule();
            spotsPage.navigateToViewSpot();

            spotsPage.clickFirstSpot();

            Thread.sleep(2000);

            String spotName = testData.get("name");
            boolean isOnSpotDetailPage = spotsPage.isSpotTitleCorrect(spotName);
            assertTrue(isOnSpotDetailPage, "Should be on Spot Detail page for the selected spot");
            takeScreenshot("DEM-045_View_Spot_Detail");
        } catch (InterruptedException e) {
        }
    }

    @Test(dataProvider = "editSpotData", dataProviderClass = SpotDataProvider.class)
    @Description("DEM-046")
    public void testDem046(Map<String, String> testData) {
        try {
            login("will", "will");
            SpotsPage spotsPage = new SpotsPage(driver);
            spotsPage.navigateToSpotsModule();
            spotsPage.navigateToViewSpot();

            spotsPage.clickFirstSpot();

            Thread.sleep(2000);

            String spotNameBeforeEdit = testData.get("nameBeforeEdit");
            boolean isOnSpotDetailPage = spotsPage.isSpotTitleCorrect(spotNameBeforeEdit);
            assertTrue(isOnSpotDetailPage, "Should be on Spot Detail page for the selected spot");
            takeScreenshot("DEM-046_View_Spot_Detail_Before_Edit");

            spotsPage.editSpot();

            CreateSpotsPage editSpotsPage = new CreateSpotsPage(driver, wait);
            editSpotsPage.addInformationFromData(testData);
            editSpotsPage.save();

            String spotName = testData.get("name");
            boolean isSaved = editSpotsPage.isSpotSavedSuccessfully(spotName);
            assertTrue(isSaved, "Spot should be saved successfully after editing");

            takeScreenshot("DEM-046_Edit_Spot");
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-047")
    public void testDem047() {
        try {
            login("will", "will");
            SpotsPage spotsPage = new SpotsPage(driver);
            spotsPage.navigateToSpotsModule();
            spotsPage.navigateToViewSpot();

            String firstRowSpotName = spotsPage.getFirstRowNameLocator().getText().trim();

            spotsPage.clickFirstSpot();

            Thread.sleep(2000);

            spotsPage.deleteSpot();
            spotsPage.clickOkInDeleteDialog();

            Thread.sleep(2000);

            spotsPage.filterQuick(firstRowSpotName, false, false);

            boolean isFilterResultEmpty = spotsPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted spot should no longer exist in the spots list");
            takeElementScreenshot("DEM-047_Deleted_Spot_Filter_Result", driver.findElement(spotsPage.getFilterResult()));

            spotsPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }
}
