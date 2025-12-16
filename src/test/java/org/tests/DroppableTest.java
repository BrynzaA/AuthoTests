package org.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tests.pages.DroppablePage;

public class DroppableTest extends BaseTest {


    @Test
    public void testDefaultFunctionalityTab() {
        DroppablePage droppablePage = new DroppablePage(driver);
        droppablePage.open();
        SoftAssert softAssert = new SoftAssert();

        droppablePage.switchToTab(1);
        droppablePage.switchToTabIframe(1);

        boolean isDefaultFunctionalityTabNotDropsOnNotDrop = droppablePage.isNotDropsOnNotDrop();

        softAssert.assertTrue(isDefaultFunctionalityTabNotDropsOnNotDrop, "Droppable drop without draggable");

        boolean isDropsOnDrop = droppablePage.isDropsOnDrop();

        softAssert.assertTrue(isDropsOnDrop, "Draggable don't drop droppable");

        softAssert.assertAll();
    }

    @Test
    public void testAcceptTab() {
        DroppablePage droppablePage = new DroppablePage(driver);
        droppablePage.open();
        SoftAssert softAssert = new SoftAssert();

        droppablePage.switchToTab(2);
        droppablePage.switchToTabIframe(2);

        boolean isAcceptTabNotDropsOnNotDrop = droppablePage.isNotDropsOnNotDrop();
        softAssert.assertTrue(isAcceptTabNotDropsOnNotDrop, "Droppable drop without draggable");

        boolean isAcceptTabNotDropOnNotDroppable = droppablePage.isAcceptTabNotDropOnNotDroppable();
        softAssert.assertTrue(isAcceptTabNotDropOnNotDroppable, "Droppable drop on non valid draggable");

        boolean isDropsOnDrop = droppablePage.isDropsOnDrop();
        softAssert.assertTrue(isDropsOnDrop, "Draggable don't drop droppable");

        softAssert.assertAll();
    }

    @Test
    public void testPreventPropagationTab() {
        DroppablePage droppablePage = new DroppablePage(driver);
        droppablePage.open();
        SoftAssert softAssert = new SoftAssert();

        droppablePage.switchToTab(3);
        droppablePage.switchToTabIframe(3);

        boolean isPreventPropagationTabNotDropsOnNotDrop = droppablePage.isNotDropsOnNotDrop();
        softAssert.assertTrue(isPreventPropagationTabNotDropsOnNotDrop, "Droppable drop without draggable");

        boolean isPreventPropagationTabNotGreedyDropsPropagateOnOuterDrop = droppablePage.isPreventPropagationTabNotGreedyDropsPropagateOnOuterDrop();
        softAssert.assertTrue(isPreventPropagationTabNotGreedyDropsPropagateOnOuterDrop, "Outer droppable should drop on drop of inner not greedy droppable");

        boolean isPreventPropagationTabGreedyDropsPropagateOnOuterDrop = droppablePage.isPreventPropagationTabGreedyDropsPropagateOnOuterDrop();
        softAssert.assertTrue(isPreventPropagationTabGreedyDropsPropagateOnOuterDrop, "Outer droppable shouldn't drop on drop of inner greedy droppable");

        boolean isPreventPropagationTabGreedyOuterDrop = droppablePage.isPreventPropagationTabGreedyOuterDrop();
        softAssert.assertTrue(isPreventPropagationTabGreedyOuterDrop, "Outer droppable2 should drop");

        softAssert.assertAll();

    }

    @Test
    public void testRevertDraggablePositionTab() {
        DroppablePage droppablePage = new DroppablePage(driver);
        droppablePage.open();
        SoftAssert softAssert = new SoftAssert();

        droppablePage.switchToTab(4);
        droppablePage.switchToTabIframe(4);

        boolean isRevertDraggablePositionTabNotDropsOnNotDrop = droppablePage.isNotDropsOnNotDrop();
        softAssert.assertTrue(isRevertDraggablePositionTabNotDropsOnNotDrop, "Droppable drop without draggable");

        boolean isDropsOnDrop = droppablePage.isDropsOnDrop();
        softAssert.assertTrue(isDropsOnDrop, "Draggable don't drop droppable");

        boolean isRevertDraggableTabRevertOnDrop = droppablePage.isRevertDraggableTabRevertOnDrop();
        softAssert.assertTrue(isRevertDraggableTabRevertOnDrop, "Revertible don't revert on drop");

        boolean isRevertDraggableTabNonRevertOnDrop = droppablePage.isRevertDraggableTabNonRevertOnDrop();
        softAssert.assertTrue(isRevertDraggableTabNonRevertOnDrop, "Revertible revert on non drop");

        boolean isRevertDraggableTabNonRevertOnNonDrop = droppablePage.isRevertDraggableTabNonRevertOnNonDrop();
        softAssert.assertTrue(isRevertDraggableTabNonRevertOnNonDrop, "Non revertible don't revert on non drop");

        boolean isRevertDraggableTabRevertOnNonDrop = droppablePage.isRevertDraggableTabRevertOnNonDrop();
        softAssert.assertTrue(isRevertDraggableTabRevertOnNonDrop, "Non revertible revert on drop");

        softAssert.assertAll();
    }
}