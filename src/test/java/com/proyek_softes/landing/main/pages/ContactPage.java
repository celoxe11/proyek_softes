package com.proyek_softes.landing.main.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ContactPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Locators berdasarkan inspect element screenshot
    private By firstNameInput = By.id("mauticform_input_suitecrmcontactform_first_name");
    private By lastNameInput = By.id("mauticform_input_suitecrmcontactform_last_name");
    private By emailInput = By.id("mauticform_input_suitecrmcontactform_email");
    private By phoneInput = By.id("mauticform_input_suitecrmcontactform_phone");
    private By companyInput = By.id("mauticform_input_suitecrmcontactform_company");
    private By jobTitleInput = By.id("mauticform_input_suitecrmcontactform_job_title");
    private By countryInput = By.id("mauticform_input_suitecrmcontactform_country"); // bersifat dropdown (select item)
    private By messageInput = By.id("mauticform_input_suitecrmcontactform_f_message"); // textarea
    
    // Checkboxes berdasarkan screenshot
    private By privacyPolicyCheckbox = By.id("mauticform_suitecrmcontactform_i_have_read_the_privacy_p");
    private By marketingCheckbox = By.id("mauticform_suitecrmcontactform_i_would_like_to_receive_m");
    
    // reCAPTCHA locators
    private By recaptchaFrame = By.cssSelector("iframe[title='reCAPTCHA']");
    private By recaptchaCheckbox = By.cssSelector(".recaptcha-checkbox-border");
    
    private By submitButton = By.id("mauticform_suitecrmcontactform_submit");
    
    // Error message locator
    private By errorMessage = By.cssSelector("span.mauticform-errormsg");
    private By firstNameError = By.cssSelector("#mauticform_suitecrrmcontactform_first_name span.mauticform-errormsg");

    public ContactPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }
    
    // Wait sampai form contact benar-benar muncul
    public boolean waitForFormToLoad() {
        try {
            System.out.println("⏳ Waiting for contact form to load...");
            System.out.println("   Current URL: " + driver.getCurrentUrl());
            
            // Cek apakah ada iframe
            try {
                List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
                System.out.println("   Found " + iframes.size() + " iframe(s) on page");
                
                // Coba cari form di main page dulu
                List<WebElement> forms = driver.findElements(By.tagName("form"));
                System.out.println("   Found " + forms.size() + " form(s) on page");
                
                // Coba cari input dengan berbagai ID yang mungkin
                List<WebElement> firstNameInputs = driver.findElements(By.cssSelector("input[id*='first_name'], input[name*='first_name'], input[placeholder*='First Name']"));
                System.out.println("   Found " + firstNameInputs.size() + " potential first name input(s)");
                
            } catch (Exception debugEx) {
                System.out.println("   Debug error: " + debugEx.getMessage());
            }
            
            // Tunggu lebih lama karena form mungkin load via iframe/async
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20));
            longWait.until(ExpectedConditions.presenceOfElementLocated(firstNameInput));
            System.out.println("✓ Contact form loaded successfully");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Contact form not found after 20 seconds");
            System.out.println("❌ Current URL: " + driver.getCurrentUrl());
            System.out.println("❌ Error: " + e.getMessage());
            
            // Print page title for debugging
            System.out.println("❌ Page title: " + driver.getTitle());
            
            return false;
        }
    }

    // Actions: Mengisi Form dengan explicit waits
    public void fillContactForm(String fName, String lName, String email, String msg) {
        try {
            System.out.println("\n🔍 Starting to fill contact form...");
            
            // Wait for form to be ready
            wait.until(ExpectedConditions.presenceOfElementLocated(firstNameInput));
            System.out.println("✓ Form is present and ready");
            
            // First Name - with scroll and click
            WebElement firstNameField = wait.until(ExpectedConditions.elementToBeClickable(firstNameInput));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstNameField);
            Thread.sleep(500);
            firstNameField.click();
            firstNameField.clear();
            firstNameField.sendKeys(fName);
            System.out.println("✓ First Name filled: " + fName);
            
            // Last Name
            WebElement lastNameField = wait.until(ExpectedConditions.elementToBeClickable(lastNameInput));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", lastNameField);
            Thread.sleep(300);
            lastNameField.click();
            lastNameField.clear();
            lastNameField.sendKeys(lName);
            System.out.println("✓ Last Name filled: " + lName);
            
            // Email
            WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(emailInput));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", emailField);
            Thread.sleep(300);
            emailField.click();
            emailField.clear();
            emailField.sendKeys(email);
            System.out.println("✓ Email filled: " + email);
            
            // Message
            WebElement messageField = wait.until(ExpectedConditions.elementToBeClickable(messageInput));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", messageField);
            Thread.sleep(300);
            messageField.click();
            messageField.clear();
            messageField.sendKeys(msg);
            System.out.println("✓ Message filled");
            
            System.out.println("✅ Contact form filled successfully\n");
            
        } catch (Exception e) {
            System.out.println("❌ Error filling form: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fill contact form", e);
        }
    }
    
    // Method untuk fill form lengkap dengan semua field
    public void fillCompleteContactForm(String fName, String lName, String email, String phone, 
                                       String company, String jobTitle, String country, String msg) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(firstNameInput));
            
            fillField(firstNameInput, fName, "First Name");
            fillField(lastNameInput, lName, "Last Name");
            fillField(emailInput, email, "Business Email");
            fillField(phoneInput, phone, "Phone");
            fillField(companyInput, company, "Company");
            fillField(jobTitleInput, jobTitle, "Job Title");
            selectCountry(country);
            fillField(messageInput, msg, "Message");
            
        } catch (Exception e) {
            System.out.println("❌ Error filling complete form: " + e.getMessage());
        }
    }
    
    // Helper method untuk fill individual field
    private void fillField(By locator, String value, String fieldName) {
        try {
            WebElement field = driver.findElement(locator);
            field.clear();
            field.sendKeys(value);
            System.out.println("✓ " + fieldName + " filled: " + value);
        } catch (Exception e) {
            System.out.println("⚠ " + fieldName + " field not filled: " + e.getMessage());
        }
    }
    
    // Method untuk select country dropdown
    public void selectCountry(String countryName) {
        try {
            WebElement countryDropdown = driver.findElement(countryInput);
            countryDropdown.click();
            Thread.sleep(500);
            
            // Select by visible text
            org.openqa.selenium.support.ui.Select select = 
                new org.openqa.selenium.support.ui.Select(countryDropdown);
            select.selectByVisibleText(countryName);
            System.out.println("✓ Country selected: " + countryName);
        } catch (Exception e) {
            System.out.println("⚠ Country not selected: " + e.getMessage());
        }
    }
    
    // Method untuk check Privacy Policy checkbox (REQUIRED)
    public void checkPrivacyPolicy() {
        try {
            WebElement privacyCheckbox = wait.until(ExpectedConditions.elementToBeClickable(privacyPolicyCheckbox));
            if (!privacyCheckbox.isSelected()) {
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", privacyCheckbox);
                Thread.sleep(300);
                privacyCheckbox.click();
                System.out.println("✓ Privacy Policy checkbox checked");
            }
        } catch (Exception e) {
            System.out.println("❌ Error checking privacy policy: " + e.getMessage());
        }
    }
    
    // Method untuk check Marketing Communications checkbox (OPTIONAL)
    public void checkMarketingCommunications() {
        try {
            WebElement marketingCheckbox = wait.until(ExpectedConditions.elementToBeClickable(this.marketingCheckbox));
            if (!marketingCheckbox.isSelected()) {
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", marketingCheckbox);
                Thread.sleep(300);
                marketingCheckbox.click();
                System.out.println("✓ Marketing communications checkbox checked");
            }
        } catch (Exception e) {
            System.out.println("❌ Error checking marketing checkbox: " + e.getMessage());
        }
    }
    
    // Method untuk handle reCAPTCHA
    public void solveRecaptcha() {
        try {
            System.out.println("⚠ Attempting to solve reCAPTCHA...");
            
            // Wait for reCAPTCHA iframe to be present
            WebElement recaptchaIframe = wait.until(ExpectedConditions.presenceOfElementLocated(recaptchaFrame));
            
            // Scroll to reCAPTCHA
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", recaptchaIframe);
            Thread.sleep(1000);
            
            // Switch to reCAPTCHA iframe
            driver.switchTo().frame(recaptchaIframe);
            
            // Click the checkbox
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(recaptchaCheckbox));
            checkbox.click();
            System.out.println("✓ reCAPTCHA checkbox clicked");
            
            // Switch back to main content
            driver.switchTo().defaultContent();
            
            // Wait for reCAPTCHA to validate (may take a few seconds)
            Thread.sleep(3000);
            
            System.out.println("✓ reCAPTCHA solved (or attempted)");
            
        } catch (Exception e) {
            System.out.println("⚠ reCAPTCHA handling warning: " + e.getMessage());
            System.out.println("⚠ Note: reCAPTCHA may require manual intervention or may not be solvable in automation");
            // Switch back to main content if stuck in iframe
            try {
                driver.switchTo().defaultContent();
            } catch (Exception ex) {
                // Ignore
            }
        }
    }

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
            System.out.println("✓ Submit button clicked");
            
        } catch (Exception e) {
            System.out.println("❌ Error clicking submit: " + e.getMessage());
        }
    }
    
    // Verification methods
    public boolean isFormDisplayed() {
        try {
            return driver.findElement(firstNameInput).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean hasValidationError() {
        try {
            Thread.sleep(1000);
            return driver.findElement(errorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getFirstNameErrorMessage() {
        try {
            return driver.findElement(firstNameError).getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
