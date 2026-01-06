package com.proyek_softes.landing.main.pages.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk halaman Training
 * Extends ResourcesPage untuk menggunakan method umum
 */
public class TrainingPage extends ResourcesPage {

    // Try for Free button
    private By tryForFreeButtonLocator = By.cssSelector("a.fusion-button[href*='/demo/']");

    // Contact Us to Receive Pricing for Training button
    private By contactUsPricingButtonLocator = By.cssSelector("a.fusion-button[href*='/about/about-us/contact/']");

    // Take Me to the Masterclasses Now button (Developer Training)
    private By masterclassesButtonLocator = By.cssSelector("a.fusion-button[href*='developer-master-classes']");

    // Privacy Policy checkbox
    private By privacyPolicyCheckboxLocator = By.cssSelector("input[name*='Privacy Policy']");

    // Pay with Card button
    private By payWithCardButtonLocator = By.cssSelector("button.simpay-payment-btn");

    // Back button (browser back navigation)

    // Series 2 text element
    private By series2TextLocator = By.xpath("//h3[contains(text(), 'Series 2 - SuiteCRM Developer Basics')]");

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public TrainingPage(WebDriver driver) {
        super(driver);
    }

    // ========================================
    // PAGE ACTIONS - Specific to Training
    // ========================================

    /**
     * Click Try for Free button
     * 
     * @return true jika berhasil
     */
    public boolean clickTryForFreeButton() {
        scrollToPercentage(0.5);
        return scrollAndClick(tryForFreeButtonLocator, "Try for Free button");
    }

    /**
     * Click Contact Us to Receive Pricing for Training button
     * 
     * @return true jika berhasil
     */
    public boolean clickContactUsPricingButton() {
        scrollToPercentage(0.6);
        return scrollAndClick(contactUsPricingButtonLocator, "Contact Us to Receive Pricing for Training button");
    }

    /**
     * Click Take Me to the Masterclasses Now button
     * 
     * @return true jika berhasil
     */
    public boolean clickMasterclassesButton() {
        scrollToPercentage(0.7);
        return scrollAndClick(masterclassesButtonLocator, "Take Me to the Masterclasses Now button");
    }

    /**
     * Check Privacy Policy checkbox
     * 
     * @return true jika berhasil
     */
    public boolean checkPrivacyPolicyCheckbox() {
        scrollToPercentage(0.8);
        return scrollAndClick(privacyPolicyCheckboxLocator, "Privacy Policy checkbox");
    }

    /**
     * Click Pay with Card button
     * 
     * @return true jika berhasil
     */
    public boolean clickPayWithCardButton() {
        scrollToPercentage(0.9);
        return scrollAndClick(payWithCardButtonLocator, "Pay with Card button");
    }

    /**
     * Click browser back button
     */
    public void clickBackButton() {
        try {
            driver.navigate().back();
            System.out.println("✓ Clicked browser back button");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("✗ Failed to click back button: " + e.getMessage());
        }
    }

    /**
     * Verify Series 2 text is visible
     * 
     * @return true jika text terlihat
     */
    public boolean verifySeries2TextVisible() {
        try {
            wait.until(d -> driver.findElement(series2TextLocator).isDisplayed());
            System.out.println("✓ Series 2 text is visible");
            return true;
        } catch (Exception e) {
            System.out.println("✗ Series 2 text not visible: " + e.getMessage());
            return false;
        }
    }
}
