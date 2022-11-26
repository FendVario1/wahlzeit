package org.wahlzeit.model;

import org.wahlzeit.exceptions.WahlzeitIllegalAssertStateException;

public class SphericCoordinate extends AbstractCoordinate {

    private double phi;
    private double theta;
    private double radius;

    /**
     *
     * @methodtype constructor
     */
    public SphericCoordinate(double phi, double theta, double radius) {
        if(radius < 0) {
            throw new WahlzeitIllegalAssertStateException(SphericCoordinate.class.getName() + ": radius " + radius + " is not positive.");
        }
        this.phi = takeModulo(phi);
        this.theta = takeModulo(theta);
        this.radius = radius;
        incWriteCount();
        assertDoubleEquals(radius, radius);
        assertDoubleEquals(phi, takeModulo(phi));
        assertDoubleEquals(theta, takeModulo(theta));
        assertClassInvariants();
    }

    public double getPhi() {
        assertClassInvariants();
        return phi;
    }

    public double getTheta() {
        assertClassInvariants();
        return theta;
    }

    public double getRadius() {
        assertClassInvariants();
        return radius;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();
        double x = radius * Math.sin(theta) * Math.cos(phi);
        double y = radius * Math.sin(theta) * Math.sin(phi);
        double z = radius * Math.cos(theta);
        CartesianCoordinate coordinate = new CartesianCoordinate(x, y, z);
        assertClassEquals(CartesianCoordinate.class, coordinate.getClass());
        return coordinate;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();
        return this;
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        assertClassInvariants();
        assertNotNull(coordinate);
        return calculateCentralAngle(this, coordinate.asSphericCoordinate());
    }

    private static double calculateCentralAngle(SphericCoordinate a, SphericCoordinate b) {
        assertNotNull(a);
        assertNotNull(b);
        a.assertClassInvariants();
        b.assertClassInvariants();
        if(Math.abs(a.getRadius()-b.getRadius()) > 0.01)
            return Double.NaN;// return NaN as distance can not be calculated using this function
        return Math.acos(Math.sin(a.getPhi())*Math.sin(b.getPhi()) +
                Math.cos(a.getPhi())*Math.cos(b.getPhi())*Math.cos(a.getTheta()-b.getTheta()));
    }

    protected void loadValues(CartesianCoordinate coordinate) {
        assertClassInvariants();
        assertNotNull(coordinate);
        SphericCoordinate spheric = coordinate.asSphericCoordinate();
        radius = spheric.radius;
        phi = spheric.phi;
        theta = spheric.theta;
        assertDoubleEquals(radius, spheric.radius);
        assertDoubleEquals(phi, spheric.phi);
        assertDoubleEquals(theta, spheric.theta);
        assertClassInvariants();
    }

    protected static double takeModulo(double angle) {
        return floatMod(angle + Math.PI) - Math.PI;
    }

    // Handles negative angles
    // taken from SO: https://stackoverflow.com/a/55205438
    private static double floatMod(double x){
        return (x - Math.floor(x/(2*Math.PI)) * 2*Math.PI);
    }

    @Override
    public void assertClassInvariants() throws IllegalStateException {
        if(phi < -0.5*Math.PI || phi > 0.5*Math.PI)
            throw new WahlzeitIllegalAssertStateException(SphericCoordinate.class.getName() + ": phi " + phi + " is not within [-0.5*Pi, 0.5*Pi].");
        if(theta < -0.5*Math.PI || theta > 0.5*Math.PI)
            throw new WahlzeitIllegalAssertStateException(SphericCoordinate.class.getName() + ": theta " + theta + " is not within [-0.5*Pi, 0.5*Pi].");
        if(radius < 0) {
            throw new WahlzeitIllegalAssertStateException(SphericCoordinate.class.getName() + ": radius " + radius + " is not positive.");
        }
    }
}
