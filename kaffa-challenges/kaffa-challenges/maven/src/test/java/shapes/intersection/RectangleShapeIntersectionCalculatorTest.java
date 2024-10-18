package shapes.intersection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RectangleShapeIntersectionCalculatorTest {

    private final int[] rectangleA = {3, 5, 11, 11};
    private final int[] rectangleB = {7, 2, 13, 7};
    private final int[] rectangleC = {11, 11, 15, 13};

    @Test
    public void rectanglesIntersects() {
        Assertions.assertTrue(new RectangleShapeIntersectionCalculator(rectangleA, rectangleB).intersects());
        Assertions.assertTrue(new RectangleShapeIntersectionCalculator(rectangleA, rectangleC).intersects());
    }

    @Test
    public void rectanglesNotIntersects() {
        Assertions.assertFalse(new RectangleShapeIntersectionCalculator(rectangleB, rectangleC).intersects());
    }

    @Test
    public void intersectionArea() {
        Assertions.assertEquals(new RectangleShapeIntersectionCalculator(rectangleA, rectangleB).intersectionArea(), 15);
        Assertions.assertEquals(new RectangleShapeIntersectionCalculator(rectangleA, rectangleC).intersectionArea(), 1);
        Assertions.assertEquals(new RectangleShapeIntersectionCalculator(rectangleB, rectangleC).intersectionArea(), 0);
    }
}
