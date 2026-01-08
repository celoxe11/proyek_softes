package com.proyek_softes.demo.pages;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    private final By loadingScreen = By.id("overlay-spinner");
    private final By navbar = By.cssSelector("nav.navbar");

    private final By searchBar = By.name("search-bar-term");

    private final By firstTab = By.id("tab0");
    private final By tabActions = By.cssSelector("#tab-actions > a.dropdown-toggle");
    private final By addDashletsButton = By.xpath("//input[contains(@class, 'addDashlets') and @value='Add Dashlets']");
    private final By addTabButton = By.xpath("//input[contains(@class, 'addDashboard') and @value='Add Tab']");
    private final By editTabButton = By.xpath("//input[contains(@class, 'addDashboard') and @value='Edit Tabs']");
    private final By newTab = By.id("tab1");

    private final By myNotesDashlet = By.id("MyNotesDashlet_select_icon");
    private final By closeModalButton = By.cssSelector("button[data-dismiss='modal']");
    private final By myNotesDashletTitle = By.xpath("//div[@class='dashboard-title']//span[text()='My Notes']");
    private final By chartCategoryAnchor = By.id("chartCategoryAnchor");
    private final By opportunitiesByLeadSourceOutcomeLink = By.xpath("//a[contains(text(), 'All Opportunities By Lead Source By Outcome')]");
    private final By opportunityChartDashletTitle = By.xpath("//div[@class='dashboard-title']//span[text()='All Opportunities By Lead Source By Outcome']");

    // Add Dashboard Modal locators
    private final By addDashboardModal = By.cssSelector(".modal-add-dashboard");
    private final By dashNameInput = By.id("dashName");
    private final By columnsSelect = By.name("numColumns");
    private final By modalAddTabButton = By.cssSelector(".modal-add-dashboard .btn-add-dashboard");
    private final By modalCancelButton = By.cssSelector(".modal-add-dashboard .btn-default");
    
    // Edit Tabs Modal locators
    private final By editTabsModal = By.cssSelector(".modal-edit-dashboard");
    private final By deleteTabButtons = By.cssSelector(".modal-edit-dashboard .modal-body .panel .btn-danger");
    private final By editTabsCloseButton = By.cssSelector(".modal-edit-dashboard .modal-footer .btn-default");
    
    // Legacy iframe locator - SuiteCRM 8 embeds legacy content in an iframe
    private final By legacyIframe = By.cssSelector("iframe");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }
    
    /**
     * Switch to the legacy iframe that contains the dashboard content.
     * SuiteCRM 8 embeds the legacy (SuiteCRM 7) dashboard in an iframe.
     * This must be called before interacting with any dashboard elements.
     */
    public void switchToLegacyFrame() {
        // First, make sure we're in the default content
        driver.switchTo().defaultContent();
        
        // Wait for the iframe to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(legacyIframe));
        
        // Wait for the iframe to be available and switch to it
        WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(legacyIframe));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe));
        
        System.out.println("✓ Switched to legacy iframe");
    }
    
    /**
     * Switch back to the main content from the iframe
     */
    public void switchToMainContent() {
        driver.switchTo().defaultContent();
        System.out.println("✓ Switched back to main content");
    }

    public void waitForPageToLoad() {
        // First switch to the legacy iframe
        switchToLegacyFrame();
        
        // Wait for loading screen to disappear
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingScreen));
        } catch (Exception e) {
            // Loading screen might not be present, continue
        }
        
        // Wait for navbar to be fully loaded (inside the iframe)
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(navbar));
        } catch (Exception e) {
            // Navbar might have different selector, try dashboard
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dashboard")));
        }
        
        // Additional wait for page stability
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("✓ Dashboard page loaded inside iframe");
    }

    public void clickFirstTab() {
        wait.until(ExpectedConditions.elementToBeClickable(firstTab)).click();
    }

    public void clickAddDashlets() {
        WebElement tabActionsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(tabActions));
        js.executeScript("arguments[0].click();", tabActionsElement);
        wait.until(ExpectedConditions.elementToBeClickable(addDashletsButton)).click();
    }

    public void clickAddTab() {
        WebElement tabActionsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(tabActions));
        js.executeScript("arguments[0].click();", tabActionsElement);
        wait.until(ExpectedConditions.elementToBeClickable(addTabButton)).click();
    }

    public void clickEditTab() {
        WebElement tabActionsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(tabActions));
        js.executeScript("arguments[0].click();", tabActionsElement);
        wait.until(ExpectedConditions.elementToBeClickable(editTabButton)).click();
    }

    public void clickMyNotesDashlet() {
        wait.until(ExpectedConditions.elementToBeClickable(myNotesDashlet)).click();
    }

    public void closeModal() {
        wait.until(ExpectedConditions.elementToBeClickable(closeModalButton)).click();
        // Wait for modal to close
        wait.until(ExpectedConditions.invisibilityOfElementLocated(closeModalButton));
    }

    public boolean hasMyNotesDashlet() {
        try {
            // Wait for the dashlet to be present on the dashboard
            wait.until(ExpectedConditions.presenceOfElementLocated(myNotesDashletTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasOpportunityChart() {
        try {
            // Wait for the opportunity chart dashlet to be present on the dashboard
            wait.until(ExpectedConditions.presenceOfElementLocated(opportunityChartDashletTitle));
            System.out.println("✓ Opportunity chart dashlet found on dashboard");
            return true;
        } catch (Exception e) {
            System.out.println("✗ Opportunity chart dashlet not found");
            return false;
        }
    }

    public void fillAddDashboardForm(Map<String, String> testData) {
        // Wait for the modal to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(addDashboardModal));
        
        // Fill in dashboard name
        WebElement dashNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(dashNameInput));
        dashNameField.clear();
        dashNameField.sendKeys(testData.get("dashName"));
        
        // Select number of columns
        WebElement columnsDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(columnsSelect));
        Select select = new Select(columnsDropdown);
        select.selectByValue(testData.get("numColumns"));
        
        System.out.println("✓ Filled Add Dashboard form with: " + testData);
    }
    
    public void confirmAddDashboard() {
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(modalAddTabButton));
        addButton.click();
        
        // Wait for modal to close
        wait.until(ExpectedConditions.invisibilityOfElementLocated(addDashboardModal));
        System.out.println("✓ Dashboard tab added successfully");
    }

    public void cancelAddDashboard() {
        WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(modalCancelButton));
        cancelButton.click();
        
        // Wait for modal to close
        wait.until(ExpectedConditions.invisibilityOfElementLocated(addDashboardModal));
        System.out.println("✓ Add Dashboard cancelled");
    }
    
    public void waitAndClickNewTab() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(newTab)).click();
    }

    public void chooseChartAndSelectOpportunity() {
        // Click on the Charts category tab
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(chartCategoryAnchor)).click();
        
        // Wait for the chart list to load and click on the specific dashlet
        WebElement opportunityChartLink = wait.until(
            ExpectedConditions.elementToBeClickable(opportunitiesByLeadSourceOutcomeLink)
        );
        opportunityChartLink.click();
        
        System.out.println("✓ Selected 'All Opportunities By Lead Source By Outcome' chart");
    }
    

    public void slow() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void removeLastDashboardTab() {
        // Wait for the Edit Tabs modal to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(editTabsModal));
        
        // Find all delete buttons in the modal
        java.util.List<WebElement> deleteButtons = driver.findElements(deleteTabButtons);
        
        if (deleteButtons.isEmpty()) {
            System.out.println("✗ No deletable tabs found");
            return;
        }
        
        // Click the last delete button
        WebElement lastDeleteButton = deleteButtons.get(deleteButtons.size() - 1);
        js.executeScript("arguments[0].scrollIntoView(true);", lastDeleteButton);
        wait.until(ExpectedConditions.elementToBeClickable(lastDeleteButton)).click();
        
        System.out.println("✓ Clicked delete button on last dashboard tab");
    }

    public void closeEditModal() {
        // Wait for the modal to be visible first
        wait.until(ExpectedConditions.visibilityOfElementLocated(editTabsModal));
        
        // Find and click the close button
        WebElement closeBtn = wait.until(ExpectedConditions.presenceOfElementLocated(editTabsCloseButton));
        js.executeScript("arguments[0].scrollIntoView(true);", closeBtn);
        js.executeScript("arguments[0].click();", closeBtn);
        
        // Wait for modal to disappear
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("✓ Closed Edit Tabs modal");
    }
    
    public boolean hasOnlyDefaultAndTestDashboard() {
        try {
            // Get all visible tab links (excluding the ACTIONS dropdown and mobile menu items)
            java.util.List<WebElement> tabs = driver.findElements(
                By.cssSelector("ul.nav-dashboard > li[role='presentation'] > a.hidden-xs")
            );
            
            // Should have exactly 2 tabs
            if (tabs.size() != 2) {
                System.out.println("✗ Expected 2 tabs, found: " + tabs.size());
                return false;
            }
            
            // Check tab names
            String tab0Text = tabs.get(0).getText().trim();
            String tab1Text = tabs.get(1).getText().trim();
            
            boolean hasSuiteCRMDashboard = tab0Text.toLowerCase().contains("suitecrm dashboard");
            boolean hasTestDashboard = tab1Text.toLowerCase().contains("test dashboard");
            
            if (hasSuiteCRMDashboard && hasTestDashboard) {
                System.out.println("✓ Dashboard has only 'SUITECRM DASHBOARD' and 'Test Dashboard' tabs");
                return true;
            } else {
                System.out.println("✗ Tab names don't match. Found: '" + tab0Text + "' and '" + tab1Text + "'");
                return false;
            }
        } catch (Exception e) {
            System.out.println("✗ Error checking tabs: " + e.getMessage());
            return false;
        }
    }

    public void searchDashboard(String searchTerm) {
        // Switch back to main content (search bar is in the main frame, not iframe)
        switchToMainContent();
        
        // Find and interact with the search bar
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar));
        searchInput.clear();
        searchInput.sendKeys(searchTerm);
        searchInput.sendKeys(Keys.ENTER);
        
        System.out.println("✓ Searched for: " + searchTerm);
        
        // Wait for page to load after search
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        // Wait for URL to change to include Search action
        try {
            longWait.until(ExpectedConditions.urlContains("Search"));
            System.out.println("✓ Navigated to search results page");
        } catch (Exception e) {
            System.out.println("URL did not change to Search, continuing...");
        }
        
        // Try to find results - first check if they're in an iframe
        try {
            switchToLegacyFrame();
            longWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".unified-search-table-wrapper")));
            System.out.println("✓ Search results loaded (in iframe)");
        } catch (Exception e) {
            // Results might be in main content, switch back
            switchToMainContent();
            try {
                longWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".unified-search-table-wrapper")));
                System.out.println("✓ Search results loaded (in main content)");
            } catch (Exception e2) {
                System.out.println("Warning: Could not find search results wrapper");
            }
        }
    }
    
    public boolean hasExpectedSearchResults() {
        try {
            // Expected names to find in search results
            String[] expectedNames = {"Willy Heiner", "Valerie Will", "Williams Hixson"};
            
            // Get all result links/spans from the search results tables
            java.util.List<WebElement> resultLinks = driver.findElements(
                By.cssSelector(".unified-search-table-wrapper table.list tbody tr td a span")
            );
            
            // Collect all text from result elements
            java.util.List<String> foundNames = new java.util.ArrayList<>();
            for (WebElement link : resultLinks) {
                String text = link.getText().trim();
                if (!text.isEmpty()) {
                    foundNames.add(text);
                }
            }
            
            System.out.println("Found names in results: " + foundNames);
            
            // Check if all expected names are present
            boolean allFound = true;
            for (String expectedName : expectedNames) {
                boolean found = false;
                for (String foundName : foundNames) {
                    if (foundName.contains(expectedName) || expectedName.contains(foundName)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("✗ Expected name not found: " + expectedName);
                    allFound = false;
                } else {
                    System.out.println("✓ Found: " + expectedName);
                }
            }
            
            return allFound;
        } catch (Exception e) {
            System.out.println("✗ Error checking search results: " + e.getMessage());
            return false;
        }
    }
}
