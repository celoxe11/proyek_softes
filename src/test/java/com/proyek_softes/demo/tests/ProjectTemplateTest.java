package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.contracts.ContractsPage;
import com.proyek_softes.demo.pages.contracts.ImportContractPage;
import com.proyek_softes.demo.pages.project_templates.CreateProjectTemplatePage;
import com.proyek_softes.demo.pages.project_templates.ImportProjectTemplatesPage;
import com.proyek_softes.demo.pages.project_templates.ProjectTemplatesPage;
import com.proyek_softes.demo.utils.ProjectTemplateDataProvider;

import io.qameta.allure.Description;

public class ProjectTemplateTest extends GenericCrudTestHelper<ProjectTemplatesPage, CreateProjectTemplatePage> {

    @Test(dataProvider = "createProjectTemplateData", dataProviderClass = ProjectTemplateDataProvider.class)
    @Description("DEM-112")
    public void testDem112(Map<String, String> testData) {
        try {
            login("will", "will");
            ProjectTemplatesPage projectTemplatesPage = new ProjectTemplatesPage(driver);
            projectTemplatesPage.navigateToProjectTemplatesModule();
            projectTemplatesPage.navigateToCreateProjectTemplate();

            CreateProjectTemplatePage createProjectTemplatePage = new CreateProjectTemplatePage(driver, wait);
            createProjectTemplatePage.addInformationFromData(testData);
            createProjectTemplatePage.save();
            boolean allTasksAdded = createProjectTemplatePage.addTask(testData);
            if (!allTasksAdded) {
                takeScreenshot("DEM-112_Error_Adding_Tasks");
                throw new AssertionError("Test failed: Not all tasks were added successfully");
            }

            Thread.sleep(3000);

            boolean isProjectSavedSuccessfully = createProjectTemplatePage.isProjectSavedSuccessfully(testData.get("name"));
            assertTrue(isProjectSavedSuccessfully, "Project Template should be saved successfully and appear in the list");
            takeScreenshot("DEM-112_Create_Project_Template_Success");

            projectTemplatesPage.navigateToViewProjectTemplate();
            boolean isInFirstRow = projectTemplatesPage.isInFirstRow(testData.get("name"));
            assertTrue(isInFirstRow, "Project Template should appear in the first row of the list after creation");
            takeElementScreenshot("DEM-112_Project_Template_In_List", projectTemplatesPage.getFirstRowLocator());

        } catch (InterruptedException e) {
            takeScreenshot("DEM-112_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }

    @Test(dataProvider = "viewProjectTemplateData", dataProviderClass = ProjectTemplateDataProvider.class)
    @Description("DEM-113")
    public void testDem113(Map<String, String> testData) {
        try {
            ProjectTemplatesPage projectTemplatesPage = new ProjectTemplatesPage(driver);

            testViewEntity(
                    testData,
                    v -> projectTemplatesPage,
                    projectTemplatesPage::navigateToProjectTemplatesModule,
                    projectTemplatesPage::navigateToViewProjectTemplate,
                    projectTemplatesPage::clickFirstProject,
                    data -> data.get("name"),
                    projectTemplatesPage::isProjectTemplateTitleCorrect,
                    "DEM-113"
            );
        } catch (Exception e) {
            takeScreenshot("DEM-113_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }

    @Test(dataProvider = "editProjectTemplateData", dataProviderClass = ProjectTemplateDataProvider.class)
    @Description("DEM-114")
    public void testDem114(Map<String, String> testData) {
        try {
            login("will", "will");
            ProjectTemplatesPage projectTemplatesPage = new ProjectTemplatesPage(driver);
            projectTemplatesPage.navigateToProjectTemplatesModule();
            projectTemplatesPage.navigateToViewProjectTemplate();
            projectTemplatesPage.clickFirstProject();

            Thread.sleep(2000);

            boolean isOnDetailPage = projectTemplatesPage.isProjectTemplateTitleCorrect(testData.get("nameBeforeEdit"));
            assertTrue(isOnDetailPage, "Should be on the detail page of the selected Project Template before editing");
            takeScreenshot("DEM-114_View_Project_Template_Detail_Before_Edit");

            projectTemplatesPage.editProjectTemplate();

            CreateProjectTemplatePage editProjectTemplatePage = new CreateProjectTemplatePage(driver, wait);
            editProjectTemplatePage.addInformationFromData(testData);
            editProjectTemplatePage.save();

            boolean isProjectSavedSuccessfully = editProjectTemplatePage.isProjectSavedSuccessfully(testData.get("name"));
            assertTrue(isProjectSavedSuccessfully, "Edited Project Template should be saved successfully");
            takeScreenshot("DEM-114_Edit_Project_Template_Success");

        } catch (InterruptedException e) {
            takeScreenshot("DEM-114_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }

    @Test
    @Description("DEM-115")
    public void testDem115() {
        try {
            login("will", "will");
            ProjectTemplatesPage projectTemplatesPage = new ProjectTemplatesPage(driver);
            projectTemplatesPage.navigateToProjectTemplatesModule();
            projectTemplatesPage.navigateToViewProjectTemplate();
            String firstProjectTemplateName = projectTemplatesPage.getFirstRowNameLocator().getText().trim();
            projectTemplatesPage.clickFirstProject();
            Thread.sleep(2000);

            projectTemplatesPage.deleteProjectTemplate();
            projectTemplatesPage.clickOkInDeleteDialog();

            // wait until return to view account
            Thread.sleep(2000);

            projectTemplatesPage.filterQuick(firstProjectTemplateName, false);

            boolean isFilterResultEmpty = projectTemplatesPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted project template should no longer exist in the project templates list");
            takeElementScreenshot("DEM-115_Deleted_Project_Template_Filter_Result", driver.findElement(projectTemplatesPage.getFilterResult()));
            projectTemplatesPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-116")
    public void testDem116() {
        try {
            login("will", "will");
            ProjectTemplatesPage projectTemplatesPage = new ProjectTemplatesPage(driver);
            projectTemplatesPage.navigateToProjectTemplatesModule();
            projectTemplatesPage.navigateToViewProjectTemplate();

            ImportProjectTemplatesPage importProjectTemplatesPage = new ImportProjectTemplatesPage(driver);
            boolean isCSV = importProjectTemplatesPage.verifyDownloadedTemplateIsCSV(10, "DEM-116_Download_History");
            assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'invoices'");

            // upload file and complete import process
            importProjectTemplatesPage.uploadFile("Project - Templates.csv");

            importProjectTemplatesPage.clickImportCreate();
            importProjectTemplatesPage.clickNext();
            importProjectTemplatesPage.clickNext();
            importProjectTemplatesPage.clickNext();
            importProjectTemplatesPage.clickImportNow();

            boolean isRecordsImported = importProjectTemplatesPage.isRecordsImported();
            assertTrue(isRecordsImported, "Records from Project - Templates.csv should be imported successfully");
            takeElementScreenshot("DEM-116_Import_Project_Templates_Success", importProjectTemplatesPage.getSummaryElement());

        } catch (Throwable e) {
            takeScreenshot("DEM-116_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }
}
