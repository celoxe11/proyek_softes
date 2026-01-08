package com.proyek_softes.demo.utils;

import org.testng.annotations.DataProvider;

public class CampaignDataProvider extends BaseDataProvider {

    private static final String ENTITY_FOLDER = "campaign_demo";

    @DataProvider(name = "createCampaignNewsletterData")
    public static Object[][] campaignNewsletterData() {
        return getTestData(ENTITY_FOLDER, "create_newsletter");
    }

    @DataProvider(name = "viewCampaignData")
    public static Object[][] viewCampaignData() {
        return getTestData(ENTITY_FOLDER, "view");
    }

    @DataProvider(name = "editCampaignData")
    public static Object[][] editCampaignData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }

    @DataProvider(name = "createCampaignEmailData")
    public static Object[][] campaignEmailData() {
        return getTestData(ENTITY_FOLDER, "create_email");
    }

    @DataProvider(name = "createCampaignNonEmailData")
    public static Object[][] campaignNonEmailData() {
        return getTestData(ENTITY_FOLDER, "create_nonemail");
    }

    @DataProvider(name = "createCampaignSurveyData")
    public static Object[][] campaignSurveyData() {
        return getTestData(ENTITY_FOLDER, "create_survey");
    }

    @DataProvider(name = "createPersonFormData")
    public static Object[][] createPersonFormData() {
        return getTestData(ENTITY_FOLDER, "create_person_form");
    }

}
