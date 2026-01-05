package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.targets_lists.CreateTargetsListPage;
import com.proyek_softes.demo.pages.targets_lists.TargetsListsPage;
import com.proyek_softes.demo.utils.TargetListDataProvider;

import io.qameta.allure.Description;

public class TargetListTest extends GenericCrudTestHelper<TargetsListsPage, CreateTargetsListPage> {

    @Test(dataProvider = "createTargetListData", dataProviderClass = TargetListDataProvider.class)
    @Description("DEM-101")
    public void testDem101(Map<String, String> testData) {
        TargetsListsPage targetsListsPage = new TargetsListsPage(driver);
        CreateTargetsListPage createTargetsListPage = new CreateTargetsListPage(driver, wait);

        testCreateEntity(
                testData,
                v -> targetsListsPage,
                targetsListsPage::navigateToTargetsModule,
                targetsListsPage::navigateToCreateTargetList,
                v -> createTargetsListPage,
                (page, data) -> page.addInformationFromData(data),
                createTargetsListPage::save,
                data -> data.get("name"),
                createTargetsListPage::isTargetListSavedSuccessfully,
                "DEM-101",
                targetsListsPage::navigateToViewTargetList,
                targetsListsPage::isInFirstRow,
                v -> targetsListsPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewTargetListData", dataProviderClass = TargetListDataProvider.class)
    @Description("DEM-102")
    public void testDem102(Map<String, String> testData) {
        TargetsListsPage targetsListsPage = new TargetsListsPage(driver);
        testViewEntity(
                testData,
                v -> targetsListsPage,
                targetsListsPage::navigateToTargetsModule,
                targetsListsPage::navigateToViewTargetList,
                targetsListsPage::clickFirstTargetList,
                data -> data.get("name"),
                targetsListsPage::isTargetListTitleCorrect,
                "DEM-102"
        );
    }

    @Test(dataProvider = "editTargetListData", dataProviderClass = TargetListDataProvider.class)
    @Description("DEM-103")
    public void testDem103(Map<String, String> testData) {
        TargetsListsPage targetsListsPage = new TargetsListsPage(driver);
        CreateTargetsListPage editTargetsListPage = new CreateTargetsListPage(driver, wait);

        testEditEntity(
                testData,
                v -> targetsListsPage,
                targetsListsPage::navigateToTargetsModule,
                targetsListsPage::navigateToViewTargetList,
                targetsListsPage::clickFirstTargetList,
                data -> data.get("nameBeforeEdit"),
                targetsListsPage::isTargetListTitleCorrect,
                "DEM-103_View_Target_List_Detail",
                targetsListsPage::editTargetList,
                v -> editTargetsListPage,
                (page, data) -> page.addInformationFromData(data),
                editTargetsListPage::save,
                data -> data.get("name"),
                editTargetsListPage::isTargetListSavedSuccessfully,
                "DEM-103"
        );
    }

    @Test
    @Description("DEM-104")
    public void testDem104() {
        try {
            login("will", "will");
            TargetsListsPage targetsListsPage = new TargetsListsPage(driver);
            targetsListsPage.navigateToTargetsModule();
            targetsListsPage.navigateToViewTargetList();

            // get the first row case name before clicking
            String firstRowCaseName = targetsListsPage.getFirstRowNameLocator().getText().trim();

            targetsListsPage.clickFirstTargetList();

            Thread.sleep(2000);

            targetsListsPage.deleteTargetList();
            targetsListsPage.clickOkInDeleteDialog();

            // wait until return to view case
            Thread.sleep(2000);

            targetsListsPage.filterQuick(firstRowCaseName, false);

            boolean isFilterResultEmpty = targetsListsPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted case should no longer exist in the cases list");
            takeElementScreenshot("DEM-104_Deleted_TargetList_Filter_Result", driver.findElement(targetsListsPage.getFilterResult()));
            targetsListsPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }
}
