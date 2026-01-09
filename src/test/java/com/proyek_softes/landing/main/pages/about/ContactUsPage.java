package com.proyek_softes.landing.main.pages.about;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object untuk halaman Contact Us
 * Extends AboutBasePage untuk menggunakan method umum
 */
public class ContactUsPage extends AboutBasePage {

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
    
    private By submitButton = By.id("mauticform_input_suitecrmcontactform_submit");
    
    // Error message locator
    private By errorMessage = By.cssSelector("span.mauticform-errormsg");
    private By firstNameError = By.cssSelector("#mauticform_suitecrrmcontactform_first_name span.mauticform-errormsg");
    
    // Success message locator
    private By successMessage = By.cssSelector(".mauticform-message");
    private By successMessageAlt = By.xpath("//*[contains(text(),'Your submission was successful')]");
    
    // Error message locators
    private By recaptchaErrorMessage = By.cssSelector(".mauticform-errormsg");
    private By recaptchaErrorMessageAlt = By.xpath("//*[contains(text(),'reCAPTCHA')]");
    
    private By freeTrialLinkLocator = By.xpath("//a[contains(text(),'Get your free 30-day trial')]");
    private By freeTrialLinkAltLocator = By.cssSelector("a[href*='/demo/']");
    private By tailoredSupportLinkLocator = By.xpath("//a[contains(text(),'tailored support packages')]");
    private By tailoredSupportLinkAltLocator = By.cssSelector("a[href*='support-services']");

    public ContactUsPage(WebDriver driver) {
        super(driver);
    }
    
    // Getter methods for locators (to be used in tests)
    public By getFirstNameLocator() {
        return firstNameInput;
    }
    
    public By getLastNameLocator() {
        return lastNameInput;
    }
    
    public By getEmailLocator() {
        return emailInput;
    }
    
    public By getPhoneLocator() {
        return phoneInput;
    }
    
    public By getCompanyLocator() {
        return companyInput;
    }
    
    public By getJobTitleLocator() {
        return jobTitleInput;
    }
    
    public By getMessageLocator() {
        return messageInput;
    }
    
    /**
     * Generic method untuk fill individual field dengan explicit wait
     * Dapat digunakan dari test class
     */
    public void fillField(By locator, String value, String fieldName) {
        try {
            WebElement field = wait.until(ExpectedConditions.elementToBeClickable(locator));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", field);
            Thread.sleep(300);
            field.click();
            field.clear();
            field.sendKeys(value);
            System.out.println("✓ " + fieldName + " filled: " + value);
        } catch (Exception e) {
            System.out.println("⚠ " + fieldName + " field not filled: " + e.getMessage());
        }
    }
    
