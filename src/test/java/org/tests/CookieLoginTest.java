package org.tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.tests.pages.LoginPage;
import org.tests.utils.ConfigReader;
import org.tests.utils.CookieManager;

//Задача U5. Cookies
@Epic("Авторизация с использованием куков")
@Feature("Cookie-based авторизация")
@Owner("A Brynza")
public class CookieLoginTest extends BaseTest {

    @BeforeTest
    public void cleanCookies() {
        CookieManager.clearCookiesFile();
    }


    @Test
    @Story("Успешный логин с куками")
    @Description("Тест проверяет успешную авторизацию по сохраненным кукам")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open().login(ConfigReader.getValidUsername(), ConfigReader.getValidPassword(), ConfigReader.getValidUsernameDesc());

        loginPage.isLoginSuccess();

        CookieManager.saveCookiesToFile(loginPage.getCookies());

        loginPage.openSuccessLoginPageWithCookie(CookieManager.loadCookiesFromFile());

        boolean isPageIsPageAfterLogin = loginPage.isPageIsPageAfterLogin();
        Assert.assertTrue(isPageIsPageAfterLogin, "Должна открыться та же страница, что и после логина");
    }

}