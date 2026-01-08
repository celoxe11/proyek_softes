package com.proyek_softes.demo.pages.surveys;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SurveysPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Navbar untuk navigasi ke surveys
    private final By navTab = By.id("grouptab_5");
    private final By subTab = By.xpath("//a[@href=\"index.php?module=Surveys&action=index&parentTab=All\"]");

    private final By createSurveyLink = By.xpath("//div[@class='actionmenulink' and text()='Create Surveys']");
    private final By listSurveyLink = By.xpath("//a[@data-action-name='List']");
    private final By viewSurveyResponsesLink = By
            .xpath("//div[@class='actionmenulink' and text()='View Survey Responses']");

    private final By firstRowSurveyName = By.xpath(
            "//table[contains(@class,'list') and contains(@class,'view')]//tr[contains(@class,'ListRow')][1]//td[@type='name']//a");
    private final By firstRowLocator = By
            .xpath("//table[contains(@class,'list') and contains(@class,'view')]//tr[contains(@class,'ListRow')][1]");
    private final By filterResult = By.className("msg");

    // filter locators
    private final By filterButton = By.xpath("//a[@title='Filter']");
    private final By quickFilterTab = By
            .xpath("//li[contains(@class, 'searchTabHandler') and contains(@class, 'basic')]/a");
    private final By modalContent = By.className("modal-content");
    private final By filterNameField = By.id("name_basic");
    private final By filterMyItemsCheckbox = By.id("current_user_only_basic");
    private final By filterSubmitButton = By.id("search_form_submit");
    private final By filterClearButton = By.id("search_form_clear");

    // detail page locators
    private final By tabActionsInDetail = By.id("tab-actions");
    private final By editButtonInDetail = By.id("edit_button");
    private final By deleteButtonInDetail = By.id("delete_button");

    public SurveysPage(WebDriver driver) {
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

    public void navigateToSurveysModule() {
        wait.until(ExpectedConditions.presenceOfElementLocated(navTab));
        // Hover over the main tab
        actions.moveToElement(driver.findElement(navTab)).perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        WebElement subTabElement = wait.until(ExpectedConditions.visibilityOfElementLocated(subTab));

        // Use JavaScript click to avoid ElementClickInterceptedException
        try {
            subTabElement.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            System.out.println("  Note: Using JavaScript click for Surveys link due to interception");
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", subTabElement);
        }
    }

    public void navigateToCreateSurvey() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createSurveyLink));
        wait.until(ExpectedConditions.elementToBeClickable(createSurveyLink));
        driver.findElement(createSurveyLink).click();
    }

    public void navigateToViewSurvey() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(listSurveyLink));
        wait.until(ExpectedConditions.elementToBeClickable(listSurveyLink));
        driver.findElement(listSurveyLink).click();
    }

    public void navigateToViewSurveyResponses() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewSurveyResponsesLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewSurveyResponsesLink));
        driver.findElement(viewSurveyResponsesLink).click();
    }

    public boolean isCreateSurveyResponsesPageDisplayed() {
        try {
            String expectedUrl = "https://demo.suiteondemand.com/index.php?module=SurveyResponses&action=index&return_module=Surveys&return_action=index";
            wait.until(ExpectedConditions.urlToBe(expectedUrl));
            return true;
        } catch (Exception e) {
            System.out.println(
                    "Warning: URL verification failed. Expected: https://demo.suiteondemand.com/index.php?module=SurveyResponses&action=index&return_module=Surveys&return_action=index");
            System.out.println("Actual: " + driver.getCurrentUrl());
            return false;
        }
    }

    public boolean isInFirstRow(String surveyName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.list.view")));
            By firstRowNameLocator = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
            wait.until(ExpectedConditions.presenceOfElementLocated(firstRowNameLocator));
            String firstRowSurveyNameText = driver.findElement(firstRowNameLocator).getText().trim();
            System.out.println(firstRowSurveyNameText);
            return firstRowSurveyNameText.contains(surveyName);
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getFirstRowNameLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowSurveyName));
        return driver.findElement(firstRowSurveyName);
    }

    public void clickFirstSurvey() {
        getFirstRowNameLocator().click();
    }

    public WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowLocator));
        return driver.findElement(firstRowLocator);
    }

    public boolean isSurveyTitleCorrect(String surveyName) {
        try {
            By pageTitleLocator = By.className("module-title-text");
            // Check title first
            try {
                String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitleLocator)).getText();
                System.out.println("DEBUG: pageTitle = [" + title + "]");
                System.out.println("DEBUG: surveyName = [" + surveyName + "]");

                if (title.toLowerCase().contains(surveyName.toLowerCase())) {
                    return true;
                }
            } catch (Exception ignore) {
                // Ignore and proceed to fallback
            }

            // Fallback: Check body content case-insensitive
            try {
                // Wait for body to be visible
                WebElement body = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
                String bodyText = body.getText().toLowerCase();

                // Debug: Check length or snippet
                // System.out.println("DEBUG: Body text length: " + bodyText.length());

                if (bodyText.contains(surveyName.toLowerCase())) {
                    return true;
                } else {
                    System.out.println(
                            "DEBUG: Body text does not contain survey name (case-insensitive) '" + surveyName + "'");
                    return false;
                }
            } catch (Exception e) {
                System.out.println("  Warning: Exception checking body text: " + e.getMessage());
                return false;
            }

        } catch (Exception e) {
            System.out.println("DEBUG: Exception in isSurveyTitleCorrect: " + e.getMessage());
            return false;
        }
    }

    public void editSurvey() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonInDetail));
        driver.findElement(editButtonInDetail).click();
    }

    public void deleteSurvey() {
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
            String msg = driver.findElement(filterResult).getText().toLowerCase();
            System.out.println("Filter result message: " + msg);
            return msg.contains("no results found");
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

        wait.until(ExpectedConditions.elementToBeClickable(quickFilterTab));
        WebElement quickFilterElement = driver.findElement(quickFilterTab);
        if (!quickFilterElement.getAttribute("class").contains("active")) {
            quickFilterElement.click();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(filterNameField));
        wait.until(ExpectedConditions.presenceOfElementLocated(filterMyItemsCheckbox));

        String nameFieldValue = driver.findElement(filterNameField).getAttribute("value").trim();
        boolean myItemsChecked = driver.findElement(filterMyItemsCheckbox).isSelected();

        if (nameFieldValue.isEmpty() && !myItemsChecked) {
            driver.findElement(filterSubmitButton).click();
            return;
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(filterClearButton));
        driver.findElement(filterClearButton).click();
        driver.findElement(filterSubmitButton).click();
    }
}