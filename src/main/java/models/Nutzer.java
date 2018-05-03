package models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({@NamedQuery(name = "findAll",query = "SELECT nutzer FROM Nutzer nutzer"),
        @NamedQuery(name = "findByVorname", query = "select nutzer from Nutzer nutzer where nutzer.vorname = :vn"),
        @NamedQuery(name = "findByMail", query = "select nutzer from Nutzer nutzer where nutzer.mail = :vn")})
public class Nutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "gen")
    @SequenceGenerator(name="gen", sequenceName = "Nutzersequenz", initialValue = 1000, allocationSize = 10)
    private int Id;
    // TODO Joe: 01.05.2018 Jede Mail darf nur einmal existieren.
    @Column(nullable = false)
    private String mail;

    @Column(nullable = false)
    private String vorname;

    @Column(nullable = false)
    private String nachname;

    @Column(nullable = false)
    private String passwort;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Geschlecht geschlecht;

    @Column(nullable = false)
    private Date geburtsdatum;

    @ManyToOne
    private Bezirk bezirk;

    @ManyToMany (cascade = CascadeType.ALL)
    private Set<Sprache> sprachenSet;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Freizeitaktivitaeten> freizeitaktivitaetenSet;

    @OneToMany(mappedBy = "nutzer", cascade = CascadeType.ALL)
    private Set<Suchanfrage> suchanfrageSet;

    public Nutzer(){
        super();
        suchanfrageSet = new HashSet<>();
    }

    public String getMail() { return mail; }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public Geschlecht getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(Geschlecht geschlecht) {
        this.geschlecht = geschlecht;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public void addBezirk(Bezirk bezirk) {
        this.bezirk = bezirk;
    }

    public Set<Sprache> getSprachenSet(){
        return sprachenSet;
    }

    public void clearSprachenSet(){
        sprachenSet.clear();
    }

    public void addSprache (Sprache sprache){
        this.sprachenSet.add(sprache);
    }

    public void clearFreizeitaktivitaetenSet(){
        freizeitaktivitaetenSet.clear();
    }

    public Set<Freizeitaktivitaeten> getFreizeitaktivitaetenSet() {
        return freizeitaktivitaetenSet;
    }

    public void addFreizeitaktivitaeten(Freizeitaktivitaeten freizeitaktivitaeten) {
        this.freizeitaktivitaetenSet.add(freizeitaktivitaeten);
    }

    // TODO Joe: 02.05.2018 spaeter loeschen
    @Override
    public String toString() {
        return getMail();
    }
}
