package com.proyek_softes.demo.pages.survey_responses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SurveyResponsesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By createSurveyResponseLink = By
            .xpath("//div[@class='actionmenulink' and text()='Create Survey Responses']");
    private final By pageTitle = By.className("module-title-text");

    public SurveyResponsesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
    }

    public void navigateToCreateSurveyResponse() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createSurveyResponseLink));
        wait.until(ExpectedConditions.elementToBeClickable(createSurveyResponseLink));
        try {
            driver.findElement(createSurveyResponseLink).click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", driver.findElement(createSurveyResponseLink));
        }
    }

    public boolean isPageTitleCorrect(String expectedTitle) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
            String title = driver.findElement(pageTitle).getText();
            return title.toLowerCase().contains(expectedTitle.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    private final By firstRowName = By.xpath(
            "//table[contains(@class,'list') and contains(@class,'view')]//tr[contains(@class,'ListRow')][1]//td[@type='name']//a");
    private final By firstRowLocator = By
            .xpath("//table[contains(@class,'list') and contains(@class,'view')]//tr[contains(@class,'ListRow')][1]");

    // Detail page actions
    private final By tabActionsInDetail = By.id("tab-actions");
    private final By editButtonInDetail = By.id("edit_button");
    private final By deleteButtonInDetail = By.id("delete_button");

    // Filter locators
    private final By filterButton = By.xpath("//a[@title='Filter']");
    private final By quickFilterTab = By
            .xpath("//li[contains(@class, 'searchTabHandler') and contains(@class, 'basic')]/a");
    private final By modalContent = By.className("modal-content");
    private final By filterNameField = By.id("name_basic");
    private final By filterMyItemsCheckbox = By.id("current_user_only_basic");
    private final By filterSubmitButton = By.id("search_form_submit");
    private final By filterClearButton = By.id("search_form_clear");
    private final By filterResult = By.className("msg");

    public void clickFirstSurveyResponse() {
        wait.until(ExpectedConditions.elementToBeClickable(firstRowName)).click();
    }

    public boolean isInFirstRow(String name) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(firstRowName));
            String text = driver.findElement(firstRowName).getText();
            return text.contains(name);
        } catch (Exception e) {
            return false;
        }
    }

    public org.openqa.selenium.WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowLocator));
        return driver.findElement(firstRowLocator);
    }

    public org.openqa.selenium.WebElement getFirstRowNameLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowName));
        return driver.findElement(firstRowName);
    }

    public void editSurveyResponse() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonInDetail));
        driver.findElement(editButtonInDetail).click();
    }

    public void deleteSurveyResponse() {
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

        wait.until(ExpectedConditions.elementToBeClickable(quickFilterTab));
        WebElement quickFilterElement = driver.findElement(quickFilterTab);
        if (!quickFilterElement.getAttribute("class").contains("active")) {
            quickFilterElement.click();
        }

        driver.findElement(filterNameField).sendKeys(name);
        WebElement myItemsCheckbox = driver.findElement(filterMyItemsCheckbox);
        if (myItemsCheckbox.isSelected() != myItems) {
            myItemsCheckbox.click();
        }

        driver.findElement(filterSubmitButton).click();
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
        org.openqa.selenium.WebElement quickFilterElement = driver.findElement(quickFilterTab);
        if (!quickFilterElement.getAttribute("class").contains("active")) {
            quickFilterElement.click();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(filterNameField));

        String nameFieldValue = driver.findElement(filterNameField).getAttribute("value").trim();

        if (nameFieldValue.isEmpty()) {
            driver.findElement(filterSubmitButton).click();
            return;
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(filterClearButton));
        driver.findElement(filterClearButton).click();
        driver.findElement(filterSubmitButton).click();
    }
}
