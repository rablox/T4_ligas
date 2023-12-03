package com.example.ligast4.model;

public class Ligas {

    private String strLeague;
    private String idLeague;

    public Ligas(String strLeague, String idLeague) {
        this.strLeague = strLeague;
        this.idLeague = idLeague;
    }

    public String getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(String idLeague) {
        this.idLeague = idLeague;
    }

    public String getStrLeague() {
        return strLeague;
    }

    public void setStrLeague(String strLeague) {
        this.strLeague = strLeague;
    }

    @Override
    public String toString() {
        return strLeague;
    }
}
