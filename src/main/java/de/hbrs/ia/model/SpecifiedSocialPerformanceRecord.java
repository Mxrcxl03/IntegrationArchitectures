package de.hbrs.ia.model;

import org.bson.Document;

public class SpecifiedSocialPerformanceRecord {
    private short targetValue;
    private short actualValue;
    private short bonus;
    private String name;

    public SpecifiedSocialPerformanceRecord(short targetValue, short actualValue, short bonus) {
        this.targetValue = targetValue;
        this.actualValue = actualValue;
        this.bonus = bonus;
    }

    public short getActualValue() {
        return actualValue;
    }

    public short getBonus() {
        return bonus;
    }

    public short getTargetValue() {
        return targetValue;
    }

    public Document toDocument() {
        return new Document("targetValue", targetValue).append("actualValue", actualValue).append("bonus", bonus);
    }
    public void setName(String name) {
        this.name = name;
    }
}
