package com.proyek_softes.demo.pages.email_templates;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmailTemplatesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Navbar for navigation to reports
    private final By navTab = By.id("grouptab_5");
    private final By subTab = By.xpath("//a[@href=\"index.php?module=EmailTemplates&action=index&parentTab=All\"]");

    private final By createEmailTemplateLink = By.xpath("//a[@data-action-name='Create']");
    private final By viewEmailTemplateLink = By.xpath("//a[@data-action-name='View_Email_Templates']");

    private final By firstRowEmailTemplateName = By.cssSelector("table.list.view tbody tr:first-child td[type='varchar'] a");
    private final By firstRowLocator = By.cssSelector("table.list.view tbody tr[height='20']:first-of-type");
    private final By filterResult = By.className("msg");

    // Filter locators
    private final By filterButton = By.xpath("//a[@title='Filter']");
    private final By quickFilterTab = By.xpath("//li[contains(@class, 'searchTabHandler') and contains(@class, 'basic')]/a");
    private final By modalContent = By.className("modal-content");
    private final By filterNameField = By.id("name_basic");
    private final By filterTypeField = By.id("type_basic");
    private final By filterSubmitButton = By.id("search_form_submit");
    private final By filterClearButton = By.id("search_form_clear");

    // Detail page locators
    private final By editButtonInDetail = By.id("editEmailTemplatesButton");
    private final By actionDropdownCaret = By.cssSelector(".sugar_action_button .suitepicon-action-caret");
    private final By deleteButtonInDetail = By.xpath("//ul[contains(@class,'subnav')]//li/a[text()='Delete']");

    public EmailTemplatesPage(WebDriver driver) {
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
            By pageTitle = By.cssSelector(".moduleTitle h2");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println("Page title after save: " + title);
            return title.toLowerCase().contains(expectedTitle.toLowerCase());
        } catch (Exception e) {
            System.out.println("Error verifying email template save: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigates to the Reports module from the main navigation
     */
    public void navigateToEmailTemplatesModule() {
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
    public void navigateToCreateEmailTemplate() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createEmailTemplateLink));
        wait.until(ExpectedConditions.elementToBeClickable(createEmailTemplateLink));
        driver.findElement(createEmailTemplateLink).click();
    }

    /**
     * Clicks the View/List link to navigate to reports list page
     */
    public void navigateToViewEmailTemplates() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewEmailTemplateLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewEmailTemplateLink));
        driver.findElement(viewEmailTemplateLink).click();
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
            By firstRowNameLocator = By.cssSelector("table.list.view tbody tr:first-child td[type='varchar'] a");
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
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowEmailTemplateName));
        return driver.findElement(firstRowEmailTemplateName);
    }

    /**
     * Clicks on the first report in the list to view its details
     */
    public void clickFirstEmailTemplate() {
        System.out.println("Clicking first email template: " + getFirstRowNameLocator().getText().trim());
        getFirstRowNameLocator().click();
    }

    /**
     * Verifies if the report detail page title matches the expected report name
     *
     * @param reportName The name to verify
     * @return true if title contains the report name
     */
    public boolean isEmailTemplateTitleCorrect(String emailTemplateName) {
        try {
            By pageTitle = By.cssSelector(".moduleTitle h2");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println("Page title after save: " + title);
            return title.toLowerCase().contains(emailTemplateName.toLowerCase());
        } catch (Exception e) {
            System.out.println("Error verifying email template save: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigates to edit the current email template from the detail page
     */
    public void navigateToEditEmailTemplate() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonInDetail));
        wait.until(ExpectedConditions.elementToBeClickable(editButtonInDetail));
        driver.findElement(editButtonInDetail).click();
    }

    /**
     * Clicks the delete button for the current email template
     */
    public void deleteEmailTemplate() {
        // Click the dropdown caret to open the submenu
        wait.until(ExpectedConditions.visibilityOfElementLocated(actionDropdownCaret));
        wait.until(ExpectedConditions.elementToBeClickable(actionDropdownCaret));
        driver.findElement(actionDropdownCaret).click();
        
        // Wait for submenu and click Delete
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteButtonInDetail));
        wait.until(ExpectedConditions.elementToBeClickable(deleteButtonInDetail));
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
     * Applies a quick filter to the email templates list
     *
     * @param name The template name to filter by
     * @param type The template type to filter by (empty string for no type
     * filter)
     */
    public void filterQuick(String name, String type) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterButton));
        driver.findElement(filterButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(modalContent));
        wait.until(ExpectedConditions.visibilityOfElementLocated(quickFilterTab));

        WebElement quickFilterElement = driver.findElement(quickFilterTab);
        if (!quickFilterElement.getAttribute("class").contains("active")) {
            quickFilterElement.click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(filterNameField));

        driver.findElement(filterNameField).sendKeys(name);

        // Select type if provided
        if (type != null && !type.isEmpty()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(filterTypeField));
            org.openqa.selenium.support.ui.Select typeSelect = new org.openqa.selenium.support.ui.Select(driver.findElement(filterTypeField));
            typeSelect.selectByValue(type);
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

        // Check if name field is filled or type is selected
        String nameFieldValue = driver.findElement(filterNameField).getAttribute("value").trim();

        boolean typeSelected = false;
        try {
            org.openqa.selenium.support.ui.Select typeSelect = new org.openqa.selenium.support.ui.Select(driver.findElement(filterTypeField));
            typeSelected = !typeSelect.getAllSelectedOptions().isEmpty()
                    && !typeSelect.getFirstSelectedOption().getAttribute("value").isEmpty();
        } catch (Exception e) {
            // Type field might not be present, ignore
        }

        if (nameFieldValue.isEmpty() && !typeSelected) {
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
