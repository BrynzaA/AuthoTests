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

    }

}
