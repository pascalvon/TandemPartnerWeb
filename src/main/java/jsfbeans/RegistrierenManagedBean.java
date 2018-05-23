package jsfbeans;

import dao.NutzerDAO;
import models.*;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class RegistrierenManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private NutzerDAO nutzerDAO;
    private Nutzer nutzer = new Nutzer();
    private int bezirkID;
    private List<Sprache> selectedSprachenList = new ArrayList<>();
    private String selectedSprachenString;
    private String[] selectedSprachenArray;
    private ArrayList<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList = new ArrayList<>();
    private String selectedFreizeitaktivitaetenString;
    private String[] selectedFreizeitaktivitaetenArray;
    // TODO Joe: 14.05.2018 spaeter wieder lokal machen die variable
    // ============================  Constructors  ===========================79
    // ===========================  public  Methods  =========================79


    public String register() {
        if (validateMail(this.nutzer.getMail())) {
            nutzer.addBezirk(nutzerDAO.findBezirkByID(bezirkID));

            splitStringAndAddToSprachenList(selectedSprachenString, selectedSprachenArray, selectedSprachenList);

            for (Sprache aSelectedSprachenList : selectedSprachenList) {
                nutzer.addSprache(aSelectedSprachenList);
            }

            selectedFreizeitaktivitaetenArray = selectedFreizeitaktivitaetenString.split(",");
            for (String aSelectedFreizeitaktivitaetenArray : selectedFreizeitaktivitaetenArray) {
                selectedFreizeitaktivitaetenList.add(nutzerDAO.findFreizeitaktivitaetenByID(aSelectedFreizeitaktivitaetenArray));
            }
            for (Freizeitaktivitaeten aSelectedFrezeitaktivitaetenList : selectedFreizeitaktivitaetenList) {
                nutzer.addFreizeitaktivitaeten(aSelectedFrezeitaktivitaetenList);
            }
            nutzerDAO.persist(nutzer);
            return "home";
        } else {
            // TODO Joe: 13.05.2018 Falls die Mail schon vorhanden oder anlegen Fehlgeschlagen ist, soll dementsprechend eine Fehlermeldung erscheinen.
            return "registrieren";
        }
    }

    private void splitStringAndAddToSprachenList(String stringToSplit, String[] stringArray, List<Sprache> sprachenList) {
        stringArray = stringToSplit.split(",");
        for (String aSelectedSprachenArray : stringArray) {
            sprachenList.add(nutzerDAO.findSpracheByID(aSelectedSprachenArray));
        }
    }

    private boolean validateMail(String mail) {
        Nutzer n = nutzerDAO.findNutzerByMail(mail);
        return n == null;
    }

    public Nutzer getNutzer() {
        return nutzer;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    public int getBezirkID() {
        return bezirkID;
    }

    public void setBezirkID(int bezirkID) {
        this.bezirkID = bezirkID;
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

    public String[] getSelectedSprachenArray() {
        return selectedSprachenArray;
    }

    public void setSelectedSprachenArray(String[] selectedSprachenArray) {
        this.selectedSprachenArray = selectedSprachenArray;
    }

    public ArrayList<Freizeitaktivitaeten> getSelectedFreizeitaktivitaetenList() {
        return selectedFreizeitaktivitaetenList;
    }

    public void setSelectedFreizeitaktivitaetenList(ArrayList<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList) {
        this.selectedFreizeitaktivitaetenList = selectedFreizeitaktivitaetenList;
    }

    public String getSelectedFreizeitaktivitaetenString() {
        return selectedFreizeitaktivitaetenString;
    }

    public void setSelectedFreizeitaktivitaetenString(String selectedFreizeitaktivitaetenString) {
        this.selectedFreizeitaktivitaetenString = selectedFreizeitaktivitaetenString;
    }

    public String[] getSelectedFreizeitaktivitaetenArray() {
        return selectedFreizeitaktivitaetenArray;
    }

    public void setSelectedFreizeitaktivitaetenArray(String[] selectedFreizeitaktivitaetenArray) {
        this.selectedFreizeitaktivitaetenArray = selectedFreizeitaktivitaetenArray;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
