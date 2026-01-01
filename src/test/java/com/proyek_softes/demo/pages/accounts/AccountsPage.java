package com.proyek_softes.demo.pages.accounts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Navbar untuk navigasi ke accounts
    private final By navTab = By.id("grouptab_0");
    private final By subTab = By.id("moduleTab_6_Accounts");

    // Sidebar dan link di sidebar
    private final By sidebarMenu = By.id("actionMenuSidebar");
    private final By createAccountLink = By.xpath("//a[@data-action-name='Create']");
    private final By importAccountLink = By.xpath("//a[@data-action-name='Import']");
    private final By viewAccountLink = By.xpath("//a[@data-action-name='List']");
    private final By recentlyViewedAccount1Link = By.xpath("//a[@class='recent-links-detail' and @accessKey='1']");
    private final By recentlyViewedAccount1LinkEdit = By.xpath("//a[@class='recent-links-edit']");

    private final By firstRowAccountName = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
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

    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    // cek berdasarkan title halaman
    public boolean checkPageTitle(String expectedTitle) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
            String title = driver.findElement(pageTitle).getText();
            return title.toUpperCase().contains(expectedTitle.toUpperCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToAccountsModule() {
        wait.until(ExpectedConditions.presenceOfElementLocated(navTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(navTab));
        actions.moveToElement(driver.findElement(navTab)).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subTab));
        driver.findElement(subTab).click();

        // Wait for sidebar to be visible after clicking the module
        wait.until(ExpectedConditions.visibilityOfElementLocated(sidebarMenu));
    }

    public void navigateToCreateAccount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createAccountLink));
        wait.until(ExpectedConditions.elementToBeClickable(createAccountLink));
        driver.findElement(createAccountLink).click();
    }

    public void navigateToViewAccounts() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewAccountLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewAccountLink));
        driver.findElement(viewAccountLink).click();
    }

    public void navigateToImportAccounts() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(importAccountLink));
        wait.until(ExpectedConditions.elementToBeClickable(importAccountLink));
        driver.findElement(importAccountLink).click();
    }

    public void navigateToRecentlyViewedAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(recentlyViewedAccount1Link));
        driver.findElement(recentlyViewedAccount1Link).click();
    }

    public void navigateToRecentlyViewedAccountEdit() {
        wait.until(ExpectedConditions.elementToBeClickable(recentlyViewedAccount1LinkEdit));
        driver.findElement(recentlyViewedAccount1LinkEdit).click();
    }

    public boolean isInFirstRow(String accountName) {
        try {
            // Wait for the table to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.list.view")));

            // Find the first data row (skip header row in tbody) and get the account name
            By firstRowNameLocator = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
            wait.until(ExpectedConditions.presenceOfElementLocated(firstRowNameLocator));

            String firstRowAccountName = driver.findElement(firstRowNameLocator).getText().trim();

            return firstRowAccountName.equals(accountName);
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowAccountName));
        return driver.findElement(firstRowAccountName);
    }

    public void clickFirstAccount() {
        getFirstRowLocator().click();
    }

    public boolean isAccountTitleCorrect(String accountName) {
        try {
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println(title.toLowerCase());
            System.out.println(accountName.toLowerCase());
            return title.toLowerCase().contains(accountName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void editAccount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonInDetail));
        driver.findElement(editButtonInDetail).click();
    }

    public void deleteAccount() {
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

    public void chooseColumns() {
        // Implementation for choosing columns to display
    }

    public void paginationNext() {
        // Implementation for pagination next
    }

    public void paginationEnd() {
        // Implementation for pagination end
    }

    public void paginationPrevious() {
        // Implementation for pagination previous
    }

    public void paginationStart() {
        // Implementation for pagination start
    }

    public void sortByColumn(String columnName) {
        // Implementation for sorting by column
    }

    public void selectAllAccounts() {
        // Implementation for selecting all accounts
    }

    public void deselectAllAccounts() {
        // Implementation for deselecting all accounts
    }

    public void selectThisPage() {

    }

    public void deselectAll() {

    }

    public void selectAll() {

    }

    public void navigateToAccountDetails(String accountName) {
        // Implementation for navigating to specific account details page
    }

    public void composeEmailToAccount(String accountName) {
        // Implementation for composing email to specific account
    }

    public void clickPhoneNumber(String accountName) {
        // Implementation for clicking phone number of specific account
    }

    public void clickUser() {
        // Implementation for clicking user
    }

    public void selectAccountCheckbox(String accountName) {
        // Implementation for selecting account checkbox
    }

    public void exitComposeDialog() {
        // Implementation for exiting compose dialog

        // click anywhere outside the dialog to close it
        // click ok
    }

    public void cancelComposeDialog() {
        // Implementation for canceling compose dialog

        // click anywhere outside the dialog to close it
        // click cancel
    }

    public void performBulkEmail() {
        // Implementation for performing bulk email
    }

    public void performBulkMerge() {
        // Implementation for performing bulk merge
    }

    public void performBulkDelete() {
        // Implementation for performing bulk delete
    }

    public void performBulkAddToTargetList() {
        // Implementation for performing bulk add to target list
    }

    public void performBulkAddContactsToTargetList() {
        // Implementation for performing bulk add contacts to campaign
    }

    public void performBulkPrintAsPDF() {
        // Implementation for performing bulk print as PDF
    }

    public void performBulkExport() {
        // Implementation for performing bulk export
    }

    public void performBulkMap() {
        // Implementation for performing bulk map
    }

    public By getPageTitle() {
        return pageTitle;
    }

    public By getFilterResult() {
        return filterResult;
    }
}
