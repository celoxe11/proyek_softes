package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class PdfTemplateDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "pdf_template_demo";

    @DataProvider(name = "createPdfTemplateData")
    public static Object[][] getCreatePdfTemplateData() {
        return BaseDataProvider.getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewPdfTemplateData")
    public static Object[][] getViewPdfTemplateData() {
        return BaseDataProvider.getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editPdfTemplateData")
    public static Object[][] getEditPdfTemplateData() {
        return BaseDataProvider.getTestData(ENTITY_FOLDER, "edit");
    }
}
