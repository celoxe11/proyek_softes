package com.proyek_softes.demo.pages.products;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    /* =========================
       NAVIGATION
       ========================= */
    private final By navTab = By.id("grouptab_5");
    private final By subTab = By.xpath(
        "//a[@href='index.php?module=AOS_Products&action=index&parentTab=All']"
    );

    private final By createProductLink = By.xpath("//a[@data-action-name='Create']");
    private final By viewProductLink   = By.xpath("//a[@data-action-name='List']");
    private final By importProductLink = By.xpath("//a[@data-action-name='Import']");

    /* =========================
       LIST VIEW
       ========================= */
    private final By firstRowProductName =
        By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");

    private final By firstRowLocator =
        By.cssSelector("table.list.view tbody tr[height='20']:first-of-type");

    private final By filterResult = By.className("msg");

    /* =========================
       FILTER
       ========================= */
    private final By filterButton = By.xpath("//a[@title='Filter']");
    private final By quickFilterTab =
        By.xpath("//li[contains(@class,'searchTabHandler') and contains(@class,'basic')]/a");

    private final By modalContent = By.className("modal-content");

    private final By filterNameField = By.id("name_basic");
    private final By filterMyItemsCheckbox = By.id("current_user_only_basic");
    private final By filterFavoritesCheckbox = By.id("favorites_only_basic");

    private final By filterSubmitButton = By.id("search_form_submit");
    private final By filterClearButton  = By.id("search_form_clear");

    /* =========================
       DETAIL PAGE
       ========================= */
    private final By tabActionsInDetail = By.id("tab-actions");
    private final By editButtonInDetail = By.id("edit_button");
    private final By deleteButtonInDetail = By.id("delete_button");

    private final By pageTitle = By.className("module-title-text");

    /* =========================
       CONSTRUCTOR
       ========================= */
    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    /* =========================
       NAVIGATION METHODS
       ========================= */
    public void navigateToProductsModule() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(navTab));
        actions.moveToElement(driver.findElement(navTab)).perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(subTab));
        driver.findElement(subTab).click();
    }

    public void navigateToCreateProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(createProductLink));
        driver.findElement(createProductLink).click();
    }

    public void navigateToViewProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(viewProductLink));
        driver.findElement(viewProductLink).click();
    }

    public void navigateToImportProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(importProductLink));
        driver.findElement(importProductLink).click();
    }

    /* =========================
       LIST & VIEW
       ========================= */
    public boolean isInFirstRow(String productName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(firstRowProductName));
            String firstRowName =
                driver.findElement(firstRowProductName).getText().trim();
            return firstRowName.contains(productName);
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getFirstRowNameLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowProductName));
        return driver.findElement(firstRowProductName);
    }

    public WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowLocator));
        return driver.findElement(firstRowLocator);
    }

    public void clickFirstProduct() {
        getFirstRowNameLocator().click();
    }

    public boolean isProductTitleCorrect(String productName) {
        try {
            String title =
                wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle))
                    .getText();
            return title.toLowerCase().contains(productName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    /* =========================
       DETAIL ACTIONS
       ========================= */
    public void editProduct() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();

        wait.until(ExpectedConditions.elementToBeClickable(editButtonInDetail));
        driver.findElement(editButtonInDetail).click();
    }

    public void deleteProduct() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();

        wait.until(ExpectedConditions.elementToBeClickable(deleteButtonInDetail));
        driver.findElement(deleteButtonInDetail).click();
    }

    public void clickOkInDeleteDialog() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    /* =========================
       FILTER
       ========================= */
    public void filterQuick(
        String name,
        boolean myItems,
        boolean favorites
    ) {
        wait.until(ExpectedConditions.elementToBeClickable(filterButton));
        driver.findElement(filterButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(modalContent));

        WebElement quickTab = driver.findElement(quickFilterTab);
        if (!quickTab.getAttribute("class").contains("active")) {
            quickTab.click();
        }

        driver.findElement(filterNameField).clear();
        driver.findElement(filterNameField).sendKeys(name);

        toggleCheckbox(filterMyItemsCheckbox, myItems);
        toggleCheckbox(filterFavoritesCheckbox, favorites);

        driver.findElement(filterSubmitButton).click();
    }

    private void toggleCheckbox(By locator, boolean expected) {
        WebElement checkbox = driver.findElement(locator);
        if (checkbox.isSelected() != expected) {
            checkbox.click();
        }
    }

    public boolean isFilterResultEmpty() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(filterResult));
            return driver.findElement(filterResult)
                         .getText()
                         .toLowerCase()
                         .contains("no results found");
        } catch (Exception e) {
            return false;
        }
    }

    public By getFilterResult() {
        return filterResult;
    }

    public void checkAndClearFilter() {
        driver.findElement(filterButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalContent));

        WebElement quickTab = driver.findElement(quickFilterTab);
        if (!quickTab.getAttribute("class").contains("active")) {
            quickTab.click();
        }

        String nameValue = driver.findElement(filterNameField)
                                 .getAttribute("value")
                                 .trim();

        boolean hasFilter =
            !nameValue.isEmpty()
            || driver.findElement(filterMyItemsCheckbox).isSelected()
            || driver.findElement(filterFavoritesCheckbox).isSelected();

        if (hasFilter) {
            driver.findElement(filterClearButton).click();
        }

        driver.findElement(filterSubmitButton).click();
    }
}
