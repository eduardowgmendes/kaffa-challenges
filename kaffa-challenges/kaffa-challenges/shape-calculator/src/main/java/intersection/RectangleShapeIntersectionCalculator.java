package intersection;

public class RectangleShapeIntersectionCalculator implements ShapeIntersectionCalculator {

    private final int[] a;
    private final int[] b;

    public RectangleShapeIntersectionCalculator(int[] a, int[] b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Checks if two Rectangles intersects. Two rectangles intersect if:
     * <p>
     * The X intervals overlap.
     * The Y intervals overlap.
     * <p>
     * If the intervals on both axes overlap, then there is an intersection; otherwise, there is none.
     *
     * @return true if the rectangles intersects, false otherwise
     */
    @Override
    public boolean intersects() {
        int x1a = a[0], y1a = a[1], x2a = a[2], y2a = a[3];
        int x1b = b[0], y1b = b[1], x2b = b[2], y2b = b[3];

        boolean intersectsAtX = x1a <= x2b && x2a >= x1b;
        boolean intersectsAtY = y1a <= y2b && y2a >= y1b;

        return intersectsAtX && intersectsAtY;
    }

    /**
     * Computes the intersection area of two rectangles if they in fact intersects.
     * Each rectangle is defined by two points, for example, @code A = (x1A, y1A; x2A, y2A).
     * Assuming @code (x1A, y1A) is the bottom-left corner and @code (x2A, y2A) is the top-right corner of rectangle A.
     * <p>
     * To determine the intersection of the dimensions, the horizontal intersection must be between the intervals [x1A, x2A] and [x1_B, x2_B],
     * and the vertical intersection must be between the intervals [y1A, y2A] and [y1B, y2B].
     * <p>
     * To find the intersection limits:
     * <p>
     * On the X-axis (horizontal): the start of the intersection is the maximum value between the starting coordinates of the two rectangles, i.e.,@code Math.max(x1A, x1_B).
     * The end of the intersection will be the minimum value between the ending coordinates of the rectangles, @code Math.min(x2A, x2_B).
     * <p>
     * On the Y-axis (vertical): the start of the intersection is Math.max(y1A, y1B), and the end of the intersection is Math.min(y2A, y2B).
     * <p>
     * If the intersection limits form a valid interval, meaning the start of the intersection is less than the end, the area of the intersection will be calculated.
     * Otherwise, the intersection will be zero.
     * <p>
     * The intersection area is calculated as:
     * <p>
     * Area = (end of xAxis − start of xAxis + 1) × (end of yAxis − start of yAxis + 1)
     *
     * @return The value of intersection area between two rectangles
     */
    @Override
    public int intersectionArea() {
        int x1a = a[0], y1a = a[1], x2a = a[2], y2a = a[3];
        int x1b = b[0], y1b = b[1], x2b = b[2], y2b = b[3];

        int x1Intersection = Math.max(x1a, x1b);
        int x2Intersection = Math.min(x2a, x2b);
        int y1Intersection = Math.max(y1a, y1b);
        int y2Intersection = Math.min(y2a, y2b);

        if (x1Intersection <= x2Intersection && y1Intersection <= y2Intersection)
            return (x2Intersection - x1Intersection + 1) * (y2Intersection - y1Intersection + 1);

        return 0;
    }
}
