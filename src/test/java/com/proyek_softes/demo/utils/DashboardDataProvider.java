package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class DashboardDataProvider extends BaseDataProvider {
    private static final String ENTITY_FOLDER = "dashboard_demo";

    @DataProvider(name = "addTabData")
    public Object[][] addTabData() {
        return getTestData(ENTITY_FOLDER, "create_tab");
    }

    @DataProvider(name = "editTabData")
    public Object[][] editTabData() {
        return getTestData(ENTITY_FOLDER, "edit_tab");
    }

    @DataProvider(name = "searchData")
    public Object[][] searchData() {
        return getTestData(ENTITY_FOLDER, "search");
    }

    @DataProvider(name = "editProfileData")
    public Object[][] editProfileData() {
        return getTestData(ENTITY_FOLDER, "edit_profile");
    }
}
