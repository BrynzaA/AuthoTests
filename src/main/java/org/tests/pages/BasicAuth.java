package org.tests.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.tests.utils.ConfigReader;

public class BasicAuth extends BasePage {


    @FindBy(css = "#displayImage")
    public WebElement displayImageButton;

    @FindBy(css = "#downloadImg")
    public WebElement downloadImg;


    public BasicAuth(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу с Basic Auth")
    public void open() {
        driver.get(ConfigReader.getBasicAuthUrl());
        waitForPageLoad();
    }


    public boolean isBasicAuthWorks(String username, String password) {
        String imgBeforeAuth = downloadImg.getAttribute("src");

        String originalUrl = driver.getCurrentUrl();
        String replacedUrl = "https://"+ username +":" + password + "@";
        String authUrl = originalUrl.replace("https://", replacedUrl);

        driver.get(authUrl);

        waitForElementVisible(displayImageButton);
        displayImageButton.click();

        String imgAfterAuth = downloadImg.getAttribute("src");

        return !imgAfterAuth.equals(imgBeforeAuth);
    }
}
