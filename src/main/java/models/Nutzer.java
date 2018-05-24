package models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({ @NamedQuery(name = "findAllNutzer",query = "SELECT nutzer FROM Nutzer nutzer"),
                @NamedQuery(name = "findNutzerByVorname", query = "select nutzer from Nutzer nutzer where nutzer.vorname = :vn"),
                @NamedQuery(name = "findNutzerByMail", query = "select nutzer from Nutzer nutzer where nutzer.mail = :vn"),
                @NamedQuery(name = "findNutzerBySprachID", query = "SELECT nutzer FROM Nutzer nutzer JOIN nutzer.sprachenSet sprache WHERE sprache.id = :vn"),
                @NamedQuery(name = "findNutzerByID", query = "SELECT nutzer FROM Nutzer nutzer WHERE nutzer.id = :vn"),
                @NamedQuery(name = "findNutzerByFreizeitaktivitaetenID", query = "SELECT nutzer FROM Nutzer nutzer JOIN nutzer.freizeitaktivitaetenSet freizeitaktivitaeten WHERE freizeitaktivitaeten.id = :vn"),
                @NamedQuery(name = "findNutzerBySuchergebnis", query = "SELECT nutzer FROM Nutzer nutzer JOIN nutzer.freizeitaktivitaetenSet freizeitaktivitaeten JOIN nutzer.sprachenSet sprache WHERE freizeitaktivitaeten.id = :fa AND sprache.id = :sp")})
public class Nutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "gen")
    @SequenceGenerator(name="gen", sequenceName = "Nutzersequenz", initialValue = 1000, allocationSize = 10)
    private int Id;

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

    @ManyToMany (fetch = FetchType.EAGER) //, cascade = CascadeType.ALL)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<Sprache> sprachenSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER) //, cascade = CascadeType.ALL)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<Freizeitaktivitaeten> freizeitaktivitaetenSet = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "nutzer", cascade = CascadeType.ALL, orphanRemoval = true)
    //@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
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

    public void setGeburtsdatum(java.util.Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public Bezirk getBezirk() {
        return bezirk;
    }

    public void setBezirk(Bezirk bezirk) {
        this.bezirk = bezirk;
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

    public void addSuchanfragen(Suchanfrage suchanfrage) {
        this.suchanfrageSet.add(suchanfrage);
    }

    public Set<Suchanfrage> getSuchanfrageSet() {
        return suchanfrageSet;
    }

    public void setSuchanfrageSet(Set<Suchanfrage> suchanfrageSet) {
        this.suchanfrageSet = suchanfrageSet;
    }

    // TODO Joe: 02.05.2018 spaeter loeschen
    @Override
    public String toString() {
        return getMail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nutzer nutzer = (Nutzer) o;
        return Id == nutzer.Id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(Id);
    }
}
