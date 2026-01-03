package com.proyek_softes.demo.pages.meetings;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MeetingsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Navbar untuk navigasi ke Meetings
    private final By navTab = By.id("grouptab_5");
    private final By subTab = By.xpath("//a[@href=\"index.php?module=Meetings&action=index&parentTab=All\"]");

    private final By scheduleMeetingLink = By.xpath("//a[@data-action-name='Schedule_Meeting']");
    private final By viewMeetingLink = By.xpath("//a[@data-action-name='List']");
    private final By importMeetingLink = By.xpath("//a[@data-action-name='Import']");

    private final By firstRowMeetingName = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
    private final By firstRowLocator = By.cssSelector("table.list.view tbody tr[height='20']:first-of-type");
    private final By filterResult = By.className("msg");

    // filter locators
    private final By filterButton = By.xpath("//a[@title='Filter']");
    private final By quickFilterTab = By.xpath("//li[contains(@class, 'searchTabHandler') and contains(@class, 'basic')]/a");
    private final By modalContent = By.className("modal-content");
    private final By filterNameField = By.id("name_basic");
    private final By filterMyItemsCheckbox = By.id("current_user_only_basic");
    private final By filterFavoritesCheckbox = By.id("favorites_only_basic");
    private final By filterOpenItemsCheckbox = By.id("open_only_basic");
    private final By filterSubmitButton = By.id("search_form_submit");
    private final By filterClearButton = By.id("search_form_clear");

    // detail page locators
    private final By tabActionsInDetail = By.id("tab-actions");
    private final By editButtonInDetail = By.id("edit_button");
    private final By deleteButtonInDetail = By.id("delete_button");

    private final By pageTitle = By.className("module-title-text");

    public MeetingsPage(WebDriver driver) {
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

    public void navigateToMeetingsModule() {
        wait.until(ExpectedConditions.presenceOfElementLocated(navTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(navTab));
        actions.moveToElement(driver.findElement(navTab)).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subTab));
        driver.findElement(subTab).click();
    }

    public void navigateToScheduleMeeting() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(scheduleMeetingLink));
        wait.until(ExpectedConditions.elementToBeClickable(scheduleMeetingLink));
        driver.findElement(scheduleMeetingLink).click();
    }

    public void navigateToViewMeeting() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewMeetingLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewMeetingLink));
        driver.findElement(viewMeetingLink).click();
    }

    public void navigateToImportMeeting() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(importMeetingLink));
        wait.until(ExpectedConditions.elementToBeClickable(importMeetingLink));
        driver.findElement(importMeetingLink).click();
    }

    public boolean isInFirstRow(String meetingName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.list.view")));
            By firstRowNameLocator = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
            wait.until(ExpectedConditions.presenceOfElementLocated(firstRowNameLocator));
            String firstRowMeetingName = driver.findElement(firstRowNameLocator).getText().trim();
            System.out.println(firstRowMeetingName);
            return firstRowMeetingName.contains(meetingName);
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getFirstRowNameLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowMeetingName));
        return driver.findElement(firstRowMeetingName);
    }

    public void clickFirstMeeting() {
        getFirstRowNameLocator().click();
    }

    public WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowLocator));
        return driver.findElement(firstRowLocator);
    }

    public boolean isMeetingTitleCorrect(String meetingName) {
        try {
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println(title.toLowerCase());
            System.out.println(meetingName.toLowerCase());
            return title.toLowerCase().contains(meetingName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void editMeeting() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonInDetail));
        driver.findElement(editButtonInDetail).click();
    }

    public void deleteMeeting() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteButtonInDetail));
        driver.findElement(deleteButtonInDetail).click();
    }

    public void clickOkInDeleteDialog() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public void filterQuick(String name, boolean myItems, boolean favorites, boolean openItems) {
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
        if (favorites != favoritesCheckbox.isSelected()) {
            favoritesCheckbox.click();
        }

        WebElement openItemsCheckbox = driver.findElement(filterOpenItemsCheckbox);
        if (openItems != openItemsCheckbox.isSelected()) {
            openItemsCheckbox.click();
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
        try {
            if (driver.findElements(filterButton).size() > 0) {
                driver.findElement(filterButton).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(filterClearButton));
                driver.findElement(filterClearButton).click();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("No filter to clear or error clearing filter: " + e.getMessage());
        }
    }
}
