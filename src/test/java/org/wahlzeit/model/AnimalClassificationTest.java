package org.wahlzeit.model;

import org.junit.Assert;
import org.junit.Test;

public class AnimalClassificationTest {

    @Test
    public void testClassHierarchy() {
        AnimalClassification kingdom = new AnimalClassification("Animal", "Animalia", ClassificationLevel.KINGDOM);
        AnimalClassification phylum = new AnimalClassification("Chordate", "Chordata", ClassificationLevel.PHYLUM);
        AnimalClassification _class = new AnimalClassification("Mammal", "Mammalia", ClassificationLevel.CLASS);
        AnimalClassification order = new AnimalClassification("Even-toed ungulate", "Artiodactyla", ClassificationLevel.ORDER);
        AnimalClassification family = new AnimalClassification("Giraffidae", "Giraffidae", ClassificationLevel.FAMILY);
        AnimalClassification genus = new AnimalClassification("Giraffe", "Giraffa", ClassificationLevel.GENUS);
        AnimalSpecies species = new AnimalSpecies("Northern giraffe", "camelopardalis", RedListState.VULNERABLE);
        AnimalSubspecies subspecies = new AnimalSubspecies("Rothschild's giraffe", "rothschildi", RedListState.NEAR_THREATENED);

        AnimalClassification.addSubtype(kingdom, phylum);
        AnimalClassification.addSubtype(phylum, _class);
        AnimalClassification.addSubtype(_class, order);
        AnimalClassification.addSubtype(order, family);
        AnimalClassification.addSubtype(family, genus);
        AnimalClassification.addSubtype(genus, species);
        AnimalClassification.addSubtype(species, subspecies);

        Assert.assertEquals("Giraffa camelopardalis rothschildi", subspecies.getFullScientificName());
        Assert.assertEquals("Animalia Chordata Mammalia Artiodactyla Giraffidae Giraffa", genus.getFullScientificName());
        Assert.assertEquals("<table><tr><td><i>NEAR_THREATENED</i></td></tr><tr><td>Animalia</td></tr><tr><td>Chordata</td></tr><tr><td>Mammalia</td></tr><tr><td>Artiodactyla</td></tr><tr><td>Giraffidae</td></tr><tr><td>Giraffa</td></tr><tr><td>camelopardalis</td></tr><tr><td>rothschildi</td></tr></table>", subspecies.getTaxationTable());
        Assert.assertEquals("<b>Northern giraffe</b> - <i>Giraffa camelopardalis</i>", species.getHtmlString());
        Assert.assertEquals(species, subspecies.getSupertype());
        Assert.assertEquals("Rothschild's giraffe", subspecies.getTrivialName());
        Assert.assertEquals(RedListState.NEAR_THREATENED, subspecies.getRedListState());


        Assert.assertTrue(subspecies.isSubtypeOf(kingdom));
        Assert.assertTrue(kingdom.hasSubtype(subspecies));
        Assert.assertFalse(kingdom.isSubtypeOf(subspecies));
        Assert.assertFalse(kingdom.isSubtypeOf(new AnimalSpecies("Hippopotamus", "Hippopotamus", RedListState.VULNERABLE)));
    }
}
