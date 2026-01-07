package com.proyek_softes.demo.pages.knowledge_base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class KnowledgeBasesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Navbar for navigation to reports
    private final By navTab = By.id("grouptab_5");
    private final By subTab = By.xpath("//a[@href=\"index.php?module=AOK_KnowledgeBase&action=index&parentTab=All\"]");

    private final By createKnowledgeBaseLink = By.xpath("//a[@data-action-name='Create']");
    private final By viewKnowledgeBaseLink = By.xpath("//a[@data-action-name='List']");

    private final By firstRowKnowledgeBaseName = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
    private final By firstRowLocator = By.cssSelector("table.list.view tbody tr[height='20']:first-of-type");
    private final By filterResult = By.className("msg");

    // Filter locators
    private final By filterButton = By.xpath("//a[@title='Filter']");
    private final By quickFilterTab = By.xpath("//li[contains(@class, 'searchTabHandler') and contains(@class, 'basic')]/a");
    private final By modalContent = By.className("modal-content");
    private final By filterNameField = By.id("name_basic");
    private final By filterMyItemsCheckbox = By.id("current_user_only_basic");
    private final By filterSubmitButton = By.id("search_form_submit");
    private final By filterClearButton = By.id("search_form_clear");

    // Detail page locators
    private final By tabActionsInDetail = By.id("tab-actions");
    private final By editButtonInDetail = By.id("edit_button");
    private final By deleteButtonInDetail = By.id("delete_button");

    // All pages have class module-title-text for their title
    private final By pageTitle = By.className("module-title-text");

    public KnowledgeBasesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    /**
     * Checks if the page title matches the expected title
     *
     * @param expectedTitle The title to verify
     * @return true if title contains expected text
     */
    public boolean checkPageTitle(String expectedTitle) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
            String title = driver.findElement(pageTitle).getText();
            return title.toUpperCase().contains(expectedTitle.toUpperCase());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Navigates to the Reports module from the main navigation
     */
    public void navigateToKnowledgeBaseModule() {
        wait.until(ExpectedConditions.presenceOfElementLocated(navTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(navTab));
        actions.moveToElement(driver.findElement(navTab)).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subTab));
        driver.findElement(subTab).click();
    }

    /**
     * Clicks the Create link to navigate to report creation page
     */
    public void navigateToCreateKnowledgeBase() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createKnowledgeBaseLink));
        wait.until(ExpectedConditions.elementToBeClickable(createKnowledgeBaseLink));
        driver.findElement(createKnowledgeBaseLink).click();
    }

    /**
     * Clicks the View/List link to navigate to reports list page
     */
    public void navigateToViewKnowledgeBase() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewKnowledgeBaseLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewKnowledgeBaseLink));
        driver.findElement(viewKnowledgeBaseLink).click();
    }

    /**
     * Verifies if the given report name appears in the first row of the table
     *
     * @param reportName The name to check
     * @return true if first row contains the report name
     */
    public boolean isInFirstRow(String reportName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.list.view")));
            By firstRowNameLocator = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
            wait.until(ExpectedConditions.presenceOfElementLocated(firstRowNameLocator));
            String firstRowName = driver.findElement(firstRowNameLocator).getText().trim();
            return firstRowName.equals(reportName);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the first row element from the reports table
     *
     * @return WebElement of the first row
     */
    public WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowLocator));
        return driver.findElement(firstRowLocator);
    }

    /**
     * Gets the report name link from the first row
     *
     * @return WebElement of the report name link
     */
    public WebElement getFirstRowNameLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowKnowledgeBaseName));
        return driver.findElement(firstRowKnowledgeBaseName);
    }

    /**
     * Clicks on the first report in the list to view its details
     */
    public void clickFirstKnowledgeBase() {
        System.out.println("Clicking first knowledge base: " + getFirstRowNameLocator().getText().trim());
        getFirstRowNameLocator().click();
    }

    /**
     * Verifies if the report detail page title matches the expected report name
     *
     * @param reportName The name to verify
     * @return true if title contains the report name
     */
    public boolean isKnowledgeBaseTitleCorrect(String knowledgeBaseName) {
        try {
            // Wait for tab actions to ensure page is loaded
            wait.until(ExpectedConditions.presenceOfElementLocated(tabActionsInDetail));

            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println("Title Found: " + title.toLowerCase());
            System.out.println("Expected: " + knowledgeBaseName.toLowerCase());
            return title.toLowerCase().contains(knowledgeBaseName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Navigates to edit the current knowledge base from the detail page
     */
    public void navigateToEditKnowledgeBase() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonInDetail));
        driver.findElement(editButtonInDetail).click();
    }

    /**
     * Clicks the delete button for the current knowledge base
     */
    public void deleteKnowledgeBase() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteButtonInDetail));
        driver.findElement(deleteButtonInDetail).click();
    }

    /**
     * Accepts the delete confirmation dialog
     */
    public void clickOkInDeleteDialog() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    /**
     * Applies a quick filter to the reports list
     *
     * @param name The report name to filter by
     * @param myItems Whether to show only items assigned to current user
     * @param favorites Whether to show only favorite items
     */
    public void filterQuick(String name, boolean myItems) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterButton));
        driver.findElement(filterButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(modalContent));
        wait.until(ExpectedConditions.visibilityOfElementLocated(quickFilterTab));

        WebElement quickFilterElement = driver.findElement(quickFilterTab);
        if (!quickFilterElement.getAttribute("class").contains("active")) {
            quickFilterElement.click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(filterNameField));
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterMyItemsCheckbox));

        driver.findElement(filterNameField).sendKeys(name);
        WebElement myItemsCheckbox = driver.findElement(filterMyItemsCheckbox);
        if (myItemsCheckbox.isSelected() != myItems) {
            myItemsCheckbox.click();
        }

        driver.findElement(filterSubmitButton).click();
    }

    /**
     * Checks if the filter returned no results
     *
     * @return true if "no results found" message is displayed
     */
    public boolean isFilterResultEmpty() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(filterResult));
            String msg = driver.findElement(filterResult).getText().toLowerCase();
            System.out.println("Filter result message: " + msg);
            return msg.contains("no results found");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the filter result message element locator
     *
     * @return By locator for filter result message
     */
    public By getFilterResult() {
        return filterResult;
    }

    /**
     * Checks if any filter is applied and clears it if necessary
     */
    public void checkAndClearFilter() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterButton));
        driver.findElement(filterButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalContent));

        wait.until(ExpectedConditions.visibilityOfElementLocated(quickFilterTab));
        WebElement quickFilterElement = driver.findElement(quickFilterTab);
        if (!quickFilterElement.getAttribute("class").contains("active")) {
            quickFilterElement.click();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(filterNameField));
        wait.until(ExpectedConditions.presenceOfElementLocated(filterMyItemsCheckbox));

        // Check if name field is filled or checkboxes are checked
        String nameFieldValue = driver.findElement(filterNameField).getAttribute("value").trim();
        boolean myItemsChecked = driver.findElement(filterMyItemsCheckbox).isSelected();

        if (nameFieldValue.isEmpty() && !myItemsChecked) {
            // No filter applied
            driver.findElement(filterSubmitButton).click();
            return;
        }

        // Clear all fields and then search, this will clear filter
        wait.until(ExpectedConditions.presenceOfElementLocated(filterClearButton));
        driver.findElement(filterClearButton).click();

        driver.findElement(filterSubmitButton).click();
    }
}
