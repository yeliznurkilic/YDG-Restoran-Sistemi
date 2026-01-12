package com.ydg.restaurant.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import java.io.File;
import java.time.Duration;

public class LoginUITest {
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
    @DisplayName("Senaryo: Personel Giriş Testi")
    void testStaffLogin() {
        driver.get("http://localhost:8081/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("1234");
        driver.findElement(By.id("loginBtn")).click();

        WebElement loginMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginMsg")));
        Assertions.assertTrue(loginMsg.getText().contains("Giriş Başarılı"));
    }

    @AfterEach
    void tearDown() { if (driver != null) driver.quit(); }
}