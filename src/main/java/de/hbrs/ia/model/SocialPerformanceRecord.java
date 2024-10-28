package de.hbrs.ia.model;

import org.bson.Document;

import java.util.List;

public class SocialPerformanceRecord {
    private Integer year;
    private Integer goalId;
    private Integer value;
    private Integer actualValue;
    private List<SpecifiedSocialPerformanceRecord> SSPR;

    public SocialPerformanceRecord(Integer goalId, Integer year) {
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

    public Integer getvalue() {
        return value;
    }

    public void setvalue(int value) {
        this.value = value;
    }

    public int getactualValue() {
        return actualValue;
    }

    public void setactualValue(int actualValue) {
        this.actualValue = actualValue;
    }

    public Integer getgoalId() {
        return goalId;
    }

    public void setgoalId(Integer goalId) {
        this.goalId = goalId;
    }

    public Integer averageActualValue() {
        int x = 0;
        if (SSPR == null){
            return 0;
        }else {
            for (SpecifiedSocialPerformanceRecord specifiedSocialPerformanceRecord : SSPR) {
                x += specifiedSocialPerformanceRecord.getActualValue();
            }
            return  (x / (SSPR.size()));
        }
    }

    public Integer averageTargetValue() { //rundung fehlt
        int x = 0;
        if (SSPR == null){
            return 0;
        }else {
            for (SpecifiedSocialPerformanceRecord specifiedSocialPerformanceRecord : SSPR) {
                x += specifiedSocialPerformanceRecord.getTargetValue();
            }
            return  (x / (SSPR.size()));
        }
    }

    public Document toDocument() {
        return new Document ("goalId", this.goalId).append("year", this.year).append("value", this.value).append("actualValue", this.actualValue).append("SSPR", this.SSPR);
    }

    public void addLeadershipCompetence(int targetValue, int actualValue, int bonus){
        SpecifiedSocialPerformanceRecord LeadershipCompetence = new SpecifiedSocialPerformanceRecord(targetValue, actualValue, bonus);
        SSPR.add(LeadershipCompetence);
        LeadershipCompetence.setName("Leadership Competence");
    }
    public void addOpenessToEmployee(int targetValue, int actualValue, int bonus){
        SpecifiedSocialPerformanceRecord LeadershipCompetence = new SpecifiedSocialPerformanceRecord(targetValue, actualValue, bonus);
        SSPR.add(LeadershipCompetence);
        LeadershipCompetence.setName("Openess to Employee");
    }
    public void addSocialBehaviourToEmployee( int targetValue, int actualValue, int bonus){
        SpecifiedSocialPerformanceRecord LeadershipCompetence = new SpecifiedSocialPerformanceRecord(targetValue, actualValue, bonus);
        SSPR.add(LeadershipCompetence);
        LeadershipCompetence.setName("Behaviour to Employee");
    }
    public void addAttitudesTowardsClient(int targetValue, int actualValue, int bonus){
        SpecifiedSocialPerformanceRecord LeadershipCompetence = new SpecifiedSocialPerformanceRecord(targetValue, actualValue, bonus);
        SSPR.add(LeadershipCompetence);
        LeadershipCompetence.setName("Attitude towards Client");
    }
    public void addCommunicationSkills(int targetValue, int actualValue, int bonus){
        SpecifiedSocialPerformanceRecord LeadershipCompetence = new SpecifiedSocialPerformanceRecord(targetValue, actualValue, bonus);
        SSPR.add(LeadershipCompetence);
        LeadershipCompetence.setName("Communication Skills");
    }
    public void addIntegrityToCompany(int targetValue, int actualValue, int bonus){
        SpecifiedSocialPerformanceRecord LeadershipCompetence = new SpecifiedSocialPerformanceRecord(targetValue, actualValue, bonus);
        SSPR.add(LeadershipCompetence);
        LeadershipCompetence.setName("Integrity To Company");
    }
}