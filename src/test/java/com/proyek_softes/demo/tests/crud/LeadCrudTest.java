package com.proyek_softes.demo.tests.crud;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.proyek_softes.demo.pages.WelcomePage;
import com.proyek_softes.demo.tests.BaseTest;
import com.proyek_softes.demo.pages.LeadsPage;

public class LeadCrudTest extends BaseTest {
    
    @Test
    public void testCreateLead() {
        System.out.println("\nTesting: Create Lead");
        WelcomePage welcomePage = new WelcomePage(driver);
        
        // Verify Dashboard
        String dashboardTitle = welcomePage.getWelcomeTitle();
        Assert.assertEquals(dashboardTitle, "Welcome to the SuiteCRM 7 Demo");
        
        // Step 2: Navigate to Leads
        LeadsPage leadsPage = welcomePage.navigateToLeads();
        
        // Step 3: Create Lead
        leadsPage.createLead("Jane", "Wilson", "ABC Corporation", "jane.wilson@abc.com");
        Assert.assertTrue(leadsPage.isLeadCreated(), "Lead should be created successfully");
        
        // Step 4: Update Lead
        leadsPage.openFirstLead();
        leadsPage.updateLead("XYZ Corporation");
        Assert.assertTrue(leadsPage.isLeadCreated());
        
        // Step 5: Delete Lead
        leadsPage.openFirstLead();
        leadsPage.deleteLead();
        System.out.println("Lead CRUD operations completed successfully!");
    }
}
