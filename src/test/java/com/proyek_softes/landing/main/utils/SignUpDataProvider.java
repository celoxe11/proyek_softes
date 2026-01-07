package com.proyek_softes.landing.main.utils;

import com.proyek_softes.demo.utils.BaseDataProvider;
import org.testng.annotations.DataProvider;


public class SignUpDataProvider extends BaseDataProvider {
    private static final String ENTITY_FOLDER = "sign_up_landing";

    @DataProvider(name = "signUpData")
    public static Object[][] getSignUpData() {
        String fileName = String.format("%s/sign_up_data.json", ENTITY_FOLDER);
        return com.proyek_softes.demo.utils.JsonDataReader.getDirectObjectData(fileName);
    }
}