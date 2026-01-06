package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class ReportDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "report_demo";

    @DataProvider(name = "createReportData")
    public static Object[][] createReportData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewReportData")
    public static Object[][] viewReportData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editReportData")
    public static Object[][] editReportData() {
        return getTestData(ENTITY_FOLDER, "edit");

    }

}
