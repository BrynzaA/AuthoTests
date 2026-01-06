package org.tests.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class WebDriverFactory {

    public static WebDriver createDriver(String browserType) {
        WebDriver driver = null;

        boolean useGrid = ConfigReader.isGridEnabled();
        String remoteUrl = ConfigReader.getRemoteUrl();

        System.out.println("Creating driver with config:");
        System.out.println("  Browser: " + browserType);
        System.out.println("  Use Grid: " + useGrid);
        System.out.println("  Remote URL: " + remoteUrl);

        if (useGrid && remoteUrl != null && !remoteUrl.isEmpty()) {
            try {
                System.out.println("Connecting to Selenoid at: " + remoteUrl);

                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName(browserType.toLowerCase());

                if ("chrome".equalsIgnoreCase(browserType)) {
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                }

                driver = new RemoteWebDriver(new URL(remoteUrl), capabilities);
                System.out.println("Successfully connected to Selenoid");

            } catch (Exception e) {
                System.err.println("Failed to connect to Selenoid: " + e.getMessage());
                e.printStackTrace();
                System.out.println("Falling back to local driver");
                driver = createLocalDriver(browserType);
            }
        } else {
            driver = createLocalDriver(browserType);
        }

        if (driver != null) {
            driver.manage().window().maximize();
        }

        return driver;
    }

    private static WebDriver createLocalDriver(String browserType) {
        switch (browserType.toLowerCase()) {
            case "chrome":
                io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                return new ChromeDriver(options);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserType);
        }
    }
}