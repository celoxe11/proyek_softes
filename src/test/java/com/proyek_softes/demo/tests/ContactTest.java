package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.contacts.ContactsPage;
import com.proyek_softes.demo.pages.contacts.CreateContactPage;
import com.proyek_softes.demo.pages.contacts.ImportContactVCardPage;
import com.proyek_softes.demo.utils.ContactDataProvider;

import io.qameta.allure.Description;

public class ContactTest extends BaseTest {

    @Test(dataProvider = "createContactData", dataProviderClass = ContactDataProvider.class)
    @Description("DEM-009")
    public void testDem009(Map<String, String> testData) {
        login("will", "will");
        ContactsPage contactsPage = new ContactsPage(driver);
        contactsPage.navigateToContactsModule();
        contactsPage.navigateToCreateContact();

        CreateContactPage createContactPage = new CreateContactPage(driver, wait);
        createContactPage.addInformationFromData(testData);
        createContactPage.save();

        String fullName = testData.get("salutation") + " " + testData.get("firstName") + " " + testData.get("lastName");
        boolean isSaved = createContactPage.isContactSavedSuccessfully(fullName);
        assertTrue(isSaved, "Contact with minimal data should be saved successfully");

        takeScreenshot("DEM-009_Create_Contact");

        contactsPage.navigateToViewContact();
        boolean isInFirstRow = contactsPage.isInFirstRow(fullName);
        assertTrue(isInFirstRow, "Created contact should appear in the first row of contacts list");

        // take screenshot of the row containing the contact name
        takeElementScreenshot("DEM-009_Contact_In_List", contactsPage.getFirstRowLocator());
    }

    @Test(dataProvider = "viewContactData", dataProviderClass = ContactDataProvider.class)
    @Description("DEM-010")
    public void testDem010(Map<String, String> testData) {
        try {
            login("will", "will");
            ContactsPage contactsPage = new ContactsPage(driver);
            contactsPage.navigateToContactsModule();
            contactsPage.navigateToViewContact();

            contactsPage.clickFirstContact();

            Thread.sleep(2000);

            String fullName = testData.get("salutation") + " " + testData.get("firstName") + " " + testData.get("lastName");
            boolean isOnContactDetailPage = contactsPage.isContactTitleCorrect(fullName);
            assertTrue(isOnContactDetailPage, "Should be on Contact Detail page for the selected contact");
            takeScreenshot("DEM-010_View_Contact_Detail");
        } catch (InterruptedException e) {
        }
    }

    @Test(dataProvider = "editContactData", dataProviderClass = ContactDataProvider.class)
    @Description("DEM-011")
    public void testDem011(Map<String, String> testData) {
        try {
            login("will", "will");
            ContactsPage contactsPage = new ContactsPage(driver);
            contactsPage.navigateToContactsModule();
            contactsPage.navigateToViewContact();

            contactsPage.clickFirstContact();

            Thread.sleep(2000);

            String fullNameBeforeEdit = testData.get("salutationBeforeEdit") + " " + testData.get("firstNameBeforeEdit") + " " + testData.get("lastNameBeforeEdit");
            boolean isOnContactDetailPage = contactsPage.isContactTitleCorrect(fullNameBeforeEdit);
            assertTrue(isOnContactDetailPage, "Should be on Contact Detail page for the selected contact");
            takeScreenshot("DEM-011_View_Contact_Detail_Before_Edit");

            contactsPage.editContact();

            CreateContactPage editContactPage = new CreateContactPage(driver, wait);
            editContactPage.addInformationFromData(testData);
            editContactPage.save();

            String fullName = testData.get("salutation") + " " + testData.get("firstName") + " " + testData.get("lastName");
            boolean isSaved = editContactPage.isContactSavedSuccessfully(fullName);
            assertTrue(isSaved, "Contact should be saved successfully after editing");

            takeScreenshot("DEM-011_Edit_Contact");
        } catch (InterruptedException e) {
        }
    }

