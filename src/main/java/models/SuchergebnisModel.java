package models;

import utilities.AgeCalculator;

/**
 * Diese Klasse repr&aumlsentiert das Ergebnis der Suchanfrage eines angemeldeten Nutzers.
 */
public class SuchergebnisModel {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    /**
     * Stellt einen Nutzer dar, welcher den Parametern aus der Suchanfrage entspricht
     */
    private Nutzer      nutzer;
    private String      vorname;
    private String      nachname;
    private Geschlecht  geschlecht;
    private int         alter;
    private Bezirk      bezirk;
    private int         commonFreizeitaktivitaetenNumber;
    private String      commonFreizeitaktivitaetenString;

    // ============================  Constructors  ===========================79
    public SuchergebnisModel(Nutzer nutzer, int commonFreizeitaktivitaetenNumber, String commonFreizeitaktivitaetenString) {
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

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public Geschlecht getGeschlecht() {
        return geschlecht;
    }

    public int getAlter() {
        return alter;
    }

    public Bezirk getBezirk() {
        return bezirk;
    }

    public int getCommonFreizeitaktivitaetenNumber() {
        return commonFreizeitaktivitaetenNumber;
    }

    public String getCommonFreizeitaktivitaetenString() {
        return commonFreizeitaktivitaetenString;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
