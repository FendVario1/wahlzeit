package org.wahlzeit.model;

public class AnimalSpecies extends AnimalClassification {
    protected final RedListState redListState;

    public AnimalSpecies(String trivial, String scientific, RedListState redListState) {
        super(trivial, scientific, ClassificationLevel.SPECIES);
        this.redListState = redListState;
    }

    protected AnimalSpecies(String trivial, String scientific, RedListState redListState, ClassificationLevel classificationLevel) {
        super(trivial, scientific, classificationLevel);
        this.redListState = redListState;
    }

    public RedListState getRedListState() {
        return redListState;
    }

    public String getHtmlString() {
        return "<b>" + trivialName + "</b> - <i>" + getFullScientificName() + "</i>";
    }

    @Override
    public String getTaxationTable() {// could be more fancy, but at least it is there :D
        return "<table><tr><td><i>" + redListState + "</i></td></tr>" + getTaxationTablePart() + "</table>";
    }

    @Override
    public String getFullScientificName() {
        if(supertype == null)
            return scientificName;
        return supertype.getScientificName() + " " + scientificName;
    }
}
