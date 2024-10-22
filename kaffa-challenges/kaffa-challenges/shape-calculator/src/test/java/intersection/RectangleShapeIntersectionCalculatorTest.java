package intersection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RectangleShapeIntersectionCalculatorTest {

    private final int[] rectangleA = {3, 5, 11, 11};
    private final int[] rectangleB = {7, 2, 13, 7};
    private final int[] rectangleC = {11, 11, 15, 13};

    @Test
    public void shouldReturnTrue_whenRectanglesIntersect() {
        Assertions.assertTrue(new RectangleShapeIntersectionCalculator(rectangleA, rectangleB).intersects());
        Assertions.assertTrue(new RectangleShapeIntersectionCalculator(rectangleA, rectangleC).intersects());
    }

    @Test
    public void shouldReturnFalse_whenRectanglesDoNotIntersect() {
        Assertions.assertFalse(new RectangleShapeIntersectionCalculator(rectangleB, rectangleC).intersects());
    }

    @Test
    public void shouldCalculateCorrectIntersectionArea_whenRectanglesIntersects() {
        Assertions.assertEquals(15, new RectangleShapeIntersectionCalculator(rectangleA, rectangleB).intersectionArea());
        Assertions.assertEquals(1, new RectangleShapeIntersectionCalculator(rectangleA, rectangleC).intersectionArea());
        Assertions.assertEquals(0, new RectangleShapeIntersectionCalculator(rectangleB, rectangleC).intersectionArea());
    }
}
