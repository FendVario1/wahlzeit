package org.wahlzeit.model;

import org.wahlzeit.exceptions.WahlzeitIllegalAssertStateException;

import java.util.HashSet;

public class AnimalClassification {

    protected final String trivialName;
    protected final String scientificName;

    protected final ClassificationLevel classificationLevel;

    protected AnimalClassification supertype;

    protected HashSet<AnimalClassification> subtypes = new HashSet<>();

    public AnimalClassification(String trivial, String scientific, ClassificationLevel classificationLevel) {
        trivialName = trivial;
        scientificName = scientific;
        this.classificationLevel = classificationLevel;
    }

    public String getTrivialName() {
        return this.trivialName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getFullScientificName() {
        if(supertype == null)
            return scientificName;
        return supertype.getFullScientificName() + " " + scientificName;
    }

    public String getHtmlString() {
        return "<b>" + trivialName + "</b> - <i>" + getScientificName() + "</i>";
    }

    public String getTaxationTable() {
        return "<table>" + getTaxationTablePart() + "</table>";
    }

    protected String getTaxationTablePart() {
        String table = "";
        if(this.supertype != null)
            table += this.supertype.getTaxationTablePart();
        table += "<tr><td>" + getScientificName() + "</td></tr>";
        return table;
    }

    protected void setSuperType(AnimalClassification classification) {
        if(classification == null)
            throw new WahlzeitIllegalAssertStateException("Tried to set null for Supertype.");
        supertype = classification;
    }

    public AnimalClassification getSupertype() {
        return supertype;
    }

    protected void addSubtype(AnimalClassification classification) {
        if(classification == null)
            throw new WahlzeitIllegalAssertStateException("Tried to set null for Subtype.");
        subtypes.add(classification);
    }

    protected void removeSubtype(AnimalClassification classification) {
        subtypes.remove(classification);
    }

    public static void addSubtype(AnimalClassification superEl, AnimalClassification subEl) {
        if(superEl == null)
            throw new WahlzeitIllegalAssertStateException("Tried to set null for Supertype.");
        if(subEl == null)
            throw new WahlzeitIllegalAssertStateException("Tried to set null for Subtype.");
        superEl.addSubtype(subEl);
        if(subEl.getSupertype() != null) {
            subEl.getSupertype().removeSubtype(subEl);
        }
        subEl.setSuperType(superEl);
    }

    public HashSet<AnimalSpecies> getSubtypes() {
        return (HashSet<AnimalSpecies>) subtypes.clone();
    }

    public ClassificationLevel getClassificationLevel() {
        return classificationLevel;
    }

    public boolean isSubtypeOf(AnimalClassification classification) {
        if(classification == null)
            return false;
        if(classification == this)
            return true;
        if(supertype == null)
            return false;
        return supertype.isSubtypeOf(classification);
    }

    public boolean hasSubtype(AnimalClassification classification) {
        if(classification == null)
            return false;
        if(this == classification)
            return true;
        for (AnimalClassification s: subtypes) {
            if(s.hasSubtype(classification))
                return true;
        }
        return false;
    }

    public boolean hasInstance(Animal animal) {
        if(animal == null)
            return false;
        if(this == animal.getSpecies())
            return true;
        for (AnimalClassification s: subtypes) {
            if(s.hasInstance(animal))
                return true;
        }
        return false;
    }
}
