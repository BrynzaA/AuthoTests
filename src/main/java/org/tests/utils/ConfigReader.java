package org.tests.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config.properties";

    static {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_PATH);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить config.properties: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static String getValidUsername() {
        return getProperty("valid.username");
    }

    public static String getValidBasicPassword() {
        return getProperty("valid.basicPassword");

    }

    public static String getValidBasicUsername() {
        return getProperty("valid.basicUsername");
    }

    public static String getValidPassword() {
        return getProperty("valid.password");
    }

    public static String getValidUsernameDesc() {
        return getProperty("valid.usernamedesc");
    }

    public static String getMainUrl() {
        return getProperty("main.url");
    }

    public static String getLoginUrl() {
        return getProperty("login.url");
    }

    public static String getLoginSuccessUrl() {
        return getProperty("login.url.success");
    }

    public static String getDroppableUrl() {
        return getProperty("droppable.url");
    }

    public static String getTabsUrl() {
        return getProperty("tabs.url");
    }

    public static String getAlertsUrl() {
        return getProperty("alerts.url");
    }

    public static String getBasicAuthUrl() {
        return getProperty("basicAuth.url");
    }

    public static String getBrowser() {
        return System.getProperty("browser", getProperty("default.browser"));
    }

    public static boolean useGrid() {
        String useGrid = System.getProperty("useGrid", getProperty("use.grid"));
        return Boolean.parseBoolean(useGrid);
    }

    public static String getGridUrl() {
        return System.getProperty("gridUrl", getProperty("grid.hub.url"));
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static boolean isGridEnabled() {
        return Boolean.parseBoolean(getProperty("use.grid", "false"));
    }

    public static String getRemoteUrl() {
        return getProperty("remote.url");
    }
}