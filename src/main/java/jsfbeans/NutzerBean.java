package jsfbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import java.util.Date;

@ManagedBean
@RequestScoped
public class NutzerBean {

    private String vorname;
    private String nachname;
    private String bezirk;
    private String [] ichSpreche;
    private String geschlecht;
    private Date gebDat;
    private String [] freizeitAktivitaeten;


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

    public String getBezirk() {
        return bezirk;
    }

    public void setBezirk(String bezirk) {
        this.bezirk = bezirk;
    }

    public String[] getIchSpreche() {
        return ichSpreche;
    }

    public void setIchSpreche(String[] ichSpreche) {
        this.ichSpreche = ichSpreche;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public Date getGebDat() {
        return gebDat;
    }

    public void setGebDat(Date gebDat) {
        this.gebDat = gebDat;
    }

    public String[] getFreizeitAktivitaeten() {
        return freizeitAktivitaeten;
    }

    public void setFreizeitAktivitaeten(String[] freizeitAktivitaeten) {
        this.freizeitAktivitaeten = freizeitAktivitaeten;
    }

    public String save() {
        //TODO: Abspeichern
        return "index";
    }

    public void saveListener (ActionEvent e){
        System.out.println("Du sprichst jetzt: " + java.util.Arrays.toString(this.ichSpreche) + ".");
    }
}
