package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class TargetListDataProvider extends BaseDataProvider {
    private static final String ENTITY_FOLDER = "target_list_demo";

    @DataProvider(name = "createTargetListData")
    public static Object[][] getCreateTargetListData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewTargetListData")
    public static Object[][] getViewTargetListData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editTargetListData")
    public static Object[][] getEditTargetListData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }

}
