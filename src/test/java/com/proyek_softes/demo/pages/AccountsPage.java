package com.proyek_softes.demo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }

    public void navigateToCreateAccount() {
        // Implementation for navigating to create account page
    }

    public void navigateToViewAccounts() {
        // Implementation for navigating to view accounts page
    }

    public void navigateToImportAccounts() {
        // Implementation for navigating to import accounts page
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
}
