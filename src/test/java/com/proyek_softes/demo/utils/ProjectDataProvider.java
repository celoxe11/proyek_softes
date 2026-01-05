package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class ProjectDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "project_demo";

    @DataProvider(name = "createProjectData")
    public static Object[][] getCreateProjectData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewProjectData")
    public static Object[][] getViewProjectData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editProjectData")
    public static Object[][] getEditProjectData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
