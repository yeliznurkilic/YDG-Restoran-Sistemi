package com.ydg.restaurant.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.time.Duration;

public class MenuUITest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        String driverPath = "C:/Projects/yazilimdogrulama/restoran-yonetimi/chromedriver-win64/chromedriver.exe";

        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(driverPath))
                .build();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        this.driver = new ChromeDriver(service, options);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Jenkins UI Test: Yemek Kaydetme")
    void testMenuSaveProcess() {
        driver.get("http://localhost:8081/");

        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.id("itemName")));
        input.clear();
        input.sendKeys("Jenkins Test Yemeği");

        WebElement saveBtn = driver.findElement(By.id("saveBtn"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);

        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("msg")));

        String text = result.getText();
        System.out.println("Jenkins Sonuç Mesajı: " + text);
        Assertions.assertTrue(text.contains("Kaydedildi"), "Kaydetme mesajı görülmedi!");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}