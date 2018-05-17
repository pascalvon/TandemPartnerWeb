package models;

import utilities.AgeCalculator;

import javax.persistence.NamedQuery;

public class Suchergebnisse {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    private Nutzer nutzer;
    private String vorname;
    private String nachname;
    private Geschlecht geschlecht;
    private String mail;
    private int alter;
    private Bezirk bezirk;

    // ============================  Constructors  ===========================79

    public Suchergebnisse(Nutzer nutzer) {
        this.nutzer = nutzer;
        this.vorname = nutzer.getVorname();
        this.nachname = nutzer.getNachname();
        this.geschlecht = nutzer.getGeschlecht();
        this.mail = nutzer.getMail();
        this.alter = AgeCalculator.calculateAge(nutzer.getGeburtsdatum());
        this.bezirk = nutzer.getBezirk();
    }

    // ===========================  public  Methods  =========================79


    public Nutzer getNutzer() {
        return nutzer;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public Geschlecht getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(Geschlecht geschlecht) {
        this.geschlecht = geschlecht;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }

    public Bezirk getBezirk() {
        return bezirk;
    }

    public void setBezirk(Bezirk bezirk) {
        this.bezirk = bezirk;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
