package jsfbeans;

import dao.NutzerDAO;
import models.Geschlecht;
import models.Nutzer;
import models.Sprache;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

@ManagedBean
@SessionScoped
@WebServlet(name = "NutzerServlet", urlPatterns = {"/NutzerServlet"})
public class Nutzerbean extends HttpServlet {

    @EJB
    private NutzerDAO nutzerDAO;
    private Nutzer aktuellerNutzer;
    private Nutzer nutzer; // TODO Joe: 01.05.2018 Dieser Nutzer noch erforderlich?
    private String mail;
    private String vorname;
    private String nachname;

    @Size(min=8, max=30, message = "Das Passwort muss zwischen 8 und 30 Zeichen lang sein!")
    private String passwort;
    private Geschlecht geschlecht;
    private java.util.Date geburtsdatum;
    //private ArrayList<Nutzer> sprachenList = findAllSprache();
    //private ArrayList<Sprache> selectedSprachen;

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

    public String nutzerAnlegen(){
    public Nutzer getNutzer() {
        return nutzer;
    }

    public Nutzer getAktuellerNutzer() {
        return aktuellerNutzer;
    }

    public void setAktuellerNutzer(Nutzer aktuellerNutzer) {
        this.aktuellerNutzer = aktuellerNutzer;
    }

    public ArrayList<Nutzer> getSprachenList() {
        return sprachenList;
    }

    public void setSprachenList(ArrayList<Nutzer> sprachenList) {
        this.sprachenList = sprachenList;
    }

    public ArrayList<Sprache> getSelectedSprachen() {
        return selectedSprachen;
    }

    public void setSelectedSprachen(ArrayList<Sprache> selectedSprachen) {
        this.selectedSprachen = selectedSprachen;
    }

    public String nutzerAnlegen(){
        Nutzer nutzer = new Nutzer();

        nutzer.setMail(mail);
        nutzer.setGeburtsdatum(new java.sql.Date(geburtsdatum.getTime()));
        nutzer.setVorname(vorname);
        nutzer.setNachname(nachname);
        nutzer.setPasswort(passwort);
        nutzer.setGeschlecht(geschlecht);

        nutzerDAO.persist(nutzer);

        return "home";
    }

    public String update() {

        if (mail.equals(aktuellerNutzer.getMail())) {
            aktuellerNutzer.setMail(mail);
        }
        else {
            aktuellerNutzer.setMail(aktuellerNutzer.getMail());
        }
        aktuellerNutzer.setGeburtsdatum(geburtsdatum);
        if (aktuellerNutzer.getVorname().equals(vorname)) {
            aktuellerNutzer.setVorname(vorname);
        }
        else {
            aktuellerNutzer.setVorname(aktuellerNutzer.getVorname());
        }
        if (aktuellerNutzer.getNachname().equals(nachname)) {
            aktuellerNutzer.setNachname(nachname);
        }
        else {
            aktuellerNutzer.setNachname(aktuellerNutzer.getNachname());
        }
        aktuellerNutzer.setPasswort(passwort);
        aktuellerNutzer.setGeschlecht(Geschlecht.WEIBLICH);

        nutzer = nutzerDAO.merge(aktuellerNutzer);

        return "profil";
    }

    // TODO Joe: 01.05.2018 Exception fuer den Fall, dass falsche E-Mail angegeben wird, muss noch gefangen werden.
    // TODO Joe: 01.05.2018 Validator auslagern auf eigene Klasse und Text ausgeben bei falscher Eingabe (siehe TODO zuvor).
    public String login() {
        if (validateNutzer(mail, passwort)) {
            aktuellerNutzer = getNutzer(mail);
            return "home";
        }
            return "login";
    }

    public String profilSpeichern(){

        //TODO: body;
        return "profil";
    }

    private boolean validateNutzer(String mail, String passwort) {
        Nutzer n = find(mail);

        return n.getPasswort().equals(passwort);
    }

    private Nutzer getNutzer(String mail) {
        return find(mail);

    }

    public Nutzer find(String Mail) {
        return em.createNamedQuery("findByMail", Nutzer.class)
                .setParameter("vn", Mail)
                .getSingleResult();
    }

    private void addSprachen() {

    }

    // TODO Joe: 02.05.2018 auf Sprache aendern
    public ArrayList<Nutzer> findAllSprache() {
        return (ArrayList<Nutzer>) em.createNamedQuery("findAll", Nutzer.class).getResultList();

    }

}
