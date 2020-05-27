package pl.lodz.p.it.ssbd2020.ssbd05.mok;


import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

@Log
public class LogOutTestUI {

    private WebDriver driver;
    private JavascriptExecutor js;

    @Before
    public void setUp() {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--lang=pl");
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);
        js = (JavascriptExecutor) driver;
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Test
    public void logOutTest() throws InterruptedException {
        driver.get("https://localhost:8181/ssbd05/");
        driver.manage().window().setSize(new Dimension(720, 1056));
        driver.findElement(By.linkText("Zaloguj")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).sendKeys("admin");
        driver.findElement(By.id("login:password")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@id=\'login:submit\']/span[2]")).click();
        driver.findElement(By.xpath("//span[2]")).click();
        driver.findElement(By.xpath("//span[contains(.,\'Wyloguj\')]")).click();
        Thread.sleep(500);
        List<WebElement> elements = driver.findElements(By.id("loginButton"));
        Assert.assertEquals(true,elements.size() > 0);
    }
}
