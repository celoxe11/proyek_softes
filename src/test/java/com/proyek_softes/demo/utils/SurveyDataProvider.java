package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class SurveyDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "survey_demo";

    @DataProvider(name = "createSurveyData")
    public static Object[][] getCreateSurveyData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewSurveyData")
    public static Object[][] getViewSurveyData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editSurveyData")
    public static Object[][] getEditSurveyData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}