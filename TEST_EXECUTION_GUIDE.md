# Test Execution Guide

```powershell
# Default (All tests - each module separate browser)
mvn test

# Test one specific module (e.g., Account)
mvn test "-DsuiteXmlFile=testng-configs/testng-account.xml"

# Test with Firefox browser
mvn test "-DsuiteXmlFile=testng-configs/testng-firefox.xml"

# Clean and test
mvn clean test
```

---
