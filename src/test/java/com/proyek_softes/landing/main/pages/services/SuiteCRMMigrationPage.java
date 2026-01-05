package com.proyek_softes.landing.main.pages.services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk halaman SuiteCRM Migration
 * Extends ServicesPage untuk menggunakan method umum
 */
public class SuiteCRMMigrationPage extends ServicesPage {

    // Buttons on SuiteCRM Migration page
    private By contactUsForMigrationsButtonLocator = By.cssSelector("a.fusion-button[href*='about-us/contact']");

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public SuiteCRMMigrationPage(WebDriver driver) {
        super(driver);
    }

    // ========================================
    // PAGE ACTIONS - Specific to SuiteCRM Migration
    // ========================================

    /**
     * Click "Contact Us for Migrations" button
     * 
     * @return true jika berhasil
     */
    public boolean clickContactUsForMigrationsButton() {
        scrollToPercentage(0.6);
        return scrollAndClick(contactUsForMigrationsButtonLocator, "Contact Us for Migrations button");
    }
}
