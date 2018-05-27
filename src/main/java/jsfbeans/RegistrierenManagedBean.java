package jsfbeans;

import dao.NutzerDAO;
import models.*;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class RegistrierenManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private NutzerDAO nutzerDAO;
    private Nutzer nutzer = new Nutzer();
    private int bezirkID;
    private List<Sprache> selectedSprachenList = new ArrayList<>();
    private String selectedSprachenString;
    private ArrayList<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList = new ArrayList<>();
    private String selectedFreizeitaktivitaetenString;
    // ============================  Constructors  ===========================79
    // ===========================  public  Methods  =========================79


    public String register() {
        if (validateMail(this.nutzer.getMail())) {
            nutzer.addBezirk(nutzerDAO.findBezirkByID(bezirkID));

            splitStringAndAddToSprachenList(selectedSprachenString, selectedSprachenList);

            for (Sprache aSelectedSprachenList : selectedSprachenList) {
                nutzer.addSprache(aSelectedSprachenList);
            }

            String[] selectedFreizeitaktivitaetenArray = selectedFreizeitaktivitaetenString.split(",");
            for (String aSelectedFreizeitaktivitaetenArray : selectedFreizeitaktivitaetenArray) {
                selectedFreizeitaktivitaetenList.add(nutzerDAO.findFreizeitaktivitaetenByID(aSelectedFreizeitaktivitaetenArray));
            }
            for (Freizeitaktivitaeten aSelectedFrezeitaktivitaetenList : selectedFreizeitaktivitaetenList) {
                nutzer.addFreizeitaktivitaeten(aSelectedFrezeitaktivitaetenList);
            }
            nutzerDAO.persist(nutzer);
            initNutzer();
            return "home";
        } else {
            // TODO Joe: 13.05.2018 Falls die Mail schon vorhanden oder anlegen Fehlgeschlagen ist, soll dementsprechend eine Fehlermeldung erscheinen.
            return "registrieren";
        }
    }

    private void splitStringAndAddToSprachenList(String stringToSplit, List<Sprache> sprachenList) {
        String[] stringArray = stringToSplit.split(",");
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

    public String getSelectedSprachenString() {
        return selectedSprachenString;
    }

    public void setSelectedSprachenString(String selectedSprachenString) {
        this.selectedSprachenString = selectedSprachenString;
    }

    public String getSelectedFreizeitaktivitaetenString() {
        return selectedFreizeitaktivitaetenString;
    }

    public void setSelectedFreizeitaktivitaetenString(String selectedFreizeitaktivitaetenString) {
        this.selectedFreizeitaktivitaetenString = selectedFreizeitaktivitaetenString;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        loginManagedBean.nutzer = nutzerDAO.findNutzerByMail(nutzer.getMail());
    }
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
