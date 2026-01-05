package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class InvoiceDataProvider {

    private static final String ENTITY_FOLDER = "invoice_demo";

    @DataProvider(name = "createInvoiceData")
    public static Object[][] getCreateInvoiceData() {
        return BaseDataProvider.getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewInvoiceData")
    public static Object[][] getViewInvoiceData() {
        return BaseDataProvider.getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editInvoiceData")
    public static Object[][] getEditInvoiceData() {
        return BaseDataProvider.getTestData(ENTITY_FOLDER, "edit");
    }
}
