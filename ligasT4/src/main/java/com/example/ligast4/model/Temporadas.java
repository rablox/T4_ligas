package com.example.ligast4.model;

public class Temporadas {

    private String strSeason;

    public void setStrSeason(String strSeason) {
        this.strSeason = strSeason;
    }

    public Temporadas(String strSeason) {
        this.strSeason = strSeason;
    }

    public String getStrSeason() {
        return strSeason;
    }

    @Override
    public String toString() {
        return strSeason;
    }



}
