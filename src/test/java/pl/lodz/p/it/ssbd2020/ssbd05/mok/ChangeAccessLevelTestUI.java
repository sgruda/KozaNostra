package pl.lodz.p.it.ssbd2020.ssbd05.mok;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;


public class ChangeAccessLevelTestUI {
    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.setAcceptInsecureCerts(true);
        options.addArguments("--lang=en");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Test
    public void testMOK3() throws Exception {
        driver.get("https://localhost:8181/ssbd05/");
        driver.manage().window().setSize(new Dimension(1800, 1020));
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).sendKeys("admin");
        driver.findElement(By.id("login:password")).click();
        driver.findElement(By.id("login:password")).sendKeys("admin123");
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
        Thread.sleep(200);
        Assert.assertEquals(driver.findElement(By.cssSelector(".ui-helper-reset > .ui-menuitem:nth-child(2)")).getText(),"Client");
        driver.findElement(By.xpath("//*[contains(@id, \'changeRoleButton\')]")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath("//*[contains(@id, \'changeManager\')]")).click();
        Thread.sleep(200);
        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
        Thread.sleep(200);
        Assert.assertEquals(driver.findElement(By.cssSelector(".ui-helper-reset > .ui-menuitem:nth-child(2)")).getText(),"Manager");
        driver.findElement(By.xpath("//*[contains(@id, \'changeRoleButton\')]")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath("//*[contains(@id, \'changeAdmin\')]")).click();
        Thread.sleep(200);
        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
        Thread.sleep(200);
        Assert.assertEquals(driver.findElement(By.cssSelector(".ui-helper-reset > .ui-menuitem:nth-child(2)")).getText(),"Admin");
        driver.findElement(By.xpath("//span[contains(.,\'Log out\')]")).click();
    }

}
