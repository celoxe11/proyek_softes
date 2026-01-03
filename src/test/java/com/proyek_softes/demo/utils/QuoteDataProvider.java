package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class QuoteDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "quote_demo";

    @DataProvider(name = "createQuoteData")
    public static Object[][] getCreateQuoteData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewQuoteData")
    public static Object[][] getViewQuoteData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editQuoteData")
    public static Object[][] getEditQuoteData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
