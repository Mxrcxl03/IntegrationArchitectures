package de.hbrs.ia.model;

import org.bson.Document;

import java.util.List;

public class SocialPerformanceRecord {
    private Integer year;
    private Integer goalId;
    private short value;
    private short actualValue;
    private List<SpecifiedSocialPerformanceRecord> SSPR;

    public SocialPerformanceRecord(Integer goalId, Integer year, Integer value, Integer actualValue) {
        this.year = year;
        this.value = averageTargetValue();
        this.actualValue = averageActualValue();
        this.goalId = goalId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public short getvalue() {
        return value;
    }

    public void setvalue(short value) {
        this.value = value;
    }

    public short getactualValue() {
        return actualValue;
    }

    public void setactualValue(short actualValue) {
        this.actualValue = actualValue;
    }

    public Integer getgoalId() {
        return goalId;
    }

    public void setgoalId(Integer goalId) {
        this.goalId = goalId;
    }

    public short averageActualValue() {
        short x = 0;
        for (SpecifiedSocialPerformanceRecord specifiedSocialPerformanceRecord : SSPR) {
            x += specifiedSocialPerformanceRecord.getActualValue();
        }
        return (short) (x / (SSPR.size()));
    }

    public short averageTargetValue() { //rundung fehlt
        short x = 0;
        for (SpecifiedSocialPerformanceRecord specifiedSocialPerformanceRecord : SSPR) {
            x += specifiedSocialPerformanceRecord.getTargetValue();
        }
        return (short) (x / (SSPR.size()));
    }

    public Document toDocument() {
        return new Document ("goalId", this.goalId).append("year", this.year).append("value", this.value).append("actualValue", this.actualValue).append("SSPR", this.SSPR);
    }

    public void addLeadershipCompetence(short targetValue, short actualValue, short bonus){
        SpecifiedSocialPerformanceRecord LeadershipCompetence = new SpecifiedSocialPerformanceRecord(targetValue, actualValue, bonus);
        SSPR.add(LeadershipCompetence);
        LeadershipCompetence.setName("Leadership Competence");
    }
    public void addOpenessToEmployee(short targetValue, short actualValue, short bonus){
        SpecifiedSocialPerformanceRecord LeadershipCompetence = new SpecifiedSocialPerformanceRecord(targetValue, actualValue, bonus);
        SSPR.add(LeadershipCompetence);
        LeadershipCompetence.setName("Openess to Employee");
    }
    public void addSocialBehaviourToEmployee(short targetValue, short actualValue, short bonus){
        SpecifiedSocialPerformanceRecord LeadershipCompetence = new SpecifiedSocialPerformanceRecord(targetValue, actualValue, bonus);
        SSPR.add(LeadershipCompetence);
        LeadershipCompetence.setName("Behaviour to Employee");
    }
    public void addAttitudesTowardsClient(short targetValue, short actualValue, short bonus){
        SpecifiedSocialPerformanceRecord LeadershipCompetence = new SpecifiedSocialPerformanceRecord(targetValue, actualValue, bonus);
        SSPR.add(LeadershipCompetence);
        LeadershipCompetence.setName("Attitude towards Client");
    }
    public void addCommunicationSkills(short targetValue, short actualValue, short bonus){
        SpecifiedSocialPerformanceRecord LeadershipCompetence = new SpecifiedSocialPerformanceRecord(targetValue, actualValue, bonus);
        SSPR.add(LeadershipCompetence);
        LeadershipCompetence.setName("Communication Skills");
    }
    public void addIntegrityToCompany(short targetValue, short actualValue, short bonus){
        SpecifiedSocialPerformanceRecord LeadershipCompetence = new SpecifiedSocialPerformanceRecord(targetValue, actualValue, bonus);
        SSPR.add(LeadershipCompetence);
        LeadershipCompetence.setName("Integrity To Company");
    }
}