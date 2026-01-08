package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.spot.CreateSpotsPage;
import com.proyek_softes.demo.pages.spot.SpotsPage;
import com.proyek_softes.demo.utils.SpotDataProvider;

import io.qameta.allure.Description;

public class SpotTest extends GenericCrudTestHelper<SpotsPage, CreateSpotsPage> {

    @Test(dataProvider = "createSpotData", dataProviderClass = SpotDataProvider.class)
    @Description("DEM-044")
    public void testDem044(Map<String, String> testData) {
        SpotsPage spotsPage = new SpotsPage(driver);
        CreateSpotsPage createSpotsPage = new CreateSpotsPage(driver, wait);
        
        testCreateEntity(
            testData,
            v -> spotsPage,
            spotsPage::navigateToSpotsModule,
            spotsPage::navigateToCreateSpot,
            v -> createSpotsPage,
            (page, data) -> page.addInformationFromData(data),
            createSpotsPage::save,
            data -> data.get("name"),
            createSpotsPage::isSpotSavedSuccessfully,
            "DEM-044",
            spotsPage::navigateToViewSpot,
            spotsPage::isInFirstRow,
            v -> spotsPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewSpotData", dataProviderClass = SpotDataProvider.class)
    @Description("DEM-045")
    public void testDem045(Map<String, String> testData) {
        SpotsPage spotsPage = new SpotsPage(driver);
        
        testViewEntity(
            testData,
            v -> spotsPage,
            spotsPage::navigateToSpotsModule,
            spotsPage::navigateToViewSpot,
            spotsPage::clickFirstSpot,
            data -> data.get("name"),
            spotsPage::isSpotTitleCorrect,
            "DEM-045"
        );
    }

    @Test(dataProvider = "editSpotData", dataProviderClass = SpotDataProvider.class)
    @Description("DEM-046")
    public void testDem046(Map<String, String> testData) {
        SpotsPage spotsPage = new SpotsPage(driver);
        CreateSpotsPage editSpotsPage = new CreateSpotsPage(driver, wait);
        
        testEditEntity(
            testData,
            v -> spotsPage,
            spotsPage::navigateToSpotsModule,
            spotsPage::navigateToViewSpot,
            spotsPage::clickFirstSpot,
            data -> data.get("nameBeforeEdit"),
            spotsPage::isSpotTitleCorrect,
            "DEM-046_View_Spot_Detail",
            spotsPage::editSpot,
            v -> editSpotsPage,
            (page, data) -> page.addInformationFromData(data),
            editSpotsPage::save,
            data -> data.get("name"),
            editSpotsPage::isSpotSavedSuccessfully,
            "DEM-046"
        );
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

            spotsPage.deleteSpot();
            spotsPage.clickOkInDeleteDialog();

            Thread.sleep(1000);

            boolean spotExists = spotsPage.isSpotExistsInTable(firstRowSpotName);
            assertTrue(!spotExists, "Deleted spot should no longer exist in the spots list");
            takeScreenshot("DEM-047_Deleted_Spot_Verification");
        } catch (InterruptedException e) {
        }
    }
}
