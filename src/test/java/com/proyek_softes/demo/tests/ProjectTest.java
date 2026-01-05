package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.projects.CreateProjectPage;
import com.proyek_softes.demo.pages.projects.ImportProjectPage;
import com.proyek_softes.demo.pages.projects.ProjectsPage;
import com.proyek_softes.demo.utils.ProjectDataProvider;

import io.qameta.allure.Description;

public class ProjectTest extends GenericCrudTestHelper<ProjectsPage, CreateProjectPage> {

    @Test(dataProvider = "createProjectData", dataProviderClass = ProjectDataProvider.class)
    @Description("DEM-105")
    public void testDem105(Map<String, String> testData) {
        try {
            login("will", "will");
            ProjectsPage projectsPage = new ProjectsPage(driver);
            projectsPage.navigateToProjectsModule();
            projectsPage.navigateToCreateProject();

            CreateProjectPage createProjectPage = new CreateProjectPage(driver, wait);
            createProjectPage.addInformationFromData(testData);
            createProjectPage.save();
            boolean isErrorMessagePresent = createProjectPage.isErrorMessagePresent();
            assertTrue(isErrorMessagePresent, "User without proper permissions should see an error message when trying to create a project");
            takeScreenshot("DEM-105_Create_Project_Without_Permission");
        } catch (Throwable e) {
            takeScreenshot("DEM-105_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }

    @Test(dataProvider = "viewProjectData", dataProviderClass = ProjectDataProvider.class)
    @Description("DEM-106")
    public void testDem106(Map<String, String> testData) {
        try {
            ProjectsPage projectsPage = new ProjectsPage(driver);
            testViewEntity(
                    testData,
                    v -> projectsPage,
                    projectsPage::navigateToProjectsModule,
                    projectsPage::navigateToViewProject,
                    projectsPage::clickFirstProject,
                    data -> data.get("name"),
                    projectsPage::isProjectTitleCorrect,
                    "DEM-106"
            );
        } catch (Throwable e) {
            takeScreenshot("DEM-106_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }

    @Test(dataProvider = "editProjectData", dataProviderClass = ProjectDataProvider.class)
    @Description("DEM-107")
    public void testDem107(Map<String, String> testData) {
        try {
            login("will", "will");
            ProjectsPage projectsPage = new ProjectsPage(driver);
            projectsPage.navigateToProjectsModule();
            projectsPage.navigateToViewProject();
            projectsPage.clickFirstProject();

            Thread.sleep(2000);

            boolean isOnDetailPage = projectsPage.isProjectTitleCorrect(testData.get("nameBeforeEdit"));
            assertTrue(isOnDetailPage, "Should be on detail page for the selected entity");
            takeScreenshot("DEM-107_View_Project_Detail");

            projectsPage.editProject();

            CreateProjectPage editProjectPage = new CreateProjectPage(driver, wait);
            editProjectPage.addInformationFromData(testData);
            editProjectPage.save();
            boolean isErrorMessagePresent = editProjectPage.isErrorMessagePresent();
            assertTrue(isErrorMessagePresent, "User without proper permissions should see an error message when trying to edit a project");
            takeScreenshot("DEM-107_Edit_Project_Without_Permission");
        } catch (Throwable e) {
            takeScreenshot("DEM-107_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }

    @Test
    @Description("DEM-108")
    public void testDem108() {
        try {
            login("will", "will");
            ProjectsPage projectsPage = new ProjectsPage(driver);
            projectsPage.navigateToProjectsModule();
            projectsPage.navigateToViewProject();

            // get the first row case name before clicking
            String firstRowCaseName = projectsPage.getFirstRowNameLocator().getText().trim();

            projectsPage.clickFirstProject();

            Thread.sleep(2000);

            projectsPage.deleteProject();
            projectsPage.clickOkInDeleteDialog();

            // wait until return to view case
            Thread.sleep(2000);

            projectsPage.filterQuick(firstRowCaseName, false, false);

            boolean isFilterResultEmpty = projectsPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted case should no longer exist in the cases list");
            takeElementScreenshot("DEM-108_Deleted_Project_Filter_Result", driver.findElement(projectsPage.getFilterResult()));
            projectsPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-109")
    public void testDem109() {
        login("will", "will");
        ProjectsPage projectsPage = new ProjectsPage(driver);
        projectsPage.navigateToProjectsModule();
        projectsPage.navigateToImportProject();

        ImportProjectPage importProjectPage = new ImportProjectPage(driver);
        boolean isCSV = importProjectPage.verifyDownloadedTemplateIsCSV(10, "DEM-109_Download_History");
        assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'projects'");
        // upload file and complete import process
        importProjectPage.uploadFile("Projects.csv");

        importProjectPage.clickImportCreate();
        importProjectPage.clickNext();
        importProjectPage.clickNext();
        importProjectPage.clickNext();
        importProjectPage.clickImportNow();

        boolean isRecordsImported = importProjectPage.isRecordsImported();
        assertTrue(isRecordsImported, "Records from Projects.csv should be imported successfully");
        takeElementScreenshot("DEM-109_Import_Projects_Success", importProjectPage.getSummaryElement());
    }

    @Test
    @Description("DEM-110")
    public void testDem110() {
        // test resource calendar
        login("will", "will");
        ProjectsPage projectsPage = new ProjectsPage(driver);
        projectsPage.navigateToProjectsModule();
        projectsPage.navigateToResourceCalendar();
        boolean isErrorMessagePresent = projectsPage.isErrorMessagePresent();
        assertTrue(isErrorMessagePresent, "User without proper permissions should see an error message when trying to access resource calendar");
        takeScreenshot("DEM-110_Resource_Calendar_Without_Permission");
    }

    @Test
    @Description("DEM-111")
    public void testDem111() {
        // test view project task
        login("will", "will");
        ProjectsPage projectsPage = new ProjectsPage(driver);
        projectsPage.navigateToProjectsModule();
        projectsPage.navigateToViewProjectTasks();

        boolean isInProjectTasksPage = projectsPage.isInProjectTasksPage();
        assertTrue(isInProjectTasksPage, "Should navigate to Project Tasks page");
        takeScreenshot("DEM-111_View_Project_Tasks_Page");

        // ProjectTasksPage projectsTaskPage = new ProjectTasksPage(driver);
        // String firstProjectTaskName = projectsTaskPage.getFirstRowNameLocator().getText().trim();
        // projectsTaskPage.clickFirstProjectTask();
        // boolean isTitleCorrect = projectsTaskPage.isProjectTaskTitleCorrect(firstProjectTaskName);
        // assertTrue(isTitleCorrect, "Project Task title should match the selected task from the list");
    }
}
