package org.wahlzeit.model;

import org.wahlzeit.exceptions.WahlzeitIllegalAssertStateException;

import java.util.ArrayList;
import java.util.List;

public class AnimalManager {

    private static AnimalManager instance = null;

    private final List<AnimalClassification> classifications = new ArrayList<>();
    private final List<Animal> animals = new ArrayList<>();

    private AnimalManager(){}

    public static synchronized AnimalManager getInstance() {
        if(instance == null)
            instance = new AnimalManager();
        return instance;
    }

    public synchronized Animal createAnimal(String name, Gender gender, String speciesTrivial, String speciesScientific, RedListState redListState) {
        assertIsValidGender(gender);
        AnimalSpecies species = getSpecies(speciesTrivial, speciesScientific, redListState);
        Animal animal = new Animal(name, gender, species);
        animals.add(animal);
        return animal;
    }

    public synchronized AnimalClassification getClassification(String speciesTrivial, String speciesScientific, ClassificationLevel classificationLevel) {
        // species are identifiable by their scientific name and classification level
        AnimalClassification classification = this.classifications.stream().filter(s -> s.getScientificName().equals(speciesScientific) && s.getClassificationLevel() == classificationLevel).findFirst().orElse(null);
        if(classification == null) {
            classification = new AnimalClassification(speciesTrivial, speciesScientific, classificationLevel);
            this.classifications.add(classification);
        }
        return classification;
    }

    public synchronized AnimalSpecies getSpecies(String speciesTrivial, String speciesScientific, RedListState redListState) {
        // species are identifiable by their scientific name
        AnimalSpecies species = (AnimalSpecies) this.classifications.stream().filter(s -> s.getScientificName().equals(speciesScientific)).findFirst().orElse(null);
        if(species == null) {
            species = new AnimalSpecies(speciesTrivial, speciesScientific, redListState);
            this.classifications.add(species);
        }
        return species;
    }

    public synchronized AnimalSubspecies getSubSpecies(String speciesTrivial, String speciesScientific, RedListState redListState) {
        // species are identifiable by their scientific name
        AnimalSubspecies species = (AnimalSubspecies) this.classifications.stream().filter(s -> s.getScientificName().equals(speciesScientific)).findFirst().orElse(null);
        if(species == null) {
            species = new AnimalSubspecies(speciesTrivial, speciesScientific, redListState);
            this.classifications.add(species);
        }
        return species;
    }

    public List<Animal> getAnimals() {
        return new ArrayList<>(animals);
    }

    public List<AnimalClassification> getClassifications() {
        return new ArrayList<>(classifications);
    }

    private void assertIsValidGender(Gender gender) {
        switch (gender) {
            case MALE:
            case FEMALE:
            case OTHER:
                break;
            default:
                throw new WahlzeitIllegalAssertStateException("Gender '" + gender + "' is not a valid gender value for an animal." );
        }
    }
}
