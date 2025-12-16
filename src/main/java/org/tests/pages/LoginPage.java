package org.tests.pages;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tests.utils.ConfigReader;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "formly_1_input_username_0")
    private WebElement usernameFieldDesc;

    @FindBy(css = "body > div.jumbotron > div > div > div > form > div.form-actions > button")
    private WebElement loginButton;

    public void enterUsername(String username) {
        waitForElementVisible(usernameField);
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        waitForElementVisible(passwordField);
        passwordField.sendKeys(password);
    }

    public void enterUsernameDesc(String usernameDesc) {
        waitForElementVisible(usernameFieldDesc);
        usernameFieldDesc.sendKeys(usernameDesc);
    }

    public void clickLoginButton() {
        waitForElementVisible(loginButton);
        loginButton.click();
    }

    public LoginPage login(String username, String password, String usernameDesc) {
        enterUsername(username);
        enterPassword(password);
        enterUsernameDesc(usernameDesc);
        clickLoginButton();
        return this;
    }

    public boolean isLoginSuccess() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(ConfigReader.getLoginSuccessUrl()));

            return  new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions
                    .invisibilityOf(usernameField));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public LoginPage open() {
        driver.get(ConfigReader.getLoginUrl());
        waitForPageLoad();
        return this;
    }

}
