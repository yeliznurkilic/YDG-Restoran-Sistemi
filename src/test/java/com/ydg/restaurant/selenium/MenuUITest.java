package com.ydg.restaurant.selenium;

import lombok.var;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MenuUITest {

    private static WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
    }


    @Test
    @Order(1)
    void shouldOpenMenuPage() {
        driver.get("http://localhost:8081/menu");
        String title = driver.getTitle();
        Assertions.assertTrue(title.toLowerCase().contains("menu"));
    }

    @Test
    @Order(2)
    void shouldListMenuItems() {
        driver.get("http://localhost:8081/menu");

        var items = driver.findElements(By.cssSelector(".menu-item"));
        Assertions.assertFalse(items.isEmpty(), "Menü öğeleri bulunamadı!");
    }

    @AfterAll
    static void cleanup() {
        if (driver != null) driver.quit();
    }
}
