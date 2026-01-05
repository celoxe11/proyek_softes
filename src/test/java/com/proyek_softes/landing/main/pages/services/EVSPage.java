package com.proyek_softes.landing.main.pages.services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk halaman Enterprise Verification Service (EVS)
 * Extends ServicesPage untuk menggunakan method umum
 */
public class EVSPage extends ServicesPage {

    // Buttons on EVS page
    private By contactUsButtonLocator = By.cssSelector("a.fusion-button[href*='about-us/contact']");

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public EVSPage(WebDriver driver) {
        super(driver);
    }

    // ========================================
    // PAGE ACTIONS - Specific to Enterprise Verification Service
    // ========================================

    /**
     * Click "Contact Us" button
     * 
     * @return true jika berhasil
     */
    public boolean clickContactUsButton() {
        scrollToPercentage(0.7);
        return scrollAndClick(contactUsButtonLocator, "Contact Us button");
    }
}
