package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class CaseDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "case_demo";

    @DataProvider(name = "createCaseData")
    public static Object[][] getCreateCaseData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewCaseData")
    public static Object[][] getViewCaseData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editCaseData")
    public static Object[][] getEditCaseData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }

}
