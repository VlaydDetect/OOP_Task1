package org.example;

import java.util.List;

public class SegmentPolygonStrategy implements Strategy<Segment, Polygon> {
    @Override
    public boolean intersects(Segment one, Polygon two) {
        List<Point> vertices = two.getVertices();
        int numVertices = vertices.size();

        SegmentSegmentStrategy sss= new SegmentSegmentStrategy();

        for (int i = 0; i < numVertices; i++) {
            int j = (i + 1) % numVertices; // следующая вершина для построения стороны многоугольника

            Segment edge = new Segment(vertices.get(i), vertices.get(j));
            if (sss.intersects(one, edge)) {
                return true;
            }
        }

        return false;
    }
}
