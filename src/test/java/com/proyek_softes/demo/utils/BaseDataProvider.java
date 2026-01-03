package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

/**
 * Base class for all data providers to eliminate code duplication.
 * Provides generic data provider methods for create, view, and edit operations.
 */
public class BaseDataProvider {

    /**
     * Generic method to create a data provider for any entity and operation.
     * 
     * @param entityFolder The folder name containing the JSON data (e.g., "account_demo", "task_demo")
     * @param operation The operation type (e.g., "create", "view", "edit")
     * @return Object[][] containing the test data
     */
    protected static Object[][] getTestData(String entityFolder, String operation) {
        String fileName = String.format("%s/%s_%s_data.json", 
            entityFolder, 
            operation, 
            entityFolder.replace("_demo", ""));
        return JsonDataReader.getDirectObjectData(fileName);
    }

    /**
     * Generic data provider for create operations.
     * Subclasses should provide the entity folder name.
     */
    @DataProvider(name = "createData")
    public static Object[][] getCreateData(String entityFolder) {
        return getTestData(entityFolder, "create");
    }

    /**
     * Generic data provider for view operations.
     * Subclasses should provide the entity folder name.
     */
    @DataProvider(name = "viewData")
    public static Object[][] getViewData(String entityFolder) {
        return getTestData(entityFolder, "view");
    }

    /**
     * Generic data provider for edit operations.
     * Subclasses should provide the entity folder name.
     */
    @DataProvider(name = "editData")
    public static Object[][] getEditData(String entityFolder) {
        return getTestData(entityFolder, "edit");
    }
}
