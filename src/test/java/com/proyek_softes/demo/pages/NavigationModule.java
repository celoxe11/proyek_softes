package com.proyek_softes.demo.pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NavigationModule {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    // Main navigation tabs
    private Map<String, By> navMap;
    
    // Nested map: parent tab -> (submenu name -> locator)
    private Map<String, Map<String, By>> subMenuMap;

    public NavigationModule(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        this.actions = new Actions(driver);

        // Initialize main navigation tabs
        this.navMap = new HashMap<>();
        navMap.put("sales", By.id("grouptab_0"));
        navMap.put("marketing", By.id("grouptab_1"));
        navMap.put("support", By.id("grouptab_2"));
        navMap.put("activities", By.id("grouptab_3"));
        navMap.put("collaboration", By.id("grouptab_4"));
        navMap.put("alltabs", By.id("grouptab_5"));
        
        // Initialize sub-menus
        initializeSubMenus();
    }
    
    private void initializeSubMenus() {
        subMenuMap = new HashMap<>();
        
        // Sales sub-menu - grouptab_0, parentTab=Sales
        Map<String, By> salesMenu = new HashMap<>();
        salesMenu.put("home", By.xpath("//a[contains(@href, 'parentTab=Sales') and contains(@href, 'module=Home')]"));
        salesMenu.put("accounts", By.xpath("//a[contains(@href, 'parentTab=Sales') and contains(@href, 'module=Accounts')]"));
        salesMenu.put("contacts", By.xpath("//a[contains(@href, 'parentTab=Sales') and contains(@href, 'module=Contacts')]"));
        salesMenu.put("opportunities", By.xpath("//a[contains(@href, 'parentTab=Sales') and contains(@href, 'module=Opportunities')]"));
        salesMenu.put("leads", By.xpath("//a[contains(@href, 'parentTab=Sales') and contains(@href, 'module=Leads')]"));
        subMenuMap.put("sales", salesMenu);
        
        // Marketing sub-menu - grouptab_1, parentTab=Marketing
        Map<String, By> marketingMenu = new HashMap<>();
        marketingMenu.put("home", By.xpath("//a[contains(@href, 'parentTab=Marketing') and contains(@href, 'module=Home')]"));
        marketingMenu.put("accounts", By.xpath("//a[contains(@href, 'parentTab=Marketing') and contains(@href, 'module=Accounts')]"));
        marketingMenu.put("contacts", By.xpath("//a[contains(@href, 'parentTab=Marketing') and contains(@href, 'module=Contacts')]"));
        marketingMenu.put("leads", By.xpath("//a[contains(@href, 'parentTab=Marketing') and contains(@href, 'module=Leads')]"));
        marketingMenu.put("campaigns", By.xpath("//a[contains(@href, 'parentTab=Marketing') and contains(@href, 'module=Campaigns')]"));
        marketingMenu.put("targets", By.xpath("//a[contains(@href, 'parentTab=Marketing') and contains(@href, 'module=Prospects')]"));
        marketingMenu.put("targetslists", By.xpath("//a[contains(@href, 'parentTab=Marketing') and contains(@href, 'module=ProspectLists')]"));
        subMenuMap.put("marketing", marketingMenu);
        
        // Support sub-menu - grouptab_2, parentTab=Support
        Map<String, By> supportMenu = new HashMap<>();
        supportMenu.put("home", By.xpath("//a[contains(@href, 'parentTab=Support') and contains(@href, 'module=Home')]"));
        supportMenu.put("accounts", By.xpath("//a[contains(@href, 'parentTab=Support') and contains(@href, 'module=Accounts')]"));
        supportMenu.put("contacts", By.xpath("//a[contains(@href, 'parentTab=Support') and contains(@href, 'module=Contacts')]"));
        supportMenu.put("cases", By.xpath("//a[contains(@href, 'parentTab=Support') and contains(@href, 'module=Cases')]"));
        subMenuMap.put("support", supportMenu);
        
        // Activities sub-menu - grouptab_3, parentTab=Activities
        Map<String, By> activitiesMenu = new HashMap<>();
        activitiesMenu.put("home", By.xpath("//a[contains(@href, 'parentTab=Activities') and contains(@href, 'module=Home')]"));
        activitiesMenu.put("calendar", By.xpath("//a[contains(@href, 'parentTab=Activities') and contains(@href, 'module=Calendar')]"));
        activitiesMenu.put("calls", By.xpath("//a[contains(@href, 'parentTab=Activities') and contains(@href, 'module=Calls')]"));
        activitiesMenu.put("meetings", By.xpath("//a[contains(@href, 'parentTab=Activities') and contains(@href, 'module=Meetings')]"));
        activitiesMenu.put("emails", By.xpath("//a[contains(@href, 'parentTab=Activities') and contains(@href, 'module=Emails')]"));
        activitiesMenu.put("tasks", By.xpath("//a[contains(@href, 'parentTab=Activities') and contains(@href, 'module=Tasks')]"));
        activitiesMenu.put("notes", By.xpath("//a[contains(@href, 'parentTab=Activities') and contains(@href, 'module=Notes')]"));
        subMenuMap.put("activities", activitiesMenu);
        
        // Collaboration sub-menu - grouptab_4, parentTab=Collaboration
        Map<String, By> collaborationMenu = new HashMap<>();
        collaborationMenu.put("home", By.xpath("//a[contains(@href, 'parentTab=Collaboration') and contains(@href, 'module=Home')]"));
        collaborationMenu.put("emails", By.xpath("//a[contains(@href, 'parentTab=Collaboration') and contains(@href, 'module=Emails')]"));
        collaborationMenu.put("documents", By.xpath("//a[contains(@href, 'parentTab=Collaboration') and contains(@href, 'module=Documents')]"));
        collaborationMenu.put("projects", By.xpath("//a[contains(@href, 'parentTab=Collaboration') and contains(@href, 'module=Project')]"));
        subMenuMap.put("collaboration", collaborationMenu);
        
        // All tabs sub-menu - grouptab_5, parentTab=All
        Map<String, By> allTabsMenu = new HashMap<>();
        allTabsMenu.put("home", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Home')]"));
        allTabsMenu.put("accounts", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Accounts')]"));
        allTabsMenu.put("contacts", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Contacts')]"));
        allTabsMenu.put("opportunities", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Opportunities')]"));
        allTabsMenu.put("leads", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Leads')]"));
        allTabsMenu.put("quotes", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=AOS_Quotes')]"));
        allTabsMenu.put("calendar", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Calendar')]"));
        allTabsMenu.put("documents", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Documents')]"));
        allTabsMenu.put("emails", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Emails')]"));
        allTabsMenu.put("spots", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Spots')]"));
        allTabsMenu.put("campaigns", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Campaigns')]"));
        allTabsMenu.put("calls", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Calls')]"));
        allTabsMenu.put("meetings", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Meetings')]"));
        allTabsMenu.put("tasks", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Tasks')]"));
        allTabsMenu.put("notes", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Notes')]"));
        allTabsMenu.put("invoices", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=AOS_Invoices')]"));
        allTabsMenu.put("contracts", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=AOS_Contracts')]"));
        allTabsMenu.put("cases", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Cases')]"));
        allTabsMenu.put("targets", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Prospects')]"));
        allTabsMenu.put("targetslists", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=ProspectLists')]"));
        allTabsMenu.put("projects", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Project') and not(contains(@href, 'Templates'))]"));
        allTabsMenu.put("projectstemplates", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=AM_ProjectTemplates')]"));
        allTabsMenu.put("events", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=FP_events')]"));
        allTabsMenu.put("locations", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=FP_Event_Locations')]"));
        allTabsMenu.put("products", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=AOS_Products') and not(contains(@href, 'Categories'))]"));
        allTabsMenu.put("productscategories", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=AOS_Product_Categories')]"));
        allTabsMenu.put("pdftemplates", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=AOS_PDF_Templates')]"));
        allTabsMenu.put("reports", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=AOR_Reports')]"));
        allTabsMenu.put("knowledgebase", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=AOK_KnowledgeBase') and not(contains(@href, 'Categories'))]"));
        allTabsMenu.put("kbcategories", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=AOK_Knowledge_Base_Categories')]"));
        allTabsMenu.put("emailtemplates", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=EmailTemplates')]"));
        allTabsMenu.put("surveys", By.xpath("//a[contains(@href, 'parentTab=All') and contains(@href, 'module=Surveys')]"));
        subMenuMap.put("alltabs", allTabsMenu);
    }

    // Hover to main navigation tab
    public void hoverToNav(String navName) {
        By navLocator = navMap.get(navName.toLowerCase().replace(" ", ""));

        if (navLocator == null) {
            throw new IllegalArgumentException("Invalid navigation name: " + navName +
                    ". Valid options: sales, marketing, support, activities, collaboration, alltabs");
        }

        WebElement element = driver.findElement(navLocator);
        actions.moveToElement(element).perform();
    }
    
    // Click on sub-menu item under a parent tab
    public void clickSubMenu(String parentTab, String subMenuItem) {
        String parent = parentTab.toLowerCase().replace(" ", "");
        String item = subMenuItem.toLowerCase().replace(" ", "").replace("-", "");
        
        Map<String, By> subMenu = subMenuMap.get(parent);
        if (subMenu == null) {
            throw new IllegalArgumentException("Invalid parent tab: " + parentTab);
        }
        
        By itemLocator = subMenu.get(item);
        if (itemLocator == null) {
            throw new IllegalArgumentException("Invalid sub-menu item '" + subMenuItem + 
                "' for parent tab '" + parentTab + "'");
        }
        
        try {
            // Scroll to top to ensure navigation bar is visible
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
            Thread.sleep(500);
            
            // Hover to parent tab first to reveal dropdown
            hoverToNav(parent);
            
            // Wait longer for dropdown to fully appear and be interactive
            Thread.sleep(800);
            
            // Wait for element to be present in DOM
            wait.until(ExpectedConditions.presenceOfElementLocated(itemLocator));
            
            // Get the element
            WebElement element = driver.findElement(itemLocator);
            
            // Hover over the submenu item to highlight it before clicking
            actions.moveToElement(element).perform();
            
            // Pause to show which submenu item is being hovered
            Thread.sleep(600);
            
            // Use JavaScript click for more reliability (bypasses visibility checks)
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click menu item: " + parentTab + " -> " + subMenuItem + 
                ". Error: " + e.getMessage(), e);
        }
    }
    
    // Navigate directly: hover parent and click sub-menu in one call
    public void navigateTo(String parentTab, String subMenuItem) {
        clickSubMenu(parentTab, subMenuItem);
    }

    public Map<String, By> getNavMap() {
        return navMap;
    }

    public Map<String, Map<String, By>> getSubMenuMap() {
        return subMenuMap;
    }
    
    
}
