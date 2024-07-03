import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static java.lang.System.getProperty;

public class DemoShop {
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
        driver.get("https://demowebshop.tricentis.com/");
    }

    @ParameterizedTest
    @CsvSource({
            "Laptop",
            "Smartphone",
            "Fiction"
    })
    void addToCart(String item) {
        driver.findElement(By.cssSelector("#small-searchterms")).sendKeys(item);
        driver.findElement(By.cssSelector("[type=submit]")).click();
        String itemName = driver.findElement(By.cssSelector(".product-title a")).getText();
        driver.findElement(By.cssSelector(".item-box:first-child input")).click();
        driver.findElement(By.cssSelector("#topcartlink a")).click();
        Assertions.assertEquals(itemName, driver.findElement(By.cssSelector(".product-name")).getText());
    }

    @Test
    public void elementsCount() {
        driver.findElement(By.cssSelector(".list [href='/books']")).click();
        String pageSize = driver.findElement(By.cssSelector("#products-pagesize [selected='selected']")).getText();

        List<WebElement> itemsCount = driver.findElements(By.cssSelector(".product-grid > div"));
        Assertions.assertTrue(itemsCount.size() <= Integer.valueOf(pageSize));

        WebElement size = driver.findElement(By.cssSelector("#products-pagesize"));
        Select selectSize = new Select(size);
        selectSize.selectByValue("https://demowebshop.tricentis.com/books?pagesize=4");

        itemsCount = driver.findElements(By.cssSelector(".product-grid > div"));
        Assertions.assertTrue(itemsCount.size() <= 4);
    }

    @AfterEach
    public void closeBr() {
        driver.quit();
    }
}
