package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * All test cases of the class {@link Photo}.
 */
public class PhotoTest {

    @Test
    public void testLocationStorage() {
        Photo photo = new Photo();
        assertNull(photo.getLocation());
        Location location = new Location(new Coordinate(1.1, 2.2, 3.3));
        photo.setLocation(location);
        assertEquals(location, photo.getLocation());
        photo = new Photo(location);
        assertEquals(location, photo.getLocation());
        photo = new Photo(new PhotoId(1234), location);
        assertEquals(location, photo.getLocation());
    }
}
