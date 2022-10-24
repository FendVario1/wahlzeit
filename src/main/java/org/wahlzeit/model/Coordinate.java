package org.wahlzeit.model;

/**
 * Class representing coordinates in a 3-dimensional cartesian system
 */
public class Coordinate {

    private final double x;
    private final double y;
    private final double z;

    /**
     *
     * @methodtype constructor
     */
    public Coordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getDistance(Coordinate coordinate) {
        return Math.sqrt(Math.pow(coordinate.getX() - x, 2) + Math.pow(coordinate.getY() - y, 2) + Math.pow(coordinate.getZ() - z, 2));
    }

    public boolean isEqual(Coordinate coordinate) {
        return Math.abs(coordinate.getX() - x) <= delta && Math.abs(coordinate.getY() - y) <= delta && Math.abs(coordinate.getZ() - z) <= delta;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != Coordinate.class)
            return false;
        if (this == obj)
            return true;
        return isEqual((Coordinate) obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
