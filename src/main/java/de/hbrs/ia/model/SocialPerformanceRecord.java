package de.hbrs.ia.model;

import org.bson.Document;

import java.sql.SQLIntegrityConstraintViolationException;

public class SocialPerformanceRecord {
    private Integer year;
    private Integer goalId;
    private String goalDesc;
    private Integer value;
    private Integer actualValue;

    public SocialPerformanceRecord(Integer goalId, String goalDesc, Integer year, Integer value, Integer actualValue) {
        this.year = year;
        this.value = value;
        this.actualValue = actualValue;
        this.goalDesc = goalDesc;
        this.goalId = goalId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getvalue() {
        return value;
    }

    public void setvalue(Integer value) {
        this.value = value;
    }

    public Integer getactualValue() {
        return actualValue;
    }

    public void setactualValue(Integer actualValue) {
        this.actualValue = actualValue;
    }

    public String getgoalDesc() {
        return goalDesc;
    }

    public void setgoalDesc(String goalDesc) {
        this.goalDesc = goalDesc;
    }

    public Integer getgoalId() {
        return goalId;
    }

    public void setgoalId(Integer goalId) {
        this.goalId = goalId;
    }

    public Document toDocument() {
        return new Document ("goalId", this.goalId).append("goalDesc", this.goalDesc).append("year", this.year).append("value", this.value).append("actualValue", this.actualValue);
    }
}
