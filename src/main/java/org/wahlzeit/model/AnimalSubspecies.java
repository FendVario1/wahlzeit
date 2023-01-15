package org.wahlzeit.model;

public class AnimalSubspecies extends AnimalSpecies {

    public AnimalSubspecies(String trivial, String scientific, RedListState redListState) {
        super(trivial, scientific, redListState, ClassificationLevel.SUBSPECIES);
    }

    public String getFullScientificName() {
        if(supertype == null)
            return scientificName;
        return supertype.getFullScientificName() + " " + scientificName;
    }

}
