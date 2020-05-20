package pl.lodz.p.it.ssbd2020.ssbd05.mok;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

public class EditOtherUserTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Test
    public void testMOK14() throws Exception {
        driver.get("https://localhost:8181/ssbd05/index.xhtml");
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("login:username")).click();
        driver.findElement(By.id("login:username")).clear();
        driver.findElement(By.id("login:username")).sendKeys("admin");
        driver.findElement(By.id("login:password")).click();
        driver.findElement(By.id("login:password")).clear();
        driver.findElement(By.id("login:password")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@id='login:submit']/span[2]")).click();
        driver.findElement(By.xpath("//button[@id='j_idt24:dynaButton']/span[2]")).click();
        driver.findElement(By.xpath("//li[@id='j_idt24:changeRoleButton']/a/span[2]")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//a[@id='j_idt24:changeAdmin']/span")).click();
        driver.findElement(By.xpath("//a[@id='menu-button']/i")).click();
        driver.findElement(By.xpath("//button[@id='listAccountsButton']/span")).click();
        driver.findElement(By.id("form1:filterAccountsTextBox")).click();
        driver.findElement(By.id("form1:filterAccountsTextBox")).clear();
        driver.findElement(By.id("form1:filterAccountsTextBox")).sendKeys("szulc");
        driver.findElement(By.xpath("//button[@id='form1:filterbutton']/span")).click();
        driver.findElement(By.xpath("//button[@id='form1:j_idt33:0:j_idt47']/span")).click();
        assertEquals("Piotr", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[2]/td[2]")).getText());
        assertEquals("Szulc", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[3]/td[2]")).getText());
        assertEquals("steez83@example.com", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[4]/td[2]")).getText());
        driver.findElement(By.xpath("//button[@id='commandButtons:j_idt60']/span")).click();
        driver.findElement(By.id("editAccount:firstName")).click();
        driver.findElement(By.id("editAccount:firstName")).clear();
        driver.findElement(By.id("editAccount:firstName")).sendKeys("Piotr Jan");
        driver.findElement(By.xpath("//button[@id='editAccount:submit']/span[2]")).click();
        driver.findElement(By.xpath("//button[@id='editAccount:j_idt47']/span[2]")).click();
        assertEquals("Edycja konta zakończona sukcesem!", driver.findElement(By.xpath("//div[@id='editAccount:j_idt35']/div/ul/li/span")).getText());
        driver.findElement(By.xpath("//button[@id='editAccount:j_idt45']/span")).click();
        assertEquals("Piotr Jan", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[2]/td[2]")).getText());
        assertEquals("Szulc", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[3]/td[2]")).getText());
        assertEquals("steez83@example.com", driver.findElement(By.xpath("//table[@id='panelGrid']/tbody/tr[4]/td[2]")).getText());
        driver.findElement(By.xpath("//button[@id='commandButtons:j_idt60']/span")).click();
        driver.findElement(By.id("editAccount:firstName")).click();
        driver.findElement(By.id("editAccount:firstName")).click();
        driver.findElement(By.id("editAccount:firstName")).clear();
        driver.findElement(By.id("editAccount:firstName")).sendKeys("Piotr");
        driver.findElement(By.xpath("//button[@id='editAccount:submit']/span[2]")).click();
        driver.findElement(By.xpath("//button[@id='editAccount:j_idt47']/span[2]")).click();
        assertEquals("Edycja konta zakończona sukcesem!", driver.findElement(By.xpath("//div[@id='editAccount:j_idt35']/div/ul/li/span")).getText());
        driver.findElement(By.xpath("//button[@id='editAccount:j_idt45']/span")).click();
        driver.findElement(By.xpath("//button[@id='j_idt24:dynaButton']/span[2]")).click();
        driver.findElement(By.xpath("//a[@id='j_idt24:logoutButton']/span[2]")).click();
    }

}
