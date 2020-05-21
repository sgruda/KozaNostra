package pl.lodz.p.it.ssbd2020.ssbd05.mok;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class BlockThenUnlockAccountTest {
    private WebDriver driver;
    JavascriptExecutor js;


    @Before
    public void setUp() {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.setAcceptInsecureCerts(true);
        options.addArguments("--lang=pl");
        driver = new ChromeDriver(options);
        js = (JavascriptExecutor) driver;

    }
    @After
    public void tearDown() {
        driver.close();
    }
    @Test
    public void blockThenUnlockAccount() {
        driver.get("https://localhost:8181/ssbd05/index.xhtml");
        driver.manage().window().setSize(new Dimension(1552, 840));
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).sendKeys("admin");
        driver.findElement(By.id("login:password")).sendKeys("admin123");
        driver.findElement(By.xpath("//*[contains(@id, 'submit')]")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[contains(@id, 'dynaButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'changeRoleButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'changeAdmin')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'menu-button')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'listAccountsButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'filterAccountsTextBox')]")).sendKeys("Szajmi2");
        driver.findElement(By.xpath("//*[contains(@id, 'filterbutton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'goDetailsButton')]")).click();
        String active = driver.findElement(By.xpath("//*[contains(@id, 'active')]")).getText();
        Assert.assertEquals("Tak",active);
        driver.findElement(By.xpath("//*[contains(@id, 'blockaccount')]")).click();
        String inactive = driver.findElement(By.xpath("//*[contains(@id, 'inactive')]")).getText();
        Assert.assertEquals("Nie",inactive);
        driver.findElement(By.xpath("//*[contains(@id, 'unlockaccount')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'dynaButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'logoutButton')]")).click();
    }
}
