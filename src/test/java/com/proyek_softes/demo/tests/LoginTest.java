package com.proyek_softes.demo.tests;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.LoginPage;
import com.proyek_softes.demo.pages.WelcomePage;

import io.qameta.allure.Description;

public class LoginTest extends BaseTest {

    @Test
    @Description("DEM-001")
    public void testDem001() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToDemo7();

        wait.until(
                ExpectedConditions.urlContains("https://demo.suiteondemand.com/index.php?action=Login&module=Users"));

        login("will", "will");

        assertTrue(driver.getCurrentUrl().contains("https://demo.suiteondemand.com/index.php?module=Home&action=Demo"),
                "Should be on dashboard page after login");

        // take screenshot
        takeScreenshot("DEM-001_Login_Page");
    }

    @Test
    @Description("DEM-002")
    public void testDem002() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToDemo8();

        wait.until(
                ExpectedConditions
                        .urlContains("https://suite8demo.suiteondemand.com/index.php?action=Login&module=Users"));

        login("will", "will");

        WelcomePage welcomePage = new WelcomePage(driver);
        welcomePage.clickTakeATour();

        welcomePage.clickNextInTour();
        welcomePage.clickNextInTour();
        welcomePage.clickNextInTour();
        welcomePage.clickNextInTour();
        boolean isOnStep5 = welcomePage.isOnStep5();
        assertTrue(isOnStep5, "Should be on Step 5 of the tour after clicking Next four times");

        // take screenshot of title
        WebElement titleElement = driver.findElement(By.className("swal2-title"));
        takeElementScreenshot("DEM-001_RedefineCustomisation", titleElement);
    }

    @Test
    @Description("DEM-003")
    public void testDem003() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToDemo7();

        wait.until(
                ExpectedConditions.urlContains("https://demo.suiteondemand.com/index.php?action=Login&module=Users"));

        login("will", "will");

        WelcomePage welcomePage = new WelcomePage(driver);
        welcomePage.clickLearnMore();
        boolean isInSuiteAssuredPage = welcomePage.isInSuiteAssuredPage();
        assertTrue(isInSuiteAssuredPage, "Should be in Suite Assured page after clicking Learn More");

        // take screenshot of current URL page
        takeScreenshot("DEM-003_SuiteAssuredPage");
    }

    @Test
    @Description("DEM-104")
    public void testDem104() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToDemo8();

        wait.until(
                ExpectedConditions.urlContains("https://suite8demo.suiteondemand.com/#/Login"));

        loginPage.login8("will", "will");

        assertTrue(driver.getCurrentUrl().contains("https://suite8demo.suiteondemand.com/#/home"),
                "Should be on dashboard page after login");

        // take screenshot
        takeScreenshot("DEM-104_Login_Page");
    }
}
