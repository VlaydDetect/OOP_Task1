package org.example;

import java.util.ArrayList;
import java.util.List;

public class Polygon implements GeometryObject {
    private final List<Point> vertices;

    public Polygon(List<Point> vertices) {
        this.vertices = vertices;
    }

    public List<Point> getVertices() {
        return vertices;
    }

    public void addVertex(Point vertex) {
        this.vertices.add(vertex);
    }

    public void addVertices(List<Point> vertices) {
        this.vertices.addAll(vertices);
    }

    @Override
    public String toString() {
        return "Polygon{" + vertices + '}';
    }

    private List<Point> getIntersectionPoints(Polygon other) {
        List<Point> intersectionPoints = new ArrayList<>();

        for (int i = 0; i < vertices.size(); i++) {
            int j = (i + 1) % vertices.size();
            Point p1 = vertices.get(i);
            Point p2 = vertices.get(j);
            Segment edge1 = new Segment(p1, p2);

            for (int k = 0; k < other.getVertices().size(); k++) {
                int l = (k + 1) % other.getVertices().size();
                Point q1 = other.getVertices().get(k);
                Point q2 = other.getVertices().get(l);
                Segment edge2 = new Segment(q1, q2);

                Point intersectionPoint = edge1.getIntersectionPoint(edge2);

                if (intersectionPoint != null) {
                    intersectionPoints.add(intersectionPoint);
                }
            }
        }

        return intersectionPoints;
    }

    private boolean containsPoint(Point point) {
        int n = this.vertices.size();
        if (n < 3) return false;

        double x = point.getX();
        double y = point.getY();
        boolean inside = false;

        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = vertices.get(i).getX();
            double yi = vertices.get(i).getY();
            double xj = vertices.get(j).getX();
            double yj = vertices.get(j).getY();

            boolean intersect = ((yi > y) != (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi);

            if (intersect) {
                inside = !inside;
            }
        }

        return inside;
    }

    private List<Point> getPointsInsideOtherPolygon(Polygon other) {
        List<Point> pointInside = new ArrayList<>();

        for (Point vertex : vertices) {
            if (other.containsPoint(vertex)) {
                pointInside.add(vertex);
            }
        }

        return pointInside;
    }

    public double getIntersectedArea(Polygon other) {
        Polygon resultPolygon = new Polygon(new ArrayList<>());

        List<Point> intersectionPoints = this.getIntersectionPoints(other);
        intersectionPoints.addAll(this.getPointsInsideOtherPolygon(other));
        intersectionPoints.addAll(other.getPointsInsideOtherPolygon(this));

        resultPolygon.addVertices(intersectionPoints);

        return resultPolygon.getArea();
    }

    public double getArea() {
        int numVertices = this.vertices.size();

        if (numVertices < 3) return 0.0;

        double area = 0.0;
        for (int i = 0; i < numVertices; i++) {
            int j = (i + 1) % numVertices;
            area += vertices.get(i).getX() * vertices.get(j).getY() - vertices.get(j).getX() * vertices.get(i).getY();
        }

        area = Math.abs(area) / 2.0;
        return area;
    }
}
