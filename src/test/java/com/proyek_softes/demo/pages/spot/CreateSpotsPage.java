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
     * Waits for the configuration GUI (pivot table) to load after selecting type
     */
    public void waitForConfigurationToLoad() {
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
     * @param rendererType e.g., "Table", "Bar Chart", "Line Chart", "Pie Chart"
     */
    public void selectRenderer(String rendererType) {
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
     * @param aggregator e.g., "Count", "Sum", "Average", "Maximum", "Minimum"
     */
    public void selectAggregator(String aggregator) {
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
     * Drags a field from unused area to rows area using JavaScript
     * @param fieldName The name of the field to drag (e.g., "Account Name", "Opportunity Name")
     */
    public void dragFieldToRows(String fieldName) {
        try {
            String script = 
                "var unused = document.querySelector('.pvtUnused');" +
                "var rows = document.querySelector('.pvtRows');" +
                "if (!unused || !rows) {" +
                "  return 'ERROR: Containers not found';" +
                "}" +
                "var field = Array.from(unused.querySelectorAll('.pvtAttr')).find(function(el) { return el.textContent.includes('" + fieldName + "'); });" +
                "if (field && rows) {" +
                "  var li = field.closest('li');" +
                "  rows.appendChild(li);" +
                "  if (typeof $ !== 'undefined' && $(rows).sortable) {" +
                "    $(rows).sortable('refresh');" +
                "  }" +
                "  if (typeof c !== 'undefined' && c.pivot) {" +
                "    c.pivot();" +
                "  }" +
                "  return 'SUCCESS';" +
                "}" +
                "return 'ERROR: Field not found';";
            
            Object result = ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(script);
            System.out.println("  → Drag result for '" + fieldName + "': " + result);
            Thread.sleep(1500); // Wait for pivot table to update
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while dragging field", e);
        }
    }

    /**
     * Configures basic spot settings with renderer and aggregator
     * Note: Full drag-and-drop configuration of rows/columns is complex and may require
     * JavaScript execution or Actions class with precise coordinates
     */
    public void configureSpot(String rendererType, String aggregator) {
        waitForConfigurationToLoad();
        
        // Drag default fields to rows (Account Name and Opportunity Name)
        dragFieldToRows("Account Name");
        dragFieldToRows("Opportunity Name");
        
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
                "if (typeof snapshotForm === 'function' && document.getElementById('EditView')) {" +
                "  snapshotForm(document.getElementById('EditView'));" +
                "  return 'Snapshot executed';" +
                "}" +
                "return 'Snapshot function not found';"
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

            // Configure spot if configuration data provided
            if (data.get("renderer") != null || data.get("aggregator") != null) {
                configureSpot(data.get("renderer"), data.get("aggregator"));
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
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(spotName.toLowerCase());
        } catch (Exception e) {
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
