# Test Execution Guide

## 📋 Available Test Configurations

You now have **7 different TestNG XML files** to run tests in different ways:

### 🎯 **1. Comprehensive Test (For Presentations)**
**File:** `testng-configs/testng-comprehensive.xml` or `testng.xml` (default)  
**What it does:** Runs ALL tests in ONE browser session (fast, no repeated logins)  
**Command:**
```powershell
mvn test
# OR
mvn test "-DsuiteXmlFile=testng-configs/testng-comprehensive.xml"
```

---

### 🔧 **2. All Individual Tests**
**File:** `testng-configs/testng-individual.xml`  
**What it does:** Runs all tests but each module opens its own browser  
**Command:**
```powershell
mvn test "-DsuiteXmlFile=testng-configs/testng-individual.xml"
```

---

### 🧪 **3. Test One Module at a Time**

#### Test Login Only
```powershell
mvn test "-DsuiteXmlFile=testng-configs/testng-login.xml"
```

#### Test Account CRUD Only
```powershell
mvn test "-DsuiteXmlFile=testng-configs/testng-account.xml"
```

#### Test Contact CRUD Only
```powershell
mvn test "-DsuiteXmlFile=testng-configs/testng-contact.xml"
```

#### Test Opportunity CRUD Only
```powershell
mvn test "-DsuiteXmlFile=testng-configs/testng-opportunity.xml"
```

#### Test Lead CRUD Only
```powershell
mvn test "-DsuiteXmlFile=testng-configs/testng-lead.xml"
```

---

## 💡 **When to Use Which?**

| Scenario | Use This | Command |
|----------|----------|---------|
| 🎤 **Presenting/Demo** | Comprehensive | `mvn test` |
| 🐛 **Testing if Account works** | Account only | `mvn test -DsuiteXmlFile=testng-account.xml` |
| 🐛 **Testing if Login works** | Login only | `mvn test -DsuiteXmlFile=testng-login.xml` |
| 🔍 **Testing specific module** | That module's XML | `mvn test -DsuiteXmlFile=testng-[module].xml` |
| 📊 **Full regression test** | Individual | `mvn test -DsuiteXmlFile=testng-individual.xml` |

---

## 🎬 **Quick Reference (PowerShell)**

```powershell
# Default (Comprehensive - FASTEST)
mvn test

# Test one specific module (e.g., Account)
mvn test "-DsuiteXmlFile=testng-configs/testng-account.xml"

# Run all tests individually (each module separate browser)
mvn test "-DsuiteXmlFile=testng-configs/testng-individual.xml"

# Clean and test
mvn clean test
```

---

## 📊 **Performance Comparison**

| Test Type | Browser Opens | Logins | Time (est.) |
|-----------|---------------|--------|-------------|
| Comprehensive | 1x | 1x | ⚡ 10 sec |
| Individual (all 5) | 5x | 5x | 🐌 50 sec |
| Single module | 1x | 1x | ⚡ 10 sec |

---

## 🎓 **Development Workflow**

**During Development (testing one feature):**
```powershell
mvn test "-DsuiteXmlFile=testng-configs/testng-account.xml"
```

**Before Commit (verify everything works):**
```powershell
mvn test "-DsuiteXmlFile=testng-configs/testng-individual.xml"
```

**For Presentation (show all tests fast):**
```powershell
mvn test
```

---

## 📁 **Project Structure**

```
proyek_softes/
├── testng.xml                          # Default config (comprehensive)
├── testng-configs/                     # All TestNG configurations
│   ├── testng-comprehensive.xml        # All tests in one browser
│   ├── testng-individual.xml           # Each test separate browser
│   ├── testng-login.xml                # Login module only
│   ├── testng-account.xml              # Account CRUD only
│   ├── testng-contact.xml              # Contact CRUD only
│   ├── testng-opportunity.xml          # Opportunity CRUD only
│   └── testng-lead.xml                 # Lead CRUD only
├── src/test/java/
│   └── com/proyek_softes/demo/
│       ├── tests/
│       │   ├── BaseTest.java           # Base test setup
│       │   ├── ComprehensiveTest.java  # Comprehensive test
│       │   ├── LoginTest.java          # Login test
│       │   └── crud/                   # CRUD test modules
│       │       ├── AccountCrudTest.java
│       │       ├── ContactCrudTest.java
│       │       ├── OpportunityCrudTest.java
│       │       └── LeadCrudTest.java
│       └── pages/                      # Page Object Model
│           ├── LoginPage.java
│           ├── WelcomePage.java
│           ├── AccountsPage.java
│           ├── ContactsPage.java
│           ├── OpportunitiesPage.java
│           └── LeadsPage.java
└── TEST_EXECUTION_GUIDE.md             # This file
```
