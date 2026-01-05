package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.events.CreateEventPage;
import com.proyek_softes.demo.pages.events.EventsPage;
import com.proyek_softes.demo.pages.events.ImportEventPage;
import com.proyek_softes.demo.utils.EventDataProvider;

import io.qameta.allure.Description;

public class EventTest extends GenericCrudTestHelper<EventsPage, CreateEventPage> {

    /**
     * DEM-117
     * Events - Create
     */
    @Test(dataProvider = "createEventData", dataProviderClass = EventDataProvider.class)
    @Description("DEM-117")
    public void testDem117(Map<String, String> testData) {
        EventsPage eventsPage = new EventsPage(driver);
        CreateEventPage createEventPage = new CreateEventPage(driver, wait);

        testCreateEntity(
            testData,
            v -> eventsPage,
            eventsPage::navigateToEventsModule,
            eventsPage::navigateToCreateEvent,
            v -> createEventPage,
            (page, data) -> page.addInformationFromData(data),
            createEventPage::save,
            data -> data.get("name"),
            createEventPage::isEventSavedSuccessfully,
            "DEM-117",
            eventsPage::navigateToViewEvent,
            eventsPage::isInFirstRow,
            v -> eventsPage.getFirstRowLocator()
        );
    }

    /**
     * DEM-118
     * Events - View
     */
    @Test(dataProvider = "viewEventData", dataProviderClass = EventDataProvider.class)
    @Description("DEM-118")
    public void testDem118(Map<String, String> testData) {
        EventsPage eventsPage = new EventsPage(driver);

        testViewEntity(
            testData,
            v -> eventsPage,
            eventsPage::navigateToEventsModule,
            eventsPage::navigateToViewEvent,
            eventsPage::clickFirstEvent,
            data -> data.get("name"),
            eventsPage::isEventTitleCorrect,
            "DEM-118"
        );
    }

    /**
     * DEM-119
     * Events - View (Edit)
     */
    @Test(dataProvider = "editEventData", dataProviderClass = EventDataProvider.class)
    @Description("DEM-119")
    public void testDem119(Map<String, String> testData) {
        EventsPage eventsPage = new EventsPage(driver);
        CreateEventPage editEventPage = new CreateEventPage(driver, wait);

        testEditEntity(
            testData,
            v -> eventsPage,
            eventsPage::navigateToEventsModule,
            eventsPage::navigateToViewEvent,
            eventsPage::clickFirstEvent,
            data -> data.get("nameBeforeEdit"),
            eventsPage::isEventTitleCorrect,
            "DEM-119_View_Event_Detail",
            eventsPage::editEvent,
            v -> editEventPage,
            (page, data) -> page.addInformationFromData(data),
            editEventPage::save,
            data -> data.get("name"),
            editEventPage::isEventSavedSuccessfully,
            "DEM-119"
        );
    }

    /**
     * DEM-120
     * Events - View (Delete)
     */
    @Test
    @Description("DEM-120")
    public void testDem120() {
        try {
            login("will", "will");

            EventsPage eventsPage = new EventsPage(driver);
            eventsPage.navigateToEventsModule();
            eventsPage.navigateToViewEvent();

            String firstRowEventName = eventsPage
                .getFirstRowNameLocator()
                .getText()
                .trim();

            eventsPage.clickFirstEvent();
            Thread.sleep(2000);

            eventsPage.deleteEvent();
            eventsPage.clickOkInDeleteDialog();
            Thread.sleep(2000);

            eventsPage.filterQuick(firstRowEventName, false);

            boolean isFilterResultEmpty = eventsPage.isFilterResultEmpty();
            assertTrue(
                isFilterResultEmpty,
                "Deleted event should no longer exist in the events list"
            );

            takeElementScreenshot(
                "DEM-120_Deleted_Event_Filter_Result",
                driver.findElement(eventsPage.getFilterResult())
            );

            eventsPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    /**
     * DEM-121
     * Events - Import
     */
    @Test
    @Description("DEM-121")
    public void testDem121() {
        login("will", "will");

        EventsPage eventsPage = new EventsPage(driver);
        eventsPage.navigateToEventsModule();
        eventsPage.navigateToImportEvent();

        ImportEventPage importEventPage = new ImportEventPage(driver);

        boolean isCSV = importEventPage.verifyDownloadedTemplateIsCSV(
            10,
            "DEM-121_Download_History"
        );

        assertTrue(
            isCSV,
            "Downloaded template should be in CSV format and named contains 'events'"
        );

        importEventPage.uploadFile("Events.csv");

        importEventPage.clickImportCreate();
        importEventPage.clickNext();
        importEventPage.clickNext();
        importEventPage.clickNext();
        importEventPage.clickImportNow();

        boolean isRecordsImported = importEventPage.isRecordsImported();
        assertTrue(
            isRecordsImported,
            "Records from Events.csv should be imported successfully"
        );

        takeElementScreenshot(
            "DEM-121_Import_Events_Success",
            importEventPage.getSummaryElement()
        );
    }
}
