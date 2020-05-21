package pl.lodz.p.it.ssbd2020.ssbd05.mok;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccessLevelsTest {

    private WebDriver driver;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.setAcceptInsecureCerts(true);
        options.addArguments("--lang=en");
        driver = new ChromeDriver(options);
        js = (JavascriptExecutor) driver;
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Test
    public void accountDetailsTest() throws InterruptedException {
        driver.get("https://localhost:8181/ssbd05/index.xhtml");
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).sendKeys("admin");
        driver.findElement(By.id("login:password")).sendKeys("admin123");
        driver.findElement(By.xpath("//*[contains(@id, 'submit')]")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[contains(@id, 'dynaButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'changeRoleButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'changeAdmin')]")).click();
        driver.findElement(By.cssSelector(".pi-bars")).click();
        driver.findElement(By.cssSelector("#listAccountsButton > .ui-button-text")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[contains(@id, 'filterAccountsTextBox')]")).sendKeys("kontodotestowpoziomowdostepu");
        driver.findElement(By.xpath("//*[contains(@id, 'filterbutton')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[contains(@id, 'goDetailsButton')]")).click();
        Thread.sleep(500);

        String first = driver.findElements(By.tagName("tr")).get(7).getText();

        driver.findElement(By.xpath("//*[contains(@id, 'addAccessLevelButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'addManagerRole')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'addRole')]")).click();

        String second = driver.findElements(By.tagName("tr")).get(7).getText();

        driver.findElement(By.xpath("//*[contains(@id, 'removeAccessLevelButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'removeManagerRole')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'removeRole')]")).click();

        String third = driver.findElements(By.tagName("tr")).get(7).getText();

        assertFalse(first.contains("Manager"));
        assertTrue(second.contains("Manager"));
        assertFalse(third.contains("Manager"));

        driver.findElement(By.xpath("//*[contains(@id, 'dynaButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id, 'logoutButton')]")).click();
    }
}
