package org.example;

import java.util.ArrayList;
import java.util.List;

public class Group implements GeometryObject {
    private final List<GeometryObject> objects;

    public Group(List<GeometryObject> objects) {
        this.objects = objects;
    }

    public void addObject(GeometryObject object) {
        this.objects.add(object);
    }

    @Override
    public String toString() {
        return "Group[" + objects + ']';
    }

    // Utility method
    private boolean intersectsAbstractStrategy(GeometryObject obj1, GeometryObject obj2) {
        if (obj1 instanceof Point point1) {
            if (obj2 instanceof Point point2) {
                return new PointPointStrategy().intersects(point1, point2);
            } else if (obj2 instanceof Segment segment) {
                return new PointSegmentStrategy().intersects(point1, segment);
            } else {
                return new PointPolygonStrategy().intersects(point1, (Polygon) obj2);
            }
        } else if (obj1 instanceof Segment segment1) {
            if (obj2 instanceof Point point) {
                return new PointSegmentStrategy().intersects(point, segment1);
            } else if (obj2 instanceof Segment segment2) {
                return new SegmentSegmentStrategy().intersects(segment1, segment2);
            } else {
                return new SegmentPolygonStrategy().intersects(segment1, (Polygon) obj2);
            }
        } else {
            if (obj2 instanceof Point point) {
                return new PointPolygonStrategy().intersects(point, (Polygon) obj1);
            } else if (obj2 instanceof Segment segment) {
                return new SegmentPolygonStrategy().intersects(segment, (Polygon) obj1);
            } else {
                return new PolygonPolygonStrategy().intersects((Polygon) obj1, (Polygon) obj2);
            }
        }
    }

    public List<GeometryObject> findIntersections() {
        List<GeometryObject> intersectingObjects = new ArrayList<>();

        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                GeometryObject object1 = objects.get(i);
                GeometryObject object2 = objects.get(j);

                // Maybe add objects as intersecting pairs
                if (intersectsAbstractStrategy(object1, object2)) {
                    if (!intersectingObjects.contains(object1)) {
                        intersectingObjects.add(object1);
                    }
                    if (!intersectingObjects.contains(object2)) {
                        intersectingObjects.add(object2);
                    }
                }
            }
        }

        return intersectingObjects;
    }

    public boolean intersects(GeometryObject object) {
        return findIntersections().contains(object);
    }

    public double getIntersectedArea() {
        if (objects == null || objects.isEmpty()) return 0.0;

        List<Polygon> polygons = new ArrayList<>();
        for (GeometryObject object : objects) {
            if (object instanceof Polygon) {
                polygons.add((Polygon) object);
            }
        }

        if (polygons.size() == 1) return 0.0;

        double area = 0.0;

        for (Polygon polygon1 : polygons) {
            for (Polygon polygon2 : polygons) {
                if (polygon1 != polygon2) {
                    area += polygon1.getIntersectedArea(polygon2);
                }
            }
        }

        return area / 2.0;
    }

    public double getTotalArea() {
        if (objects == null || objects.isEmpty()) return 0.0;

        List<Polygon> polygons = new ArrayList<>();
        for (GeometryObject object : objects) {
            if (object instanceof Polygon) {
                polygons.add((Polygon) object);
            }
        }

        if (polygons.size() == 1) return polygons.get(0).getArea();

        double area = 0.0;

        for (Polygon polygon1 : polygons) {
            for (Polygon polygon2 : polygons) {
                if (polygon1 != polygon2) {
                    area += polygon1.getArea() + polygon2.getArea() - polygon1.getIntersectedArea(polygon2);
                }
            }
        }

        return area / 2.0;
    }
}
