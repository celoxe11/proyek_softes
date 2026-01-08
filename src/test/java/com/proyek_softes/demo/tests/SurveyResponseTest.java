package com.proyek_softes.demo.tests;

import java.util.Map;

import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.survey_responses.CreateSurveyResponsePage;
import com.proyek_softes.demo.pages.survey_responses.SurveyResponsesPage;
import com.proyek_softes.demo.pages.surveys.SurveysPage;
import com.proyek_softes.demo.utils.SurveyResponseDataProvider;

import io.qameta.allure.Description;

import static org.testng.Assert.assertTrue;

public class SurveyResponseTest extends GenericCrudTestHelper<SurveyResponsesPage, CreateSurveyResponsePage> {

    @Test(dataProvider = "createSurveyResponseData", dataProviderClass = SurveyResponseDataProvider.class)
    @Description("DEM-162")
    public void testDem162(Map<String, String> testData) {
        SurveysPage surveysPage = new SurveysPage(driver);
        SurveyResponsesPage surveyResponsesPage = new SurveyResponsesPage(driver);
        CreateSurveyResponsePage createSurveyResponsePage = new CreateSurveyResponsePage(driver, wait);

        testCreateEntity(
                testData,
                v -> surveyResponsesPage,
                () -> {
                    surveysPage.navigateToSurveysModule();
                    surveysPage.navigateToViewSurveyResponses();
                },
                surveyResponsesPage::navigateToCreateSurveyResponse,
                v -> createSurveyResponsePage,
                (page, data) -> page.fillInformationFromData(data),
                createSurveyResponsePage::save,
                data -> data.get("name"),
                createSurveyResponsePage::isSurveyResponseSavedSuccessfully,
                "DEM-162",
                () -> {
                    surveysPage.navigateToSurveysModule();
                    surveysPage.navigateToViewSurveyResponses();
                },
                surveyResponsesPage::isInFirstRow,
                v -> surveyResponsesPage.getFirstRowLocator());
    }

    @Test
    @Description("DEM-163")
    public void testDem163() {
        login("will", "will");
        SurveysPage surveysPage = new SurveysPage(driver);
        SurveyResponsesPage surveyResponsesPage = new SurveyResponsesPage(driver);
        CreateSurveyResponsePage createSurveyResponsePage = new CreateSurveyResponsePage(driver, wait);

        surveysPage.navigateToSurveysModule();
        surveysPage.navigateToViewSurveyResponses();
        surveyResponsesPage.navigateToCreateSurveyResponse();

        boolean isCreatePage = createSurveyResponsePage.isCreatePageDisplayed();
        assertTrue(isCreatePage, "Should be on Create Survey Responses page");
        takeScreenshot("DEM-163_Create_Survey_Response_Page");
    }

    @Test(dataProvider = "editSurveyResponseData", dataProviderClass = SurveyResponseDataProvider.class)
    @Description("DEM-164")
    public void testDem164(Map<String, String> testData) {
        login("will", "will");
        SurveysPage surveysPage = new SurveysPage(driver);
        SurveyResponsesPage surveyResponsesPage = new SurveyResponsesPage(driver);
        CreateSurveyResponsePage editSurveyResponsePage = new CreateSurveyResponsePage(driver, wait);

        surveysPage.navigateToSurveysModule();
        surveysPage.navigateToViewSurveyResponses();

        surveyResponsesPage.clickFirstSurveyResponse();
        surveyResponsesPage.editSurveyResponse();

        editSurveyResponsePage.fillInformationFromData(testData);
        editSurveyResponsePage.save();

        String newName = testData.get("name");
        boolean isSaved = editSurveyResponsePage.isSurveyResponseSavedSuccessfully(newName);
        assertTrue(isSaved, "Survey Response should be saved with new name");
        takeScreenshot("DEM-164_Edited_Survey_Response");

        surveyResponsesPage.deleteSurveyResponse();
        surveyResponsesPage.clickOkInDeleteDialog();

        // Wait for delete to process
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        surveyResponsesPage.filterQuick(newName, false, false);

        boolean isFilterResultEmpty = surveyResponsesPage.isFilterResultEmpty();
        assertTrue(isFilterResultEmpty, "Deleted survey response should no longer exist in the list");
        takeElementScreenshot("DEM-164_No_Results_Found", driver.findElement(surveyResponsesPage.getFilterResult()));

        surveyResponsesPage.checkAndClearFilter();
    }

}
