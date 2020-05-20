package pl.lodz.p.it.ssbd2020.ssbd05.mok;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import java.util.concurrent.TimeUnit;

public class AccountDetailsTest {

    private WebDriver driver;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Test
    public void accountDetailsTest() {
//        driver.get("https://localhost:8181/ssbd05/index.xhtml");
//        driver.manage().window().setSize(new Dimension(1552, 840));
//        driver.findElement(By.id("loginButton")).click();
//        driver.findElement(By.id("login:username")).click();
//        driver.findElement(By.id("login:username")).sendKeys("admin");
//        driver.findElement(By.id("login:password")).sendKeys("admin123");
//        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
//        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
//        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
//        driver.findElement(By.cssSelector(".ui-state-hover > .ui-menuitem-text")).click();
//        driver.findElement(By.cssSelector("#j_idt26\\3A accountDetailsButton > .ui-menuitem-text")).click();
//        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
//
//        String login = (String) js.executeScript("document.getElementById('loginDetails').value");
//        assertEquals("admin", login);
//
//        driver.findElement(By.cssSelector(".ui-state-hover > .ui-menuitem-text")).click();
    }
}
