package com.proyek_softes.demo.tests;

import java.util.Map;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.NavigationModule;

public class NavigationTest extends BaseTest {
    private NavigationModule navigationModule;

    @Test
    public void testSalesNavigation() {
        testParentTabNavigation("sales");
    }

    @Test
    public void testMarketingNavigation() {
        testParentTabNavigation("marketing");
    }

    @Test
    public void testSupportNavigation() {
        testParentTabNavigation("support");
    }

    @Test
    public void testActivitiesNavigation() {
        testParentTabNavigation("activities");
    }

    @Test
    public void testCollaborationNavigation() {
        testParentTabNavigation("collaboration");
    }

    @Test
    public void testAllTabsNavigation() {
        testParentTabNavigation("alltabs");
    }

    private void testParentTabNavigation(String parentTab) {
        System.out.println("\n========================================");
        System.out.println("Testing: " + parentTab.toUpperCase() + " Navigation");
        System.out.println("========================================");
        
        if (navigationModule == null) {
            login("will", "will");
            navigationModule = new NavigationModule(driver);
        }

        Map<String, By> subMenus = navigationModule.getSubMenuMap().get(parentTab);
        
        if (subMenus == null) {
            System.out.println("⚠ Warning: No sub-menus found for " + parentTab);
            return;
        }

        int successCount = 0;
        int failCount = 0;
        
        for (String subMenuItem : subMenus.keySet()) {         
            
            try {
                System.out.println("\n➤ Navigating to: " + parentTab + " -> " + subMenuItem);
                String currentUrl = driver.getCurrentUrl();
                
                navigationModule.navigateTo(parentTab, subMenuItem);
                
                // Wait for page to load
                Thread.sleep(2000);
                
                // Verify URL changed (indicating navigation occurred)
                String newUrl = driver.getCurrentUrl();
                Assert.assertNotEquals(currentUrl, newUrl, 
                    "URL should change after navigation to " + parentTab + " -> " + subMenuItem);
                
                // Verify URL contains expected module parameter
                String expectedModule = getExpectedModuleName(subMenuItem);
                if (expectedModule != null) {
                    Assert.assertTrue(newUrl.contains("module=" + expectedModule) || 
                                    newUrl.contains(expectedModule.toLowerCase()),
                        "URL should contain module: " + expectedModule);
                }
                
                System.out.println("✅ Successfully navigated to: " + parentTab + " -> " + subMenuItem);
                System.out.println("   URL: " + newUrl);
                successCount++;
                
                // Add gap between navigations
                Thread.sleep(1500);
                
            } catch (Exception e) {
                System.out.println("❌ Failed to navigate to: " + parentTab + " -> " + subMenuItem);
                System.out.println("   Error: " + e.getMessage());
                failCount++;
            }
        }
        
        System.out.println("\n--- " + parentTab.toUpperCase() + " Summary ---");
        System.out.println("✅ Successful: " + successCount);
        System.out.println("❌ Failed: " + failCount);
        System.out.println("⏭ Skipped: " + (subMenus.size() - successCount - failCount));
        
        // Assert that at least 70% of non-skipped navigations were successful
        if (successCount + failCount > 0) {
            double successRate = (double) successCount / (successCount + failCount);
            Assert.assertTrue(successRate >= 0.7, 
                parentTab + " navigation success rate should be at least 70%, but was: " + 
                String.format("%.1f", successRate * 100) + "%");
        }
    }
    
    private String getExpectedModuleName(String subMenuItem) {
        // Map sub-menu item names to expected module names
        switch (subMenuItem.toLowerCase()) {
            case "accounts": return "Accounts";
            case "contacts": return "Contacts";
            case "opportunities": return "Opportunities";
            case "leads": return "Leads";
            case "campaigns": return "Campaigns";
            case "targets": return "Prospects";
            case "targetslists": return "ProspectLists";
            case "cases": return "Cases";
            case "calendar": return "Calendar";
            case "calls": return "Calls";
            case "meetings": return "Meetings";
            case "emails": return "Emails";
            case "tasks": return "Tasks";
            case "notes": return "Notes";
            case "documents": return "Documents";
            case "projects": return "Project";
            case "quotes": return "AOS_Quotes";
            case "home": return "Home";
            default: return null;
        }
    }
}