    @Test
    @Description("DEM-012")
    public void testDem012() {
        try {
            login("will", "will");
            ContactsPage contactsPage = new ContactsPage(driver);
            contactsPage.navigateToContactsModule();
            contactsPage.navigateToViewContact();

            contactsPage.checkAndClearFilter();

            // get the first row contact name before clicking
            String firstRowContactName = contactsPage.getFirstRowNameLocator().getText().trim();

            contactsPage.clickFirstContact();

            Thread.sleep(2000);

            contactsPage.deleteContact();
            contactsPage.clickOkInDeleteDialog();

            // wait until return to view contact
            Thread.sleep(2000);

            contactsPage.filterQuick(firstRowContactName, false, false);

            boolean isFilterResultEmpty = contactsPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted contact should no longer exist in the contacts list");
            takeElementScreenshot("DEM-012_Deleted_Contact_Filter_Result", driver.findElement(contactsPage.getFilterResult()));

            contactsPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }

    }

    @Test
    @Description("DEM-013")
    public void testDem013() {
        login("will", "will");
        ContactsPage contactsPage = new ContactsPage(driver);
        contactsPage.navigateToContactsModule();
        contactsPage.navigateToImportVCard();

        ImportContactVCardPage importContactVCardPage = new ImportContactVCardPage(driver);
        importContactVCardPage.uploadFile("Contacts_vCard.vcf");
        importContactVCardPage.clickImportButton();

        boolean isContactSavedSuccessfully = importContactVCardPage.isContactSavedSuccessfully("Bob Johnson");
        assertTrue(isContactSavedSuccessfully, "Contact should be saved successfully after importing");
        takeScreenshot("DEM-013_Import_Contact_VCard");

        contactsPage.navigateToViewContact();

        String importedContactName = "Bob Johnson";
        boolean isInFirstRow = contactsPage.isInFirstRow(importedContactName);
        assertTrue(isInFirstRow, "Imported contact should appear in the contacts list");

        takeElementScreenshot("DEM-013_Imported_Contact_In_List", contactsPage.getFirstRowLocator());

    }

    // @Test
    // public void testNavigateToCreateAccount() {
    //     System.out.println("\n--- Testing: Navigate to Create Account Page ---");
    //     login("will", "will");
    //     ContactsPage contactsPage = new ContactsPage(driver);
    //     contactsPage.navigateToContactsModule();
    //     contactsPage.navigateToCreateContact();
    //     boolean isOnCreatePage = contactsPage.checkPageTitle("CREATE");
    //     assertTrue(isOnCreatePage, "Should be on Create Account page with SAVE button and name field visible");
    //     System.out.println("✅ Navigation to Create Account page verified successfully!");
    // }
    // @Test
    // public void testNavigateToCreateContactFromVcard() {
    //     System.out.println("\n--- Testing: Navigate to Create Contact from Vcard Page ---");
    //     login("will", "will");
    //     ContactsPage contactsPage = new ContactsPage(driver);
    //     contactsPage.navigateToContactsModule();
    //     contactsPage.navigateToCreateContactFromVcard();
    //     boolean isOnCreateFromVcardPage = contactsPage.checkImportVcardPageTitle("IMPORT VCARD");
    //     assertTrue(isOnCreateFromVcardPage,
    //             "Should be on Create Contact from Vcard page with file upload field visible");
    //     System.out.println("✅ Navigation to Create Contact from Vcard page verified successfully!");
    // }
    // @Test
    // public void testNavigateToImportContact() {
    //     System.out.println("\n--- Testing: Navigate to Import Contact Page ---");
    //     login("will", "will");
    //     ContactsPage contactsPage = new ContactsPage(driver);
    //     contactsPage.navigateToContactsModule();
    //     contactsPage.navigateToImportContact();
    //     boolean isOnImportPage = contactsPage.checkPageTitle("Step 1: Upload Import File");
    //     assertTrue(isOnImportPage, "Should be on Import Contact page with file upload field visible");
    //     System.out.println("✅ Navigation to Import Contact page verified successfully!");
    // }
    // @Test
    // public void testNavigateToViewContacts() {
    //     System.out.println("\n--- Testing: Navigate to View Contacts Page ---");
    //     login("will", "will");
    //     ContactsPage contactsPage = new ContactsPage(driver);
    //     contactsPage.navigateToContactsModule();
    //     contactsPage.navigateToViewContact();
    //     boolean isOnViewPage = contactsPage.checkPageTitle("CONTACTS");
    //     assertTrue(isOnViewPage, "Should be on View Contacts page with contacts list visible");
    //     System.out.println("✅ Navigation to View Contacts page verified successfully!");
    // }
}
