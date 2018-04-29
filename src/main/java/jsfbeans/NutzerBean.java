package jsfbeans;

import dao.NutzerDAO;
import models.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;


@ManagedBean
@RequestScoped
public class NutzerBean {

    private String mail;
    private String vorname;
    private String nachname;
    private String passwort;
    private Bezirk bezirk;
    private Geschlecht geschlecht;
    private Date gebDat;
    private Set<Freizeitaktivitaeten> freizeitAkt;
    private Set<Sprache> ichSpreche;

    //Methoden für Formulare

        //Profil bearbeiten

            //Profil speichern

    public String save(){
        System.out.println("Test1");

        NutzerDAO nutzerDAO = new NutzerDAO();

        Nutzer nutzer = new Nutzer();

        // Geburtstadatum hinzufügen

        nutzer.setMail(this.getMail());
        nutzer.setGeburtsdatum(this.getGebDat());
        nutzer.setVorname(this.getVorname());
        nutzer.setNachname(this.getNachname());
        nutzer.setPasswort(this.getPasswort());
        nutzer.setGeschlecht(this.getGeschlecht());

        nutzerDAO.persist(nutzer);
        nutzerDAO.shutdown();

        return "home";
    }

    public void lol()
    {
        System.out.println("Test3");
    }

    public void saveListener(ActionEvent e)
    {
        System.out.println("Test2");
    }

    //Profil löschen

    public String profilLoeschen()    {
        //hallo
        return "index";
    }



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

    public Bezirk getBezirk() {
        return bezirk;
    }

    public void setBezirk(Bezirk bezirk) {
        this.bezirk = bezirk;
    }

    public Geschlecht getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(Geschlecht geschlecht) {
        this.geschlecht = geschlecht;
    }

    public Date getGebDat() {
        return gebDat;
    }

    public void setGebDat(Date gebDat) {
        this.gebDat = gebDat;
    }

    public Set<Freizeitaktivitaeten> getFreizeitAkt() {
        return freizeitAkt;
    }

    public void setFreizeitAkt(Set<Freizeitaktivitaeten> freizeitAkt) {
        this.freizeitAkt = freizeitAkt;
    }

    public Set<Sprache> getIchSpreche() {
        return ichSpreche;
    }

    public void setIchSpreche(Set<Sprache> ichSpreche) {
        this.ichSpreche = ichSpreche;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
