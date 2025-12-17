package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class AccountDataProvider {

    private static final String DATA_FILE = "testdata/account_data.json";

    @DataProvider(name = "createAccountData")
    public static Object[][] getCreateAccountData() {
        return JsonDataReader.getDataProviderArray(DATA_FILE, "createAccountData");
    }

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
        return JsonDataReader.getDataProviderArray(DATA_FILE, "loginData");
    }

    @DataProvider(name = "validationTestData")
    public static Object[][] getValidationTestData() {
        return JsonDataReader.getDataProviderArray(DATA_FILE, "validationTestData");
    }
}
