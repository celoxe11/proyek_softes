package com.proyek_softes.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dashboardButton = By.cssSelector(".navbar-brand.with-home-icon");
    private final By dashboardPresentation = By.xpath("//a[@id='tab0']");
    private final By actionsDropdown = By.xpath("//li[@id='tab-actions']/a");
    private final By addDashletButton = By.className("addDashlets");
    private final By addDashletModal = By.className("modal-add-dashlet");
    private final By closeDashletModal = By
            .cssSelector("#pagecontent > div.modal.fade.modal-add-dashlet.in > div > div > div.modal-footer > button");
    private final By searchDashlet = By.xpath("//input[@id=\"search_string\"]");
    private final By searchDashletButton = By.cssSelector("#dashletSearch > table > tbody > tr > td > input:nth-child(2)");
    private final By clearDashletSearch = By.cssSelector("#dashletSearch > table > tbody > tr > td > input:nth-child(3)");
    private final By searchResultsContainer = By.id("searchResults");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    public void navigateToDashboard() {
        WebElement dashboardBtn = driver.findElement(dashboardButton);
        dashboardBtn.click();
    }

    public String getDashboardTitle() {
        return driver.findElement(dashboardPresentation).getText();
    }

    public void actionsDropdownClicked() {
        driver.findElement(actionsDropdown).click();
    }

    public void addDashlet() {
        driver.findElement(addDashletButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addDashletModal));
        wait.until(ExpectedConditions.elementToBeClickable(searchDashlet));
    }

    public void closeAddDashletModal() {
        driver.findElement(closeDashletModal).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(addDashletModal));
    }

    public void searchDashlet(String dashletName) {
        WebElement searchInput = driver.findElement(searchDashlet);
        searchInput.clear();
        searchInput.sendKeys(dashletName);
        driver.findElement(searchDashletButton).click();
        // Wait for search results to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultsContainer));
    }

    public boolean isDashletOptionVisible(String dashletId) {
        try {
            // Wait for search results container first
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultsContainer));
            // Add a small delay for results to render
            Thread.sleep(500);

            wait.until(ExpectedConditions.presenceOfElementLocated(By.id(dashletId)));

            // Also verify it's in the search results by getting the element
            WebElement dashlet = driver.findElement(By.id(dashletId));
            System.out.println("✅ Found dashlet in search results: " + dashletId);
            return true;
        } catch (Exception e) {
            System.out.println("❌ Dashlet not found: " + e.getMessage());
            return false;
        }
    }

    public void clearSearchDashlet() {
        driver.findElement(clearDashletSearch).click();
        wait.until(ExpectedConditions.textToBePresentInElementValue(searchDashlet, ""));
    }
}
