package org.tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tests.pages.BasicAuth;

//Задача U13. Basic auth
@Epic("Basic auth")
@Feature("Basic auth")
@Owner("A Brynza")
public class BasicAuthTest extends BaseTest {

    @Test(dataProvider = "validBasicAuthCredentials", dataProviderClass = CredentialsDataProvider.class)
    @Feature("Успешная авторизация по basic")
    @Description("Тест проверяет успешную авторизацию по basic")
    public void testBasicAuthPage(String username, String password, String scenarioDescription) {

        Allure.parameter("Сценарий", scenarioDescription);
        Allure.parameter("Username", username);
        Allure.parameter("Password", "******");
        BasicAuth basicAuth = new BasicAuth(driver);
        basicAuth.open();

        SoftAssert softAssert = new SoftAssert();

        boolean isAlertBlockPassText = basicAuth.isBasicAuthWorks(username, password);
        softAssert.assertTrue(isAlertBlockPassText, "Авторизация по Basic auth должна работать");

        softAssert.assertAll();
    }
}
