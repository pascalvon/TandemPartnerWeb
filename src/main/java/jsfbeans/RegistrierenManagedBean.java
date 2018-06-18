package jsfbeans;

import dao.DAO;
import models.*;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Die {@code RegistrierenManagedBean} dient zur Verwaltung der Variablen und Methoden f&uuml;r die {@code profil.xhtml}.
 */
@ManagedBean
@RequestScoped
public class RegistrierenManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    /**
     * Das {@code DAO}-Objekt, welches Methoden enth&auml;lt, um abfragen mit der Datenbank zu realisieren.
     */
    @EJB
    private DAO                             dao;

    /**
     * Das {@code Nutzer}-Objekt, welches im Konstruktor initialisiert wird und in dem die Eingaben des Nutzers
     * gehalten werden.
     */
    private Nutzer                          nutzer;

    /**
     * {@code int}-Wert, in dem die Bezirk-ID des Nutzers nach der Eingabe gehalten wird.
     */
    private int                             bezirkID;

    /**
     *
     */
    private List<Sprache>                   selectedSprachenList;
    private String                          selectedSprachenString;
    private ArrayList<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList;
    private String                          selectedFreizeitaktivitaetenString;

    // ============================  Constructors  ===========================79
    public RegistrierenManagedBean() {
        this.nutzer                             = new Nutzer();
        this.selectedSprachenList               = new ArrayList<>();
        this.selectedFreizeitaktivitaetenList   = new ArrayList<>();
    }

    // ===========================  public  Methods  =========================79
    public String register() {
            nutzer.addBezirk(dao.findBezirkByID(bezirkID));
            addSprachenToNutzer();
            addFreizeitaktivitaetenToNutzer();
            dao.merge(nutzer);
            initNutzer();
            return "/nutzer/home?faces-redirect=true";
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
    private void addSprachenToNutzer() {
        String[] selectedSprachenArray = selectedSprachenString.split(",");
        for (String aSelectedSprachenArray : selectedSprachenArray) {
            selectedSprachenList.add(dao.findSpracheByID(Integer.parseInt(aSelectedSprachenArray)));
        }
        for (Sprache aSelectedSprachenList : selectedSprachenList) {
            nutzer.addSprache(aSelectedSprachenList);
        }
    }

    private void addFreizeitaktivitaetenToNutzer() {
        String[] selectedFreizeitaktivitaetenArray = selectedFreizeitaktivitaetenString.split(",");
        for (String aSelectedFreizeitaktivitaetenArray : selectedFreizeitaktivitaetenArray) {
            selectedFreizeitaktivitaetenList.add(dao.findFreizeitaktivitaetenByID(Integer.parseInt(aSelectedFreizeitaktivitaetenArray)));
        }
        for (Freizeitaktivitaeten aSelectedFrezeitaktivitaetenList : selectedFreizeitaktivitaetenList) {
            nutzer.addFreizeitaktivitaeten(aSelectedFrezeitaktivitaetenList);
        }
    }

    private void initNutzer() {
        FacesContext context = FacesContext.getCurrentInstance();

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        loginManagedBean.setNutzer(dao.findNutzerByMail(nutzer.getMail()));
        context.getExternalContext().getSessionMap().put("nutzer", nutzer.getMail());

    }

    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
