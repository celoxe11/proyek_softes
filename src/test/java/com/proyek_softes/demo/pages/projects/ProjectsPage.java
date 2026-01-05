package com.proyek_softes.demo.pages.projects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProjectsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Navbar untuk navigasi ke Projects
    private final By navTab = By.id("grouptab_5");
    private final By subTab = By.xpath("//a[@href='index.php?module=Project&action=index&parentTab=All']");

    private final By createProjectLink = By.xpath("//a[@data-action-name='Create']");
    private final By viewProjectLink = By.xpath("//a[@data-action-name='List']");
    private final By importProjectLink = By.xpath("//a[@data-action-name='Import']");
    private final By resourceCalendarLink = By.xpath("//a[@data-action-name='Resource_Chart']");
    private final By viewProjectTaskLink = By.xpath("//a[@data-action-name='View_Project_Tasks']");

    private final By firstRowProjectName = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
    private final By firstRowLocator = By.cssSelector("table.list.view tbody tr[height='20']:first-of-type");
    private final By filterResult = By.className("msg");

    // filter locators
    private final By filterButton = By.xpath("//a[@title='Filter']");
    private final By quickFilterTab = By.xpath("//li[contains(@class, 'searchTabHandler') and contains(@class, 'basic')]/a");
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

    public ProjectsPage(WebDriver driver) {
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

    public void navigateToProjectsModule() {
        wait.until(ExpectedConditions.presenceOfElementLocated(navTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(navTab));
        actions.moveToElement(driver.findElement(navTab)).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subTab));
        driver.findElement(subTab).click();
    }

    public void navigateToCreateProject() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createProjectLink));
        wait.until(ExpectedConditions.elementToBeClickable(createProjectLink));
        driver.findElement(createProjectLink).click();
    }

    public void navigateToViewProject() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewProjectLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewProjectLink));
        driver.findElement(viewProjectLink).click();
    }

    public void navigateToImportProject() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(importProjectLink));
        wait.until(ExpectedConditions.elementToBeClickable(importProjectLink));
        driver.findElement(importProjectLink).click();
    }

    public void navigateToResourceCalendar() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(resourceCalendarLink));
        wait.until(ExpectedConditions.elementToBeClickable(resourceCalendarLink));
        driver.findElement(resourceCalendarLink).click();
    }

    public void navigateToViewProjectTasks() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewProjectTaskLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewProjectTaskLink));
        driver.findElement(viewProjectTaskLink).click();
    }

    public boolean isInFirstRow(String projectName) {
        try {
            System.out.println("Looking for project name: " + projectName);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.list.view")));
            By firstRowNameLocator = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
            wait.until(ExpectedConditions.presenceOfElementLocated(firstRowNameLocator));
            String firstRowProjectName = driver.findElement(firstRowNameLocator).getText().trim();
            System.out.println(firstRowProjectName);
            return firstRowProjectName.contains(projectName);
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getFirstRowNameLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowProjectName));
        return driver.findElement(firstRowProjectName);
    }

    public void clickFirstProject() {
        getFirstRowNameLocator().click();
    }

    public WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowLocator));
        return driver.findElement(firstRowLocator);
    }

    public boolean isProjectTitleCorrect(String projectName) {
        try {
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println(title.toLowerCase());
            System.out.println(projectName.toLowerCase());
            return title.toLowerCase().contains(projectName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void editProject() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonInDetail));
        driver.findElement(editButtonInDetail).click();
    }

    public void deleteProject() {
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

    public boolean isInProjectTasksPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("moduleTitle")));
        String title = driver.findElement(By.className("moduleTitle")).getText().trim();
        System.out.println("Page title: " + title);
        return title.toLowerCase().contains("project task");
    }
}
