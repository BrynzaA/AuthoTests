package org.tests.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.tests.utils.ConfigReader;

public class AlertsPage extends BasePage {
    public AlertsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#wrapper > div.container.margin-top-20 > div.container.responsive-tabs-default > div.internal_navi > ul > li:nth-child(2) > a")
    public WebElement inputAlertTab;

    private WebElement getInputAlertTab() {
        waitForElementVisible(inputAlertTab);
        inputAlertTab.click();
        return inputAlertTab;
    }

    @Step("Открыть страницу с Alerts")
    public void open() {
        driver.get(ConfigReader.getAlertsUrl());
        waitForPageLoad();
    }

    @Step("Проверка передачи текста из Alerts")
    public boolean isAlertBlockPassText(String testText) {
        getInputAlertTab();
        WebElement iframe = driver.findElement(By.cssSelector("#example-1-tab-2 iframe"));
        switchToIframe(iframe);

        WebElement button = driver.findElement(By.cssSelector("body > button"));

        button.click();

        Alert alert = driver.switchTo().alert();
        alert.sendKeys(testText);
        alert.accept();

        driver.switchTo().defaultContent();

        switchToIframe(iframe);

        WebElement demoField = driver.findElement(By.id("demo"));
        return demoField.getText().contains(testText);
    }

    private void switchToIframe(WebElement iframe) {
        driver.switchTo().frame(iframe);

    }

}
