package com.ydg.restaurant.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import java.io.File;
import java.time.Duration;

public class FeedbackUITest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        String driverPath = "C:/Projects/yazilimdogrulama/restoran-yonetimi/chromedriver-win64/chromedriver.exe";
        ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File(driverPath)).build();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*", "--headless=new");
        this.driver = new ChromeDriver(service, options);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Senaryo: Müşteri Geri Bildirim Testi")
    void testCustomerFeedback() {
        driver.get("http://localhost:8081/");
        WebElement area = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("feedbackText")));
        area.sendKeys("Yemekler harikaydı, teşekkürler!");
        driver.findElement(By.id("submitFeedback")).click();

        WebElement feedMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("feedMsg")));
        Assertions.assertTrue(feedMsg.getText().contains("iletildi"));
    }

    @AfterEach
    void tearDown() { if (driver != null) driver.quit(); }
}