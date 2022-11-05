package org.wahlzeit.model;


import org.junit.Test;
import org.wahlzeit.utils.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * All test cases of the class {@link AnimalPhoto}.
 */
public class AnimalPhotoTest {

    @Test
    public void testSerialization() throws SQLException {
        ResultSet rset = mock(ResultSet.class);
        AnimalPhoto photo = new AnimalPhoto();
        photo.setSpecies("Fox");
        // set values that would otherwise create errors.
        photo.ownerHomePage = StringUtil.asUrl("https://example.org");

        photo.writeOn(rset);

        verify(rset, times(1)).updateString(AnimalPhoto.speciesLabel, "Fox");
        // test Photo fields are still serialized
        verify(rset, times(1)).updateInt("id", photo.getId().asInt());
    }

    @Test
    public void testDeserialization() throws SQLException {
        ResultSet rset = mock(ResultSet.class);
        // return valid values, as otherwise the test would fail
        when(rset.getString("owner_email_address")).thenReturn("test@example.org");
        when(rset.getString("owner_home_page")).thenReturn("https://example.org");

        AnimalPhoto photo = new AnimalPhoto();

        photo.readFrom(rset);

        verify(rset, times(1)).getString(AnimalPhoto.speciesLabel);
        // test Photo fields are still correctly populated
        verify(rset, times(1)).getInt("id");
    }

    @Test
    public void testClassUsage() {
        AnimalPhotoFactory.initialize();
        Photo photo = PhotoFactory.getInstance().createPhoto();
        assertEquals(AnimalPhoto.class, photo.getClass());
    }
}
