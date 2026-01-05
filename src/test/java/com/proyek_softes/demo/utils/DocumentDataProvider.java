package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class DocumentDataProvider extends BaseDataProvider {
    private static final String ENTITY_FOLDER = "document_demo";

    @DataProvider(name = "createDocumentData")
    public static Object[][] getCreateDocumentData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewDocumentData")
    public static Object[][] getViewDocumentData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editDocumentData")
    public static Object[][] getEditDocumentData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
