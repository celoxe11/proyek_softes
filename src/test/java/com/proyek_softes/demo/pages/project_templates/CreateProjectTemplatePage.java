package com.proyek_softes.demo.pages.project_templates;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateProjectTemplatePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE_HEADER']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private final By inviteesSearchFirstName = By.id("search_first_name");
    private final By inviteesSearchLastName = By.id("search_last_name");
    private final By inviteesSearchEmail = By.id("search_email");
    private final By inviteesSearchButton = By.id("invitees_search");
    private final By inviteesAddButton1 = By.id("invitees_add_1");
    private final By inviteesListDiv = By.id("list_div_win");

    private final By addTaskButton = By.id("add_button");
    private final By taskDialogContent = By.id("template_dialog");

    // Task Dialog locators
    private final By taskDialogRadioSubtask = By.id("Subtask");
    private final By taskDialogRadioMilestone = By.id("Milestone");
    private final By taskDialogParentTask = By.id("parent_task");
    private final By taskDialogTaskName = By.id("task_name");
    private final By taskDialogPredecessor = By.id("Predecessor");
    private final By taskDialogRelationType = By.id("relation_type");
    private final By taskDialogDuration = By.id("Duration");
    private final By taskDialogDurationUnit = By.id("Duration_unit");
    private final By taskDialogResources = By.id("Resources");
    private final By taskDialogComplete = By.id("Complete");
    private final By taskDialogNotes = By.id("Notes");
    private final By taskDialogAddButton = By.xpath("//div[@class='ui-dialog-buttonset']//button[text()='Add']");
    private final By taskDialogCancelButton = By.xpath("//div[@class='ui-dialog-buttonset']//button[text()='Cancel']");

    private Map<String, By> inputLocators;
    private Map<String, By> taskDialogLocators;

    public CreateProjectTemplatePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
        initializeTaskDialogLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();

        // Basic information fields
        inputLocators.put("name", By.id("name"));
        inputLocators.put("status", By.id("status"));
        inputLocators.put("override_business_hours", By.id("override_business_hours"));
        inputLocators.put("priority", By.id("priority"));
        inputLocators.put("assigned_user_name", By.id("assigned_user_name"));
    }

    private void initializeTaskDialogLocators() {
        taskDialogLocators = new HashMap<>();

        // Task dialog fields
        taskDialogLocators.put("task_name", taskDialogTaskName);
        taskDialogLocators.put("predecessor", taskDialogPredecessor);
        taskDialogLocators.put("relation_type", taskDialogRelationType);
        taskDialogLocators.put("duration", taskDialogDuration);
        taskDialogLocators.put("duration_unit", taskDialogDurationUnit);
        taskDialogLocators.put("resources", taskDialogResources);
        taskDialogLocators.put("complete", taskDialogComplete);
        taskDialogLocators.put("notes", taskDialogNotes);
        taskDialogLocators.put("parent_task", taskDialogParentTask);
    }

    public void save() {
        try {
            WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(buttonSave));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", saveButton);
            Thread.sleep(300);

            try {
                wait.until(ExpectedConditions.elementToBeClickable(buttonSave)).click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                System.out.println("  Note: Using JavaScript click for Save button due to overlap");
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", saveButton);
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while saving", e);
        }
    }

    public void cancel() {
        driver.findElement(buttonCancel).click();
    }

    public void addInformationFromData(Map<String, String> data) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

            // Basic information fields
            fillInputFieldFromData("name", data.get("name"));
            selectDropdown("status", data.get("status"));
            fillCheckboxFromData("override_business_hours", data.get("override_business_hours"));
            selectDropdown("priority", data.get("priority"));

            // Add invitees if specified in test data
            String inviteeFirstName = data.get("invitee_first_name");
            if (inviteeFirstName != null && !inviteeFirstName.isEmpty()) {
                addInvitees(inviteeFirstName);
            }

            Thread.sleep(500);
            System.out.println("  → Filled project template data: " + data.get("name"));

        } catch (InterruptedException e) {
            throw new RuntimeException("Error filling project form: " + e.getMessage(), e);
        }
    }

    private void fillInputFieldFromData(String fieldName, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }

        By locator = inputLocators.get(fieldName);
        if (locator == null) {
            throw new IllegalArgumentException("No locator defined for field: " + fieldName);
        }

        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            Thread.sleep(200);

            element.clear();
            element.sendKeys(value);
            Thread.sleep(200);
        } catch (InterruptedException e) {
            System.err.println("Error filling field " + fieldName + ": " + e.getMessage());
            throw new RuntimeException("Failed to fill field: " + fieldName, e);
        }
    }

    private void selectDropdown(String fieldName, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }

        By locator = inputLocators.get(fieldName);
        if (locator == null) {
            throw new IllegalArgumentException("No locator defined for field: " + fieldName);
        }

        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            Thread.sleep(200);

            Select select = new Select(element);
            try {
                select.selectByVisibleText(value);
            } catch (Exception e) {
                try {
                    select.selectByValue(value);
                } catch (Exception e2) {
                    System.err.println("Could not select value " + value + " in dropdown " + fieldName);
                    throw e2;
                }
            }
            Thread.sleep(200);
        } catch (Exception e) {
            System.err.println("Error selecting dropdown " + fieldName + ": " + e.getMessage());
            throw new RuntimeException("Failed to select dropdown: " + fieldName, e);
        }
    }

    private void fillCheckboxFromData(String fieldName, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }

        By locator = inputLocators.get(fieldName);
        if (locator == null) {
            throw new IllegalArgumentException("No locator defined for field: " + fieldName);
        }

        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            Thread.sleep(200);

            boolean shouldBeChecked = value.equalsIgnoreCase("true")
                    || value.equalsIgnoreCase("1")
                    || value.equalsIgnoreCase("yes");

            if (shouldBeChecked != element.isSelected()) {
                element.click();
            }
            Thread.sleep(200);
        } catch (InterruptedException e) {
            System.err.println("Error filling checkbox " + fieldName + ": " + e.getMessage());
            throw new RuntimeException("Failed to fill checkbox: " + fieldName, e);
        }
    }

    public boolean isProjectSavedSuccessfully(String projectName) {
        try {
            By templateNameField = By.cssSelector("span.sugar_field#name");
            String actualName = wait.until(ExpectedConditions.presenceOfElementLocated(templateNameField)).getText().trim();
            System.out.println("Template name in detail view: " + actualName);
            System.out.println("Expected project name: " + projectName);
            return actualName.equalsIgnoreCase(projectName);
        } catch (Exception e) {
            System.out.println("  Warning: Could not verify project saved: " + e.getMessage());
            return false;
        }
    }

    /**
     * Add invitees to the call by searching for first name and adding the first
     * result
     *
     * @param inviteeFirstName The first name to search for
     */
    public void addInvitees(String inviteeFirstName) {
        try {
            // Scroll to the invitees section
            WebElement firstNameField = wait.until(ExpectedConditions.presenceOfElementLocated(inviteesSearchFirstName));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", firstNameField);
            Thread.sleep(300);

            // Enter first name in search field
            firstNameField.clear();
            firstNameField.sendKeys(inviteeFirstName);
            System.out.println("  → Searching for invitee with first name: " + inviteeFirstName);

            // Click search button
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(inviteesSearchButton));
            searchButton.click();

            // Wait for search results to load
            Thread.sleep(1000);
            wait.until(ExpectedConditions.presenceOfElementLocated(inviteesListDiv));

            // Click the first "Add" button in the results
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(inviteesAddButton1));
            addButton.click();
            System.out.println("  → Added first invitee from search results");

            Thread.sleep(500);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while adding invitees", e);
        } catch (Exception e) {
            System.out.println("  Warning: Could not add invitee: " + e.getMessage());
        }
    }

    public boolean addTask(Map<String, String> testData) {
        try {
            // Get tasks JSON string from test data
            String tasksJson = testData.get("tasks");
            if (tasksJson == null || tasksJson.isEmpty()) {
                System.out.println("  → No tasks to add");
                return true;
            }

            // Parse JSON manually (simple approach)
            tasksJson = tasksJson.trim();
            if (!tasksJson.startsWith("[") || !tasksJson.endsWith("]")) {
                System.out.println("  → Tasks JSON format invalid");
                return false;
            }

            String[] tasks = tasksJson.substring(1, tasksJson.length() - 1).split("\\},\\{");
            System.out.println("  → Parsed " + tasks.length + " task(s) from JSON");

            for (int i = 0; i < tasks.length; i++) {
                String task = tasks[i].replaceAll("[\\[\\]\\{\\}]", "");

                // Parse task fields
                Map<String, String> taskData = parseJsonObject(task);

                // Click Add Task button to open dialog
                WebElement addTaskBtn = wait.until(ExpectedConditions.elementToBeClickable(addTaskButton));
                addTaskBtn.click();
                System.out.println("  → Clicked Add Task button for task " + i);

                // Wait for dialog to open
                wait.until(ExpectedConditions.visibilityOfElementLocated(taskDialogContent));
                Thread.sleep(300);

                // Fill and submit task dialog
                addTaskFromDialog(taskData);

                // Verify task was added to the table
                String taskName = taskData.get("task_name");
                if (taskName != null && !taskName.isEmpty()) {
                    if (!isTaskInTable(taskName)) {
                        System.out.println("  ✗ Failed to add task to table: " + taskName);
                        return false;
                    }
                }
            }

            System.out.println("  → Added " + tasks.length + " task(s)");

            return true;

        } catch (Exception e) {
            System.out.println("  Warning: Could not add tasks: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Simple JSON object parser for key-value pairs
     */
    private Map<String, String> parseJsonObject(String json) {
        Map<String, String> result = new HashMap<>();
        String[] pairs = json.split(",");

        for (String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0].replaceAll("[\"'\\s]", "");
                String value = keyValue[1].replaceAll("[\"']", "").trim();
                result.put(key, value);
            }
        }

        return result;
    }

    /**
     * Add a task to the project template from the task dialog
     *
     * @param taskData Map containing task details (task_name, milestone_type,
     * duration, resources, etc.)
     */
    private void addTaskFromDialog(Map<String, String> taskData) {
        try {
            // Wait for dialog to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(taskDialogContent));
            System.out.println("  → Task dialog opened");

            // Select task type (Task/Milestone)
            String milestoneType = taskData.get("milestone_type");
            if (milestoneType != null && milestoneType.equalsIgnoreCase("Milestone")) {
                WebElement milestoneRadio = wait.until(ExpectedConditions.elementToBeClickable(taskDialogRadioMilestone));
                milestoneRadio.click();
                System.out.println("  → Selected Milestone");
            } else {
                WebElement subtaskRadio = wait.until(ExpectedConditions.elementToBeClickable(taskDialogRadioSubtask));
                if (!subtaskRadio.isSelected()) {
                    subtaskRadio.click();
                }
                System.out.println("  → Selected Task");
            }

            Thread.sleep(300);

            // Fill task name (required)
            fillTaskDialogField("task_name", taskData.get("task_name"));

            // Select predecessor if provided
            selectTaskDialogDropdown("predecessor", taskData.get("predecessor"));

            // Select relation type if provided
            selectTaskDialogDropdown("relation_type", taskData.get("relation_type"));

            // Fill duration
            fillTaskDialogField("duration", taskData.get("duration"));

            // Select duration unit if provided
            selectTaskDialogDropdown("duration_unit", taskData.get("duration_unit"));

            // Select assigned resource
            selectTaskDialogDropdown("resources", taskData.get("resources"));

            // Fill % complete
            fillTaskDialogField("complete", taskData.get("complete"));

            // Click Add button
            WebElement addButton = wait.until(ExpectedConditions.presenceOfElementLocated(taskDialogAddButton));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addButton);
            Thread.sleep(300);
            
            try {
                wait.until(ExpectedConditions.elementToBeClickable(taskDialogAddButton)).click();
            } catch (Exception e) {
                System.out.println("  Note: Using JavaScript click for Add button");
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", addButton);
            }
            System.out.println("  → Task added: " + taskData.get("task_name"));

            // Wait for dialog to close
            wait.until(ExpectedConditions.invisibilityOfElementLocated(taskDialogContent));
            Thread.sleep(500);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while adding task", e);
        } catch (Exception e) {
            System.out.println("  Error: Could not add task: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Fill a field in the task dialog
     */
    private void fillTaskDialogField(String fieldName, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }

        By locator = taskDialogLocators.get(fieldName);
        if (locator == null) {
            System.out.println("  Warning: No locator defined for task field: " + fieldName);
            return;
        }

        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            element.clear();
            element.sendKeys(value);
            System.out.println("  → Filled " + fieldName + ": " + value);
            Thread.sleep(200);
        } catch (Exception e) {
            System.out.println("  Warning: Could not fill task field " + fieldName + ": " + e.getMessage());
        }
    }

    /**
     * Select a dropdown option in the task dialog
     */
    private void selectTaskDialogDropdown(String fieldName, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }

        By locator = taskDialogLocators.get(fieldName);
        if (locator == null) {
            System.out.println("  Warning: No locator defined for task dropdown: " + fieldName);
            return;
        }

        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            Select select = new Select(element);

            try {
                select.selectByVisibleText(value);
            } catch (Exception e) {
                try {
                    select.selectByValue(value);
                } catch (Exception e2) {
                    System.out.println("  Warning: Could not select value " + value + " in dropdown " + fieldName);
                }
            }
            System.out.println("  → Selected " + fieldName + ": " + value);
            Thread.sleep(200);
        } catch (Exception e) {
            System.out.println("  Warning: Could not select task dropdown " + fieldName + ": " + e.getMessage());
        }
    }

    /**
     * Cancel the task dialog
     */
    public void cancelTaskDialog() {
        try {
            WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(taskDialogCancelButton));
            cancelButton.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(taskDialogContent));
            System.out.println("  → Task dialog cancelled");
        } catch (Exception e) {
            System.out.println("  Warning: Could not cancel task dialog: " + e.getMessage());
        }
    }

    /**
     * Check if a task exists in the task table
     *
     * @param taskName The name of the task to check
     * @return true if task exists in the table, false otherwise
     */
    public boolean isTaskInTable(String taskName) {
        try {
            By taskTable = By.id("Task_table");
            wait.until(ExpectedConditions.presenceOfElementLocated(taskTable));

            // Find all task name links in the table
            By taskNameLinks = By.cssSelector("#Task_table .Task_name a");
            java.util.List<WebElement> taskLinks = driver.findElements(taskNameLinks);

            for (WebElement taskLink : taskLinks) {
                String actualTaskName = taskLink.getText().trim();
                if (actualTaskName.equalsIgnoreCase(taskName)) {
                    System.out.println("  ✓ Task found in table: " + taskName);
                    return true;
                }
            }

            System.out.println("  ✗ Task not found in table: " + taskName);
            return false;

        } catch (Exception e) {
            System.out.println("  Warning: Could not verify task in table: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the count of tasks in the task table
     *
     * @return number of tasks in the table
     */
    public int getTaskCount() {
        try {
            By taskTable = By.id("Task_table");
            wait.until(ExpectedConditions.presenceOfElementLocated(taskTable));

            By taskRows = By.cssSelector("#Task_table .row_sortable");
            return driver.findElements(taskRows).size();

        } catch (Exception e) {
            System.out.println("  Warning: Could not count tasks: " + e.getMessage());
            return 0;
        }
    }
}
