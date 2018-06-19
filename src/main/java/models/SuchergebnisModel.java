package models;

import utilities.AgeCalculator;

/**
 * Diese Klasse repr&auml;sentiert einen vorgeschlagenen Nutzer aus dem Ergebnis der Suchanfrage eines angemeldeten Nutzers.
 */
public class SuchergebnisModel {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    /**
     * Stellt einen vorgeschlagenen Nutzer dar, welcher den Suchparametern aus der Suchanfrage entspricht
     */
    private Nutzer      nutzer;

    /**
     * Repr&auml;sentiert den Vornamen des vorgeschlagenen Nutzers.
     */
    private String      vorname;

    /**
     * Repr&auml;sentiert den Nachnamen des vorgeschlagenen Nutzers.
      */
    private String      nachname;

    /**
     * Repr&auml;sentiert das Geschlecht des vorgeschlagenen Nutzers.
     */
    private Geschlecht  geschlecht;

    /**
     * Repr&auml;sentiert das Alter des vorgeschlagenen Nutzers, welches mit der statischen Methode {@code calculateAge}
     * der Klasse {@code AgeCalculator} berechnet wird.
     */
    private int         alter;

    /**
     * Repr&auml;sentiert den Wohnbezirk des vorgeschlagenen Nutzers.
     */
    private Bezirk      bezirk;

    /**
     * Repr&auml;sentiert die Anzahl der gemeinsamen Freizeitaktivit&auml;ten des vorgeschlagenen Nutzers mit denen des
     * angemeldeten Nutzers.
     */
    private int         commonFreizeitaktivitaetenNumber;

    /**
     * Repr&auml;sentiert die gemeinsamen Freizeitaktivit&auml;ten des vorgeschlagenen Nutzers mit denen des
     * angemeldeten Nutzers.
     */
    private String      commonFreizeitaktivitaetenString;

    // ============================  Constructors  ===========================79
    /**
     * Konstruiert ein {@code SuchergebnisModel} mit den eingegebenen Parametern und weißt diese den entsprechenden
     * Variablen zu.
     *
     * @param nutzer Stellt den vorgeschlagenen Nutzer dar.
     * @param commonFreizeitaktivitaetenNumber Stellt die Anzahl der gemeinsamen Freizeitaktivit&auml;ten des
     *                                         vorgeschlagenen Nutzers mit dem angemeldeten Nutzers dar.
     * @param commonFreizeitaktivitaetenString Stellt die gemeinsamen Freizeitaktivit&auml;ten des
     *                                         vorgeschlagenen Nutzers mit dem angemeldeten Nutzers dar.
     */
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

    /**
     * Liefert das {@code Nutzer}-Objekt mit den Daten des vorgeschlagenen Nutzers zur&uuml;ck.
     *
     * @return {@code Nutzer}-Objekt mit den Daten des vorgeschlagenen Nutzers.
     */
    public Nutzer getNutzer() {
        return nutzer;
    }

    /**
     * Liefert den Vornamen des vorgeschlagenen Nutzers als {@code String} zur&uuml;ck.
     *
     * @return Gibt ein {@code String}-Objekt zur&uuml;ck, welches den Vornamen des vorgeschlagenen Nutzers darstellt.
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * Liefert den Nachnamen des vorgeschlagenen Nutzers als {@code String} zur&uuml;ck.
     *
     * @return Gibt ein {@code String}-Objekt zur&uuml;ck, welches den Nachnamen des vorgeschlagenen Nutzers darstellt.
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * Liefert das Geschlecht des vorgeschlagenen Nutzers als {@code Geschlecht}-Objekt zur&uuml;ck.
     *
     * @return Gibt ein {@code Geschlecht}-Objekt zur&uuml;ck, welches das Geschlecht des vorgeschlagenen Nutzers darstellt.
     */
    public Geschlecht getGeschlecht() {
        return geschlecht;
    }

    /**
     * Liefert das Alter des vorgeschlagenen Nutzers als {@code int}-Wert zur&uuml;ck.
     *
     * @return Gibt einein {@code int}-Wert zur&uuml;ck, welcher das Alter des vorgeschlagenen Nutzers darstellt.
     */
    public int getAlter() {
        return alter;
    }

    /**
     * Liefert den Wohnbezirk des vorgeschlagenen Nutzers als {@code String} zur&uuml;ck.
     *
     * @return Gibt ein {@code String}-Objekt zur&uuml;ck, welches den Wohnbezirk des vorgeschlagenen Nutzers darstellt.
     */
    public Bezirk getBezirk() {
        return bezirk;
    }

    /**
     * Liefert die Anzahl der gemeinsamen Freizeitaktivit&auml;ten des vorgeschlagenen Nutzers  mit denen des
     * angemeldeten Nutzers als {@code int}-Wert zur&uuml;ck.
     *
     * @return  Gibt einen {@code int}-Wert zur&uuml;ck, welcher die Anzahl der gemeinsamen Freizeitaktivit&auml;ten
     *          des vorgeschlagenen Nutzers  mit denen des angemeldeten Nutzers darstellt.
     */
    public int getCommonFreizeitaktivitaetenNumber() {
        return commonFreizeitaktivitaetenNumber;
    }

    /**
     * Liefert die gemeinsamen Freizeitaktivit&auml;ten des vorgeschlagenen Nutzers mit denen des angemeldeten
     * Nutzers als {@code String} zur&uuml;ck.
     *
     * @return Gibt ein {@code String}-Objekt zur&uuml;ck, welches die gemeinsamen Freizeitaktivit&auml;ten
     * des vorgeschlagenen Nutzers  mit denen des angemeldeten Nutzers darstellt.
     */
    public String getCommonFreizeitaktivitaetenString() {
        return commonFreizeitaktivitaetenString;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
