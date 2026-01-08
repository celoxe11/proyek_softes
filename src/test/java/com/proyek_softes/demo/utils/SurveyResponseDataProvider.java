package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class SurveyResponseDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "survey_response_demo";

    @DataProvider(name = "createSurveyResponseData")
    public static Object[][] getCreateSurveyResponseData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewSurveyResponseData")
    public static Object[][] getViewSurveyResponseData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editSurveyResponseData")
    public static Object[][] getEditSurveyResponseData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}