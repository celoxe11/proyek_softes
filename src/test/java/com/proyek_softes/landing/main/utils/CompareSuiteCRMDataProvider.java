package com.proyek_softes.landing.main.utils;

import com.proyek_softes.demo.utils.BaseDataProvider;
import org.testng.annotations.DataProvider;

/**
 * Data Provider untuk Compare SuiteCRM
 */
public class CompareSuiteCRMDataProvider extends BaseDataProvider {
    private static final String ENTITY_FOLDER = "compare_suitecrm";

    @DataProvider(name = "whitepaperFormData")
    public static Object[][] getWhitepaperFormData() {
        String fileName = String.format("%s/whitepaper_form_data.json", ENTITY_FOLDER);
        return com.proyek_softes.demo.utils.JsonDataReader.getDirectObjectData(fileName);
    }
}
