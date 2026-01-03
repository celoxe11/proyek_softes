package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class TaskDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "task_demo";

    @DataProvider(name = "createTaskData")
    public static Object[][] getCreateTaskData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewTaskData")
    public static Object[][] getViewTaskData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editTaskData")
    public static Object[][] getEditTaskData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
