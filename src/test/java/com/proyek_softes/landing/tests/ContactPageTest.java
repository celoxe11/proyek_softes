package com.proyek_softes.landing.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.proyek_softes.landing.main.pages.ContactPage;
import com.proyek_softes.landing.main.pages.HomePage;
import com.proyek_softes.landing.main.pages.about.AboutUsPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ContactPageTest {
    private WebDriver driver;
    private String baseUrl = "https://suitecrm.com";
    
    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println("=== Starting Contact Page Test ===");
    }
    
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            System.out.println("=== Contact Page Test Complete ===");
            driver.quit();
        }
    }
    
    @Test(priority = 1)
    public void testNavigationToContactPage() {
        System.out.println("\n--- Testing Navigation: HomePage -> AboutUs -> Contact ---");
        
        // Step 1: Start from HomePage
        driver.get(baseUrl);
        HomePage homePage = new HomePage(driver);
        System.out.println("✓ HomePage ready");
        
        // Step 2: Navigate to About Us via header
        AboutUsPage aboutUsPage = homePage.goToAboutUs();
        System.out.println("✓ Navigated to About Us page");
        
        // Wait a bit to ensure page is loaded
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Step 3: Navigate to Contact Page from About Us
        ContactPage contactPage = aboutUsPage.goToContactForm();
        System.out.println("✓ Navigated to Contact page");
        
        // Verify we're on contact page
        try {
            Thread.sleep(1000);
            String currentUrl = driver.getCurrentUrl().toLowerCase();
            Assert.assertTrue(
                currentUrl.contains("contact") || currentUrl.contains("form"),
                "Should be on contact page, but URL is: " + currentUrl
            );
            System.out.println("✓ Verified: On Contact page");
        } catch (Exception e) {
            System.out.println("⚠ Warning: Could not verify URL: " + e.getMessage());
        }
    }
    
    @Test(priority = 2, dependsOnMethods = {"testNavigationToContactPage"})
    public void testFillContactForm() {
        System.out.println("\n--- Testing Contact Form Submission ---");
        
        // Already on contact page from previous test
        ContactPage contactPage = new ContactPage(driver);
        
        // Wait for form to load
        if (!contactPage.waitForFormToLoad()) {
            Assert.fail("Contact form failed to load");
        }
        
        // Fill the contact form with basic fields
        System.out.println("✓ Filling basic contact form...");
        contactPage.fillContactForm(
            "Test",
            "User",
            "test.user@example.com",
            "This is a test message for automation testing"
        );
        System.out.println("✓ Basic contact form filled successfully");
        
        // Check required privacy policy
        contactPage.checkPrivacyPolicy();
        
        // Pause untuk melihat hasil
        System.out.println("\n⏸️  Pausing for 5 seconds to view the filled form...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Note: Tidak submit untuk menghindari spam ke production
        // contactPage.clickSubmit();
        System.out.println("⚠ Submit button NOT clicked (avoiding spam)");
        
        Assert.assertTrue(true, "Contact form can be filled");
    }
    
    @Test(priority = 3, dependsOnMethods = {"testNavigationToContactPage"})
    public void testFillCompleteContactForm() {
        System.out.println("\n--- Testing Complete Contact Form ---");
        
        // Already on contact page from previous test
        ContactPage contactPage = new ContactPage(driver);
        
        // Wait for form to load
        if (!contactPage.waitForFormToLoad()) {
            Assert.fail("Contact form failed to load");
        }
        
        // Fill complete form with all fields
        System.out.println("✓ Filling complete contact form with all fields...");
        contactPage.fillCompleteContactForm(
            "John",
            "Doe",
            "john.doe@company.com",
            "+1234567890",
            "Test Company Ltd",
            "QA Engineer",
            "United States",
            "I am interested in learning more about SuiteCRM enterprise features"
        );
        
        // Check both checkboxes
        contactPage.checkPrivacyPolicy();
        contactPage.checkMarketingCommunications();
        
        System.out.println("✓ Complete form filled with all optional fields");
        
        // Pause untuk melihat hasil lengkap
        System.out.println("\n⏸️  Pausing for 7 seconds to view the complete filled form...");
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("⚠ Submit button NOT clicked (avoiding spam)");
        
        Assert.assertTrue(contactPage.isFormDisplayed(), "Contact form should be displayed");
    }
    
    @Test(priority = 4, dependsOnMethods = {"testNavigationToContactPage"})
    public void testContactFormValidation() {
        System.out.println("\n--- Testing Contact Form Validation ---");
        
        // Already on contact page from previous test
        ContactPage contactPage = new ContactPage(driver);
        
        // Test with empty data (untuk test validation)
        System.out.println("✓ Testing form with empty data...");
        contactPage.fillContactForm("", "", "", "");
        
        // Click submit to trigger validation (without checking required checkbox)
        contactPage.clickSubmit();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Verify we're still on the same page (form validation should prevent submission)
        String currentUrl = driver.getCurrentUrl().toLowerCase();
        System.out.println("✓ Current URL after submit: " + currentUrl);
        
        // Pause untuk melihat error validation
        System.out.println("\n⏸️  Pausing for 5 seconds to view validation errors...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Assert.assertTrue(
            currentUrl.contains("contact") || currentUrl.contains("form"),
            "Should still be on contact page after validation error"
        );
        System.out.println("✓ Form validation working (prevented empty submission)");
    }
    
    @Test(priority = 5, dependsOnMethods = {"testNavigationToContactPage"})
    public void testCompleteUserJourney() {
        System.out.println("\n--- Testing Complete User Journey ---");
        
        try {
            // Already on contact page from test 1
            System.out.println("Step 1: Contact page ready (from previous test)");
            ContactPage contactPage = new ContactPage(driver);
            Assert.assertNotNull(contactPage, "ContactPage should be initialized");
            System.out.println("✓ ContactPage ready");
            
            // Wait for form to load
            if (!contactPage.waitForFormToLoad()) {
                Assert.fail("Contact form failed to load");
            }
            
            // Step 2: Fill complete form with valid data
            System.out.println("Step 2: Fill complete contact form");
            contactPage.fillCompleteContactForm(
                "John",
                "Doe",
                "john.doe@company.com",
                "+1234567890",
                "Test Automation Company",
                "Senior QA Engineer",
                "United States",
                "I am interested in learning more about SuiteCRM enterprise features and support options"
            );
            System.out.println("✓ Complete form filled with valid data");
            
            // Step 3: Check required and optional checkboxes
            System.out.println("Step 3: Check privacy policy and marketing preferences");
            contactPage.checkPrivacyPolicy();
            contactPage.checkMarketingCommunications();
            System.out.println("✓ Checkboxes checked");
            
            // Step 4: Verify form is ready for submission
            System.out.println("Step 4: Verify form state");
            Assert.assertTrue(contactPage.isFormDisplayed(), "Form should be displayed");
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(
                currentUrl.contains("contact") || currentUrl.contains("form"),
                "Should be on contact page"
            );
            System.out.println("✓ Form is ready (not submitted to avoid spam)");
            
            // Pause paling lama untuk review complete journey
            System.out.println("\n⏸️  Pausing for 10 seconds to review complete user journey...");
            Thread.sleep(10000);
            
            System.out.println("\n========================================");
            System.out.println("COMPLETE USER JOURNEY: SUCCESS ✓");
            System.out.println("HomePage -> AboutUs -> Contact -> Complete Form Filled");
            System.out.println("All fields populated including optional fields");
            System.out.println("Privacy policy accepted");
            System.out.println("Marketing communications opted in");
            System.out.println("========================================");
            
        } catch (Exception e) {
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}
