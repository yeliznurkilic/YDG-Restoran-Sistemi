package com.ydg.restaurant.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import java.util.List;

/**
 * Yazılım Doğrulama ve Geçerleme Projesi - Selenium UI Testleri
 * Ödev Gereksinimi: En az 3 test senaryosu içermelidir.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MenuUITest {

    private WebDriver driver;
    // Uygulamanın lokalde çalıştığı port (Loglarda 8082 olarak göründü)
    private final String baseUrl = "http://localhost:8082/menu";

    @BeforeEach
    void setup() {
        // 1. Selenium'un hatalı tarama yapmasını engellemek için JDK istemcisini zorla
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // 2. WebDriverManager ile driver'ı indir ve yolunu al
        WebDriverManager wdm = WebDriverManager.chromedriver();
        wdm.setup();
        String driverPath = wdm.getDownloadedDriverPath();

        // 3. KRİTİK: Selenium'un kendi kendine driver aramasını (ve hata vermesini) engelle
        // Yolu manuel vererek getenv() çağrısını baypas ediyoruz
        System.setProperty("webdriver.chrome.driver", driverPath);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        // Driver'ı başlat
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    @Order(1)
    @DisplayName("Senaryo 1: Ana Sayfa Yükleme ve Başlık Doğrulama")
    void openHomePage() {
        driver.get(baseUrl);
        String title = driver.getTitle();
        // Sayfa başlığının "Menü" içerdiğini doğrula
        Assertions.assertTrue(title.contains("Menü"), "Hata: Sayfa başlığı 'Menü' kelimesini içermiyor!");
    }

    @Test
    @Order(2)
    @DisplayName("Senaryo 2: Menü İçerik Listesi Kontrolü")
    void shouldListMenuItems() {
        driver.get(baseUrl);
        // HTML içinde menü öğelerini temsil eden class'ı bul (Örn: .table-responsive veya .menu-item)
        // Eğer 404 alıyorsan uygulama henüz tam ayağa kalkmamış olabilir
        List<WebElement> items = driver.findElements(By.tagName("table"));
        Assertions.assertFalse(items.isEmpty(), "Hata: Menü sayfası yüklendi ancak tablo bulunamadı!");
    }

    @Test
    @Order(3)
    @DisplayName("Senaryo 3: Yeni Ürün Ekleme Sayfasına Geçiş Kontrolü")
    void shouldAccessAddPage() {
        driver.get(baseUrl + "/add");
        String currentUrl = driver.getCurrentUrl();
        // URL'nin /add ile bittiğini doğrula
        Assertions.assertTrue(currentUrl.contains("/add"), "Hata: Ürün ekleme sayfasına ulaşılamadı!");

        // Sayfa üzerindeki "Ekle" butonunun veya bir input alanının varlığını kontrol et
        WebElement form = driver.findElement(By.tagName("form"));
        Assertions.assertNotNull(form, "Hata: Ürün ekleme formuna ulaşılamadı!");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}