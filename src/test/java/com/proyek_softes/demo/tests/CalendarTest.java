package com.proyek_softes.demo.tests;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.calendar.CalendarPage;
import com.proyek_softes.demo.pages.calendar.NavigationChecker;
import com.proyek_softes.demo.pages.calendar.TodayPage;

import io.qameta.allure.Description;

public class CalendarTest extends BaseTest {

    @Test
    @Description("DEM-032")
    public void testDem032() {
        login("will", "will");
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.navigateToCalendarModule();
        calendarPage.navigateToScheduleMeeting();

        NavigationChecker navigationChecker = new NavigationChecker(driver);
        boolean isOnScheduleMeetingPage = navigationChecker.isPageCorrect("MEETINGS", "CREATE");
        assertTrue(isOnScheduleMeetingPage, "User should be on Schedule Meeting page");
        takeScreenshot("DEM-032_Schedule_Meeting_Page_Access");
    }

    @Test
    @Description("DEM-033")
    public void testDem033() {
        login("will", "will");
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.navigateToCalendarModule();
        calendarPage.navigateToScheduleCall();

        NavigationChecker navigationChecker = new NavigationChecker(driver);
        boolean isOnScheduleMeetingPage = navigationChecker.isPageCorrect("CALLS", "CREATE");
        assertTrue(isOnScheduleMeetingPage, "User should be on Schedule Call page");
        takeScreenshot("DEM-033_Schedule_Call_Page_Access");
    }

    @Test
    @Description("DEM-034")
    public void testDem034() {
        login("will", "will");
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.navigateToCalendarModule();
        calendarPage.navigateToCreateTask();

        NavigationChecker navigationChecker = new NavigationChecker(driver);
        boolean isOnScheduleMeetingPage = navigationChecker.isPageCorrect("TASKS", "CREATE");
        assertTrue(isOnScheduleMeetingPage, "User should be on Create Task page");
        takeScreenshot("DEM-034_Create_Task_Page_Access");
    }

    @Test
    @Description("DEM-035")
    public void testDem035() {
        login("will", "will");
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.navigateToCalendarModule();
        calendarPage.navigateToTodayView();

        TodayPage todayPage = new TodayPage(driver);
        boolean isTodayCorrect = todayPage.isDisplayedDateCorrect();
        assertTrue(isTodayCorrect, "The date displayed on Today view should be correct");
        takeElementScreenshot("DEM-035_Today_View_Access", todayPage.getDateHeadingLocator());
    }
}
