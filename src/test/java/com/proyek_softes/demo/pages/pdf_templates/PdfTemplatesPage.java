package com.proyek_softes.demo.pages.pdf_templates;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PdfTemplatesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Navbar untuk navigasi ke opportunities
    private final By navTab = By.id("grouptab_5");
    private final By subTab = By.xpath("//a[@href=\"index.php?module=AOS_PDF_Templates&action=index&parentTab=All\"]");

    private final By createPdfTemplateLink = By.xpath("//a[@data-action-name='Create']");
    private final By importPdfTemplateLink = By.xpath("//a[@data-action-name='Import']");
    private final By viewPdfTemplateLink = By.xpath("//a[@data-action-name='List']");

    private final By firstRowPdfTemplateName = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
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

    // all page locator (semua page punya class module-title-text) untuk title mereka
    private final By pageTitle = By.className("module-title-text");

    public PdfTemplatesPage(WebDriver driver) {
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

    public void navigateToPdfTemplateModule() {
        wait.until(ExpectedConditions.presenceOfElementLocated(navTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(navTab));
        actions.moveToElement(driver.findElement(navTab)).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subTab));
        driver.findElement(subTab).click();
    }

    public void navigateToCreatePdfTemplate() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createPdfTemplateLink));
        wait.until(ExpectedConditions.visibilityOfElementLocated(createPdfTemplateLink));
        driver.findElement(createPdfTemplateLink).click();
    }

    public void navigateToImportPdfTemplates() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(importPdfTemplateLink));
        wait.until(ExpectedConditions.elementToBeClickable(importPdfTemplateLink));
        driver.findElement(importPdfTemplateLink).click();
    }

    public void navigateToViewPdfTemplates() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewPdfTemplateLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewPdfTemplateLink));
        driver.findElement(viewPdfTemplateLink).click();
    }

    public boolean isInFirstRow(String pdfTemplateName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.list.view")));
            By firstRowNameLocator = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
            wait.until(ExpectedConditions.presenceOfElementLocated(firstRowNameLocator));
            String firstRowName = driver.findElement(firstRowNameLocator).getText().trim();
            return firstRowName.equals(pdfTemplateName);
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowLocator));
        return driver.findElement(firstRowLocator);
    }

    public WebElement getFirstRowNameLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowPdfTemplateName));
        return driver.findElement(firstRowPdfTemplateName);
    }

    public void clickFirstPdfTemplate() {
        System.out.println("Clicking first pdf template: " + getFirstRowNameLocator().getText().trim());
        getFirstRowNameLocator().click();
    }

    public boolean isPdfTemplateTitleCorrect(String pdfTemplateName) {
        try {
            // make sure to wait for tab actions to ensure page is loaded, karena ada di detail 
            wait.until(ExpectedConditions.presenceOfElementLocated(tabActionsInDetail));
            
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println("Title Found: " + title.toLowerCase());
            System.out.println("Expected: " + pdfTemplateName.toLowerCase());
            return title.toLowerCase().contains(pdfTemplateName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void editPdfTemplate() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonInDetail));
        driver.findElement(editButtonInDetail).click();
    }

    public void deletePdfTemplate() {
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
        WebElement quickFilterElement = driver.findElement(quickFilterTab);
        if (!quickFilterElement.getAttribute("class").contains("active")) {
            quickFilterElement.click();
        }
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

        wait.until(ExpectedConditions.visibilityOfElementLocated(quickFilterTab));
        WebElement quickFilterElement = driver.findElement(quickFilterTab);
        if (!quickFilterElement.getAttribute("class").contains("active")) {
            quickFilterElement.click();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(filterNameField));
        wait.until(ExpectedConditions.presenceOfElementLocated(filterMyItemsCheckbox));
        wait.until(ExpectedConditions.presenceOfElementLocated(filterFavoritesCheckbox));

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
}
