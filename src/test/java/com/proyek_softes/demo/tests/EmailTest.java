package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.emails.ComposeEmailPage;
import com.proyek_softes.demo.pages.emails.EmailsPage;
import com.proyek_softes.demo.utils.EmailDataProvider;

import io.qameta.allure.Description;

public class EmailTest extends GenericCrudTestHelper<EmailsPage, ComposeEmailPage> {

    @Test(dataProvider = "createEmailData", dataProviderClass = EmailDataProvider.class)
    @Description("DEM-040")
    public void testDem040(Map<String, String> testData) {
        try {
            login("will", "will");
            EmailsPage emailsPage = new EmailsPage(driver);
            emailsPage.navigateToEmailsModule();
            emailsPage.navigateToCreateEmail();

            ComposeEmailPage composeEmailPage = new ComposeEmailPage(driver, wait);
            composeEmailPage.addInformationFromData(testData);
            composeEmailPage.clickSendButton();

            Thread.sleep(1000); // Wait for potential error modal to appear

            // Check for error modal first
            if (composeEmailPage.isEmailSendErrorModalPresent()) {
                String errorMessage = composeEmailPage.getEmailSendErrorMessage();
                System.out.println("❌ Email send failed with error: " + errorMessage);
                takeScreenshot("DEM-040_FAILED_Email_Send_Error");
                throw new AssertionError("Email send failed: " + errorMessage);
            }

            boolean isEmailCreated = composeEmailPage.isEmailSentSuccessfully();
            assertTrue(isEmailCreated, "Email should be sent successfully");
            takeScreenshot("DEM-040_Email_Sent_Successfully");
        } catch (AssertionError e) {
            // Re-throw AssertionError without wrapping
            throw e;
        } catch (Exception e) {
            System.out.println("❌ Test failed with unexpected error: " + e.getMessage());
            e.printStackTrace();
            takeScreenshot("DEM-040_FAILED_Unexpected_Error");
            throw new AssertionError("Test failed with unexpected error - " + e.getMessage(), e);
        }
    }

    @Test
    @Description("DEM-041")
    public void testDem041() {
        login("will", "will");
        EmailsPage emailsPage = new EmailsPage(driver);
        emailsPage.navigateToEmailsModule();
        emailsPage.navigateToViewEmail();

        boolean isInEmailsPage = emailsPage.isInEmailsPage();
        assertTrue(isInEmailsPage, "Should be in Emails page");
        takeScreenshot("DEM-041_In_Emails_Page");
    }

    @Test
    @Description("DEM-042")
    public void testDem042() {
        // edit email test implementation
        /**
         * Tidak ada data yang disediakan di Emails dan tidak bisa bisa membuat
         * email baru. Maka dari itu, test ini tidak bisa diimplementasikan.
         */
    }

    @Test
    @Description("DEM-043")
    public void testDem043() {
        // delete email test implementation
        /**
         * Tidak ada data yang disediakan di Emails dan tidak bisa bisa membuat
         * email baru. Maka dari itu, test ini tidak bisa diimplementasikan.
         */
    }

}
