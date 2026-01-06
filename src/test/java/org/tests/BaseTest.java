package org.tests;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.tests.utils.WebDriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        String host = System.getProperty("selenium.host", "localhost");
        String port = System.getProperty("selenium.port", "4444");
        String remote = System.getProperty("selenium.remote", "true");

        System.out.println("Test configuration:");
        System.out.println("  Browser: " + browser);
        System.out.println("  Host: " + host);
        System.out.println("  Port: " + port);
        System.out.println("  Remote: " + remote);

        driver = WebDriverFactory.createDriver(browser, host, port, Boolean.parseBoolean(remote));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void captureScreenshot(String testName) {
        try {
            if (driver instanceof TakesScreenshot) {
                String screenshotDir = "target/screenshots/";
                Path screenshotPath = Paths.get(screenshotDir);

                if (!Files.exists(screenshotPath)) {
                    Files.createDirectories(screenshotPath);
                }

                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String screenshotName = testName + "_" + System.currentTimeMillis() + ".png";
                Path destination = screenshotPath.resolve(screenshotName);

                Files.copy(screenshot.toPath(), destination);

                Allure.addAttachment("Скриншот при падении теста",
                        "image/png",
                        Files.newInputStream(destination),
                        ".png");
            }
        } catch (IOException e) {
            System.err.println("Не удалось сохранить скриншот: " + e.getMessage());
        }
    }
}