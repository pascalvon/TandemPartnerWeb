package jsfbeans;

import dao.NutzerDAO;
import models.Geschlecht;
import models.Nutzer;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.validator.ValidatorException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ManagedBean
@SessionScoped
@WebServlet(name = "NutzerServlet", urlPatterns = {"/NutzerServlet"})
public class Nutzerbean extends HttpServlet {

    @EJB
    private NutzerDAO nutzerDAO;

    private String mail;
    private String vorname;
    private String nachname;

    @Size(min=8, max=30, message = "Das Passwort muss zwischen 8 und 30 Zeichen lang sein!")
    private String passwort;


    private Geschlecht geschlecht;
    private java.util.Date geburtsdatum;

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

        Nutzer nutzer = new Nutzer();

        nutzer.setMail(mail);
        nutzer.setGeburtsdatum(new java.sql.Date(geburtsdatum.getTime()));
        nutzer.setVorname(vorname);
        nutzer.setNachname(nachname);
        nutzer.setPasswort(passwort);
        nutzer.setGeschlecht(geschlecht);

        nutzerDAO.persist(nutzer);
        nutzerDAO.shutdown();

        return "home";
    }


}