package jsfbeans;

import dao.DAO;
import models.Freizeitaktivitaeten;
import models.Nutzer;
import models.Sprache;
import utilities.FreizeitaktivitaetenStringTransformer;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ProfilManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private DAO         dao;
    private Nutzer      nutzer;
    private String      mail;
    private int         bezirkID;
    private String      selectedSprachenString;
    private String      selectedFreizeitaktivitaetenString;

    // ============================  Constructors  ===========================79
    public ProfilManagedBean() {
        initNutzer();
        this.mail = nutzer.getMail();
        this.bezirkID = nutzer.getBezirk().getId();
    }

    // ===========================  public  Methods  =========================79
    public String update() {
        if (mail.equals(nutzer.getMail()) || validateMail(mail)) {
            nutzer.addBezirk(dao.findBezirkByID(bezirkID));

            nutzer.clearSprachenSet();
            List<Sprache> selectedSprachenList = new ArrayList<>();
            String[] selectedSprachenArray = selectedSprachenString.split(",");
            for (String aSelectedSprachenArray : selectedSprachenArray) {
                selectedSprachenList.add(dao.findSpracheByID(aSelectedSprachenArray));
            }
            for (Sprache aSelectedSprachenList : selectedSprachenList) {
                nutzer.addSprache(aSelectedSprachenList);
            }

            nutzer.clearFreizeitaktivitaetenSet();
            ArrayList<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList = new ArrayList<>();
            String[] selectedFreizeitaktivitaetenArray = selectedFreizeitaktivitaetenString.split(",");
            for (String aSelectedFreizeitaktivitaetenArray : selectedFreizeitaktivitaetenArray) {
                selectedFreizeitaktivitaetenList.add(dao.findFreizeitaktivitaetenByID(aSelectedFreizeitaktivitaetenArray));
            }
            for (Freizeitaktivitaeten aSelectedFrezeitaktivitaetenList : selectedFreizeitaktivitaetenList) {
                nutzer.addFreizeitaktivitaeten(aSelectedFrezeitaktivitaetenList);
            }

            nutzer.setMail(mail);
            dao.merge(nutzer);
            refreshNutzer();
            return "home";
        } else {
            // TODO Joe: 13.05.2018 Falls die Mail schon vorhanden oder bearbeiten Fehlgeschlagen ist, soll dementsprechend eine Fehlermeldung erscheinen.
            return "profil";
        }
    }

    // TODO Joe: 27.05.2018 Ein Fenster mit einer Bestaetigung sollte aufploppen, wenn auf loeschen geklickt wird
    public String deleteNutzer() {
        dao.deleteSuchanfrageByNutzer(nutzer);
        dao.deleteMatchanfrageByNutzer(nutzer);
        dao.deleteNutzer(nutzer);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login";
    }

    public Nutzer getNutzer() {
        return nutzer;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getBezirkID() {
        return bezirkID;
    }

    public void setBezirkID(int bezirkID) {
        this.bezirkID = bezirkID;
    }

    public String getSelectedSprachenString() {

        ArrayList<Sprache> selectedSprachenList = new ArrayList<>(nutzer.getSprachenSet());
        String[] selectedSprachenArray = new String[selectedSprachenList.size()];
        for (int i = 0; i < selectedSprachenList.size(); i++) {
            selectedSprachenArray[i] = String.valueOf(selectedSprachenList.get(i).getId());
        }
        selectedSprachenString = String.join(",", selectedSprachenArray);
        return selectedSprachenString;
    }

    public void setSelectedSprachenString(String selectedSprachenString) {
        this.selectedSprachenString = selectedSprachenString;
    }

    public String getSelectedFreizeitaktivitaetenString() {
        selectedFreizeitaktivitaetenString = FreizeitaktivitaetenStringTransformer.selectedFreizeitaktivitaetenString(nutzer);
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
        nutzer = loginManagedBean.nutzer;
    }

    private void refreshNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        loginManagedBean.nutzer = dao.findNutzerByMail(nutzer.getMail());
    }

    private boolean validateMail(String mail) {
        Nutzer n = dao.findNutzerByMail(mail);
        return n == null;
    }

    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
