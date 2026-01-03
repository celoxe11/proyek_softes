package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class LeadDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "lead_demo";

    @DataProvider(name = "createLeadData")
    public static Object[][] getCreateLeadData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewLeadData")
    public static Object[][] getViewLeadData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editLeadData")
    public static Object[][] getEditLeadData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
