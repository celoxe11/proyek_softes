package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.surveys.CreateSurveyPage;
import com.proyek_softes.demo.pages.surveys.SurveysPage;
import com.proyek_softes.demo.utils.SurveyDataProvider;

import io.qameta.allure.Description;

public class SurveyTest extends GenericCrudTestHelper<SurveysPage, CreateSurveyPage> {

    @Test(dataProvider = "createSurveyData", dataProviderClass = SurveyDataProvider.class)
    @Description("DEM-158")
    public void testDem158(Map<String, String> testData) {
        SurveysPage surveysPage = new SurveysPage(driver);
        CreateSurveyPage createSurveyPage = new CreateSurveyPage(driver, wait);

        testCreateEntity(
                testData,
                v -> surveysPage,
                surveysPage::navigateToSurveysModule,
                surveysPage::navigateToCreateSurvey,
                v -> createSurveyPage,
                (page, data) -> page.fillInformationFromData(data), // Method name changed in CreateSurveyPage
                createSurveyPage::save,
                data -> data.get("name"),
                createSurveyPage::isSurveySavedSuccessfully,
                "DEM-158",
                surveysPage::navigateToViewSurvey,
                surveysPage::isInFirstRow,
                v -> surveysPage.getFirstRowLocator());
    }

    @Test(dataProvider = "viewSurveyData", dataProviderClass = SurveyDataProvider.class)
    @Description("DEM-159")
    public void testDem159(Map<String, String> testData) {
        SurveysPage surveysPage = new SurveysPage(driver);

        testViewEntity(
                testData,
                v -> surveysPage,
                surveysPage::navigateToSurveysModule,
                surveysPage::navigateToViewSurvey,
                surveysPage::clickFirstSurvey,
                data -> data.get("name"),
                surveysPage::isSurveyTitleCorrect,
                "DEM-159");
    }

    @Test(dataProvider = "editSurveyData", dataProviderClass = SurveyDataProvider.class)
    @Description("DEM-160")
    public void testDem160(Map<String, String> testData) {
        SurveysPage surveysPage = new SurveysPage(driver);
        CreateSurveyPage editSurveyPage = new CreateSurveyPage(driver, wait);

        testEditEntity(
                testData,
                v -> surveysPage,
                surveysPage::navigateToSurveysModule,
                surveysPage::navigateToViewSurvey,
                surveysPage::clickFirstSurvey,
                data -> data.get("nameBeforeEdit"),
                surveysPage::isSurveyTitleCorrect,
                "DEM-160_View_Survey_Detail",
                surveysPage::editSurvey,
                v -> editSurveyPage,
                (page, data) -> page.fillInformationFromData(data),
                editSurveyPage::save,
                data -> data.get("name"),
                editSurveyPage::isSurveySavedSuccessfully,
                "DEM-160");
    }

    @Test
    @Description("DEM-161")
    public void testDem161() {
        try {
            login("will", "will");
            SurveysPage surveysPage = new SurveysPage(driver);
            surveysPage.navigateToSurveysModule();
            surveysPage.navigateToViewSurvey();

            String firstRowSurveyName = surveysPage.getFirstRowNameLocator().getText().trim();

            surveysPage.clickFirstSurvey();

            Thread.sleep(2000);

            surveysPage.deleteSurvey();
            surveysPage.clickOkInDeleteDialog();

            Thread.sleep(2000);

            surveysPage.filterQuick(firstRowSurveyName, false, false);

            boolean isFilterResultEmpty = surveysPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted survey should no longer exist in the surveys list");
            takeElementScreenshot("DEM-161_Deleted_Survey_Filter_Result",
                    driver.findElement(surveysPage.getFilterResult()));

            surveysPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-162")
    public void testDem162() {
        login("will", "will");
        SurveysPage surveysPage = new SurveysPage(driver);
        surveysPage.navigateToSurveysModule();
        surveysPage.navigateToViewSurveyResponses();

        boolean isCreateSurveyResponsesPageDisplayed = surveysPage.isCreateSurveyResponsesPageDisplayed();
        assertTrue(isCreateSurveyResponsesPageDisplayed,
                "Should navigate to Create Survey Responses page (which corresponds to View Survey Responses menu)");
        takeScreenshot("DEM-162_View_Survey_Responses_Page");
    }
}
