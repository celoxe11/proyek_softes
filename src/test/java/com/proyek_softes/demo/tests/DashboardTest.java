package com.proyek_softes.demo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.DashboardPage;

public class DashboardTest extends BaseTest {
    @Test
    public void testDashboardAccess() {
        System.out.println("\nTesting: Dashboard Access");
        login("will", "will");
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.navigateToDashboard();
        String dashboardTitle = dashboardPage.getDashboardTitle().trim();
        Assert.assertEquals(dashboardTitle, "SUITECRM DASHBOARD",
                "Dashboard should be accessible after login");
        System.out.println(" Dashboard access verified successfully!");
    }
}
