package com.ydg.restaurant.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MenuUITest {

    private WebDriver driver;
    // Docker ağı içindeki adresler (Docker Compose servis isimleri kullanılır)
    private final String appUrl = "http://app:8080/menu";
    private final String seleniumUrl = "http://selenium:4444/wd/hub";

    @BeforeEach
    void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // KRİTİK DÜZELTME: Kendi bilgisayarındaki driver'ı değil,
        // Docker içindeki Selenium servisini kullanıyoruz.
        // Bu sayede "s1 is null" hatası tamamen engellenir.
        driver = new RemoteWebDriver(new URL(seleniumUrl), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    @Order(1)
    @DisplayName("Senaryo 1: Sayfa Başlık Kontrolü")
    void openHomePage() {
        driver.get(appUrl);
        Assertions.assertTrue(driver.getTitle().contains("Menü"), "Başlık Menü içermeli!");
    }

    @Test
    @Order(2)
    @DisplayName("Senaryo 2: Menü Tablosu Kontrolü")
    void shouldListMenuItems() {
        driver.get(appUrl);
        // Sayfada tablo olup olmadığını kontrol eder
        List<WebElement> tables = driver.findElements(By.tagName("table"));
        Assertions.assertFalse(tables.isEmpty(), "Menü tablosu bulunamadı!");
    }

    @Test
    @Order(3)
    @DisplayName("Senaryo 3: Ekleme Sayfası Erişimi")
    void shouldAccessAddPage() {
        driver.get(appUrl + "/add");
        Assertions.assertTrue(driver.getCurrentUrl().contains("/add"), "Ekleme sayfasına ulaşılamadı!");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}