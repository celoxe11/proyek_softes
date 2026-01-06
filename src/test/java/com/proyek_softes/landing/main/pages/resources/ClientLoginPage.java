package com.proyek_softes.landing.main.pages.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk halaman Client Login
 * Extends ResourcesPage untuk menggunakan method umum
 */
public class ClientLoginPage extends ResourcesPage {

    // Username field
    private By usernameFieldLocator = By.cssSelector("input[name='username']");

    // Password field
    private By passwordFieldLocator = By.cssSelector("input[name='password']");

    // LOG IN button
    private By loginButtonLocator = By.cssSelector("button[type='submit'].login-button");

    // Error message
    private By errorMessageLocator = By.xpath("//div[contains(text(), 'Username and password do not match')]");

    // Forgot your username link
    private By forgotUsernameLink = By.cssSelector("a[href*='remind?item=101']");

    // Email address field (forgot username form)
    private By emailFieldLocator = By.cssSelector("input[name='form_email']");

    // Submit button (forgot username form)
    private By submitButtonLocator = By.cssSelector("button[type='submit'].btn-primary");

    // Success message after forgot username
    private By successMessageLocator = By.xpath("//div[contains(text(), 'If the email address you entered is registered')]");

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ClientLoginPage(WebDriver driver) {
        super(driver);
    }

    // ========================================
    // PAGE ACTIONS - Specific to Client Login
    // ========================================

    /**
     * Enter username
     * 
     * @param username username to enter
     * @return true jika berhasil
     */
    public boolean enterUsername(String username) {
        try {
            System.out.println("Entering username: " + username);
            wait.until(d -> driver.findElement(usernameFieldLocator)).clear();
            wait.until(d -> driver.findElement(usernameFieldLocator)).sendKeys(username);
            System.out.println("Username entered successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to enter username: " + e.getMessage());
            return false;
        }
    }

    /**
     * Enter password
     * 
     * @param password password to enter
     * @return true jika berhasil
     */
    public boolean enterPassword(String password) {
        try {
            System.out.println("Entering password");
            wait.until(d -> driver.findElement(passwordFieldLocator)).clear();
            wait.until(d -> driver.findElement(passwordFieldLocator)).sendKeys(password);
            System.out.println("Password entered successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to enter password: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click LOG IN button
     * 
     * @return true jika berhasil
     */
    public boolean clickLoginButton() {
        return scrollAndClick(loginButtonLocator, "LOG IN button");
    }

    /**
     * Verify error message is visible
     * 
     * @param expectedMessage expected error message text
     * @return true jika message terlihat
     */
    public boolean verifyErrorMessage(String expectedMessage) {
        try {
            wait.until(d -> driver.findElement(errorMessageLocator).isDisplayed());
            String actualMessage = driver.findElement(errorMessageLocator).getText();
            System.out.println("Error message: " + actualMessage);
            boolean matches = actualMessage.contains(expectedMessage);
            if (matches) {
                System.out.println("Error message verified");
            } else {
                System.out.println("Error message does not match. Expected: " + expectedMessage + ", Actual: " + actualMessage);
            }
            return matches;
        } catch (Exception e) {
            System.out.println("Error message not visible: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click Forgot your username link
     * 
     * @return true jika berhasil
     */
    public boolean clickForgotUsernameLink() {
        return scrollAndClick(forgotUsernameLink, "Forgot your username link");
    }

    /**
     * Enter email address in forgot username form
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
     * Click Submit button on forgot username form
     * 
     * @return true jika berhasil
     */
    public boolean clickSubmitButton() {
        return scrollAndClick(submitButtonLocator, "Submit button");
    }

    /**
     * Verify success message is visible after forgot username
     * 
     * @param expectedMessage expected success message text
     * @return true jika message terlihat
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
