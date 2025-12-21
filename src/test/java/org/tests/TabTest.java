package org.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tests.pages.TabsPage;

//Задача U11. Tabs
@Epic("Tabs")
@Feature("Страница tabs")
@Owner("A Brynza")
public class TabTest extends BaseTest {

    @Test
    @Feature("Успешное взаимодействие с блоком табов на странице")
    @Description("Тест проверяет успешное взаимодействие с блоком табов на странице")
    public void testTabsPage() {
        TabsPage tabsPage = new TabsPage(driver);
        tabsPage.open();

        SoftAssert softAssert = new SoftAssert();

        boolean isTabSwitchedToSecond = tabsPage.isTabSwitchToSecond();
        softAssert.assertTrue(isTabSwitchedToSecond, "Таб должен меняться с 1 на 2");

        boolean isTabSwitchToThird = tabsPage.isTabSwitchToThird();
        softAssert.assertTrue(isTabSwitchToThird, "Таб должен меняться с 2 на 3");

        softAssert.assertAll();
    }
}
