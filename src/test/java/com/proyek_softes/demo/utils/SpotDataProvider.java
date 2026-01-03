package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class SpotDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "spot_demo";

    @DataProvider(name = "createSpotData")
    public static Object[][] getCreateSpotData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewSpotData")
    public static Object[][] getViewSpotData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editSpotData")
    public static Object[][] getEditSpotData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
