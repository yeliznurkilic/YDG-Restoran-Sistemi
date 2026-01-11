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
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    @Order(1)
    void openHomePage() {
        driver.get("http://localhost:8081/menu");
        String title = driver.getTitle();
        Assertions.assertTrue(title.contains("Menu"));
    }

    @Test
    @Order(2)
    void shouldListMenuItems() {
        driver.get("http://localhost:8081/menu");
        List<WebElement> items = driver.findElements(By.cssSelector(".menu-item"));

        Assertions.assertTrue(items.size() > 0, "Menü boş olmamalı!");
    }

    @AfterAll
    static void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
