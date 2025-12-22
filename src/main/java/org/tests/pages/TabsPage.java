package org.tests.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.tests.utils.ConfigReader;

public class TabsPage extends BasePage {
    public TabsPage(WebDriver driver) {
        super(driver);
    }


    @FindBy(css = "#wrapper > div.container.margin-top-20 > div.container.responsive-tabs-default > div.internal_navi > ul > li.active > a")
    private WebElement openNewWindowTab;

    @FindBy(css = "#wrapper > div.container.margin-top-20 > div.container.responsive-tabs-default > div.internal_navi > ul > li:nth-child(2) > a")
    private WebElement openSeparateNewWindowTab;

    @FindBy(css = "#wrapper > div.container.margin-top-20 > div.container.responsive-tabs-default > div.internal_navi > ul > li:nth-child(3) > a")
    private WebElement framesetTab;

    @FindBy(css = "#wrapper > div.container.margin-top-20 > div.container.responsive-tabs-default > div.internal_navi > ul > li:nth-child(4) > a")
    private WebElement openMultipleWindowsTab;

    private WebElement getOpenNewWindowTab() {
        waitForElementVisible(openNewWindowTab);
        openNewWindowTab.click();
        return openNewWindowTab;
    }

    private WebElement getOpenSeparateNewWindowTab() {
        waitForElementVisible(openSeparateNewWindowTab);
        openSeparateNewWindowTab.click();
        return openSeparateNewWindowTab;
    }

    private WebElement getFramesetTab() {
        waitForElementVisible(framesetTab);
        framesetTab.click();
        return framesetTab;
    }

    private WebElement getOpenMultipleWindowsTab() {
        waitForElementVisible(openMultipleWindowsTab);
        openMultipleWindowsTab.click();
        return openMultipleWindowsTab;
    }

    @Step("Открыть страницу с Tabs")
    public void open() {
        driver.get(ConfigReader.getTabsUrl());
        waitForPageLoad();
    }

    @Step("Проверка смены таба с OPEN NEW WINDOW на OPEN SEPARATE WINDOW")
    public boolean isTabSwitchToSecond() {
        getOpenNewWindowTab();
        String firstTabText = openNewWindowTab.getText();
        getOpenSeparateNewWindowTab();
        String secondTabText = openSeparateNewWindowTab.getText();
        return !firstTabText.equals(secondTabText);
    }

    @Step("Проверка смены таба с OPEN SEPARATE WINDOW на FRAMESET")
    public boolean isTabSwitchToThird() {
        String secondTabText = openSeparateNewWindowTab.getText();
        getFramesetTab();
        String thirdTabText = framesetTab.getText();
        return !secondTabText.equals(thirdTabText);
    }
}
