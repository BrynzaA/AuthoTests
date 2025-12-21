package org.tests.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

//Задача U9. Браузеры
public class WebDriverFactory {

    private static final String DEFAULT_BROWSER = "chrome";
    private static final boolean USE_GRID = false;
    private static final String GRID_HUB_URL = "http://localhost:4444/wd/hub";

    public static WebDriver createDriver() {
        String browserType = System.getProperty("browser", DEFAULT_BROWSER);
        boolean useGrid = Boolean.parseBoolean(System.getProperty("useGrid", String.valueOf(USE_GRID)));
        String gridUrl = System.getProperty("gridUrl", GRID_HUB_URL);

        return createDriver(browserType, useGrid, gridUrl);
    }

    public static WebDriver createDriver(String browserType, boolean useGrid, String gridUrl) {
        WebDriver driver;

        if (useGrid) {
            driver = createGridDriver(browserType, gridUrl);
        } else {
            driver = createLocalDriver(browserType);
        }

        driver.manage().window().maximize();
        return driver;
    }

    private static WebDriver createLocalDriver(String browserType) {
        switch (browserType.toLowerCase()) {
            case "chrome":
                return createChromeDriver();
            case "firefox":
                return createFirefoxDriver();
            case "edge":
                return createEdgeDriver();
            case "ie":
            case "internetexplorer":
                return createInternetExplorerDriver();
            default:
                throw new IllegalArgumentException("Неподдерживаемый браузер: " + browserType);
        }
    }

    private static WebDriver createGridDriver(String browserType, String gridUrl) {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            switch (browserType.toLowerCase()) {
                case "chrome":
                    capabilities.setBrowserName("chrome");
                    capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
                    break;
                case "firefox":
                    capabilities.setBrowserName("firefox");
                    capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, getFirefoxOptions());
                    break;
                case "edge":
                    capabilities.setBrowserName("MicrosoftEdge");
                    capabilities.setCapability(EdgeOptions.CAPABILITY, getEdgeOptions());
                    break;
                case "ie":
                case "internetexplorer":
                    capabilities.setBrowserName("internet explorer");
                    capabilities.setCapability(InternetExplorerOptions.IE_OPTIONS, getInternetExplorerOptions());
                    break;
                default:
                    throw new IllegalArgumentException("Неподдерживаемый браузер для Grid: " + browserType);
            }

            return new RemoteWebDriver(new URL(gridUrl), capabilities);

        } catch (MalformedURLException e) {
            throw new RuntimeException("Неверный URL Grid: " + gridUrl, e);
        }
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = getChromeOptions();
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = getFirefoxOptions();
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = getEdgeOptions();
        return new EdgeDriver(options);
    }

    private static WebDriver createInternetExplorerDriver() {
        WebDriverManager.iedriver().setup();
        InternetExplorerOptions options = getInternetExplorerOptions();
        return new InternetExplorerDriver(options);
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");

        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();

        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();

        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        return options;
    }

    private static InternetExplorerOptions getInternetExplorerOptions() {
        InternetExplorerOptions options = new InternetExplorerOptions();

        options.introduceFlakinessByIgnoringSecurityDomains();
        options.ignoreZoomSettings();
        options.requireWindowFocus();
        options.enablePersistentHovering();

        options.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        options.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
        options.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
        options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

        options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

        return options;
    }
}
