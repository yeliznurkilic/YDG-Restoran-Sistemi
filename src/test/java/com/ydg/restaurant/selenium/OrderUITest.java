package com.ydg.restaurant.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import java.io.File;
import java.time.Duration;

public class OrderUITest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        String driverPath = "C:/Projects/yazilimdogrulama/restoran-yonetimi/chromedriver-win64/chromedriver.exe";
        ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File(driverPath)).build();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*", "--headless=new");
        this.driver = new ChromeDriver(service, options);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Senaryo: Sipariş Durumu Sorgulama")
    void testOrderStatus() {
        driver.get("http://localhost:8081/");
        WebElement orderInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("orderId")));
        orderInput.sendKeys("ORD-123");
        WebElement searchBtn = driver.findElement(By.id("searchBtn"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);
        WebElement statusMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("statusMsg")));
        Assertions.assertTrue(statusMsg.getText().contains("Hazırlanıyor"));
    }

    @AfterEach
    void tearDown() { if (driver != null) driver.quit(); }
}