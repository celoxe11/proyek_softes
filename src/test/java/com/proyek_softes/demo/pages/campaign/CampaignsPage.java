package com.proyek_softes.demo.pages.campaign;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CampaignsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Navbar untuk navigasi ke Calls
    private final By navTab = By.id("grouptab_5");
    private final By subTab = By.linkText("Campaigns");

    private final By createCampaignLink = By.xpath("//a[@data-action-name='Create']");
    private final By viewCampaignLink = By.xpath("//a[@data-action-name='List']");
    private final By importCampaignLink = By.xpath("//a[@data-action-name='Import']");
    private final By createEmailTemplateLink = By.xpath("//a[@data-action-name='View_Create_Email_Templates']");
    private final By viewEmailTemplateLink = By.xpath("//a[@data-action-name='View_Email_Templates']");
    private final By viewDiagnosticsLink = By.xpath("//a[@data-action-name='View_Diagnostics']");
    private final By createPersonFormLink = By.xpath("//a[@data-action-name='Create_Person_Form']");

    // Campaign type locators
    private final By newsletterCampaignType = By.xpath("//span[text()='Newsletter']/parent::a");
    private final By emailCampaignType = By.xpath("//span[text()='Email']/parent::a");
    private final By nonEmailCampaignType = By.xpath("//span[text()='Non-email based Campaign']/parent::a");
    private final By surveyCampaignType = By.xpath("//span[text()='Survey']/parent::a");

    private final By firstRowCampaignName = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
    private final By firstRowLocator = By.cssSelector("table.list.view tbody tr[height='20']:first-of-type");
    private final By filterResult = By.className("msg");

    // filter locators
    private final By filterButton = By.xpath("//a[@title='Filter']");
    private final By quickFilterTab = By.xpath("//li[contains(@class, 'searchTabHandler') and contains(@class, 'basic')]/a");
    private final By modalContent = By.className("modal-content");
    private final By filterNameField = By.id("name_basic");
    private final By filterMyItemsCheckbox = By.id("current_user_only_basic");
    private final By filterSubmitButton = By.id("search_form_submit");
    private final By filterClearButton = By.id("search_form_clear");

    // detail page locators
    private final By tabActionsInDetail = By.id("tab-actions");
    private final By deleteButtonInDetail = By.id("delete_button");

    private final By pageTitle = By.className("module-title-text");

    public CampaignsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    public boolean checkPageTitle(String expectedTitle) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("module-title-text")));
            String title = driver.findElement(By.className("module-title-text")).getText();
            return title.toUpperCase().contains(expectedTitle.toUpperCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToCampaignsModule() {
        wait.until(ExpectedConditions.presenceOfElementLocated(navTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(navTab));
        actions.moveToElement(driver.findElement(navTab)).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subTab));
        driver.findElement(subTab).click();
    }

    public void navigateToCreateCampaign() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createCampaignLink));
        wait.until(ExpectedConditions.elementToBeClickable(createCampaignLink));
        driver.findElement(createCampaignLink).click();
    }

    public void navigateToViewCampaign() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewCampaignLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewCampaignLink));
        driver.findElement(viewCampaignLink).click();
    }

    public void navigateToImportCampaign() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(importCampaignLink));
        wait.until(ExpectedConditions.elementToBeClickable(importCampaignLink));
        driver.findElement(importCampaignLink).click();
    }

    public void navigateToCreateEmailTemplate() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createEmailTemplateLink));
        wait.until(ExpectedConditions.elementToBeClickable(createEmailTemplateLink));
        driver.findElement(createEmailTemplateLink).click();
    }

    public void navigateToViewEmailTemplate() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewEmailTemplateLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewEmailTemplateLink));
        driver.findElement(viewEmailTemplateLink).click();
    }

    public void navigateToViewDiagnostics() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewDiagnosticsLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewDiagnosticsLink));
        driver.findElement(viewDiagnosticsLink).click();
    }

    public void navigateToCreatePersonForm() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createPersonFormLink));
        wait.until(ExpectedConditions.elementToBeClickable(createPersonFormLink));
        driver.findElement(createPersonFormLink).click();
    }

    public void selectNewsletterCampaign() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(newsletterCampaignType));
        wait.until(ExpectedConditions.elementToBeClickable(newsletterCampaignType));
        driver.findElement(newsletterCampaignType).click();
    }

    public void selectEmailCampaign() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailCampaignType));
        wait.until(ExpectedConditions.elementToBeClickable(emailCampaignType));
        driver.findElement(emailCampaignType).click();
    }

    public void selectNonEmailCampaign() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nonEmailCampaignType));
        wait.until(ExpectedConditions.elementToBeClickable(nonEmailCampaignType));
        driver.findElement(nonEmailCampaignType).click();
    }

    public void selectSurveyCampaign() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(surveyCampaignType));
        wait.until(ExpectedConditions.elementToBeClickable(surveyCampaignType));
        driver.findElement(surveyCampaignType).click();
    }

    public boolean isInFirstRow(String callName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.list.view")));
            By firstRowNameLocator = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
            wait.until(ExpectedConditions.presenceOfElementLocated(firstRowNameLocator));
            String firstRowCallName = driver.findElement(firstRowNameLocator).getText().trim();
            System.out.println(firstRowCallName);
            return firstRowCallName.contains(callName);
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getFirstRowNameLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowCampaignName));
        return driver.findElement(firstRowCampaignName);
    }

    public void clickFirstCampaign() {
        getFirstRowNameLocator().click();
    }

    public WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowLocator));
        return driver.findElement(firstRowLocator);
    }

    public boolean isCampaignTitleCorrect(String campaignName) {
        try {
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println(title.toLowerCase());
            System.out.println(campaignName.toLowerCase());
            return title.toLowerCase().contains(campaignName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }
    
    public void findAndClickCampaign(String campaignName) {
        try {
            // Wait for the list view table to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.list.view")));
            
            // Find the campaign link by name in the table
            By campaignLinkLocator = By.xpath("//table[contains(@class, 'list view')]//td[@type='name' and @field='name']//a[contains(text(), '" + campaignName + "')]");
            
            // Wait for the campaign link to be present and clickable
            WebElement campaignLink = wait.until(ExpectedConditions.elementToBeClickable(campaignLinkLocator));
            
            // Scroll the element into view
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", campaignLink);
            
            Thread.sleep(300);
            
            // Click the campaign link
            campaignLink.click();
            
            System.out.println("Successfully clicked campaign: " + campaignName);
        } catch (InterruptedException e) {
            System.err.println("Failed to find or click campaign: " + campaignName);
            throw new RuntimeException("Could not find campaign with name: " + campaignName, e);
        }
    }

    private void editInlineField(String fieldName, String newValue) throws InterruptedException {
        try {
            // Find the field element by field attribute
            By fieldLocator = By.cssSelector(".detail-view-field[field='" + fieldName + "']");
            WebElement fieldElement = wait.until(ExpectedConditions.presenceOfElementLocated(fieldLocator));
            
            // Scroll to field
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fieldElement);
            Thread.sleep(300);
            
            // Double-click to activate inline editing
            actions.doubleClick(fieldElement).perform();
            Thread.sleep(500); // Wait for inline edit to activate
            
            // Find the input field - try different input types
            WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("#EditView input[name='" + fieldName + "'], #EditView textarea[name='" + fieldName + "'], #EditView select[name='" + fieldName + "']")));
            
            // Clear and fill the input field
            inputField.clear();
            inputField.sendKeys(newValue);
            
            // Click the save button
            By saveButtonLocator = By.id("inlineEditSaveButton");
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(saveButtonLocator));
            saveButton.click();
            
            // Wait for save to complete
            Thread.sleep(1000);
            
            System.out.println("Successfully edited field: " + fieldName + " with value: " + newValue);
            
        } catch (Exception e) {
            System.out.println("Failed to edit field: " + fieldName + " - " + e.getMessage());
        }
    }

    public void editCampaign(Map<String, String> editData) {
        try {
            // Wait for detail view to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("detail-view")));
            
            // Edit specific fields based on what's in the test data
            // Overview Tab Fields
            if (editData.containsKey("name") && !editData.get("name").isEmpty()) {
                editInlineField("name", editData.get("name"));
                System.out.println("name edited: " + editData.get("name"));
            }
            
            if (editData.containsKey("status") && !editData.get("status").isEmpty()) {
                editInlineField("status", editData.get("status"));
                System.out.println("status edited: " + editData.get("status"));
            }
            
            if (editData.containsKey("start_date") && !editData.get("start_date").isEmpty()) {
                editInlineField("start_date", editData.get("start_date"));
                System.out.println("start_date edited: " + editData.get("start_date"));
            }
            
            if (editData.containsKey("campaign_type") && !editData.get("campaign_type").isEmpty()) {
                editInlineField("campaign_type", editData.get("campaign_type"));
                System.out.println("campaign_type edited: " + editData.get("campaign_type"));
            }
            
            if (editData.containsKey("end_date") && !editData.get("end_date").isEmpty()) {
                editInlineField("end_date", editData.get("end_date"));
                System.out.println("end_date edited: " + editData.get("end_date"));
            }
            
            if (editData.containsKey("frequency") && !editData.get("frequency").isEmpty()) {
                editInlineField("frequency", editData.get("frequency"));
                System.out.println("frequency edited: " + editData.get("frequency"));
            }
            
            // Budget Tab Fields - Need to switch to Budget tab first
            if (editData.containsKey("budget") || editData.containsKey("expected_cost") || 
                editData.containsKey("actual_cost") || editData.containsKey("expected_revenue") ||
                editData.containsKey("impressions") || editData.containsKey("objective")) {
                
                // Click Budget tab
                By budgetTab = By.xpath("//a[@id='tab1' and contains(text(), 'Budget')]");
                wait.until(ExpectedConditions.elementToBeClickable(budgetTab));
                driver.findElement(budgetTab).click();
                Thread.sleep(500);
                System.out.println("Switched to Budget tab");
                
                if (editData.containsKey("budget") && !editData.get("budget").isEmpty()) {
                    editInlineField("budget", editData.get("budget"));
                    System.out.println("budget edited: " + editData.get("budget"));
                }
                
                if (editData.containsKey("expected_cost") && !editData.get("expected_cost").isEmpty()) {
                    editInlineField("expected_cost", editData.get("expected_cost"));
                    System.out.println("expected_cost edited: " + editData.get("expected_cost"));
                }
                
                if (editData.containsKey("actual_cost") && !editData.get("actual_cost").isEmpty()) {
                    editInlineField("actual_cost", editData.get("actual_cost"));
                    System.out.println("actual_cost edited: " + editData.get("actual_cost"));
                }
                
                if (editData.containsKey("expected_revenue") && !editData.get("expected_revenue").isEmpty()) {
                    editInlineField("expected_revenue", editData.get("expected_revenue"));
                    System.out.println("expected_revenue edited: " + editData.get("expected_revenue"));
                }
                
                if (editData.containsKey("impressions") && !editData.get("impressions").isEmpty()) {
                    editInlineField("impressions", editData.get("impressions"));
                    System.out.println("impressions edited: " + editData.get("impressions"));
                }
                
                if (editData.containsKey("objective") && !editData.get("objective").isEmpty()) {
                    editInlineField("objective", editData.get("objective"));
                    System.out.println("objective edited: " + editData.get("objective"));
                }
            }
            
            System.out.println("Completed editing campaign fields");
            
        } catch (Exception e) {
            System.err.println("Error during campaign edit: " + e.getMessage());
            throw new RuntimeException("Failed to edit campaign", e);
        }
    }

    public void deleteCampaign() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteButtonInDetail));
        driver.findElement(deleteButtonInDetail).click();
    }


    public void clickOkInDeleteDialog() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public void filterQuick(String name, boolean myItems) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterButton));
        driver.findElement(filterButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(modalContent));

        wait.until(ExpectedConditions.elementToBeClickable(quickFilterTab));
        WebElement quickFilterElement = driver.findElement(quickFilterTab);
        if (!quickFilterElement.getAttribute("class").contains("active")) {
            quickFilterElement.click();
        }

        driver.findElement(filterNameField).sendKeys(name);
        WebElement myItemsCheckbox = driver.findElement(filterMyItemsCheckbox);
        if (myItemsCheckbox.isSelected() != myItems) {
            myItemsCheckbox.click();
        }

        driver.findElement(filterSubmitButton).click();
    }

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

    public By getFilterResult() {
        return filterResult;
    }

    public void checkAndClearFilter() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterButton));
        driver.findElement(filterButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalContent));

        wait.until(ExpectedConditions.elementToBeClickable(quickFilterTab));
        WebElement quickFilterElement = driver.findElement(quickFilterTab);
        if (!quickFilterElement.getAttribute("class").contains("active")) {
            quickFilterElement.click();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(filterNameField));
        wait.until(ExpectedConditions.presenceOfElementLocated(filterMyItemsCheckbox));

        String nameFieldValue = driver.findElement(filterNameField).getAttribute("value").trim();
        boolean myItemsChecked = driver.findElement(filterMyItemsCheckbox).isSelected();

        if (nameFieldValue.isEmpty() && !myItemsChecked) {
            driver.findElement(filterSubmitButton).click();
            return;
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(filterClearButton));
        driver.findElement(filterClearButton).click();
        driver.findElement(filterSubmitButton).click();
    }
}
