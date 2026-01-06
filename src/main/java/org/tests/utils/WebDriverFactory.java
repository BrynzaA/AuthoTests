package org.tests.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

//Задача U9. Браузеры
public class WebDriverFactory {

    public static WebDriver createDriver(String browser, String host, String port, boolean useRemote) {
        if (useRemote) {
            return createRemoteDriver(browser, host, port);
        } else {
            return createLocalDriver(browser);
        }
    }

    private static WebDriver createRemoteDriver(String browser, String host, String port) {
        try {
            String remoteUrl = "http://" + host + ":" + port + "/wd/hub";
            DesiredCapabilities capabilities = new DesiredCapabilities();

            System.out.println("Creating remote driver for Selenoid at: " + remoteUrl);
            System.out.println("Browser: " + browser);

            switch (browser.toLowerCase()) {
                case "chrome":
                    capabilities.setBrowserName("chrome");
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    break;

                case "firefox":
                    capabilities.setBrowserName("firefox");
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--start-maximized");
                    capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
                    break;

                default:
                    throw new IllegalArgumentException("Неподдерживаемый браузер для Selenoid: " + browser);
            }

            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", false);
            capabilities.setCapability("enableLog", true);
            capabilities.setCapability("timeZone", "Europe/Moscow");
            capabilities.setCapability("sessionTimeout", "5m");

            RemoteWebDriver driver = new RemoteWebDriver(new URL(remoteUrl), capabilities);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

            return driver;

        } catch (MalformedURLException e) {
            throw new RuntimeException("Неверный URL Selenoid: " + host + ":" + port, e);
        }
    }

    private static WebDriver createLocalDriver(String browser) {
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                driver = new FirefoxDriver(firefoxOptions);
                break;

            default:
                throw new IllegalArgumentException("Неподдерживаемый браузер: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        return driver;
    }
}