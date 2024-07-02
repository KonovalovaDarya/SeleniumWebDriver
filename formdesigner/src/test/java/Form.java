import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static java.lang.System.getProperty;

public class Form {
    WebDriver driver = null;

    @BeforeEach
    public void openBr() {
        String browser = getProperty("browser");

        if (browser.equals("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equals("edge")) {
            driver = new EdgeDriver();
        } else if (browser.equals("firefox")) {
            driver = new FirefoxDriver();
        }

        driver.manage().window().maximize();
        driver.get("https://formdesigner.ru/examples/order.html");
    }

    @Test
    public void form() {
        driver.findElement(By.cssSelector("#c-p-bn")).click();

        WebElement iframeForm = driver.findElement(By.tagName("iframe"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",iframeForm);
        driver.switchTo().frame(iframeForm);

        driver.findElement(By.cssSelector("button[name='submit']")).click();

        Assertions.assertEquals("ФИО: field is required.", driver.findElement(By.cssSelector("#form_1006 ul li:first-child")).getText());
        Assertions.assertEquals("E-mail field is required.", driver.findElement(By.cssSelector("#form_1006 ul li:nth-child(2)")).getText());
        Assertions.assertEquals("Количество field is required.", driver.findElement(By.cssSelector("#form_1006 ul li:nth-child(3)")).getText());
        Assertions.assertEquals("Дата доставки field is required.", driver.findElement(By.cssSelector("#form_1006 ul li:last-child")).getText());
    }

    @AfterEach
    public void closeBr() {
        driver.quit();
    }
}