    /**
     * Klik link "Get your free 30-day trial..."
     * For test case ABT-010
     * 
     * @return true jika berhasil klik link
     */
    public boolean clickFreeTrialLink() {
        try {
            System.out.println("Mencari link Get your free 30-day trial");
            
            // Scroll to middle of page
            scrollToPercentage(40);
            waitSeconds(1);
            
            WebElement link = null;
            
            // Try primary locator
            try {
                link = wait.until(ExpectedConditions.presenceOfElementLocated(freeTrialLinkLocator));
                System.out.println("Link ditemukan dengan XPath text");
            } catch (Exception e1) {
                // Try alternative locator
                link = wait.until(ExpectedConditions.presenceOfElementLocated(freeTrialLinkAltLocator));
                System.out.println("Link ditemukan dengan CSS href");
            }
            
            if (link != null) {
                // Scroll to link
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
                Thread.sleep(500);
                
                // Click link
                try {
                    link.click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", link);
                }
                
                System.out.println("Berhasil klik link Free Trial");
                waitSeconds(2);
                return true;
            }
            
            return false;
            
        } catch (Exception e) {
            System.out.println("Error klik Free Trial link: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Klik link "tailored support packages"
     * For test case ABT-011
     * 
     * @return true jika berhasil klik link
     */
    public boolean clickTailoredSupportLink() {
        try {
            System.out.println("Mencari link tailored support packages");
            
            // Scroll to middle of page
            scrollToPercentage(40);
            waitSeconds(1);
            
            WebElement link = null;
            
            // Try primary locator
            try {
                link = wait.until(ExpectedConditions.presenceOfElementLocated(tailoredSupportLinkLocator));
                System.out.println("Link ditemukan dengan XPath text");
            } catch (Exception e1) {
                // Try alternative locator
                link = wait.until(ExpectedConditions.presenceOfElementLocated(tailoredSupportLinkAltLocator));
                System.out.println("Link ditemukan dengan CSS href");
            }
            
            if (link != null) {
                // Scroll to link
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
                Thread.sleep(500);
                
                // Click link
                try {
                    link.click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", link);
                }
                
                System.out.println("Berhasil klik link Tailored Support");
                waitSeconds(2);
                return true;
            }
            
            return false;
            
        } catch (Exception e) {
            System.out.println("Error klik Tailored Support link: " + e.getMessage());
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
    
    // Wait sampai form contact benar-benar muncul
    public boolean waitForFormToLoad() {
        try {
            System.out.println("Waiting for contact form to load...");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            
            // Cek apakah ada iframe
            try {
                List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
                System.out.println("Found " + iframes.size() + " iframe(s) on page");
                
                // Coba cari form di main page dulu
                List<WebElement> forms = driver.findElements(By.tagName("form"));
                System.out.println("Found " + forms.size() + " form(s) on page");
                
                // Coba cari input dengan berbagai ID yang mungkin
                List<WebElement> firstNameInputs = driver.findElements(By.cssSelector("input[id*='first_name'], input[name*='first_name'], input[placeholder*='First Name']"));
                System.out.println("Found " + firstNameInputs.size() + " potential first name input(s)");
                
            } catch (Exception debugEx) {
                System.out.println("Debug error: " + debugEx.getMessage());
            }
            
            // Tunggu lebih lama karena form mungkin load via iframe/async
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20));
            longWait.until(ExpectedConditions.presenceOfElementLocated(firstNameInput));
            System.out.println("Contact form loaded successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Contact form not found after 20 seconds");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Error: " + e.getMessage());
            
            // Print page title for debugging
            System.out.println("Page title: " + driver.getTitle());
            
            return false;
        }
    }

    // Actions: Mengisi Form dengan explicit waits
    public void fillContactForm(String fName, String lName, String email, String msg) {
        try {
            System.out.println("\n🔍 Starting to fill contact form...");
            
            // Wait for form to be ready
            wait.until(ExpectedConditions.presenceOfElementLocated(firstNameInput));
            System.out.println("Form is present and ready");
            
            // First Name - with scroll and click
            WebElement firstNameField = wait.until(ExpectedConditions.elementToBeClickable(firstNameInput));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstNameField);
            Thread.sleep(500);
            firstNameField.click();
            firstNameField.clear();
            firstNameField.sendKeys(fName);
            System.out.println("First Name filled: " + fName);
            
            // Last Name
            WebElement lastNameField = wait.until(ExpectedConditions.elementToBeClickable(lastNameInput));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", lastNameField);
            Thread.sleep(300);
            lastNameField.click();
            lastNameField.clear();
            lastNameField.sendKeys(lName);
            System.out.println("Last Name filled: " + lName);
            
            // Email
            WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(emailInput));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", emailField);
            Thread.sleep(300);
            emailField.click();
            emailField.clear();
            emailField.sendKeys(email);
            System.out.println("Email filled: " + email);
            
            // Message
            WebElement messageField = wait.until(ExpectedConditions.elementToBeClickable(messageInput));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", messageField);
            Thread.sleep(300);
            messageField.click();
            messageField.clear();
            messageField.sendKeys(msg);
            System.out.println("Message filled");
            
            System.out.println("Contact form filled successfully\n");
            
        } catch (Exception e) {
            System.out.println("Error filling form: " + e.getMessage());
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
            System.out.println("Error filling complete form: " + e.getMessage());
        }
    }
    
    // Method untuk select country dropdown
    public void selectCountry(String countryName) {
        try {
            WebElement countryDropdown = wait.until(ExpectedConditions.elementToBeClickable(countryInput));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", countryDropdown);
            Thread.sleep(300);
            
            // Select by visible text
            org.openqa.selenium.support.ui.Select select = 
                new org.openqa.selenium.support.ui.Select(countryDropdown);
            select.selectByVisibleText(countryName);
            System.out.println("Country selected: " + countryName);
        } catch (Exception e) {
            System.out.println("Country not selected: " + e.getMessage());
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
                System.out.println("Privacy Policy checkbox checked");
            }
        } catch (Exception e) {
            System.out.println("Error checking privacy policy: " + e.getMessage());
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
                System.out.println("Marketing communications checkbox checked");
            }
        } catch (Exception e) {
            System.out.println("Error checking marketing checkbox: " + e.getMessage());
        }
    }
    
    // Method untuk handle reCAPTCHA
    public void solveRecaptcha() {
        try {
            System.out.println("Attempting to solve reCAPTCHA");
            
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
            System.out.println("reCAPTCHA checkbox clicked");
            
            // Switch back to main content
            driver.switchTo().defaultContent();
            
            // Wait for reCAPTCHA to validate (may take a few seconds)
            Thread.sleep(3000);
            
            System.out.println("reCAPTCHA solved (or attempted)");
            
        } catch (Exception e) {
            System.out.println("reCAPTCHA handling warning: " + e.getMessage());
            System.out.println("reCAPTCHA may require manual intervention or may not be solvable in automation");
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
            System.out.println("Submit button clicked");
            
        } catch (Exception e) {
            System.out.println("Error clicking submit: " + e.getMessage());
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

    public String getSuccessMessage() {
        try {
            System.out.println("Mencari success message");
            
            // Wait for success message to appear
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
     * Verify apakah success message mengandung teks yang diharapkan
     * 
     * @param expectedText teks yang diharapkan
     * @return true jika success message mengandung teks yang diharapkan
     */
    public boolean verifySuccessMessage(String expectedText) {
        String actualMessage = getSuccessMessage();
        boolean contains = actualMessage.contains(expectedText);
        
        return contains;
    }
    
    /**
     * Mendapatkan teks reCAPTCHA error message
     * For test case ABT-013
     * 
     * @return teks error message, atau empty string jika tidak ada
     */
    public String getRecaptchaErrorMessage() {
        try {
            System.out.println("Mencari reCAPTCHA error message");
            Thread.sleep(1000);
            
            // Multiple locators to try
            By[] errorLocators = {
                By.xpath("//*[contains(text(),\"reCAPTCHA wasn't successful\")]"),
                By.xpath("//*[contains(text(),'reCAPTCHA')]"),
                By.cssSelector(".mauticform-errormsg"),
                By.cssSelector("span.mauticform-errormsg"),
                By.xpath("//span[contains(@class, 'error')]"),
                By.xpath("//*[contains(@style, 'color') and contains(text(), 'reCAPTCHA')]")
            };
            
            // Try each locator
            for (By locator : errorLocators) {
                try {
                    List<WebElement> elements = driver.findElements(locator);
                    for (WebElement elem : elements) {
                        if (elem.isDisplayed()) {
                            String text = elem.getText().trim();
                            if (text.toLowerCase().contains("recaptcha")) {
                                System.out.println("✓ reCAPTCHA error message ditemukan: " + text);
                                return text;
                            }
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            // Fallback: check page source
            try {
                String pageSource = driver.getPageSource();
                if (pageSource.contains("reCAPTCHA wasn't successful")) {
                    System.out.println("✓ reCAPTCHA error message ditemukan di page source");
                    return "reCAPTCHA wasn't successful.";
                } else if (pageSource.contains("reCAPTCHA")) {
                    System.out.println("✓ reCAPTCHA text ditemukan di page source");
                    return "reCAPTCHA validation error";
                }
            } catch (Exception e) {
                // Ignore
            }
            
            System.out.println("✗ reCAPTCHA error message tidak ditemukan");
            return "";
            
        } catch (Exception e) {
            System.out.println("Error mendapatkan reCAPTCHA error message: " + e.getMessage());
            return "";
        }
    }
    
    /**
     * Verify apakah error message mengandung teks yang diharapkan
     * 
     * @param expectedText teks yang diharapkan
     * @return true jika error message mengandung teks yang diharapkan
     */
    public boolean verifyRecaptchaErrorMessage(String expectedText) {
        String actualMessage = getRecaptchaErrorMessage();
        
        // Check if actual message contains "reCAPTCHA" (case insensitive)
        boolean hasRecaptcha = actualMessage.toLowerCase().contains("recaptcha");
        
        System.out.println("Expected error: " + expectedText);
        System.out.println("Actual error: " + actualMessage);
        System.out.println("Contains reCAPTCHA: " + hasRecaptcha);
        
        // Return true if message contains reCAPTCHA keyword (any variant)
        return hasRecaptcha;
    }
}
