package pl.lodz.p.it.ssbd2020.ssbd05.mok;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ListAccountsTestUI {

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
    public void getAllAccountsTest() {
        driver.get("https://localhost:8181/ssbd05/index.xhtml");
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
        driver.findElement(By.xpath("//*[contains(@id,'listAccountsButton')]")).click();
        List<WebElement> rows = driver.findElements(By.tagName("tr"));
        assertTrue(rows.size() > 3);

        driver.findElement(By.xpath("//*[contains(@id,'dynaButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id,'logoutButton')]")).click();
    }

    @Test
    public void filterAccountsTest() {
        driver.get("https://localhost:8181/ssbd05/index.xhtml");
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
        driver.findElement(By.xpath("//*[contains(@id,'listAccountsButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id,'filterAccountsTextBox')]")).click();
        driver.findElement(By.xpath("//*[contains(@id,'filterAccountsTextBox')]")).sendKeys("selenium");
        driver.findElement(By.xpath("//*[contains(@id,'filterbutton')]")).click();

        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[1]/div[3]/form/div/div/table/tbody/tr[3]")));
        List<WebElement> rows = driver.findElements(By.tagName("tr"));
        assertEquals(3, rows.size());

        driver.findElement(By.xpath("//*[contains(@id,'dynaButton')]")).click();
        driver.findElement(By.xpath("//*[contains(@id,'logoutButton')]")).click();
    }
}
