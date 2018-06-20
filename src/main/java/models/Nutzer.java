package models;

import org.hibernate.annotations.Cascade;
import utilities.HashedPasswordGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Bildet eine Nutzer-Entit&auml;t als Objekt in Java ab.
 */
@Entity
@NamedQueries({ @NamedQuery(name = "findNutzerByMail", query = "SELECT nutzer FROM Nutzer nutzer WHERE nutzer.mail = :mail"),
                @NamedQuery(name = "findNutzerBySprachID", query = "SELECT nutzer FROM Nutzer nutzer JOIN nutzer.sprachenSet sprache WHERE sprache.id = :spracheID"),
                @NamedQuery(name = "findNutzerByMailBoolean", query = "SELECT nutzer FROM Nutzer nutzer WHERE nutzer.mail = :mail"),
                @NamedQuery(name = "deleteNutzer", query = "DELETE FROM Nutzer nutzer WHERE nutzer.id = :id")})
public class Nutzer {

    /**
     * Repr&auml;sentiert die ID der Nutzer-Entit&auml;t.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "gen")
    @SequenceGenerator(name="gen", sequenceName = "Nutzersequenz", initialValue = 1000, allocationSize = 10)
    private int id;

    /**
     * Repr&auml;sentiert die E-Mail-Adresse der Nutzer-Entit&auml;t.
     */
    @Column(nullable = false)
    private String mail;

    /**
     * Repr&auml;sentiert den Vornamen der Nutzer-Entit&auml;t.
     */
    @Column(nullable = false)
    private String vorname;

    /**
     * Repr&auml;sentiert den Nachnamen der Nutzer-Entit&auml;t.
     */
    @Column(nullable = false)
    private String nachname;

    /**
     * Repr&auml;sentiert das Passwort der Nutzer-Entit&auml;t.
     */
    @Column(nullable = false)
    private String passwort;

    /**
     * Repr&auml;sentiert das Geschlecht der Nutzer-Entit&auml;t.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Geschlecht geschlecht;

    /**
     * Repr&auml;sentiert das Geburtsdatum der Nutzer-Entit&auml;t.
     */
    @Column(nullable = false)
    private Date geburtsdatum;

    /**
     * Repr&auml;sentiert den Wohnbezirk der Nutzer-Entit&auml;t.
     */
    @ManyToOne
    private Bezirk bezirk;

