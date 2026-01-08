package com.proyek_softes.landing.main.pages.services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk halaman Support Services
 * Extends ServicesPage untuk menggunakan method umum
 */
public class SupportServicesPage extends ServicesPage {

    // Contact Us buttons for each package
    private By goldContactUsButtonLocator = By
            .xpath("(//a[contains(@class, 'fusion-button') and contains(.//span, 'Contact Us')])[2]");

    // Links on Support Services page
    private By enterpriseVerificationServiceLinkLocator = By.cssSelector("a[href*='enterprise-verification-service']");
    private By fullyManagedHostingLinkLocator = By.cssSelector("a[href*='suitecrmhosted']");
    private By termsAndConditionsLinkLocator = By
            .cssSelector("a[href*='SuiteCRM_Support_Services_Terms_And_Conditions']");

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public SupportServicesPage(WebDriver driver) {
        super(driver);
    }

    // ========================================
    // PAGE ACTIONS - Specific to Support Services
    // ========================================

    /**
     * Click Contact Us button for Gold package
     * 
     * @return true jika berhasil
     */
    public boolean clickGoldContactUsButton() {
        scrollToPercentage(0.33);
        return scrollAndClick(goldContactUsButtonLocator, "Contact Us button for Gold package");
    }

    /**
     * Click Enterprise Verification Service link
     * 
     * @return true jika berhasil
     */
    public boolean clickEnterpriseVerificationServiceLink() {
        scrollToPercentage(0.5);
        
        By[] locators = {
            By.cssSelector("a[href*='enterprise-verification-service']"),
            By.xpath("//a[contains(@href, 'enterprise-verification-service')]"),
            By.partialLinkText("Enterprise Verification Service"),
            By.linkText("Enterprise Verification Service")
        };
        
        return scrollAndClickWithFallback(locators, "Enterprise Verification Service link");
    }

    /**
     * Click "Learn more about our Fully Managed SuiteCRM Hosting Services" link
     * 
     * @return true jika berhasil
     */
    public boolean clickFullyManagedHostingLink() {
        scrollToPercentage(0.7);
        
        By[] locators = {
            By.cssSelector("a[href*='suitecrmhosted']"),
            By.xpath("//a[contains(@href, 'suitecrmhosted')]"),
            By.partialLinkText("Fully Managed"),
            By.partialLinkText("Hosting Services")
        };
        
        return scrollAndClickWithFallback(locators, "Fully Managed SuiteCRM Hosting Services link");
    }

    /**
     * Click "please click here" link for Terms and Conditions
     * 
     * @return true jika berhasil
     */
    public boolean clickTermsAndConditionsLink() {
        scrollToPercentage(0.8);
        
        By[] locators = {
            By.cssSelector("a[href*='SuiteCRM_Support_Services_Terms_And_Conditions']"),
            By.xpath("//a[contains(@href, 'SuiteCRM_Support_Services_Terms_And_Conditions')]"),
            By.xpath("//a[contains(@href, 'Terms_And_Conditions')]"),
            By.partialLinkText("please click here")
        };
        
        return scrollAndClickWithFallback(locators, "Terms and Conditions link (please click here)");
    }
}
