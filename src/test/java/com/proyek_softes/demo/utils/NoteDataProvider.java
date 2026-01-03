package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class NoteDataProvider extends BaseDataProvider {
    private static final String ENTITY_FOLDER = "note_demo";

    @DataProvider(name = "createNoteData")
    public static Object[][] getCreateTaskData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewNoteData")
    public static Object[][] getViewTaskData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editNoteData")
    public static Object[][] getEditTaskData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
