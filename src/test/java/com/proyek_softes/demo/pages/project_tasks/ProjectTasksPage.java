package com.proyek_softes.demo.pages.project_tasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProjectTasksPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Navbar untuk navigasi ke Project Tasks
    private final By navTab = By.id("grouptab_5");
    private final By subTab = By.xpath("//a[@href='index.php?module=Project&action=index&parentTab=All']");

    private final By projectTaskLink = By.xpath("//div[@class='actionmenulink' and text()='Project Tasks']");
    private final By projectListLink = By.xpath("//div[@class='actionmenulink' and text()='Project List']");
    private final By createProjectTaskLink = By.xpath("//div[@class='actionmenulink' and text()='Create Project']");
    private final By viewProjectTaskLink = By.xpath("//div[@class='actionmenulink' and text()='View Project Tasks']");
    private final By importProjectTaskLink = By
            .xpath("//div[@class='actionmenulink' and text()='Import Project Tasks']");

    private final By firstRowProjectTaskName = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
    private final By firstRowLocator = By.cssSelector("table.list.view tbody tr[height='20']:first-of-type");
    private final By filterResult = By.className("msg");

    // filter locators
    private final By filterButton = By.xpath("//a[@title='Filter']");
    private final By quickFilterTab = By
            .xpath("//li[contains(@class, 'searchTabHandler') and contains(@class, 'basic')]/a");
    private final By modalContent = By.className("modal-content");
    private final By filterNameField = By.id("name_basic");
    private final By filterMyItemsCheckbox = By.id("current_user_only_basic");
    private final By filterFavoritesCheckbox = By.id("favorites_only_basic");
    private final By filterSubmitButton = By.id("search_form_submit");
    private final By filterClearButton = By.id("search_form_clear");

    // detail page locators
    private final By tabActionsInDetail = By.id("tab-actions");
    private final By editButtonInDetail = By.id("edit_button");
    private final By deleteButtonInDetail = By.id("delete_button");

    private final By pageTitle = By.className("module-title-text");

    public ProjectTasksPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    public boolean checkPageTitle(String expectedTitle) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("module-title-text")));
            String title = driver.findElement(By.className("module-title-text")).getText();
            return title.toUpperCase().contains(expectedTitle.toUpperCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToProjectTasksModule() {
        // First navigate to Projects module (same as ProjectsPage)
        wait.until(ExpectedConditions.presenceOfElementLocated(navTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(navTab));
        actions.moveToElement(driver.findElement(navTab)).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subTab));
        driver.findElement(subTab).click();

        // Then navigate to View Project Tasks (using updated locator)
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewProjectTaskLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewProjectTaskLink));
        driver.findElement(viewProjectTaskLink).click();
    }

    public void navigateToCreateProject() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createProjectTaskLink));
        wait.until(ExpectedConditions.elementToBeClickable(createProjectTaskLink));
        driver.findElement(createProjectTaskLink).click();
    }

    public void navigateToProjectList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(projectListLink));
        wait.until(ExpectedConditions.elementToBeClickable(projectListLink));
        driver.findElement(projectListLink).click();
    }

    public void navigateToProjectTasks() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(projectTaskLink));
        wait.until(ExpectedConditions.elementToBeClickable(projectTaskLink));
        driver.findElement(projectTaskLink).click();
    }

    public void navigateToImportProjectTask() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(importProjectTaskLink));
        wait.until(ExpectedConditions.elementToBeClickable(importProjectTaskLink));
        driver.findElement(importProjectTaskLink).click();
    }

    public boolean isInFirstRow(String projectTaskName) {
        try {
            System.out.println("Looking for project task name: " + projectTaskName);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.list.view")));
            By firstRowNameLocator = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
            wait.until(ExpectedConditions.presenceOfElementLocated(firstRowNameLocator));
            String firstRowTaskName = driver.findElement(firstRowNameLocator).getText().trim();
            System.out.println(firstRowTaskName);
            return firstRowTaskName.contains(projectTaskName);
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getFirstRowNameLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowProjectTaskName));
        return driver.findElement(firstRowProjectTaskName);
    }

    public void clickFirstProjectTask() {
        getFirstRowNameLocator().click();
    }

    public WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowLocator));
        return driver.findElement(firstRowLocator);
    }

    public boolean isProjectTaskTitleCorrect(String projectTaskName) {
        try {
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println(title.toLowerCase());
            System.out.println(projectTaskName.toLowerCase());
            return title.toLowerCase().contains(projectTaskName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void editProjectTask() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonInDetail));
        driver.findElement(editButtonInDetail).click();
    }

    public void deleteProjectTask() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteButtonInDetail));
        driver.findElement(deleteButtonInDetail).click();
    }

    public void clickOkInDeleteDialog() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public void filterQuick(String name, boolean myItems, boolean favorites) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterButton));
        driver.findElement(filterButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(modalContent));
        wait.until(ExpectedConditions.visibilityOfElementLocated(quickFilterTab));
        driver.findElement(quickFilterTab).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(filterNameField));
        WebElement filterNameInput = driver.findElement(filterNameField);
        filterNameInput.clear();
        filterNameInput.sendKeys(name);

        WebElement myItemsCheckbox = driver.findElement(filterMyItemsCheckbox);
        if (myItems != myItemsCheckbox.isSelected()) {
            myItemsCheckbox.click();
        }
        WebElement favoritesCheckbox = driver.findElement(filterFavoritesCheckbox);
        if (favoritesCheckbox.isSelected() != favorites) {
            favoritesCheckbox.click();
        }

        driver.findElement(filterSubmitButton).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isFilterResultEmpty() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(filterResult));
            String resultText = driver.findElement(filterResult).getText();
            return resultText.contains("No results found") || resultText.contains("0 records");
        } catch (Exception e) {
            return false;
        }
    }

    public By getFilterResult() {
        return filterResult;
    }

    public void checkAndClearFilter() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterButton));
        driver.findElement(filterButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalContent));

        wait.until(ExpectedConditions.visibilityOfElementLocated(quickFilterTab));
        WebElement quickFilterElement = driver.findElement(quickFilterTab);
        if (!quickFilterElement.getAttribute("class").contains("active")) {
            quickFilterElement.click();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(filterNameField));
        wait.until(ExpectedConditions.presenceOfElementLocated(filterMyItemsCheckbox));

        // check if name field is filled or checkboxes are checked
        String nameFieldValue = driver.findElement(filterNameField).getAttribute("value").trim();
        boolean myItemsChecked = driver.findElement(filterMyItemsCheckbox).isSelected();
        boolean favoritesChecked = driver.findElement(filterFavoritesCheckbox).isSelected();

        if (nameFieldValue.isEmpty() && !myItemsChecked && !favoritesChecked) {
            // no filter applied
            driver.findElement(filterSubmitButton).click();
            return;
        }

        // clear all fields and then search, this will clear filter
        wait.until(ExpectedConditions.presenceOfElementLocated(filterClearButton));
        driver.findElement(filterClearButton).click();

        driver.findElement(filterSubmitButton).click();
    }

    public boolean isInCreateProjectPage() {
        try {
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println("Page title: " + title);
            return title.toUpperCase().contains("CREATE");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInProjectListPage() {
        try {
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println("Page title: " + title);
            return title.toUpperCase().contains("PROJECTS");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInProjectTasksPage() {
        try {
            return wait.until(ExpectedConditions.urlContains("module=ProjectTask&action=index"));
        } catch (Exception e) {
            return false;
        }
    }
}
