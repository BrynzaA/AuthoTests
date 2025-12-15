package org.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.tests.pages.MainPage;

public class MainPageTest extends BaseTest {

    @Test
    public void testMainPageElements() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        String pageTitle = mainPage.getPageTitle();
        System.out.println("Заголовок страницы: " + pageTitle);
        Assert.assertNotNull(pageTitle, "Заголовок страницы не должен быть null");

        String currentUrl = mainPage.getCurrentUrl();
        System.out.println("Текущий URL: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("way2automation"),
                "URL должен содержать 'way2automation'");

        boolean isHeaderDisplayed = mainPage.isHeaderDisplayed();
        System.out.println("Хедер отображается: " + isHeaderDisplayed);
        Assert.assertTrue(isHeaderDisplayed, "Хедер должен отображаться");

        boolean isFooterDisplayed = mainPage.isFooterDisplayed();
        System.out.println("Футер отображается: " + isFooterDisplayed);
        Assert.assertTrue(isFooterDisplayed, "Футер должен отображаться");

        boolean isSliderWorks = mainPage.isSliderWorks();
        System.out.println("Слайдер меняется: " + isSliderWorks);
        Assert.assertTrue(isSliderWorks, "Слайдер должен меняться при свайпе");

        boolean isSliderWorksBackwards = mainPage.isSliderWorksBackwards();
        System.out.println("Слайдер возвращается назад: " + isSliderWorksBackwards);
        Assert.assertTrue(isSliderWorksBackwards, "Слайдер должен возвращаться назад при свайпе туда-обратно");

        boolean isHeaderScrolledToFooterVisible = mainPage.isHeaderScrolledToFooterVisible();
        System.out.println("Главное меню видно при скроллинге в конец страницы: " + isHeaderScrolledToFooterVisible);
        Assert.assertTrue(isHeaderScrolledToFooterVisible, "Главное меню должно быть видно при скроллинге в конец страницы");

        boolean isHeaderScrolledToFixCoordinateVisible = mainPage.isHeaderScrolledToFixCoordinateVisible();
        System.out.println("Главное меню видно при скроллинге: " + isHeaderScrolledToFixCoordinateVisible);
        Assert.assertTrue(isHeaderScrolledToFixCoordinateVisible, "Главное меню должно быть видно при скроллинге");
    }

}
