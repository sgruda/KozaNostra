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

public class EditOtherUserTestUI {
    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        ChromeOptions options = new ChromeOptions();
    //    options.setHeadless(true);
        options.setAcceptInsecureCerts(true);
        options.addArguments("--lang=pl");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Test
    public void testMOK14() throws Exception {
        driver.get("https://localhost:8181/ssbd05/");
        driver.manage().window().fullscreen();
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).clear();
        driver.findElement(By.id("login:username")).sendKeys("admin");
        driver.findElement(By.id("login:password")).click();
        driver.findElement(By.id("login:password")).clear();
        driver.findElement(By.id("login:password")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@id='login:submit']/span")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//form/button/span")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//li[3]/a/span[2]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//li[3]/ul/li[3]/a/span")).click();
        driver.findElement(By.xpath("//a[@id='menu-button']/i")).click();
        driver.findElement(By.xpath("//button/span")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[contains(@id,'filterAccountsTextBox')]")).sendKeys("szulc");
        driver.findElement(By.xpath("//button[@id='form1:filterbutton']/span")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//td[7]/button/span")).click();
        Thread.sleep(2000);
        assertEquals("Szulc", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[3]/td[2]")).getText());
        assertEquals("steez83@example.com", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[4]/td[2]")).getText());
        driver.findElement(By.xpath("//span[contains(.,'Edytuj')]")).click();
        Thread.sleep(500);
        driver.findElement(By.id("editAccount:firstName")).clear();
        Thread.sleep(500);
        driver.findElement(By.id("editAccount:firstName")).sendKeys("Piotrek");
        Thread.sleep(500);
        driver.findElement(By.xpath("//button[@id='editAccount:submit']/span")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[3]/button/span[2]")).click();
        Thread.sleep(2000);
        assertEquals("Piotrek", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[2]/td[2]")).getText());
        assertEquals("Szulc", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[3]/td[2]")).getText());
        assertEquals("steez83@example.com", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[4]/td[2]")).getText());
        driver.findElement(By.xpath("//span[contains(.,'Edytuj')]")).click();
        Thread.sleep(500);
        driver.findElement(By.id("editAccount:firstName")).click();
        driver.findElement(By.id("editAccount:firstName")).click();
        driver.findElement(By.id("editAccount:firstName")).clear();
        Thread.sleep(500);
        driver.findElement(By.id("editAccount:firstName")).sendKeys("Piotr");
        driver.findElement(By.xpath("//button[@id='editAccount:submit']/span[2]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[3]/button/span[2]")).click();;
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[2]")).click();
    }

}
