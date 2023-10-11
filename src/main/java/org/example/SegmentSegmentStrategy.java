package org.example;

public class SegmentSegmentStrategy implements Strategy<Segment, Segment> {

    @Override
    public boolean intersects(Segment one, Segment two) {
        if (one.getStart().getX() == one.getEnd().getX() && one.getStart().getY() == one.getEnd().getY()) {
            Point p = new Point(one.getStart().getX(), one.getStart().getY());
            return new PointSegmentStrategy().intersects(p, two);
        }

        double x1 = one.getStart().getX();
        double y1 = one.getStart().getY();
        double x2 = one.getEnd().getX();
        double y2 = one.getEnd().getY();

        double x3 = two.getStart().getX();
        double y3 = two.getStart().getY();
        double x4 = two.getEnd().getX();
        double y4 = two.getEnd().getY();

        // проверка на параллельность
        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        if (denominator == 0) {
            // Совпадают ли прямые
            return x1 == x3 && x2 == x4 && y1 == y3 && y2 == y4;
        }

        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
        double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / denominator;

        // проверка, что точка пересения лежит на обоих отрезках
        return t >= 0 && t <= 1 && u >= 0 && u <= 1;
    }
}
