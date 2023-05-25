
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("web-driver.chrome.driver", "./driver/win/chromedriver");
    }


    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");

    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void positiveTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Метелева Светлана");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79816844874");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals(expected, actual);

    }

    @Test
    public void emptyName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79816844874");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void latinName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Meteleva Svetlana");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79816844874");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void numberName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("123");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79816844874");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void onlyHyphen() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("-");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79816844874");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void onlySpace() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("          ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79816844874");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void emptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Метелева Светлана");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void longPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Метелева Светлана");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+798168448740");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void shortPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Метелева Светлана");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7981684487");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void lettersPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Метелева Светлана");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("ффффффффф");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void noPlusPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Метелева Светлана");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("89816844874");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void radioButton() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Метелева Светлана");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79816844874");
        driver.findElement(By.tagName("button")).click();
        Boolean expected = true;
        Boolean actual = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).isEnabled();
        assertEquals(expected, actual);
    }
}

