package com.proyek_softes.landing.main.pages.services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk halaman SuiteCRM Hosted
 * Extends ServicesPage untuk menggunakan method umum
 */
public class SuiteCRMHostedPage extends ServicesPage {

    // Start Free Trial buttons for each package
    private By starterStartFreeTrialButtonLocator = By
            .xpath("(//input[@type='submit' and @value='Start free trial'])[1]");
    private By businessStartFreeTrialButtonLocator = By
            .xpath("(//input[@type='submit' and @value='Start free trial'])[2]");
    private By premiumStartFreeTrialButtonLocator = By
            .xpath("(//input[@type='submit' and @value='Start free trial'])[3]");

    // Contact Us button for Dedicated package
    private By dedicatedContactUsButtonLocator = By.cssSelector("div.dedicated a.fusion-button[href*='contact']");

    // Links on SuiteCRM Hosted page
    private By migrationServicesLinkLocator = By.cssSelector("a[href*='enterprise/migrations']");
    private By getStartedWithSuiteCRMHostedButtonLocator = By.cssSelector("a.fusion-button[href*='suitecrmhosted']");

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public SuiteCRMHostedPage(WebDriver driver) {
        super(driver);
    }

    // ========================================
    // PAGE ACTIONS - Specific to SuiteCRM Hosted
    // ========================================

    /**
     * Click Start Free Trial button for Starter package
     * 
     * @return true jika berhasil
     */
    public boolean clickStarterStartFreeTrialButton() {
        scrollToPercentage(0.3);
        return scrollAndClick(starterStartFreeTrialButtonLocator, "Start Free Trial button for Starter package");
    }

    /**
     * Click Start Free Trial button for Business package
     * 
     * @return true jika berhasil
     */
    public boolean clickBusinessStartFreeTrialButton() {
        scrollToPercentage(0.3);
        return scrollAndClick(businessStartFreeTrialButtonLocator, "Start Free Trial button for Business package");
    }

    /**
     * Click Start Free Trial button for Premium package
     * 
     * @return true jika berhasil
     */
    public boolean clickPremiumStartFreeTrialButton() {
        scrollToPercentage(0.3);
        return scrollAndClick(premiumStartFreeTrialButtonLocator, "Start Free Trial button for Premium package");
    }

    /**
     * Click Contact Us button for Dedicated package
     * 
     * @return true jika berhasil
     */
    public boolean clickDedicatedContactUsButton() {
        scrollToPercentage(0.3);
        return scrollAndClick(dedicatedContactUsButtonLocator, "Contact Us button for Dedicated package");
    }

    /**
     * Click "click here to read about our migration services" link
     * 
     * @return true jika berhasil
     */
    public boolean clickMigrationServicesLink() {
        scrollToPercentage(0.7);
        return scrollAndClick(migrationServicesLinkLocator, "Migration Services link");
    }

    /**
     * Click "Get Started With SuiteCRM Hosted" button
     * 
     * @return true jika berhasil
     */
    public boolean clickGetStartedWithSuiteCRMHostedButton() {
        scrollToPercentage(0.8);
        return scrollAndClick(getStartedWithSuiteCRMHostedButtonLocator, "Get Started With SuiteCRM Hosted button");
    }
}
