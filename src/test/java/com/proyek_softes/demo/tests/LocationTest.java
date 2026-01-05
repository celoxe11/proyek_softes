package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.locations.CreateLocationPage;
import com.proyek_softes.demo.pages.locations.LocationsPage;
import com.proyek_softes.demo.utils.LocationDataProvider;

import io.qameta.allure.Description;

public class LocationTest extends GenericCrudTestHelper<LocationsPage, CreateLocationPage> {
    /**
     * DEM-122
     * Locations - Create
     */
    @Test(dataProvider = "createLocationData", dataProviderClass = LocationDataProvider.class)
    @Description("DEM-122")
    public void testDem122(Map<String, String> testData) {
        LocationsPage locationsPage = new LocationsPage(driver);
        CreateLocationPage createLocationPage =
                new CreateLocationPage(driver, wait);

        testCreateEntity(
            testData,
            v -> locationsPage,
            locationsPage::navigateToLocationsModule,
            locationsPage::navigateToCreateLocation,
            v -> createLocationPage,
            (page, data) -> page.addInformationFromData(data),
            createLocationPage::save,
            data -> data.get("name"),
            createLocationPage::isLocationSavedSuccessfully,
            "DEM-122",
            locationsPage::navigateToViewLocation,
            locationsPage::isInFirstRow,
            v -> locationsPage.getFirstRowLocator()
        );
    }

    /**
     * DEM-123
     * Locations - View
     */
    @Test(dataProvider = "viewLocationData", dataProviderClass = LocationDataProvider.class)
    @Description("DEM-123")
    public void testDem123(Map<String, String> testData) {
        LocationsPage locationsPage = new LocationsPage(driver);

        testViewEntity(
            testData,
            v -> locationsPage,
            locationsPage::navigateToLocationsModule,
            locationsPage::navigateToViewLocation,
            locationsPage::clickFirstLocation,
            data -> data.get("name"),
            locationsPage::isLocationTitleCorrect,
            "DEM-123"
        );
    }

    /**
     * DEM-124
     * Locations - View (Edit)
     */
    @Test(dataProvider = "editLocationData", dataProviderClass = LocationDataProvider.class)
    @Description("DEM-124")
    public void testDem124(Map<String, String> testData) {
        LocationsPage locationsPage = new LocationsPage(driver);
        CreateLocationPage editLocationPage = new CreateLocationPage(driver, wait);

        testEditEntity(
            testData,
            v -> locationsPage,
            locationsPage::navigateToLocationsModule,
            locationsPage::navigateToViewLocation,
            locationsPage::clickFirstLocation,
            data -> data.get("nameBeforeEdit"),
            locationsPage::isLocationTitleCorrect,
            "DEM-124_View_Location_Detail",
            locationsPage::editLocation,
            v -> editLocationPage,
            (page, data) -> page.addInformationFromData(data),
            editLocationPage::save,
            data -> data.get("name"),
            editLocationPage::isLocationSavedSuccessfully,
            "DEM-124"
        );
    }

    /**
     * DEM-125
     * Locations - View (Delete)
     */
    @Test
    @Description("DEM-125")
    public void testDem125() {
        try {
            login("will", "will");

            LocationsPage locationsPage = new LocationsPage(driver);
            locationsPage.navigateToLocationsModule();
            locationsPage.navigateToViewLocation();

            String firstRowLocationName = locationsPage
                .getFirstRowNameLocator()
                .getText()
                .trim();

            locationsPage.clickFirstLocation();
            Thread.sleep(2000);

            locationsPage.deleteLocation();
            locationsPage.clickOkInDeleteDialog();
            Thread.sleep(2000);

            locationsPage.filterQuick(firstRowLocationName, false);

            boolean isFilterResultEmpty = locationsPage.isFilterResultEmpty();
            assertTrue(
                isFilterResultEmpty,
                "Deleted location should no longer exist in the locations list"
            );

            takeElementScreenshot(
                "DEM-125_Deleted_Location_Filter_Result",
                driver.findElement(locationsPage.getFilterResult())
            );

            locationsPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }
}