    /**
     * Enth&auml;lt ein {@code Set} mit den {@code Sprache}-Objekten, die der Nutzer-Entit&auml;t
     * in der Datenbank zugewiesen sind.
     */
    @ManyToMany (fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<Sprache> sprachenSet = new HashSet<>();

    /**
     * Enth&auml;lt ein {@code Set} mit den {@code Freizeitaktivitaeten}-Objekten, die der Nutzer-Entit&auml;t
     * in der Datenbank zugewiesen sind.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<Freizeitaktivitaeten> freizeitaktivitaetenSet = new HashSet<>();

    /**
     * Ein {@code Set}-Objekt, welches, durch die {@code @OneToMany}-Annotation, in der Datenbank die
     * Suchanfrage-Tabelle mit der Nutzer-Tabelle mapped.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "nutzer")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<Suchanfrage> suchanfrageSet = new HashSet<>();

    /**
     * Gibt die ID der Nutzer-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt die ID der Nutzer-Entit&auml;t zur&uuml;ck.
     */
    public int getId() {
        return id;
    }

    /**
     * Gibt die E-Mail-Adresse der Nutzer-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt die E-Mail-Adresse der Nutzer-Entit&auml;t zur&uuml;ck.
     */
    public String getMail() { return mail; }

    /**
     * Ersetzt die E-Mail-Adresse durch eine neue E-Mail-Adresse.
     *
     * @param mail Der {@code String} mit der neuen E-Mail-Adresse, welcher die alte E-Mail-Adresse ersetzt.
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Gibt den Vornamen der Nutzer-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt den Vornamen der Nutzer-Entit&auml;t zur&uuml;ck.
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * Ersetzt den Vornamen durch einen neuen Vornamen.
     *
     * @param vorname Der {@code String} mit dem neuen Vornamen, welcher den alten Vornamen ersetzt.
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     * Gibt den Nachnamen der Nutzer-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt den Nachnamen der Nutzer-Entit&auml;t zur&uuml;ck.
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * Ersetzt den Nachnamen durch einen neuen Nachnamen.
     *
     * @param nachname Der {@code String} mit dem neuen Nachnamen, welcher den alten Nachnamen ersetzt.
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     * Gibt das Passwort der Nutzer-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt das Passwort der Nutzer-Entit&auml;t zur&uuml;ck.
     */
    public String getPasswort() {
        return passwort;
    }

    /**
     * Ersetzt das Passwort durch ein neues Passwort.
     *
     * @param passwort Der {@code String} mit dem neuen Passwort, welcher das alte Passwort ersetzt.
     */
    public void setPasswort(String passwort) {
        this.passwort = HashedPasswordGenerator.generateHash(passwort);
    }

    /**
     * Gibt das Geschlecht der Nutzer-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt das Geschlecht der Nutzer-Entit&auml;t zur&uuml;ck.
     */
    public Geschlecht getGeschlecht() {
        return geschlecht;
    }

    /**
     * Ersetzt das Geschlecht durch ein neues Geschlecht.
     *
     * @param geschlecht Das {@code Geschlecht}-Objekt mit dem neuen Geschlecht, welches das alte Geschlecht ersetzt.
     */
    public void setGeschlecht(Geschlecht geschlecht) {
        this.geschlecht = geschlecht;
    }

    /**
     * Gibt das Geburtsdatum der Nutzer-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt das Geburtsdatum der Nutzer-Entit&auml;t zur&uuml;ck.
     */
    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    /**
     * Ersetzt das Geburtsdatum durch ein neues Geburtsdatum.
     *
     * @param geburtsdatum Das {@code Date}-Objekt mit dem neuen Geburtsdatum, welches das alte Geburtsdatum ersetzt.
     */
    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    /**
     * Gibt den Wohnbezirk der Nutzer-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt den Wohnbezirk der Nutzer-Entit&auml;t zur&uuml;ck.
     */
    public Bezirk getBezirk() {
        return bezirk;
    }

    /**
     * Ersetzt den Wohnbezirk durch einen neuen Wohnbezirk.
     *
     * @param bezirk Das {@code Bezirk}-Objekt mit dem neuen Wohnbezirk, welches den alten Wohnbezirk ersetzt.
     */
    public void setBezirk(Bezirk bezirk) {
        this.bezirk = bezirk;
    }

    /**
     * Gibt das {@code Set}-Objekt mit den gesprochenen Sprachen der Nutzer-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt das {@code Set}-Objekt mit den gesprochenen Sprachen der Nutzer-Entit&auml;t zur&uuml;ck.
     */
    public Set<Sprache> getSprachenSet(){
        return sprachenSet;
    }

    /**
     * Leert das {@code Set}-Objekt mit den gesprochenen Sprachen der Nutzer-Entit&auml;t
     */
    public void clearSprachenSet(){
        sprachenSet.clear();
    }

    /**
     * F&uuml;gt ein {@code Sprache}-Objekt dem {@link #sprachenSet sprachenSet} hinzu.
     *
     * @param sprache Das einzuf&uuml;gende {@code Sprache}-Objekt.
     */
    public void addSprache (Sprache sprache){
            this.sprachenSet.add(sprache);
    }

    /**
     * Gibt das {@code Set}-Objekt mit den Freizeitaktivit&auml;ten der Nutzer-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt das {@code Set}-Objekt mit den Freizeitaktivit&auml;ten der Nutzer-Entit&auml;t zur&uuml;ck.
     */
    public Set<Freizeitaktivitaeten> getFreizeitaktivitaetenSet() {
        return freizeitaktivitaetenSet;
    }

    /**
     * Leert das {@code Set}-Objekt mit den Freizeitaktivit&auml;ten der Nutzer-Entit&auml;t
     */
    public void clearFreizeitaktivitaetenSet(){
        freizeitaktivitaetenSet.clear();
    }

    /**
     * F&uuml;gt ein {@code Freizeitaktivitaeten}-Objekt dem {@link #freizeitaktivitaetenSet freizeitaktivitaetenSet}
     * hinzu.
     *
     * @param freizeitaktivitaeten Das einzuf&uuml;gende {@code Freizeitaktivitaeten}-Objekt.
     */
    public void addFreizeitaktivitaeten(Freizeitaktivitaeten freizeitaktivitaeten) {
        this.freizeitaktivitaetenSet.add(freizeitaktivitaeten);
    }

    /**
     * &Uuml;berschreibt die {@link #toString() toString}-Methode, sodass die {@link #mail mail}
     * zur&uuml;ckgegeben wird.
     *
     * @return Gibt die E-Mail-Adresse des Nutzers zur&uuml;ck.
     */
    @Override
    public String toString() {
        return mail;
    }

    /**
     * &Uuml;berschreibt die {@link #equals(Object) equals}-Methode, sodass {@code Nutzer}-Ojekte &uuml;ber
     * ihre {@link #id id} verglichen werden.
     *
     * @param o Das Objekt, mit dem verglichen werden soll.
     * @return Gibt true zur&uuml;ck, wenn beide Objekte die selbe ID besitzen und false, wenn sie eine
     * unterschiedliche ID besitzen.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nutzer nutzer = (Nutzer) o;
        return id == nutzer.id;
    }

    /**
     * &Uuml;berschreibt die {@link #hashCode() hashCode}-Methode, sodass der Hashcode von {@code Nutzer}-
     * Objekten ihrer ID entspricht.
     *
     * @return Gibt den Hashcode des Objekts als seine ID wieder.
     */
    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
