package org.wahlzeit.model;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

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

    @Test
    public void testSerialization() throws SQLException {
        Coordinate coordinate = mock(Coordinate.class);
        Location location = new Location(coordinate);
        ResultSet rset = mock(ResultSet.class);

        location.writeOn(rset);

        verify(coordinate, times(1)).writeOn(rset);
    }

    @Test
    public void testDeserialization() throws SQLException {
        Coordinate coordinate = mock(Coordinate.class);
        Location location = new Location(coordinate);
        ResultSet rset = mock(ResultSet.class);

        location.readFrom(rset);

        verify(coordinate, times(1)).readFrom(rset);
    }
}
