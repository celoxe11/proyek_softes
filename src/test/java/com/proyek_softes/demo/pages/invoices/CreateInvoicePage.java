package com.proyek_softes.demo.pages.invoices;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateInvoicePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private final By addGroupButton = By.id("addGroup");
    private final By selecttAccountButton = By.id("btn_billing_account");
    private final By selectContactButton = By.id("btn_billing_contact");

    private Map<String, By> inputLocators;

    public CreateInvoicePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();
        // Overview fields
        inputLocators.put("name", By.id("name"));
        inputLocators.put("quote_number", By.id("quote_number"));
        inputLocators.put("quote_date", By.id("quote_date"));
        inputLocators.put("due_date", By.id("due_date"));
        inputLocators.put("invoice_date", By.id("invoice_date"));
        inputLocators.put("assigned_user_name", By.id("assigned_user_name"));
        inputLocators.put("status", By.id("status"));
        inputLocators.put("description", By.id("description"));

        // Invoice To fields
        inputLocators.put("billing_account", By.id("billing_account"));
        inputLocators.put("billing_contact", By.id("billing_contact"));
        inputLocators.put("billing_address_street", By.id("billing_address_street"));
        inputLocators.put("billing_address_city", By.id("billing_address_city"));
        inputLocators.put("billing_address_state", By.id("billing_address_state"));
        inputLocators.put("billing_address_postalcode", By.id("billing_address_postalcode"));
        inputLocators.put("billing_address_country", By.id("billing_address_country"));
        inputLocators.put("shipping_address_street", By.id("shipping_address_street"));
        inputLocators.put("shipping_address_city", By.id("shipping_address_city"));
        inputLocators.put("shipping_address_state", By.id("shipping_address_state"));
        inputLocators.put("shipping_address_postalcode", By.id("shipping_address_postalcode"));
        inputLocators.put("shipping_address_country", By.id("shipping_address_country"));

        // Line Items summary fields (read-only, calculated fields)
        inputLocators.put("shipping_amount", By.id("shipping_amount"));
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

            // Overview section
            fillInputFieldFromData("name", data.get("name"));
            fillInputFieldFromData("quote_number", data.get("quote_number"));
            fillInputFieldFromData("quote_date", data.get("quote_date"));
            fillInputFieldFromData("due_date", data.get("due_date"));
            fillInputFieldFromData("invoice_date", data.get("invoice_date"));
            fillInputFieldFromData("assigned_user_name", data.get("assigned_user_name"));

            // Status dropdown
            String status = data.get("status");
            if (status != null && !status.isEmpty()) {
                selectDropdown("status", status);
            }

            fillInputFieldFromData("description", data.get("description"));

            // Invoice To section
            clickSelectAccount();
            if (data.get("billing_account") != null && !data.get("billing_account").isEmpty()) {
                selectFirstItem(data.get("billing_account"));
            } else {
                selectFirstItem();
            }

            clickSelectContact();
            if (data.get("billing_contact") != null && !data.get("billing_contact").isEmpty()) {
                selectFirstItem(data.get("billing_contact"));
            } else {
                selectFirstItem();
            }

            // Billing address
            fillInputFieldFromData("billing_address_street", data.get("billing_address_street"));
            fillInputFieldFromData("billing_address_city", data.get("billing_address_city"));
            fillInputFieldFromData("billing_address_state", data.get("billing_address_state"));
            fillInputFieldFromData("billing_address_postalcode", data.get("billing_address_postalcode"));
            fillInputFieldFromData("billing_address_country", data.get("billing_address_country"));

            // Shipping address
            fillInputFieldFromData("shipping_address_street", data.get("shipping_address_street"));
            fillInputFieldFromData("shipping_address_city", data.get("shipping_address_city"));
            fillInputFieldFromData("shipping_address_state", data.get("shipping_address_state"));
            fillInputFieldFromData("shipping_address_postalcode", data.get("shipping_address_postalcode"));
            fillInputFieldFromData("shipping_address_country", data.get("shipping_address_country"));

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
            System.out.println("  → Filled invoice data: " + data.get("name"));

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

    public boolean isInvoiceSavedSuccessfully(String invoiceName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println("Page title element located: " + pageTitle.toString());
            System.out.println("Current Page Title: " + title);
            System.out.println("Expected Invoice Name: " + invoiceName);
            return title.toLowerCase().contains(invoiceName.toLowerCase());
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

    public void clickSelectContact() {
        wait.until(ExpectedConditions.elementToBeClickable(selectContactButton)).click();
    }

    public void clickSelectAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(selecttAccountButton)).click();
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

            // Find and click the account name in the first row
            By firstAccountLink = By.cssSelector("table.list.view tbody tr:first-child td:first-child a");
            WebElement accountLink = wait.until(ExpectedConditions.elementToBeClickable(firstAccountLink));
            accountLink.click();

            // Wait for popup to close and switch back to main window
            wait.until(ExpectedConditions.numberOfWindowsToBe(1));
            driver.switchTo().window(mainWindow);

            // Wait a moment for the account name to populate
            Thread.sleep(500);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while selecting account name", e);
        }
    }

    // Overloaded method to select first account without specifying name
    public void selectFirstItem() {
        selectFirstItem(null);
    }

    /**
     * Adds service line items from JSON array string Expected format:
     * [{"name":"Service
     * 1","list_price":"100","discount":"10","discount_type":"Percentage","tax":"5.0"},...]
     */
    private void addServiceLines(String servicesJson) {
        try {
            // Parse JSON manually (simple approach)
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
                    Thread.sleep(800); // Wait for the new line to be created
                } else {
                    System.out.println("  → ✗ Add Service Line button not found!");
                }

                // Parse service fields
                Map<String, String> serviceData = parseJsonObject(service);

                // Fill service fields
                fillServiceLine(i, serviceData);
            }

            System.out.println("  → Added " + services.length + " service line(s)");

        } catch (Exception e) {
            System.out.println("  Warning: Could not add service lines: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Adds product line items from JSON array string Expected format:
     * [{"quantity":"2","name":"Product
     * 1","list_price":"50","discount":"5","discount_type":"Amount","tax":"7.5"},...]
     */
    private void addProductLines(String productsJson) {
        try {
            // Parse JSON manually (simple approach)
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
                    Thread.sleep(800); // Wait for the new line to be created
                } else {
                    System.out.println("  → ✗ Add Product Line button not found!");
                }

                // Parse product fields
                Map<String, String> productData = parseJsonObject(product);

                // Fill product fields
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
            System.out.println("    → Looking for service_name" + index);
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
            } else {
                System.out.println("    → ✗ Service name field not found or data missing");
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

            // Discount type (Percentage/Amount)
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

            // Tax (VAT)
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
            System.out.println("    → Looking for product_product_qty" + index);
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
            } else {
                System.out.println("    → ✗ Quantity field not found or data missing");
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

            // Discount type (Percentage/Amount)
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

            // Tax (VAT)
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
