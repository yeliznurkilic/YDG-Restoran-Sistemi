package com.ydg.restaurant.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MenuUITest {
    private WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Jenkins için
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @Test
    @DisplayName("Senaryo 1: Arayüz Üzerinden Masa Kaydetme")
    void testAddTableFromUI() {
        // Jenkins'te docker konteynerına 8081'den erişiyoruz
        driver.get("http://localhost:8081/");

        driver.findElement(By.id("tableNumber")).sendKeys("12");
        driver.findElement(By.id("capacity")).sendKeys("4");
        driver.findElement(By.id("saveTableBtn")).click();

        // Başarı kontrolü (Yönlendirme veya sonuç mesajı bekleyebilirsin)
        Assertions.assertTrue(driver.getCurrentUrl().contains("tables") || driver.getPageSource().contains("12"));
    }

    @AfterEach
    void tearDown() { if (driver != null) driver.quit(); }
}