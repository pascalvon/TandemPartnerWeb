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
     * Ein {@code List}-Objekt mit der Typisierung {@code Sprache}, welches im Konstruktor instanziiert wird und
     * in dem die {@code Sprache}-Objekte gehalten werden, die den Sprachennamen
     * aus {@link #selectedSprachenString selectedSprachenString} entsprechen.
     */
    private List<Sprache>                   selectedSprachenList;

    /**
     * {@code String}, in dem die Namen der Sprachen gehalten werden, die der Nutzer ausw&auml;hlt.
     */
    private String                          selectedSprachenString;

    /**
     * Ein {@code List}-Objekt mit der Typisierung {@code Freizeitaktivitaeten}, welches im Konstruktor instanziiert
     * wird und in dem die {@code Freizeitaktivitaeten}-Objekte gehalten werden, die den Namen der Freizeitaktivitaeten
     * aus {@link #selectedFreizeitaktivitaetenString selectedFreizeitaktivitaetenString} entsprechen.
     */
    private List<Freizeitaktivitaeten>      selectedFreizeitaktivitaetenList;

    /**
     * {@code String}, in dem die Namen der Freizeitaktivitaeten gehalten werden, die der Nutzer ausw&auml;hlt.
     */
    private String                          selectedFreizeitaktivitaetenString;

    // ============================  Constructors  ===========================79
    /**
     * Initialisiert ein neu erzeugtes {@code ProfilManagedBean}-Objekt und instanziiert {@link #nutzer nutzer},
     * {@link #selectedSprachenList selectedSprachenList} und
     * {@link #selectedFreizeitaktivitaetenList selectedFreizeitaktivitaetenList}.
     */
    public RegistrierenManagedBean() {
        this.nutzer                             = new Nutzer();
        this.selectedSprachenList               = new ArrayList<>();
        this.selectedFreizeitaktivitaetenList   = new ArrayList<>();
    }

    public RegistrierenManagedBean(DAO dao, int nutzerID) {
        this.dao                                = dao;
        this.nutzer                             = new Nutzer(nutzerID);
        this.selectedSprachenList               = new ArrayList<>();
        this.selectedFreizeitaktivitaetenList   = new ArrayList<>();
    }

    // ===========================  public  Methods  =========================79
    /**
     * Erstellt eine neue Nutzer-Entit&auml;t in der Datenbank.
     * Daf%uuml;r wird dem {@link #nutzer nutzer} &uuml;ber die {@link #bezirkID bezirkID} ein {@code Bezirk}-Objekt
     * hinzugef&uuml;gt. Danach werden die Methoden {@link #addSprachenToNutzer() addSprachenToNutzer} und
     * {@link #addFreizeitaktivitaetenToNutzer() addFreizeitaktivitaetenToNutzer} aufgerufen.
     * Anschlie&szlig;end wird der {@link #nutzer nutzer} in die Datenbank gemerged und die Methode
     * {@link #initNutzer() initNutzer} aufgerufen.
     *
     * @return  Gibt den {@code String} zur&uuml;ck, mit dem der Nutzer bei erfolgreicher Registrierung auf die
     *          {@code home.xhtml} weitergeleitet wird.
     */
    public String register() {
            nutzer.setBezirk(dao.findBezirkByID(bezirkID));
            addSprachenToNutzer();
            addFreizeitaktivitaetenToNutzer();
            dao.merge(nutzer);
            if (contextExists()) {
                initNutzer();
            }
            return "/nutzer/home?faces-redirect=true";
    }

    /**
     * Liefert das {@code Nutzer}-Objekt zur&uuml;ck, welches die Eingaben des Nutzers enth&auml;lt.
     *
     * @return {@code Nutzer}-Objekt mit den Eingaben des Nutzers.
     */
    public Nutzer getNutzer() {
        return nutzer;
    }

    /**
     * Ersetzt das {@code Nutzer}-Objekt durch das &uuml;bergebene {@code Nutzer}-Objekt.
     *
     * @param nutzer {@code Nutzer}-Objekt, welches das alte Objekt ersetzt.
     */
    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    /**
     * Liefert die Bezirk-ID des Nutzers zur&uuml;ck.
     *
     * @return Gibt die Bezirk-ID des Nutzers zur&uuml;ck.
     */
    public int getBezirkID() {
        return bezirkID;
    }

    /**
     * Ersetzt die Bezirk-ID des Nutzers durch eine neue Bezirk-ID.
     *
     * @param bezirkID Die Bezirk-ID, welche die alte Bezirk-ID ersetzt.
     */
    public void setBezirkID(int bezirkID) {
        this.bezirkID = bezirkID;
    }

    /**
     * Liefert den {@code String} mit den selektierten Sprachen des Nutzers zur&uuml;ck.
     *
     * @return Gibt den {@code String} mit den selektierten Sprachen des angemeldeten Nutzers zur&uuml;ck.
     */
    public String getSelectedSprachenString() {
        return selectedSprachenString;
    }

    /**
     * Ersetzt den {@code String} mit den selektierten Sprachen des Nutzers durch einen neue {@code String}.
     *
     * @param selectedSprachenString Der {@code String} mit den selektierten Sprachen, welche den alten {@code String}
     *                               ersetzen.
     */
    public void setSelectedSprachenString(String selectedSprachenString) {
        this.selectedSprachenString = selectedSprachenString;
    }


    /**
     * Liefert den {@code String} mit den selektierten Freizeitaktivitaeten des Nutzers zur&uuml;ck.
     *
     * @return Gibt den {@code String} mit den selektierten Freizeitaktivitaeten des angemeldeten Nutzers zur&uuml;ck.
     */
    public String getSelectedFreizeitaktivitaetenString() {
        return selectedFreizeitaktivitaetenString;
    }

    /**
     * Ersetzt den {@code String} mit den selektierten Freizeitaktivitaeten des Nutzers durch einen neue {@code String}.
     *
     * @param selectedFreizeitaktivitaetenString Der {@code String} mit den selektierten Freizeitaktivitaeten,
     *                                           welche den alten {@code String} ersetzen.
     */
    public void setSelectedFreizeitaktivitaetenString(String selectedFreizeitaktivitaetenString) {
        this.selectedFreizeitaktivitaetenString = selectedFreizeitaktivitaetenString;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    /**
     * Wandelt {@link #selectedSprachenString selectedSprachenString} um in eine Liste von {@code Sprache}-Objekten und
     * f&uuml;gt diese Objekte dem {@link #nutzer nutzer} hinzu.
     */
    private void addSprachenToNutzer() {
        String[] selectedSprachenArray = selectedSprachenString.split(",");
        for (String aSelectedSprachenArray : selectedSprachenArray) {
            selectedSprachenList.add(dao.findSpracheByID(Integer.parseInt(aSelectedSprachenArray)));
        }
        for (Sprache aSelectedSprachenList : selectedSprachenList) {
            nutzer.addSprache(aSelectedSprachenList);
        }
    }

    /**
     * Wandelt {@link #selectedFreizeitaktivitaetenString selectedFreizeitaktivitaetenString} um in eine Liste von {@code Freizeitaktivitaeten}-Objekten und
     * f&uuml;gt diese Objekte dem {@link #nutzer nutzer} hinzu.
     */
    private void addFreizeitaktivitaetenToNutzer() {
        String[] selectedFreizeitaktivitaetenArray = selectedFreizeitaktivitaetenString.split(",");
        for (String aSelectedFreizeitaktivitaetenArray : selectedFreizeitaktivitaetenArray) {
            selectedFreizeitaktivitaetenList.add(dao.findFreizeitaktivitaetenByID(Integer.parseInt(aSelectedFreizeitaktivitaetenArray)));
        }
        for (Freizeitaktivitaeten aSelectedFrezeitaktivitaetenList : selectedFreizeitaktivitaetenList) {
            nutzer.addFreizeitaktivitaeten(aSelectedFrezeitaktivitaetenList);
        }
    }

    /**
     * Erzeugt eine {@code LoginManagedBean} und f&uuml;gt ihrem {@code Nutzer}-Objekt {@link #nutzer nutzer} hinzu.
     * Zus&auml;tslich wird die E-Mail-Adresse des {@code Nutzer}-Objekts in der Session registriert, um durch
     * den Filter passieren zu k&ouml;nnen.
     */
    private void initNutzer() {
        FacesContext context = FacesContext.getCurrentInstance();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        loginManagedBean.setNutzer(dao.findNutzerByMail(nutzer.getMail()));
        context.getExternalContext().getSessionMap().put("nutzer", nutzer.getMail());

    }

    private boolean contextExists() {
        try {
            return FacesContext.getCurrentInstance() != null;
        } catch (NullPointerException e) {
            return false;
        }
    }
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
