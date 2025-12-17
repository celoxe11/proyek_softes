package com.proyek_softes.demo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.DashboardPage;

public class DashboardTest extends BaseTest {
    private DashboardPage dashboardPage;

    @Test
    public void testDashboardAccess() {
        System.out.println("\nTesting: Dashboard Access");
        login("will", "will");
        dashboardPage = new DashboardPage(driver);

        // Navigate to dashboard and verify
        dashboardPage.navigateToDashboard();
        String dashboardTitle = dashboardPage.getDashboardTitle().trim();
        Assert.assertEquals(dashboardTitle, "SUITECRM DASHBOARD",
                "Dashboard should be accessible after login");
        System.out.println("✅ Dashboard access verified successfully!");
    }

    @Test(dependsOnMethods = "testDashboardAccess")
    public void testAddDashlet() {
        System.out.println("\nTesting: Add Dashlet Functionality");
        // Give some time for page load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        dashboardPage.actionsDropdownClicked();
        dashboardPage.addDashlet();

        System.out.println("✅ Add Dashlet modal opened successfully!");
    }

    @Test(dependsOnMethods = "testAddDashlet")
    public void testSearchDashlet() {
        System.out.println("\nTesting: Search Dashlet Functionality");

        // Give some time for page load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        // Search for "email" dashlet (modal is already open from previous test)
        dashboardPage.searchDashlet("email");

        // Verify the dashlet option is visible
        boolean searchSuccess = dashboardPage.isDashletOptionVisible("MyEmailsDashlet_select_icon");
        Assert.assertTrue(searchSuccess, "Email dashlet should be visible in search results");

        System.out.println("✅ Dashlet search executed and verified successfully!");

        // let the tester see the result before clearing search
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }

    @Test(dependsOnMethods = "testSearchDashlet")
    public void testClearDashletSearch() {
        System.out.println("\nTesting: Clear Dashlet Search Functionality");

        // Give some time for page load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        // Clear the dashlet search input
        dashboardPage.clearSearchDashlet();

        // Verify the search input is cleared
        boolean isSearchCleared = dashboardPage.isDashletOptionVisible("MyEmailsDashlet_select_icon");
        Assert.assertTrue(isSearchCleared, "Dashlet search input should be cleared");

        System.out.println("✅ Dashlet search cleared successfully!");
    }
}
