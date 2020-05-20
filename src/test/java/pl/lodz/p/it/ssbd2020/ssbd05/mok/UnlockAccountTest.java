package pl.lodz.p.it.ssbd2020.ssbd05.mok;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class UnlockAccountTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @After
    public void tearDown() {
        driver.close();
    }
    @Test
    public void unlockAccount() {
        driver.get("https://localhost:8181/ssbd05/index.xhtml");
        driver.manage().window().setSize(new Dimension(1528, 814));
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).sendKeys("admin");
        driver.findElement(By.id("login:password")).sendKeys("admin123");
        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".ui-state-hover > .ui-menuitem-text")).click();
        driver.findElement(By.cssSelector(".ui-state-hover > .ui-menuitem-text")).click();
        driver.findElement(By.cssSelector(".header")).click();
        driver.findElement(By.cssSelector(".pi-bars")).click();
        {
            WebElement element = driver.findElement(By.linkText("LogoPlaceholder"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        driver.findElement(By.cssSelector("#j_idt11 > .ui-button-text")).click();
        driver.findElement(By.cssSelector("#form1\\3Aj_idt42\\3A 23\\3Aj_idt56 > .ui-button-text")).click();
        driver.findElement(By.cssSelector("#commandButtons\\3Aj_idt71 > .ui-button-text")).click();
        driver.findElement(By.cssSelector("#j_idt26\\3A dynaButton > .ui-button-text")).click();
        driver.findElement(By.cssSelector(".ui-state-hover > .ui-menuitem-text")).click();
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).sendKeys("Ginner");
        driver.findElement(By.id("login:password")).sendKeys("Herbs22!");
        driver.findElement(By.id("login:password")).sendKeys(Keys.ENTER);
        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".ui-state-hover > .ui-menuitem-text")).click();
    }
}
