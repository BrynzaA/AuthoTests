package org.tests;

import io.qameta.allure.Allure;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.tests.utils.WebDriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    private static final String SCREENSHOT_DIR = "target/screenshots/";
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = WebDriverFactory.createDriver();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void captureScreenshot(String testName) {
        try {
            Path screenshotDir = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotName = testName + "_" + System.currentTimeMillis() + ".png";
            Path destination = screenshotDir.resolve(screenshotName);

            Files.copy(screenshot.toPath(), destination);

            Allure.addAttachment("Скриншот при падении теста",
                    "image/png",
                    Files.newInputStream(destination),
                    ".png");


        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить скриншот: " + e.getMessage());
        }
    }
}
