package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class QuoteDataProvider {

    private static final String CREATE_QUOTE_FILE = "quote_demo/create_quote_data.json";
    private static final String EDIT_QUOTE_FILE = "quote_demo/edit_quote_data.json";
    private static final String VIEW_QUOTE_FILE = "quote_demo/view_quote_data.json";

    @DataProvider(name = "createQuoteData")
    public static Object[][] getCreateQuoteData() {
        return JsonDataReader.getDirectObjectData(CREATE_QUOTE_FILE);
    }

    @DataProvider(name = "viewQuoteData")
    public static Object[][] getViewQuoteData() {
        return JsonDataReader.getDirectObjectData(VIEW_QUOTE_FILE);
    }

    @DataProvider(name = "editQuoteData")
    public static Object[][] getEditQuoteData() {
        return JsonDataReader.getDirectObjectData(EDIT_QUOTE_FILE);
    }
}
