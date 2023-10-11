package org.example;

public interface Strategy<K extends GeometryObject, T extends GeometryObject> {
    boolean intersects(K one, T two);
}
