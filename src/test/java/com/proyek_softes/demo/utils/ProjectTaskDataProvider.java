package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class ProjectTaskDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "project_task_demo";

    @DataProvider(name = "createProjectData")
    public static Object[][] getCreateProjectData() {
        return getTestData(ENTITY_FOLDER, "create");
    }
}
