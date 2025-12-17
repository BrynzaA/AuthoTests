package org.tests.pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.tests.utils.ConfigReader;

public class MainPage extends BasePage {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "ast-above-header-bar")
    private WebElement header;

    @FindBy(css = "#page > div.elementor.elementor-25361.elementor-location-footer.nitro-offscreen > div")
    private WebElement footer;

    @FindBy(css = "#post-17 > div > div > section.elementor-section.elementor-top-section.elementor-element.elementor-element-14f241ad.elementor-section-boxed.elementor-section-height-default.nitro-offscreen")
    private WebElement slider;

    @FindBy(xpath = "//div[contains(@class, 'elementor-swiper')]//img[not(contains(@style, 'display: none'))]")
    private WebElement sliderImg;


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
            waitForSlider();

            String initialSrc = getImg(sliderImg);


            Actions action = new Actions(driver);
            action.clickAndHold(slider)
                    .pause(Duration.ofMillis(500))
                    .moveByOffset(-500, 0)
                    .pause(Duration.ofMillis(500))
                    .release()
                    .perform();

            action.pause(Duration.ofMillis(1000)).perform();

            String newSrc = getImg(sliderImg);

            return !initialSrc.equals(newSrc);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSliderWorksBackwards() {
        try {
            waitForSlider();

            String initialSrc = getImg(sliderImg);


            Actions action = new Actions(driver);
            action.dragAndDropBy(slider, 500, 0)
                    .pause(Duration.ofMillis(500))
                    .release()
                    .pause(Duration.ofMillis(1000))
                    .perform();


            action.dragAndDropBy(slider, -500, 0)
                    .pause(Duration.ofMillis(500))
                    .release()
                    .pause(Duration.ofMillis(1000))
                    .perform();


            String returnedSrc = getImg(sliderImg);

            return initialSrc.equals(returnedSrc);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isHeaderScrolledToFooterVisible() {
        waitForElementVisible(footer, 1);

        Actions action = new Actions(driver);
        action.scrollToElement(footer)
                .perform();


        return header.isDisplayed();
    }

    public boolean isHeaderScrolledToFixCoordinateVisible() {
        waitForElementVisible(header, 1);

        Actions action = new Actions(driver);
        action.scrollByAmount(0, -300)
                .pause(Duration.ofMillis(500))
                .moveByOffset(0, 100)
                .perform();


        return header.isDisplayed();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getImg(WebElement sliderImg) {
        return sliderImg.getAttribute("src");
    }

    public void waitForSlider() {
        waitForElementVisible(slider, 5);

        waitForElementVisible(sliderImg, 5);
    }
}