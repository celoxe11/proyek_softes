package com.proyek_softes.demo.pages.contracts;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateContractPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private final By addGroupButton = By.id("addGroup");
    private final By selectAccountButton = By.id("btn_contract_account");
    private final By selectContactButton = By.id("btn_contact");
    private final By selectOpportunityButton = By.id("btn_opportunity");

    private Map<String, By> inputLocators;

    public CreateContractPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();
        // Basic fields
        inputLocators.put("name", By.id("name"));
        inputLocators.put("status", By.id("status"));
        inputLocators.put("total_contract_value", By.id("total_contract_value"));
        inputLocators.put("assigned_user_name", By.id("assigned_user_name"));
        inputLocators.put("start_date", By.id("start_date"));
        inputLocators.put("contract_account", By.id("contract_account"));
        inputLocators.put("end_date", By.id("end_date"));
        inputLocators.put("contact", By.id("contact"));
        inputLocators.put("renewal_reminder_date", By.id("renewal_reminder_date"));
        inputLocators.put("opportunity", By.id("opportunity"));
        inputLocators.put("customer_signed_date", By.id("customer_signed_date"));
        inputLocators.put("contract_type", By.id("contract_type"));
        inputLocators.put("company_signed_date", By.id("company_signed_date"));
        inputLocators.put("description", By.id("description"));

        // Line Items fields
        inputLocators.put("shipping_amount", By.id("shipping_amount"));
        inputLocators.put("shipping_tax_amt", By.id("shipping_tax_amt"));
        inputLocators.put("total_amt", By.id("total_amt"));
        inputLocators.put("discount_amount", By.id("discount_amount"));
        inputLocators.put("subtotal_amount", By.id("subtotal_amount"));
        inputLocators.put("tax_amount", By.id("tax_amount"));
        inputLocators.put("total_amount", By.id("total_amount"));
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

            // Basic section
            fillInputFieldFromData("name", data.get("name"));

            // Status dropdown
            String status = data.get("status");
            if (status != null && !status.isEmpty()) {
                selectDropdown("status", status);
            }

            fillInputFieldFromData("total_contract_value", data.get("total_contract_value"));
            fillInputFieldFromData("assigned_user_name", data.get("assigned_user_name"));
            fillInputFieldFromData("start_date", data.get("start_date"));

            // Select Account (required)
            clickSelectAccount();
            if (data.get("contract_account") != null && !data.get("contract_account").isEmpty()) {
                selectFirstItem(data.get("contract_account"));
            } else {
                selectFirstItem();
            }

            fillInputFieldFromData("end_date", data.get("end_date"));

            // Select Contact
            clickSelectContact();
            if (data.get("contact") != null && !data.get("contact").isEmpty()) {
                selectFirstItem(data.get("contact"));
            } else {
                selectFirstItem();
            }

            fillInputFieldFromData("renewal_reminder_date", data.get("renewal_reminder_date"));

            // Select Opportunity (if provided)
            if (data.get("opportunity") != null && !data.get("opportunity").isEmpty()) {
                clickSelectOpportunity();
                selectFirstItem(data.get("opportunity"));
            }

            fillInputFieldFromData("customer_signed_date", data.get("customer_signed_date"));

            // Contract Type dropdown
            String contractType = data.get("contract_type");
            if (contractType != null && !contractType.isEmpty()) {
                selectDropdown("contract_type", contractType);
            }

            fillInputFieldFromData("company_signed_date", data.get("company_signed_date"));
            fillInputFieldFromData("description", data.get("description"));

            // Add line items group
            driver.findElement(addGroupButton).click();
            Thread.sleep(1000); // Wait for group to be added and rendered

            // Add service lines if provided
            String servicesJson = data.get("services");
            System.out.println("Services JSON: " + servicesJson);
            if (servicesJson != null && !servicesJson.isEmpty()) {
                addServiceLines(servicesJson);
            }

            // Add product lines if provided
            String productsJson = data.get("products");
            System.out.println("Products JSON: " + productsJson);
            if (productsJson != null && !productsJson.isEmpty()) {
                addProductLines(productsJson);
            }

            // Line Items section - shipping amount can be filled
            fillInputFieldFromData("shipping_amount", data.get("shipping_amount"));

            Thread.sleep(500);
            System.out.println("  → Filled contract data: " + data.get("name"));

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

    public boolean isContractSavedSuccessfully(String contractName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(contractName.toLowerCase());
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

    public void clickSelectAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(selectAccountButton)).click();
    }

    public void clickSelectContact() {
        wait.until(ExpectedConditions.elementToBeClickable(selectContactButton)).click();
    }

    public void clickSelectOpportunity() {
        wait.until(ExpectedConditions.elementToBeClickable(selectOpportunityButton)).click();
    }

    public void selectFirstItem(String name) {
        try {
            // Store the current window handle
            String mainWindow = driver.getWindowHandle();

            // Wait for the popup window to appear
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            // Switch to the popup window
            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(mainWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            // Wait for the table to load in the popup
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.list.view")));

            // Find and click the first item in the table
            By firstItemLink = By.cssSelector("table.list.view tbody tr:first-child td:first-child a");
            WebElement itemLink = wait.until(ExpectedConditions.elementToBeClickable(firstItemLink));
            itemLink.click();

            // Wait for popup to close and switch back to main window
            wait.until(ExpectedConditions.numberOfWindowsToBe(1));
            driver.switchTo().window(mainWindow);

            // Wait a moment for the field to populate
            Thread.sleep(500);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while selecting item", e);
        }
    }

    // Overloaded method to select first item without specifying name
    public void selectFirstItem() {
        selectFirstItem(null);
    }

    /**
     * Adds service line items from JSON array string
     */
    private void addServiceLines(String servicesJson) {
        try {
            servicesJson = servicesJson.trim();
            if (!servicesJson.startsWith("[") || !servicesJson.endsWith("]")) {
                return;
            }

            String[] services = servicesJson.substring(1, servicesJson.length() - 1).split("\\},\\{");
            System.out.println("  → Parsed " + services.length + " service(s) from JSON");

            for (int i = 0; i < services.length; i++) {
                String service = services[i].replaceAll("[\\[\\]\\{\\}]", "");

                // ALWAYS click Add Service Line button to create the fields
                System.out.println("  → Clicking Add Service Line button for service " + i);
                By addServiceButton = By.cssSelector("input.add_service_line");
                if (driver.findElements(addServiceButton).size() > 0) {
                    WebElement addButton = driver.findElement(addServiceButton);
                    ((org.openqa.selenium.JavascriptExecutor) driver)
                            .executeScript("arguments[0].scrollIntoView({block: 'center'});", addButton);
                    Thread.sleep(200);
                    addButton.click();
                    System.out.println("  → Clicked Add Service Line, waiting for fields to appear...");
                    Thread.sleep(800);
                } else {
                    System.out.println("  → ✗ Add Service Line button not found!");
                }

                Map<String, String> serviceData = parseJsonObject(service);
                fillServiceLine(i, serviceData);
            }

            System.out.println("  → Added " + services.length + " service line(s)");

        } catch (Exception e) {
            System.out.println("  Warning: Could not add service lines: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Adds product line items from JSON array string
     */
    private void addProductLines(String productsJson) {
        try {
            productsJson = productsJson.trim();
            if (!productsJson.startsWith("[") || !productsJson.endsWith("]")) {
                return;
            }

            String[] products = productsJson.substring(1, productsJson.length() - 1).split("\\},\\{");
            System.out.println("  → Parsed " + products.length + " product(s) from JSON");

            for (int i = 0; i < products.length; i++) {
                String product = products[i].replaceAll("[\\[\\]\\{\\}]", "");

                // ALWAYS click Add Product Line button to create the fields
                System.out.println("  → Clicking Add Product Line button for product " + i);
                By addProductButton = By.cssSelector("input.add_product_line");
                if (driver.findElements(addProductButton).size() > 0) {
                    WebElement addButton = driver.findElement(addProductButton);
                    ((org.openqa.selenium.JavascriptExecutor) driver)
                            .executeScript("arguments[0].scrollIntoView({block: 'center'});", addButton);
                    Thread.sleep(200);
                    addButton.click();
                    System.out.println("  → Clicked Add Product Line, waiting for fields to appear...");
                    Thread.sleep(800);
                } else {
                    System.out.println("  → ✗ Add Product Line button not found!");
                }

                Map<String, String> productData = parseJsonObject(product);
                fillProductLine(i, productData);
            }

            System.out.println("  → Added " + products.length + " product line(s)");

        } catch (Exception e) {
            System.out.println("  Warning: Could not add product lines: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Simple JSON object parser for key-value pairs
     */
    private Map<String, String> parseJsonObject(String json) {
        Map<String, String> result = new HashMap<>();
        String[] pairs = json.split(",");

        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].replaceAll("[\"'\\s]", "");
                String value = keyValue[1].replaceAll("[\"']", "").trim();
                result.put(key, value);
            }
        }

        return result;
    }

    /**
     * Fills a service line with data
     */
    private void fillServiceLine(int index, Map<String, String> data) {
        try {
            System.out.println("  → Filling service line " + index + " with data: " + data);

            // Service name
            By serviceName = By.id("service_name" + index);
            if (driver.findElements(serviceName).size() > 0 && data.containsKey("name")) {
                try {
                    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(serviceName));
                    ((org.openqa.selenium.JavascriptExecutor) driver)
                            .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                    Thread.sleep(200);
                    element.clear();
                    element.sendKeys(data.get("name"));
                    System.out.println("    → ✓ Filled service name: " + data.get("name"));
                } catch (Exception e) {
                    System.out.println("    → ✗ Failed to fill service name: " + e.getMessage());
                }
            }

            // List price
            By listPrice = By.id("service_product_list_price" + index);
            if (driver.findElements(listPrice).size() > 0 && data.containsKey("list_price")) {
                try {
                    WebElement element = driver.findElement(listPrice);
                    element.clear();
                    element.sendKeys(data.get("list_price"));
                    System.out.println("    → ✓ Filled list price: " + data.get("list_price"));
                } catch (Exception e) {
                    System.out.println("    → ✗ Failed to fill list price: " + e.getMessage());
                }
            }

            // Discount
            By discount = By.id("service_product_discount" + index);
            if (driver.findElements(discount).size() > 0 && data.containsKey("discount")) {
                try {
                    WebElement element = driver.findElement(discount);
                    element.clear();
                    element.sendKeys(data.get("discount"));
                    System.out.println("    → ✓ Filled discount: " + data.get("discount"));
                } catch (Exception e) {
                    System.out.println("    → ✗ Failed to fill discount: " + e.getMessage());
                }
            }

            // Discount type
            By discountType = By.id("service_discount" + index);
            if (driver.findElements(discountType).size() > 0 && data.containsKey("discount_type")) {
                try {
                    Select select = new Select(driver.findElement(discountType));
                    select.selectByVisibleText(data.get("discount_type"));
                    System.out.println("    → ✓ Selected discount type: " + data.get("discount_type"));
                } catch (Exception e) {
                    System.out.println("    → ✗ Failed to select discount type: " + e.getMessage());
                }
            }

            // Tax
            By tax = By.id("service_vat" + index);
            if (driver.findElements(tax).size() > 0 && data.containsKey("tax")) {
                try {
                    Select select = new Select(driver.findElement(tax));
                    select.selectByValue(data.get("tax"));
                    System.out.println("    → ✓ Selected tax: " + data.get("tax"));
                } catch (Exception e) {
                    System.out.println("    → ✗ Failed to select tax: " + e.getMessage());
                }
            }

            Thread.sleep(300);
        } catch (Exception e) {
            System.out.println("  ✗ ERROR filling service line " + index + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Fills a product line with data
     */
    private void fillProductLine(int index, Map<String, String> data) {
        try {
            System.out.println("  → Filling product line " + index + " with data: " + data);

            // Quantity
            By quantity = By.id("product_product_qty" + index);
            if (driver.findElements(quantity).size() > 0 && data.containsKey("quantity")) {
                try {
                    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(quantity));
                    ((org.openqa.selenium.JavascriptExecutor) driver)
                            .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                    Thread.sleep(200);
                    element.clear();
                    element.sendKeys(data.get("quantity"));
                    System.out.println("    → ✓ Filled quantity: " + data.get("quantity"));
                } catch (Exception e) {
                    System.out.println("    → ✗ Failed to fill quantity: " + e.getMessage());
                }
            }

            // Product name
            By productName = By.id("product_name" + index);
            if (driver.findElements(productName).size() > 0 && data.containsKey("name")) {
                try {
                    WebElement element = driver.findElement(productName);
                    element.clear();
                    element.sendKeys(data.get("name"));
                    System.out.println("    → ✓ Filled product name: " + data.get("name"));
                } catch (Exception e) {
                    System.out.println("    → ✗ Failed to fill product name: " + e.getMessage());
                }
            }

            // Part number
            By partNumber = By.id("product_part_number" + index);
            if (driver.findElements(partNumber).size() > 0 && data.containsKey("part_number")) {
                try {
                    WebElement element = driver.findElement(partNumber);
                    element.clear();
                    element.sendKeys(data.get("part_number"));
                    System.out.println("    → ✓ Filled part number: " + data.get("part_number"));
                } catch (Exception e) {
                    System.out.println("    → ✗ Failed to fill part number: " + e.getMessage());
                }
            }

            // List price
            By listPrice = By.id("product_product_list_price" + index);
            if (driver.findElements(listPrice).size() > 0 && data.containsKey("list_price")) {
                try {
                    WebElement element = driver.findElement(listPrice);
                    element.clear();
                    element.sendKeys(data.get("list_price"));
                    System.out.println("    → ✓ Filled list price: " + data.get("list_price"));
                } catch (Exception e) {
                    System.out.println("    → ✗ Failed to fill list price: " + e.getMessage());
                }
            }

            // Discount
            By discount = By.id("product_product_discount" + index);
            if (driver.findElements(discount).size() > 0 && data.containsKey("discount")) {
                try {
                    WebElement element = driver.findElement(discount);
                    element.clear();
                    element.sendKeys(data.get("discount"));
                    System.out.println("    → ✓ Filled discount: " + data.get("discount"));
                } catch (Exception e) {
                    System.out.println("    → ✗ Failed to fill discount: " + e.getMessage());
                }
            }

            // Discount type
            By discountType = By.id("product_discount" + index);
            if (driver.findElements(discountType).size() > 0 && data.containsKey("discount_type")) {
                try {
                    Select select = new Select(driver.findElement(discountType));
                    select.selectByVisibleText(data.get("discount_type"));
                    System.out.println("    → ✓ Selected discount type: " + data.get("discount_type"));
                } catch (Exception e) {
                    System.out.println("    → ✗ Failed to select discount type: " + e.getMessage());
                }
            }

            // Tax
            By tax = By.id("product_vat" + index);
            if (driver.findElements(tax).size() > 0 && data.containsKey("tax")) {
                try {
                    Select select = new Select(driver.findElement(tax));
                    select.selectByValue(data.get("tax"));
                    System.out.println("    → ✓ Selected tax: " + data.get("tax"));
                } catch (Exception e) {
                    System.out.println("    → ✗ Failed to select tax: " + e.getMessage());
                }
            }

            // Description
            By description = By.id("product_description" + index);
            if (driver.findElements(description).size() > 0 && data.containsKey("description")) {
                try {
                    WebElement element = driver.findElement(description);
                    element.clear();
                    element.sendKeys(data.get("description"));
                    System.out.println("    → ✓ Filled description: " + data.get("description"));
                } catch (Exception e) {
                    System.out.println("    → ✗ Failed to fill description: " + e.getMessage());
                }
            }

            // Item description
            By itemDescription = By.id("product_item_description" + index);
            if (driver.findElements(itemDescription).size() > 0 && data.containsKey("item_description")) {
                try {
                    WebElement element = driver.findElement(itemDescription);
                    element.clear();
                    element.sendKeys(data.get("item_description"));
                    System.out.println("    → ✓ Filled item description: " + data.get("item_description"));
                } catch (Exception e) {
                    System.out.println("    → ✗ Failed to fill item description: " + e.getMessage());
                }
            }

            Thread.sleep(300);
        } catch (Exception e) {
            System.out.println("  ✗ ERROR filling product line " + index + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
