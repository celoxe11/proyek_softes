package com.proyek_softes.landing.main.pages.services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk halaman Consultancy and Implementation
 * Extends ServicesPage untuk menggunakan method umum
 */
public class ConsultancyPage extends ServicesPage {

    // Get Started button
    private By getStartedButtonLocator = By.cssSelector("a.fusion-button[href*='about-us/contact']");

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ConsultancyPage(WebDriver driver) {
        super(driver);
    }

    // ========================================
    // PAGE ACTIONS - Specific to Consultancy and Implementation
    // ========================================

    /**
     * Click Get Started button
     * 
     * @return true jika berhasil
     */
    public boolean clickGetStartedButton() {
        scrollToPercentage(0.5);
        return scrollAndClick(getStartedButtonLocator, "Get Started button");
    }
}
