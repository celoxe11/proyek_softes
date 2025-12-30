package com.proyek_softes.demo.pages.contacts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContactsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    // Navbar untuk navigasi ke accounts
    private By navTab = By.id("grouptab_0");
    private By subTab = By.id("moduleTab_6_Contacts");

    private By sidebarMenu = By.id("actionMenuSidebar");
    private By createContactLink = By.xpath("//a[@data-action-name='Create']");
    private By createContactFromVcard = By.xpath("//a[@data-action-name='Create_Contact_Vcard']");
    private By importContactLink = By.xpath("//a[@data-action-name='Import']");
    private By viewContactLink = By.xpath("//a[@data-action-name='List']");

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

}
