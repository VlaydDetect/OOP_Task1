package org.example;

public class PointSegmentStrategy implements Strategy<Point, Segment> {
    @Override
    public boolean intersects(Point one, Segment two) {
        double x1 = two.getStart().getX();
        double y1 = two.getStart().getY();
        double x2 = two.getEnd().getX();
        double y2 = two.getEnd().getY();

        double a = y1 - y2;
        double b = x2 - x1;
        double c = x1 * y2 - x2 * y1;

        if (one.getX() >= Math.min(x1, x2) &&
                one.getX() <= Math.max(x1, x2) &&
                one.getY() >= Math.min(y1, y2) &&
                one.getY() <= Math.max(y1, y2)) {
            return a * one.getX() + b * one.getY() + c == 0;
        }

        return false;
    }
}
