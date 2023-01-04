package org.wahlzeit.model;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.wahlzeit.model.CartesianCoordinate.getCartesianCoordinate;

/**
 * All test cases of the class {@link Photo}.
 */
public class PhotoTest {

    @Test
    public void testLocationStorage() {
        Photo photo = new Photo();
        assertNull(photo.getLocation());
        Location location = new Location(getCartesianCoordinate(1.1, 2.2, 3.3));
        photo.setLocation(location);
        assertEquals(location, photo.getLocation());
        photo = new Photo(location);
        assertEquals(location, photo.getLocation());
        photo = new Photo(new PhotoId(1234), location);
        assertEquals(location, photo.getLocation());
    }

    @Test
    public void testSerialization() throws SQLException {
        Location location = new Location(getCartesianCoordinate(1.1, 2.2, 3.3));
        ResultSet rset = mock(ResultSet.class);

        location.writeOn(rset);

        verify(rset, times(1)).updateLong("coordinate_x", 110000);
        verify(rset, times(1)).updateLong("coordinate_y", 220000);
        verify(rset, times(1)).updateLong("coordinate_z", 330000);
    }

    @Test
    public void testDeserialization() throws SQLException {
        Location location = mock(Location.class);
        ResultSet rset = mock(ResultSet.class);

        location.readFrom(rset);

        verify(location, times(1)).readFrom(rset);
    }
}
