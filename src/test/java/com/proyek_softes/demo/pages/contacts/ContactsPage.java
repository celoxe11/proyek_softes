package com.proyek_softes.demo.pages.contacts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContactsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Navbar untuk navigasi ke accounts
    private final By navTab = By.id("grouptab_0");
    private final By subTab = By.id("moduleTab_6_Contacts");

    // private final By sidebarMenu = By.id("actionMenuSidebar");
    private final By createContactLink = By.xpath("//a[@data-action-name='Create']");
    private final By createContactFromVcard = By.xpath("//a[@data-action-name='Create_Contact_Vcard']");
    private final By importContactLink = By.xpath("//a[@data-action-name='Import']");
    private final By viewContactLink = By.xpath("//a[@data-action-name='List']");
    private final By importVCardLink = By.xpath("//a[@data-action-name='Create_Contact_Vcard']");

    private final By firstRowContactName = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
    private final By firstRowLocator = By.cssSelector("table.list.view tbody tr[height='20']:first-of-type");
    private final By filterResult = By.className("msg");

    // filter locators
    private final By filterButton = By.xpath("//a[@title='Filter']");
    private final By quickFilterTab = By.xpath("//li[contains(@class, 'searchTabHandler') and contains(@class, 'basic')]/a");
    private final By modalContent = By.className("modal-content");
    private final By filterNameField = By.id("search_name_basic");
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

    public ContactsPage(WebDriver driver) {
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

    public boolean checkImportVcardPageTitle(String expectedTitle) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("moduleTitle")));

            // Method 1: Chain findElement calls to get child element
            String title = driver.findElement(By.className("moduleTitle"))
                    .findElement(By.tagName("h2"))
                    .getText();

            return title.toUpperCase().contains(expectedTitle.toUpperCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToContactsModule() {
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(navTab));
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(navTab));
        actions.moveToElement(driver.findElement(navTab)).perform();
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(subTab));
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(subTab));
        driver.findElement(subTab).click();
    }

    public void navigateToCreateContact() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createContactLink));
        wait.until(ExpectedConditions.elementToBeClickable(createContactLink));
        driver.findElement(createContactLink).click();
    }

    public void navigateToCreateContactFromVcard() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createContactFromVcard));
        wait.until(ExpectedConditions.elementToBeClickable(createContactFromVcard));
        driver.findElement(createContactFromVcard).click();
    }

    public void navigateToImportContact() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(importContactLink));
        wait.until(ExpectedConditions.elementToBeClickable(importContactLink));
        driver.findElement(importContactLink).click();
    }

    public void navigateToViewContact() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewContactLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewContactLink));
        driver.findElement(viewContactLink).click();
    }

    public void navigateToImportVCard() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(importVCardLink));
        wait.until(ExpectedConditions.elementToBeClickable(importVCardLink));
        driver.findElement(importVCardLink).click();
    }

    public boolean isInFirstRow(String contactName) {
        try {
            // Wait for the table to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.list.view")));

            // Find the first data row (skip header row in tbody) and get the account name
            By firstRowNameLocator = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
            wait.until(ExpectedConditions.presenceOfElementLocated(firstRowNameLocator));

            String firstRowContactName = driver.findElement(firstRowNameLocator).getText().trim();

            System.out.println(firstRowContactName);

            return firstRowContactName.contains(contactName);
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getFirstRowNameLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowContactName));
        return driver.findElement(firstRowContactName);
    }

    public void clickFirstContact() {
        getFirstRowNameLocator().click();
    }

    public WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowLocator));
        return driver.findElement(firstRowLocator);
    }

    public boolean isContactTitleCorrect(String contactName) {
        try {
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println(title.toLowerCase());
            System.out.println(contactName.toLowerCase());
            return title.toLowerCase().contains(contactName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void editContact() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonInDetail));
        driver.findElement(editButtonInDetail).click();
    }

    public void deleteContact() {
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

        // submit filter
        driver.findElement(filterSubmitButton).click();
    }

    public void filterAdvanced() {
        // Implementation for advanced filtering accounts
    }

    public boolean isFilterResultEmpty() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(filterResult));
            String msg = driver.findElement(filterResult).getText().toLowerCase();
            System.out.println("Filter result message: " + msg);
            System.out.println("Filter result message: " + msg.contains("no results found"));
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
