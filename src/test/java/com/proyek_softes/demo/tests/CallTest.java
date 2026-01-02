package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.calls.CallsPage;
import com.proyek_softes.demo.pages.calls.CreateCallPage;
import com.proyek_softes.demo.utils.CallDataProvider;

import io.qameta.allure.Description;

public class CallTest extends BaseTest {

    @Test(dataProvider = "createCallData", dataProviderClass = CallDataProvider.class)
    @Description("DEM-060")
    public void testDem060(Map<String, String> testData) {
        login("will", "will");
        CallsPage callsPage = new CallsPage(driver);
        callsPage.navigateToCallsModule();
        callsPage.navigateToCreateCall();

        CreateCallPage createCallPage = new CreateCallPage(driver, wait);
        createCallPage.addInformationFromData(testData);
        createCallPage.save();

        String callName = testData.get("name");
        boolean isSaved = createCallPage.isCallSavedSuccessfully(callName);
        assertTrue(isSaved, "Call should be saved successfully");

        takeScreenshot("DEM-060_Create_Call");

        callsPage.navigateToViewCall();
        boolean isInFirstRow = callsPage.isInFirstRow(callName);
        assertTrue(isInFirstRow, "Created call should appear in the first row of calls list");

        takeElementScreenshot("DEM-060_Call_In_List", callsPage.getFirstRowLocator());
    }

    @Test(dataProvider = "viewCallData", dataProviderClass = CallDataProvider.class)
    @Description("DEM-061")
    public void testDem061(Map<String, String> testData) {
        try {
            login("will", "will");
            CallsPage callsPage = new CallsPage(driver);
            callsPage.navigateToCallsModule();
            callsPage.navigateToViewCall();

            callsPage.clickFirstCall();

            Thread.sleep(2000);

            String callName = testData.get("name");
            boolean isOnCallDetailPage = callsPage.isCallTitleCorrect(callName);
            assertTrue(isOnCallDetailPage, "Should be on Call Detail page for the selected call");
            takeScreenshot("DEM-061_View_Call_Detail");
        } catch (InterruptedException e) {
        }
    }

    @Test(dataProvider = "editCallData", dataProviderClass = CallDataProvider.class)
    @Description("DEM-062")
    public void testDem062(Map<String, String> testData) {
        try {
            login("will", "will");
            CallsPage callsPage = new CallsPage(driver);
            callsPage.navigateToCallsModule();
            callsPage.navigateToViewCall();

            callsPage.clickFirstCall();

            Thread.sleep(2000);

            String callNameBeforeEdit = testData.get("nameBeforeEdit");
            boolean isOnCallDetailPage = callsPage.isCallTitleCorrect(callNameBeforeEdit);
            assertTrue(isOnCallDetailPage, "Should be on Call Detail page for the selected call");
            takeScreenshot("DEM-062_View_Call_Detail_Before_Edit");
            callsPage.editCall();

            CreateCallPage editCallPage = new CreateCallPage(driver, wait);
            editCallPage.addInformationFromData(testData);
            editCallPage.save();

            String callName = testData.get("name");
            boolean isSaved = editCallPage.isCallSavedSuccessfully(callName);
            assertTrue(isSaved, "Call should be saved successfully after editing");

            takeScreenshot("DEM-062_Edit_Call");
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-063")
    public void testDem063() {
        // import call test to be implemented
    }
}
