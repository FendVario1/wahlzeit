package org.wahlzeit.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SphericCoordinate extends AbstractCoordinate {

    private double phi;
    private double theta;
    private double radius;

    /**
     *
     * @methodtype constructor
     */
    public SphericCoordinate(double phi, double theta, double radius) {
        this.phi = takeModulo(phi);
        this.theta = takeModulo(theta);
        this.radius = radius;
        incWriteCount();
    }

    public double getPhi() {
        return phi;
    }

    public double getTheta() {
        return theta;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        double x = radius * Math.sin(theta) * Math.cos(phi);
        double y = radius * Math.sin(theta) * Math.sin(phi);
        double z = radius * Math.cos(theta);
        return new CartesianCoordinate(x, y, z);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        return calculateCentralAngle(this, coordinate.asSphericCoordinate());
    }

    private static double calculateCentralAngle(SphericCoordinate a, SphericCoordinate b) {
        if(Math.abs(a.getRadius()-b.getRadius()) > 0.01)
            return Double.NaN;// return NaN as distance can not be calculated using this function
        return Math.acos(Math.sin(a.getPhi())*Math.sin(b.getPhi()) +
                Math.cos(a.getPhi())*Math.cos(b.getPhi())*Math.cos(a.getTheta()-b.getTheta()));
    }

    protected void loadValues(CartesianCoordinate coordinate) {
        SphericCoordinate spheric = coordinate.asSphericCoordinate();
        this.radius = spheric.radius;
        this.phi = spheric.phi;
        this.theta = spheric.theta;
    }

    protected static double takeModulo(double angle) {
        return floatMod(angle + Math.PI) - Math.PI;
    }

    // Handles negative angles
    // taken from SO: https://stackoverflow.com/a/55205438
    private static double floatMod(double x){
        return (x - Math.floor(x/(2*Math.PI)) * 2*Math.PI);
    }
}
