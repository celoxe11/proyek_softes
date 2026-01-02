package com.proyek_softes.demo.pages.contacts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ImportContactPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    
    // Locators

    public ImportContactPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
    }
}
