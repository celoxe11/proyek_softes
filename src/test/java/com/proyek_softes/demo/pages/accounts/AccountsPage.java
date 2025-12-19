package com.proyek_softes.demo.pages.accounts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountsPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    // Navbar untuk navigasi ke accounts
    private By navTab = By.id("grouptab_0");
    private By subTab = By.id("moduleTab_6_Accounts");

    // Sidebar dan link di sidebar
    private By sidebarMenu = By.id("actionMenuSidebar");
    private By createAccountLink = By.xpath("//a[@data-action-name='Create']");
    private By importAccountLink = By.xpath("//a[@data-action-name='Import']");
    private By viewAccountLink = By.xpath("//a[@data-action-name='List']");
    private By recentlyViewedAccount1Link = By.xpath("//a[@class='recent-links-detail' and @accessKey='1']");
    private By recentlyViewedAccount1LinkEdit = By.xpath("//a[@class='recent-links-edit']");

    // all page locator (semua page punya class module-title-text) untuk title
    // mereka
    private By pageTitle = By.className("module-title-text");

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

    public void filterQuick() {
        // Implementation for quick filtering accounts
    }

    public void filterAdvanced() {
        // Implementation for advanced filtering accounts
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
}
