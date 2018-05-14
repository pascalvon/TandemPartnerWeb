package jsfbeans;

import dao.NutzerDAO;
import models.*;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ManagedBean
@SessionScoped
public class RegistrierenManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private NutzerDAO nutzerDAO;
    private Nutzer nutzer = new Nutzer();
    private ArrayList<Bezirk> bezirkList;
    // TODO Joe: 13.05.2018 anstelle des ArrayList mit List versuchen
    private List<Sprache> selectedSprachenList = new ArrayList<>();
    private List<Sprache> sprachenList;
    private String selectedSprachenString;
    private ArrayList<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList;
    private ArrayList<Freizeitaktivitaeten> freizeitaktivitaetenList;
    // TODO Joe: 14.05.2018 spaeter wieder lokal machen die variable
    private String[] arr;
    // ============================  Constructors  ===========================79
    // ===========================  public  Methods  =========================79


    public String register() {
        if (validateMail(this.nutzer.getMail())) {

            // TODO Joe: 14.05.2018 Wenn es funktioniert auslagern in Methode
            arr = selectedSprachenString.split("\\W+ ");
            for (String anArr : arr) {
                selectedSprachenList.add(nutzerDAO.findSprache(anArr));
            }

            selectedSprachenList.removeAll(Collections.singleton(null));

            for (Sprache aSelectedSprachenList : selectedSprachenList) {
                nutzer.addSprache(aSelectedSprachenList);
            }

//            for (Freizeitaktivitaeten aSelectedFrezeitaktivitaetenList : selectedFreizeitaktivitaetenList) {
//                nutzer.addFreizeitaktivitaeten(aSelectedFrezeitaktivitaetenList);
//            }
            nutzerDAO.persist(nutzer);
            return "home";
        } else {
            // TODO Joe: 13.05.2018 Falls die Mail schon vorhanden oder anlegen Fehlgeschlagen ist, soll dementsprechend eine Fehlermeldung erscheinen.
            return "registrieren";
        }
    }

    private boolean validateMail(String mail) {
        Nutzer n = nutzerDAO.find(mail);
        return n == null;
    }

    public Nutzer getNutzer() {
        return nutzer;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    public List<Sprache> getSprachenList() {
        if (sprachenList == null) {
            sprachenList = nutzerDAO.findAllSprache();
        }
        return sprachenList;
    }

    public void setSprachenList(List<Sprache> sprachenList) {
        this.sprachenList = sprachenList;
    }

    public List<Sprache> getSelectedSprachenList() {
        return selectedSprachenList;
    }

    public void setSelectedSprachenList(List<Sprache> selectedSprachenList) {
        this.selectedSprachenList = selectedSprachenList;
    }

    public String getSelectedSprachenString() {
        return selectedSprachenString;
    }

    public void setSelectedSprachenString(String selectedSprachenString) {
        this.selectedSprachenString = selectedSprachenString;
    }

    public ArrayList<Freizeitaktivitaeten> getFreizeitaktivitaetenList() {
        return freizeitaktivitaetenList;
    }

    public void setFreizeitaktivitaetenList(ArrayList<Freizeitaktivitaeten> freizeitaktivitaetenList) {
        this.freizeitaktivitaetenList = freizeitaktivitaetenList;
    }

    public ArrayList<Freizeitaktivitaeten> getSelectedFreizeitaktivitaetenList() {
        return selectedFreizeitaktivitaetenList;
    }

    public void setSelectedFreizeitaktivitaetenList(ArrayList<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList) {
        this.selectedFreizeitaktivitaetenList = selectedFreizeitaktivitaetenList;
    }

    public ArrayList<Bezirk> getBezirkList() {
        return bezirkList;
    }

    public void setBezirkList(ArrayList<Bezirk> bezirkList) {
        this.bezirkList = bezirkList;
    }

    public String[] getArr() {
        return arr;
    }

    public void setArr(String[] arr) {
        this.arr = arr;
    }


    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
