# Test Automation Framework - Documentation

## Daftar Isi
- [Arsitektur Test](#arsitektur-test)
- [GenericCrudTestHelper](#genericcrudtesthelper)
- [Struktur Test Class](#struktur-test-class)
- [Cara Menggunakan](#cara-menggunakan)
- [Contoh Implementasi](#contoh-implementasi)

---

## Arsitektur Test

Framework test automation ini dibangun dengan prinsip **DRY (Don't Repeat Yourself)** untuk mengeliminasi duplikasi kode dan meningkatkan maintainability. Struktur utama terdiri dari:

### 1. BaseTest
Class dasar yang menyediakan:
- Setup dan teardown WebDriver
- Login functionality
- Screenshot utilities
- Konfigurasi browser

### 2. GenericCrudTestHelper
Class generik yang menyediakan reusable methods untuk operasi CRUD (Create, Read, Update, Delete) pada semua entity test.

### 3. Entity Test Classes
Test classes spesifik untuk setiap entity yang meng-extend `GenericCrudTestHelper`:
- `AccountTest`
- `CallTest`
- `ContactTest`
- `LeadTest`
- `OpportunityTest`
- `QuoteTest`
- `SpotTest`
- `TaskTest`

---

## GenericCrudTestHelper

### Konsep
`GenericCrudTestHelper` adalah abstract class yang menggunakan **Generic Types** dan **Functional Programming** untuk menyediakan template methods yang dapat digunakan ulang untuk semua entity tests.

### Generic Types
```java
public abstract class GenericCrudTestHelper<ListPage, CreatePage> extends BaseTest
```
- `ListPage`: Type untuk page object yang menampilkan list/view entity
- `CreatePage`: Type untuk page object yang digunakan untuk create/edit entity

### Method Utama

#### 1. testCreateEntity()
Method untuk testing pembuatan entity baru.

**Workflow:**
1. Login ke aplikasi
2. Navigate ke module entity
3. Navigate ke halaman create
4. Isi form dengan data dari data provider
5. Save entity
6. Verifikasi entity tersimpan dengan sukses
7. Ambil screenshot
8. Navigate ke halaman view/list
9. Verifikasi entity muncul di baris pertama
10. Ambil screenshot dari list

**Parameters:**
- `testData`: Data test dari data provider
- `listPageSupplier`: Function untuk membuat instance list page object
- `navigateToModule`: Method untuk navigate ke module (menggunakan method reference)
- `navigateToCreate`: Method untuk navigate ke halaman create
- `createPageSupplier`: Function untuk membuat instance create page object
- `fillForm`: BiConsumer untuk mengisi form dengan data test
- `saveMethod`: Method untuk save form
- `entityNameExtractor`: Function untuk extract nama entity dari test data
- `isEntitySaved`: Function untuk verifikasi entity tersimpan
- `screenshotName`: Base nama untuk screenshot
- `navigateToView`: Method untuk navigate ke view/list page
- `isInFirstRow`: Function untuk verifikasi entity ada di baris pertama
- `getFirstRowLocator`: Function untuk mendapatkan locator baris pertama

**Contoh Penggunaan:**
```java
testCreateEntity(
    testData,
    v -> accountsPage,                                  // List page supplier
    accountsPage::navigateToAccountsModule,             // Navigate to module
    accountsPage::navigateToCreateAccount,              // Navigate to create
    v -> createAccountsPage,                            // Create page supplier
    (page, data) -> page.addInformationFromData(data), // Fill form
    createAccountsPage::save,                           // Save method
    data -> data.get("name"),                           // Extract entity name
    createAccountsPage::isAccountSavedSuccessfully,     // Verify saved
    "DEM-004",                                          // Screenshot name
    accountsPage::navigateToViewAccounts,               // Navigate to view
    accountsPage::isInFirstRow,                         // Check first row
    v -> accountsPage.getFirstRowLocator()              // Get row locator
);
```

#### 2. testViewEntity()
Method untuk testing melihat detail entity.

**Workflow:**
1. Login ke aplikasi
2. Navigate ke module entity
3. Navigate ke halaman view/list
4. Klik entity pertama di list
5. Tunggu page load (2 detik)
6. Verifikasi berada di detail page yang benar
7. Ambil screenshot

**Parameters:**
- `testData`: Data test dari data provider
- `listPageSupplier`: Function untuk membuat instance list page object
- `navigateToModule`: Method untuk navigate ke module
- `navigateToView`: Method untuk navigate ke view/list page
- `clickFirst`: Method untuk klik entity pertama
- `entityNameExtractor`: Function untuk extract nama entity dari test data
- `isTitleCorrect`: Function untuk verifikasi title di detail page
- `screenshotName`: Base nama untuk screenshot

**Contoh Penggunaan:**
```java
testViewEntity(
    testData,
    v -> accountsPage,                           // List page supplier
    accountsPage::navigateToAccountsModule,      // Navigate to module
    accountsPage::navigateToViewAccounts,        // Navigate to view
    accountsPage::clickFirstAccount,             // Click first entity
    data -> data.get("name"),                    // Extract entity name
    accountsPage::isAccountTitleCorrect,         // Verify title
    "DEM-005"                                    // Screenshot name
);
```

#### 3. testEditEntity()
Method untuk testing edit entity yang sudah ada.

**Workflow:**
1. Login ke aplikasi
2. Navigate ke module entity
3. Navigate ke halaman view/list
4. Klik entity pertama
5. Tunggu page load (2 detik)
6. Verifikasi berada di detail page (sebelum edit)
7. Ambil screenshot "before edit"
8. Klik tombol edit
9. Isi form dengan data baru
10. Save perubahan
11. Verifikasi entity tersimpan dengan sukses
12. Ambil screenshot "after edit"

**Parameters:**
- `testData`: Data test dari data provider
- `listPageSupplier`: Function untuk membuat instance list page object
- `navigateToModule`: Method untuk navigate ke module
- `navigateToView`: Method untuk navigate ke view/list page
- `clickFirst`: Method untuk klik entity pertama
- `entityNameBeforeEditExtractor`: Function untuk extract nama entity sebelum edit
- `isTitleCorrect`: Function untuk verifikasi title di detail page
- `screenshotNameBefore`: Nama screenshot sebelum edit
- `editMethod`: Method untuk klik tombol edit
- `createPageSupplier`: Function untuk membuat instance edit page object
- `fillForm`: BiConsumer untuk mengisi form dengan data baru
- `saveMethod`: Method untuk save form
- `entityNameExtractor`: Function untuk extract nama entity setelah edit
- `isEntitySaved`: Function untuk verifikasi entity tersimpan
- `screenshotNameAfter`: Base nama untuk screenshot setelah edit

**Contoh Penggunaan:**
```java
testEditEntity(
    testData,
    v -> accountsPage,                                  // List page supplier
    accountsPage::navigateToAccountsModule,             // Navigate to module
    accountsPage::navigateToViewAccounts,               // Navigate to view
    accountsPage::clickFirstAccount,                    // Click first entity
    data -> data.get("name_before_edit"),               // Name before edit
    accountsPage::isAccountTitleCorrect,                // Verify title
    "DEM-006_View_Account_Detail",                      // Screenshot before
    accountsPage::editAccount,                          // Click edit button
    v -> editAccountsPage,                              // Edit page supplier
    (page, data) -> page.addInformationFromData(data), // Fill form
    editAccountsPage::save,                             // Save method
    data -> data.get("name"),                           // Name after edit
    editAccountsPage::isAccountSavedSuccessfully,       // Verify saved
    "DEM-006"                                           // Screenshot after
);
```

---

## Struktur Test Class

### Template Structure
```java
public class EntityTest extends GenericCrudTestHelper<EntityPage, CreateEntityPage> {
    
    @Test(dataProvider = "createEntityData", dataProviderClass = EntityDataProvider.class)
    @Description("TEST-ID")
    public void testCreateEntity(Map<String, String> testData) {
        // Initialize page objects
        EntityPage entityPage = new EntityPage(driver);
        CreateEntityPage createEntityPage = new CreateEntityPage(driver, wait);
        
        // Call generic create method
        testCreateEntity(
            testData,
            v -> entityPage,
            entityPage::navigateToModule,
            // ... other parameters
        );
    }
    
    @Test(dataProvider = "viewEntityData", dataProviderClass = EntityDataProvider.class)
    @Description("TEST-ID")
    public void testViewEntity(Map<String, String> testData) {
        // Similar structure for view test
    }
    
    @Test(dataProvider = "editEntityData", dataProviderClass = EntityDataProvider.class)
    @Description("TEST-ID")
    public void testEditEntity(Map<String, String> testData) {
        // Similar structure for edit test
    }
}
```

### Keuntungan Struktur Ini

1. **Code Reusability**: Logic test yang sama tidak perlu ditulis berulang
2. **Consistency**: Semua test mengikuti pattern yang sama
3. **Maintainability**: Perubahan logic hanya perlu dilakukan di satu tempat
4. **Readability**: Test intent lebih jelas dengan declarative style
5. **Type Safety**: Dengan generic types, type checking dilakukan saat compile time

---

## Cara Menggunakan

### 1. Membuat Test Class Baru

```java
public class NewEntityTest extends GenericCrudTestHelper<NewEntityPage, CreateNewEntityPage> {
    
    @Test(dataProvider = "createNewEntityData", dataProviderClass = NewEntityDataProvider.class)
    @Description("DEM-XXX")
    public void testCreateNewEntity(Map<String, String> testData) {
        NewEntityPage entityPage = new NewEntityPage(driver);
        CreateNewEntityPage createPage = new CreateNewEntityPage(driver, wait);
        
        testCreateEntity(
            testData,
            v -> entityPage,
            entityPage::navigateToNewEntityModule,
            entityPage::navigateToCreateNewEntity,
            v -> createPage,
            (page, data) -> page.addInformationFromData(data),
            createPage::save,
            data -> data.get("name"),
            createPage::isNewEntitySavedSuccessfully,
            "DEM-XXX",
            entityPage::navigateToViewNewEntity,
            entityPage::isInFirstRow,
            v -> entityPage.getFirstRowLocator()
        );
    }
}
```

### 2. Membuat Page Object

Pastikan page object memiliki methods yang diperlukan:
```java
public class NewEntityPage {
    public void navigateToNewEntityModule() { }
    public void navigateToCreateNewEntity() { }
    public void navigateToViewNewEntity() { }
    public void clickFirstNewEntity() { }
    public boolean isNewEntityTitleCorrect(String name) { }
    public boolean isInFirstRow(String name) { }
    public WebElement getFirstRowLocator() { }
    public void editNewEntity() { }
}

public class CreateNewEntityPage {
    public void addInformationFromData(Map<String, String> data) { }
    public void save() { }
    public boolean isNewEntitySavedSuccessfully(String name) { }
}
```

### 3. Membuat Data Provider

```java
public class NewEntityDataProvider extends BaseDataProvider {
    private static final String ENTITY_FOLDER = "new_entity_demo";
    
    @DataProvider(name = "createNewEntityData")
    public static Object[][] getCreateNewEntityData() {
        return getTestData(ENTITY_FOLDER, "create");
    }
    
    @DataProvider(name = "viewNewEntityData")
    public static Object[][] getViewNewEntityData() {
        return getTestData(ENTITY_FOLDER, "view");
    }
    
    @DataProvider(name = "editNewEntityData")
    public static Object[][] getEditNewEntityData() {
        return getTestData(ENTITY_FOLDER, "edit");
    }
}
```

---

## Contoh Implementasi

### TaskTest.java
```java
public class TaskTest extends GenericCrudTestHelper<TasksPage, CreateTaskPage> {

    @Test(dataProvider = "createTaskData", dataProviderClass = TaskDataProvider.class)
    @Description("DEM-070")
    public void testDem070(Map<String, String> testData) {
        TasksPage tasksPage = new TasksPage(driver);
        CreateTaskPage createTaskPage = new CreateTaskPage(driver, wait);
        
        testCreateEntity(
            testData,
            v -> tasksPage,
            tasksPage::navigateToTasksModule,
            tasksPage::navigateToCreateTask,
            v -> createTaskPage,
            (page, data) -> page.addInformationFromData(data),
            createTaskPage::save,
            data -> data.get("name"),
            createTaskPage::isTaskSavedSuccessfully,
            "DEM-070",
            tasksPage::navigateToViewTask,
            tasksPage::isInFirstRow,
            v -> tasksPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewTaskData", dataProviderClass = TaskDataProvider.class)
    @Description("DEM-071")
    public void testDem071(Map<String, String> testData) {
        TasksPage tasksPage = new TasksPage(driver);
        
        testViewEntity(
            testData,
            v -> tasksPage,
            tasksPage::navigateToTasksModule,
            tasksPage::navigateToViewTask,
            tasksPage::clickFirstTask,
            data -> data.get("name"),
            tasksPage::isTaskTitleCorrect,
            "DEM-071"
        );
    }

    @Test(dataProvider = "editTaskData", dataProviderClass = TaskDataProvider.class)
    @Description("DEM-072")
    public void testDem072(Map<String, String> testData) {
        TasksPage tasksPage = new TasksPage(driver);
        CreateTaskPage editTaskPage = new CreateTaskPage(driver, wait);
        
        testEditEntity(
            testData,
            v -> tasksPage,
            tasksPage::navigateToTasksModule,
            tasksPage::navigateToViewTask,
            tasksPage::clickFirstTask,
            data -> data.get("nameBeforeEdit"),
            tasksPage::isTaskTitleCorrect,
            "DEM-072_View_Task_Detail",
            tasksPage::editTask,
            v -> editTaskPage,
            (page, data) -> page.addInformationFromData(data),
            editTaskPage::save,
            data -> data.get("name"),
            editTaskPage::isTaskSavedSuccessfully,
            "DEM-072"
        );
    }
}
```

---

## Best Practices

### 1. Method References
Gunakan method references (`::`) untuk parameter yang berupa method tanpa argument:
```java
// Good
accountsPage::navigateToAccountsModule

// Avoid
() -> accountsPage.navigateToAccountsModule()
```

### 2. Lambda Expressions
Gunakan lambda expressions untuk operations yang membutuhkan parameter atau logic:
```java
// For BiConsumer with 2 parameters
(page, data) -> page.addInformationFromData(data)

// For Function with return value
data -> data.get("name")

// For supplier without parameter
v -> accountsPage
```

### 3. Screenshot Naming
Gunakan naming convention yang konsisten:
- Create: `"TEST-ID_Create"`
- View: `"TEST-ID_View_Detail"`
- Edit Before: `"TEST-ID_View_Detail_Before_Edit"`
- Edit After: `"TEST-ID_Edit"`

### 4. Data Provider
Pastikan key di JSON data sesuai dengan yang digunakan di test:
- Create: `name`, `description`, dll
- View: `name` untuk verifikasi
- Edit: `nameBeforeEdit`, `name`, dll

---

## Troubleshooting

### Issue: NullPointerException pada page object
**Solution**: Pastikan page object diinisialisasi sebelum dipassing ke generic method:
```java
TasksPage tasksPage = new TasksPage(driver);
CreateTaskPage createTaskPage = new CreateTaskPage(driver, wait);
```

### Issue: Method not found pada runtime
**Solution**: Pastikan page object memiliki semua methods yang diperlukan dengan signature yang benar.

### Issue: Test data tidak terbaca
**Solution**: 
1. Periksa path file JSON di data provider
2. Pastikan key di JSON sesuai dengan key yang diakses di test
3. Verify data provider annotation di test method

---

## Future Enhancements

1. **Delete Operation**: Tambahkan `testDeleteEntity()` method
2. **Bulk Operations**: Support untuk multiple entity operations
3. **Custom Assertions**: Tambahkan assertion helpers yang lebih spesifik
4. **Error Handling**: Improved error messages dan reporting
5. **Parallel Execution**: Support untuk parallel test execution
6. **Retry Mechanism**: Auto-retry untuk flaky tests

---

*Dokumentasi ini akan terus diupdate seiring dengan perkembangan framework.*