package com.proyek_softes.landing.main.pages.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk halaman Compare SuiteCRM
 * Extends ResourcesPage untuk menggunakan method umum
 */
public class CompareSuiteCRMPage extends ResourcesPage {

    // SuiteCRM vs Salesforce - Whitepaper button
    private By salesforceWhitepaperButtonLocator = By.cssSelector("a.fusion-button[href*='open-source-vs-salesforce']");

    // Privacy Policy link (dalam form)
    private By privacyPolicyLinkLocator = By.cssSelector("a[href*='privacy-policy']");

    // DEMO button on Salesforce comparison page
    private By salesforceDemoButtonLocator = By.cssSelector("a.fusion-button[href*='/demo/']");

    // DEMO button on Microsoft Dynamics comparison page
    private By microsoftDynamicsDemoButtonLocator = By.cssSelector("a.fusion-button[href*='/demo/']");

    // Form elements for whitepaper form
    private By fullNameFieldLocator = By.id("mauticform_input_suitecrmpvssalesforcegtedcontent_full_name");
    private By emailFieldLocator = By.id("mauticform_input_suitecrmpvssalesforcegtedcontent_your_email_address");
    private By companyNameFieldLocator = By.id("mauticform_input_suitecrmpvssalesforcegtedcontent_company_name");
    private By countryDropdownLocator = By.id("mauticform_input_suitecrmpvssalesforcegtedcontent_country");
    private By privacyPolicyCheckboxLocator = By.id("mauticform_checkboxgrp_checkbox_i_have_read_the_privacy_p_10");
    private By marketingInfoCheckboxLocator = By.id("mauticform_checkboxgrp_checkbox_i_would_like_to_receive_a_10");
    private By captchaCheckboxLocator = By.cssSelector(".recaptcha-checkbox");
    private By submitButtonLocator = By.id("mauticform_input_suitecrmpvssalesforcegtedcontent_submit");
    private By successMessageLocator = By.cssSelector(".mauticform-message");

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public CompareSuiteCRMPage(WebDriver driver) {
        super(driver);
    }

    // ========================================
    // PAGE ACTIONS - Specific to Compare SuiteCRM
    // ========================================

    /**
     * Click SuiteCRM vs Salesforce - Whitepaper button
     * 
     * @return true jika berhasil
     */
    public boolean clickSalesforceWhitepaperButton() {
        scrollToPercentage(0.4);
        return scrollAndClick(salesforceWhitepaperButtonLocator, "SuiteCRM vs Salesforce - Whitepaper button");
    }

    /**
     * Click Privacy Policy link
     * 
     * @return true jika berhasil
     */
    public boolean clickPrivacyPolicyLink() {
        scrollToPercentage(0.7);
        return scrollAndClick(privacyPolicyLinkLocator, "Privacy Policy link");
    }

    /**
     * Click DEMO button on Salesforce comparison page
     * 
     * @return true jika berhasil
     */
    public boolean clickSalesforceDemoButton() {
        scrollToPercentage(0.3);
        return scrollAndClick(salesforceDemoButtonLocator, "DEMO button");
    }

    /**
     * Click DEMO button on Microsoft Dynamics comparison page
     * 
     * @return true jika berhasil
     */
    public boolean clickMicrosoftDynamicsDemoButton() {
        scrollToPercentage(0.3);
        return scrollAndClick(microsoftDynamicsDemoButtonLocator, "DEMO button");
    }

    /**
     * Enter full name in whitepaper form
     * 
     * @param fullName full name to enter
     * @return true jika berhasil
     */
    public boolean enterFullName(String fullName) {
        try {
            System.out.println("Entering full name: " + fullName);
            wait.until(d -> driver.findElement(fullNameFieldLocator)).clear();
            wait.until(d -> driver.findElement(fullNameFieldLocator)).sendKeys(fullName);
            System.out.println("Full name entered successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to enter full name: " + e.getMessage());
            return false;
        }
    }

    /**
     * Enter email in whitepaper form
     * 
     * @param email email to enter
     * @return true jika berhasil
     */
    public boolean enterEmail(String email) {
        try {
            System.out.println("Entering email: " + email);
            wait.until(d -> driver.findElement(emailFieldLocator)).clear();
            wait.until(d -> driver.findElement(emailFieldLocator)).sendKeys(email);
            System.out.println("Email entered successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to enter email: " + e.getMessage());
            return false;
        }
    }

    /**
     * Enter company name in whitepaper form
     * 
     * @param companyName company name to enter
     * @return true jika berhasil
     */
    public boolean enterCompanyName(String companyName) {
        try {
            System.out.println("Entering company name: " + companyName);
            wait.until(d -> driver.findElement(companyNameFieldLocator)).clear();
            wait.until(d -> driver.findElement(companyNameFieldLocator)).sendKeys(companyName);
            System.out.println("Company name entered successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to enter company name: " + e.getMessage());
            return false;
        }
    }

    /**
     * Select country from dropdown
     * 
     * @param country country to select
     * @return true jika berhasil
     */
    public boolean selectCountry(String country) {
        try {
            System.out.println("Selecting country: " + country);
            org.openqa.selenium.support.ui.Select countrySelect = 
                new org.openqa.selenium.support.ui.Select(wait.until(d -> driver.findElement(countryDropdownLocator)));
            countrySelect.selectByVisibleText(country);
            System.out.println("Country selected successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to select country: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check privacy policy checkbox
     * 
     * @return true jika berhasil
     */
    public boolean checkPrivacyPolicy() {
        return scrollAndClick(privacyPolicyCheckboxLocator, "Privacy Policy checkbox");
    }

    /**
     * Check marketing information checkbox
     * 
     * @return true jika berhasil
     */
    public boolean checkMarketingInfo() {
        return scrollAndClick(marketingInfoCheckboxLocator, "Marketing Information checkbox");
    }

    /**
     * Check CAPTCHA checkbox (manual step - user must complete image selection)
     * 
     * @return true jika berhasil
     */
    public boolean checkCaptcha() {
        System.out.println("CAPTCHA checkbox - Manual step required: User must complete image selection");
        return scrollAndClick(captchaCheckboxLocator, "CAPTCHA checkbox");
    }

    /**
     * Click Submit button on whitepaper form
     * 
     * @return true jika berhasil
     */
    public boolean clickSubmitButton() {
        return scrollAndClick(submitButtonLocator, "Submit button");
    }

    /**
     * Verify success message is displayed
     * 
     * @param expectedMessage expected success message text
     * @return true jika message terlihat dan sesuai
     */
    public boolean verifySuccessMessage(String expectedMessage) {
        try {
            wait.until(d -> driver.findElement(successMessageLocator).isDisplayed());
            String actualMessage = driver.findElement(successMessageLocator).getText();
            System.out.println("Success message: " + actualMessage);
            boolean matches = actualMessage.contains(expectedMessage);
            if (matches) {
                System.out.println("Success message verified");
            } else {
                System.out.println("Success message does not match. Expected: " + expectedMessage + ", Actual: " + actualMessage);
            }
            return matches;
        } catch (Exception e) {
            System.out.println("Success message not visible: " + e.getMessage());
            return false;
        }
    }
}
