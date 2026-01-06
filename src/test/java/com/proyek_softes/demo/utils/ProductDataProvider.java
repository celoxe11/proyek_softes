package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class ProductDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "product_demo";

    @DataProvider(name = "createProductData")
    public static Object[][] getCreateProductData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewProductData")
    public static Object[][] getViewProductData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editProductData")
    public static Object[][] getEditProductData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
