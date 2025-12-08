# Landing Page Navigation Testing

Testing suite untuk menguji semua tombol navigasi dan sub-navigasi pada landing page website.

## 📁 Struktur File

```
src/test/java/com/proyek_softes/demo/landing/
├── BaseLandingTest.java              # Base class untuk landing page tests
├── LandingNavigationPage.java        # Page Object untuk navigasi landing page
├── LandingNavigationTest.java        # Test cases untuk navigasi
└── Navigations.java                  # (template)

testng-configs/
└── testng-landing-navigation.xml     # TestNG configuration untuk landing navigation
```

## 🎯 Menu yang Ditest

### 1. **About Menu**
Sub-menu items:
- About Us
- News & Press
- SuiteCRM Roadmap
- SuiteCRM Journey
- Careers
- Newsletter Sign-Up
- Contact Us

### 2. **Products Menu**
Sub-menu items:
- SuiteCRM
- SuiteOnDemand
- Pricing

### 3. **Services Menu**
Sub-menu items:
- Consulting
- Support
- Training
- Development

### 4. **Resources Menu**
Sub-menu items:
- Documentation
- Wiki
- Blog
- Videos
- Case Studies
- Downloads

### 5. **Community Menu**
Sub-menu items:
- Forums
- Contribute
- Extensions
- Partners

## 🚀 Cara Menjalankan Test

### 1. Update Base URL
Edit file `LandingNavigationTest.java` atau `BaseLandingTest.java` dan ubah URL:

```java
private String baseUrl = "https://suitecrm.com"; // Ganti dengan URL Anda
```

### 2. Jalankan Test

**Semua navigation tests:**
```powershell
mvn test "-DsuiteXmlFile=testng-configs/testng-landing-navigation.xml"
```

**Dengan specific browser:**
```powershell
# Chrome (default)
mvn test "-DsuiteXmlFile=testng-configs/testng-landing-navigation.xml"

# Firefox
mvn test "-DsuiteXmlFile=testng-configs/testng-landing-navigation.xml" "-Dbrowser=firefox"
```

## 📊 Test Cases

### Test Priority dan Coverage

| Priority | Test Name | Description |
|----------|-----------|-------------|
| 1 | `testMainMenuVisibility` | Verifikasi semua menu utama visible |
| 2 | `testAboutMainMenu` | Test klik menu About |
| 3 | `testAboutSubMenus` | Test semua sub-menu About |
| 4 | `testProductsMainMenu` | Test klik menu Products |
| 5 | `testProductsSubMenus` | Test semua sub-menu Products |
| 6 | `testServicesMainMenu` | Test klik menu Services |
| 7 | `testServicesSubMenus` | Test semua sub-menu Services |
| 8 | `testResourcesMainMenu` | Test klik menu Resources |
| 9 | `testResourcesSubMenus` | Test semua sub-menu Resources |
| 10 | `testCommunityMainMenu` | Test klik menu Community |
| 11 | `testCommunitySubMenus` | Test semua sub-menu Community |
| 12 | `testDropdownVisibility` | Test dropdown muncul saat hover |
| 13 | `testAllNavigationItems` | Comprehensive test semua navigasi |

## 🔧 Customisasi

### Menambah Sub-Menu Baru

Edit `LandingNavigationPage.java` di method `initializeSubMenus()`:

```java
// Contoh: Menambah sub-menu di About
Map<String, By> aboutMenu = new HashMap<>();
aboutMenu.put("aboutus", By.linkText("About Us"));
aboutMenu.put("newitem", By.linkText("New Menu Item")); // Tambah ini
```

### Mengubah Locator Strategy

Jika website menggunakan ID atau CSS selector berbeda:

```java
// Gunakan ID
mainMenuMap.put("about", By.id("menu-about"));

// Gunakan CSS selector
mainMenuMap.put("about", By.cssSelector(".nav-item.about"));

// Gunakan XPath
mainMenuMap.put("about", By.xpath("//nav//a[text()='About']"));
```

### Menyesuaikan Wait Time

Edit di constructor `LandingNavigationPage.java`:

```java
this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Ubah dari 10 ke 15
```

## 📈 Output Example

```
=== Starting Landing Page Navigation Tests ===

--- Testing Main Menu Visibility ---
✓ about menu is visible
✓ products menu is visible
✓ services menu is visible
✓ resources menu is visible
✓ community menu is visible

--- Testing About Sub-Menus ---
✓ Navigated to: about -> aboutus
✓ Navigated to: about -> news
✓ Navigated to: about -> roadmap
...

--- About Sub-Menu Summary ---
✓ Successful: 6/7
✓ Success Rate: 85.7%

========================================
FINAL NAVIGATION TEST SUMMARY
========================================
Total Tests: 24
✓ Successful: 20
❌ Failed: 4
Success Rate: 83.3%
========================================
```

## 🐛 Troubleshooting

### Issue: Element not found
**Solusi:** Update locator di `LandingNavigationPage.java` sesuai dengan HTML website Anda

### Issue: Dropdown tidak muncul
**Solusi:** Tambah delay atau ubah wait time:
```java
Thread.sleep(800); // Tambah dari 500 ms
```

### Issue: Test terlalu lambat
**Solusi:** 
1. Kurangi wait time
2. Disable screenshots
3. Gunakan headless mode

## 📝 Notes

- Test menggunakan **hover untuk dropdown** menu
- Setiap test kembali ke home page sebelum test berikutnya
- Success rate minimum: **50%** untuk setiap category
- Overall success rate minimum: **40%**

## 🔗 Related Files

- SuiteCRM Navigation: `NavigationModule.java`, `NavigationTest.java`
- Base Test: `BaseTest.java`
- Other landing tests: (tambah di sini)
