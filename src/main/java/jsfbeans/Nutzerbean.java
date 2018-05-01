package jsfbeans;

import dao.NutzerDAO;
import models.Geschlecht;
import models.Nutzer;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    private Nutzer aktuellerNutzer;
    private Nutzer nutzer; // TODO Joe: 01.05.2018 Dieser Nutzer noch erforderlich?
    private String mail;
    private String vorname;
    private String nachname;
    private String passwort;
    private Geschlecht geschlecht;
    private Date geburtsdatum;
    @PersistenceContext(unitName = "ExperimentalJPADatabase")
    EntityManager em;

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

    public Nutzer getNutzer() {
        return nutzer;
    }

    public Nutzer getAktuellerNutzer() {
        return aktuellerNutzer;
    }

    public void setAktuellerNutzer(Nutzer aktuellerNutzer) {
        this.aktuellerNutzer = aktuellerNutzer;
    }

    public String registerNutzer(){
        System.out.println("Test");

        Calendar calender = Calendar.getInstance();
        calender.set(1975,Calendar.MARCH, 15);
        Date geburtsdatum = new Date(calender.getTime().getTime());

        nutzer = new Nutzer();
        nutzer.setMail(mail);
        nutzer.setGeburtsdatum(geburtsdatum);
        nutzer.setVorname(vorname);
        nutzer.setNachname(nachname);
        nutzer.setPasswort(passwort);
        nutzer.setGeschlecht(Geschlecht.WEIBLICH);

        nutzerDAO.persist(nutzer);
        nutzerDAO.shutdown();

        return "login";
    }

    public String update() {

        Calendar calender = Calendar.getInstance();
        calender.set(1975,Calendar.MARCH, 15);
        Date geburtsdatum = new Date(calender.getTime().getTime());

        if (mail.equals(aktuellerNutzer.getMail()))
            aktuellerNutzer.setMail(mail);
        else
            aktuellerNutzer.setMail(aktuellerNutzer.getMail());

        aktuellerNutzer.setGeburtsdatum(geburtsdatum);

        // TODO Joe: 01.05.2018 vorname und getVorname wechseln
        if (vorname != null || aktuellerNutzer.getVorname().equals(vorname))
            aktuellerNutzer.setVorname(vorname);
        else
            aktuellerNutzer.setVorname(aktuellerNutzer.getVorname());

        if (nachname != null || aktuellerNutzer.getNachname().equals(nachname))
            aktuellerNutzer.setNachname(nachname);
        else
            aktuellerNutzer.setNachname(aktuellerNutzer.getNachname());
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

    private boolean validateNutzer(String mail, String passwort) {
        NutzerDAO dao = new NutzerDAO();
        Nutzer n = find(mail);

        return n.getPasswort().equals(passwort);
    }

    private Nutzer getNutzer(String mail) {
        NutzerDAO dao = new NutzerDAO();
        return find(mail);

    }

    public Nutzer find(String Mail) {
        return em.createNamedQuery("findByMail", Nutzer.class)
                .setParameter("vn", Mail)
                .getSingleResult();
    }


}
