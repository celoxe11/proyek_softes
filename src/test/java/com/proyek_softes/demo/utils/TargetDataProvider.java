package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class TargetDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "target_demo";

    @DataProvider(name = "createTargetData")
    public static Object[][] getCreateTargetData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewTargetData")
    public static Object[][] getViewTargetData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editTargetData")
    public static Object[][] getEditTargetData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
    
}
