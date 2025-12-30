package com.proyek_softes.demo.tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.contacts.ContactsPage;

public class ContactTest extends BaseTest {
    @Test
    public void testNavigateToCreateAccount() {
        System.out.println("\n--- Testing: Navigate to Create Account Page ---");
        login("will", "will");
        ContactsPage contactsPage = new ContactsPage(driver);
        contactsPage.navigateToContactsModule();
        contactsPage.navigateToCreateContact();
        boolean isOnCreatePage = contactsPage.checkPageTitle("CREATE");
        assertTrue(isOnCreatePage, "Should be on Create Account page with SAVE button and name field visible");
        System.out.println("✅ Navigation to Create Account page verified successfully!");
    }

    @Test
    public void testNavigateToCreateContactFromVcard() {
        System.out.println("\n--- Testing: Navigate to Create Contact from Vcard Page ---");
        login("will", "will");
        ContactsPage contactsPage = new ContactsPage(driver);
        contactsPage.navigateToContactsModule();
        contactsPage.navigateToCreateContactFromVcard();
        boolean isOnCreateFromVcardPage = contactsPage.checkImportVcardPageTitle("IMPORT VCARD");
        assertTrue(isOnCreateFromVcardPage,
                "Should be on Create Contact from Vcard page with file upload field visible");
        System.out.println("✅ Navigation to Create Contact from Vcard page verified successfully!");
    }

    @Test
    public void testNavigateToImportContact() {
        System.out.println("\n--- Testing: Navigate to Import Contact Page ---");
        login("will", "will");
        ContactsPage contactsPage = new ContactsPage(driver);
        contactsPage.navigateToContactsModule();
        contactsPage.navigateToImportContact();
        boolean isOnImportPage = contactsPage.checkPageTitle("Step 1: Upload Import File");
        assertTrue(isOnImportPage, "Should be on Import Contact page with file upload field visible");
        System.out.println("✅ Navigation to Import Contact page verified successfully!");
    }

    @Test
    public void testNavigateToViewContacts() {
        System.out.println("\n--- Testing: Navigate to View Contacts Page ---");
        login("will", "will");
        ContactsPage contactsPage = new ContactsPage(driver);
        contactsPage.navigateToContactsModule();
        contactsPage.navigateToViewContact();
        boolean isOnViewPage = contactsPage.checkPageTitle("CONTACTS");
        assertTrue(isOnViewPage, "Should be on View Contacts page with contacts list visible");
        System.out.println("✅ Navigation to View Contacts page verified successfully!");
    }

}
