package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.contacts.ContactsPage;
import com.proyek_softes.demo.pages.contacts.CreateContactPage;
import com.proyek_softes.demo.pages.contacts.ImportContactVCardPage;
import com.proyek_softes.demo.utils.ContactDataProvider;

import io.qameta.allure.Description;

public class ContactTest extends GenericCrudTestHelper<ContactsPage, CreateContactPage> {

    @Test(dataProvider = "createContactData", dataProviderClass = ContactDataProvider.class)
    @Description("DEM-009")
    public void testDem009(Map<String, String> testData) {
        ContactsPage contactsPage = new ContactsPage(driver);
        CreateContactPage createContactPage = new CreateContactPage(driver, wait);
        
        testCreateEntity(
            testData,
            v -> contactsPage,
            contactsPage::navigateToContactsModule,
            contactsPage::navigateToCreateContact,
            v -> createContactPage,
            (page, data) -> page.addInformationFromData(data),
            createContactPage::save,
            data -> data.get("salutation") + " " + data.get("firstName") + " " + data.get("lastName"),
            createContactPage::isContactSavedSuccessfully,
            "DEM-009",
            contactsPage::navigateToViewContact,
            contactsPage::isInFirstRow,
            v -> contactsPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewContactData", dataProviderClass = ContactDataProvider.class)
    @Description("DEM-010")
    public void testDem010(Map<String, String> testData) {
        ContactsPage contactsPage = new ContactsPage(driver);
        
        testViewEntity(
            testData,
            v -> contactsPage,
            contactsPage::navigateToContactsModule,
            contactsPage::navigateToViewContact,
            contactsPage::clickFirstContact,
            data -> data.get("salutation") + " " + data.get("firstName") + " " + data.get("lastName"),
            contactsPage::isContactTitleCorrect,
            "DEM-010"
        );
    }

    @Test(dataProvider = "editContactData", dataProviderClass = ContactDataProvider.class)
    @Description("DEM-011")
    public void testDem011(Map<String, String> testData) {
        ContactsPage contactsPage = new ContactsPage(driver);
        CreateContactPage editContactPage = new CreateContactPage(driver, wait);
        
        testEditEntity(
            testData,
            v -> contactsPage,
            contactsPage::navigateToContactsModule,
            contactsPage::navigateToViewContact,
            contactsPage::clickFirstContact,
            data -> data.get("salutationBeforeEdit") + " " + data.get("firstNameBeforeEdit") + " " + data.get("lastNameBeforeEdit"),
            contactsPage::isContactTitleCorrect,
            "DEM-011_View_Contact_Detail",
            contactsPage::editContact,
            v -> editContactPage,
            (page, data) -> page.addInformationFromData(data),
            editContactPage::save,
            data -> data.get("salutation") + " " + data.get("firstName") + " " + data.get("lastName"),
            editContactPage::isContactSavedSuccessfully,
            "DEM-011"
        );
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
