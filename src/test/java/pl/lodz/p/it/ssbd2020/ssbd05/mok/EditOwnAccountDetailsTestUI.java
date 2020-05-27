package pl.lodz.p.it.ssbd2020.ssbd05.mok;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class EditOwnAccountDetailsTestUI {
    private WebDriver driver;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        ChromeOptions options = new ChromeOptions();
      //  options.setHeadless(true);
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
    public void editOwnAccountDetailsTest() throws InterruptedException {
        driver.get("https://localhost:8181/ssbd05/index.xhtml");
        driver.manage().window().setSize(new Dimension(1936, 1056));
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).sendKeys("TestZmianyHasla");
        driver.findElement(By.id("login:password")).click();
        driver.findElement(By.id("login:password")).sendKeys("TestZmianyHasla123@");
        driver.findElement(By.cssSelector(".ui-button-text:nth-child(2)")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(".ui-button-icon-left")).click();
        Thread.sleep(500);
        driver.findElement(By.linkText("Account Details")).click();
        Thread.sleep(500);
        driver.findElement(By.linkText("Account Details")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[2]/span")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("editAccount:firstName")).click();
        driver.findElement(By.id("editAccount:firstName")).clear();
        driver.findElement(By.id("editAccount:firstName")).sendKeys("Tester");
        driver.findElement(By.id("editAccount:lastName")).click();
        driver.findElement(By.id("editAccount:lastName")).clear();
        driver.findElement(By.id("editAccount:lastName")).sendKeys("Testowanie");
        driver.switchTo().frame(0);
        driver.findElement(By.cssSelector(".recaptcha-checkbox-border")).click();
        Thread.sleep(2000);
        driver.switchTo().defaultContent();
        driver.findElement(By.cssSelector("#editAccount\\3Asubmit > .ui-button-text")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(.,\'Yes\')]")).click();
        Thread.sleep(1000);
        Assert.assertEquals((driver.findElement(By.id("firstnameDetails")).getText()),"Tester");
        Assert.assertEquals((driver.findElement(By.id("lastnameDetails")).getText()),"Testowanie");
        driver.findElement(By.xpath("//button[2]/span")).click();
        Thread.sleep(500);
        driver.findElement(By.id("editAccount:firstName")).click();
        driver.findElement(By.id("editAccount:firstName")).clear();
        driver.findElement(By.id("editAccount:firstName")).sendKeys("Test");
        driver.findElement(By.id("editAccount:lastName")).click();
        driver.findElement(By.id("editAccount:lastName")).clear();
        driver.findElement(By.id("editAccount:lastName")).sendKeys("Test");
        driver.switchTo().frame(0);
        driver.findElement(By.cssSelector(".recaptcha-checkbox-border")).click();
        Thread.sleep(2000);
        driver.switchTo().defaultContent();
        driver.findElement(By.cssSelector("#editAccount\\3Asubmit > .ui-button-icon-left")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[3]/button/span[2]")).click();
        Thread.sleep(1000);
        Assert.assertEquals((driver.findElement(By.id("firstnameDetails")).getText()),"Test");
        Assert.assertEquals((driver.findElement(By.id("lastnameDetails")).getText()),"Test");
    }
}
