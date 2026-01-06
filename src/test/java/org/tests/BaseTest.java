package org.tests;

import org.openqa.selenium.WebDriver;
import org.tests.utils.WebDriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.tests.utils.ConfigReader;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        String browser = System.getProperty("browser",
                ConfigReader.getProperty("browser", "chrome"));
        String useGrid = System.getProperty("use.grid",
                ConfigReader.getProperty("use.grid", "false"));

        driver = WebDriverFactory.createDriver(browser);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}