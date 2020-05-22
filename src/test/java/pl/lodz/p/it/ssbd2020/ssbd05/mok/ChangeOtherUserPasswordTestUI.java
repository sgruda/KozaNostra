package pl.lodz.p.it.ssbd2020.ssbd05.mok;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

@Log
public class ChangeOtherUserPasswordTestUI {

    private WebDriver driver;
    private JavascriptExecutor js;

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
    public void changeOtherUsersPassword(){
        driver.get("https://localhost:8181/ssbd05/");
        driver.manage().window().setSize(new Dimension(1187, 1020));
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).sendKeys("admin");
        driver.findElement(By.id("login:password")).sendKeys("admin123");
        driver.findElement(By.xpath("//*[contains(@id,'submit')]")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        driver.findElement(By.xpath("//*[contains(@id,'dynaButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id,'changeRoleButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id,'changeAdmin')]")).click();
        driver.findElement(By.cssSelector(".pi-bars")).click();
        {
            WebElement element = driver.findElement(By.cssSelector(".pi-bars"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        driver.findElement(By.cssSelector("#j_idt11 > .ui-button-text")).click();
        driver.findElement(By.id("form1:j_idt36")).click();
        driver.findElement(By.id("form1:j_idt36")).sendKeys("nieUsuwacKrystian");
        driver.findElement(By.xpath("//span[contains(.,\'Szukaj\')]")).click();
        driver.findElement(By.xpath("//td[7]/button/span")).click();
        driver.findElement(By.cssSelector("#j_idt66 > .ui-button-text")).click();
        driver.findElement(By.id("changePassword:password")).click();
        driver.findElement(By.id("changePassword:password")).sendKeys("zaq1@WSX");
        driver.findElement(By.id("changePassword:confirmPassword")).sendKeys("zaq1@WSX");
        driver.findElement(By.xpath("//span[contains(.,\'Zmień hasło\')]")).click();
        driver.findElement(By.xpath("//span[contains(.,\'admin\')]")).click();
        driver.findElement(By.xpath("//span[contains(.,\'Wyloguj\')]")).click();
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).sendKeys("nieUsuwac1");
        driver.findElement(By.id("login:password")).sendKeys("zaq1@WSX");
        driver.findElement(By.xpath("//span[contains(.,\'Zaloguj\')]")).click();
        driver.findElement(By.xpath("//span[contains(.,\'nieUsuwac1\')]")).click();
        driver.findElement(By.xpath("//span[contains(.,\'Wyloguj\')]")).click();
    }
}
