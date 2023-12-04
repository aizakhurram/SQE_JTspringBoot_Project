package com.jtspringproject.JtSpringProject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class LoginE2ETest {

    private ChromeDriver driver;


    @Before
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().browserVersion("119.0.6045.200").setup();
        driver = new ChromeDriver();
        Thread.sleep(5000); // 5 seconds delay
    }


    @Test
    public void testLogin() {
        // Navigate to your login page
        driver.get("http://localhost:8080/admin/login");

        // Find username and password fields and submit button
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));

        // Input test credentials
        String username = "admin";
        String password = "123";

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        // Find login button by class name and click
        WebElement loginButton = driver.findElement(By.className("btn-primary"));
        loginButton.click();

        // Wait for the next page to load (Assuming successful login redirects to dashboard)
        // You might need to use WebDriverWait here depending on your app's behavior

        // Assuming after successful login, you are redirected to a dashboard
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "http://localhost:8080/admin/loginvalidate";
        assertEquals(expectedUrl, actualUrl);
    }


    @After
    public void tearDown() {
        // Close the browser after the test
        driver.quit();
    }
}
