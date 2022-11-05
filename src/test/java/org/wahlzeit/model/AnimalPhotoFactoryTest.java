package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnimalPhotoFactoryTest {

    @Test
    public void testClassUsage() {
        AnimalPhotoFactory.initialize();
        PhotoFactory factory = PhotoFactory.getInstance();
        assertEquals(AnimalPhotoFactory.class, factory.getClass());
    }
}
