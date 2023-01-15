package org.wahlzeit.model;

public class Animal {

    protected AnimalSpecies species;

    protected String name;
    protected Gender gender;

    protected Animal(String name, Gender gender, AnimalSpecies species) {
        this.name = name;
        this.gender = gender;
        this.species = species;
    }

    public AnimalSpecies getSpecies() {
        return species;
    }

    public void setSpecies(AnimalSpecies species) {
        this.species = species;
    }
}
