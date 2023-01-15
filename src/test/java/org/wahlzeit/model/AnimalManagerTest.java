package org.wahlzeit.model;

import org.junit.Assert;
import org.junit.Test;

public class AnimalManagerTest {

    @Test
    public void testAnimalLink(){
        String trivial = "Rothschild's giraffe";
        String scientific = "rothschildi";

        AnimalManager manager = AnimalManager.getInstance();
        AnimalClassification genus = manager.getClassification("Giraffe", "Giraffa", ClassificationLevel.GENUS);
        AnimalSpecies species = manager.getSpecies("Northern giraffe", "camelopardalis", RedListState.VULNERABLE);
        AnimalSubspecies subspecies = manager.getSubSpecies(trivial, scientific, RedListState.NEAR_THREATENED);
        AnimalClassification.addSubtype(genus, species);
        AnimalClassification.addSubtype(species, subspecies);

        Animal animal = manager.createAnimal("Günther", Gender.MALE, trivial, scientific, RedListState.NEAR_THREATENED);
        Assert.assertEquals(subspecies, animal.getSpecies());
        Assert.assertTrue(genus.hasInstance(animal));
        Assert.assertTrue(manager.getAnimals().contains(animal));
        Assert.assertTrue(manager.getClassifications().contains(genus));
        Assert.assertTrue(manager.getClassifications().contains(subspecies));
    }
}
