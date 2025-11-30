package com.proyek_softes.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ContactsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By createContactBtn = By.name("Create");
    private By firstNameField = By.id("first_name");
    private By lastNameField = By.id("last_name");
    private By emailField = By.id("Contacts0emailAddress0");
    private By phoneField = By.id("phone_work");
    private By saveBtn = By.id("SAVE");
    private By firstContactLink = By.xpath("(//table[@class='list view table-responsive']//tr[@class='oddListRowS1']//a)[1]");
    private By editBtn = By.id("edit_button");
    private By deleteBtn = By.id("delete_button");

    public ContactsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public ContactsPage createContact(String firstName, String lastName, String email, String phone) {
        wait.until(ExpectedConditions.elementToBeClickable(createContactBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(saveBtn).click();
        return this;
    }

    public ContactsPage openFirstContact() {
        wait.until(ExpectedConditions.elementToBeClickable(firstContactLink)).click();
        return this;
    }

    public ContactsPage updateContact(String newLastName) {
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        WebElement lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField));
        lastNameInput.clear();
        lastNameInput.sendKeys(newLastName);
        driver.findElement(saveBtn).click();
        return this;
    }

    public ContactsPage deleteContact() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        return this;
    }

    public boolean isContactCreated() {
        try {
            return driver.getCurrentUrl().contains("Contacts");
        } catch (Exception e) {
            return false;
        }
    }
}
