package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class ContractDataProvider extends BaseDataProvider {
    private static final String ENTITY_FOLDER = "contract_demo";

    @DataProvider(name = "createContractData")
    public static Object[][] getCreateContractData() {
        return getTestData(ENTITY_FOLDER, "create");
    }

    @DataProvider(name = "viewContractData")
    public static Object[][] getViewContractData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editContractData")
    public static Object[][] getEditContractData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
    
}
