package com.revature;
 
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.support.ui.Select;
import java.io.File;
 
 
public class SeleniumTest {
    private WebDriver webDriver;

    @BeforeEach
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "driver/chromedriver");//linux_64
        // Get file
        File file = new File("src/main/java/com/revature/index.html");
        String path = "file://" + file.getAbsolutePath();
 
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        webDriver = new ChromeDriver(options);

 
        // Open the HTML file
        webDriver.get(path);
    }
    @AfterEach
    public void tearDown() {
        webDriver.quit();
    }
 
    @Test
    public void testRegistrationForm() {
    // Existing code for entering user information
    webDriver.findElement(By.id("name")).sendKeys("John Doe");
    webDriver.findElement(By.id("email")).sendKeys("john@example.com");
    webDriver.findElement(By.id("password")).sendKeys("password123");
 
    Select genderSelect = new Select(webDriver.findElement(By.id("gender")));
    genderSelect.selectByValue("male");
 
    webDriver.findElement(By.id("birthdate")).sendKeys("1990-01-01");
    webDriver.findElement(By.id("terms")).click();
    webDriver.findElement(By.xpath("//button[text()='Register']")).click();
 
    // Assertions to verify successful registration
    // 1. Check for success message (assuming there's one)
    String successMessage = webDriver.findElement(By.cssSelector(".success-message")).getText(); // Adjust selector as needed
    assertEquals("Registration successful!", successMessage);
 
    // 2. Check user redirection (optional)
    String currentUrl = webDriver.getCurrentUrl();
    assertTrue(currentUrl.contains("/dashboard") || currentUrl.contains("/profile")); // Adjust URLs based on application logic
 
    // 3. Check user data in database (if possible)
    // This may require database access within your test framework (e.g., using JPA or JDBC)
    // Here's an example using a mock database (replace with your actual logic)
    // User expectedUser = new User("John Doe", "john@example.com", "password123", /* other user data */);
    // User actualUser = /* Your logic to retrieve user from database based on email or other identifier */;
    // assertEquals(expectedUser, actualUser);
    }
 
 
   @Test
    public void testLoginForm() {
    webDriver.findElement(By.id("loginEmail")).sendKeys("user@example.com");
    webDriver.findElement(By.id("loginPassword")).sendKeys("password123");
    webDriver.findElement(By.xpath("//button[text()='Login']")).click();
 
    // Assertions to verify successful login
    // 1. Check for success message (assuming there's one)
    String successMessage = webDriver.findElement(By.cssSelector(".success-message")).getText(); // Adjust selector as needed
    assertEquals("Login successful!", successMessage);
 
    // 2. Check user redirection (optional)
    String currentUrl = webDriver.getCurrentUrl();
    assertTrue(currentUrl.contains("/dashboard") || currentUrl.contains("/profile")); // Adjust URLs based on application logic
 
    // 3. Check for logged-in user information (optional)
    WebElement userNameElement = webDriver.findElement(By.id("loggedInUserName")); // Adjust selector as needed
    String loggedInUserName = userNameElement.getText();
    assertEquals("John Doe", loggedInUserName); // Replace with expected username
 
    // 4. Check for availability of specific elements (optional)
    boolean isLogoutButtonPresent = webDriver.findElements(By.xpath("//button[text()='Logout']")).size() > 0;
    assertTrue(isLogoutButtonPresent);
    }
 
 
    @Test
    public void testFeedbackForm() {
    webDriver.findElement(By.id("comments")).sendKeys("Great service!");
    webDriver.findElement(By.id("rating")).sendKeys("5");
    webDriver.findElement(By.xpath("//button[text()='Submit Feedback']")).click();
 
    // Assertions to verify successful feedback submission
    // 1. Check for success message (assuming there's one)
    String successMessage = webDriver.findElement(By.cssSelector(".success-message")).getText(); // Adjust selector as needed
    assertEquals("Feedback submitted!", successMessage);
 
    // 2. Check for disabled submit button (optional)
    WebElement submitButton = webDriver.findElement(By.xpath("//button[text()='Submit Feedback']"));
    assertTrue(submitButton.isEnabled() == false); // Button should be disabled after submission
    }
 
 
    @Test
    public void testFormValidation() {
        webDriver.findElement(By.xpath("//button[text()='Register']")).click();
        String nameError = webDriver.findElement(By.id("name")).getAttribute("validationMessage");
        assertNotEquals("", nameError);
 
        webDriver.findElement(By.id("email")).sendKeys("invalid-email");
        webDriver.findElement(By.xpath("//button[text()='Register']")).click();
        String emailError = webDriver.findElement(By.id("email")).getAttribute("validationMessage");
        assertNotEquals("", emailError);
 
        webDriver.findElement(By.id("password")).clear();
        webDriver.findElement(By.xpath("//button[text()='Register']")).click();
        String passwordError = webDriver.findElement(By.id("password")).getAttribute("validationMessage");
        assertNotEquals("", passwordError);
    }
 
    @Test
    public void testGenderSelect() {
        Select genderSelect = new Select(webDriver.findElement(By.id("gender")));
 
        assertEquals("Male", genderSelect.getFirstSelectedOption().getText());
 
        genderSelect.selectByValue("female");
        assertEquals("Female", genderSelect.getFirstSelectedOption().getText());
 
        genderSelect.selectByValue("other");
        assertEquals("Other", genderSelect.getFirstSelectedOption().getText());
    }
}
