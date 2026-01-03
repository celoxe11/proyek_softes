package com.proyek_softes.demo.tests;

import java.util.Map;

import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.calls.CallsPage;
import com.proyek_softes.demo.pages.calls.CreateCallPage;
import com.proyek_softes.demo.utils.CallDataProvider;

import io.qameta.allure.Description;

public class CallTest extends GenericCrudTestHelper<CallsPage, CreateCallPage> {

    @Test(dataProvider = "createCallData", dataProviderClass = CallDataProvider.class)
    @Description("DEM-060")
    public void testDem060(Map<String, String> testData) {
        CallsPage callsPage = new CallsPage(driver);
        CreateCallPage createCallPage = new CreateCallPage(driver, wait);
        
        testCreateEntity(
            testData,
            v -> callsPage,
            callsPage::navigateToCallsModule,
            callsPage::navigateToCreateCall,
            v -> createCallPage,
            (page, data) -> page.addInformationFromData(data),
            createCallPage::save,
            data -> data.get("name"),
            createCallPage::isCallSavedSuccessfully,
            "DEM-060",
            callsPage::navigateToViewCall,
            callsPage::isInFirstRow,
            v -> callsPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewCallData", dataProviderClass = CallDataProvider.class)
    @Description("DEM-061")
    public void testDem061(Map<String, String> testData) {
        CallsPage callsPage = new CallsPage(driver);
        
        testViewEntity(
            testData,
            v -> callsPage,
            callsPage::navigateToCallsModule,
            callsPage::navigateToViewCall,
            callsPage::clickFirstCall,
            data -> data.get("name"),
            callsPage::isCallTitleCorrect,
            "DEM-061"
        );
    }

    @Test(dataProvider = "editCallData", dataProviderClass = CallDataProvider.class)
    @Description("DEM-062")
    public void testDem062(Map<String, String> testData) {
        CallsPage callsPage = new CallsPage(driver);
        CreateCallPage editCallPage = new CreateCallPage(driver, wait);
        
        testEditEntity(
            testData,
            v -> callsPage,
            callsPage::navigateToCallsModule,
            callsPage::navigateToViewCall,
            callsPage::clickFirstCall,
            data -> data.get("nameBeforeEdit"),
            callsPage::isCallTitleCorrect,
            "DEM-062_View_Call_Detail",
            callsPage::editCall,
            v -> editCallPage,
            (page, data) -> page.addInformationFromData(data),
            editCallPage::save,
            data -> data.get("name"),
            editCallPage::isCallSavedSuccessfully,
            "DEM-062"
        );
    }

    @Test
    @Description("DEM-063")
    public void testDem063() {
        // import call test to be implemented
    }
}
