package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class AccountDataProvider {

    private static final String DATA_FILE = "testdata/account_data.json";

    @DataProvider(name = "createAccountData")
    public static Object[][] getCreateAccountData() {
        return JsonDataReader.getDataProviderArray(DATA_FILE, "createAccountData");
    }

    @DataProvider(name = "minimalAccountData")
    public static Object[][] getMinimalAccountData() {
        Object[][] allData = JsonDataReader.getDataProviderArray(DATA_FILE, "createAccountData");
        // Return only the first test case (minimal data)
        return new Object[][] { allData[0] };
    }

    @DataProvider(name="duplicateAccountDataConfirmation")
    public static Object[][] getDuplicateAccountDataConfirmation() {
        // Get nested "datas" array from "Account with duplicate data" test case
        return JsonDataReader.getNestedDataProviderArray(DATA_FILE, "createAccountData", 
            "Account with duplicate data", "datas");
    }

    @DataProvider(name = "fullAccountData")
    public static Object[][] getFullAccountData() {
        Object[][] allData = JsonDataReader.getDataProviderArray(DATA_FILE, "createAccountData");
        // Return only the second test case (full data)
        return new Object[][] { allData[1] };
    }

    @DataProvider(name = "multipleEmailAccountData")
    public static Object[][] getMultipleEmailAccountData() {
        Object[][] allData = JsonDataReader.getDataProviderArray(DATA_FILE, "createAccountData");
        // Return only the third test case (multiple emails)
        return new Object[][] { allData[2] };
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
