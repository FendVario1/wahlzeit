package org.wahlzeit.model;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.wahlzeit.model.CartesianCoordinate.getCartesianCoordinate;

/**
 * All test cases of the class {@link Location}.
 */
public class LocationTest {

    @Test
    public void testLocation() {
        CartesianCoordinate coordinate = getCartesianCoordinate(1.1, 2.2, 3.3);
        Location location = new Location(coordinate);
        assertNotNull(location.getCoordinate());
        assertEquals(coordinate, location.getCoordinate());
    }

    @Test
    public void testSerialization() throws SQLException {
        CartesianCoordinate coordinate = mock(CartesianCoordinate.class);
        Location location = new Location(coordinate);
        ResultSet rset = mock(ResultSet.class);

        location.writeOn(rset);

        verify(coordinate, times(1)).writeOn(rset);
    }

    @Test
    public void testDeserialization() throws SQLException {
        CartesianCoordinate coordinate = mock(CartesianCoordinate.class);
        Location location = new Location(coordinate);
        ResultSet rset = mock(ResultSet.class);

        location.readFrom(rset);

        verify(coordinate, times(1)).readFrom(rset);
    }
}
