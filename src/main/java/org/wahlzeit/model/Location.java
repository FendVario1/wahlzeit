package org.wahlzeit.model;

import org.jetbrains.annotations.NotNull;

public class Location {

    public final Coordinate coordinate;


    /**
     *
     * @methodtype constructor
     */
    public Location(@NotNull Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
