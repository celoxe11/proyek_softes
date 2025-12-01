package com.proyek_softes.demo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateAccountsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    // locators can be added here

    public CreateAccountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }

    public void cancel() {
        // Implementation for canceling account creation
    }

    public void save() {
        // Implementation for saving the new account
    }

    public void addInformation() {
        // Implementation for adding account information
    }
}
