package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnimalPhotoManagerTest {

    @Test
    public void testClassUsage() {
        AnimalPhotoManager.initialize();
        PhotoManager manager = PhotoManager.getInstance();
        assertEquals(AnimalPhotoManager.class, manager.getClass());
    }
}
