package de.hbrs.ia.model;

import org.bson.Document;

public class SpecifiedSocialPerformanceRecord {
    private Integer targetValue;
    private Integer actualValue;
    private Integer bonus;
    private String name;

    public SpecifiedSocialPerformanceRecord(int targetValue, int actualValue, int bonus) {
        this.targetValue = targetValue;
        this.actualValue = actualValue;
        this.bonus = bonus;
    }

    public int getActualValue() {
        return actualValue;
    }

    public int getBonus() {
        return bonus;
    }

    public int getTargetValue() {
        return targetValue;
    }

    public Document toDocument() {
        return new Document("targetValue", targetValue).append("actualValue", actualValue).append("bonus", bonus);
    }
    public void setName(String name) {
        this.name = name;
    }
}
