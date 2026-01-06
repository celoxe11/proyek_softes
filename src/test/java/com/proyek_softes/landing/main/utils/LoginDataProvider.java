package com.proyek_softes.landing.main.utils;

import com.proyek_softes.demo.utils.BaseDataProvider;
import org.testng.annotations.DataProvider;

/**
 * Data Provider untuk Client Login
 */
public class LoginDataProvider extends BaseDataProvider {
    private static final String ENTITY_FOLDER = "login_landing";

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
        String fileName = String.format("%s/%s_data.json", ENTITY_FOLDER, ENTITY_FOLDER);
        return com.proyek_softes.demo.utils.JsonDataReader.getDirectObjectData(fileName);
    }

    @DataProvider(name = "forgotUsernameData")
    public static Object[][] getForgotUsernameData() {
        String fileName = String.format("%s/forgot_username_data.json", ENTITY_FOLDER);
        return com.proyek_softes.demo.utils.JsonDataReader.getDirectObjectData(fileName);
    }
}
