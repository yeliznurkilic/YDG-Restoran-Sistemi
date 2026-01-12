package com.ydg.restaurant.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import java.io.File;
import java.time.Duration;

public class ReservationUITest {
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
    @DisplayName("Senaryo: Masa Rezervasyonu")
    void testTableReservation() {
        driver.get("http://localhost:8081/");
        WebElement tableInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("tableNo")));
        tableInput.sendKeys("15");
        WebElement reserveBtn = driver.findElement(By.id("reserveBtn"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", reserveBtn);
        WebElement resMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resResult")));
        Assertions.assertTrue(resMsg.getText().contains("Rezerve Edildi"));
    }

    @AfterEach
    void tearDown() { if (driver != null) driver.quit(); }
}