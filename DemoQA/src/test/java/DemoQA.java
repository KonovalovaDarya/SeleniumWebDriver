import dev.failsafe.internal.util.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.security.Key;

import static java.lang.System.getProperty;

public class DemoQA {
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
        driver.get("https://demoqa.com/automation-practice-form");
    }

    @Test
    public void DemoQA() {
        driver.findElement(By.cssSelector("#firstName")).sendKeys("firstName");
        driver.findElement(By.cssSelector("#lastName")).sendKeys("lastName");
        driver.findElement(By.cssSelector("#userEmail")).sendKeys("email@email.com");
        driver.findElement(By.cssSelector("label[for='gender-radio-1']")).click();
        driver.findElement(By.cssSelector("#userNumber")).sendKeys("0000000000");

        driver.findElement(By.cssSelector("#dateOfBirthInput")).click();
        WebElement month = driver.findElement(By.cssSelector(".react-datepicker__month-select"));
        Select selectMonth = new Select(month);
        selectMonth.selectByIndex(9);
        WebElement year = driver.findElement(By.cssSelector(".react-datepicker__year-select"));
        Select selectYear = new Select(year);
        selectYear.selectByIndex(90);
        driver.findElement(By.cssSelector("div[class='react-datepicker__day react-datepicker__day--012']")).click();

        driver.findElement(By.cssSelector("#subjectsContainer input")).sendKeys("English");
        driver.findElement(By.cssSelector("#subjectsContainer input")).sendKeys(Keys.RETURN);

        File img = new File("src/test/java/img.jpg");
        String filePath = img.getAbsolutePath();
        driver.findElement(By.cssSelector("#uploadPicture")).sendKeys(filePath);

        driver.findElement(By.cssSelector("label[for='hobbies-checkbox-2']")).click();
        driver.findElement(By.cssSelector("#currentAddress")).sendKeys("Novosibirsk");

        driver.findElement(By.cssSelector("#state input")).sendKeys("NCR");
        driver.findElement(By.cssSelector("#state input")).sendKeys(Keys.RETURN);
        driver.findElement(By.cssSelector("#city input")).sendKeys("Delhi");
        driver.findElement(By.cssSelector("#city input")).sendKeys(Keys.RETURN);

        driver.findElement(By.cssSelector("#submit")).click();

        Assertions.assertEquals("firstName lastName", driver.findElement(By.cssSelector("tbody tr:first-child td:nth-child(2)")).getText());
        Assertions.assertEquals("email@email.com", driver.findElement(By.cssSelector("tbody tr:nth-child(2) td:nth-child(2)")).getText());
        Assertions.assertEquals("Male", driver.findElement(By.cssSelector("tbody tr:nth-child(3) td:nth-child(2)")).getText());
        Assertions.assertEquals("0000000000", driver.findElement(By.cssSelector("tbody tr:nth-child(4) td:nth-child(2)")).getText());
        Assertions.assertEquals("12 October,1990", driver.findElement(By.cssSelector("tbody tr:nth-child(5) td:nth-child(2)")).getText());
        Assertions.assertEquals("English", driver.findElement(By.cssSelector("tbody tr:nth-child(6) td:nth-child(2)")).getText());
        Assertions.assertEquals("Reading", driver.findElement(By.cssSelector("tbody tr:nth-child(7) td:nth-child(2)")).getText());
        Assertions.assertEquals("img.jpg", driver.findElement(By.cssSelector("tbody tr:nth-child(8) td:nth-child(2)")).getText());
        Assertions.assertEquals("Novosibirsk", driver.findElement(By.cssSelector("tbody tr:nth-child(9) td:nth-child(2)")).getText());
        Assertions.assertEquals("NCR Delhi", driver.findElement(By.cssSelector("tbody tr:nth-child(10) td:nth-child(2)")).getText());
    }

    @AfterEach
    public void closeBr() {
        driver.quit();
    }
}