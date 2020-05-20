package pl.lodz.p.it.ssbd2020.ssbd05.mok;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
@Log
public class UnlockAccountTest {
    private WebDriver driver;
    JavascriptExecutor js;
    boolean staleElement;

    @Before
    public void setUp() {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);
        js = (JavascriptExecutor) driver;

    }
    @After
    public void tearDown() {
        driver.close();
    }
    @Test
    public void unlockAccount() {
//        driver.get("https://localhost:8181/ssbd05/admin/listAccounts.xhtml");
//        driver.manage().window().setSize(new Dimension(1528, 814));
//        log.severe("przed logowaniem " +driver.getCurrentUrl());
//        driver.findElement(By.id("login:username")).click();
//        driver.findElement(By.id("login:username")).sendKeys("admin");
//        driver.findElement(By.id("login:password")).sendKeys("admin123");
//        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
//        driver.findElement(By.id("form1:j_idt41")).click();
//        driver.findElement(By.id("form1:j_idt41")).sendKeys("Szajmi2");
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//        driver.findElement(By.cssSelector("#form1\\3A filterbutton > .ui-button-text")).click();
////        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
////        driver.findElement(By.cssSelector("#form1\\3Aj_idt42\\3A 0\\3Aj_idt56 > .ui-button-text")).click();
////        driver.findElement(By.name("form1:j_idt42:0:j_idt56")).click();
//        log.severe(driver.getCurrentUrl().toString());
//        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
//        while(staleElement) {
////        driver.findElement(By.name("commandButtons:unlockaccount"));
//            try {
//                driver.findElement(By.xpath("//*[contains(@id,'details')]")).click();
//                staleElement=false;
//            }catch (StaleElementReferenceException staleElementReferenceException) {
//                staleElement = true;
//            }
//        }
//        log.severe("po wcisnieciu details: " +driver.getCurrentUrl().toString());
//        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//        while(staleElement) {
//            try {
//                driver.findElement(By.xpath("//*[contains(@id,'unlockaccount')]")).click();
//                staleElement=false;
//            }catch (StaleElementReferenceException staleElementReferenceException){
//                staleElement=true;
//            }
//        }
//        log.severe("koniec: " + driver.getCurrentUrl());
////      driver.findElement(By.xpath("//*[contains(@id,'changeAdmin')]")).click();
////        driver.findElement(By.cssSelector("#commandButtons\\3Aj_idt71 > .ui-button-text")).click();
        driver.get("https://localhost:8181/ssbd05/index.xhtml");
        driver.manage().window().setSize(new Dimension(1552, 840));
        log.severe(driver.getCurrentUrl());
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).sendKeys("admin");
        driver.findElement(By.id("login:password")).sendKeys("admin123");
        driver.findElement(By.xpath("//*[contains(@id, 'submit')]")).click();
        log.severe(driver.getCurrentUrl());
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[contains(@id, 'dynaButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'changeRole')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'changeForAdmin')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'menu-button')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'listAccountsButton')]")).click();
        log.severe(driver.getCurrentUrl());
//        driver.findElement(By.id("form1:j_idt35")).click();
//        driver.findElement(By.id("form1:j_idt35")).sendKeys("Szajmi");
//        driver.findElement(By.cssSelector("#form1\\3A filterbutton > .ui-button-text")).click();
//        driver.findElement(By.xpath("//*[contains(@id, 'accountfilter')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'accountfilter')]")).sendKeys("Szajmi2");
        driver.findElement(By.xpath("//*[contains(@id, 'filterbutton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'goDetailsButton')]")).click();
        log.severe("po details: " + driver.getCurrentUrl());
        driver.findElement(By.xpath("//*[contains(@id, 'blockaccount')]")).click();
        log.severe("po unlocku: " + driver.getCurrentUrl());
//        String login = driver.findElement(By.xpath("//*[contains(@id, 'loginDetails')]")).getText();
//        String email = driver.findElement(By.xpath("//*[contains(@id, 'emailDetails')]")).getText();
//        assertEquals("admin", login);
//        assertEquals("sanah@example.com", email);

        driver.findElement(By.xpath("//*[contains(@id, 'dynaButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'logoutButton')]")).click();
    }
}
