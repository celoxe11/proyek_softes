package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.project_tasks.ImportProjectTaskPage;
import com.proyek_softes.demo.pages.project_tasks.ProjectTasksPage;

import com.proyek_softes.demo.utils.ProjectTaskDataProvider;

import io.qameta.allure.Description;

public class ProjectTaskTest extends GenericCrudTestHelper<ProjectTasksPage, Object> {

    /**
     * DEM-165
     * Project Tasks - Create Project
     * Mengarahkan ke menu Create Project.
     */
    @Test(dataProvider = "createProjectData", dataProviderClass = ProjectTaskDataProvider.class)
    @Description("DEM-165")
    public void testDem165(Map<String, String> testData) {
        login("will", "will");
        ProjectTasksPage projectTasksPage = new ProjectTasksPage(driver);
        projectTasksPage.navigateToProjectTasksModule();
        projectTasksPage.navigateToCreateProject();

        boolean isInCreateProjectPage = projectTasksPage.isInCreateProjectPage();
        assertTrue(isInCreateProjectPage, "Should navigate to Create Project page");
        takeScreenshot("DEM-165_Create_Project_Page");
    }

    /**
     * DEM-166
     * Project Tasks - Project List
     * Mengarahkan ke menu View Projects Lists.
     */
    @Test
    @Description("DEM-166")
    public void testDem166() {
        login("will", "will");
        ProjectTasksPage projectTasksPage = new ProjectTasksPage(driver);
        projectTasksPage.navigateToProjectTasksModule();
        projectTasksPage.navigateToProjectList();

        boolean isInProjectListPage = projectTasksPage.isInProjectListPage();
        assertTrue(isInProjectListPage, "Should navigate to Project List page");
        takeScreenshot("DEM-166_Project_List_Page");
    }

    /**
     * DEM-167
     * Project Tasks - Project Tasks
     * Mengarahkan ke menu View Project Tasks.
     */
    @Test
    @Description("DEM-167")
    public void testDem167() {
        login("will", "will");
        ProjectTasksPage projectTasksPage = new ProjectTasksPage(driver);
        projectTasksPage.navigateToProjectTasksModule();
        projectTasksPage.navigateToProjectTasks();

        boolean isInProjectTasksPage = projectTasksPage.isInProjectTasksPage();
        assertTrue(isInProjectTasksPage, "Should navigate to Project Tasks page");
        takeScreenshot("DEM-167_Project_Tasks_Page");
    }

    /**
     * DEM-168
     * Project Tasks - Import
     * Melakukan insert dan update project tasks dengan mengimpor file .csv.
     */
    @Test
    @Description("DEM-168")
    public void testDem168() {
        try {
            login("will", "will");

            ProjectTasksPage projectTasksPage = new ProjectTasksPage(driver);
            projectTasksPage.navigateToProjectTasksModule();
            projectTasksPage.navigateToImportProjectTask();

            ImportProjectTaskPage importProjectTaskPage = new ImportProjectTaskPage(driver);
            boolean isCSV = importProjectTaskPage.verifyDownloadedTemplateIsCSV(10, "DEM-168_Download_History");
            assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'project tasks'");

            // upload file and complete import process
            importProjectTaskPage.uploadFile("Project_Tasks.csv");

            importProjectTaskPage.clickImportCreate();
            importProjectTaskPage.clickNext();
            importProjectTaskPage.clickNext();
            importProjectTaskPage.clickNext();
            importProjectTaskPage.clickImportNow();

            boolean isRecordsImported = importProjectTaskPage.isRecordsImported();
            assertTrue(isRecordsImported, "Records from Project_Tasks.csv should be imported successfully");
            takeElementScreenshot("DEM-168_Import_Project_Tasks_Success", importProjectTaskPage.getSummaryElement());

        } catch (Exception e) {
            takeScreenshot("DEM-168_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }
}