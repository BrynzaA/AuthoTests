package org.tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage{

    public MainPage(WebDriver driver) {
        super(driver);
    }

    private static final String URL = "https://www.way2automation.com/";

    @FindBy(className = "ast-above-header-bar")
    private WebElement header;

    @FindBy(xpath = "//div[@data-elementor-type='footer']")
    private WebElement footer;

    public void open() {
        driver.get(URL);
    }

    public boolean isHeaderDisplayed() {
        return header.isDisplayed();
    }

    public boolean isFooterDisplayed() {
        return footer.isDisplayed();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
