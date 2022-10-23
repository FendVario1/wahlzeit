package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * All test cases of the class {@link Location}.
 */
public class LocationTest {

    @Test
    public void testLocation() {
        Coordinate coordinate = new Coordinate(1.1, 2.2, 3.3);
        Location location = new Location(coordinate);
        assertNotNull(location.coordinate);
        assertEquals(coordinate, location.coordinate);
    }
}
