package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class LocationDataProvider extends BaseDataProvider{
    private static final String ENTITY_FOLDER = "location_demo";

    @DataProvider(name = "createLocationData")
    public static Object[][] getCreateLocationData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewLocationData")
    public static Object[][] getViewLocationData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editLocationData")
    public static Object[][] getEditLocationData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}