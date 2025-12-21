package org.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tests.pages.AlertsPage;

//Задача U12. Alerts
@Epic("Alerts")
@Feature("Страница alerts")
@Owner("A Brynza")
public class AlertsTest extends BaseTest {

    @Test
    @Feature("Успешное взаимодействие с алертом")
    @Description("Тест проверяет успешное взаимодействие с алертом")
    public void testTabsPage() {
        AlertsPage alertsPage = new AlertsPage(driver);
        alertsPage.open();

        SoftAssert softAssert = new SoftAssert();

        boolean isAlertBlockPassText = alertsPage.isAlertBlockPassText("Test Text");
        softAssert.assertTrue(isAlertBlockPassText, "Поле должно принимать текст из алерта");

        softAssert.assertAll();
    }
}
