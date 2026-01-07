package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.email_templates.CreateEmailTemplatePage;
import com.proyek_softes.demo.pages.email_templates.EmailTemplatesPage;
import com.proyek_softes.demo.utils.EmailTemplateDataProvider;

import io.qameta.allure.Description;

public class EmailTemplateTest extends GenericCrudTestHelper<EmailTemplatesPage, CreateEmailTemplatePage> {
    @Test(dataProvider = "createEmailTemplateData", dataProviderClass = EmailTemplateDataProvider.class)
    @Description("DEM-154")
    public void testDem154(Map<String, String> testData) {
        CreateEmailTemplatePage createEmailTemplatePage = new CreateEmailTemplatePage(driver, wait);
        EmailTemplatesPage emailTemplatesPage = new EmailTemplatesPage(driver);
        testCreateEntity(
                testData,
                v -> emailTemplatesPage,
                emailTemplatesPage::navigateToEmailTemplatesModule,
                emailTemplatesPage::navigateToCreateEmailTemplate,
                v -> createEmailTemplatePage,
                (page, data) -> page.addInformationFromData(data),
                createEmailTemplatePage::save,
                data -> data.get("name"),
                createEmailTemplatePage::isEmailTemplateSavedSuccessfully,
                "DEM-154",
                emailTemplatesPage::navigateToViewEmailTemplates,
                emailTemplatesPage::isInFirstRow,
                v -> emailTemplatesPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewEmailTemplateData", dataProviderClass = EmailTemplateDataProvider.class)
    @Description("DEM-155")
    public void testDem155(Map<String, String> testData) {
        EmailTemplatesPage emailTemplatesPage = new EmailTemplatesPage(driver);

        testViewEntity(
                testData,
                v -> emailTemplatesPage,
                emailTemplatesPage::navigateToEmailTemplatesModule,
                emailTemplatesPage::navigateToViewEmailTemplates,
                emailTemplatesPage::clickFirstEmailTemplate,
                data -> data.get("name"),
                emailTemplatesPage::isEmailTemplateTitleCorrect,
                "DEM-155"
        );
    }

    @Test(dataProvider = "editEmailTemplateData", dataProviderClass = EmailTemplateDataProvider.class)
    @Description("DEM-156")
    public void testDem156(Map<String, String> testData) {
        EmailTemplatesPage emailTemplatesPage = new EmailTemplatesPage(driver);
        CreateEmailTemplatePage createEmailTemplatePage = new CreateEmailTemplatePage(driver, wait);
        testEditEntity(
                testData,
                v -> emailTemplatesPage,
                emailTemplatesPage::navigateToEmailTemplatesModule,
                emailTemplatesPage::navigateToViewEmailTemplates,
                emailTemplatesPage::clickFirstEmailTemplate,
                data -> data.get("nameBeforeEdit"),
                emailTemplatesPage::isEmailTemplateTitleCorrect,
                "DEM-156_View_EmailTemplate_Detail",
                emailTemplatesPage::navigateToEditEmailTemplate,
                v -> createEmailTemplatePage,
                (page, data) -> page.addInformationFromData(data),
                createEmailTemplatePage::save,
                data -> data.get("name"),
                createEmailTemplatePage::isEmailTemplateSavedSuccessfully,
                "DEM-156"
        );
    }

    @Test
    @Description("DEM-157")
    public void testDem157() {
        try {
            login("will", "will");
            EmailTemplatesPage emailTemplatesPage = new EmailTemplatesPage(driver);
            emailTemplatesPage.navigateToEmailTemplatesModule();
            emailTemplatesPage.navigateToViewEmailTemplates();
            String firstEmailTemplateName = emailTemplatesPage.getFirstRowNameLocator().getText().trim();
            emailTemplatesPage.clickFirstEmailTemplate();
            Thread.sleep(2000);

            emailTemplatesPage.deleteEmailTemplate();
            emailTemplatesPage.clickOkInDeleteDialog();
            // wait until return to view account
            Thread.sleep(2000);

            emailTemplatesPage.filterQuick(firstEmailTemplateName, null);

            boolean isFilterResultEmpty = emailTemplatesPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted email template should no longer exist in the email templates list");
            takeElementScreenshot("DEM-157_Deleted_EmailTemplate_Filter_Result", driver.findElement(emailTemplatesPage.getFilterResult()));
            emailTemplatesPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }
}
