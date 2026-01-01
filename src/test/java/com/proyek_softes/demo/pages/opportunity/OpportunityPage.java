package com.proyek_softes.demo.pages.opportunity;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OpportunityPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Navbar untuk navigasi ke opportunities
    private final By navTab = By.id("grouptab_0");
    private final By subTab = By.id("moduleTab_6_Opportunities");

    private final By createOpportunityLink = By.xpath("//a[@data-action-name='Create']");
    private final By importOpportunityLink = By.xpath("//a[@data-action-name='Import']");
    private final By viewOpportunityLink = By.xpath("//a[@data-action-name='List']");

    private final By firstRowOpportunityName = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
    private final By filterResult = By.className("msg");

    // filter locators
    private final By filterButton = By.xpath("//a[@title='Filter']");
    private final By modalContent = By.className("modal-content");
    private final By filterNameField = By.id("name_basic");
    private final By filterMyItemsCheckbox = By.id("current_user_only_basic");
    private final By filterFavoritesCheckbox = By.id("favorites_only_basic");
    private final By filterSubmitButton = By.id("search_form_submit");

    // detail page locators
    private final By tabActionsInDetail = By.id("tab-actions");
    private final By editButtonInDetail = By.id("edit_button");
    private final By deleteButtonInDetail = By.id("delete_button");

    // all page locator (semua page punya class module-title-text) untuk title mereka
    private final By pageTitle = By.className("module-title-text");

    public OpportunityPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    public boolean checkPageTitle(String expectedTitle) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
            String title = driver.findElement(pageTitle).getText();
            return title.toUpperCase().contains(expectedTitle.toUpperCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToOpportunitiesModule() {
        wait.until(ExpectedConditions.presenceOfElementLocated(navTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(navTab));
        actions.moveToElement(driver.findElement(navTab)).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subTab));
        driver.findElement(subTab).click();
    }

    public void navigateToCreateOpportunity() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createOpportunityLink));
        wait.until(ExpectedConditions.elementToBeClickable(createOpportunityLink));
        driver.findElement(createOpportunityLink).click();
    }

    public void navigateToImportOpportunities() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(importOpportunityLink));
        wait.until(ExpectedConditions.elementToBeClickable(importOpportunityLink));
        driver.findElement(importOpportunityLink).click();
    }

    public void navigateToViewOpportunities() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewOpportunityLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewOpportunityLink));
        driver.findElement(viewOpportunityLink).click();
    }

    public boolean isInFirstRow(String opportunityName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.list.view")));
            By firstRowNameLocator = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
            wait.until(ExpectedConditions.presenceOfElementLocated(firstRowNameLocator));
            String firstRowName = driver.findElement(firstRowNameLocator).getText().trim();
            return firstRowName.equals(opportunityName);
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowOpportunityName));
        return driver.findElement(firstRowOpportunityName);
    }

    public void clickFirstOpportunity() {
        getFirstRowLocator().click();
    }

    public boolean isOpportunityTitleCorrect(String opportunityName) {
        try {
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println(title.toLowerCase());
            System.out.println(opportunityName.toLowerCase());
            return title.toLowerCase().contains(opportunityName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void editOpportunity() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonInDetail));
        driver.findElement(editButtonInDetail).click();
    }

    public void deleteOpportunity() {
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
        driver.findElement(filterNameField).sendKeys(name);
        WebElement myItemsCheckbox = driver.findElement(filterMyItemsCheckbox);
        if (myItemsCheckbox.isSelected() != myItems) {
            myItemsCheckbox.click();
        }
        WebElement favoritesCheckbox = driver.findElement(filterFavoritesCheckbox);
        if (favoritesCheckbox.isSelected() != favorites) {
            favoritesCheckbox.click();
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
}
