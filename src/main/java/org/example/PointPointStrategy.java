package org.example;

public class PointPointStrategy implements Strategy<Point, Point> {
    @Override
    public boolean intersects(Point one, Point two) {
        return one.getX() == two.getX() && one.getY() == two.getY();
    }
}
