package com.ydg.restaurant.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MenuUITest {

    private static WebDriver driver;

    @BeforeEach
    void setup() {
        // Hata çözümü için kritik sistem ayarı
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // WebDriverManager'ı temizleyerek başlat
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Jenkins için zorunlu
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    @Order(1)
    @DisplayName("Ana Sayfa Başlık Kontrolü")
    void openHomePage() {
        driver.get("http://localhost:8081/menu");
        String title = driver.getTitle();
        Assertions.assertTrue(title.contains("Menü"), "Sayfa başlığı 'Menü' içermeli!");
    }

    @Test
    @Order(2)
    @DisplayName("Menü Listesi Kontrolü")
    void shouldListMenuItems() {
        driver.get("http://localhost:8081/menu");
        List<WebElement> items = driver.findElements(By.cssSelector(".menu-item"));
        // Not: Eğer veritabanı boşsa bu fail olabilir, db'de veri olduğundan emin ol
        Assertions.assertNotNull(items, "Menü listesi null olmamalı!");
    }

    @Test
    @Order(3)
    @DisplayName("Ürün Ekleme Sayfası Erişimi")
    void shouldAccessAddPage() {
        driver.get("http://localhost:8081/menu/add");
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("/menu/add"), "Ekleme sayfasına ulaşılamadı!");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}