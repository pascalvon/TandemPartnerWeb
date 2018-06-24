package models;

import utilities.AgeCalculator;

import java.util.Objects;

/**
 * Diese Klasse repr&auml;sentiert einen zustandegekommenen Match eines angemeldeten Nutzers.
 */
public class MatchanfragenModel {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    /**
     * Stellt einen Matchpartner dar, der einen zustandegekommenen Match mit dem angemeldeten Nutzer hat.
     */
    private Nutzer          nutzer;

    /**
     * Repr&auml;sentiert den Vornamen des Matchpartners.
     */
    private String          vorname;

    /**
     * Repr&auml;sentiert den Nachnamen des Matchpartners.
     */
    private String          nachname;

    /**
     * Repr&auml;sentiert das Geschlecht des Matchpartners.
     */
    private Geschlecht      geschlecht;

    /**
     * Repr&auml;sentiert das Alter des Matchpartners, welches mit der statischen Methode {@code calculateAge}
     * der Klasse {@code AgeCalculator} berechnet wird.
     */
    private int             alter;

    /**
     * Repr&auml;sentiert den Wohnbezirk des Matchpartners.
     */
    private Bezirk          bezirk;

    /**
     * Repr&auml;sentiert die Anzahl der gemeinsamen Freizeitaktivit&auml;ten des Matchpartners mit denen des
     * angemeldeten Nutzers.
     */
    private int             commonFreizeitaktivitaetenNumber;

    /**
     * Repr&auml;sentiert die gemeinsamen Freizeitaktivit&auml;ten des Matchpartners mit denen des
     * angemeldeten Nutzers.
     */
    private String          commonFreizeitaktivitaetenString;

    /**
     * Repr&auml;sentiert die E-Mail-Adresse des Matchpartners.
     */
    private String          mail;

    /**
     * {@code Matchanfrage}-Objekt, welches die Informationen der Matchanfrage zwischen dem Matchpartner und
     * dem angemeldeten Nutzer enth&auml;lt.
     */
    private Matchanfragen   matchanfragen;

    /**
     * {@code String}, welcher Auskunft dar&uuml;ber gibt, ob der angemeldete Nutzer die urspr&uuml;ngliche
     * Matchanfrage empfangen oder gesendet hat.
     */
    private String          origin;

    // ============================  Constructors  ===========================79

    /**
     * Konstruiert ein {@code MatchanfragenModel} mit den eingegebenen Parametern und weißt diese den entsprechenden
     * Variablen zu.
     *
     * @param nutzer Stellt den Matchpartner dar.
     * @param commonFreizeitaktivitaetenNumber Stellt die Anzahl der gemeinsamen Freizeitaktivit&auml;ten des
     *                                         Matchpartners mit dem angemeldeten Nutzers dar.
     * @param commonFreizeitaktivitaetenString Stellt die gemeinsamen Freizeitaktivit&auml;ten des
     *                                         Matchpartners mit dem angemeldeten Nutzers dar.
     * @param matchanfragen Das {@code Matchanfragen}-Objekt mit den Informationen der matchanfrage zwischen dem
     *                      Matchpartner und dem angemeldeten Nutzer.
     * @param origin Gibt Auskunft dar&uuml;ber, ob der angemeldete Nutzer die urspr&uuml;ngliche Matchanfrage
     *               empfangen oder gesendet hat.
     */
    public MatchanfragenModel(Nutzer nutzer,  int commonFreizeitaktivitaetenNumber, String commonFreizeitaktivitaetenString, Matchanfragen matchanfragen, String origin) {
        this.nutzer                             = nutzer;
        this.vorname                            = nutzer.getVorname();
        this.nachname                           = nutzer.getNachname();
        this.geschlecht                         = nutzer.getGeschlecht();
        this.alter                              = AgeCalculator.calculateAge(nutzer.getGeburtsdatum());
        this.bezirk                             = nutzer.getBezirk();
        this.commonFreizeitaktivitaetenNumber   = commonFreizeitaktivitaetenNumber;
        this.commonFreizeitaktivitaetenString   = commonFreizeitaktivitaetenString;
        this.mail                               = nutzer.getMail();
        this.matchanfragen                      = matchanfragen;
        this.origin                             = origin;
    }

