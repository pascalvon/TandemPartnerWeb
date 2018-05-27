package models;

import utilities.AgeCalculator;

public class Suchergebnis {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    private Nutzer      nutzer;
    private String      vorname;
    private String      nachname;
    private Geschlecht  geschlecht;
    private int         alter;
    private Bezirk      bezirk;
    private int         commonFreizeitaktivitaetenNumber;
    private String      commonFreizeitaktivitaetenString;

    // ============================  Constructors  ===========================79
    public Suchergebnis(Nutzer nutzer, int commonFreizeitaktivitaetenNumber, String commonFreizeitaktivitaetenString) {
        this.nutzer                             = nutzer;
        this.vorname                            = nutzer.getVorname();
        this.nachname                           = nutzer.getNachname();
        this.geschlecht                         = nutzer.getGeschlecht();
        this.alter                              = AgeCalculator.calculateAge(nutzer.getGeburtsdatum());
        this.bezirk                             = nutzer.getBezirk();
        this.commonFreizeitaktivitaetenNumber   = commonFreizeitaktivitaetenNumber;
        this.commonFreizeitaktivitaetenString   = commonFreizeitaktivitaetenString;
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

    public int getCommonFreizeitaktivitaetenNumber() {
        return commonFreizeitaktivitaetenNumber;
    }

    public void setCommonFreizeitaktivitaetenNumber(int commonFreizeitaktivitaetenNumber) {
        this.commonFreizeitaktivitaetenNumber = commonFreizeitaktivitaetenNumber;
    }

    public String getCommonFreizeitaktivitaetenString() {
        return commonFreizeitaktivitaetenString;
    }

    public void setCommonFreizeitaktivitaetenString(String commonFreizeitaktivitaetenString) {
        this.commonFreizeitaktivitaetenString = commonFreizeitaktivitaetenString;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
