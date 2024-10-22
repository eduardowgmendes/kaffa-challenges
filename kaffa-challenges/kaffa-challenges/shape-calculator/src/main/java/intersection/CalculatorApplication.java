package intersection;

import java.util.Arrays;

public class CalculatorApplication {

    public static void main(String[] args) {

        String argsCoordinatesA = args[0];
        String argsCoordinatesB = args[1];

        if (argsCoordinatesA != null && argsCoordinatesB != null) {
            if (argsCoordinatesA.isEmpty()) {
                System.err.print("coordinates args for RectangleA cannot be empty");
            } else if (argsCoordinatesB.isEmpty()) {
                System.err.print("coordinates args for RectangleB cannot be empty");
            } else {

                int[] coordinatesA = parseCoordinates(argsCoordinatesA);
                int[] coordinatesB = parseCoordinates(argsCoordinatesB);

                if (isNotEmpty(coordinatesA) && isNotEmpty(coordinatesB)) {
                    RectangleShapeIntersectionCalculator calculator = new RectangleShapeIntersectionCalculator(coordinatesA, coordinatesB);

                    boolean intersects = calculator.intersects();
                    System.out.printf("Rectangle A intersects Rectangle B ? %s", intersects);

                    if (intersects) {
                        int intersectionArea = calculator.intersectionArea();
                        System.out.printf("\nThe intersection area of rectangles is %d", intersectionArea);
                    } else {
                        System.out.printf("\nIntersection not detected with these coordinates:\nA:%s\nB:%s\n", Arrays.toString(coordinatesA), Arrays.toString(coordinatesB));
                    }
                } else {
                    System.out.printf("one or more coordinates args are not valid. Try again following this: %s", "java -jar shape-calculator-1.0-SNAPSHOT.jar \"0, 0, 0, 0\" \"0, 0, 0, 0\"");
                }
            }
        } else {
            System.out.printf("must provide coordinates args. Try again following this: %s", "java -jar shape-calculator-1.0-SNAPSHOT.jar \"0, 0, 0, 0\" \"0, 0, 0, 0\"");
        }
    }

    private static int[] parseCoordinates(String coordinates) {
        if (coordinates == null) return null;
        return Arrays.stream(coordinates.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static boolean isNotEmpty(int[] values) {
        return values.length > 0;
    }
}
