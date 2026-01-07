package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class KBCategoryDataProvider extends BaseDataProvider{
    private static final String ENTITY_FOLDER = "kb_category_demo";

    @DataProvider(name = "createKBCategoryData")
    public static Object[][] getCreateKBCategoryData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewKBCategoryData")
    public static Object[][] getViewKBCategoryData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editKBCategoryData")
    public static Object[][] getEditKBCategoryData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
