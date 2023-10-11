package org.example;

public class PointPolygonStrategy implements Strategy<Point, Polygon> {
    @Override
    public boolean intersects(Point one, Polygon two) {
        PointPointStrategy pps = new PointPointStrategy();
        for (Point vertex : two.getVertices()) {
            if (pps.intersects(vertex, one)) return true;
        }

        return false;
    }
}
