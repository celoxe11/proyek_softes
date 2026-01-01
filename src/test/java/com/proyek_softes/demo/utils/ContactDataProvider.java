package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class ContactDataProvider {

    private static final String CREATE_CONTACT_FILE = "contact_demo/create_contact_data.json";
    private static final String EDIT_CONTACT_FILE = "contact_demo/edit_contact_data.json";
    private static final String VIEW_CONTACT_FILE = "contact_demo/view_contact_data.json";

    @DataProvider(name = "createContactData")
    public static Object[][] getCreateContactData() {
        return JsonDataReader.getDirectObjectData(CREATE_CONTACT_FILE);
    }

    @DataProvider(name = "viewContactData")
    public static Object[][] getViewContactData() {
        return JsonDataReader.getDirectObjectData(VIEW_CONTACT_FILE);
    }

    @DataProvider(name = "editContactData")
    public static Object[][] getEditContactData() {
        return JsonDataReader.getDirectObjectData(EDIT_CONTACT_FILE);
    }
}
