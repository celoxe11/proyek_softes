package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class EmailDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "email_demo";

    @DataProvider(name = "createEmailData")
    public static Object[][] createEmailData() {
        return getTestData(ENTITY_FOLDER, "compose");
    }
}
