package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Point a = new Point(-1, -2);
        Segment ab = new Segment(new Point(-2,-4), new Point(2, 4));
        Segment cd = new Segment(new Point(3, -4), new Point(4, 4));
        Segment ef = new Segment(new Point(-1, 2), new Point(3, 1));

        Point p1 = new Point(1, -3);
        Point p2 = new Point(3, -2);
        Point p3 = new Point(3, -5);
        List<Point> list = new ArrayList<>(Arrays.asList(p1, p2, p3));
        Polygon polygon1 = new Polygon(list);
        Polygon polygon2 = new Polygon(new ArrayList<>(Arrays.asList(new Point(2, -3), new Point(4, -2), new Point(5, -3), new Point(4, -4))));

        PointSegmentStrategy pointSegmentStrategy = new PointSegmentStrategy();
        PointPolygonStrategy pointPolygonStrategy = new PointPolygonStrategy();
        SegmentSegmentStrategy segmentSegmentStrategy = new SegmentSegmentStrategy();
        SegmentPolygonStrategy segmentPolygonStrategy = new SegmentPolygonStrategy();
        PolygonPolygonStrategy polygonPolygonStrategy = new PolygonPolygonStrategy();

        System.out.println("does intersected point a AND segment ab: " + pointSegmentStrategy.intersects(a, ab));   // True
        System.out.println("does intersected segment cd AND segment ab: " + segmentSegmentStrategy.intersects(cd, ab));  // False
        System.out.println("does intersected segment ef AND segment ab: " + segmentSegmentStrategy.intersects(ef, ab));  // True
        System.out.println("does intersected segment ef AND segment cd: " + segmentSegmentStrategy.intersects(ef, cd));  // False

        System.out.println("does intersected polygon1 AND polygon2: " + polygonPolygonStrategy.intersects(polygon1, polygon2)); // True
        System.out.println("does intersected polygon1 AND point a: " + pointPolygonStrategy.intersects(a, polygon1)); // False
        System.out.println("does intersected polygon1 AND segment ef: " + segmentPolygonStrategy.intersects(ef, polygon1)); // False
        System.out.println("does intersected polygon1 AND segment cd: " + segmentPolygonStrategy.intersects(cd, polygon1)); // True
        System.out.println("does intersected polygon2 AND segment cd: " + segmentPolygonStrategy.intersects(cd, polygon2)); // True

        Group group = new Group(new ArrayList<>(Arrays.asList(a, cd, polygon2)));

        System.out.println("Group intersects count: " + group.findIntersections().size()); // cd & polygon2 = 2

        Group polygonsGroup = new Group(new ArrayList<>(Arrays.asList(polygon1, polygon2)));
        System.out.println("Group intersected area: " + group.getIntersectedArea()); // 0
        System.out.println("Group total area: " + group.getTotalArea());       // 3

        System.out.println("Polygon1 area: " + polygon1.getArea());
        System.out.println("Polygon2 area: " + polygon2.getArea());

        System.out.println("PolygonsGroup intersected area: " + polygonsGroup.getIntersectedArea()); // 0.5
        System.out.println("PolygonsGroup total area: " + polygonsGroup.getTotalArea());       // 5.5
    }
}