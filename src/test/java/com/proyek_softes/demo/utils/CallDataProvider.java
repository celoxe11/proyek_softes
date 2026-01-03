package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class CallDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "call_demo";

    @DataProvider(name = "createCallData")
    public static Object[][] getCreateCallData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewCallData")
    public static Object[][] getViewCallData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editCallData")
    public static Object[][] getEditCallData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
