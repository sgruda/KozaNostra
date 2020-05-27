package pl.lodz.p.it.ssbd2020.ssbd05.mok;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ConfirmationMailTestUI {
    private WebDriver driver;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--lang=en");
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);
        js = (JavascriptExecutor) driver;
    }
    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void confirmationMailTest() throws InterruptedException {
        driver.get("https://localhost:8181/ssbd05/index.xhtml");
        driver.manage().window().setSize(new Dimension(1936, 1056));
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).sendKeys("admin");
        driver.findElement(By.id("login:password")).click();
        driver.findElement(By.id("login:password")).sendKeys("admin123");
        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
        driver.findElement(By.linkText("Change role")).click();
        Thread.sleep(200);
        driver.findElement(By.linkText("Admin")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("menu-bars")).click();
        Thread.sleep(200);
        driver.findElement(By.cssSelector("#listAccountsButton > .ui-button-text")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("form1:filterAccountsTextBox")).click();
        driver.findElement(By.id("form1:filterAccountsTextBox")).sendKeys("TestMaila");
        driver.findElement(By.id("form1:filterAccountsTextBox")).sendKeys(Keys.ENTER);
        Thread.sleep(300);
        driver.findElement(By.xpath("//td[7]/button/span")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(.,'Send confirmation email')]")).click();
        Thread.sleep(1000);
        Assert.assertEquals(driver.findElement(By.cssSelector(".ui-messages-info-summary")).getText(),"Confirmation email has been successfully sent");
    }
}
