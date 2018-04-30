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

@ManagedBean
@SessionScoped
@WebServlet(name = "NutzerServlet", urlPatterns = {"/NutzerServlet"})
public class Nutzerbean extends HttpServlet {

    @EJB
    private NutzerDAO nutzerDAO;

    private String mail;

    private String vorname;

    private String nachname;

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

    public String save(){
        System.out.println("Test");

        Nutzer nutzer = new Nutzer();

        nutzer.setMail(mail);
        nutzer.setGeburtsdatum(new java.sql.Date(geburtsdatum.getTime()));
        nutzer.setVorname(vorname);
        nutzer.setNachname(nachname);
        nutzer.setPasswort(passwort);
        nutzer.setGeschlecht(Geschlecht.WEIBLICH);

        nutzerDAO.persist(nutzer);
        nutzerDAO.shutdown();

        return "home";
    }

    /*public void validateMail() throws ValidatorException
    {
        if(!java.util.regex.Pattern.matches("[A-Za-z0-9._%+-]{3,}@[a-zA-Z]{3,}.[a-zA-Z]", mail))
        {
            FacesMessage msg = new FacesMessage("Ungültige E-Mail-Adresse!","Bitte eine gültige Mail im Format mail@example.com angeben!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

    }*/
}
