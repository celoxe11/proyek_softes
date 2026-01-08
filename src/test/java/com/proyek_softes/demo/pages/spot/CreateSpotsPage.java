package com.proyek_softes.demo.pages.spot;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateSpotsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private Map<String, By> inputLocators;

    public CreateSpotsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();
        inputLocators.put("name", By.id("name"));
        inputLocators.put("type", By.id("type"));
    }

    /**
     * Waits for the configuration GUI (pivot table) to load after selecting
     * type
     */
    private void waitForConfigurationToLoad() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("output")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("pvtUi")));
            Thread.sleep(1000); // Additional wait for JavaScript to initialize
            System.out.println("  → Configuration GUI loaded");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while waiting for configuration", e);
        }
    }

    /**
     * Selects the chart/table renderer type from dropdown
     *
     * @param rendererType e.g., "Table", "Bar Chart", "Line Chart", "Pie Chart"
     */
    private void selectRenderer(String rendererType) {
        try {
            By rendererDropdown = By.className("pvtRenderer");
            WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(rendererDropdown));
            Select select = new Select(dropdown);
            select.selectByVisibleText(rendererType);
            Thread.sleep(500); // Wait for chart to render
            System.out.println("  → Selected renderer: " + rendererType);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while selecting renderer", e);
        }
    }

    /**
     * Selects the aggregation function from dropdown
     *
     * @param aggregator e.g., "Count", "Sum", "Average", "Maximum", "Minimum"
     */
    private void selectAggregator(String aggregator) {
        try {
            By aggregatorDropdown = By.className("pvtAggregator");
            WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(aggregatorDropdown));
            Select select = new Select(dropdown);
            select.selectByVisibleText(aggregator);
            Thread.sleep(500); // Wait for aggregation to update
            System.out.println("  → Selected aggregator: " + aggregator);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while selecting aggregator", e);
        }
    }

    /**
     * Select attribute
     *
     * @param aggregator e.g., "Sum", "Average", "Maximum", "Minimum"
     * @param attribute e.g., "Amount", "Quantity"
     */
    private void selectAttribute(String aggregator, String attribute) {
        try {
            if (aggregator.equals("Sum") || aggregator.equals("Average")
                    || aggregator.equals("Maximum") || aggregator.equals("Minimum")) {
                // If numeric aggregation, select attribute field
                By attributeDropdown = By.className("pvtAttrDropdown");
                WebElement attrDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(attributeDropdown));
                Select attrSelect = new Select(attrDropdown);
                // For simplicity, select the first numeric field available
                if (attribute != null && !attribute.isEmpty()) {
                    attrSelect.selectByVisibleText(attribute);
                } else {
                    attrSelect.selectByIndex(0); // Select first option if none specified
                }
                Thread.sleep(500); // Wait for attribute to apply
                System.out.println("  → Selected attribute for aggregation");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while selecting aggregator", e);
        }

    }

    /**
     * Drags a field from unused area to rows area using JavaScript
     *
     * @param fieldName The name of the field to drag (e.g., "Account Name",
     * "Opportunity Name")
     */
    private void dragFieldToRows(String fieldName) {
        try {
            String script
                    = "var unused = document.querySelector('.pvtUnused');"
                    + "var rows = document.querySelector('.pvtRows');"
                    + "if (!unused || !rows) {"
                    + "  return 'ERROR: Containers not found';"
                    + "}"
                    + "var field = Array.from(unused.querySelectorAll('.pvtAttr')).find(function(el) { return el.textContent.includes('" + fieldName + "'); });"
                    + "if (field && rows) {"
                    + "  var li = field.closest('li');"
                    + "  rows.appendChild(li);"
                    + "  if (typeof $ !== 'undefined' && $(rows).sortable) {"
                    + "    $(rows).sortable('refresh');"
                    + "  }"
                    + "  if (typeof c !== 'undefined' && c.pivot) {"
                    + "    c.pivot();"
                    + "  }"
                    + "  return 'SUCCESS';"
                    + "}"
                    + "return 'ERROR: Field not found';";

            Object result = ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(script);
            System.out.println("  → Drag result for '" + fieldName + "': " + result);
            Thread.sleep(1500); // Wait for pivot table to update
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while dragging field", e);
        }
    }

    /**
     * Drags a field from unused area to columns area using JavaScript
     * (Used for Accounts type configuration)
     *
     * @param fieldName The name of the field to drag (e.g., "Name", "Industry")
     */
    private void dragFieldToCols(String fieldName) {
        try {
            String script
                    = "var unused = document.querySelector('.pvtUnused');"
                    + "var cols = document.querySelector('.pvtCols');"
                    + "if (!unused || !cols) {"
                    + "  return 'ERROR: Containers not found';"
                    + "}"
                    + "var field = Array.from(unused.querySelectorAll('.pvtAttr')).find(function(el) { return el.textContent.includes('" + fieldName + "'); });"
                    + "if (field && cols) {"
                    + "  var li = field.closest('li');"
                    + "  cols.appendChild(li);"
                    + "  if (typeof $ !== 'undefined' && $(cols).sortable) {"
                    + "    $(cols).sortable('refresh');"
                    + "  }"
                    + "  if (typeof c !== 'undefined' && c.pivot) {"
                    + "    c.pivot();"
                    + "  }"
                    + "  return 'SUCCESS';"
                    + "}"
                    + "return 'ERROR: Field not found';";

            Object result = ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(script);
            System.out.println("  → Drag to cols result for '" + fieldName + "': " + result);
            Thread.sleep(1500); // Wait for pivot table to update
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while dragging field to cols", e);
        }
    }

    /**
     * Clears all configured spots by dragging fields from rows/cols back to
     * unused area This is useful for the edit functionality to reset the
     * configuration
     */
    private void clearSpots() {
        try {
            String script
                    = "var rows = document.querySelector('.pvtRows');"
                    + "var cols = document.querySelector('.pvtCols');"
                    + "var unused = document.querySelector('.pvtUnused');"
                    + "if (!rows || !cols || !unused) {"
                    + "  return 'ERROR: Containers not found';"
                    + "}"
                    + // Move all items from rows to unused
                    "var rowItems = rows.querySelectorAll('li');"
                    + "for (var i = 0; i < rowItems.length; i++) {"
                    + "  unused.appendChild(rowItems[i]);"
                    + "}"
                    + // Move all items from cols to unused
                    "var colItems = cols.querySelectorAll('li');"
                    + "for (var i = 0; i < colItems.length; i++) {"
                    + "  unused.appendChild(colItems[i]);"
                    + "}"
                    + // Refresh sortable and pivot table
                    "if (typeof $ !== 'undefined') {"
                    + "  if ($(unused).sortable) $(unused).sortable('refresh');"
                    + "  if ($(rows).sortable) $(rows).sortable('refresh');"
                    + "  if ($(cols).sortable) $(cols).sortable('refresh');"
                    + "}"
                    + "if (typeof c !== 'undefined' && c.pivot) {"
                    + "  c.pivot();"
                    + "}"
                    + "return 'SUCCESS: All fields cleared';";

            Object result = ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(script);
            System.out.println("  → Clear spots result: " + result);
            Thread.sleep(1000); // Wait for pivot table to update
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while clearing spots", e);
        }
    }

    /**
     * Clears all configured spots for Accounts type by dragging fields from
     * cols back to unused area The Accounts type uses columns instead of rows
     */
    private void clearSpotsAccounts() {
        try {
            String script
                    = "var cols = document.querySelector('.pvtCols');"
                    + "var unused = document.querySelector('.pvtUnused');"
                    + "if (!cols || !unused) {"
                    + "  return 'ERROR: Containers not found';"
                    + "}"
                    + // Move all items from cols to unused
                    "var colItems = cols.querySelectorAll('li');"
                    + "for (var i = 0; i < colItems.length; i++) {"
                    + "  unused.appendChild(colItems[i]);"
                    + "}"
                    + // Refresh sortable and pivot table
                    "if (typeof $ !== 'undefined') {"
                    + "  if ($(unused).sortable) $(unused).sortable('refresh');"
                    + "  if ($(cols).sortable) $(cols).sortable('refresh');"
                    + "}"
                    + "if (typeof c !== 'undefined' && c.pivot) {"
                    + "  c.pivot();"
                    + "}"
                    + "return 'SUCCESS: All cols fields cleared';";

            Object result = ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(script);
            System.out.println("  → Clear spots (Accounts) result: " + result);
            Thread.sleep(1000); // Wait for pivot table to update
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while clearing spots (Accounts)", e);
        }
    }

    /**
     * Configures basic spot settings with renderer and aggregator Note: Full
     * drag-and-drop configuration of rows/columns is complex and may require
     * JavaScript execution or Actions class with precise coordinates
     */
    private void configureSpot(String rendererType, String aggregator, String attribute, String[] fields, String type) {
        waitForConfigurationToLoad();

        // Clear existing configuration first based on type
        if ("Accounts".equals(type)) {
            clearSpotsAccounts();
        } else {
            clearSpots();
        }

        System.out.println("  → Configuring spot with renderer: " + rendererType + ", aggregator: " + aggregator + ", attribute: " + attribute + ", fields: " + (fields != null ? String.join(", ", fields) : "none") + ", type: " + type);

        // Drag specified fields based on type
        if (fields != null) {
            for (String field : fields) {
                if ("Accounts".equals(type)) {
                    dragFieldToCols(field);
                } else {
                    dragFieldToRows(field);
                }
            }
        }

        // Wait and check if table rendered
        try {
            Thread.sleep(2000);
            boolean tableExists = driver.findElements(By.cssSelector(".pvtTable")).size() > 0;
            System.out.println("  → Pivot table exists after drag: " + tableExists);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (rendererType != null && !rendererType.isEmpty()) {
            selectRenderer(rendererType);
        }

        if (aggregator != null && !aggregator.isEmpty()) {
            selectAggregator(aggregator);
        }

        if (aggregator.equals("Sum") || aggregator.equals("Average")
                || aggregator.equals("Maximum") || aggregator.equals("Minimum")) {
            selectAttribute(aggregator, attribute);
        }

        // Additional wait for final render
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Trigger form snapshot to save configuration state
        // This JavaScript executes the same snapshot function used by the save buttons
        try {
            String result = (String) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "if (typeof snapshotForm === 'function' && document.getElementById('EditView')) {"
                    + "  snapshotForm(document.getElementById('EditView'));"
                    + "  return 'Snapshot executed';"
                    + "}"
                    + "return 'Snapshot function not found';"
            );
            System.out.println("  → " + result);
            Thread.sleep(1000); // Wait for snapshot to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while capturing configuration", e);
        }
    }

    /**
     * Verifies that the configuration table/chart is displayed
     */
    public boolean isConfigurationDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("pvtTable")));
            return driver.findElement(By.className("pvtTable")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void save() {
        try {
            WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(buttonSave));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", saveButton);
            Thread.sleep(300);

            try {
                wait.until(ExpectedConditions.elementToBeClickable(buttonSave)).click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                System.out.println("  Note: Using JavaScript click for Save button due to overlap");
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", saveButton);
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while saving", e);
        }
    }

    public void cancel() {
        driver.findElement(buttonCancel).click();
    }

    public void addInformationFromData(java.util.Map<String, String> data) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

            // Fill name
            fillInputFieldFromData("name", data.get("name"));

            // Select type (Area for Analysis)
            String type = data.get("type");
            if (type != null && !type.isEmpty()) {
                selectDropdown("type", type);
            }

            // Wait for configuration to load after selecting type
            Thread.sleep(3000); // Increased wait time for configuration to fully load

            // Parse fields array from JSON string
            String[] fields = null;
            String fieldsData = data.get("fields");
            if (fieldsData != null && !fieldsData.isEmpty()) {
                // Remove brackets and quotes, then split by comma
                fieldsData = fieldsData.replaceAll("[\\[\\]\"]", "").trim();
                if (!fieldsData.isEmpty()) {
                    fields = fieldsData.split("\\s*,\\s*");
                }
            }

            // Configure spot if configuration data provided
            if (data.get("renderer") != null || data.get("aggregator") != null || data.get("attribute") != null || fields != null) {
                configureSpot(data.get("renderer"), data.get("aggregator"), data.get("attribute"), fields, type);
            } else {
                // Even if no renderer/aggregator specified, wait for default configuration
                waitForConfigurationToLoad();
                System.out.println("  → Using default configuration without changes");
                Thread.sleep(1000);
            }

            System.out.println("  → Filled spot data: " + data.get("name") + " (" + data.get("type") + ")");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while adding information from data", e);
        }
    }

    private void fillInputFieldFromData(String fieldKey, String value) {
        if (value != null && !value.isEmpty()) {
            By locator = inputLocators.get(fieldKey);
            if (locator != null) {
                try {
                    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    element.clear();
                    element.sendKeys(value);
                } catch (Exception e) {
                    System.out.println("  Warning: Could not fill field " + fieldKey + ": " + e.getMessage());
                }
            }
        }
    }

    private void selectDropdown(String fieldKey, String value) {
        if (value != null && !value.isEmpty()) {
            By locator = inputLocators.get(fieldKey);
            if (locator != null) {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                Select dropdown = new Select(element);
                dropdown.selectByVisibleText(value);
            }
        }
    }

    public boolean isSpotSavedSuccessfully(String spotName) {
        try {
            By pageTitle = By.cssSelector(".moduleTitle h2");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println("Page title after save: " + title);
            System.out.println("Expected spot name: " + spotName);
            return title.toLowerCase().contains(spotName.toLowerCase());
        } catch (Exception e) {
            System.out.println("Error verifying spot save: " + e.getMessage());
            return false;
        }
    }

    public boolean hasValidationError() {
        try {
            return driver.findElements(By.className("validation-message")).size() > 0
                    || driver.findElements(By.className("error")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
