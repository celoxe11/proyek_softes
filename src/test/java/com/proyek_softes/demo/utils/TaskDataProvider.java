package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class TaskDataProvider {

    private static final String CREATE_TASK_FILE = "task_demo/create_task_data.json";
    private static final String VIEW_TASK_FILE = "task_demo/view_task_data.json";
    private static final String EDIT_TASK_FILE = "task_demo/edit_task_data.json";

    @DataProvider(name = "createTaskData")
    public static Object[][] getCreateTaskData() {
        return JsonDataReader.getDirectObjectData(CREATE_TASK_FILE);
    }

    @DataProvider(name = "viewTaskData")
    public static Object[][] getViewTaskData() {
        return JsonDataReader.getDirectObjectData(VIEW_TASK_FILE);
    }

    @DataProvider(name = "editTaskData")
    public static Object[][] getEditTaskData() {
        return JsonDataReader.getDirectObjectData(EDIT_TASK_FILE);
    }
}
