package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class OpportunityDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "opportunity_demo";

    @DataProvider(name = "createOpportunityData")
    public static Object[][] getCreateOpportunityData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewOpportunityData")
    public static Object[][] getViewOpportunityData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editOpportunityData")
    public static Object[][] getEditOpportunityData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
