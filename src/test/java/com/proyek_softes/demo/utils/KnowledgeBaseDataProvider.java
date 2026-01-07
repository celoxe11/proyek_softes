package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class KnowledgeBaseDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "knowledge_base_demo";

    @DataProvider(name = "createKnowledgeBaseData")
    public static Object[][] getCreateKnowledgeBaseData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewKnowledgeBaseData")
    public static Object[][] getViewKnowledgeBaseData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editKnowledgeBaseData")
    public static Object[][] getEditKnowledgeBaseData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }

}
