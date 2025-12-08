# Landing Page Navigation Testing

## Struktur HTML Navigation (dari Inspect Element)

Berdasarkan inspect element, struktur navigation adalah:

```html
<nav class="fusion-main-menu">
  <ul id="menu-new-menu-2024-test">
    <!-- About Menu -->
    <li id="menu-item-564732" class="menu-item">
      <a title="About" href="#">About</a>
      <ul class="sub-menu">
        <li id="menu-item-564390"><a href="/about-us/">About Us</a></li>
        <li id="menu-item-564388"><a href="/newsroom/">News & Press</a></li>
        <li id="menu-item-564373"><a href="/suitecrm-roadmap/">SuiteCRM Roadmap</a></li>
      </ul>
    </li>
    <!-- Other menus... -->
  </ul>
</nav>
```

## Locator Strategy

### Main Menu Items
- **Menggunakan CSS Selector dengan ID** untuk menu utama
- Contoh: `li#menu-item-564732 > a[title='About']`

### Sub-Menu Items
- **Menggunakan CSS Selector dengan ID dan href** untuk sub-menu
- Contoh: `li#menu-item-564390 a[href*='about-us']`

## File Structure

```
src/test/java/com/proyek_softes/demo/landing/
├── LandingNavigationPage.java    # Page Object dengan locators
└── LandingNavigationTest.java    # Test cases

testng-configs/
├── testng-landing-navigation.xml  # Run semua navigation tests
└── testng-landing-about.xml       # Run About menu tests saja
```

## Running Tests

### Test About Menu Saja (Quick Test)
```powershell
mvn test "-DsuiteXmlFile=testng-configs/testng-landing-about.xml"
```

### Test All Navigation
```powershell
mvn test "-DsuiteXmlFile=testng-configs/testng-landing-navigation.xml"
```

## Test Coverage

### Main Menus Tested:
1. ✅ About
2. ✅ Products
3. ✅ Services
4. ✅ Resources
5. ✅ Community

### Sub-Menu Navigation:
- Hover over main menu
- Wait for dropdown animation
- Click sub-menu item
- Verify URL navigation

## Troubleshooting

### Element Not Clickable
- Script menggunakan JavaScript click sebagai fallback
- Scroll into view otomatis
- Wait explicit untuk element visibility

### Dropdown Tidak Muncul
- Hover action dengan Actions class
- Wait 800ms untuk animation
- Verify dropdown visibility sebelum click

## Customization

Untuk menambah menu baru, edit di `LandingNavigationPage.java`:

```java
// Di initializeMainMenu()
mainMenuMap.put("newmenu", By.cssSelector("li#menu-item-XXXXX > a"));

// Di initializeSubMenus()
Map<String, By> newMenu = new HashMap<>();
newMenu.put("submenu1", By.cssSelector("li#menu-item-YYYYY a"));
subMenuMap.put("newmenu", newMenu);
```

## Current Status

✅ Compilation: Success  
⏳ Test Execution: Ready to run  
📝 Based on actual HTML structure from inspect element
