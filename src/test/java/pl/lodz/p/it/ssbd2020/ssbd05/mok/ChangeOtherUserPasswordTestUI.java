package pl.lodz.p.it.ssbd2020.ssbd05.mok;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

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
        options.addArguments("--lang=en");
        driver = new ChromeDriver(options);
        js = (JavascriptExecutor) driver;
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Test
    public void changeOtherUsersPassword() throws InterruptedException {
        driver.get("https://localhost:8181/ssbd05/index.xhtml");
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).sendKeys("admin");
        driver.findElement(By.id("login:password")).click();
        driver.findElement(By.id("login:password")).click();
        {
            WebElement element = driver.findElement(By.id("login:password"));
            Actions builder = new Actions(driver);
            builder.doubleClick(element).perform();
        }
        driver.findElement(By.id("login:password")).sendKeys("admin123");
        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
        Thread.sleep(200);
        driver.findElement(By.linkText("Change role")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath("//a[contains(.,\'Admin\')]")).click();
        Thread.sleep(200);
        driver.findElement(By.id("menu-bars")).click();
        {
            WebElement element = driver.findElement(By.id("menu-bars"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        driver.findElement(By.cssSelector("#listAccountsButton > .ui-button-text")).click();
        driver.findElement(By.id("form1:filterAccountsTextBox")).click();
        driver.findElement(By.id("form1:filterAccountsTextBox")).sendKeys("NieUsuwacKrystian");
        driver.findElement(By.cssSelector("#form1\\3A filterbutton > .ui-button-text")).click();
        {
            WebElement element = driver.findElement(By.cssSelector("#form1\\3A filterbutton > .ui-button-text"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        driver.findElement(By.xpath("//td[7]/button/span")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath("//div[2]/button/span")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("#changePassword\\3Apassword")).click();
        driver.findElement(By.cssSelector("#changePassword\\3Apassword")).sendKeys("Hasło123!");
        driver.findElement(By.cssSelector("#changePassword\\3A confirmPassword")).click();
        driver.findElement(By.cssSelector("#changePassword\\3A confirmPassword")).sendKeys("Hasło123!");
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#changePassword\\3Asubmit > .ui-button-text")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath("//span[contains(.,\'Yes\')]")).click();
        Thread.sleep(1000);
        Assert.assertEquals(driver.findElement(By.cssSelector(".ui-messages-info-summary")).getText(),"Password has been changed");
    }
}
