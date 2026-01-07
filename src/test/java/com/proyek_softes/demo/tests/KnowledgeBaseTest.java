package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.knowledge_base.CreateKnowledgeBasePage;
import com.proyek_softes.demo.pages.knowledge_base.KnowledgeBasesPage;
import com.proyek_softes.demo.utils.KnowledgeBaseDataProvider;

import io.qameta.allure.Description;

public class KnowledgeBaseTest extends GenericCrudTestHelper<KnowledgeBasesPage, CreateKnowledgeBasePage> {
    @Test(dataProvider = "createKnowledgeBaseData", dataProviderClass = KnowledgeBaseDataProvider.class)
    @Description("DEM-146")
    public void testDem146(Map<String, String> testData) {
        CreateKnowledgeBasePage createKnowledgeBasePage = new CreateKnowledgeBasePage(driver, wait);
        KnowledgeBasesPage knowledgeBasesPage = new KnowledgeBasesPage(driver);
        testCreateEntity(
                testData,
                v -> knowledgeBasesPage,
                knowledgeBasesPage::navigateToKnowledgeBaseModule,
                knowledgeBasesPage::navigateToCreateKnowledgeBase,
                v -> createKnowledgeBasePage,
                (page, data) -> page.addInformationFromData(data),
                createKnowledgeBasePage::save,
                data -> data.get("name"),
                createKnowledgeBasePage::isKnowledgeBaseSavedSuccessfully,
                "DEM-146",
                knowledgeBasesPage::navigateToViewKnowledgeBase,
                knowledgeBasesPage::isInFirstRow,
                v -> knowledgeBasesPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewKnowledgeBaseData", dataProviderClass = KnowledgeBaseDataProvider.class)
    @Description("DEM-147")
    public void testDem147(Map<String, String> testData) {
        KnowledgeBasesPage knowledgeBasesPage = new KnowledgeBasesPage(driver);

        testViewEntity(
                testData,
                v -> knowledgeBasesPage,
                knowledgeBasesPage::navigateToKnowledgeBaseModule,
                knowledgeBasesPage::navigateToViewKnowledgeBase,
                knowledgeBasesPage::clickFirstKnowledgeBase,
                data -> data.get("name"),
                knowledgeBasesPage::isKnowledgeBaseTitleCorrect,
                "DEM-147"
        );
    }

    @Test(dataProvider = "editKnowledgeBaseData", dataProviderClass = KnowledgeBaseDataProvider.class)
    @Description("DEM-148")
    public void testDem148(Map<String, String> testData) {
        KnowledgeBasesPage knowledgeBasesPage = new KnowledgeBasesPage(driver);
        CreateKnowledgeBasePage createKnowledgeBasePage = new CreateKnowledgeBasePage(driver, wait);
        testEditEntity(
                testData,
                v -> knowledgeBasesPage,
                knowledgeBasesPage::navigateToKnowledgeBaseModule,
                knowledgeBasesPage::navigateToViewKnowledgeBase,
                knowledgeBasesPage::clickFirstKnowledgeBase,
                data -> data.get("nameBeforeEdit"),
                knowledgeBasesPage::isKnowledgeBaseTitleCorrect,
                "DEM-148_View_KnowledgeBase_Detail",
                knowledgeBasesPage::navigateToEditKnowledgeBase,
                v -> createKnowledgeBasePage,
                (page, data) -> page.addInformationFromData(data),
                createKnowledgeBasePage::save,
                data -> data.get("name"),
                createKnowledgeBasePage::isKnowledgeBaseSavedSuccessfully,
                "DEM-148"
        );
    }

    @Test
    @Description("DEM-149")
    public void testDem149() {
        try {
            login("will", "will");
            KnowledgeBasesPage knowledgeBasesPage = new KnowledgeBasesPage(driver);
            knowledgeBasesPage.navigateToKnowledgeBaseModule();
            knowledgeBasesPage.navigateToViewKnowledgeBase();
            String firstKnowledgeBaseName = knowledgeBasesPage.getFirstRowNameLocator().getText().trim();
            knowledgeBasesPage.clickFirstKnowledgeBase();
            Thread.sleep(2000);

            knowledgeBasesPage.deleteKnowledgeBase();
            knowledgeBasesPage.clickOkInDeleteDialog();
            // wait until return to view account
            Thread.sleep(2000);

            knowledgeBasesPage.filterQuick(firstKnowledgeBaseName, false);

            boolean isFilterResultEmpty = knowledgeBasesPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted knowledge base should no longer exist in the knowledge bases list");
            takeElementScreenshot("DEM-149_Deleted_KnowledgeBase_Filter_Result", driver.findElement(knowledgeBasesPage.getFilterResult()));
            knowledgeBasesPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

}
