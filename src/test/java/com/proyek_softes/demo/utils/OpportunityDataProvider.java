package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class OpportunityDataProvider {

    private static final String CREATE_OPPORTUNITY_FILE = "opportunity_demo/create_opportunity_data.json";
    private static final String VIEW_OPPORTUNITY_FILE = "opportunity_demo/view_opportunity_data.json";
    private static final String EDIT_OPPORTUNITY_FILE = "opportunity_demo/edit_opportunity_data.json";

    @DataProvider(name = "createOpportunityData")
    public static Object[][] getCreateOpportunityData() {
        return JsonDataReader.getDirectObjectData(CREATE_OPPORTUNITY_FILE);
    }

    @DataProvider(name = "viewOpportunityData")
    public static Object[][] getViewOpportunityData() {
        return JsonDataReader.getDirectObjectData(VIEW_OPPORTUNITY_FILE);
    }

    @DataProvider(name = "editOpportunityData")
    public static Object[][] getEditOpportunityData() {
        return JsonDataReader.getDirectObjectData(EDIT_OPPORTUNITY_FILE);
    }
}
