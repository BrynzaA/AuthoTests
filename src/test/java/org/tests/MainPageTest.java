package org.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tests.pages.MainPage;

public class MainPageTest extends BaseTest {

    @Test
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
    public void testMainPageSlider() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        SoftAssert softAssert = new SoftAssert();

        boolean isSliderWorks = mainPage.isSliderWorks();
        softAssert.assertTrue(isSliderWorks, "Слайдер должен меняться при свайпе");

        boolean isSliderWorksBackwards = mainPage.isSliderWorksBackwards();
        softAssert.assertTrue(isSliderWorksBackwards, "Слайдер должен возвращаться назад при свайпе туда-обратно");

        softAssert.assertAll();
    }

}