    /**
     * Konstruiert ein {@code MatchanfragenModel} mit den eingegebenen Parametern und weißt diese den entsprechenden
     * Variablen zu.
     *
     * @param nutzer Stellt den Matchpartner dar.
     * @param commonFreizeitaktivitaetenNumber Stellt die Anzahl der gemeinsamen Freizeitaktivit&auml;ten des
     *                                         Matchpartners mit dem angemeldeten Nutzers dar.
     * @param commonFreizeitaktivitaetenString Stellt die gemeinsamen Freizeitaktivit&auml;ten des
     *                                         Matchpartners mit dem angemeldeten Nutzers dar.
     * @param matchanfragen Das {@code Matchanfragen}-Objekt mit den Informationen der matchanfrage zwischen dem
     *                      Matchpartner und dem angemeldeten Nutzer.
     */
    public MatchanfragenModel(Nutzer nutzer,  int commonFreizeitaktivitaetenNumber, String commonFreizeitaktivitaetenString, Matchanfragen matchanfragen) {
        this.nutzer                             = nutzer;
        this.vorname                            = nutzer.getVorname();
        this.nachname                           = nutzer.getNachname();
        this.geschlecht                         = nutzer.getGeschlecht();
        this.alter                              = AgeCalculator.calculateAge(nutzer.getGeburtsdatum());
        this.bezirk                             = nutzer.getBezirk();
        this.commonFreizeitaktivitaetenNumber   = commonFreizeitaktivitaetenNumber;
        this.commonFreizeitaktivitaetenString   = commonFreizeitaktivitaetenString;
        this.mail                               = nutzer.getMail();
        this.matchanfragen                      = matchanfragen;
    }

    // ===========================  public  Methods  =========================79

    /**
     * Liefert das {@code Nutzer}-Objekt mit den Daten des Matchpartners zur&uuml;ck.
     *
     * @return {@code Nutzer}-Objekt mit den Daten des Matchpartners.
     */
    public Nutzer getNutzer() {
        return nutzer;
    }

    /**
     * Liefert den Vornamen des Matchpartners als {@code String} zur&uuml;ck.
     *
     * @return Gibt ein {@code String}-Objekt zur&uuml;ck, welches den Vornamen des Matchpartners darstellt.
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * Liefert den Nachnamen des Matchpartners als {@code String} zur&uuml;ck.
     *
     * @return Gibt ein {@code String}-Objekt zur&uuml;ck, welches den Nachnamen des Matchpartners darstellt.
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * Liefert das Geschlecht des Matchpartners als {@code Geschlecht}-Objekt zur&uuml;ck.
     *
     * @return Gibt ein {@code Geschlecht}-Objekt zur&uuml;ck, welches das Geschlecht des Matchpartners darstellt.
     */
    public Geschlecht getGeschlecht() {
        return geschlecht;
    }

    /**
     * Liefert das Alter des Matchpartners als {@code int}-Wert zur&uuml;ck.
     *
     * @return Gibt einein {@code int}-Wert zur&uuml;ck, welcher das Alter des Matchpartners darstellt.
     */
    public int getAlter() {
        return alter;
    }

    /**
     * Liefert den Wohnbezirk des Matchpartners als {@code String} zur&uuml;ck.
     *
     * @return Gibt ein {@code String}-Objekt zur&uuml;ck, welches den Wohnbezirk des Matchpartners darstellt.
     */
    public Bezirk getBezirk() {
        return bezirk;
    }

