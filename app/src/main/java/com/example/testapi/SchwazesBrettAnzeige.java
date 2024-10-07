package com.example.testapi;

public class SchwazesBrettAnzeige {
    private  String AnzeigeName;
    private  String Beschreibung;
    private  int erstellerId;

    public SchwazesBrettAnzeige(String beschreibung, String anzeigeName, int erstellerId) {
        this.Beschreibung = beschreibung;
        this.AnzeigeName = anzeigeName;
        this.erstellerId = erstellerId;
    }

    public String getAnzeigeName() {
        return AnzeigeName;
    }

    public void setAnzeigeName(String anzeigeName) {
        AnzeigeName = anzeigeName;
    }

    public int getErstellerId() {
        return erstellerId;
    }

    public void setErstellerId(int erstellerId) {
        this.erstellerId = erstellerId;
    }

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        Beschreibung = beschreibung;
    }
}
