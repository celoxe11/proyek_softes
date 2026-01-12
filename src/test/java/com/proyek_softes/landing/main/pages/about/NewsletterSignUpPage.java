package com.proyek_softes.landing.main.pages.about;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object untuk halaman Newsletter Sign Up
 * Extends AboutBasePage untuk menggunakan method umum
 */
public class NewsletterSignUpPage extends AboutBasePage {

    // Locators untuk form elements
    private By emailInputLocator = By.cssSelector("input[name='mauticform[email]']");
    private By privacyPolicyCheckboxLocator = By.id("mauticform_checkboxgrp_checkbox_i_have_read_the_privacy_p_10");
    private By marketingCheckboxLocator = By.id("mauticform_checkboxgrp_checkbox_i_would_like_to_receive_m_10");
    private By submitButtonLocator = By.cssSelector("button[name='mauticform[submit]']");
    private By successMessageLocator = By.xpath("//div[contains(text(),'Thank you for joining our mailing list!')]");

    public NewsletterSignUpPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Verify we are on the Newsletter Sign Up page
     * 
     * @return true if on newsletter page
     */
    public boolean isOnNewsletterPage() {
        String currentUrl = getCurrentUrl();
        return currentUrl.contains("newsletter") || currentUrl.contains("sign-up");
    }

    /**
     * Fill email field
     * 
     * @param email email address to fill
     * @return true jika berhasil
     */
    public boolean fillEmail(String email) {
        try {
            System.out.println("Mengisi email: " + email);
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputLocator));
            emailInput.clear();
            emailInput.sendKeys(email);
            System.out.println("Email berhasil diisi");
            return true;
        } catch (Exception e) {
            System.out.println("Error mengisi email: " + e.getMessage());
            return false;
        }
    }

    /**
     * Centang Privacy Policy checkbox
     * 
     * @return true jika berhasil
     */
    public boolean checkPrivacyPolicy() {
        try {
            System.out.println("Mencentang Privacy Policy checkbox");
            
            waitForPageLoad();
            scrollToPercentage(0.6);
            Thread.sleep(500);
            
            // Try multiple locators
            By[] locators = {
                By.id("mauticform_checkboxgrp_checkbox_i_have_read_the_privacy_p_10"),
                By.cssSelector("input[name='mauticform[i_have_read_the_privacy_p][]']"),
                By.xpath("//input[@id='mauticform_checkboxgrp_checkbox_i_have_read_the_privacy_p_10']"),
                By.xpath("//label[contains(., 'Privacy Policy')]//input[@type='checkbox']"),
                By.xpath("//input[@type='checkbox' and contains(@id, 'privacy')]")
            };
            
            for (By locator : locators) {
                try {
                    WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    
                    // Scroll to checkbox
                    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", checkbox);
                    Thread.sleep(500);
                    
                    if (!checkbox.isSelected()) {
                        // Click checkbox
                        try {
                            checkbox.click();
                        } catch (Exception e) {
                            js.executeScript("arguments[0].click();", checkbox);
                        }
                        
                        System.out.println("Privacy Policy checkbox berhasil dicentang");
                    } else {
                        System.out.println("Privacy Policy checkbox sudah tercentang");
                    }
                    return true;
                } catch (Exception e) {
                    System.out.println("Locator failed: " + locator + " - " + e.getMessage());
                    continue;
                }
            }
            
            System.out.println("Semua locator privacy policy gagal");
            return false;
            
        } catch (Exception e) {
            System.out.println("Error mencentang Privacy Policy: " + e.getMessage());
            return false;
        }
    }

    /**
     * Centang marketing communications checkbox
     * 
     * @return true jika berhasil
     */
    public boolean checkMarketingCommunications() {
        try {
            System.out.println("Mencentang marketing communications checkbox");
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(marketingCheckboxLocator));
            
            if (!checkbox.isSelected()) {
                // Scroll to checkbox
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", checkbox);
                Thread.sleep(500);
                
                // Click checkbox
                try {
                    checkbox.click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", checkbox);
                }
                
                System.out.println("Marketing communications checkbox berhasil dicentang");
            } else {
                System.out.println("Marketing communications checkbox sudah tercentang");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error mencentang marketing communications: " + e.getMessage());
            return false;
        }
    }

    /**
     * Handle CAPTCHA (for automation, we just wait and note it)
     * Note: CAPTCHA tidak bisa di-automate, test ini akan fail di production
     * 
     * @return true (placeholder)
     */
    public boolean handleCaptcha() {
        try {
            System.out.println("==============================================");
            System.out.println("CAPTCHA DETECTED - MANUAL INTERVENTION NEEDED");
            System.out.println("==============================================");
            System.out.println("Please solve the CAPTCHA manually...");
            System.out.println("Waiting 60 seconds for manual CAPTCHA completion...");
            
            // Wait 60 seconds for manual CAPTCHA solving
            for (int i = 120; i > 0; i--) {
                System.out.println("Time remaining: " + i + " seconds");
                Thread.sleep(1000);
            }
            
            System.out.println("CAPTCHA wait time completed");
            System.out.println("==============================================");
            
            return true;
        } catch (Exception e) {
            System.out.println("CAPTCHA handling error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click Submit button
     * 
     * @return true jika berhasil
     */
    public boolean clickSubmitButton() {
        try {
            System.out.println("Menekan button Submit");
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator));
            
            // Scroll to button
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", submitButton);
            Thread.sleep(500);
            
            // Click button
            try {
                submitButton.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", submitButton);
            }
            
            System.out.println("Submit button berhasil diklik");
            return true;
        } catch (Exception e) {
            System.out.println("Error klik Submit button: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify success message appears
     * 
     * @return true jika success message muncul
     */
    public boolean verifySuccessMessage() {
        try {
            System.out.println("Verifying success message");
            waitSeconds(3);
            
            WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(successMessageLocator));
            
            if (successMessage.isDisplayed()) {
                String messageText = successMessage.getText();
                System.out.println("Success message found: " + messageText);
                return messageText.contains("Thank you for joining our mailing list!");
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("Success message not found: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify required error message appears
     * 
     * @return true jika error message "This is required." muncul
     */
    public boolean verifyRequiredErrorMessage() {
        try {
            System.out.println("Verifying required error message");
            waitSeconds(1);
            
            // Multiple locators to find the error message
            By[] errorLocators = {
                By.xpath("//*[contains(text(),'This is required')]"),
                By.xpath("//span[contains(text(),'This is required')]"),
                By.cssSelector(".mauticform-errormsg"),
                By.cssSelector("span.mauticform-errormsg"),
                By.xpath("//span[@class='mauticform-errormsg']"),
                By.xpath("//*[contains(@style,'color') and contains(text(),'required')]")
            };
            
            for (By locator : errorLocators) {
                try {
                    WebElement errorMessage = driver.findElement(locator);
                    if (errorMessage.isDisplayed()) {
                        String messageText = errorMessage.getText();
                        System.out.println("Error message found: " + messageText);
                        if (messageText.toLowerCase().contains("required")) {
                            return true;
                        }
                    }
                } catch (Exception e) {
                    // Continue to next locator
                    continue;
                }
            }
            
            // Fallback: check if page still has the form (submission blocked by validation)
            try {
                String pageSource = driver.getPageSource();
                if (pageSource.contains("This is required")) {
                    System.out.println("Validation error found in page source");
                    return true;
                }
            } catch (Exception e) {
                // Ignore
            }
            
            System.out.println("✗ Error message not found");
            return false;
        } catch (Exception e) {
            System.out.println("Required error message verification failed: " + e.getMessage());
            return false;
        }
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
}
