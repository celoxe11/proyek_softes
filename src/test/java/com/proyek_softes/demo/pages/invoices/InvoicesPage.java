package com.proyek_softes.demo.pages.invoices;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InvoicesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Navbar for navigation to Invoices
    private final By navTab = By.id("grouptab_5");
    private final By subTab = By.xpath("//a[@href=\"index.php?module=AOS_Invoices&action=index&parentTab=All\"]");

    private final By createInvoiceLink = By.xpath("//a[@data-action-name='Create']");
    private final By viewInvoiceLink = By.xpath("//a[@data-action-name='List']");
    private final By importInvoiceLink = By.xpath("//a[@data-action-name='Import' and contains(@href, 'import_module=AOS_Invoices')]");
    private final By importLineItemsLink = By.xpath("//a[@data-action-name='Import' and contains(@href, 'import_module=AOS_Products_Quotes')]");
    
    private final By firstRowInvoiceName = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
    private final By firstRowLocator = By.cssSelector("table.list.view tbody tr[height='20']:first-of-type");

    private final By filterResult = By.className("msg");

    // Filter locators
    private final By filterButton = By.xpath("//a[@title='Filter']");
    private final By quickFilterTab = By.xpath("//li[contains(@class, 'searchTabHandler') and contains(@class, 'basic')]/a");
    private final By modalContent = By.className("modal-content");
    private final By filterNameField = By.id("name_basic");
    private final By filterMyItemsCheckbox = By.id("current_user_only_basic");
    private final By filterFavoritesCheckbox = By.id("favorites_only_basic");
    private final By filterSubmitButton = By.id("search_form_submit");
    private final By filterClearButton = By.id("search_form_clear");

    // Detail page locators
    private final By tabActionsInDetail = By.id("tab-actions");
    private final By editButtonInDetail = By.id("edit_button");
    private final By deleteButtonInDetail = By.id("delete_button");

    private final By pageTitle = By.className("module-title-text");

    public InvoicesPage(WebDriver driver) {
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

    public void navigateToInvoicesModule() {
        wait.until(ExpectedConditions.presenceOfElementLocated(navTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(navTab));
        actions.moveToElement(driver.findElement(navTab)).perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subTab));
        driver.findElement(subTab).click();
    }

    public void navigateToCreateInvoice() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createInvoiceLink));
        wait.until(ExpectedConditions.elementToBeClickable(createInvoiceLink));
        driver.findElement(createInvoiceLink).click();
    }

    public void navigateToViewInvoice() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewInvoiceLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewInvoiceLink));
        driver.findElement(viewInvoiceLink).click();
    }

    public void navigateToImportInvoice() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(importInvoiceLink));
        wait.until(ExpectedConditions.elementToBeClickable(importInvoiceLink));
        driver.findElement(importInvoiceLink).click();
    }

    public void navigateToImportLineItems() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(importLineItemsLink));
        wait.until(ExpectedConditions.elementToBeClickable(importLineItemsLink));
        driver.findElement(importLineItemsLink).click();
    }

    public boolean isInFirstRow(String invoiceName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.list.view")));
            wait.until(ExpectedConditions.presenceOfElementLocated(firstRowInvoiceName));
            String firstRowText = driver.findElement(firstRowInvoiceName).getText().trim();
            System.out.println(firstRowText);
            return firstRowText.contains(invoiceName);
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getFirstRowNameLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowInvoiceName));
        return driver.findElement(firstRowInvoiceName);
    }

    public void clickFirstInvoice() {
        getFirstRowNameLocator().click();
    }

    public WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowLocator));
        return driver.findElement(firstRowLocator);
    }

    public boolean isInvoiceTitleCorrect(String invoiceName) {
        try {
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println(title.toLowerCase());
            System.out.println(invoiceName.toLowerCase());
            return title.toLowerCase().contains(invoiceName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void editInvoice() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonInDetail));
        driver.findElement(editButtonInDetail).click();
    }

    public void deleteInvoice() {
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

        driver.findElement(filterNameField).clear();
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
        wait.until(ExpectedConditions.presenceOfElementLocated(filterFavoritesCheckbox));

        String nameFieldValue = driver.findElement(filterNameField).getAttribute("value").trim();
        boolean myItemsChecked = driver.findElement(filterMyItemsCheckbox).isSelected();
        boolean favoritesChecked = driver.findElement(filterFavoritesCheckbox).isSelected();

        if (nameFieldValue.isEmpty() && !myItemsChecked && !favoritesChecked) {
            driver.findElement(filterSubmitButton).click();
            return;
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(filterClearButton));
        driver.findElement(filterClearButton).click();
        driver.findElement(filterSubmitButton).click();
    }
}
