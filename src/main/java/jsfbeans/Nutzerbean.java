package jsfbeans;

import dao.NutzerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Geschlecht;
import models.Nutzer;
import models.Sprache;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpServlet;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@ManagedBean
@SessionScoped
public class Nutzerbean {

    @EJB
    private NutzerDAO nutzerDAO;
    private Nutzer aktuellerNutzer = new Nutzer();
    private Nutzer nutzer; // TODO Joe: 01.05.2018 Dieser Nutzer noch erforderlich?
    private String mail;
    private String vorname;
    private String nachname;

    @Size(min=8, max=30, message = "Das Passwort muss zwischen 8 und 30 Zeichen lang sein!")
    private String passwort;
    private Geschlecht geschlecht;
    private java.util.Date geburtsdatum;
    private List<String> sprachenList; //= nutzerDAO.findAllSprache();
    private List<String> selectedSprachen;

    public String getMail() {
        return mail;
    }

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

    public java.util.Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(java.util.Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }


    public Nutzer getNutzer() {
        return nutzer;
    }

    public Nutzer getAktuellerNutzer() {
        return aktuellerNutzer;
    }

    public void setAktuellerNutzer(Nutzer aktuellerNutzer) {
        this.aktuellerNutzer = aktuellerNutzer;
    }

    public List<String> getSprachenList() {
        return sprachenList;
    }

    public void setSprachenList(List<String> sprachenList) {
        this.sprachenList = sprachenList;
    }

    public List<String> getSelectedSprachen() {
        return selectedSprachen;
    }

    public void setSelectedSprachen(List<String> selectedSprachen) {
        this.selectedSprachen = selectedSprachen;
    }

    public String nutzerAnlegen(){

        aktuellerNutzer.setMail(aktuellerNutzer.getMail());
        aktuellerNutzer.setGeburtsdatum(new java.sql.Date(aktuellerNutzer.getGeburtsdatum().getTime()));
        aktuellerNutzer.setVorname(aktuellerNutzer.getVorname());
        aktuellerNutzer.setNachname(aktuellerNutzer.getNachname());
        aktuellerNutzer.setPasswort(passwort);
        aktuellerNutzer.setGeschlecht(aktuellerNutzer.getGeschlecht());
        // TODO Joe: 04.05.2018 Testen, ob persist mit merge hier geaendert werden kann
        nutzerDAO.merge(aktuellerNutzer);

        return "home";
    }

    public String update() {

        aktuellerNutzer.setMail(aktuellerNutzer.getMail());
        aktuellerNutzer.setGeburtsdatum(new java.sql.Date(aktuellerNutzer.getGeburtsdatum().getTime()));
        aktuellerNutzer.setVorname(aktuellerNutzer.getVorname());
        aktuellerNutzer.setNachname(aktuellerNutzer.getNachname());
        aktuellerNutzer.setPasswort(passwort);
        aktuellerNutzer.setGeschlecht(aktuellerNutzer.getGeschlecht());

        aktuellerNutzer = nutzerDAO.merge(aktuellerNutzer);

        return "profil";
    }

    // TODO Joe: 01.05.2018 Exception fuer den Fall, dass falsche E-Mail angegeben wird, muss noch gefangen werden.
    // TODO Joe: 01.05.2018 Validator auslagern auf eigene Klasse und Text ausgeben bei falscher Eingabe (siehe TODO zuvor).
    public String login() {
        if (validateNutzer(aktuellerNutzer.getMail(), passwort)) {
            aktuellerNutzer = findNutzer(aktuellerNutzer.getMail());
            return "home";
        }
            return "login";
    }

    private boolean validateNutzer(String mail, String passwort) {
        Nutzer n = nutzerDAO.find(mail);

        return n.getPasswort().equals(passwort);
    }

    private Nutzer findNutzer(String mail) {
        return nutzerDAO.find(mail);

    }
    {
        sprachenList = Arrays.asList("green", "yellow", "red");
    }

    public List<Sprache> allSpracheList() {
        //sprachenList = Arrays.asList("green", "yellow", "red");
    //    return sprachenList;
        return nutzerDAO.findAllSprache();
    }

}
