package org.tests;


import io.qameta.allure.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tests.pages.LoginPage;


//Задача U6. JavaScriptExecutor
@Epic("Авторизация")
@Feature("Логин")
@Owner("A Brynza")
public class LoginPageJavaScriptExecutorTest extends BaseTest {

    @Test
    @Story("Фокус на полях")
    @Description("Тест проверяет перемещение фокуса между полями")
    @Severity(SeverityLevel.NORMAL)
    public void testRemoveFocus() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        SoftAssert softAssert = new SoftAssert();

        loginPage.enterUsername("testuser");

        softAssert.assertTrue(loginPage.hasFocusOnUsernameField(), "Фокус должен устанавливаться на поле ввода имени пользователя");

        loginPage.removeFocusFromUsernameField();

        softAssert.assertFalse(loginPage.hasFocusOnUsernameField(), "Фокус должен сняться с поля ввода имени пользователя");

        softAssert.assertAll();
    }

    @Test
    @Story("Наличие скролла на странице логина")
    @Description("Тест проверяет наличие скролла на странице логина")
    @Severity(SeverityLevel.MINOR)
    public void testCheckScroll() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        SoftAssert softAssert = new SoftAssert();

        boolean hasVerticalScroll = loginPage.hasVerticalScroll();
        softAssert.assertFalse(hasVerticalScroll, "На странице должен отсутствовать вертикальный скролл");

        boolean hasHorizontalScroll = loginPage.hasHorizontalScroll();
        softAssert.assertFalse(hasHorizontalScroll, "На странице должен отсутствовать горизонтальный скролл");

        softAssert.assertAll();
    }
}
