package pl.lodz.p.it.ssbd2020.ssbd05.mok;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class BlockAccountTest {

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
    public void blockAccountTest(){

    }
}
