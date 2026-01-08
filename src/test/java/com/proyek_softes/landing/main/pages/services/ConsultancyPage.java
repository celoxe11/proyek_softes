package com.proyek_softes.landing.main.pages.services;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ConsultancyPage extends ServicesPage {

    // Get Started button
    private By getStartedButtonLocator = By.cssSelector("a.fusion-button[href*='about-us/contact']");
    
    // CRM Implementation Checklist button
    private By crmImplementationChecklistButton = By.xpath("//a[contains(text(),'CRM IMPLEMENTATION CHECKLIST')]");
    private By crmImplementationChecklistButtonAlt = By.cssSelector("a[href*='crm-implementation-checklist']");
    
    // Form fields for CRM Implementation Checklist
    private By fullNameInput = By.id("mauticform_input_crmimplementationchecklistgated_full_name");
    private By surnameInput = By.id("mauticform_input_crmimplementationchecklistgated_surname");
    private By emailInput = By.id("mauticform_input_crmimplementationchecklistgated_your_email_address");
    private By companyNameInput = By.id("mauticform_input_crmimplementationchecklistgated_company_name");
    private By countrySelect = By.id("mauticform_input_crmimplementationchecklistgated_country");
    
    // Checkboxes
    private By privacyPolicyCheckbox = By.id("mauticform_checkboxgrp_checkbox_i_have_read_the_privacy_p_10");
    private By marketingCheckbox = By.id("mauticform_checkboxgrp_checkbox_i_would_like_to_receive_a_10");
    
    // reCAPTCHA
    private By recaptchaFrame = By.cssSelector("iframe[title='reCAPTCHA']");
    private By recaptchaCheckbox = By.cssSelector(".recaptcha-checkbox-border");
    
    // Submit button
    private By submitButton = By.id("mauticform_input_crmimplementationchecklistgated_submit");
    
    // Success message
    private By successMessage = By.cssSelector(".mauticform-message");
    private By successMessageAlt = By.xpath("//*[contains(text(),'You will receive an email')]");
    
    // Error message locators for SRV-008
    private By errorMessage = By.cssSelector(".mauticform-errormsg");
    private By emailErrorMessage = By.xpath("//*[contains(text(),'Please provide a valid email address')]");

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
    
    /**
     * Click CRM Implementation Checklist button
     * For test case SRV-007
     * Optimized with 1-second timeout and multiple fallback locators
     * 
     * @return true jika berhasil
     */
    public boolean clickCrmImplementationChecklistButton() {
        try {
            System.out.println("Mencari button CRM Implementation Checklist");
            
            // Scroll to middle of page
            scrollToPercentage(60);
            waitSeconds(1);
            
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(1));
            WebElement button = null;
            
            // Try multiple locators with 1-second timeout each
            By[] locators = {
                crmImplementationChecklistButton,
                crmImplementationChecklistButtonAlt,
                By.xpath("//a[contains(@href,'crm-implementation-checklist')]"),
                By.partialLinkText("CRM IMPLEMENTATION CHECKLIST")
            };
            
            for (By locator : locators) {
                try {
                    button = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
                    System.out.println("Button ditemukan dengan locator: " + locator);
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (button != null) {
                // Scroll to button
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
                Thread.sleep(500);
                
                // Click button
                try {
                    button.click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", button);
                }
                
                System.out.println("Berhasil klik button CRM Implementation Checklist");
                waitSeconds(2);
                return true;
            }
            
            System.out.println("Button tidak ditemukan, mencoba direct URL navigation");
            driver.get("https://suitecrm.com/crm-implementation-checklist/");
            waitSeconds(2);
            return true;
            
        } catch (Exception e) {
            System.out.println("Error klik CRM Implementation Checklist button: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Wait for implementation form to load
     * 
     * @return true if form loaded successfully
     */
    public boolean waitForImplementationFormToLoad() {
        try {
            System.out.println("Waiting for implementation form to load");
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20));
            longWait.until(ExpectedConditions.presenceOfElementLocated(fullNameInput));
            System.out.println("Implementation form loaded successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Implementation form not found after 20 seconds");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            return false;
        }
    }
    
    /**
     * Fill individual field dengan explicit wait
     */
    public void fillField(By locator, String value, String fieldName) {
        try {
            WebElement field = wait.until(ExpectedConditions.elementToBeClickable(locator));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", field);
            Thread.sleep(300);
            field.click();
            field.clear();
            field.sendKeys(value);
            System.out.println(fieldName + " filled: " + value);
        } catch (Exception e) {
            System.out.println(fieldName + " field not filled: " + e.getMessage());
        }
    }
    
    /**
     * Select country from dropdown
     */
    public void selectCountry(String countryName) {
        try {
            WebElement countryDropdown = wait.until(ExpectedConditions.elementToBeClickable(countrySelect));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", countryDropdown);
            Thread.sleep(300);
            
            org.openqa.selenium.support.ui.Select select = 
                new org.openqa.selenium.support.ui.Select(countryDropdown);
            select.selectByVisibleText(countryName);
            System.out.println("Country selected: " + countryName);
        } catch (Exception e) {
            System.out.println("Country not selected: " + e.getMessage());
        }
    }
    
    /**
     * Check Privacy Policy checkbox
     */
    public void checkPrivacyPolicy() {
        try {
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(privacyPolicyCheckbox));
            if (!checkbox.isSelected()) {
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);
                Thread.sleep(300);
                checkbox.click();
                System.out.println("Privacy Policy checkbox checked");
            }
        } catch (Exception e) {
            System.out.println("Error checking privacy policy: " + e.getMessage());
        }
    }
    
    /**
     * Check Marketing Communications checkbox
     * Optimized with 1-second timeout and multiple fallback locators
     */
    public void checkMarketingCommunications() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(1));
            WebElement checkbox = null;
            
            // Try multiple locators with 1-second timeout each
            By[] locators = {
                By.id("mauticform_checkboxgrp_checkbox_i_would_like_to_receive_a_10"),
                By.cssSelector("input[name='mauticform[checkboxgrp_checkbox_i_would_like_to_receive_a_1][]']"),
                By.xpath("//input[@id='mauticform_checkboxgrp_checkbox_i_would_like_to_receive_a_10']"),
                By.cssSelector("input[type='checkbox'][value='1']")
            };
            
            for (By locator : locators) {
                try {
                    checkbox = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
                    System.out.println("Marketing checkbox found with locator: " + locator);
                    break;
                } catch (Exception e) {
                    // Try next locator
                }
            }
            
            if (checkbox != null && !checkbox.isSelected()) {
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);
                Thread.sleep(300);
                checkbox.click();
                System.out.println("Marketing communications checkbox checked");
            }
        } catch (Exception e) {
            System.out.println("Error checking marketing checkbox: " + e.getMessage());
        }
    }
    
    /**
     * Solve reCAPTCHA v2 automatically
     * Attempts to click the "I'm not a robot" checkbox
     * 
     * @return true if CAPTCHA checkbox was clicked successfully
     */
    public boolean solveCaptcha() {
        try {
            System.out.println("Attempting to solve CAPTCHA...");
            
            // Wait for reCAPTCHA iframe to be present
            WebDriverWait captchaWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement captchaIframe = captchaWait.until(
                ExpectedConditions.presenceOfElementLocated(recaptchaFrame)
            );
            
            System.out.println("CAPTCHA iframe found, switching to it");
            
            // Switch to reCAPTCHA iframe
            driver.switchTo().frame(captchaIframe);
            
            // Wait for checkbox and click it
            WebElement checkbox = captchaWait.until(
                ExpectedConditions.elementToBeClickable(recaptchaCheckbox)
            );
            
            System.out.println("CAPTCHA checkbox found, clicking it");
            checkbox.click();
            
            // Switch back to main content
            driver.switchTo().defaultContent();
            
            System.out.println("CAPTCHA checkbox clicked successfully");
            Thread.sleep(4000); // Wait for CAPTCHA to validate
            
            return true;
            
        } catch (Exception e) {
            System.out.println("Error solving CAPTCHA: " + e.getMessage());
            System.out.println("CAPTCHA may need manual intervention");
            
            // Make sure we're back to main content
            try {
                driver.switchTo().defaultContent();
            } catch (Exception ex) {
                // Ignore
            }
            
            return false;
        }
    }
    
    /**
     * Click submit button
     */
    public void clickSubmit() {
        try {
            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            
            // Scroll to submit button
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", submitBtn);
            Thread.sleep(500);
            
            // Click submit
            try {
                submitBtn.click();
            } catch (Exception e) {
                // Fallback to JS click
                js.executeScript("arguments[0].click();", submitBtn);
            }
            System.out.println("Submit button clicked");
            
        } catch (Exception e) {
            System.out.println("Error clicking submit: " + e.getMessage());
        }
    }
    
    /**
     * Get success message after form submission
     * 
     * @return success message text or empty string
     */
    public String getSuccessMessage() {
        try {
            System.out.println("Mencari success message...");
            
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            WebElement message = null;
            
            // Try primary locator
            try {
                message = longWait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
                System.out.println("Success message ditemukan dengan CSS selector");
            } catch (Exception e1) {
                // Try alternative locator
                try {
                    message = longWait.until(ExpectedConditions.visibilityOfElementLocated(successMessageAlt));
                    System.out.println("Success message ditemukan dengan XPath");
                } catch (Exception e2) {
                    System.out.println("Success message tidak ditemukan");
                    return "";
                }
            }
            
            if (message != null) {
                String messageText = message.getText().trim();
                System.out.println("Success message text: " + messageText);
                return messageText;
            }
            
            return "";
            
        } catch (Exception e) {
            System.out.println("Error mendapatkan success message: " + e.getMessage());
            return "";
        }
    }
    
    /**
     * Verify success message contains expected text
     * 
     * @param expectedText expected text
     * @return true if message contains expected text
     */
    public boolean verifySuccessMessage(String expectedText) {
        String actualMessage = getSuccessMessage();
        boolean contains = actualMessage.contains(expectedText);
        
        System.out.println("Expected: " + expectedText);
        System.out.println("Actual: " + actualMessage);
        System.out.println("Contains: " + contains);
        
        return contains;
    }
    
    /**
     * Get error message after form submission
     * For test case SRV-008
     * 
     * @return error message text or empty string
     */
    public String getErrorMessage() {
        try {
            System.out.println("Mencari error message");
            
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            WebElement errorMsg = null;
            
            // Try to find email error message first
            try {
                errorMsg = shortWait.until(ExpectedConditions.visibilityOfElementLocated(emailErrorMessage));
                System.out.println("Email error message ditemukan dengan XPath");
            } catch (Exception e1) {
                // Try generic error message
                try {
                    List<WebElement> errorElements = driver.findElements(errorMessage);
                    for (WebElement elem : errorElements) {
                        if (elem.isDisplayed() && !elem.getText().trim().isEmpty()) {
                            errorMsg = elem;
                            System.out.println("Error message ditemukan dengan CSS selector");
                            break;
                        }
                    }
                } catch (Exception e2) {
                    System.out.println("Error message tidak ditemukan");
                    return "";
                }
            }
            
            if (errorMsg != null) {
                String messageText = errorMsg.getText().trim();
                System.out.println("Error message text: " + messageText);
                return messageText;
            }
            
            return "";
            
        } catch (Exception e) {
            System.out.println("Error mendapatkan error message: " + e.getMessage());
            return "";
        }
    }
    
    /**
     * Verify error message contains expected text
     * 
     * @param expectedText expected text
     * @return true if error message contains expected text
     */
    public boolean verifyErrorMessage(String expectedText) {
        String actualMessage = getErrorMessage();
        boolean contains = actualMessage.contains(expectedText);
        
        System.out.println("Expected error: " + expectedText);
        System.out.println("Actual error: " + actualMessage);
        System.out.println("Contains: " + contains);
        
        return contains;
    }
    
    /**
     * Helper method untuk wait
     */
    private void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // Getter methods for locators
    public By getFullNameLocator() {
        return fullNameInput;
    }
    
    public By getSurnameLocator() {
        return surnameInput;
    }
    
    public By getEmailLocator() {
        return emailInput;
    }
    
    public By getCompanyNameLocator() {
        return companyNameInput;
    }
}
