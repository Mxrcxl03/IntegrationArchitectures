package de.hbrs.ia.model;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class SalesMan {
    private String firstname;
    private String lastname;
    private Integer sid = 1;
    private List <SocialPerformanceRecord> SPR;

    public SalesMan(String firstname, String lastname, Integer sid) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.sid = sid;

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getId() {
        return sid;
    }

    public void setId(Integer sid) {
        this.sid = sid;
    }

    public void addSPR(SocialPerformanceRecord SPR) {
        if (this.SPR == null) {

        }else {
            this.SPR.add(SPR);
        }
    }

    public void clearSPR() {
        this.SPR.clear();
    }

    public Document toDocument() {
        org.bson.Document document = new Document();
        document.append("firstname" , this.firstname );
        document.append("lastname" , this.lastname );
        document.append("sid" , this.sid);
        document.append("SPR" , this.SPR );
        return document;
    }

    public void delete(SocialPerformanceRecord record) {
        if (SPR == null) {
            SPR = new ArrayList<>(); // Initialisieren, falls null
        }
        SPR.remove(record);
    }

}