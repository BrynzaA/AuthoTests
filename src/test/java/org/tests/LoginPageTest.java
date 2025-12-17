package org.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.tests.pages.LoginPage;
import org.tests.utils.ConfigReader;
import io.qameta.allure.*;

@Epic("Авторизация")
@Feature("Логин")
@Owner("A Brynza")
public class LoginPageTest extends BaseTest {

    @Test
    @Story("Успешный логин")
    @Description("Тест проверяет успешную авторизацию с валидными данными")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open().login(ConfigReader.getValidUsername(), ConfigReader.getValidPassword(), ConfigReader.getValidUsernameDesc());

        boolean isLoginSuccess = loginPage.isLoginSuccess();
        Assert.assertTrue(isLoginSuccess, "Логин должен быть успешен");
    }
}
