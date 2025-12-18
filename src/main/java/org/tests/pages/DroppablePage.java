package org.tests.pages;

import io.qameta.allure.Step;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.tests.utils.ConfigReader;

public class DroppablePage extends BasePage {

    private WebElement element;

    public DroppablePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#wrapper > div.container.margin-top-20 > div.container.responsive-tabs-default > div.internal_navi > ul > li.active")
    private WebElement firstTestPageHref;

    @FindBy(css = "#wrapper > div.container.margin-top-20 > div.container.responsive-tabs-default > div.internal_navi > ul > li:nth-child(2)")
    private WebElement secondTestPageHref;

    @FindBy(css = "#wrapper > div.container.margin-top-20 > div.container.responsive-tabs-default > div.internal_navi > ul > li:nth-child(3)")
    private WebElement thirdTestPageHref;

    @FindBy(css = "#wrapper > div.container.margin-top-20 > div.container.responsive-tabs-default > div.internal_navi > ul > li:nth-child(4)")
    private WebElement forthTestPageHref;




    private WebElement getFirstTestPageHref() {
        waitForElementVisible(firstTestPageHref);
        firstTestPageHref.click();
        return firstTestPageHref;
    }

    private WebElement getSecondTestPageHref() {
        waitForElementVisible(secondTestPageHref);
        secondTestPageHref.click();
        return secondTestPageHref;
    }

    private WebElement getThirdTestPageHref() {
        waitForElementVisible(thirdTestPageHref);
        thirdTestPageHref.click();
        return thirdTestPageHref;
    }

    private WebElement getForthTestPageHref() {
        waitForElementVisible(forthTestPageHref);
        forthTestPageHref.click();
        return forthTestPageHref;
    }

    @Step("Переключиться на вкладку")
    public void switchToTab(int tabIndex) {
        switch (tabIndex) {
            case 1:
                getFirstTestPageHref();
                break;
            case 2:
                getSecondTestPageHref();
                break;
            case 3:
                getThirdTestPageHref();
                break;
            case 4:
                getForthTestPageHref();
                break;
        }
        driver.get(ConfigReader.getDroppableUrl() + "#example-1-tab-" + tabIndex);
        waitForPageLoad();
    }

    @Step("Переключиться на нужный iframe")
    public void switchToTabIframe(int tabIndex) {
        WebElement targetIframe = null;
        switch (tabIndex) {
            case 1:
                targetIframe = findIframeWithElement("droppable");
                break;
            case 2:
                targetIframe = findIframeWithElement("draggable-nonvalid");
                break;
            case 3:
                targetIframe = findIframeWithElement("droppable2-inner");
                break;
            case 4:
                targetIframe = findIframeWithElement("draggable2");
                break;
        }
        assert targetIframe != null;
        driver.switchTo().frame(targetIframe);
    }

    private void dropHere(WebElement draggable) {
        new Actions(driver).dragAndDrop(draggable, element)
                .pause(Duration.ofMillis(500))
                .perform();
    }

    private void dragALittleBit(WebElement draggable) {
        new Actions(driver).dragAndDropBy(draggable, 1, 1)
                .pause(Duration.ofMillis(500))
                .perform();
    }

    private void dragALittleBitNTimes(WebElement draggable, int times) {
        Actions actions = new Actions(driver);
        for (int i = 0; i < times; i++) {
            actions.dragAndDropBy(draggable, -1, -1);
        }
        actions.pause(Duration.ofMillis(500))
                .perform();
    }

    private String getText() {
        return element.getText();
    }

    private boolean isDropped() {
        return getText().equals("Dropped!");
    }

    private boolean isDropped(WebElement webElement) {
        return webElement.getText().equals("Dropped!");
    }

    @Step("Проверка, что перетягивание draggable на droppable вызывает изменение droppable на Dropped!")
    public boolean isDropsOnDrop() {

        WebElement draggable = driver.findElement(By.id("draggable"));

        element = driver.findElement(By.id("droppable"));

        dropHere(draggable);

        return isDropped();
    }

    @Step("Проверка, что перетягивание draggable не на droppable не вызывает изменение droppable на Dropped!")
    public boolean isNotDropsOnNotDrop() {
        WebElement draggable = driver.findElement(By.id("draggable"));

        element = driver.findElement(By.id("droppable"));

        dragALittleBit(draggable);

        return !isDropped();
    }

