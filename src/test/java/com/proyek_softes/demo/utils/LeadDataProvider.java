package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class LeadDataProvider {

    private static final String CREATE_LEAD_FILE = "lead_demo/create_lead_data.json";
    private static final String VIEW_LEAD_FILE = "lead_demo/view_lead_data.json";
    private static final String EDIT_LEAD_FILE = "lead_demo/edit_lead_data.json";

    @DataProvider(name = "createLeadData")
    public static Object[][] getCreateLeadData() {
        return JsonDataReader.getDirectObjectData(CREATE_LEAD_FILE);
    }

    @DataProvider(name = "viewLeadData")
    public static Object[][] getViewLeadData() {
        return JsonDataReader.getDirectObjectData(VIEW_LEAD_FILE);
    }

    @DataProvider(name = "editLeadData")
    public static Object[][] getEditLeadData() {
        return JsonDataReader.getDirectObjectData(EDIT_LEAD_FILE);
    }
}
