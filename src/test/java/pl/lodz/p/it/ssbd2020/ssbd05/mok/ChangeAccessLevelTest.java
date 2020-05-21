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
        assertEquals("Klient", driver.findElement(By.xpath("//div[@id='j_idt26:j_idt27']/div/div/ul/li[2]")).getText());
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//li[3]/a/span[2]")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//li[3]/ul/li[2]/a/span")).click();
        driver.findElement(By.xpath("//a[@id='menu-button']/i")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        assertEquals("Panel menadżera", driver.findElement(By.xpath("//div[@id='j_idt9']/h1")).getText());
        driver.findElement(By.xpath("//button[@id='j_idt26:dynaButton']/span[2]")).click();
        assertEquals("Menadżer", driver.findElement(By.xpath("//div[@id='j_idt26:j_idt27']/div/div/ul/li[2]")).getText());
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//li[3]/a/span[2]")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//li[3]/ul/li[3]/a/span")).click();
        driver.findElement(By.xpath("//a[@id='menu-button']/i")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertEquals("Panel administratora", driver.findElement(By.xpath("//div[@id='j_idt9']/h1")).getText());
        driver.findElement(By.xpath("//button[@id='j_idt26:dynaButton']/span[2]")).click();
        assertEquals("Administrator", driver.findElement(By.xpath("//div[@id='j_idt26:j_idt27']/div/div/ul/li[2]")).getText());
        driver.findElement(By.xpath("//div[@id='j_idt26:j_idt27']/div/div/ul/li[4]/a/span[2]")).click();
    }

}
