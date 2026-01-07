package com.proyek_softes.demo.pages.kb_categories;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class KBCategoriesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Navbar untuk navigasi ke KB Categories
    private final By navTab = By.id("grouptab_5");
    private final By subTab =
            By.xpath("//a[@href=\"index.php?module=AOK_Knowledge_Base_Categories&action=index&parentTab=All\"]");

    private final By createKBCategoryLink = By.xpath("//a[@data-action-name='Create']");
    private final By viewKBCategoryLink = By.xpath("//a[@data-action-name='List']");

    private final By firstRowKBCategoryName =
        By.cssSelector("table.list.view tbody tr:first-child td[type='name'] b a");

    private final By firstRowLocator =
        By.cssSelector("table.list.view tbody tr.oddListRowS1:first-child");

    private final By filterResult = By.className("msg");

    // filter locators
    private final By filterButton = By.xpath("//a[@title='Filter']");
    private final By quickFilterTab =
            By.xpath("//li[contains(@class, 'searchTabHandler') and contains(@class, 'basic')]/a");
    private final By modalContent = By.className("modal-content");

    private final By filterNameField = By.id("name_basic");
    private final By filterMyItemsCheckbox = By.id("current_user_only_basic");

    private final By filterSubmitButton = By.id("search_form_submit");
    private final By filterClearButton = By.id("search_form_clear");

    // detail page locators
    private final By tabActionsInDetail = By.id("tab-actions");
    private final By editButtonInDetail = By.id("edit_button");
    private final By deleteButtonInDetail = By.id("delete_button");

    private final By pageTitle = By.className("module-title-text");

    public KBCategoriesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    public boolean checkPageTitle(String expectedTitle) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
            String title = driver.findElement(pageTitle).getText();
            return title.toUpperCase().contains(expectedTitle.toUpperCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToKBCategoriesModule() {
        wait.until(ExpectedConditions.presenceOfElementLocated(navTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(navTab));
        actions.moveToElement(driver.findElement(navTab)).perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subTab));
        driver.findElement(subTab).click();
    }

    public void navigateToCreateKBCategory() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createKBCategoryLink));
        wait.until(ExpectedConditions.elementToBeClickable(createKBCategoryLink));
        driver.findElement(createKBCategoryLink).click();
    }

    public void navigateToViewKBCategory() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewKBCategoryLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewKBCategoryLink));
        driver.findElement(viewKBCategoryLink).click();
    }

    public boolean isInFirstRow(String kbCategoryName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("table.list.view")
            ));
            wait.until(ExpectedConditions.presenceOfElementLocated(firstRowKBCategoryName));
            WebElement firstRowElement = driver.findElement(firstRowKBCategoryName);
            String firstRowText = firstRowElement.getText().trim();
            
            // Clean up the text - remove extra whitespace and potential HTML entities
            firstRowText = firstRowText.replaceAll("\\s+", " ").trim();
            String cleanExpectedName = kbCategoryName.replaceAll("\\s+", " ").trim();
            
            System.out.println("First row text: '" + firstRowText + "'");
            System.out.println("Looking for: '" + cleanExpectedName + "'");
            
            // Try exact match first, then contains
            return firstRowText.equals(cleanExpectedName) || firstRowText.contains(cleanExpectedName);
        } catch (Exception e) {
            System.err.println("Error in isInFirstRow: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public WebElement getFirstRowNameLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowKBCategoryName));
        return driver.findElement(firstRowKBCategoryName);
    }

    public void clickFirstKBCategory() {
        getFirstRowNameLocator().click();
    }

    public WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowLocator));
        return driver.findElement(firstRowLocator);
    }

    public boolean isKBCategoryTitleCorrect(String kbCategoryName) {
        try {
            String title =
                    wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println("Page title: '" + title.toLowerCase() + "'");
            System.out.println("Expected: '" + kbCategoryName.toLowerCase() + "'");
            
            // Try exact match first, then contains
            return title.toLowerCase().equals(kbCategoryName.toLowerCase()) || 
                   title.toLowerCase().contains(kbCategoryName.toLowerCase());
        } catch (Exception e) {
            System.err.println("Error in isKBCategoryTitleCorrect: " + e.getMessage());
            return false;
        }
    }

    public void editKBCategory() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabActionsInDetail));
        driver.findElement(tabActionsInDetail).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonInDetail));
        driver.findElement(editButtonInDetail).click();
    }

    public void deleteKBCategory() {
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

        driver.findElement(filterNameField).clear();
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

        String nameFieldValue =
                driver.findElement(filterNameField).getAttribute("value").trim();
        boolean myItemsChecked =
                driver.findElement(filterMyItemsCheckbox).isSelected();

        if (nameFieldValue.isEmpty()
                && !myItemsChecked) {
            driver.findElement(filterSubmitButton).click();
            return;
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(filterClearButton));
        driver.findElement(filterClearButton).click();
        driver.findElement(filterSubmitButton).click();
    }
}
