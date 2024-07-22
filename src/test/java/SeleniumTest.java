package com.revature;
 
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
import java.io.File;
 
public class SeleniumTest {
    private WebDriver webDriver;
   // private WebDriverWait wait;
    private String path;
    @BeforeEach
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "driver/chromedriver");//linux_64
        // Get file
        File file = new File("src/main/java/com/revature/comparison_logical_operators.js");
        path = "file://" + file.getAbsolutePath();
 
        // Create a new ChromeDriver instance
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        webDriver = new ChromeDriver(options);
        // EdgeOptions options = new EdgeOptions();
        // options.addArguments("headless");
        // webDriver = new EdgeDriver(options);
 
        // Open the HTML file
        webDriver.get(path);
    }
    @AfterEach
    public void tearDown() {
        webDriver.quit();
    }
 
     @Test
    public void testDefaultValues() {
        webDriver.get(path);
        submitForm();
        String results = webDriver.findElement(By.id("results")).getText();
        assertTrue(results.contains("num == false: true"));
        assertTrue(results.contains("num === 0: true"));
        assertTrue(results.contains("str != \"\": false"));
        assertTrue(results.contains("obj !== null: true"));
        assertTrue(results.contains("arr.length > 0 && obj: false"));
        assertTrue(results.contains("bool || !bool: true"));
        assertTrue(results.contains("!!num: false"));
        assertTrue(results.contains("!!str: false"));
        assertTrue(results.contains("!!obj: true"));
        assertTrue(results.contains("!!arr: true"));
        assertTrue(results.contains("!!bool: true"));
        assertTrue(results.contains("!!nullUndef: false"));
    }
 
    @Test
    public void testNonEmptyValues() {
        webDriver.get(path);
        webDriver.findElement(By.id("number")).clear();
        webDriver.findElement(By.id("number")).sendKeys("5");
        webDriver.findElement(By.id("string")).sendKeys("test");
        new Select(webDriver.findElement(By.id("object"))).selectByValue("nonempty");
        new Select(webDriver.findElement(By.id("array"))).selectByValue("nonempty");
        submitForm();
        String results = webDriver.findElement(By.id("results")).getText();
        assertTrue(results.contains("num == false: false"));
        assertTrue(results.contains("num === 0: false"));
        assertTrue(results.contains("str != \"\": true"));
        assertTrue(results.contains("obj !== null: true"));
        assertTrue(results.contains("arr.length > 0 && obj: true"));
        assertTrue(results.contains("!!num: true"));
        assertTrue(results.contains("!!str: true"));
    }
 
    @Test
    public void testNullAndUndefined() {
        webDriver.get(path);
        new Select(webDriver.findElement(By.id("nullUndefined"))).selectByValue("null");
        submitForm();
        String results = webDriver.findElement(By.id("results")).getText();
        assertTrue(results.contains("!!nullUndef: false"));
 
        webDriver.get(path);
        new Select(webDriver.findElement(By.id("nullUndefined"))).selectByValue("undefined");
        submitForm();
        results = webDriver.findElement(By.id("results")).getText();
        assertTrue(results.contains("!!nullUndef: false"));
    }
 
    private void submitForm() {
        webDriver.findElement(By.cssSelector("button[type='submit']")).click();
    }
}
