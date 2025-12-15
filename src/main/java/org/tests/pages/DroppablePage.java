package org.tests.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.tests.utils.ConfigReader;

public class DroppablePage extends BasePage {

    public DroppablePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "/html/body/section/div[1]/div[1]/div[1]/ul/li[1]/a")
    private WebElement firstTestPageHref;

    @FindBy(xpath = "/html/body/section/div[1]/div[1]/div[1]/ul/li[2]/a")
    private WebElement secondTestPageHref;

    @FindBy(xpath = "/html/body/section/div[1]/div[1]/div[1]/ul/li[3]/a")
    private WebElement thirdTestPageHref;

    @FindBy(xpath = "/html/body/section/div[1]/div[1]/div[1]/ul/li[4]/a")
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

    public boolean isFirstTestPageWorks() {
        waitForElementVisible(firstTestPageHref);
        if (!firstTestPageHref.isDisplayed()) return false;

        firstTestPageHref.click();

        driver.get(ConfigReader.getDroppableUrl() + "#example-1-tab-1");
        waitForPageLoad();

        WebElement targetIframe = findIframeWithElement("droppable");

        driver.switchTo().frame(targetIframe);

        WebElement draggable = driver.findElement(By.id("draggable"));

        WebElement droppable = driver.findElement(By.id("droppable"));


        try {
            Actions actions = new Actions(driver);
            actions.dragAndDrop(draggable, droppable).perform();

            Thread.sleep(2000);

            return droppable.getText().equals("Dropped!");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSecondTestPageWorks() {
        waitForElementVisible(secondTestPageHref);
        if (!secondTestPageHref.isDisplayed()) return false;

        secondTestPageHref.click();

        driver.get(ConfigReader.getDroppableUrl() + "#example-1-tab-2");
        waitForPageLoad();

        WebElement targetIframe = findIframeWithElement("draggable-nonvalid");

        driver.switchTo().frame(targetIframe);

        WebElement draggableNonvalid = driver.findElement(By.id("draggable-nonvalid"));

        WebElement draggable = driver.findElement(By.id("draggable"));

        WebElement droppable = driver.findElement(By.id("droppable"));


        try {
            Actions actions = new Actions(driver);
            actions.dragAndDrop(draggableNonvalid, droppable).perform();

            Thread.sleep(2000);

            if (droppable.getText().equals("Dropped!")) return false;

            actions.dragAndDrop(draggable, droppable).perform();

            Thread.sleep(2000);

            return droppable.getText().equals("Dropped!");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    public boolean isThirdTestPageWorks() {
        waitForElementVisible(thirdTestPageHref);
        if (!thirdTestPageHref.isDisplayed()) return false;

        thirdTestPageHref.click();

        driver.get(ConfigReader.getDroppableUrl() + "#example-1-tab-3");
        waitForPageLoad();

        WebElement targetIframe = findIframeWithElement("droppable2-inner");

        driver.switchTo().frame(targetIframe);

        WebElement draggable = driver.findElement(By.id("draggable"));

        WebElement droppable = driver.findElement(By.id("droppable"));

        WebElement droppableInner = driver.findElement(By.id("droppable-inner"));

        WebElement droppable2 = driver.findElement(By.id("droppable2"));

        WebElement droppable2Inner = driver.findElement(By.id("droppable2-inner"));


        try {
            Actions actions = new Actions(driver);
            actions.dragAndDrop(draggable, droppable).perform();

            Thread.sleep(2000);

            actions.clickAndHold(draggable)
                    .moveToElement(droppable2, 0, -50)
                    .release().perform();

            Thread.sleep(2000);

            actions.dragAndDrop(draggable, droppable2Inner).perform();

            Thread.sleep(2000);

            actions.dragAndDrop(draggable, droppableInner).perform();

            Thread.sleep(2000);



            return droppable.getText().equals("Dropped!\nDropped!")
                    && droppableInner.getText().equals("Dropped!")
                    && droppable2.getText().equals("Dropped!\nDropped!")
                    && droppable2Inner.getText().equals("Dropped!");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isForthTestPageWorks() {
        waitForElementVisible(forthTestPageHref);
        if (!forthTestPageHref.isDisplayed()) return false;

        forthTestPageHref.click();

        driver.get(ConfigReader.getDroppableUrl() + "#example-1-tab-4");
        waitForPageLoad();

        WebElement targetIframe = findIframeWithElement("draggable2");
        driver.switchTo().frame(targetIframe);

        WebElement draggable2 = driver.findElement(By.id("draggable2"));
        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement droppable = driver.findElement(By.id("droppable"));

        try {
            Point oldReturnOnDropLocation = draggable.getLocation();
            Point oldNotReturnOnDropLocation = draggable2.getLocation();
            Actions actions = new Actions(driver);

            actions.dragAndDrop(draggable, droppable).perform();
            Thread.sleep(2000);

            boolean isDraggableTriggerDrop = droppable.getText().equals("Dropped!");
            if (!isDraggableTriggerDrop) return false;

            boolean isDraggableReturned = draggable.getLocation().equals(oldReturnOnDropLocation);
            if (!isDraggableReturned) return false;

            actions.dragAndDropBy(draggable, oldReturnOnDropLocation.x + 10, oldReturnOnDropLocation.y + 10).perform();
            Thread.sleep(2000);

            boolean isDraggableNotStayed = !draggable.getLocation().equals(oldReturnOnDropLocation);
            if (!isDraggableNotStayed) return false;

            actions.dragAndDropBy(draggable2, 0, 0).perform();
            Thread.sleep(2000);

            boolean isDraggable2ReturnedOutside = draggable2.getLocation().equals(oldNotReturnOnDropLocation);
            if (!isDraggable2ReturnedOutside) return false;

            actions.dragAndDrop(draggable2, droppable).perform();
            Thread.sleep(2000);

            boolean isDraggable2TriggerDrop = droppable.getText().equals("Dropped!");
            if (!isDraggable2TriggerDrop) return false;

            Point newReturnOnDropLocation = draggable2.getLocation();

            actions.dragAndDrop(draggable2, draggable).perform();
            Thread.sleep(2000);

            boolean isDraggable2ReturnedFromDrop = draggable2.getLocation().equals(newReturnOnDropLocation);
            if (!isDraggable2ReturnedFromDrop) return false;

            actions.dragAndDropBy(draggable2, 1, 1).perform();
            Thread.sleep(2000);

            boolean isDraggable2MovedInside = !draggable2.getLocation().equals(newReturnOnDropLocation);
            if (!isDraggable2MovedInside) return false;

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                driver.switchTo().defaultContent();
            } catch (Exception e) {
                // Игнорируем
            }
        }
    }

    private WebElement findIframeWithElement(String elementId) {
        try {
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));

            for (WebElement iframe : iframes) {
                try {
                    driver.switchTo().frame(iframe);

                    List<WebElement> draggables = driver.findElements(By.id(elementId));
                    if (!draggables.isEmpty()) {
                        System.out.println("Найден " + elementId + " в iframe");
                        return iframe;
                    }
                } catch (Exception ignored) {
                } finally {
                    driver.switchTo().defaultContent();
                }
            }
            throw new RuntimeException("Не удалось найти iframe с draggable элементом");

        } catch (Exception e) {
            throw new RuntimeException("Не удалось найти iframe с draggable элементом", e);
        }
    }



    public void open() {
        driver.get(ConfigReader.getDroppableUrl());
        waitForPageLoad();
    }
}
