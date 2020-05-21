package pl.lodz.p.it.ssbd2020.ssbd05.mok;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

public class EditOtherUserTest {
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
    public void testMOK14() throws Exception {
        driver.get("https://localhost:8181/ssbd05/");
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).clear();
        driver.findElement(By.id("login:username")).sendKeys("admin");
        driver.findElement(By.id("login:password")).click();
        driver.findElement(By.id("login:password")).clear();
        driver.findElement(By.id("login:password")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@id='login:submit']/span")).click();
        driver.findElement(By.xpath("//button[@id='j_idt26:dynaButton']/span")).click();
        driver.findElement(By.xpath("//div[@id='j_idt26:j_idt27']/div/div/ul/li[3]/a/span[2]")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//div[@id='j_idt26:j_idt27']/div/div/ul/li[3]/ul/li[3]/a/span")).click();
        driver.findElement(By.xpath("//a[@id='menu-button']/i")).click();
        driver.findElement(By.xpath("//button[@id='j_idt11']/span")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("form1:j_idt41")).click();
        driver.findElement(By.id("form1:j_idt41")).click();
        driver.findElement(By.id("form1:j_idt41")).clear();
        driver.findElement(By.id("form1:j_idt41")).sendKeys("szulc");
        driver.findElement(By.xpath("//button[@id='form1:filterbutton']/span")).click();
        driver.findElement(By.xpath("//button[@id='form1:j_idt42:0:j_idt56']/span")).click();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        assertEquals("Piotr", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[2]/td[2]")).getText());
        assertEquals("Szulc", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[3]/td[2]")).getText());
        assertEquals("steez83@example.com", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[4]/td[2]")).getText());
        driver.findElement(By.xpath("//button[@id='commandButtons:j_idt68']/span")).click();
        driver.findElement(By.id("editAccount:firstName")).click();
        driver.findElement(By.id("editAccount:firstName")).clear();
        driver.findElement(By.id("editAccount:firstName")).sendKeys("Piotr Jan");
        driver.findElement(By.xpath("//button[@id='editAccount:submit']/span")).click();
        driver.findElement(By.xpath("//button[@id='editAccount:j_idt55']/span[2]")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertEquals("Edycja konta zakończona sukcesem!", driver.findElement(By.xpath("//div[@id='editAccount:j_idt43']/div/ul/li/span")).getText());
        driver.findElement(By.xpath("//button[@id='editAccount:j_idt53']/span")).click();
        assertEquals("Piotr Jan", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[2]/td[2]")).getText());
        assertEquals("Szulc", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[3]/td[2]")).getText());
        assertEquals("steez83@example.com", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[4]/td[2]")).getText());
        driver.findElement(By.xpath("//button[@id='commandButtons:j_idt68']/span")).click();
        driver.findElement(By.id("editAccount:firstName")).click();
        driver.findElement(By.id("editAccount:firstName")).clear();
        driver.findElement(By.id("editAccount:firstName")).sendKeys("Piotr");
        driver.findElement(By.xpath("//button[@id='editAccount:submit']/span[2]")).click();
        driver.findElement(By.xpath("//button[@id='editAccount:j_idt55']/span")).click();
        driver.findElement(By.xpath("//button[@id='j_idt26:dynaButton']/span[2]")).click();
        assertEquals("Edytuj dane konta", driver.getTitle());
        driver.findElement(By.xpath("//button[@id='j_idt26:dynaButton']/span[2]")).click();
        driver.findElement(By.xpath("//div[@id='j_idt26:j_idt27']/div/div/ul/li[4]/a/span[2]")).click();
    }

}
