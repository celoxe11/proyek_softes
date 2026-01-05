package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class EventDataProvider extends BaseDataProvider{
    private static final String ENTITY_FOLDER = "event_demo";

    @DataProvider(name = "createEventData")
    public static Object[][] getCreateEventData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewEventData")
    public static Object[][] getViewEventData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editEventData")
    public static Object[][] getEditEventData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
