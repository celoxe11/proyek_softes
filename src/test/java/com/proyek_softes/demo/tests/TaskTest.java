package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.tasks.CreateTaskPage;
import com.proyek_softes.demo.pages.tasks.ImportTaskPage;
import com.proyek_softes.demo.pages.tasks.TasksPage;
import com.proyek_softes.demo.utils.TaskDataProvider;

import io.qameta.allure.Description;

public class TaskTest extends BaseTest {

    @Test(dataProvider = "createTaskData", dataProviderClass = TaskDataProvider.class)
    @Description("DEM-070")
    public void testDem070(Map<String, String> testData) {
        login("will", "will");
        TasksPage tasksPage = new TasksPage(driver);
        tasksPage.navigateToTasksModule();
        tasksPage.navigateToCreateTask();

        CreateTaskPage createTaskPage = new CreateTaskPage(driver, wait);
        createTaskPage.addInformationFromData(testData);
        createTaskPage.save();

        String taskName = testData.get("name");
        boolean isSaved = createTaskPage.isTaskSavedSuccessfully(taskName);
        assertTrue(isSaved, "Task should be saved successfully");

        takeScreenshot("DEM-070_Create_Task");

        tasksPage.navigateToViewTask();
        boolean isInFirstRow = tasksPage.isInFirstRow(taskName);
        assertTrue(isInFirstRow, "Created task should appear in the first row of tasks list");

        takeElementScreenshot("DEM-070_Task_In_List", tasksPage.getFirstRowLocator());
    }

    @Test(dataProvider = "viewTaskData", dataProviderClass = TaskDataProvider.class)
    @Description("DEM-071")
    public void testDem071(Map<String, String> testData) {
        try {
            login("will", "will");
            TasksPage tasksPage = new TasksPage(driver);
            tasksPage.navigateToTasksModule();
            tasksPage.navigateToViewTask();

            tasksPage.clickFirstTask();

            Thread.sleep(2000);

            String taskName = testData.get("name");
            boolean isOnTaskDetailPage = tasksPage.isTaskTitleCorrect(taskName);
            assertTrue(isOnTaskDetailPage, "Should be on Task Detail page for the selected task");
            takeScreenshot("DEM-071_View_Task_Detail");
        } catch (InterruptedException e) {
        }

    }

    @Test(dataProvider = "editTaskData", dataProviderClass = TaskDataProvider.class)
    @Description("DEM-072")
    public void testDem072(Map<String, String> testData) {
        try {
            login("will", "will");
            TasksPage tasksPage = new TasksPage(driver);
            tasksPage.navigateToTasksModule();
            tasksPage.navigateToViewTask();

            tasksPage.clickFirstTask();

            Thread.sleep(2000);

            String taskNameBeforeEdit = testData.get("nameBeforeEdit");
            boolean isOnTaskDetailPage = tasksPage.isTaskTitleCorrect(taskNameBeforeEdit);
            assertTrue(isOnTaskDetailPage, "Should be on Task Detail page for the selected task");
            takeScreenshot("DEM-072_View_Task_Detail_Before_Edit");
            tasksPage.editTask();

            CreateTaskPage editTaskPage = new CreateTaskPage(driver, wait);
            editTaskPage.addInformationFromData(testData);
            editTaskPage.save();

            String taskName = testData.get("name");
            boolean isSaved = editTaskPage.isTaskSavedSuccessfully(taskName);
            assertTrue(isSaved, "Task should be saved successfully after editing");

            takeScreenshot("DEM-072_Edit_Task");
        } catch (InterruptedException e) {
        }
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
