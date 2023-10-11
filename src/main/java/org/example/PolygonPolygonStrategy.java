package org.example;

import java.util.List;

public class PolygonPolygonStrategy implements Strategy<Polygon, Polygon> {
    @Override
    public boolean intersects(Polygon one, Polygon two) {
        List<Point> vertices = two.getVertices();
        int numVerticesOther = vertices.size();
        int numVerticesSelf = one.getVertices().size();

        SegmentSegmentStrategy sss = new SegmentSegmentStrategy();

        for (int i = 0; i < numVerticesSelf; i++) {
            int j = (i + 1) % numVerticesSelf;
            Segment edge1 = new Segment(one.getVertices().get(i), one.getVertices().get(j));

            for (int k = 0; k < numVerticesOther; k++) {
                int l = (k + 1) % numVerticesOther;
                Segment edge2 = new Segment(vertices.get(k), vertices.get(l));

                if (sss.intersects(edge1, edge2)) return true;
            }
        }

        return false;
    }
}
