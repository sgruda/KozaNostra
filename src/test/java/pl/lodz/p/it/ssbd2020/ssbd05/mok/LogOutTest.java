package pl.lodz.p.it.ssbd2020.ssbd05.mok;


import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Log
public class LogOutTest {

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
    public void logOutTest() {
        driver.get("https://localhost:8181/ssbd05/");
        driver.manage().window().setSize(new Dimension(720, 1056));
        driver.findElement(By.linkText("Zaloguj")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).sendKeys("admin");
        driver.findElement(By.id("login:password")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@id=\'login:submit\']/span[2]")).click();
        driver.findElement(By.xpath("//span[2]")).click();
        driver.findElement(By.xpath("//span[contains(.,\'Wyloguj\')]")).click();
    }
}
