package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.tasks.CreateTaskPage;
import com.proyek_softes.demo.pages.tasks.ImportTaskPage;
import com.proyek_softes.demo.pages.tasks.TasksPage;
import com.proyek_softes.demo.utils.TaskDataProvider;

import io.qameta.allure.Description;

public class TaskTest extends GenericCrudTestHelper<TasksPage, CreateTaskPage> {

    @Test(dataProvider = "createTaskData", dataProviderClass = TaskDataProvider.class)
    @Description("DEM-070")
    public void testDem070(Map<String, String> testData) {
        TasksPage tasksPage = new TasksPage(driver);
        CreateTaskPage createTaskPage = new CreateTaskPage(driver, wait);
        
        testCreateEntity(
            testData,
            v -> tasksPage,
            tasksPage::navigateToTasksModule,
            tasksPage::navigateToCreateTask,
            v -> createTaskPage,
            (page, data) -> page.addInformationFromData(data),
            createTaskPage::save,
            data -> data.get("name"),
            createTaskPage::isTaskSavedSuccessfully,
            "DEM-070",
            tasksPage::navigateToViewTask,
            tasksPage::isInFirstRow,
            v -> tasksPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewTaskData", dataProviderClass = TaskDataProvider.class)
    @Description("DEM-071")
    public void testDem071(Map<String, String> testData) {
        TasksPage tasksPage = new TasksPage(driver);
        
        testViewEntity(
            testData,
            v -> tasksPage,
            tasksPage::navigateToTasksModule,
            tasksPage::navigateToViewTask,
            tasksPage::clickFirstTask,
            data -> data.get("name"),
            tasksPage::isTaskTitleCorrect,
            "DEM-071"
        );
    }

    @Test(dataProvider = "editTaskData", dataProviderClass = TaskDataProvider.class)
    @Description("DEM-072")
    public void testDem072(Map<String, String> testData) {
        TasksPage tasksPage = new TasksPage(driver);
        CreateTaskPage editTaskPage = new CreateTaskPage(driver, wait);
        
        testEditEntity(
            testData,
            v -> tasksPage,
            tasksPage::navigateToTasksModule,
            tasksPage::navigateToViewTask,
            tasksPage::clickFirstTask,
            data -> data.get("nameBeforeEdit"),
            tasksPage::isTaskTitleCorrect,
            "DEM-072_View_Task_Detail",
            tasksPage::editTask,
            v -> editTaskPage,
            (page, data) -> page.addInformationFromData(data),
            editTaskPage::save,
            data -> data.get("name"),
            editTaskPage::isTaskSavedSuccessfully,
            "DEM-072"
        );
    }

    @Test
    @Description("DEM-073")
    public void testDem073() {
        try {
            login("will", "will");
            TasksPage tasksPage = new TasksPage(driver);
            tasksPage.navigateToTasksModule();
            tasksPage.navigateToViewTask();

            String firstRowTaskName = tasksPage.getFirstRowNameLocator().getText().trim();

            tasksPage.clickFirstTask();

            Thread.sleep(2000);

            tasksPage.deleteTask();
            tasksPage.clickOkInDeleteDialog();
            Thread.sleep(2000);

            tasksPage.filterQuick(firstRowTaskName, false, false, false);

            boolean isFilterResultEmpty = tasksPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted task should no longer exist in the tasks list");
            takeElementScreenshot("DEM-073_Deleted_Task_Filter_Result", driver.findElement(tasksPage.getFilterResult()));

            tasksPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-074")
    public void testDem074() {
        login("will", "will");
        TasksPage tasksPage = new TasksPage(driver);
        tasksPage.navigateToTasksModule();
        tasksPage.navigateToImportTask();

        ImportTaskPage importTaskPage = new ImportTaskPage(driver);
        boolean isCSV = importTaskPage.verifyDownloadedTemplateIsCSV(10, "DEM-074_Download_History");
        assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'tasks'");
        
        // upload file and complete import process
        importTaskPage.uploadFile("Tasks.csv");

        importTaskPage.clickImportCreate();
        importTaskPage.clickNext();
        importTaskPage.clickNext();
        importTaskPage.clickNext();
        importTaskPage.clickImportNow();
        
        boolean isRecordsImported = importTaskPage.isRecordsImported();
        assertTrue(isRecordsImported, "Records from Tasks.csv should be imported successfully");
        takeElementScreenshot("DEM-074_Import_Tasks_Success", importTaskPage.getSummaryElement());
    }

}
