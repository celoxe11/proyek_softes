package com.proyek_softes.demo.tests.crud;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.proyek_softes.demo.pages.WelcomePage;
import com.proyek_softes.demo.tests.BaseTest;
import com.proyek_softes.demo.pages.ContactsPage;

public class ContactCrudTest extends BaseTest {
    
    @Test
    public void testCreateContact() {
        System.out.println("\nTesting: Create Contact");
        WelcomePage welcomePage = new WelcomePage(driver);
        
        // Verify Dashboard
        String dashboardTitle = welcomePage.getWelcomeTitle();
        Assert.assertEquals(dashboardTitle, "Welcome to the SuiteCRM 7 Demo");
        
        // Step 2: Navigate to Contacts
        ContactsPage contactsPage = welcomePage.navigateToContacts();
        
        // Step 3: Create Contact
        contactsPage.createContact("John", "Doe", "john.doe@test.com", "555-1234");
        Assert.assertTrue(contactsPage.isContactCreated(), "Contact should be created successfully");
        
        // Step 4: Update Contact
        contactsPage.openFirstContact();
        contactsPage.updateContact("Smith");
        Assert.assertTrue(contactsPage.isContactCreated());
        
        // Step 5: Delete Contact
        contactsPage.openFirstContact();
        contactsPage.deleteContact();
        System.out.println("Contact CRUD operations completed successfully!");
    }
}