    @Step("Проверка, что перетягивание non-valid-draggable на droppable не вызывает изменение droppable на Dropped!")
    public boolean isAcceptTabNotDropOnNotDroppable() {
        WebElement draggableNonvalid = driver.findElement(By.id("draggable-nonvalid"));
        element = driver.findElement(By.id("droppable"));
        dropHere(draggableNonvalid);
        return !isDropped();
    }

    @Step("Проверка, что перетягивание draggable на non greedy droppable вызывает изменение внешнего droppable на Dropped!")
    public boolean isPreventPropagationTabNotGreedyDropsPropagateOnOuterDrop() {

        WebElement draggable = driver.findElement(By.id("draggable"));

        WebElement droppable = driver.findElement(By.id("droppable"));

        element = driver.findElement(By.id("droppable-inner"));

        dropHere(draggable);

        return isDropped() && droppable.getText().equals("Dropped!\nDropped!");
    }

    @Step("Проверка, что перетягивание draggable на greedy droppable не вызывает изменение внешнего droppable на Dropped!")
    public boolean isPreventPropagationTabGreedyDropsPropagateOnOuterDrop() {

        WebElement draggable = driver.findElement(By.id("draggable"));

        WebElement droppable2 = driver.findElement(By.id("droppable2"));

        element = driver.findElement(By.id("droppable2-inner"));

        dropHere(draggable);

        return isDropped() && !isDropped(droppable2);

    }

    @Step("Проверка, что перетягивание draggable на внешний droppable вызывает его изменение на Dropped!")
    public boolean isPreventPropagationTabGreedyOuterDrop() {
        WebElement draggable = driver.findElement(By.id("draggable"));

        element = driver.findElement(By.id("droppable2"));

        new  Actions(driver).clickAndHold(draggable)
                .moveToElement(element, 0, -50)
                .release().pause(Duration.ofMillis(500)).perform();

        return element.getText().equals("Dropped!\nDropped!");
    }

    @Step("Проверка, что перетягивание draggable, возвращающегося при попадании на droppable вызывает его возвращение на исходное место")
    public boolean isRevertDraggableTabRevertOnDrop() {
        WebElement draggable = driver.findElement(By.id("draggable"));
        element = driver.findElement(By.id("droppable"));
        Point oldLocation = draggable.getLocation();
        dropHere(draggable);
        return oldLocation.equals(draggable.getLocation());

    }

    @Step("Проверка, что перетягивание draggable, возвращающегося при попадании на droppable, не на droppable не вызывает его возвращение на исходное место")
    public boolean isRevertDraggableTabNonRevertOnDrop() {
        WebElement draggable = driver.findElement(By.id("draggable"));
        Point oldLocation = draggable.getLocation();
        dragALittleBit(draggable);
        return !oldLocation.equals(draggable.getLocation());
    }

    @Step("Проверка, что перетягивание draggable, возвращающегося при попадании не на droppable, не на droppable вызывает его возвращение на исходное место")
    public boolean isRevertDraggableTabNonRevertOnNonDrop() {
        WebElement draggable = driver.findElement(By.id("draggable2"));
        Point oldLocation = draggable.getLocation();
        dragALittleBit(draggable);
        return oldLocation.equals(draggable.getLocation());
    }

    @Step("Проверка, что перетягивание draggable, возвращающегося при попадании не на droppable, на droppable не вызывает его возвращение на исходное место")
    public boolean isRevertDraggableTabRevertOnNonDrop() {
        WebElement draggable = driver.findElement(By.id("draggable2"));
        element = driver.findElement(By.id("droppable"));
        Point oldLocation = draggable.getLocation();
        dropHere(draggable);
        return !oldLocation.equals(draggable.getLocation());
    }

    private WebElement findIframeWithElement(String elementId) {
        try {
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));

            for (WebElement iframe : iframes) {
                try {
                    driver.switchTo().frame(iframe);

                    List<WebElement> draggables = driver.findElements(By.id(elementId));
                    if (!draggables.isEmpty()) {
                        return iframe;
                    }
                } catch (Exception ignored) {
                } finally {
                    driver.switchTo().defaultContent();
                }
            }
            throw new RuntimeException("Не удалось найти iframe с " + elementId +" элементом");

        } catch (Exception e) {
            throw new RuntimeException("Не удалось найти iframe с " + elementId + " элементом", e);
        }
    }


    @Step("Открыть страницу с Drag and Drop")
    public void open() {
        driver.get(ConfigReader.getDroppableUrl());
        waitForPageLoad();
    }

}
