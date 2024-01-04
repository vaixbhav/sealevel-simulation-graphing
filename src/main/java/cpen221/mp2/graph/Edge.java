package cpen221.mp2.graph;

import java.util.NoSuchElementException;

public class Edge<V extends Vertex> {

    private V v1;
    private V v2;
    private int length;

    public Edge(V v1, V v2) {
        this(v1, v2, 1);
    }

    public Edge(V v1, V v2, int length) {
        if (v1 == null || v2 == null) {
            throw new IllegalArgumentException("Vertices cannot be null");
        }
        if (v1.equals(v2)) {
            throw new IllegalArgumentException("The same vertex cannot be at both ends of an edge");
        }
        if (length < 0) {
            throw new IllegalArgumentException("Edge weight cannot be negative");
        }
        this.v1 = v1;
        this.v2 = v2;
        this.length = length;
    }

    public V v1() {
        return v1;
    }

    public V v2() {
        return v2;
    }

    public int length() {
        return length;
    }

    public boolean equals(Object o) {
        if (o instanceof Edge<?>) {
            Edge<?> other = (Edge<?>) o;
            if (other.v1.equals(this.v1) && other.v2.equals(this.v2)) {
                return true;
            }
            if (other.v1.equals(this.v2) && other.v2.equals(this.v1)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return v1.hashCode() + v2.hashCode();
    }

    public boolean incident(V v) {
        if (v == null) {
            return false;
        }
        if (v.equals(v1) || v.equals(v2)) {
            return true;
        }
        return false;
    }
}
