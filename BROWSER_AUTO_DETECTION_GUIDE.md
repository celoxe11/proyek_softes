# 🔍 Browser Auto-Detection Guide

## Fitur Deteksi Browser Otomatis

Sistem ini secara otomatis mendeteksi browser yang tersedia di sistem Anda dan menggunakannya untuk menjalankan test Selenium.

## 📦 Komponen Utama

### 1. BrowserDetector.java

Utility class yang mendeteksi dan membuat WebDriver secara otomatis.

**Lokasi**: `src/test/java/com/proyek_softes/landing/main/utils/BrowserDetector.java`

**Browser yang Didukung** (berurutan prioritas):

1. ✅ Google Chrome
2. ✅ Chromium
3. ✅ Mozilla Firefox
4. ✅ Microsoft Edge

### 2. DriverSetup.java

Base class untuk setup WebDriver dengan auto-detection.

**Lokasi**: `src/test/java/com/proyek_softes/landing/main/base/DriverSetup.java`

## 🚀 Cara Penggunaan

### Metode 1: Auto-Detection (Recommended)

```java
import com.proyek_softes.landing.main.utils.BrowserDetector;
import org.openqa.selenium.WebDriver;

@BeforeClass
public void setUp() {
    // Deteksi dan gunakan browser yang tersedia
    driver = BrowserDetector.createDriver();
}
```

### Metode 2: Specify Browser Type

```java
import com.proyek_softes.landing.main.utils.BrowserDetector;
import com.proyek_softes.landing.main.utils.BrowserDetector.BrowserType;

@BeforeClass
public void setUp() {
    // Gunakan browser spesifik
    driver = BrowserDetector.createDriver(BrowserType.FIREFOX);
}
```

### Metode 3: With Fallback

```java
@BeforeClass
public void setUp() {
    // Coba satu per satu sampai ada yang berhasil
    driver = BrowserDetector.createDriverWithFallback();
}
```

### Metode 4: Via DriverSetup Class

```java
import com.proyek_softes.landing.main.base.DriverSetup;

@BeforeClass
public void setUp() {
    driver = DriverSetup.setupDriver();
}
```

## 📋 Contoh Output

Ketika test dijalankan, Anda akan melihat output seperti:

```
🔍 Mendeteksi browser yang tersedia...
✓ Firefox ditemukan
🚀 Memulai Firefox...
✓ Browser berhasil dijalankan
```

## 🔧 Cara Kerja

1. **Deteksi**: System mengecek keberadaan browser dengan command `which` (Linux/Mac) atau `where` (Windows)
2. **Setup**: WebDriverManager otomatis download driver yang sesuai
3. **Launch**: Browser dibuka dan di-maximize
4. **Ready**: WebDriver siap digunakan untuk testing

## ✨ Keuntungan

1. ✅ **Tidak perlu hardcode browser** - otomatis detect
2. ✅ **Cross-platform** - bekerja di Windows, Linux, Mac
3. ✅ **Fallback support** - jika satu browser gagal, coba yang lain
4. ✅ **Zero configuration** - tidak perlu setup manual
5. ✅ **Informative logging** - jelas browser mana yang digunakan

## 🎯 Test Files yang Sudah Diupdate

- ✅ `LandingNavigationTest.java`
- ✅ `ContactPageTest.java`

## 🔨 Menjalankan Test

### Dengan Maven

```bash
# Set JAVA_HOME ke Java 17
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

# Run specific test suite
mvn test -DsuiteXmlFile=testng-configs/landing/testng-landing-navigation.xml

# Run all tests
mvn clean test
```

### Prioritas Browser

Sistem akan mencoba browser dalam urutan:

1. Chrome (paling diutamakan)
2. Chromium
3. Firefox
4. Edge

Browser pertama yang ditemukan akan digunakan.

## ⚙️ Kustomisasi

### Mengubah Urutan Prioritas

Edit method `createDriverWithFallback()` di `BrowserDetector.java`:

```java
BrowserType[] browsers = {
    BrowserType.FIREFOX,    // Prioritas 1
    BrowserType.CHROME,     // Prioritas 2
    BrowserType.EDGE,       // Prioritas 3
    BrowserType.CHROMIUM    // Prioritas 4
};
```

### Menambahkan Browser Baru

1. Tambahkan enum di `BrowserType`
2. Implementasi di `createDriver()` switch case
3. Tambahkan deteksi di `detectAvailableBrowser()`

## 🐛 Troubleshooting

### Error: "Tidak ada browser yang tersedia"

**Solusi**: Install minimal satu browser yang didukung:

```bash
# Ubuntu/Debian
sudo apt install firefox
# atau
sudo apt install chromium-browser
# atau
sudo snap install chromium
```

### Error: "Invalid target release: 17"

**Solusi**: Set JAVA_HOME ke Java 17:

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
```

### Browser terbuka tapi test fail

**Solusi**: Cek network connection dan URL yang ditest

## 📝 Notes

- WebDriverManager otomatis download driver yang sesuai (geckodriver, chromedriver, dll)
- Tidak perlu install driver secara manual
- Browser akan di-maximize otomatis
- Semua WebDriver method standard tetap dapat digunakan

## 🆕 Update untuk Test Lain

Untuk mengupdate test file lain:

```java
// BEFORE (hardcoded Chrome)
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

@BeforeClass
public void setUp() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
}

// AFTER (auto-detection)
import com.proyek_softes.landing.main.utils.BrowserDetector;

@BeforeClass
public void setUp() {
    driver = BrowserDetector.createDriver();
}
```

---

**Created**: December 24, 2025
**Version**: 1.0
**Author**: Automated Testing Team
