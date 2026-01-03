package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class ContactDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "contact_demo";

    @DataProvider(name = "createContactData")
    public static Object[][] getCreateContactData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewContactData")
    public static Object[][] getViewContactData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editContactData")
    public static Object[][] getEditContactData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
