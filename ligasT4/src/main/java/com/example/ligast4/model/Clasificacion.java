package com.example.ligast4.model;

public class Clasificacion {
    private String strTeam;
    private int intPoints;
    private int intGoalsFor;
    private int intGoalsAgainst;
    private String strTeamBadge;
    private String strForm;

    public Clasificacion(String strTeam, int intPoints, int intGoalsFor, int intGoalsAgainst, String strTeamBadge, String strForm) {
        this.strTeam = strTeam;
        this.intPoints = intPoints;
        this.intGoalsFor = intGoalsFor;
        this.intGoalsAgainst = intGoalsAgainst;
        this.strTeamBadge = strTeamBadge;
        this.strForm = strForm;
    }

    public String getStrTeamBadge() {
        return strTeamBadge;
    }

    public void setStrTeamBadge(String strTeamBadge) {
        this.strTeamBadge = strTeamBadge;
    }

    public String getStrForm() {
        return strForm;
    }

    public void setStrForm(String strForm) {
        this.strForm = strForm;
    }
    public String getStrTeam() {
        return strTeam;
    }

    public void setStrTeam(String strTeam) {
        this.strTeam = strTeam;
    }

    public int getIntPoints() {
        return intPoints;
    }

    public void setIntPoints(int intPoints) {
        this.intPoints = intPoints;
    }

    public int getIntGoalsFor() {
        return intGoalsFor;
    }

    public void setIntGoalsFor(int intGoalsFor) {
        this.intGoalsFor = intGoalsFor;
    }

    public int getIntGoalsAgainst() {
        return intGoalsAgainst;
    }

    public void setIntGoalsAgainst(int intGoalsAgainst) {
        this.intGoalsAgainst = intGoalsAgainst;
    }

    @Override
    public String toString() {
        return strTeam + " - Puntos: " + intPoints + ", GF: " + intGoalsFor + ", GC: " + intGoalsAgainst;
    }
}
