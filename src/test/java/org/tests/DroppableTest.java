package org.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.tests.pages.DroppablePage;

public class DroppableTest extends BaseTest {

    @Test
    public void testDroppablePage() {
        DroppablePage droppablePage = new DroppablePage(driver);
        droppablePage.open();

//        boolean isFirstTestPageWorks = droppablePage.isFirstTestPageWorks();
//        System.out.println("Первая droppable страница работает: " + isFirstTestPageWorks);
//        Assert.assertTrue(isFirstTestPageWorks);

//        boolean isSecondTestPageWorks = droppablePage.isSecondTestPageWorks();
//        System.out.println("Вторая droppable страница работает: " + isSecondTestPageWorks);
//        Assert.assertTrue(isSecondTestPageWorks);

//        boolean isThirdTestPageWorks = droppablePage.isThirdTestPageWorks();
//        System.out.println("Третья droppable страница работает: " + isThirdTestPageWorks);
//        Assert.assertTrue(isThirdTestPageWorks);

        boolean isForthTestPageWorks = droppablePage.isForthTestPageWorks();
        System.out.println("Четвертая droppable страница работает: " + isForthTestPageWorks);
        Assert.assertTrue(isForthTestPageWorks);
    }

}
