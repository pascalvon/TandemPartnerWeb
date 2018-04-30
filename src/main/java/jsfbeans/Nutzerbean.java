package jsfbeans;

import dao.NutzerDAO;
import models.Geschlecht;
import models.Nutzer;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.sql.Date;
import java.util.Calendar;

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

    private Date geburtsdatum;

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

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String save(){
        System.out.println("Test");

        Nutzer nutzer = new Nutzer();

        Calendar calender = Calendar.getInstance();
        calender.set(1975,Calendar.MARCH, 15);
        Date geburtsdatum = new Date(calender.getTime().getTime());

        nutzer.setMail(mail);
        nutzer.setGeburtsdatum(geburtsdatum);
        nutzer.setVorname(vorname);
        nutzer.setNachname(nachname);
        nutzer.setPasswort(passwort);
        nutzer.setGeschlecht(Geschlecht.WEIBLICH);

        nutzerDAO.persist(nutzer);
        nutzerDAO.shutdown();

        return "home";
    }
}
