package com.ydg.restaurant.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

// @TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Eğer tek bir senaryo çalıştırıyorsanız buna gerek yok.
public class RestaurantSeleniumTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String SWAGGER_URL = "http://localhost:8081/swagger-ui/index.html";

    @BeforeEach
    void setup() {
        // Sürücü hatasını önlemek için WebDriverManager üzerinden güvenli başlatma
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Jenkins için zorunlu
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        // create() metodu, içsel null pointer hatalarını yönetir
        driver = WebDriverManager.chromedriver().capabilities(options).create();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("UI Senaryosu: Yeni Menü Öğesi (Yemek) Ekleme")
    void testAddMenuItem() {
        // 1. Swagger UI sayfasına git
        driver.get(SWAGGER_URL);

        // 2. "menu-item-controller" başlığına tıkla (açmak için)
        // XPath kullanarak controller span'ini bulup tıklıyoruz.
        click(By.xpath("//span[contains(text(),'menu-item-controller')]"));

        // 3. "addMenuItem" POST işlemini bulup tıkla (açmak için)
        // XPath kullanarak spesifik POST operasyon div'ini bulup tıklıyoruz.
        click(By.xpath("//div[contains(@id,'operations-menu-item-controller-addMenuItem')]"));

        // 4. "Try it out" butonuna tıkla
        click(By.cssSelector(".try-out button"));

        // 5. Request body kısmına JSON verisini gir
        WebElement textArea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".body-param__text")));
        textArea.clear();
        String jsonBody = "{\n" +
                "  \"name\": \"Mercimek Çorbası\",\n" +
                "  \"description\": \"Ev yapımı mercimek çorbası\",\n" +
                "  \"price\": 35.0,\n" +
                "  \"categoryId\": 1 \n" + // Varsayılan olarak 1 id'li bir kategori olduğunu varsayıyoruz.
                "}";
        textArea.sendKeys(jsonBody);

        // 6. "Execute" butonuna tıkla
        click(By.cssSelector(".execute"));

        // 7. Cevabı kontrol et: 200 veya 201 HTTP durumu bekliyoruz
        WebElement status = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".live-responses-table .response-col_status")));
        String statusCode = status.getText();
        System.out.println("HTTP Status Code: " + statusCode); // Debug amaçlı
        Assertions.assertTrue(statusCode.contains("200") || statusCode.contains("201"),
                "Menü öğesi eklenirken hata oluştu! HTTP Status: " + statusCode);
    }

    // Elementlere tıklamak için yardımcı metod
    private void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}