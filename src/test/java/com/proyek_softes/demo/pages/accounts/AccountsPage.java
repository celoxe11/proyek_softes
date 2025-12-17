package com.proyek_softes.demo.pages.accounts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
        By navTab = By.id("grouptab_0");
        By subTab = By.id("moduleTab_6_Accounts");
        By createAccountLink = By.xpath("//a[@data-action-name='Create']");

        try {
            // Scroll to top to ensure navigation is visible
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
            Thread.sleep(500);
            
            WebElement navTabElement = wait.until(ExpectedConditions.presenceOfElementLocated(navTab));
            
            // Use JavaScript to trigger hover
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].dispatchEvent(new MouseEvent('mouseover', {bubbles: true}));", navTabElement);
            
            Thread.sleep(1000);

            // Wait for submenu element to be present
            WebElement subTabElement = wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
            
            // Ensure it's visible before clicking
            wait.until(ExpectedConditions.visibilityOf(subTabElement));

            // Use JavaScript click for more reliability
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", subTabElement);

            // Wait for page to load after navigation
            Thread.sleep(2000);

            // Wait for the action sidebar to be present
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("actionMenuSidebar")));

            // Wait for the create account link to be clickable with increased timeout
            WebDriverWait extendedWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(15));
            extendedWait.until(ExpectedConditions.elementToBeClickable(createAccountLink));

            // Click the create account link using JavaScript
            WebElement createAccountElement = driver.findElement(createAccountLink);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", createAccountElement);
            
            // Wait for create page to load
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to Create Account page. Error: " + e.getMessage(), e);
        }
    }

    public void navigateToViewAccounts() {
        // Implementation for navigating to view accounts page
        By viewAccountLink = By.xpath("//a[@data-action-name='List']");
        try {
            Thread.sleep(600);

            // Wait for the action sidebar to be present
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("actionMenuSidebar")));

            // Wait for the view accounts link to be clickable with increased timeout
            WebDriverWait extendedWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(15));
            extendedWait.until(ExpectedConditions.elementToBeClickable(viewAccountLink));

            // Click the view accounts link using JavaScript
            WebElement viewAccountElement = driver.findElement(viewAccountLink);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", viewAccountElement);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click view accounts link. Error: " + e.getMessage(), e);
        }
    }

    public void navigateToImportAccounts() {
        // Implementation for navigating to import accounts page
        By importAccountLink = By.xpath("//a[@data-action-name='Import']");
        try {
            Thread.sleep(600);

            // Wait for the action sidebar to be present
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("actionMenuSidebar")));

            // Wait for the import accounts link to be clickable with increased timeout
            WebDriverWait extendedWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(15));
            extendedWait.until(ExpectedConditions.elementToBeClickable(importAccountLink));

            // Click the import accounts link using JavaScript
            WebElement importAccountElement = driver.findElement(importAccountLink);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", importAccountElement);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click import accounts link. Error: " + e.getMessage(), e);
        }
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
