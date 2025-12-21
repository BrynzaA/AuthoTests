package org.tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.tests.pages.LoginPage;

//Задача U3. DataProvider
@Epic("Авторизация")
@Feature("Логин")
@Owner("A Brynza")
public class LoginPageDataProviderTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    @Test(dataProvider = "invalidCredentials", dataProviderClass = CredentialsDataProvider.class,
            description = "Проверка авторизации с различными наборами данных")
    @Story("Параметризованный логин")
    @Description("Тест проверяет авторизацию с различными комбинациями логина, пароля и описания")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginWithDifferentCredentials(String username, String password,
                                                  String usernameDesc, String scenarioDescription) {

        Allure.parameter("Сценарий", scenarioDescription);
        Allure.parameter("Username", username);
        Allure.parameter("Password", "******");
        Allure.parameter("Username Description", usernameDesc);

        loginPage.login(username, password, usernameDesc);

        boolean shouldBeSuccessful = scenarioDescription.equals("Успешная валидация");

        if (shouldBeSuccessful) {
            boolean isSuccess = loginPage.isLoginSuccess();
            Assert.assertTrue(isSuccess,
                    String.format("Сценарий '%s': Ожидался успешный логин", scenarioDescription));

            Assert.assertFalse(loginPage.isLoginFailed(),
                    "При успешном логине не должно быть сообщения об ошибке");
        } else {
            boolean isButtonActive = loginPage.isButtonActive();

            if (!isButtonActive) {
                Assert.assertFalse(isButtonActive,
                        String.format("Сценарий '%s': Кнопка должна быть не активна", scenarioDescription));
            } else {
                boolean isFailed = loginPage.isLoginFailed();
                Assert.assertTrue(isFailed,
                        String.format("Сценарий '%s': Ожидалась ошибка авторизации", scenarioDescription));
            }
        }
    }
}