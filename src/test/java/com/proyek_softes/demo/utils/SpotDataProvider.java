package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class SpotDataProvider {

    private static final String CREATE_SPOT_FILE = "spot_demo/create_spot_data.json";
    private static final String EDIT_SPOT_FILE = "spot_demo/edit_spot_data.json";
    private static final String VIEW_SPOT_FILE = "spot_demo/view_spot_data.json";

    @DataProvider(name = "createSpotData")
    public static Object[][] getCreateSpotData() {
        return JsonDataReader.getDirectObjectData(CREATE_SPOT_FILE);
    }

    @DataProvider(name = "viewSpotData")
    public static Object[][] getViewSpotData() {
        return JsonDataReader.getDirectObjectData(VIEW_SPOT_FILE);
    }

    @DataProvider(name = "editSpotData")
    public static Object[][] getEditSpotData() {
        return JsonDataReader.getDirectObjectData(EDIT_SPOT_FILE);
    }
}
