package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class ProductCategoryDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "product_category_demo";

    @DataProvider(name = "createProductCategoryData")
    public static Object[][] getCreateProductCategoryData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewProductCategoryData")
    public static Object[][] getViewProductCategoryData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editProductCategoryData")
    public static Object[][] getEditProductCategoryData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
