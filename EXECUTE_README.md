## Running Tests

### Test About Menu Saja (Quick Test)
```powershell
mvn test "-DsuiteXmlFile=testng-configs/testng-landing-about.xml"
```

### Test All Navigation
```powershell
mvn test "-DsuiteXmlFile=testng-configs/testng-landing-navigation.xml"
```
## TEST CASE MODUL 1 - ABOUT NAVIGATION

### RUN Test Case news Navigation
```powershell
mvn test "-DsuiteXmlFile=testng-configs/landing/testng-news-navigation.xml"
```

### RUN Test Case Roadmap Navigation
```powershell
mvn test "-DsuiteXmlFile=testng-configs/landing/testng-roadmap-navigation.xml"
```

### Requirement
- JAVA harus versi 17