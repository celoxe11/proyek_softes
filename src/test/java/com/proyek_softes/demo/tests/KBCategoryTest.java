package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.kb_categories.CreateKBCategoryPage;
import com.proyek_softes.demo.pages.kb_categories.KBCategoriesPage;
import com.proyek_softes.demo.utils.KBCategoryDataProvider;

import io.qameta.allure.Description;

public class KBCategoryTest extends GenericCrudTestHelper<KBCategoriesPage, CreateKBCategoryPage> {
    /**
     * DEM-150
     * KB Categories - Create
     */
    @Test(dataProvider = "createKBCategoryData", dataProviderClass = KBCategoryDataProvider.class)
    @Description("DEM-150")
    public void testDem150(Map<String, String> testData) {
        KBCategoriesPage kbCategoriesPage = new KBCategoriesPage(driver);
        CreateKBCategoryPage createKBCategoryPage =
                new CreateKBCategoryPage(driver, wait);

        testCreateEntity(
            testData,
            v -> kbCategoriesPage,
            kbCategoriesPage::navigateToKBCategoriesModule,
            kbCategoriesPage::navigateToCreateKBCategory,
            v -> createKBCategoryPage,
            (page, data) -> page.addInformationFromData(data),
            createKBCategoryPage::save,
            data -> data.get("name"),
            createKBCategoryPage::isKBCategorySavedSuccessfully,
            "DEM-150",
            kbCategoriesPage::navigateToViewKBCategory,
            kbCategoriesPage::isInFirstRow,
            v -> kbCategoriesPage.getFirstRowLocator()
        );
    }

    /**
     * DEM-151
     * KB Categories - View
     */
    @Test(dataProvider = "viewKBCategoryData", dataProviderClass = KBCategoryDataProvider.class)
    @Description("DEM-151")
    public void testDem151(Map<String, String> testData) {
        KBCategoriesPage kbCategoriesPage = new KBCategoriesPage(driver);

        testViewEntity(
            testData,
            v -> kbCategoriesPage,
            kbCategoriesPage::navigateToKBCategoriesModule,
            kbCategoriesPage::navigateToViewKBCategory,
            kbCategoriesPage::clickFirstKBCategory,
            data -> data.get("name"),
            kbCategoriesPage::isKBCategoryTitleCorrect,
            "DEM-151"
        );
    }

    /**
     * DEM-152
     * KB Categories - View (Edit)
     */
    @Test(dataProvider = "editKBCategoryData", dataProviderClass = KBCategoryDataProvider.class)
    @Description("DEM-152")
    public void testDem152(Map<String, String> testData) {
        KBCategoriesPage kbCategoriesPage = new KBCategoriesPage(driver);
        CreateKBCategoryPage editKBCategoryPage = new CreateKBCategoryPage(driver, wait);

        testEditEntity(
            testData,
            v -> kbCategoriesPage,
            kbCategoriesPage::navigateToKBCategoriesModule,
            kbCategoriesPage::navigateToViewKBCategory,
            kbCategoriesPage::clickFirstKBCategory,
            data -> data.get("nameBeforeEdit"),
            kbCategoriesPage::isKBCategoryTitleCorrect,
            "DEM-152_View_KBCategory_Detail",
            kbCategoriesPage::editKBCategory,
            v -> editKBCategoryPage,
            (page, data) -> page.addInformationFromData(data),
            editKBCategoryPage::save,
            data -> data.get("name"),
            editKBCategoryPage::isKBCategorySavedSuccessfully,
            "DEM-152"
        );
    }

    /**
     * DEM-153
     * KB Categories - View (Delete)
     */
    @Test
    @Description("DEM-153")
    public void testDem153() {
        try {
            login("will", "will");

            KBCategoriesPage kbCategoriesPage = new KBCategoriesPage(driver);
            kbCategoriesPage.navigateToKBCategoriesModule();
            kbCategoriesPage.navigateToViewKBCategory();

            String firstRowKBCategoryName = kbCategoriesPage
                .getFirstRowNameLocator()
                .getText()
                .trim();

            kbCategoriesPage.clickFirstKBCategory();
            Thread.sleep(2000);

            kbCategoriesPage.deleteKBCategory();
            kbCategoriesPage.clickOkInDeleteDialog();
            Thread.sleep(2000);

            kbCategoriesPage.filterQuick(firstRowKBCategoryName, false);

            boolean isFilterResultEmpty = kbCategoriesPage.isFilterResultEmpty();
            assertTrue(
                isFilterResultEmpty,
                "Deleted KB category should no longer exist in the KB categories list"
            );

            takeElementScreenshot(
                "DEM-153_Deleted_KBCategory_Filter_Result",
                driver.findElement(kbCategoriesPage.getFilterResult())
            );

            kbCategoriesPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }
}
