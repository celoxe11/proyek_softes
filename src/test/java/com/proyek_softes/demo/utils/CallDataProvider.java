package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class CallDataProvider {

    private static final String CREATE_CALL_FILE = "call_demo/create_call_data.json";
    private static final String VIEW_CALL_FILE = "call_demo/view_call_data.json";
    private static final String EDIT_CALL_FILE = "call_demo/edit_call_data.json";

    @DataProvider(name = "createCallData")
    public static Object[][] getCreateCallData() {
        return JsonDataReader.getDirectObjectData(CREATE_CALL_FILE);
    }

    @DataProvider(name = "viewCallData")
    public static Object[][] getViewCallData() {
        return JsonDataReader.getDirectObjectData(VIEW_CALL_FILE);
    }

    @DataProvider(name = "editCallData")
    public static Object[][] getEditCallData() {
        return JsonDataReader.getDirectObjectData(EDIT_CALL_FILE);
    }
}
