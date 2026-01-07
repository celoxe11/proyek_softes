package com.proyek_softes.demo.utils;

public class EmailTemplateDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "email_template_demo";

    @org.testng.annotations.DataProvider(name = "createEmailTemplateData")
    public static Object[][] getCreateEmailTemplateData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @org.testng.annotations.DataProvider(name = "viewEmailTemplateData")
    public static Object[][] getViewEmailTemplateData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @org.testng.annotations.DataProvider(name = "editEmailTemplateData")
    public static Object[][] getEditEmailTemplateData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
    
}
