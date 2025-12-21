package org.tests;

import org.testng.annotations.DataProvider;
import org.tests.utils.ConfigReader;

public class CredentialsDataProvider {

    @DataProvider(name = "invalidCredentials")
    public static Object[][] getInvalidCredentials() {
        return new Object[][]{
                // username, password, usernameDesc, scenarioDescription
                {"", "password123", "", "Пустой username"},
                {"username", "", "", "Пустой password"},
                {"", "", "", "Пустые username и password"},
                {"username", "password123", "username", "Не валидные username и password"},
                {ConfigReader.getValidUsername(), ConfigReader.getValidPassword(), ConfigReader.getValidUsernameDesc(), "Успешная валидация"},
        };
    }

    @DataProvider(name = "validBasicAuthCredentials")
    public static Object[][] getValidBasicAuthCredentials() {
        return new Object[][]{
                {ConfigReader.getValidBasicUsername(), ConfigReader.getValidBasicPassword(), "Успешная валидация"}
        };
    }
}