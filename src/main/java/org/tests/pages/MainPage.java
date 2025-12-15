package org.tests.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tests.utils.ConfigReader;

public class MainPage extends BasePage {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "ast-above-header-bar")
    private WebElement header;

    @FindBy(xpath = "/html/body/div[1]/div[2]")
    private WebElement footer;

    @FindBy(className = "elementor-swiper")
    private WebElement slider;

    @FindBy(xpath = "//div[contains(@class, 'elementor-swiper')]//img")
    private List<WebElement> sliderImages;

    public void open() {
        driver.get(ConfigReader.getMainUrl());
        waitForPageLoad();
    }

    public boolean isHeaderDisplayed() {
        return waitForElementVisible(header).isDisplayed();
    }

    public boolean isFooterDisplayed() {
        return waitForElementVisible(footer).isDisplayed();
    }

    public boolean isSliderWorks() {
        try {
            waitForElementVisible(slider, 5);

            WebElement currentImage = waitForElement(By.xpath(
                    "//div[contains(@class, 'elementor-swiper')]//img[not(contains(@style, 'display: none'))]"
            ), 5);

            String initialSrc = currentImage.getAttribute("src");

            Thread.sleep(1000);

            Actions action = new Actions(driver);
            action.clickAndHold(slider)
                    .pause(Duration.ofMillis(500))
                    .moveByOffset(-300, 0)
                    .pause(Duration.ofMillis(500))
                    .release()
                    .perform();

            Thread.sleep(2000);

            WebElement newImage = waitForElement(By.xpath(
                    "//div[contains(@class, 'elementor-swiper')]//img[not(contains(@style, 'display: none'))]"
            ), 5);

            String newSrc = newImage.getAttribute("src");

            return !initialSrc.equals(newSrc);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSliderWorksBackwards() {
        try {
            waitForElementVisible(slider, 5);

            WebElement initialImage = waitForElement(By.xpath(
                    "//div[contains(@class, 'elementor-swiper')]//img[not(contains(@style, 'display: none'))]"
            ), 5);

            String initialSrc = initialImage.getAttribute("src");

            Thread.sleep(1000);

            Actions action = new Actions(driver);
            action.clickAndHold(slider)
                    .pause(Duration.ofMillis(500))
                    .moveByOffset(-300, 0)
                    .pause(Duration.ofMillis(500))
                    .release()
                    .perform();

            Thread.sleep(2000);

            action.clickAndHold(slider)
                    .pause(Duration.ofMillis(500))
                    .moveByOffset(300, 0)
                    .pause(Duration.ofMillis(500))
                    .release()
                    .perform();

            Thread.sleep(2000);

            WebElement returnedImage = waitForElement(By.xpath(
                    "//div[contains(@class, 'elementor-swiper')]//img[not(contains(@style, 'display: none'))]"
            ), 5);

            String returnedSrc = returnedImage.getAttribute("src");

            return initialSrc.equals(returnedSrc);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isHeaderScrolledToFooterVisible() {
        waitForElementVisible(footer, 5);

        Actions action = new Actions(driver);
        action.scrollToElement(footer)
                .perform();


        return header.isDisplayed();
    }

    public boolean isHeaderScrolledToFixCoordinateVisible() {
        waitForElementVisible(header, 5);

        Actions action = new Actions(driver);
        action.scrollByAmount(0, 300)
                .pause(Duration.ofMillis(500))
                .moveByOffset(-100, 0)
                .perform();


        return header.isDisplayed();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}