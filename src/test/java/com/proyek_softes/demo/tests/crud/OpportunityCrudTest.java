package com.proyek_softes.demo.tests.crud;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.proyek_softes.demo.pages.WelcomePage;
import com.proyek_softes.demo.tests.BaseTest;
import com.proyek_softes.demo.pages.OpportunitiesPage;

public class OpportunityCrudTest extends BaseTest {
    
    @Test
    public void testCreateOpportunity() {
        System.out.println("\nTesting: Create Opportunity");
        WelcomePage welcomePage = new WelcomePage(driver);
        
        // Verify Dashboard
        String dashboardTitle = welcomePage.getWelcomeTitle();
        Assert.assertEquals(dashboardTitle, "Welcome to the SuiteCRM 7 Demo");
        
        // Step 2: Navigate to Opportunities
        OpportunitiesPage opportunitiesPage = welcomePage.navigateToOpportunities();
        
        // Step 3: Create Opportunity
        opportunitiesPage.createOpportunity("Big Deal 2024", "100000", "Prospecting");
        Assert.assertTrue(opportunitiesPage.isOpportunityCreated(), "Opportunity should be created successfully");
        
        // Step 4: Update Opportunity
        opportunitiesPage.openFirstOpportunity();
        opportunitiesPage.updateOpportunity("Mega Deal 2024");
        Assert.assertTrue(opportunitiesPage.isOpportunityCreated());
        
        // Step 5: Delete Opportunity
        opportunitiesPage.openFirstOpportunity();
        opportunitiesPage.deleteOpportunity();
        System.out.println("Opportunity CRUD operations completed successfully!");
    }
}
