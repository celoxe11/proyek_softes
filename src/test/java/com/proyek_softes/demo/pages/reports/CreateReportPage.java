package com.proyek_softes.demo.pages.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CreateReportPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Button locators
    private final By buttonSaveHeader = By.id("SAVE_HEADER");
    private final By buttonSaveFooter = By.id("SAVE_FOOTER");
    private final By buttonCancelHeader = By.id("CANCEL_HEADER");
    private final By buttonCancelFooter = By.id("CANCEL_FOOTER");

    // Tab locators
    private final By tabFields = By.cssSelector(".tab-toggler.toggle-detailpanel_fields");
    private final By tabConditions = By.cssSelector(".tab-toggler.toggle-detailpanel_conditions");
    private final By tabCharts = By.cssSelector(".tab-toggler.toggle-detailpanel_charts");

    // Module tree and field locators
    private final By moduleTree = By.id("fieldTree");
    private final By fieldTreeLeafs = By.id("fieldTreeLeafs");
    private final By fieldLinesDisplay = By.id("fieldLines");
    private final By moduleTreeFolders = By.cssSelector("#fieldTree .jqtree-folder");
    private final By fieldTreeItems = By.cssSelector("#fieldTreeLeafs .jqtree_common");

    // Display area locators
    private final By fieldDisplayPanel = By.id("detailpanel_fields");
    private final By conditionsPanel = By.id("detailpanel_conditions");
    private final By chartsPanel = By.id("detailpanel_charts");

    // Chart button
    private final By addChartButton = By.id("addChartButton");

    private Map<String, By> inputLocators;

    public CreateReportPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();
        inputLocators.put("name", By.id("name"));
        inputLocators.put("assigned_user_name", By.id("assigned_user_name"));
        inputLocators.put("report_module", By.id("report_module"));
        inputLocators.put("graphs_per_row", By.id("graphs_per_row"));
        inputLocators.put("description", By.id("description"));
        inputLocators.put("group_display", By.id("group_display"));
        inputLocators.put("group_display_1", By.id("group_display_1"));
    }

    /**
     * Saves the report by clicking the Save button
     */
    public void save() {
        try {
            WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(buttonSaveHeader));
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", saveButton);
            Thread.sleep(300);

            try {
                wait.until(ExpectedConditions.elementToBeClickable(buttonSaveHeader)).click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                System.out.println("  Note: Using JavaScript click for Save button due to overlap");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while saving", e);
        }
    }

    /**
     * Cancels the report creation
     */
    public void cancel() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonCancelHeader)).click();
    }

    /**
     * Fills report information from a data map
     * 
     * @param data Map containing field names and values
     */
    public void addInformationFromData(Map<String, String> data) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

            // Fill name
            fillInputFieldFromData("name", data.get("name"));

            // Select report module - this will populate the module tree
            String reportModule = data.get("report_module");
            if (reportModule != null && !reportModule.isEmpty()) {
                selectReportModule(reportModule);
                // Wait for module tree to populate
                Thread.sleep(1000);
            }

            // Fill graphs per row
            fillInputFieldFromData("graphs_per_row", data.get("graphs_per_row"));

            // Fill description
            fillInputFieldFromData("description", data.get("description"));

            // Clear all existing fields from display area (important for edit scenarios)
            clearAllFieldsFromDisplay();
            
            // Expand module tree folder and add fields if specified
            String moduleTreeFolder = data.get("module_tree_folder");
            System.out.println("Module Tree Folder: " + moduleTreeFolder);
            if (moduleTreeFolder != null && !moduleTreeFolder.isEmpty()) {
                clickModuleTreeFolder(moduleTreeFolder);
                Thread.sleep(500);
                
                // Add fields from the field list
                String fieldsToAddStr = data.get("fields_to_add");
                System.out.println("Fields to Add: " + fieldsToAddStr);
                if (fieldsToAddStr != null && !fieldsToAddStr.isEmpty()) {
                    // Parse the fields (assuming comma-separated or JSON array format)
                    String[] fields = fieldsToAddStr.replace("[", "").replace("]", "")
                                                     .replace("\"", "").split(",\\s*");
                    for (String field : fields) {
                        if (!field.trim().isEmpty()) {
                            addFieldFromFieldList(field.trim());
                            Thread.sleep(300);
                        }
                    }
                }
            }

            // Configure field options if specified
            String fieldConfigsStr = data.get("field_configurations");
            if (fieldConfigsStr != null && !fieldConfigsStr.isEmpty()) {
                parseAndConfigureFields(fieldConfigsStr);
            }

            // Select main group if specified
            String mainGroup = data.get("main_group");
            if (mainGroup != null && !mainGroup.isEmpty()) {
                selectMainGroup(mainGroup);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while adding information", e);
        }
    }

    /**
     * Selects a module from the Report Module dropdown
     * 
     * @param moduleName The name of the module to select
     */
    public void selectReportModule(String moduleName) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("report_module")));
            Select select = new Select(dropdown);
            select.selectByVisibleText(moduleName);
            // Wait for module tree to update
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while selecting report module", e);
        }
    }

    /**
     * Clicks on a module tree folder to expand it and show its fields
     * 
     * @param folderName The name of the folder to click (e.g., "Cases", "Created by")
     */
    public void clickModuleTreeFolder(String folderName) {
        try {
            WebElement tree = wait.until(ExpectedConditions.presenceOfElementLocated(moduleTree));
            
            // Find the folder by its title text
            String xpath = String.format("//div[@id='fieldTree']//span[@class='jqtree_common jqtree-title jqtree-title-folder' and text()='%s']", folderName);
            WebElement folder = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", folder);
            Thread.sleep(300);
            
            folder.click();
            
            // Wait for fields to populate
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while clicking module tree folder", e);
        }
    }

    /**
     * Clicks a field from the field list to add it to the Fields display area
     * 
     * @param fieldName The name of the field to add (e.g., "Account Disabled", "First Name")
     */
    public void addFieldFromFieldList(String fieldName) {
        try {
            // Find the field in the fieldTreeLeafs list
            String xpath = String.format("//div[@id='fieldTreeLeafs']//span[@class='jqtree-title jqtree_common' and text()='%s']", fieldName);
            WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", field);
            Thread.sleep(200);
            
            // Click to add field
            field.click();
            
            // Wait for field to be added to display
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while adding field", e);
        }
    }

    /**
     * Switches to the Fields tab
     */
    public void switchToFieldsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(tabFields)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(fieldDisplayPanel));
    }

    /**
     * Switches to the Conditions tab
     */
    public void switchToConditionsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(tabConditions)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(conditionsPanel));
    }

    /**
     * Switches to the Charts tab
     */
    public void switchToChartsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(tabCharts)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(chartsPanel));
    }

    /**
     * Verifies if a field has been added to the Fields display area
     * 
     * @param fieldName The field name to verify
     * @return true if field is present in display area
     */
    public boolean isFieldInDisplayArea(String fieldName) {
        try {
            // Look for the field label in the fieldLines tbody
            String xpath = String.format("//div[@id='fieldLines']//span[contains(@id, 'aor_fields_field_label') and text()='%s']", fieldName);
            List<WebElement> fields = driver.findElements(By.xpath(xpath));
            return !fields.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clears all fields from the display area by clicking their delete buttons
     * This is useful when editing a report to remove existing fields before adding new ones
     */
    public void clearAllFieldsFromDisplay() {
        try {
            // Find all field rows
            List<WebElement> rows = driver.findElements(By.cssSelector("#fieldLines tbody tr[id^='field_line']"));
            System.out.println("Found " + rows.size() + " field rows to clear");
            
            // Click delete button for each non-deleted field
            for (WebElement row : rows) {
                try {
                    String rowId = row.getAttribute("id");
                    String deletedInputId = rowId.replace("field_line", "aor_fields_deleted");
                    WebElement deletedInput = row.findElement(By.id(deletedInputId));
                    
                    // Only delete if not already deleted
                    if (deletedInput.getAttribute("value").equals("0")) {
                        // Find the delete button - try multiple selector patterns
                        WebElement deleteButton = null;
                        
                        // Try pattern 1: button with id containing 'delete'
                        try {
                            deleteButton = row.findElement(By.cssSelector("button[id*='delete']"));
                        } catch (Exception e1) {
                            // Try pattern 2: button with fa-minus icon
                            try {
                                deleteButton = row.findElement(By.cssSelector("button.fa-minus-circle"));
                            } catch (Exception e2) {
                                // Try pattern 3: any button with onclick containing delete
                                try {
                                    deleteButton = row.findElement(By.cssSelector("button[onclick*='delete']"));
                                } catch (Exception e3) {
                                    // Try pattern 4: input type button
                                    deleteButton = row.findElement(By.cssSelector("input[type='button'][value='']"));
                                }
                            }
                        }
                        
                        if (deleteButton != null) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", deleteButton);
                            Thread.sleep(100);
                            deleteButton.click();
                            Thread.sleep(200);
                            System.out.println("Deleted field row: " + rowId);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Could not delete field row: " + e.getMessage());
                }
            }
            
            Thread.sleep(500);
            System.out.println("Cleared all fields from display area");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while clearing fields", e);
        } catch (Exception e) {
            System.out.println("Error clearing fields: " + e.getMessage());
        }
    }

    /**
     * Gets the count of fields added to the display area
     * 
     * @return Number of fields in the display
     */
    public int getFieldDisplayCount() {
        try {
            List<WebElement> rows = driver.findElements(By.cssSelector("#fieldLines tbody tr[id^='field_line']"));
            // Filter out deleted rows
            int count = 0;
            for (WebElement row : rows) {
                String rowId = row.getAttribute("id");
                String deletedInputId = rowId.replace("field_line", "aor_fields_deleted");
                WebElement deletedInput = row.findElement(By.id(deletedInputId));
                if (deletedInput.getAttribute("value").equals("0")) {
                    count++;
                }
            }
            return count;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Gets the list of all available module folders in the tree
     * 
     * @return List of module folder names
     */
    public List<String> getModuleTreeFolders() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(moduleTree));
            List<WebElement> folders = driver.findElements(By.cssSelector("#fieldTree > ul > li.jqtree-folder > div > span.jqtree-title"));
            return folders.stream()
                    .map(WebElement::getText)
                    .toList();
        } catch (Exception e) {
            return List.of();
        }
    }

    /**
     * Gets the list of all fields in the current field list
     * 
     * @return List of field names
     */
    public List<String> getAvailableFields() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(fieldTreeLeafs));
            List<WebElement> fields = driver.findElements(By.cssSelector("#fieldTreeLeafs li > div > span.jqtree-title"));
            return fields.stream()
                    .map(WebElement::getText)
                    .toList();
        } catch (Exception e) {
            return List.of();
        }
    }

    /**
     * Checks if the Add Chart button is visible
     * 
     * @return true if button is visible
     */
    public boolean isAddChartButtonVisible() {
        try {
            return driver.findElement(addChartButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clicks the Add Chart button in the Charts tab
     */
    public void clickAddChartButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addChartButton)).click();
    }

    // Helper methods

    private void fillInputFieldFromData(String fieldName, String value) {
        if (value != null && !value.isEmpty()) {
            By locator = inputLocators.get(fieldName);
            if (locator != null) {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                element.clear();
                element.sendKeys(value);
            }
        }
    }

    private void selectDropdown(String fieldName, String value) {
        if (value != null && !value.isEmpty()) {
            By locator = inputLocators.get(fieldName);
            if (locator != null) {
                WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                Select select = new Select(dropdown);
                select.selectByVisibleText(value);
            }
        }
    }

    /**
     * Gets the currently selected report module
     * 
     * @return The selected module name
     */
    public String getSelectedReportModule() {
        try {
            WebElement dropdown = driver.findElement(By.id("report_module"));
            Select select = new Select(dropdown);
            return select.getFirstSelectedOption().getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Verifies if the module tree is populated (has folders)
     * 
     * @return true if tree has folders
     */
    public boolean isModuleTreePopulated() {
        try {
            List<WebElement> folders = driver.findElements(moduleTreeFolders);
            return !folders.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifies if the field list is populated (has fields)
     * 
     * @return true if field list has items
     */
    public boolean isFieldListPopulated() {
        try {
            List<WebElement> fields = driver.findElements(fieldTreeItems);
            return !fields.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifies if the report was saved successfully by checking the page title
     * 
     * @param reportName The name of the report that should be displayed
     * @return true if save was successful
     */
    public boolean isReportSavedSuccessfully(String reportName) {
        try {
            By pageTitle = By.className("module-title-text");
            wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle));
            String title = driver.findElement(pageTitle).getText();
            System.out.println("Page title after save: " + title);
            return title.toLowerCase().contains(reportName.toLowerCase());
        } catch (Exception e) {
            System.out.println("Error verifying report save: " + e.getMessage());
            return false;
        }
    }

    /**
     * Parses field configurations from JSON string and applies them
     * Uses the actual visible field indexes instead of the indexes from JSON
     * 
     * @param fieldConfigsStr JSON string containing field configurations
     */
    private void parseAndConfigureFields(String fieldConfigsStr) {
        try {
            Gson gson = new Gson();
            JsonArray configurations = gson.fromJson(fieldConfigsStr, JsonArray.class);
            
            // Get all non-deleted field indexes (visible fields)
            List<Integer> visibleFieldIndexes = getVisibleFieldIndexes();
            System.out.println("Visible field indexes: " + visibleFieldIndexes);
            
            // Configure each field using its actual visible index
            for (int i = 0; i < configurations.size() && i < visibleFieldIndexes.size(); i++) {
                JsonObject config = configurations.get(i).getAsJsonObject();
                
                // Use the actual visible field index, not the one from JSON
                int actualFieldIndex = visibleFieldIndexes.get(i);
                boolean display = config.get("display").getAsBoolean();
                boolean link = config.get("link").getAsBoolean();
                String label = config.get("label").getAsString();
                String function = config.get("function").getAsString();
                String sort = config.get("sort").getAsString();
                boolean groupBy = config.get("group_by").getAsBoolean();
                String total = config.get("total").getAsString();
                
                System.out.println("Configuring field at actual index " + actualFieldIndex + " with label: " + label);
                configureField(actualFieldIndex, display, link, label, function, sort, groupBy, total);
            }
            
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Error parsing field configurations: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Gets the list of indexes for all visible (non-deleted) fields
     * 
     * @return List of field indexes that are not deleted
     */
    private List<Integer> getVisibleFieldIndexes() {
        List<Integer> indexes = new ArrayList<>();
        try {
            List<WebElement> rows = driver.findElements(By.cssSelector("#fieldLines tbody tr[id^='field_line']"));
            
            for (WebElement row : rows) {
                String rowId = row.getAttribute("id");
                String deletedInputId = rowId.replace("field_line", "aor_fields_deleted");
                
                try {
                    WebElement deletedInput = row.findElement(By.id(deletedInputId));
                    // Only include non-deleted fields (value = "0")
                    if (deletedInput.getAttribute("value").equals("0")) {
                        // Extract the index from the row ID
                        String indexStr = rowId.replace("field_line", "");
                        indexes.add(Integer.parseInt(indexStr));
                    }
                } catch (Exception e) {
                    // Skip if element not found
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting visible field indexes: " + e.getMessage());
        }
        return indexes;
    }

    /**
     * Configures a specific field's options in the field table
     * 
     * @param fieldIndex The index of the field (0-based)
     * @param display Whether to display the field
     * @param link Whether to make the field a link
     * @param label Custom label for the field
     * @param function Aggregate function (empty, COUNT, MIN, MAX, SUM, AVG)
     * @param sort Sort order (empty, ASC, DESC)
     * @param groupBy Whether to group by this field
     * @param total Total function (empty, COUNT, SUM, AVG)
     */
    public void configureField(int fieldIndex, boolean display, boolean link, String label,
                               String function, String sort, boolean groupBy, String total) {
        try {
            // Wait for field row to be present
            By fieldRow = By.id("field_line" + fieldIndex);
            wait.until(ExpectedConditions.presenceOfElementLocated(fieldRow));

            // Configure Display checkbox
            WebElement displayCheckbox = driver.findElement(By.id("aor_fields_display" + fieldIndex));
            if (displayCheckbox.isSelected() != display) {
                displayCheckbox.click();
            }

            // Configure Link checkbox
            WebElement linkCheckbox = driver.findElement(By.id("aor_fields_link" + fieldIndex));
            if (linkCheckbox.isSelected() != link) {
                linkCheckbox.click();
            }

            // Configure Label
            if (label != null && !label.isEmpty()) {
                WebElement labelInput = driver.findElement(By.id("aor_fields_label" + fieldIndex));
                labelInput.clear();
                labelInput.sendKeys(label);
            }

            // Configure Function dropdown
            if (function != null && !function.isEmpty()) {
                WebElement functionDropdown = driver.findElement(By.id("aor_fields_field_function[" + fieldIndex + "]"));
                Select functionSelect = new Select(functionDropdown);
                functionSelect.selectByValue(function);
            }

            // Configure Sort dropdown
            if (sort != null && !sort.isEmpty()) {
                WebElement sortDropdown = driver.findElement(By.id("aor_fields_sort_by" + fieldIndex));
                Select sortSelect = new Select(sortDropdown);
                sortSelect.selectByValue(sort);
            }

            // Configure Group By checkbox
            WebElement groupByCheckbox = driver.findElement(By.id("aor_fields_group_by" + fieldIndex));
            if (groupByCheckbox.isSelected() != groupBy) {
                groupByCheckbox.click();
            }

            // Configure Total dropdown
            if (total != null && !total.isEmpty()) {
                WebElement totalDropdown = driver.findElement(By.id("aor_fields_total" + fieldIndex));
                Select totalSelect = new Select(totalDropdown);
                totalSelect.selectByValue(total);
            }

            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while configuring field", e);
        }
    }

    /**
     * Selects the main group for the report
     * 
     * @param value The value/index to select in the group_display dropdown
     */
    public void selectMainGroup(String value) {
        try {
            WebElement groupDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("group_display")));
            Select select = new Select(groupDropdown);
            select.selectByValue(value);
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while selecting main group", e);
        }
    }
}

