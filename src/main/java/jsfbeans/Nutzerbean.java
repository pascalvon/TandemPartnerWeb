package jsfbeans;

import dao.NutzerDAO;
import models.Geschlecht;
import models.Nutzer;
import models.Sprache;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.validation.constraints.Size;
import java.util.List;

@ManagedBean
@SessionScoped
public class Nutzerbean {

    @EJB
    private NutzerDAO nutzerDAO;
    private Nutzer aktuellerNutzer = new Nutzer();
    private String mail;
    private String vorname;
    private String nachname;

    @Size(min=8, max=30, message = "Das Passwort muss zwischen 8 und 30 Zeichen lang sein!")
    private String passwort;
    private Geschlecht geschlecht;
    private java.util.Date geburtsdatum;
    private List<Sprache> sprachenList;
    private List<Sprache> selectedSprachen;

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public Nutzer getAktuellerNutzer() {
        return aktuellerNutzer;
    }

    public void setAktuellerNutzer(Nutzer aktuellerNutzer) {
        this.aktuellerNutzer = aktuellerNutzer;
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
        Nutzer n = nutzerDAO.findNutzerByMail(mail);

        return n.getPasswort().equals(passwort);
    }

    private Nutzer findNutzer(String mail) {
        return nutzerDAO.findNutzerByMail(mail);

    }

    public List<Sprache> allSpracheList() {
        return nutzerDAO.findAllSprache();
    }

}
