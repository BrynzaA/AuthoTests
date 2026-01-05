package org.tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tests.pages.MainPage;

@Epic("Ключевые компоненты главной страницы")
@Feature("Главная страница")
@Owner("A Brynza")
public class MainPageTest extends BaseTest {

    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            captureScreenshot(result.getMethod().getMethodName());
        }
    }

    @Test
    @Story("Успешное отображение главной страницы")
    @Description("Тест проверяет корректное отображение главной страницы")
    @Severity(SeverityLevel.BLOCKER)
    public void testMainPageBasic() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        String pageTitle = mainPage.getPageTitle();
        Assert.assertNotNull(pageTitle, "Заголовок страницы не должен быть null");

        String currentUrl = mainPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("way2automation"),
                "URL должен содержать 'way2automation'");
    }

    @Test
    @Story("Успешное отображение хэдера и футера главной страницы")
    @Description("Тест проверяет успешное отображение хэдера и футера главной страницы")
    @Severity(SeverityLevel.NORMAL)
    public void testMainPageElements() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        SoftAssert softAssert = new SoftAssert();

        boolean isHeaderDisplayed = mainPage.isHeaderDisplayed();
        softAssert.assertTrue(isHeaderDisplayed, "Хедер должен отображаться");

        boolean isFooterDisplayed = mainPage.isFooterDisplayed();
        softAssert.assertTrue(isFooterDisplayed, "Футер должен отображаться");

        softAssert.assertAll();


    }

    @Test
    @Feature("Успешное отображение хэдера при скроллинге главной страницы")
    @Description("Тест проверяет успешное отображение хэдера при скроллинге главной страницы")
    @Severity(SeverityLevel.MINOR)
    public void testMainPageScrolledHeader() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        SoftAssert softAssert = new SoftAssert();

        boolean isHeaderScrolledToFixCoordinateVisible = mainPage.isHeaderScrolledToFixCoordinateVisible();
        softAssert.assertTrue(isHeaderScrolledToFixCoordinateVisible, "Главное меню должно быть видно при скроллинге");

        boolean isHeaderScrolledToFooterVisible = mainPage.isHeaderScrolledToFooterVisible();
        softAssert.assertTrue(isHeaderScrolledToFooterVisible, "Главное меню должно быть видно при скроллинге в конец страницы");

        softAssert.assertAll();
    }

    @Test
    @Feature("Успешное взаимодействие со слайдером на главной страницы")
    @Description("Тест проверяет успешное взаимодействие со слайдером на главной страницы")
    public void testMainPageSlider() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        SoftAssert softAssert = new SoftAssert();

        boolean isSliderWorks = mainPage.isSliderWorks();
        softAssert.assertTrue(isSliderWorks, "Слайдер должен меняться при свайпе");

//        boolean isSliderWorksBackwards = mainPage.isSliderWorksBackwards();
//        softAssert.assertTrue(isSliderWorksBackwards, "Слайдер должен возвращаться назад при свайпе туда-обратно");

        softAssert.assertAll();
    }

    //Задача U4. Screenshots
//    @Test(retryAnalyzer = SingleTestRetryAnalyzer.class)
//    @Story("Падающий тест - проверка заголовка страницы")
//    @Description("Тест специально падает из-за неверного заголовка")
//    @Severity(SeverityLevel.MINOR)
//    public void testFailingPageTitle() {
//        MainPage mainPage = new MainPage(driver);
//        mainPage.open();
//
//        String pageTitle = mainPage.getPageTitle();
//        Assert.assertEquals(pageTitle, "Неправильный заголовок",
//                "Этот тест специально падает из-за неверного заголовка");
//    }

}