    /**
     * Liefert die Anzahl der gemeinsamen Freizeitaktivit&auml;ten des Matchpartners  mit denen des
     * angemeldeten Nutzers als {@code int}-Wert zur&uuml;ck.
     *
     * @return Gibt einen {@code int}-Wert zur&uuml;ck, welcher die Anzahl der gemeinsamen Freizeitaktivit&auml;ten
     * des Matchpartners  mit denen des angemeldeten Nutzers darstellt.
     */
    public int getCommonFreizeitaktivitaetenNumber() {
        return commonFreizeitaktivitaetenNumber;
    }

    /**
     * Liefert die gemeinsamen Freizeitaktivit&auml;ten des Matchpartners mit denen des angemeldeten
     * Nutzers als {@code String} zur&uuml;ck.
     *
     * @return Gibt ein {@code String}-Objekt zur&uuml;ck, welches die gemeinsamen Freizeitaktivit&auml;ten
     * des vorgeschlagenen Nutzers  mit denen des Matchpartners darstellt.
     */
    public String getCommonFreizeitaktivitaetenString() {
        return commonFreizeitaktivitaetenString;
    }

    /**
     * Liefert die E-Mail-Adresse des Matchpartners als {@code String} zur&uuml;ck.
     *
     * @return Gibt ein {@code String}-Objekt zur&uuml;ck, welches die E-Mail-Adresse des Matchpartners darstellt.
     */
    public String getMail() {
        return mail;
    }

    /**
     * Liefert das {@code Matchanfragen}-Objekt wieder, welches die Informationen der Matchanfrage zwischen dem
     * Matchpartner und dem angemeldeten Nutzer enth&auml;lt.
     * @return  Gibt ein {@code Matchanfragen}-Objekt mit den Informationen der Matchanfrage zwischen dem Matchpartner
     *          und dem angemeldeten Nutzer zur&uuml;ck.
     */
    public Matchanfragen getMatchanfragen() {
        return matchanfragen;
    }

    /**
     * Liefert die Information, ob der angemeldete Nutzer die urspr&uuml;ngliche Matchanfrage empfangen oder gesendet
     * hat.
     * @return Gibt ein {@code String}-Objekt mit der Information des Ursprungs der Matchanfrage.
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * &Uuml;berschreibt die {@link #equals(Object) equals}-Methode, sodass {@code MatchanfragenModel}-Ojekte &uuml;ber
     * bestimmte Parameter verglichen werden.
     *
     * @param   o Das Objekt, mit dem verglichen werden soll.
     * @return  Gibt true zur&uuml;ck, wenn die Parameter der beiden Objekte die selben sind und false, wenn sie
     *          unterschiedliche Parameter besitzen.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchanfragenModel that = (MatchanfragenModel) o;
        return alter == that.alter &&
                commonFreizeitaktivitaetenNumber == that.commonFreizeitaktivitaetenNumber &&
                Objects.equals(nutzer, that.nutzer) &&
                Objects.equals(vorname, that.vorname) &&
                Objects.equals(nachname, that.nachname) &&
                geschlecht == that.geschlecht &&
                Objects.equals(bezirk, that.bezirk) &&
                Objects.equals(commonFreizeitaktivitaetenString, that.commonFreizeitaktivitaetenString) &&
                Objects.equals(mail, that.mail) &&
                Objects.equals(matchanfragen, that.matchanfragen) &&
                Objects.equals(origin, that.origin);
    }

    /**
     * &Uuml;berschreibt die {@link #hashCode() hashCode}-Methode, sodass der Hashcode von {@code MatchanfragenModel}-
     * Objekten ihrer Parameter entsprechen.
     *
     * @return Gibt den Hashcode des Objekts als seine Parameter wieder.
     */
    @Override
    public int hashCode() {

        return Objects.hash(nutzer, vorname, nachname, geschlecht, alter, bezirk, commonFreizeitaktivitaetenNumber, commonFreizeitaktivitaetenString, mail, matchanfragen, origin);
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
