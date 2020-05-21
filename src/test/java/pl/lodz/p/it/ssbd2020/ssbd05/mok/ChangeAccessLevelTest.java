package pl.lodz.p.it.ssbd2020.ssbd05.mok;

import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class ChangeAccessLevelTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.setAcceptInsecureCerts(true);
        options.addArguments("--lang=pl");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Test
    public void testMOK3() throws Exception {
        driver.get("https://localhost:8181/ssbd05/index.xhtml");
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).clear();
        driver.findElement(By.id("login:username")).sendKeys("admin");
        driver.findElement(By.id("login:password")).click();
        driver.findElement(By.id("login:password")).clear();
        driver.findElement(By.id("login:password")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@id='login:submit']/span[2]")).click();
        driver.findElement(By.xpath("//button[@id='j_idt26:dynaButton']/span")).click();
        Thread.sleep(2000);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//li[3]/a/span[2]")).click();
//        assertEquals("Klient", driver.findElement(By.xpath("//li[2]")).getText());
//        Thread.sleep(2000);
        driver.findElement(By.xpath("//li[3]/a/span[2]")).click();
//        assertEquals("Klient", driver.findElement(By.xpath("//li[2]")).getText());
        Thread.sleep(2000);
        driver.findElement(By.xpath("//li[3]/ul/li[2]/a/span")).click();
        driver.findElement(By.xpath("//a[@id='menu-button']/i")).click();
        Thread.sleep(2000);
        assertEquals("Panel menadżera", driver.findElement(By.xpath("//h1")).getText());
        driver.findElement(By.xpath("//span[2]")).click();
        assertEquals("Menadżer", driver.findElement(By.xpath("//li[2]")).getText());
        Thread.sleep(2000);
        driver.findElement(By.xpath("//li[3]/a/span[2]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//li[3]/ul/li[3]/a/span")).click();
        driver.findElement(By.xpath("//a[@id='menu-button']/i")).click();
        Thread.sleep(2000);
        assertEquals("Panel administratora", driver.findElement(By.xpath("//h1")).getText());
        driver.findElement(By.xpath("//span[2]")).click();
        assertEquals("Administrator", driver.findElement(By.xpath("//li[2]")).getText());
        driver.findElement(By.xpath("//li[4]/a/span[2]")).click();
    }

}
