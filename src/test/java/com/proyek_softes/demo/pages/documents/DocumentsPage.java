package com.proyek_softes.demo.pages.documents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DocumentsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Navbar untuk navigasi ke Documents
    private final By navTab = By.id("grouptab_5");
    private final By subTab = By.xpath("//a[@href=\"index.php?module=Documents&action=index&parentTab=All\"]");

    private final By createDocumentLink = By.xpath("//a[@data-action-name='Create']");
    private final By viewDocumentLink = By.xpath("//a[@data-action-name='List']");

    private final By firstRowDocumentName
            = By.cssSelector("table.list.view tbody tr:first-child td[type='varchar'] a");
    private final By firstRowLocator
            = By.cssSelector("table.list.view tbody tr[height='20']:first-of-type");

    private final By filterResult = By.className("msg");

    // filter locators
    private final By filterButton = By.xpath("//a[@title='Filter']");
    private final By quickFilterTab
            = By.xpath("//li[contains(@class, 'searchTabHandler') and contains(@class, 'basic')]/a");
    private final By modalContent = By.className("modal-content");
    private final By filterNameField = By.id("document_name_basic");
    private final By filterFavoritesCheckbox = By.id("favorites_only_basic");
    private final By filterSubmitButton = By.id("search_form_submit");
    private final By filterClearButton = By.id("search_form_clear");

    // detail page locators
    private final By tabActionsInDetail = By.id("tab-actions");
    private final By editButtonInDetail = By.id("edit_button");
    private final By deleteButtonInDetail = By.id("delete_button");

    private final By pageTitle = By.className("module-title-text");

    public DocumentsPage(WebDriver driver) {
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

    public void navigateToDocumentsModule() {
        wait.until(ExpectedConditions.presenceOfElementLocated(navTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(navTab));
        actions.moveToElement(driver.findElement(navTab)).perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subTab));
        driver.findElement(subTab).click();
    }

    public void navigateToCreateDocument() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createDocumentLink));
        wait.until(ExpectedConditions.elementToBeClickable(createDocumentLink));
        driver.findElement(createDocumentLink).click();
    }

    public void navigateToViewDocument() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewDocumentLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewDocumentLink));
        driver.findElement(viewDocumentLink).click();
    }

    public boolean isInFirstRow(String documentName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("table.list.view")
            ));
            wait.until(ExpectedConditions.presenceOfElementLocated(firstRowDocumentName));
            String firstRowText = driver.findElement(firstRowDocumentName).getText().trim();
            System.out.println(firstRowText);
            return firstRowText.contains(documentName);
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getFirstRowNameLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowDocumentName));
        return driver.findElement(firstRowDocumentName);
    }

    public void clickFirstDocument() {
        getFirstRowNameLocator().click();
    }

    public WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowLocator));
        return driver.findElement(firstRowLocator);
    }

    public boolean isDocumentTitleCorrect(String documentName) {
        try {
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println(title.toLowerCase());
            System.out.println(documentName.toLowerCase());
            return title.toLowerCase().contains(documentName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void editDocument() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonInDetail));
        driver.findElement(editButtonInDetail).click();
    }

    public void deleteDocument() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteButtonInDetail));
        driver.findElement(deleteButtonInDetail).click();
    }

    public void clickOkInDeleteDialog() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public void filterQuick(String name, boolean favorites) {
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
        wait.until(ExpectedConditions.presenceOfElementLocated(filterFavoritesCheckbox));

        String nameFieldValue = driver.findElement(filterNameField).getAttribute("value").trim();
        boolean favoritesChecked = driver.findElement(filterFavoritesCheckbox).isSelected();

        if (nameFieldValue.isEmpty() && !favoritesChecked) {
            driver.findElement(filterSubmitButton).click();
            return;
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(filterClearButton));
        driver.findElement(filterClearButton).click();
        driver.findElement(filterSubmitButton).click();
    }
}
