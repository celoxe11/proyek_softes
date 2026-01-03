package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class MeetingDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "meeting_demo";

    @DataProvider(name = "createMeetingData")
    public static Object[][] getCreateMeetingData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewMeetingData")
    public static Object[][] getViewMeetingData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editMeetingData")
    public static Object[][] getEditMeetingData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
