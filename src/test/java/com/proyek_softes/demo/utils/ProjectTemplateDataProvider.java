package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class ProjectTemplateDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "project_template_demo";

    @DataProvider(name = "createProjectTemplateData")
    public static Object[][] getCreateProjectTemplateData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewProjectTemplateData")
    public static Object[][] getViewProjectTemplateData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editProjectTemplateData")
    public static Object[][] getEditProjectTemplateData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }

}
