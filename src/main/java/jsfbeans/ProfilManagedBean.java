package jsfbeans;

import dao.NutzerDAO;
import models.Freizeitaktivitaeten;
import models.Nutzer;
import models.Sprache;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class ProfilManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private NutzerDAO nutzerDAO;
    private Nutzer nutzer;
    private String mail;
    private int bezirkID;
    private String selectedSprachenString;
    private String selectedFreizeitaktivitaetenString;

    // ============================  Constructors  ===========================79
    public ProfilManagedBean() {
        this.nutzer = initNutzer();
        this.mail = nutzer.getMail();
        this.bezirkID = nutzer.getBezirk().getId();
    }

    // ===========================  public  Methods  =========================79

    private Nutzer initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean login = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = login.nutzer;
        return nutzer;
    }

    public String update() {
        if (mail.equals(nutzer.getMail()) || validateMail(mail)) {
            nutzer.addBezirk(nutzerDAO.findBezirkByID(bezirkID));

            List<Sprache> selectedSprachenList = new ArrayList<>();
            // TODO Joe: 14.05.2018 Wenn es funktioniert auslagern in Methode
            // TODO Joe: 15.05.2018 muss noch ueberprueft werden, ob Sprachen oder Aktivitaeten entfernt wurden
            String[] selectedSprachenArray = selectedSprachenString.split(",");
            for (String aSelectedSprachenArray : selectedSprachenArray) {
                selectedSprachenList.add(nutzerDAO.findSpracheByID(aSelectedSprachenArray));
            }
            //selectedSprachenList.removeAll(Collections.singleton(null));

            for (Sprache aSelectedSprachenList : selectedSprachenList) {
                nutzer.addSprache(aSelectedSprachenList);
            }

            ArrayList<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList = new ArrayList<>();
            String[] selectedFreizeitaktivitaetenArray = selectedFreizeitaktivitaetenString.split(",");
            for (String aSelectedFreizeitaktivitaetenArray : selectedFreizeitaktivitaetenArray) {
                selectedFreizeitaktivitaetenList.add(nutzerDAO.findFreizeitaktivitaetenByID(aSelectedFreizeitaktivitaetenArray));
            }
            for (Freizeitaktivitaeten aSelectedFrezeitaktivitaetenList : selectedFreizeitaktivitaetenList) {
                nutzer.addFreizeitaktivitaeten(aSelectedFrezeitaktivitaetenList);
            }
            nutzer.setMail(mail);
            nutzerDAO.merge(nutzer);
            return "home";
        } else {
            // TODO Joe: 13.05.2018 Falls die Mail schon vorhanden oder bearbeiten Fehlgeschlagen ist, soll dementsprechend eine Fehlermeldung erscheinen.
            return "profil";
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
        ArrayList<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList = new ArrayList<>(nutzer.getFreizeitaktivitaetenSet());
        String[] selectedFreizeitaktivitaetenArray = new String[selectedFreizeitaktivitaetenList.size()];
        for (int i = 0; i < selectedFreizeitaktivitaetenList.size(); i++) {
            selectedFreizeitaktivitaetenArray[i] = String.valueOf(selectedFreizeitaktivitaetenList.get(i).getId());
        }
        selectedFreizeitaktivitaetenString = String.join(",", selectedFreizeitaktivitaetenArray);
        return selectedFreizeitaktivitaetenString;
    }

    public void setSelectedFreizeitaktivitaetenString(String selectedFreizeitaktivitaetenString) {
        this.selectedFreizeitaktivitaetenString = selectedFreizeitaktivitaetenString;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
