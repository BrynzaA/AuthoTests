package org.tests.pages;

import io.qameta.allure.Step;
import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.Cookie;
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

    public Set<Cookie> cookies;

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "formly_1_input_username_0")
    private WebElement usernameFieldDesc;

    @FindBy(css = "body > div.jumbotron > div > div > div > form > div.form-actions > button")
    private WebElement loginButton;

    @FindBy(css = "body > div.jumbotron > div > div > div > div.alert.alert-danger.ng-binding.ng-scope")
    private WebElement failedLoginAlert;

    @Step("Ввести имя пользователя")
    public void enterUsername(String username) {
        waitForElementVisible(usernameField);
        usernameField.sendKeys(username);
    }

    @Step("Ввести пароль")
    public void enterPassword(String password) {
        waitForElementVisible(passwordField);
        passwordField.sendKeys(password);
    }

    @Step("Ввести описание пользователя")
    public void enterUsernameDesc(String usernameDesc) {
        waitForElementVisible(usernameFieldDesc);
        usernameFieldDesc.sendKeys(usernameDesc);
    }

    @Step("Нажать кнопку 'Login'")
    public void clickLoginButton() {
        waitForElementVisible(loginButton);
        loginButton.click();
    }

    @Step("Выполнить логин с username={username}, password={password}, description={usernameDesc}")
    public LoginPage login(String username, String password, String usernameDesc) {
        enterUsername(username);
        enterPassword(password);
        enterUsernameDesc(usernameDesc);
        clickLoginButton();
        return this;
    }

    @Step("Проверить успешность логина")
    public boolean isLoginSuccess() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(ConfigReader.getLoginSuccessUrl()));

            cookies = driver.manage().getCookies();

            return  new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions
                    .invisibilityOf(usernameField));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Step("Проверить что открылась страница пост-логина")
    public boolean isPageIsPageAfterLogin() {
        return driver.getCurrentUrl().equals(ConfigReader.getLoginSuccessUrl());
    }

    @Step("Проверить доступность кнопки логина")
    public boolean isButtonActive() {
        return loginButton.isEnabled();
    }

    @Step("Проверить не успешность логина")
    public boolean isLoginFailed() {
        try {

            return  waitForElementVisible(failedLoginAlert, 1).isDisplayed();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Step("Открыть страницу логина")
    public LoginPage open() {
        driver.get(ConfigReader.getLoginUrl());
        waitForPageLoad();
        return this;
    }

    @Step("Открыть страницу закрытую за формой логина с куками")
    public void openSuccessLoginPageWithCookie(Set<Cookie> fileCookies) {
        fileCookies.forEach(cookie -> driver.manage().addCookie(cookie));
        driver.get(ConfigReader.getLoginSuccessUrl());
        waitForPageLoad();
    }

    @Step("Получить куки")
    public Set<Cookie> getCookies() {
        return cookies;
    }

    @Step("Убрать фокус из поля ввода")
    public void removeFocusFromInput(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        if (element != null) {
            js.executeScript("arguments[0].blur();", element);
        } else {
            js.executeScript("if(document.activeElement) { document.activeElement.blur(); }");
        }
    }

    @Step("Убрать фокус с поля username")
    public void removeFocusFromUsernameField() {
        removeFocusFromInput(usernameField);
    }

    @Step("Проверить есть ли фокус на элементе")
    public boolean hasFocus(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (Boolean) js.executeScript(
                "return document.activeElement === arguments[0];",
                element
        );
    }

    @Step("Проверить есть ли фокус на поле username")
    public boolean hasFocusOnUsernameField() {
        return hasFocus(usernameField);
    }

    @Step("Проверить наличие вертикального скролла на странице")
    public boolean hasVerticalScroll() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (Boolean) js.executeScript(
                "return document.documentElement.scrollHeight > document.documentElement.clientHeight;"
        );
    }

    @Step("Проверить наличие горизонтального скролла на странице")
    public boolean hasHorizontalScroll() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (Boolean) js.executeScript(
                "return document.documentElement.scrollWidth > document.documentElement.clientWidth;"
        );
    }
}
