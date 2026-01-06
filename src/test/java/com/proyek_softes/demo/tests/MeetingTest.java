package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.meetings.MeetingsPage;
import com.proyek_softes.demo.pages.meetings.ScheduleMeetingPage;
import com.proyek_softes.demo.pages.meetings.ImportMeetingPage;
import com.proyek_softes.demo.utils.MeetingDataProvider;

import io.qameta.allure.Description;

public class MeetingTest extends GenericCrudTestHelper<MeetingsPage, ScheduleMeetingPage> {

    @Test(dataProvider = "createMeetingData", dataProviderClass = MeetingDataProvider.class)
    @Description("DEM-065")
    public void testDem065(Map<String, String> testData) {
        MeetingsPage meetingsPage = new MeetingsPage(driver);
        ScheduleMeetingPage scheduleMeetingPage = new ScheduleMeetingPage(driver, wait);

        testCreateEntity(
                testData,
                v -> meetingsPage,
                meetingsPage::navigateToMeetingsModule,
                meetingsPage::navigateToScheduleMeeting,
                v -> scheduleMeetingPage,
                (page, data) -> page.addInformationFromData(data),
                scheduleMeetingPage::save,
                data -> data.get("name"),
                scheduleMeetingPage::isMeetingSavedSuccessfully,
                "DEM-065",
                meetingsPage::navigateToViewMeeting,
                meetingsPage::isInFirstRow,
                v -> meetingsPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewMeetingData", dataProviderClass = MeetingDataProvider.class)
    @Description("DEM-066")
    public void testDem066(Map<String, String> testData) {
        MeetingsPage meetingsPage = new MeetingsPage(driver);

        testViewEntity(
                testData,
                v -> meetingsPage,
                meetingsPage::navigateToMeetingsModule,
                meetingsPage::navigateToViewMeeting,
                meetingsPage::clickFirstMeeting,
                data -> data.get("name"),
                meetingsPage::isMeetingTitleCorrect,
                "DEM-066"
        );
    }

    @Test(dataProvider = "editMeetingData", dataProviderClass = MeetingDataProvider.class)
    @Description("DEM-067")
    public void testDem067(Map<String, String> testData) {
        MeetingsPage meetingsPage = new MeetingsPage(driver);
        ScheduleMeetingPage editMeetingPage = new ScheduleMeetingPage(driver, wait);

        testEditEntity(
                testData,
                v -> meetingsPage,
                meetingsPage::navigateToMeetingsModule,
                meetingsPage::navigateToViewMeeting,
                meetingsPage::clickFirstMeeting,
                data -> data.get("nameBeforeEdit"),
                meetingsPage::isMeetingTitleCorrect,
                "DEM-067_View_Meeting_Detail",
                meetingsPage::editMeeting,
                v -> editMeetingPage,
                (page, data) -> page.addInformationFromData(data),
                editMeetingPage::save,
                data -> data.get("name"),
                editMeetingPage::isMeetingSavedSuccessfully,
                "DEM-067"
        );
    }

    @Test
    @Description("DEM-068")
    public void testDem068() {
        try {
            login("will", "will");
            MeetingsPage meetingsPage = new MeetingsPage(driver);
            meetingsPage.navigateToMeetingsModule();
            meetingsPage.navigateToViewMeeting();

            // get the first row meeting name before clicking
            String firstRowMeetingName = meetingsPage.getFirstRowNameLocator().getText().trim();

            meetingsPage.clickFirstMeeting();

            Thread.sleep(2000);

            meetingsPage.deleteMeeting();
            meetingsPage.clickOkInDeleteDialog();

            // wait until return to view meeting
            Thread.sleep(2000);

            meetingsPage.filterQuick(firstRowMeetingName, false, false, false);

            boolean isFilterResultEmpty = meetingsPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted meeting should no longer exist in the meetings list");
            takeElementScreenshot("DEM-068_Deleted_Meeting_Filter_Result", driver.findElement(meetingsPage.getFilterResult()));

            meetingsPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-069")
    public void testDem069() {
        login("will", "will");
        MeetingsPage meetingsPage = new MeetingsPage(driver);
        meetingsPage.navigateToMeetingsModule();
        meetingsPage.navigateToImportMeeting();

        ImportMeetingPage importMeetingPage = new ImportMeetingPage(driver);
        boolean isCSV = importMeetingPage.verifyDownloadedTemplateIsCSV(10, "DEM-069_Download_History");
        assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'meetings'");
        
        // upload file and complete import process
        importMeetingPage.uploadFile("Meetings.csv");

        importMeetingPage.clickImportCreate();
        importMeetingPage.clickNext();
        importMeetingPage.clickNext();
        importMeetingPage.clickNext();
        importMeetingPage.clickImportNow();
        
        boolean isRecordsImported = importMeetingPage.isRecordsImported();
        assertTrue(isRecordsImported, "Records from Meetings.csv should be imported successfully");
        takeElementScreenshot("DEM-069_Import_Meetings_Success", importMeetingPage.getSummaryElement());
    }
}
